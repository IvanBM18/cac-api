package org.modular.cac.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Entity
@Table(name = "contests")
public class Contest {

    public Contest(){
        this.startDate = LocalDateTime.now();
    }

    @Id
    private Long contestId;
    @Column(nullable = false)
    private Integer totalProblems;
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime startDate;
    @Column(nullable = false)
    private Long resourceId;
}
