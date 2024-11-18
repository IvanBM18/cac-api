package org.modular.cac.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
}
