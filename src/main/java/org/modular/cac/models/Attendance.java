package org.modular.cac.models;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "attendances")
public class Attendance {

    @Id
    private Long attendanceId;
    @Column(nullable = false)
    private Long studentId;
    @Column(nullable = false)
    private Long classId;
}
