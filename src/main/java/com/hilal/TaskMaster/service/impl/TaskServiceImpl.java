package com.hilal.TaskMaster.service.impl;

import com.hilal.TaskMaster.entity.Status;
import com.hilal.TaskMaster.entity.Tasks;
import com.hilal.TaskMaster.entity.Teams;
import com.hilal.TaskMaster.entity.Users;
import com.hilal.TaskMaster.entity.dto.TaskRequestDto;
import com.hilal.TaskMaster.entity.dto.TaskResponseDto;
import com.hilal.TaskMaster.repository.TaskRepository;
import com.hilal.TaskMaster.service.TaskService;
import com.hilal.TaskMaster.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final TeamService teamService;

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
                .createdAt(savedTask.getCreatedAt())
                .updatedAt(savedTask.getUpdatedAt())
                .assignedUser((savedTask.getAssignedTo() != null) ? savedTask.getAssignedTo().getUserName() : null)
                .teamName((savedTask.getTeam() != null) ? savedTask.getTeam().getTeamName() : null)
                .createdBy(savedTask.getCreatedBy().getUserName())
                .build();
        return Optional.of(responseDto);
    }

    @Override
    public Optional<Tasks> getTaskById(long taskId) {
        Optional<Tasks> task = taskRepository.findById(taskId);
        return task;
    }
    @Override
    public Optional<TaskResponseDto> getTaskDTOById(long taskId) {
        Optional<Tasks> task = getTaskById(taskId);
        if (task.isEmpty()) {
            return Optional.empty();
        }
        Tasks t = task.get();
        TaskResponseDto responseDto = TaskResponseDto.builder()
                .taskId(t.getTaskId())
                .title(t.getTitle())
                .description(t.getDescription())
                .dueDate(t.getDueDate())
                .status(t.getStatus())
                .priority(t.getPriority())
                .createdAt(t.getCreatedAt())
                .updatedAt(t.getUpdatedAt())
                .assignedUser((t.getAssignedTo() != null) ? t.getAssignedTo().getUserName() : null)
                .teamName((t.getTeam() != null) ? t.getTeam().getTeamName() : null)
                .createdBy(t.getCreatedBy().getUserName())
                .build();
        return Optional.of(responseDto);
    }

    @Override
    public Optional<TaskResponseDto> assignTask(long taskId, Users user) {
        Optional<Tasks> taskOpt = getTaskById(taskId);
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
                .createdAt(updatedTask.getCreatedAt())
                .updatedAt(updatedTask.getUpdatedAt())
                .assignedUser((updatedTask.getAssignedTo() != null) ? updatedTask.getAssignedTo().getUserName() : null)
                .teamName((updatedTask.getTeam() != null) ? updatedTask.getTeam().getTeamName() : null)
                .createdBy(updatedTask.getCreatedBy().getUserName())
                .build();
        return Optional.of(responseDto);
    }

    @Override
    public Optional<TaskResponseDto> updateTask(long taskId, TaskRequestDto taskRequestDto) {
        Optional<Tasks> taskOpt = getTaskById(taskId);
        if (taskOpt.isEmpty()) {
            return Optional.empty();
        }
        Tasks task = taskOpt.get();
        if (taskRequestDto.getTitle() != null) {
            task.setTitle(taskRequestDto.getTitle());
        }
        if (taskRequestDto.getDescription() != null) {
            task.setDescription(taskRequestDto.getDescription());
        }
        if (taskRequestDto.getDueDate() != null) {
            task.setDueDate(taskRequestDto.getDueDate());
        }
        if (taskRequestDto.getStatus() != null) {
            task.setStatus(taskRequestDto.getStatus());
        }
        if (taskRequestDto.getPriority() != null) {
            task.setPriority(taskRequestDto.getPriority());
        }
        task.setUpdatedAt(LocalDateTime.now());
        Tasks updatedTask = taskRepository.save(task);
        TaskResponseDto responseDto = TaskResponseDto.builder()
                .taskId(updatedTask.getTaskId())
                .title(updatedTask.getTitle())
                .description(updatedTask.getDescription())
                .dueDate(updatedTask.getDueDate())
                .status(updatedTask.getStatus())
                .priority(updatedTask.getPriority())
                .createdAt(updatedTask.getCreatedAt())
                .updatedAt(updatedTask.getUpdatedAt())
                .assignedUser((updatedTask.getAssignedTo() != null) ? updatedTask.getAssignedTo().getUserName() : null)
                .teamName((updatedTask.getTeam() != null) ? updatedTask.getTeam().getTeamName() : null)
                .createdBy(updatedTask.getCreatedBy().getUserName())
                .build();
        return Optional.of(responseDto);
    }

    @Override
    public Optional<TaskResponseDto> addTaskToTeam(long taskId, long teamId) {
        Optional<Teams> teamOpt = teamService.getTeamById(teamId);
        Optional<Tasks> taskOpt = getTaskById(taskId);
        if (teamOpt.isEmpty() || taskOpt.isEmpty()) {
            return Optional.empty();
        }
        Tasks task = taskOpt.get();
        task.setTeam(teamOpt.get());
        task.setUpdatedAt(LocalDateTime.now());
        Tasks updatedTask = taskRepository.save(task);
        TaskResponseDto responseDto = TaskResponseDto.builder()
                .taskId(updatedTask.getTaskId())
                .title(updatedTask.getTitle())
                .description(updatedTask.getDescription())
                .dueDate(updatedTask.getDueDate())
                .status(updatedTask.getStatus())
                .priority(updatedTask.getPriority())
                .createdAt(updatedTask.getCreatedAt())
                .updatedAt(updatedTask.getUpdatedAt())
                .assignedUser((updatedTask.getAssignedTo() != null) ? updatedTask.getAssignedTo().getUserName() : null)
                .teamName((updatedTask.getTeam() != null) ? updatedTask.getTeam().getTeamName() : null)
                .createdBy(updatedTask.getCreatedBy().getUserName())
                .build();
        return Optional.of(responseDto);
    }

    @Override
    public List<TaskResponseDto> getTasksByStatus(Status status) {
        List<Tasks> tasks = taskRepository.findByStatus(status);
        if (tasks.isEmpty()) {
            return List.of();
        }
        List<TaskResponseDto> responseDtos = tasks.stream()
                .map(task -> TaskResponseDto.builder()
                .taskId(task.getTaskId())
                .title(task.getTitle())
                .description(task.getDescription())
                .dueDate(task.getDueDate())
                .status(task.getStatus())
                .priority(task.getPriority())
                .createdAt(task.getCreatedAt())
                .updatedAt(task.getUpdatedAt())
                .assignedUser((task.getAssignedTo() != null) ? task.getAssignedTo().getUserName() : null)
                .teamName((task.getTeam() != null) ? task.getTeam().getTeamName() : null)
                .createdBy(task.getCreatedBy().getUserName())
                .build()).toList();
        return responseDtos;
    }

    @Override
    public String deleteTask(long taskId) {
        Optional<Tasks> taskOpt = getTaskById(taskId);
        if (taskOpt.isEmpty()) {
            return "Task with ID: " + taskId + " not found";
        }
        taskRepository.deleteById(taskId);
        return "Task with ID: " + taskId + " deleted";
    }

    @Override
    public List<TaskResponseDto> getAllTasksForUser(Users user) {
        List<Tasks> tasks = taskRepository.findByAssignedToUserIdOrCreatedByUserId(user.getUserId(),user.getUserId());
        if (tasks.isEmpty()) {
            return List.of();
        }
        List<TaskResponseDto> responseDtos = tasks.stream()
                .map(task -> TaskResponseDto.builder()
                        .taskId(task.getTaskId())
                        .title(task.getTitle())
                        .description(task.getDescription())
                        .dueDate(task.getDueDate())
                        .status(task.getStatus())
                        .priority(task.getPriority())
                        .createdAt(task.getCreatedAt())
                        .updatedAt(task.getUpdatedAt())
                        .assignedUser((task.getAssignedTo() != null) ? task.getAssignedTo().getUserName() : null)
                        .teamName((task.getTeam() != null) ? task.getTeam().getTeamName() : null)
                        .createdBy(task.getCreatedBy().getUserName())
                        .build()).toList();
        return responseDtos;
    }

    @Override
    public Optional<TaskResponseDto> markTaskAsComplete(long taskId) {
        Optional<Tasks> taskOpt = getTaskById(taskId);
        if (taskOpt.isEmpty()) {
            return Optional.empty();
        }
        Tasks task = taskOpt.get();
        task.setStatus(Status.COMPLETED);
        task.setUpdatedAt(LocalDateTime.now());
        task.setCompletedAt(LocalDateTime.now());
        Tasks updatedTask = taskRepository.save(task);

        TaskResponseDto responseDto = TaskResponseDto.builder()
                .taskId(updatedTask.getTaskId())
                .title(updatedTask.getTitle())
                .description(updatedTask.getDescription())
                .dueDate(updatedTask.getDueDate())
                .status(updatedTask.getStatus())
                .priority(updatedTask.getPriority())
                .createdAt(updatedTask.getCreatedAt())
                .updatedAt(updatedTask.getUpdatedAt())
                .assignedUser((updatedTask.getAssignedTo() != null) ? updatedTask.getAssignedTo().getUserName() : null)
                .teamName((updatedTask.getTeam() != null) ? updatedTask.getTeam().getTeamName() : null)
                .createdBy(updatedTask.getCreatedBy().getUserName())
                .build();
        return Optional.of(responseDto);
    }

    @Override
    public Optional<TaskResponseDto> unassignTask(long taskId) {
        Optional<Tasks> taskOpt = getTaskById(taskId);
        if (taskOpt.isEmpty()) {
            return Optional.empty();
        }
        Tasks task = taskOpt.get();
        task.setAssignedTo(null);
        task.setUpdatedAt(LocalDateTime.now());
        Tasks updatedTask = taskRepository.save(task);
        TaskResponseDto responseDto = TaskResponseDto.builder()
                .taskId(updatedTask.getTaskId())
                .title(updatedTask.getTitle())
                .description(updatedTask.getDescription())
                .dueDate(updatedTask.getDueDate())
                .status(updatedTask.getStatus())
                .priority(updatedTask.getPriority())
                .createdAt(updatedTask.getCreatedAt())
                .updatedAt(updatedTask.getUpdatedAt())
                .assignedUser((updatedTask.getAssignedTo() != null) ? updatedTask.getAssignedTo().getUserName() : null)
                .teamName((updatedTask.getTeam() != null) ? updatedTask.getTeam().getTeamName() : null)
                .createdBy(updatedTask.getCreatedBy().getUserName())
                .build();
        return Optional.of(responseDto);
    }

    @Override
    public Optional<TaskResponseDto> removeTaskFromTeam(long taskId) {
        Optional<Tasks> taskOpt = getTaskById(taskId);
        if (taskOpt.isEmpty()) {
            return Optional.empty();
        }
        Tasks task = taskOpt.get();
        task.setTeam(null);
        task.setUpdatedAt(LocalDateTime.now());
        Tasks updatedTask = taskRepository.save(task);
        TaskResponseDto responseDto = TaskResponseDto.builder()
                .taskId(updatedTask.getTaskId())
                .title(updatedTask.getTitle())
                .description(updatedTask.getDescription())
                .dueDate(updatedTask.getDueDate())
                .status(updatedTask.getStatus())
                .priority(updatedTask.getPriority())
                .createdAt(updatedTask.getCreatedAt())
                .updatedAt(updatedTask.getUpdatedAt())
                .assignedUser((updatedTask.getAssignedTo() != null) ? updatedTask.getAssignedTo().getUserName() : null)
                .teamName((updatedTask.getTeam() != null) ? updatedTask.getTeam().getTeamName() : null)
                .createdBy(updatedTask.getCreatedBy().getUserName())
                .build();
        return Optional.of(responseDto);
    }

    @Override
    public List<TaskResponseDto> getAllTasksForTeam(long teamId) {
        List<Tasks> tasks = taskRepository.findByTeamTeamId(teamId);
        if (tasks.isEmpty()) {
            return List.of();
        }
        List<TaskResponseDto> responseDtos = tasks.stream()
                .map(task -> TaskResponseDto.builder()
                        .taskId(task.getTaskId())
                        .title(task.getTitle())
                        .description(task.getDescription())
                        .dueDate(task.getDueDate())
                        .status(task.getStatus())
                        .priority(task.getPriority())
                        .createdAt(task.getCreatedAt())
                        .updatedAt(task.getUpdatedAt())
                        .assignedUser((task.getAssignedTo() != null) ? task.getAssignedTo().getUserName() : null)
                        .teamName((task.getTeam() != null) ? task.getTeam().getTeamName() : null)
                        .createdBy(task.getCreatedBy().getUserName())
                        .build()).toList();
        return responseDtos;
    }

    @Override
    public List<TaskResponseDto> getAllOverdueTasks() {
        List<Tasks> tasks = taskRepository.findByDueDateBeforeAndStatusNot(LocalDateTime.now(), Status.COMPLETED);
        if (tasks.isEmpty()) {
            return List.of();
        }
        List<TaskResponseDto> responseDtos = tasks.stream()
                .map(task -> TaskResponseDto.builder()
                        .taskId(task.getTaskId())
                        .title(task.getTitle())
                        .description(task.getDescription())
                        .dueDate(task.getDueDate())
                        .status(task.getStatus())
                        .priority(task.getPriority())
                        .createdAt(task.getCreatedAt())
                        .updatedAt(task.getUpdatedAt())
                        .assignedUser((task.getAssignedTo() != null) ? task.getAssignedTo().getUserName() : null)
                        .teamName((task.getTeam() != null) ? task.getTeam().getTeamName() : null)
                        .createdBy(task.getCreatedBy().getUserName())
                        .build()).toList();
        return responseDtos;
    }
}
