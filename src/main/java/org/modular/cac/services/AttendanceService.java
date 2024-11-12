package org.modular.cac.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.Page;
import org.modular.cac.models.Attendance;
import org.modular.cac.models.views.GroupAttendance;
import org.modular.cac.repositories.AttendanceRepository;
import org.modular.cac.repositories.views.GroupAttendanceRepository;
import org.modular.cac.student.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@AllArgsConstructor(onConstructor_ = @Autowired)
public class AttendanceService {

    private final GroupAttendanceRepository groupAttendance;

    private final AttendanceRepository attendanceRepository;

    private final StudentService studentService;

    public List<Attendance> getAll(Pageable page){
        return attendanceRepository.findAll(page).stream().toList();
    }

    public List<Attendance> getAllForStudent(Long id){
        return attendanceRepository.getAllByStudent(id);
    }

    public List<Attendance> getAllForStudent(String siiauCode){
        var resultStudent = studentService.searchStudentByCode(siiauCode);
        if(resultStudent.isEmpty()){
            return Collections.emptyList();
        }

        return attendanceRepository.getAllByStudent(resultStudent.get().getStudentId());
    }

    public List<Attendance> getAllForClass(Long classId){
        return groupAttendance.attendanceByClass(classId)
                .stream()
                .map(GroupAttendance::parse)
                .collect(Collectors.toList());
    }

    public List<GroupAttendance> getAllByGroup(Long groupId){
        var result = groupAttendance.attendanceByGroup(groupId);
        return result;
    }

    public boolean updateAttendance(Attendance attendance){
        if(attendance.getAttendanceId() == null || attendance.getAttendanceId() < 0){
            log.info("[PUT attendance] failed, invalid attendance id");
            return false;
        }
        attendanceRepository.save(attendance);
        return true;
    }

    public void addAttendance(Attendance attendance){
        if(attendanceRepository.existsById(attendance.getAttendanceId())){
            log.info("[Post attendance] failed,id must be empty");
            throw new IllegalArgumentException("Id must be empty");
        }
        attendanceRepository.save(attendance);
    }
}
