package org.modular.cac.endpoints;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.dhatim.fastexcel.reader.Cell;
import org.dhatim.fastexcel.reader.ReadableWorkbook;
import org.dhatim.fastexcel.reader.Row;
import org.dhatim.fastexcel.reader.Sheet;
import org.modular.cac.models.dto.FullAttendance;
import org.modular.cac.models.dto.RowData;
import org.modular.cac.models.dto.ColumnValue;
import org.modular.cac.services.AttendanceService;
import org.modular.cac.utils.AttendanceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/file")
@Tag(name = "Import/Export",description = "API for Importing and Exporting metadata from .xslx fil")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ExcelController {

    public static final String tempDir = "temp";

    private final AttendanceService attendanceService;

    @PostMapping("/import")
    public ResponseEntity<String> uploadAssistance(@RequestBody MultipartFile file){

        try {
            byte[] fileBytes = file.getBytes();
            var result = processFile(fileBytes);

            var notSavedRows = attendanceService.importAttendance(result);
            var notSavedMessage = "Hubo conflictos para guardar la asistencia de: " +
                    notSavedRows.stream().map(RowData::toString).collect(Collectors.joining("/n"))
                    + " valida que su nombre + apellido sean unicos";

            return ResponseEntity.ok("Archivo recibido y procesado con éxito.\n Se añadieron asistencias para " + result.size() + " alumnos. Errores: \n" + notSavedMessage);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al procesar el archivo");
        }
    }

    @GetMapping("/export")
    public ResponseEntity<Resource> exportAssistance(@RequestParam(value = "fromMonth",defaultValue = "0") int fromMonth, @RequestParam(value = "fromMonth", defaultValue = "0") int toMonth) throws IOException {
        List<FullAttendance> fullAttendances;
        //TODO: Test this conditional
        if(fromMonth == 0 && toMonth == 0){
            fullAttendances = attendanceService.getFullAttendance();
        }else if (fromMonth != 0 && toMonth == 0){
            fullAttendances = attendanceService.getFullAttendance().stream()
                    .filter(AttendanceUtils.filterStartingByMonth(fromMonth))
                    .collect(Collectors.toList());
        }else if(fromMonth == 0) {
            fullAttendances = attendanceService.getFullAttendance().stream()
                    .filter(AttendanceUtils.filterMonthNotOverGivenMonth(toMonth))
                    .collect(Collectors.toList());
        }else {
            fullAttendances = attendanceService.getFullAttendance().stream()
                    .filter(AttendanceUtils.filterbyMonthRange(fromMonth,toMonth))
                    .collect(Collectors.toList());
        }
        var data = FullAttendance.mapToRowData(fullAttendances);

        Path output = AttendanceUtils.exportAttendance(data);
        Resource resource = new UrlResource(output.toUri());
        if (!resource.exists() || !resource.isReadable()) {
            throw new RuntimeException("No se puede leer el archivo: " + output);
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }


    public List<RowData> processFile(byte[] file) throws IOException {
        List<RowData> attendancesToUpload = new ArrayList<>();

        try(ReadableWorkbook workbook = new ReadableWorkbook(new ByteArrayInputStream(file))){
            Sheet mainSheet = workbook.getFirstSheet();
            int rowCount = 0;
            Month startMonth = null;
            Map<Integer, ColumnValue<String>> valueForColumnMap = null;

            for(Row r : mainSheet.read()){
                if(rowCount == 0){
                    startMonth = getStartMonth(r);
                }else if (rowCount == 1){
                    valueForColumnMap = mapTypesFromRow(r);
                }else {
                    if(startMonth == null){
                        throw new IOException("Starting Month not found in first row of given file");
                    }

                    if(valueForColumnMap == null || valueForColumnMap.isEmpty() || valueForColumnMap.size() == 1){
                        throw new IOException("Failed to map any type from the 2nd row");
                    }

                    var row = parseRow(r,valueForColumnMap, startMonth);
                    if(row.getFirstName() != null){
                        attendancesToUpload.add(row);
                    }
                }
                rowCount++;
            }

        }
        return attendancesToUpload;
    }

    private Month getStartMonth(Row r){
        for(Cell c : r){
            if(c == null || StringUtils.isEmpty(c.getRawValue())){
                continue;
            }
            return AttendanceUtils.parseMonth(c.getRawValue());
        }
        return null;
    }

    private Map<Integer, ColumnValue<String>> mapTypesFromRow(Row r){
        Map<Integer, ColumnValue<String>> mappedTypes = new HashMap<>();
        for(Cell c : r){
            if(c == null || StringUtils.isEmpty(c.getRawValue())){
                continue;
            }
            var cellValue = c.getRawValue();
            if(NumberUtils.isCreatable(cellValue)){
                mappedTypes.put(c.getColumnIndex(),new ColumnValue<>(cellValue));
            }
            mappedTypes.put(c.getColumnIndex(),new ColumnValue<>(cellValue));
        }

        return mappedTypes;
    }

    private RowData parseRow(Row r, Map<Integer, ColumnValue<String>> columnValues, Month startingMonth){
        var currentMonth = startingMonth;
        var lastDay = 0;
        RowData result = new RowData();
        for(Map.Entry<Integer, ColumnValue<String>> cellType : columnValues.entrySet()){
            var rawType = cellType.getValue().getValueType();
            boolean isDayOfMonth = NumberUtils.isCreatable(rawType);
            var currentCell = r.getCell(cellType.getKey());
            String rawValue;

            rawValue = currentCell == null ? null : currentCell.getRawValue();
            if(rawValue == null){
                if(isDayOfMonth)
                    rawValue = "0";
                else
                    continue;
            }

            if(isDayOfMonth){
                int dayOfMonth = Integer.parseInt(rawType.contains(".")
                        ? rawType.substring(0,rawType.indexOf("."))
                        : rawType);
                if(lastDay > dayOfMonth){
                    currentMonth = currentMonth.plus(1);
                }
                lastDay = dayOfMonth;

                //0 no se registra asistencia,
                //1 se registra
                if(rawValue.equals("1.0") || rawValue.equals("1")){
                    result.addAttendance(AttendanceUtils.getDateFrom(currentMonth, dayOfMonth) );
                }

            }else if(rawType.equalsIgnoreCase("apellido")){
                result.setLastName(rawValue);
            }else if (rawType.equalsIgnoreCase("nombre")){
                result.setFirstName(rawValue);
            }
        }

        return result;
    }
}
