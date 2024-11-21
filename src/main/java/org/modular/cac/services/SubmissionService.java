package org.modular.cac.services;

import lombok.RequiredArgsConstructor;
import org.modular.cac.models.Submission;
import org.modular.cac.models.views.HandleSubmissions;
import org.modular.cac.repositories.SubmissionRepository;
import org.modular.cac.repositories.views.HandleSubmissionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class SubmissionService {

    private final SubmissionRepository repo;
    private final HandleSubmissionsRepository handleSubmissionsRepo;

    public List<HandleSubmissions> getAllSubmissions(Pageable page){
        return handleSubmissionsRepo.findAll(page).stream().toList();
    }

    public List<HandleSubmissions> getAllbyStudentId(Pageable page,Long id){
        var result = handleSubmissionsRepo.findByStudentId(id,page);
        return result.stream().toList();
    }

    public List<HandleSubmissions> getAllbySiiauCode(Pageable page,String siiauCode){
        var result = handleSubmissionsRepo.findBySiiauCode(siiauCode,page);
        return result.stream().toList();
    }


    public List<HandleSubmissions> getAllbyHandle(Pageable page,String handle){
        var result = handleSubmissionsRepo.findByIdentifier(handle,page);
        return result.stream().toList();
    }


    public void saveMultipleSubmissions(List<Submission> submissions){
        repo.saveAll(submissions);
    }

    public void saveSubmission(Submission submission){
        submission.setSubmissionId(submission.getSubmissionId() == null ? -1 : submission.getSubmissionId());
        if(repo.existsById(submission.getSubmissionId())){
            return;
        }
        repo.save(submission);
    }
}
