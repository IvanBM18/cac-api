package org.modular.cac.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modular.cac.models.Attendance;
import org.modular.cac.models.Classes;
import org.modular.cac.models.Student;
import org.modular.cac.models.dto.FullAttendance;
import org.modular.cac.models.dto.RowData;
import org.modular.cac.models.views.GroupAttendance;
import org.modular.cac.repositories.AttendanceRepository;
import org.modular.cac.repositories.SubjectsRepository;
import org.modular.cac.repositories.views.GroupAttendanceRepository;
import org.modular.cac.student.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Slf4j
@Component
@AllArgsConstructor(onConstructor_ = @Autowired)
public class AttendanceService {

    private final GroupAttendanceRepository groupAttendance;

    private final AttendanceRepository attendanceRepository;
    private final SubjectsRepository subjectsRepository;

    private final StudentService studentService;

    private final SubjectService classService;

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

    public void addAttendanceWithoutClass(Attendance attendance, LocalDateTime dateOfClass){
        var tempName = "Clase del " + dateOfClass.getDayOfMonth()  + " de " + dateOfClass.getMonth().getDisplayName(TextStyle.FULL, new Locale("es","MX")) + "," + dateOfClass.getYear();
        var subject = classService.getSubjectByDate(dateOfClass.getMonthValue(),dateOfClass.getDayOfMonth());
        Classes actualSubject;
        actualSubject = subject.orElseGet(() ->
                classService.addNewSubject(new Classes(-1L, tempName, "", dateOfClass, 0L, null)));

        attendance.setClassId(actualSubject.getClassId());
        attendanceRepository.save(attendance);
    }

    public List<RowData> importAttendance(List<RowData> attendances){
        var result = new ArrayList<RowData>();
        attendances.forEach( i ->{
            final AtomicLong studentId = new AtomicLong(-1L);
            String lastName = i.getLastName();
            String firstName = i .getFirstName();
            if(lastName != null && firstName != null){
                var referencedStudents = studentService.searchStudentsByName(firstName.toLowerCase(),lastName.toLowerCase());
                if(referencedStudents.size() > 1){
                    result.add(i);
                }else if (referencedStudents.isEmpty()){
                    var currentStudent = new Student(-1L,firstName,lastName,"","", LocalDateTime.now());
                    studentId.set(studentService.addStudentWithoutCode(currentStudent).getStudentId());
                }else {
                    studentId.set(referencedStudents.get(0).getStudentId());
                }

                if(studentId.get() != -1L){
                    i.getAttendances().forEach(date -> {
                        try{
                            addAttendanceWithoutClass(new Attendance(-1L, studentId.get(), -1L),date);
                        }catch (Exception e){
                            log.warn("Attendance for {} at {}, not saved: {}",studentId.get(),date.toLocalDate().toString(), e.getMessage());
                        }
                    });
                }
            }
        });
        return result;
    }

    public List<FullAttendance> getFullAttendance(){

        List<Object[]> result = subjectsRepository.findFullAttendance();
        List<FullAttendance> fullAttendances = new ArrayList<>();

        for (Object[] row : result) {
            Long classId = ((BigDecimal)row[0]).longValue() ;
            String className = (String) row[1];
            String classDescription = (String) row[2];
            LocalDateTime classDate = ((Timestamp) row[3]).toLocalDateTime();
            Long studentId = ((BigDecimal) row[4]).longValue();
            String firstName = (String) row[5];
            String lastName = (String) row[6];
            String email = (String) row[7];
            String siiauCode = (String) row[8];
            Long attendanceId = ((BigDecimal) row[9]).longValue();

            FullAttendance fullAttendance = new FullAttendance(
                    classId, className, classDescription, classDate,
                    studentId, firstName, lastName, email, siiauCode,
                    attendanceId
            );

            fullAttendances.add(fullAttendance);
        }

        return fullAttendances;
    }

    public List<Attendance> getAbsolutelyAll(){
        var result = attendanceRepository.findAll();
        var allClasses = new ArrayList<Attendance>();
        result.forEach(allClasses::add);
        return allClasses;
    }
}
