package org.modular.cac.utils;

import lombok.experimental.UtilityClass;
import org.dhatim.fastexcel.Workbook;
import org.dhatim.fastexcel.Worksheet;
import org.modular.cac.TestExport;
import org.modular.cac.models.dto.FullAttendance;
import org.modular.cac.models.dto.RowData;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.*;
import java.util.function.Predicate;

@UtilityClass
public class AttendanceUtils {

    public static LocalDateTime getDateFrom(Month month, Integer day){
        int year = LocalDate.now().getYear();
        var date = LocalDate.of(year,month,day);

        return date.atStartOfDay();
    }

    public static Month parseMonth(String month){
        try{
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM", new Locale("es", "MX"));
            TemporalAccessor temporal = formatter.parse(month.toLowerCase());
            return  Month.from(temporal);
        }catch (Exception e){
            return null;
        }

    }

    public static Predicate<FullAttendance> filterbyMonthRange(int fromMonth, int toMonth) {
        return fa -> {
            int classMonth = fa.getClassDate().getMonthValue();
            return classMonth >= fromMonth && classMonth <= toMonth;
        };
    }

    public static Predicate<FullAttendance> filterStartingByMonth(int fromMonth) {
        return fa -> fa.getClassDate().getMonthValue() == fromMonth;
    }

    public static Predicate<FullAttendance> filterMonthNotOverGivenMonth(int toMonth) {
        return fa -> fa.getClassDate().getMonthValue() <= toMonth;
    }

    public Path exportAttendance(List<RowData> data) throws IOException {
        var outputPath = Paths.get("").resolve("output.xlsx");
        try (FileOutputStream fos = new FileOutputStream(outputPath.toAbsolutePath().toString()) ) {
            Workbook workbook = new Workbook(fos, "My Application", "1.0");
            Worksheet worksheet = workbook.newWorksheet("Asistencias");

            // Escribir encabezados
            Map<String, Integer> dayColumns = writeHeaders(worksheet, data);

            // Escribir datos de asistencias
            writeAttendanceData(worksheet, data, dayColumns);

            // Guardar archivo
            workbook.finish();
        }

        return outputPath.toAbsolutePath();
    }

    private static Map<String, Integer> writeHeaders(Worksheet worksheet, List<RowData> data) {
        // Colección de días por mes
        Map<String, Set<Integer>> monthDays = new LinkedHashMap<>();
        for (RowData rowData : data) {
            for (LocalDateTime attendance : rowData.getAttendances()) {
                String month = attendance.format(DateTimeFormatter.ofPattern("MMMM"));
                int day = attendance.getDayOfMonth();
                monthDays.computeIfAbsent(month, k -> new TreeSet<>()).add(day);
            }
        }

        // Escribir encabezados
        worksheet.value(1, 0, "Apellido");
        worksheet.value(1, 1, "Nombre");

        Map<String, Integer> dayColumns = new HashMap<>();
        int col = 2; // Desde la tercera columna
        for (Map.Entry<String, Set<Integer>> entry : monthDays.entrySet()) {
            String month = entry.getKey();
            Set<Integer> days = entry.getValue();

            // Celda combinada para el mes
            worksheet.range(0, col, 0, col + days.size() - 1).merge();
            worksheet.value(0, col, month);

            // Escribir días
            for (int day : days) {
                worksheet.value(1, col, day);
                dayColumns.put(month + "-" + day, col); // Mapear "mes-día" a su columna
                col++;
            }
        }
        return dayColumns;
    }

    private static void writeAttendanceData(Worksheet worksheet, List<RowData> data, Map<String, Integer> dayColumns) {
        int row = 2; // Fila inicial para datos
        for (RowData rowData : data) {
            worksheet.value(row, 0, rowData.getLastName());
            worksheet.value(row, 1, rowData.getFirstName());

            for (LocalDateTime attendance : rowData.getAttendances()) {
                String month = attendance.format(DateTimeFormatter.ofPattern("MMMM"));
                int day = attendance.getDayOfMonth();
                Integer col = dayColumns.get(month + "-" + day);

                if (col != null) {
                    worksheet.value(row, col, "1");
                }
            }
            row++;
        }
    }

}
