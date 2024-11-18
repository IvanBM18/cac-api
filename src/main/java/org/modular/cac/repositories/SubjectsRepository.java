package org.modular.cac.repositories;

import org.modular.cac.models.Classes;
import org.modular.cac.models.dto.FullAttendance;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectsRepository extends PagingAndSortingRepository<Classes,Long>, CrudRepository<Classes,Long> {

    @Query( value = "SELECT c.class_id AS class_id, " +
            "    c.name AS name," +
            "    c.description AS description, " +
            "    c.class_date AS class_date," +
            "    c.group_id AS group_id, " +
            "    c.professor_id AS professor_id" +
            "    FROM Classes c JOIN groups g " +
            "    ON g.group_name = :groupName"
            ,nativeQuery = true)
    List<Classes> findByGroupName(@Param("groupName") String groupName);

    @Query(value = "SELECT * FROM classes c WHERE EXTRACT(MONTH FROM c.class_date) = :month AND EXTRACT(DAY FROM c.class_date) = :day",
            nativeQuery = true)
    List<Classes> findClassesByDayAndMonth(@Param("day") int day, @Param("month") int month);

    @Query(value = "SELECT c.class_id AS classId, " +
            "    c.name AS className, " +
            "    c.description AS classDescription, " +
            "    c.class_date AS classDate," +
            "    s.student_id AS studentId, " +
            "    s.first_name AS firstName, " +
            "    s.last_name AS lastName, " +
            "    s.email AS email, " +
            "    s.siiau_code AS siiauCode, " +
            "    a.attendance_id AS attendanceId " +
            "FROM classes c " +
            "LEFT JOIN attendances a ON c.class_id = a.class_id " +
            "JOIN students s ON s.student_id = a.student_id",
            nativeQuery = true)
    List<Object[]> findFullAttendance();


}
