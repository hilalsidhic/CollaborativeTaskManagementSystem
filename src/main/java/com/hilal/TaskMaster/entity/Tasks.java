package com.hilal.TaskMaster.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tasks {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long taskId;

    private String title;
    private String description;

    @Enumerated(EnumType.STRING)
    private Priority priority;
    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime completedAt;

    private LocalDateTime dueDate;

    @ManyToOne
    @JoinColumn(name = "teamId", referencedColumnName = "teamId")
    private Teams team;

    @ManyToOne
    @JoinColumn(name = "assignedTo", referencedColumnName = "userId")
    private Users assignedTo;

    @ManyToOne
    @JoinColumn(name = "createdBy", referencedColumnName = "userId")
    private Users createdBy;


    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Comments> comments;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Attachments> attachments;
}
