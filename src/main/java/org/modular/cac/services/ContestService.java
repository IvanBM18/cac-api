package org.modular.cac.services;

import lombok.AllArgsConstructor;
import org.modular.cac.models.Contest;
import org.modular.cac.models.Student;
import org.modular.cac.repositories.ContestRepository;
import org.modular.cac.repositories.views.StudentSubmissionRepository;
import org.modular.cac.views.StudentSubmissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor(onConstructor_ = @Autowired)
public class ContestService {

    private final ContestRepository contestRepo;
    private final StudentSubmissionRepository studentSubmissionRepository;

    public List<StudentSubmissions> getAllContestsFromStudent(Student s){
        return studentSubmissionRepository.findSubmissionsByStudentId(s.getStudentId());
    }

    public List<Contest> getAllContests(Pageable page){
        return contestRepo.findAll(page).stream().toList();
    }

    /**
     * Like a set adds a contest if it exists
     * @param contest contest to add
     * @return true if added, if it already exists returns false
     */
    public boolean addIfNonExistent(Contest contest){
        contest.setContestId(contest.getContestId() == null ? -1 : contest.getContestId());
        if(contestRepo.existsById(contest.getContestId())){
            return false;
        }
        contestRepo.save(contest);
        return true;
    }

}
