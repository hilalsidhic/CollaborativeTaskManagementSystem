package com.hilal.TaskMaster.controller;

import com.hilal.TaskMaster.entity.Status;
import com.hilal.TaskMaster.entity.Tasks;
import com.hilal.TaskMaster.entity.Users;
import com.hilal.TaskMaster.entity.dto.TaskRequestDto;
import com.hilal.TaskMaster.entity.dto.TaskResponseDto;
import com.hilal.TaskMaster.service.TaskService;
import com.hilal.TaskMaster.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;
    private final UserService userService;

    @PostMapping("/")
    public ResponseEntity<TaskResponseDto> createTask(Authentication authentication, @RequestBody TaskRequestDto taskRequestDto) {
        String userEmail = authentication.getName();
        Optional<Users> user = userService.getUserByEmail(userEmail);
        if(user.isEmpty()) return ResponseEntity.notFound().build();
        Optional<TaskResponseDto> task = taskService.createTask(taskRequestDto,user.get());
        if (task.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(task.get());
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<TaskResponseDto> getTask(@PathVariable long taskId) {
        Optional<TaskResponseDto> task = taskService.getTaskDTOById(taskId);
        if (task.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(task.get());
    }

    @GetMapping("/all")
    public ResponseEntity<List<TaskResponseDto>> getAllTaskForUser(Authentication authentication) {
        String userEmail = authentication.getName();
        Optional<Users> user = userService.getUserByEmail(userEmail);
        if(user.isEmpty()) return ResponseEntity.notFound().build();
        List<TaskResponseDto> tasks = taskService.getAllTasksForUser(user.get());
        if (tasks.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(tasks);
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<TaskResponseDto> updateTask(@PathVariable long taskId, @RequestBody TaskRequestDto taskDetails) {
        Optional<TaskResponseDto> updatedTask = taskService.updateTask(taskId, taskDetails);
        if (updatedTask.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(updatedTask.get());
    }

    @DeleteMapping("/{taskId}")
    public String deleteTask(@PathVariable long taskId) {
        return "Task with ID: " + taskId + " deleted";
    }

    @PutMapping("/{taskId}/assign/{userId}")
    public ResponseEntity<TaskResponseDto> assignTask(@PathVariable long taskId, @PathVariable long userId) {
        Optional<Users> user = userService.getUserById(userId);
        if(user.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        Optional<TaskResponseDto> updatedTask = taskService.assignTask(taskId, user.get());
        if (updatedTask.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(updatedTask.get());
    }

    @PutMapping("/{taskId}/markascomplete")
    public ResponseEntity<TaskResponseDto> markTaskAsComplete(@PathVariable long taskId) {
        Optional<TaskResponseDto> updatedTask = taskService.markTaskAsComplete(taskId);
        if (updatedTask.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(updatedTask.get());
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<TaskResponseDto>> getTasksByStatus(@PathVariable Status status) {
        List<TaskResponseDto> tasks = taskService.getTasksByStatus(status);
        if (tasks.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(tasks);
    }

    @PostMapping("/search/")
    public String searchTasks(@RequestBody String criteria) {
        return "Search results for criteria: " + criteria;
    }

    @PostMapping("/{taskId}/comments/")
    public String addComment(@PathVariable long id, @RequestBody String comment) {
        return "Comment added to task with ID: " + id;
    }

    @PostMapping("/{id}/attachments/")
    public String addAttachment(@PathVariable String id, @RequestBody String attachment) {
        return "Attachment added to task with ID: " + id;
    }

    @PutMapping("/{taskId}/addToTeam/{teamId}")
    public ResponseEntity<TaskResponseDto> addTaskToTeam(@PathVariable long taskId, @PathVariable long teamId) {
        Optional<TaskResponseDto> updatedTask = taskService.addTaskToTeam(taskId, teamId);
        if (updatedTask.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(updatedTask.get());
    }
}
