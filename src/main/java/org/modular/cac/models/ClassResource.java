package org.modular.cac.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Entity
@Table(name = "class_resources")
public class ClassResource {

    public ClassResource(){this.uploadDate = LocalDateTime.now(); }

    @Id
    private Long resourceId;
    @Column(nullable = false)
    private char contentType;
    @Lob
    private byte[] blobContent;
    @Lob
    private String clobContent;
    private String contentUrl;
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime uploadDate;
    @Column(nullable = false)
    private Long userId;
    private Long classId;
}
