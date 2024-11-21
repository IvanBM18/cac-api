package org.modular.cac.endpoints;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.modular.cac.models.Attendance;
import org.modular.cac.models.dto.FullAttendance;
import org.modular.cac.models.views.GroupAttendance;
import org.modular.cac.services.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/attendance")
@Tag(name = "Attendance",description = "API for Student Attendance")
@AllArgsConstructor(onConstructor_ = @Autowired)
public class AttendanceController {

    AttendanceService service;


    @GetMapping
    public List<Attendance> getAll(@RequestParam(name = "page",defaultValue = "0")int page,
                                   @RequestParam(name = "size",defaultValue = "20")int size){
        Pageable pageRequest = PageRequest.of(page, size);
        return service.getAll(pageRequest);
    }

    @GetMapping("/abs/all")
    public ResponseEntity<List<Attendance>> getAbsAll(){

        return ResponseEntity.ok(service.getAbsolutelyAll());
    }

    @GetMapping("/all")
    public ResponseEntity<List<FullAttendance>> getAll(){

        return ResponseEntity.ok(service.getFullAttendance());
    }

    @GetMapping("/student/{id}")
    public List<Attendance> getAllForStudent(@PathVariable("id")Long id){
        return service.getAllForStudent(id);
    }
    @GetMapping("/student")
    public List<Attendance> getAllForStudent(@RequestParam(name="siiauCode")String siiauCode){
        return service.getAllForStudent(siiauCode);
    }

    @GetMapping("/subject/{id}")
    public List<Attendance> getAllForSubject(@PathVariable("id")Long classId){
        return service.getAllForClass(classId);
    }

    @GetMapping("/group/{id}")
    public List<GroupAttendance> getAllForGroup(@PathVariable("id")Long groupId){
        return service.getAllByGroup(groupId);
    }

    @PutMapping
    public boolean updateAttendance(@RequestBody Attendance attendance){
        return service.updateAttendance(attendance);
    }

    @PostMapping
    public void addAttendance(@RequestBody Attendance attendance){
        if(attendance.getAttendanceId() == null){
            attendance.setAttendanceId((long) -1);
        }
        service.addAttendance(attendance);
    }

    //New assistance (POST)

}
