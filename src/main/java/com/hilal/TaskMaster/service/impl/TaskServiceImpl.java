package com.hilal.TaskMaster.service.impl;

import com.hilal.TaskMaster.entity.Status;
import com.hilal.TaskMaster.entity.Tasks;
import com.hilal.TaskMaster.entity.Teams;
import com.hilal.TaskMaster.entity.Users;
import com.hilal.TaskMaster.entity.dto.TaskRequestDto;
import com.hilal.TaskMaster.entity.dto.TaskResponseDto;
import com.hilal.TaskMaster.exception.customExceptions.BadRequestException;
import com.hilal.TaskMaster.exception.customExceptions.ResourceNotFoundException;
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

    private TaskResponseDto buildDtoFromTask(Tasks task) {
        return TaskResponseDto.builder()
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
                .build();
    }

    @Override
    public TaskResponseDto createTask(TaskRequestDto taskRequestDto, Users user) {
        if(taskRequestDto == null || user == null){
            throw new BadRequestException("Task and user cant be null");
        }
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
        return buildDtoFromTask(savedTask);
    }

    @Override
    public Tasks getTaskById(long taskId) {
        if(taskId <= 0){
            throw new BadRequestException("TaskId should be valid");
        }
        return taskRepository.findById(taskId)
                .orElseThrow(()-> new ResourceNotFoundException("Task not found with id: " + taskId));
    }
    @Override
    public TaskResponseDto getTaskDTOById(long taskId) {
        Tasks task = getTaskById(taskId);
        return buildDtoFromTask(task);
    }

    @Override
    public TaskResponseDto assignTask(long taskId, Users user) {
        if(user == null){
            throw new BadRequestException("User cannot be null");
        }
        Tasks task = getTaskById(taskId);
        task.setAssignedTo(user);
        task.setUpdatedAt(LocalDateTime.now());
        Tasks updatedTask = taskRepository.save(task);
        return buildDtoFromTask(updatedTask);
    }

    @Override
    public TaskResponseDto updateTask(long taskId, TaskRequestDto taskRequestDto) {
        if(taskRequestDto == null){
            throw new BadRequestException("TaskRequestDto cannot be null");
        }
        Tasks task = getTaskById(taskId);
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
        return buildDtoFromTask(updatedTask);
    }

    @Override
    public TaskResponseDto addTaskToTeam(long taskId, long teamId) {
        Teams team = teamService.getTeamById(teamId);
        Tasks task = getTaskById(taskId);
        task.setTeam(team);
        task.setUpdatedAt(LocalDateTime.now());
        Tasks updatedTask = taskRepository.save(task);
        return buildDtoFromTask(updatedTask);
    }

    @Override
    public List<TaskResponseDto> getTasksByStatus(Status status) {
        List<Tasks> tasks = taskRepository.findByStatus(status);
        return tasks.stream()
                .map(this::buildDtoFromTask)
                .toList();
    }

    @Override
    public String deleteTask(long taskId) {
        Tasks task = getTaskById(taskId);
        taskRepository.deleteById(taskId);
        return "Task with ID: " + taskId + " deleted";
    }

    @Override
    public List<TaskResponseDto> getAllTasksForUser(Users user) {
        List<Tasks> tasks = taskRepository.findByAssignedToUserIdOrCreatedByUserId(user.getUserId(), user.getUserId());
        return tasks.stream()
                .map(this::buildDtoFromTask)
                .toList();
    }

    @Override
    public TaskResponseDto markTaskAsComplete(long taskId) {
            Tasks task = getTaskById(taskId);
            task.setStatus(Status.COMPLETED);
            task.setUpdatedAt(LocalDateTime.now());
            task.setCompletedAt(LocalDateTime.now());
            Tasks updatedTask = taskRepository.save(task);

            return buildDtoFromTask(updatedTask);
    }

    @Override
    public TaskResponseDto unassignTask(long taskId) {
        Tasks task = getTaskById(taskId);
        task.setAssignedTo(null);
        task.setUpdatedAt(LocalDateTime.now());
        Tasks updatedTask = taskRepository.save(task);
        return buildDtoFromTask(updatedTask);
    }

    @Override
    public TaskResponseDto removeTaskFromTeam(long taskId) {
        Tasks task = getTaskById(taskId);
        task.setTeam(null);
        task.setUpdatedAt(LocalDateTime.now());
        Tasks updatedTask = taskRepository.save(task);
        return buildDtoFromTask(updatedTask);
    }

    @Override
    public List<TaskResponseDto> getAllTasksForTeam(long teamId) {
        List<Tasks> tasks = taskRepository.findByTeamTeamId(teamId);
        return tasks.stream()
                .map(this::buildDtoFromTask)
                .toList();
    }

    @Override
    public List<TaskResponseDto> getAllOverdueTasks() {
        List<Tasks> tasks = taskRepository.findByDueDateBeforeAndStatusNot(LocalDateTime.now(), Status.COMPLETED);
        return tasks.stream()
                .map(this::buildDtoFromTask)
                .toList();
    }

}
