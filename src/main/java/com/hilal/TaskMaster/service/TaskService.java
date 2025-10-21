package com.hilal.TaskMaster.service;

import com.hilal.TaskMaster.entity.Status;
import com.hilal.TaskMaster.entity.Tasks;
import com.hilal.TaskMaster.entity.Users;
import com.hilal.TaskMaster.entity.dto.TaskRequestDto;
import com.hilal.TaskMaster.entity.dto.TaskResponseDto;

import java.util.List;
import java.util.Optional;

public interface TaskService {
    TaskResponseDto createTask(TaskRequestDto taskRequestDto, Users user);
    Tasks getTaskById(long taskId);
    TaskResponseDto getTaskDTOById(long taskId);
    TaskResponseDto updateTask(long taskId, TaskRequestDto taskRequestDto);
    String deleteTask(long taskId);

    TaskResponseDto assignTask(long taskId, Users user);
    TaskResponseDto unassignTask(long taskId);
    TaskResponseDto markTaskAsComplete(long taskId);

    List<TaskResponseDto> getTasksByStatus(Status status);
    List<TaskResponseDto> getAllTasksForUser(Users user);

    List<TaskResponseDto> getAllTasksForTeam(long teamId);
    List<TaskResponseDto> getAllOverdueTasks();
    TaskResponseDto addTaskToTeam(long taskId, long teamId);
    TaskResponseDto removeTaskFromTeam(long taskId);

}
