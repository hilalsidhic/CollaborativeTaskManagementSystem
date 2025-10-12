package com.hilal.TaskMaster.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Attachments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long attachmentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "taskId", referencedColumnName = "taskId")
    private Tasks task;

    private String fileName;
    private String fileType;
    private long fileSize;
    private String fileUrl;

    private LocalDateTime uploadedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uploadedBy", referencedColumnName = "userId")
    private Users uploadedBy;
}
