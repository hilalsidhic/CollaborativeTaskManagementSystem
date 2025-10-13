package com.hilal.TaskMaster.entity.dto;

import com.hilal.TaskMaster.entity.Priority;
import com.hilal.TaskMaster.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskResponseDto {
    private long taskId;
    private String title;
    private String description;
    private Status status;
    private Priority priority;
    private LocalDateTime dueDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String assignedUser;
    private String teamName;
    private String createdBy;
}
