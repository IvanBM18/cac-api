package org.modular.cac.endpoints;


import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modular.cac.codeProfile.CodeProfileService;
import org.modular.cac.models.Contest;
import org.modular.cac.models.Submission;
import org.modular.cac.models.dto.ContestSummary;
import org.modular.cac.services.ContestService;
import org.modular.cac.services.SubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@RestController
@RequestMapping("api/v1/submission")
@Tag(name = "Submission",description = "API for Student Submissions")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class SubmissionsController {

    private final SubmissionService submissionService;

    private final CodeProfileService codeProfileService;

    private final ContestService contestService;

    //Get Submissions for studentId, siiauCode, codeProfileId and handle

    @PostMapping("/")
    public int addSubmission(Submission submission){
        submissionService.saveSubmission(submission);
        return 1;
    }

    @PostMapping("/batch")
    public int addSubmissions(@RequestBody List<ContestSummary> summaryForParticipant){
        if(summaryForParticipant.isEmpty()){
            return 0;
        }
        AtomicInteger result = new AtomicInteger();
        Contest contest = summaryForParticipant.get(0).getContest();
        contestService.addIfNonExistent(contest);
        summaryForParticipant.forEach(i -> {
            String handle = i.getHandle();
            Long codeProfileId = codeProfileService.addIfNonExistent(handle);

            List<Submission> submissions = i.getSubmissions().stream()
                    .peek(submission -> submission.setCodeProfileId(codeProfileId))
                    .toList();

            log.info("[Submissions] saving {} submisisions for {} at contest {} with Id {}",submissions.size(), handle,contest.getName() ,contest.getContestId() );
            submissionService.saveMultipleSubmissions(submissions);
            result.addAndGet(submissions.size());
        });
        return result.get();
    }
}


