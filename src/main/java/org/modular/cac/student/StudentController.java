package org.modular.cac.student;

import org.modular.cac.models.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/student")
public class StudentController {

    private final StudentService service;

    @Autowired
    public StudentController(StudentService service){
        this.service = service;
    }

    @GetMapping
    public List<Student> getStudents(){
        return service.getStudents();
    }

    @GetMapping(path = "/{id}")
    public Student getStudentById(@PathVariable("id")Long id) {
        return service.searchStudent(id).orElse(new Student());
    }

    @PostMapping
    public void addnewStudent(@RequestBody Student student){
        service.addStudent(student);
    }

}
