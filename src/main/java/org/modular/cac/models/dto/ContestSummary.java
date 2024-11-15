package org.modular.cac.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modular.cac.models.Contest;
import org.modular.cac.models.Submission;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
/**
 * One per participant
 */
public class ContestSummary {

    private Contest contest;
    private String handle;
    private List<Submission> submissions;
}
