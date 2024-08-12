package org.modular.cac.student;

import org.modular.cac.models.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public List<Student> getStudents(@RequestParam(name = "page",defaultValue = "0")int page,
                                     @RequestParam(name = "size",defaultValue = "20")int size){
        Pageable pagedRequest = PageRequest.of(page,size);
        return service.getStudents(pagedRequest);
    }

    @GetMapping(path = "/{id}")
    public Student getStudentById(@PathVariable("id")Long id) {
        return service.searchStudent(id).orElse(new Student());
    }

    @PostMapping
    public void addnewStudent(@RequestBody Student newStudent){
        service.addStudent(newStudent);
    }

    @PutMapping
    public void updateStudent(@RequestBody Student student){
        service.updateStudent(student);
    }

}
