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
public class TaskRequestDto {
    private String title;
    private String description;
    private Priority priority;
    private Status status;
    private LocalDateTime dueDate;
}
