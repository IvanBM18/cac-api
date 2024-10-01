package org.modular.cac.services;

import lombok.AllArgsConstructor;
import org.modular.cac.models.Student;
import org.modular.cac.repositories.ContestRepository;
import org.modular.cac.repositories.StudentSubmissionRepository;
import org.modular.cac.views.StudentSubmissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor(onConstructor_ = @Autowired)
public class ContestService {

    @Autowired
    private final ContestRepository contestRepo;
    @Autowired
    private final StudentSubmissionRepository studentSubmissionRepository;

    public List<StudentSubmissions> getAllContestsFromStudent(Student s){
        return studentSubmissionRepository.findSubmissionsByStudentId(s.getStudent_id());
    }

}