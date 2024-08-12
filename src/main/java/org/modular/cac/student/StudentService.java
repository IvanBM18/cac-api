package org.modular.cac.student;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.Page;
import org.modular.cac.models.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class StudentService {
    private final StudentRepository repository;

    @Autowired
    public StudentService(StudentRepository repository){
        this.repository = repository;
    }


    public List<Student> getStudents(Pageable page) {
        return repository.findAll(page).stream().toList();
    }

    public Optional<Student> searchStudent(Long id){
        return repository.findById(id);
    }

    public void addStudent(Student student){
        if(student.getStudent_id() != null){
            throw  new IllegalArgumentException("Student must have empty id for creation");
        }
        if(repository.findBySiiauCode(student.getSiiauCode()).isPresent()){
            throw new IllegalStateException("Siiau Code taken");
        }
        if(!student.isValid()){
            throw new IllegalArgumentException("Invalid Siiau Code");
        }
        repository.save(student);
    }

    public void updateStudent(Student student){
        if(student.getStudent_id() == null ||  repository.findById(student.getStudent_id()).isEmpty()){
            throw new IllegalArgumentException("Non Existent Student");
        }
        repository.save(student);
    }
}
