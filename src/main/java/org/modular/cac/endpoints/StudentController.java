package org.modular.cac.endpoints;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.modular.cac.models.Student;
import org.modular.cac.student.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/student")
@Tag(name = "Students",description = "API for CAC Students")
public class StudentController {

    private final StudentService service;

    @Autowired
    public StudentController(StudentService service){
        this.service = service;
    }

    @GetMapping
    public List<Student> getStudents(@RequestParam(name = "page",defaultValue = "0")int page,
                                     @RequestParam(name = "size",defaultValue = "20")int size){
        Pageable pagedRequest = PageRequest.of(page,size);
        return service.getStudents(pagedRequest);
    }

    @GetMapping(path = "/{id}")
    public Student getStudentById(@PathVariable("id")Long id) {
        return service.searchStudent(id).orElse(new Student());
    }

    @GetMapping(path = "/{siiauCode")
    public Student getStudentByCode(@PathVariable("siiauCode")String siiauCode){
        return service.searchStudentByCode(siiauCode).orElse(new Student());
    }

    @GetMapping(path = "/{name}")
    public List<Student> getStudentsByName(@PathVariable("name")String name){
        return service.searchStudentsByName(name);
    }


    @PostMapping
    public void addnewStudent(@RequestBody Student newStudent){
        service.addStudent(newStudent);
    }

    @PutMapping
    public void updateStudent(@RequestBody Student student){
        service.updateStudent(student);
    }

    @DeleteMapping(path = "/{id}")
    public boolean deleteStudent(@PathVariable("id")Long id){
        return service.deleteStudent(id);
    }

}
