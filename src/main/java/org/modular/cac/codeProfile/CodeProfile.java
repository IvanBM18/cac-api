package org.modular.cac.codeProfile;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modular.cac.student.Student;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "code_profiles")
public class CodeProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence-generator")
    @SequenceGenerator(name = "student_id_squence",sequenceName = "code_profile_sequence",initialValue = 0,allocationSize = 1)
    private Long id;
    @Column(nullable = false)
    private String platform;
    @Column(nullable = false)
    private String identifier;
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

}
