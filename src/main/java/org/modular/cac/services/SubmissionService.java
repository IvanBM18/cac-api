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

    public List<Submission> getAllSubmissions(Pageable page){
        return repo.findAll(page).stream().toList();
    }

    public List<Submission> getAllbyStudentId(Pageable page,Long id){
        var result = handleSubmissionsRepo.findByStudentId(id,page);
        return result.stream().map(HandleSubmissions::mapToSubmission).toList();
    }

    public List<Submission> getAllbySiiauCode(Pageable page,String siiauCode){
        var result = handleSubmissionsRepo.findByStudent_SiiauCode(siiauCode,page);
        return result.stream().map(HandleSubmissions::mapToSubmission).toList();
    }


    public List<Submission> getAllbyHandle(Pageable page,String handle){
        var result = handleSubmissionsRepo.findByIdentifier(handle,page);
        return result.stream().map(HandleSubmissions::mapToSubmission).toList();
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
