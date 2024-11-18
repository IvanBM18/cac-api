package org.modular.cac.repositories.dto;

import org.modular.cac.models.dto.FullAttendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FullAttendanceRepository extends JpaRepository<FullAttendance, Long> {

    @Query(value = """
        SELECT c.class_id AS classId, c.name AS className, c.description AS classDescription, c.class_date AS classDate,
               s.student_id AS studentId, s.first_name AS firstName, s.last_name AS lastName, s.email AS email, s.siiau_code AS siiauCode,
               a.attendance_id AS attendanceId
        FROM cac.classes c
        LEFT JOIN cac.attendances a ON c.class_id = a.class_id
        JOIN cac.students s ON s.student_id = a.student_id
    """, nativeQuery = true)
    List<FullAttendance> findClassAttendances();
}
