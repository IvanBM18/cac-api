package org.modular.cac.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "classes")
public class Classes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long classid;
    @Column(nullable = false)
    private String name;
    private String description;
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime classDate;
    private Long groupId;
    private Long professorId;
}
