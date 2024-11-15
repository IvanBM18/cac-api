package org.modular.cac.models.views;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "group_attendance")
public class HandleSubmissions {

    @Id
    @Column(name = "submission_id")
    private Long submissionId;

    @Column(name = "code_profile_id")
    private Long codeProfileId;

    @Column(name = "identifier")
    private String identifier;

    @Column(name = "student_id")
    private Long studentId;

    @Column(name = "problem")
    private String problem;

    @Column(name = "verdict")
    private String verdict;

    @Column(name = "contest_id")
    private Long contestId;

    @Column(name = "name")
    private String contestName;

    @Column(name = "total_problems")
    private Integer totalProblems;

    @Column(name = "difficulty")
    private Integer difficulty;

    @Column(name = "resource_id")
    private Long resourceId;
}
