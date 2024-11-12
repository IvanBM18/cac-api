package org.modular.cac.models.views;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.View;
import org.modular.cac.models.Attendance;

@Data
@Entity
@Table(name = "group_attendance")
public class GroupAttendance {

    @Id
    @Column(name = "attendance_id")
    private Long attendanceId;
    @Column(name = "group_id")
    private Long groupId;
    @Column(name = "group_name")
    private String groupName;
    @Column(name = "student_id")
    private Long studentId;
    @Column(name = "class_id")
    private Long classId;
    @Column(name = "name")
    private String name;
    @Column(name = "professor_id")
    private Long professorId;

    @JsonIgnore
    public static Attendance parse(GroupAttendance attendance){
        return new Attendance(attendance.getAttendanceId(), attendance.getStudentId(), attendance.getClassId());
    }
}
