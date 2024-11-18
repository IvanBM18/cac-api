package org.modular.cac.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FullAttendance {

    // Fields from the `classes` table
    private Long classId;
    private String className;
    private String classDescription;
    private LocalDateTime classDate;

    // Fields from the `students` table
    private Long studentId;
    private String firstName;
    private String lastName;
    private String email;
    private String siiauCode;

    // Field from the `attendances` table
    private Long attendanceId;

    public static List<RowData> mapToRowData(List<FullAttendance> fullAttendances) {
        // Agrupar FullAttendance por lastName y firstName (por estudiante)
        return fullAttendances.stream()
                .collect(Collectors.groupingBy(
                        fa -> new AbstractMap.SimpleEntry<>(fa.getLastName(), fa.getFirstName()), // Agrupar por apellido y nombre
                        Collectors.mapping(FullAttendance::getClassDate, Collectors.toList()) // Recopilar las fechas de las clases (attendances)
                ))
                .entrySet()
                .stream()
                .map(entry -> new RowData(
                        entry.getKey().getKey(), // lastName
                        entry.getKey().getValue(), // firstName
                        entry.getValue() // Lista de attendances
                ))
                .collect(Collectors.toList());
    }
}
