package org.modular.cac.endpoints;


import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.modular.cac.codeProfile.CodeProfileService;
import org.modular.cac.models.Contest;
import org.modular.cac.models.Submission;
import org.modular.cac.models.dto.ContestSummary;
import org.modular.cac.services.ContestService;
import org.modular.cac.services.SubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
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

    @GetMapping("/")
    public List<Submission> getSubmissions(@RequestParam(name = "page",defaultValue = "0")int page,
                                           @RequestParam(name = "size",defaultValue = "20")int size){
        Pageable pagedRequest = PageRequest.of(page,size);
        return submissionService.getAllSubmissions(pagedRequest);
    }
    @GetMapping("/student")
    public List<Submission> getSubmissions(@RequestParam(name = "page",defaultValue = "0")int page,
                                           @RequestParam(name = "size",defaultValue = "20")int size,
                                           @RequestParam(name = "id", required = false) Long studentId,
                                           @RequestParam(name = "siiauCode", required = false) String siiauCode){
        Pageable pagedRequest = PageRequest.of(page,size);
        if( studentId != null && !studentId.equals((long) -1) ){
            return submissionService.getAllbyStudentId(pagedRequest,studentId);
        }else if ( !StringUtils.isEmpty(siiauCode)){
            return submissionService.getAllbySiiauCode(pagedRequest,siiauCode);
        }else {
            return Collections.emptyList();
        }
    }

    @GetMapping("/handle/{handle}")
    public List<Submission> getByHandle(@RequestParam(name = "page",defaultValue = "0")int page,
                                        @RequestParam(name = "size",defaultValue = "20")int size,
                                        @PathVariable(name = "handle") String handle){
        Pageable pagedRequest = PageRequest.of(page,size);
        return submissionService.getAllbyHandle(pagedRequest,handle);
    }


    @PostMapping("/")
    public int addSubmission(@RequestBody Submission submission){
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


