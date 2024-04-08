package org.modular.cac.models;

import jakarta.persistence.*;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "code_profiles")
public class CodeProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String platform;
    @Column(nullable = false)
    private String identifier;
    @Column(nullable = false)
    private Long student_id;

}
