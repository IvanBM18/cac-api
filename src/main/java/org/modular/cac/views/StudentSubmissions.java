package org.modular.cac.views;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;

@Immutable
@Table(name = "student_submissions")
@Entity(name = "student_submissions")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentSubmissions {

    @Id
    @Column(name = "contest_id")
    private Long contestId;
    @Column(name = "total_problems")
    private Integer totalProblems;
    @Column(name="difficulty")
    private Integer difficulty;
    @Column(name="verdict")
    private String verdict;
    @Column(name = "student_id")
    private Long studentId;

}
