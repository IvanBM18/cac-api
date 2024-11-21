package org.modular.cac.endpoints;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.modular.cac.models.Classes;
import org.modular.cac.models.Student;
import org.modular.cac.services.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/subject")
@Tag(name = "Subjects",description = "API for Subjects(Materias)")
public class SubjectsController {

    private final SubjectService service;

    @Autowired
    public SubjectsController(SubjectService service){
        this.service =service;
    }

    @GetMapping
    public List<Classes> getSubjects(@RequestParam(name = "page",defaultValue = "0")int page,
                                    @RequestParam(name = "size",defaultValue = "20")int size,
                                     @RequestParam(name = "group",defaultValue ="")String group){
        Pageable pagedRequest = PageRequest.of(page,size);
        if(group.isEmpty()){
            return service.getSubjects(pagedRequest);
        }
        return service.getSubjectsByGroup(group);
    }


    @GetMapping("/id/{id}")
    public Classes getSubjectById(@PathVariable("id")Long id){
        return service.findSubject(id).orElse(new Classes());
    }

    @GetMapping("/no-attendance")
    public List<Classes> getClassesWithoutAttendance(){
        return service.getWithoutAttendance();
    }



    @GetMapping("/all")
    public List<Classes> getAll(){
        return service.getAbsolutelyAll();
    }

    @PutMapping
    public void updateStudent(@RequestBody Classes student){
        service.updateSubject(student);
    }

    @PostMapping
    public void addnewSubject(@RequestBody Classes newClass){
        service.addNewSubject(newClass);
    }

    @DeleteMapping(path = "/{id}")
    public boolean deleteSubject(@PathVariable("id")Long id){
        return service.deleteSubject(id);
    }
}
