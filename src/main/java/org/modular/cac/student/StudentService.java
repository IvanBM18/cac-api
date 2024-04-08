package org.modular.cac.student;

import lombok.extern.slf4j.Slf4j;
import org.modular.cac.models.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


    public List<Student> getStudents() {
        return repository.findAll();
    }

    public Optional<Student> searchStudent(Long id){
        return repository.findById(id);
    }

    public void addStudent(Student student){
        if(repository.findBySiiauCode(student.getSiiauCode()).isPresent()){
            throw new IllegalStateException("Siiau Code taken");
        }
        if(!student.isValid()){
            throw new IllegalArgumentException("Invalid Siiau Code");
        }
        repository.save(student);
    }
}
