package org.modular.cac.services;

import lombok.RequiredArgsConstructor;
import org.modular.cac.models.Submission;
import org.modular.cac.repositories.SubmissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class SubmissionService {

    private final SubmissionRepository repo;


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
