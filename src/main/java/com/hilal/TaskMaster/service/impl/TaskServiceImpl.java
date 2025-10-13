package com.hilal.TaskMaster.service.impl;

import com.hilal.TaskMaster.entity.Tasks;
import com.hilal.TaskMaster.entity.Users;
import com.hilal.TaskMaster.entity.dto.TaskRequestDto;
import com.hilal.TaskMaster.entity.dto.TaskResponseDto;
import com.hilal.TaskMaster.repository.TaskRepository;
import com.hilal.TaskMaster.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;

    @Override
    public Optional<TaskResponseDto> createTask(TaskRequestDto taskRequestDto, Users user) {
        Tasks task = Tasks.builder()
                .title(taskRequestDto.getTitle())
                .description(taskRequestDto.getDescription())
                .createdBy(user)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .dueDate(taskRequestDto.getDueDate())
                .status(taskRequestDto.getStatus())
                .priority(taskRequestDto.getPriority())
                .build();
        Tasks savedTask = taskRepository.save(task);
        TaskResponseDto responseDto = TaskResponseDto.builder()
                .taskId(savedTask.getTaskId())
                .title(savedTask.getTitle())
                .description(savedTask.getDescription())
                .dueDate(savedTask.getDueDate())
                .status(savedTask.getStatus())
                .priority(savedTask.getPriority())
                .build();
        return Optional.of(responseDto);
    }

    @Override
    public Optional<Tasks> getTaskById(long taskId) {
        Optional<Tasks> task = taskRepository.findById(taskId);
        return task;
    }

    @Override
    public Optional<TaskResponseDto> assignTask(long taskId, Users user) {
        Optional<Tasks> taskOpt = taskRepository.findById(taskId);
        if (taskOpt.isEmpty()) {
            return Optional.empty();
        }
        Tasks task = taskOpt.get();
        task.setAssignedTo(user);
        task.setUpdatedAt(LocalDateTime.now());
        Tasks updatedTask = taskRepository.save(task);
        TaskResponseDto responseDto = TaskResponseDto.builder()
                .taskId(updatedTask.getTaskId())
                .title(updatedTask.getTitle())
                .description(updatedTask.getDescription())
                .dueDate(updatedTask.getDueDate())
                .status(updatedTask.getStatus())
                .priority(updatedTask.getPriority())
                .build();
        return Optional.of(responseDto);
    }

    @Override
    public Optional<TaskResponseDto> updateTask(long taskId, TaskRequestDto taskRequestDto) {
        return Optional.empty();
    }

    @Override
    public List<TaskResponseDto> getTasksByStatus(String status) {
        return List.of();
    }

    @Override
    public Optional<TaskResponseDto> markTaskAsComplete(long taskId) {
        return Optional.empty();
    }
}
