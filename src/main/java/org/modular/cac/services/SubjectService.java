package org.modular.cac.services;

import org.modular.cac.models.Classes;
import org.modular.cac.models.Student;
import org.modular.cac.repositories.SubjectsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class SubjectService {

    private final SubjectsRepository repository;

    @Autowired
    public SubjectService(SubjectsRepository repository){
        this.repository = repository;
    }

    public List<Classes> getSubjects(Pageable page){
        return repository.findAll(page).stream().toList();
    }

    public List<Classes> getSubjectsByGroup(String groupName){
        return repository.findByGroupName(groupName);
    }

    public Optional<Classes> findSubject(Long id){
        return repository.findById(id);
    }

    public void addNewSubject(Classes newSubject){
        if(newSubject.getClassId() != null){
            throw new IllegalArgumentException("Subjects must have empty id for creation");
        }
        if(!newSubject.isValid()){
            throw new IllegalStateException("Subjects must be linked to a group");
        }
        repository.save(newSubject);
    }

    public void updateSubject(Classes subject){
        if(subject.getClassId() == null ||  repository.findById(subject.getClassId()).isEmpty()){
            throw new IllegalArgumentException("Non Existent Student");
        }
        repository.save(subject);
    }

    public boolean deleteSubject(Long id){
        if(findSubject(id).isPresent()){
            repository.deleteById(id);
            return true;
        }
        throw new IllegalStateException("Non Existent Suybject Id");
    }
}
