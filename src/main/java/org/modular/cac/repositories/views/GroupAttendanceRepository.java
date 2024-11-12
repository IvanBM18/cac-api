package org.modular.cac.repositories.views;

import jakarta.persistence.Id;
import org.modular.cac.models.views.GroupAttendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GroupAttendanceRepository extends JpaRepository<GroupAttendance,Long> {

    @Query(nativeQuery = true, value =
            "SELECT * FROM group_attendance WHERE group_id = :groupId"
    )
    public List<GroupAttendance> attendanceByGroup(@Param("groupId") Long groupId);
    @Query(nativeQuery = true, value =
            "SELECT * FROM group_attendance WHERE class_id = :classId"
    )
    public List<GroupAttendance> attendanceByClass(@Param("classId") Long classId);
}
