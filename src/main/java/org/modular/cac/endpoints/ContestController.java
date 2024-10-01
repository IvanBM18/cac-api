package org.modular.cac.endpoints;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.modular.cac.services.ContestService;
import org.modular.cac.student.StudentService;
import org.modular.cac.views.StudentSubmissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/v1/contest")
@Tag(name = "Contest",description = "API for Contest Data")
public class ContestController {

    @Autowired
    StudentService studentService;

    @Autowired
    ContestService service;

    @GetMapping(value = "/student/{id}")
    public List<StudentSubmissions> getAllContestByStudent(@PathVariable("id") Long id){
        var search = studentService.searchStudent(id);
        if(search.isEmpty()){
            return Collections.emptyList();
        }
        return service.getAllContestsFromStudent(search.get());
    }
}
