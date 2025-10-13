package com.hilal.TaskMaster.service;

import com.hilal.TaskMaster.entity.Status;
import com.hilal.TaskMaster.entity.Tasks;
import com.hilal.TaskMaster.entity.Users;
import com.hilal.TaskMaster.entity.dto.TaskRequestDto;
import com.hilal.TaskMaster.entity.dto.TaskResponseDto;

import java.util.List;
import java.util.Optional;

public interface TaskService {
    Optional<TaskResponseDto> createTask(TaskRequestDto taskRequestDto, Users user);
    Optional<Tasks> getTaskById(long taskId);
    Optional<TaskResponseDto> assignTask(long taskId, Users user);
    Optional<TaskResponseDto> markTaskAsComplete(long taskId);
    List<TaskResponseDto> getTasksByStatus(Status status);
    Optional<TaskResponseDto> updateTask(long taskId, TaskRequestDto taskRequestDto);
    List<TaskResponseDto> getAllTasksForUser(Users user);
    Optional<TaskResponseDto> addTaskToTeam(long taskId, long teamId);
}
