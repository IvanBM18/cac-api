package org.modular.cac.student;

import lombok.extern.slf4j.Slf4j;
import org.modular.cac.models.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
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


    public List<Student> getStudents(Pageable page) {
        return repository.findAll(page).stream().toList();
    }

    public Optional<Student> searchStudent(Long id){
        return repository.findById(id);
    }

    public Optional<Student> searchStudentByCode(String siiauCode){
        return repository.findBySiiauCode(siiauCode);
    }

    public List<Student> searchStudentsByName(String firstName, String lastName){
        var name = firstName + lastName;
        return repository.findByName(name);
    }

    public Optional<Student> searchByEmail(String email){
        return repository.findByEmail(email);
    }

    public Student addStudent(Student student){
        student.setStudentId((long) (-1));
        if(repository.findBySiiauCode(student.getSiiauCode()).isPresent()){
            throw new IllegalStateException("Siiau Code already taken by another student");
        }
        if(!student.isValid()){
            throw new IllegalArgumentException("Invalid Siiau Code");
        }
        return repository.save(student);
    }

    public Student addStudentWithoutCode(Student student){
        student.setStudentId((long) (-1));
        return repository.save(student);
    }


    public void updateStudent(Student student){
        if(student.getStudentId() == null ||  repository.findById(student.getStudentId()).isEmpty()){
            throw new IllegalArgumentException("Non Existent Student");
        }
        repository.save(student);
    }

    public boolean deleteStudent(Long id){
        if(searchStudent(id).isPresent()){
            repository.deleteById(id);
            return true;
        }
        throw new IllegalStateException("Non Existent Student Id");
    }
}
