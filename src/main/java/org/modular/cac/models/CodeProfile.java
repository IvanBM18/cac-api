package org.modular.cac.models;

import jakarta.persistence.*;
import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "code_profiles")
@Table(name = "code_profiles")
public class CodeProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "code_profile_id")
    private Long codeProfileId;
    @Column(nullable = false)
    private String platform;
    @Column(nullable = false)
    private String identifier;

    @OneToOne
    @JoinColumn(name = "student_id")
    private Student student;

}
