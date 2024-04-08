package org.modular.cac.models;

import jakarta.persistence.*;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
