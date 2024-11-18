package org.modular.cac;

import org.dhatim.fastexcel.Workbook;
import org.dhatim.fastexcel.Worksheet;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class TestExport {

    public static class RowData {
        private String lastName;
        private String firstName;
        private List<LocalDateTime> attendances;

        public RowData(String lastName, String firstName, List<LocalDateTime> attendances) {
            this.lastName = lastName;
            this.firstName = firstName;
            this.attendances = attendances;
        }

        public String getLastName() {
            return lastName;
        }

        public String getFirstName() {
            return firstName;
        }

        public List<LocalDateTime> getAttendances() {
            return attendances;
        }
    }

    public static void main(String[] args) throws IOException {
        // Datos de ejemplo
        List<RowData> data = createSampleData();

        // Generar archivo Excel
        try (FileOutputStream fos = new FileOutputStream("output.xlsx")) {
            Workbook workbook = new Workbook(fos, "My Application", "1.0");
            Worksheet worksheet = workbook.newWorksheet("Asistencias");

            // Escribir encabezados
            Map<String, Integer> dayColumns = writeHeaders(worksheet, data);

            // Escribir datos de asistencias
            writeAttendanceData(worksheet, data, dayColumns);

            // Guardar archivo
            workbook.finish();
        }
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

    private static List<RowData> createSampleData() {
        List<RowData> data = new ArrayList<>();

        data.add(new RowData("Doe", "John", List.of(
                LocalDateTime.of(2024, 11, 1, 10, 0),
                LocalDateTime.of(2024, 11, 3, 10, 0),
                LocalDateTime.of(2024, 12, 2, 10, 0),
                LocalDateTime.of(2024, 12, 5, 10, 0)
        )));

        data.add(new RowData("Smith", "Jane", List.of(
                LocalDateTime.of(2024, 12, 2, 10, 0),
                LocalDateTime.of(2024, 11, 3, 10, 0)
        )));

        return data;
    }
}
