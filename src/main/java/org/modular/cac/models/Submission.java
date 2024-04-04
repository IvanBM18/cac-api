package org.modular.cac.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Entity
@Table(name = "submissions")
public class Submission {

    public Submission(){
        this.penalty = 0;
    }

    @Id
    private Long submissionId;
    @Column(nullable = false)
    private String problem;
    @Column(nullable = false)
    private String verdict;
    private Integer penalty;
    @Column(nullable = false)
    private Long codeProfileId;
    @Column(nullable = false)
    private Long contestId;
}
