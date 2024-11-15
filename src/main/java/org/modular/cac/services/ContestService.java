package org.modular.cac.services;

import lombok.AllArgsConstructor;
import org.modular.cac.models.ClassResource;
import org.modular.cac.models.Contest;
import org.modular.cac.models.Student;
import org.modular.cac.repositories.ClassResourcesRepository;
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
    private final ClassResourceService resourceService;

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
        contest.setResourceId(null);
        if(contestRepo.existsById(contest.getContestId())){
            return false;
        }
        var savedContest = contestRepo.save(contest);
        var resource = new ClassResource(-1L, "U",
                null,
                null,
                "https://codeforces.com/contest/" + savedContest.getContestId(),
                "Contest URL For " + savedContest.getName(),
                savedContest.getStartDate(),
                0L,
                null
                );
        savedContest.setResourceId(resourceService.add(resource));
        contestRepo.save(savedContest);
        return true;
    }

}
