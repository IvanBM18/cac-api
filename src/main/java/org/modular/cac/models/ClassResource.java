package org.modular.cac.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Blob;
import java.sql.Clob;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Entity
@Table(name = "class_resources")
public class ClassResource {

    public ClassResource(){this.uploadDate = LocalDateTime.now(); }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RESOURCE_ID", nullable = false)
    private Long resourceId;

    @Column(name = "CONTENT_TYPE", nullable = false, length = 1)
    private String contentType;

    @Lob
    @Column(name = "BLOB_CONTENT")
    private Blob blobContent;

    @Lob
    @Column(name = "CLOB_CONTENT")
    private Clob clobContent;

    @Column(name = "CONTENT_URL", length = 256)
    private String contentUrl;

    @Column(name = "DESCRIPTION", length = 512)
    private String description;

    @Column(name = "UPLOAD_DATE")
    private LocalDateTime uploadDate;

    @Column(name = "USER_ID", nullable = false)
    private Long userId;

    @Column(name = "CLASS_ID")
    private Long classId;
}
