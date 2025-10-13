package com.hilal.TaskMaster.controller;

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

    @GetMapping("/{id}")
    public ResponseEntity<Tasks> getTask(@PathVariable long id) {
        Optional<Tasks> task = taskService.getTaskById(id);
        if (task.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(task.get());
    }

    @GetMapping("/")
    public String getAllTasks() {
        return "List of all tasks";
    }

    @PutMapping("/{id}")
    public String updateTask(@PathVariable String id) {
        return "Task with ID: " + id + " updated";
    }

    @DeleteMapping("/{id}")
    public String deleteTask(@PathVariable String id) {
        return "Task with ID: " + id + " deleted";
    }

    @PutMapping("/{taskId}/assign/{userId}")
    public ResponseEntity<TaskResponseDto> assignTask(@PathVariable long id, @PathVariable long userId) {
        Optional<Users> user = userService.getUserById(userId);
        if(user.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        Optional<TaskResponseDto> updatedTask = taskService.assignTask(id, user.get());
        if (updatedTask.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(updatedTask.get());
    }

    @PutMapping("/{id}/markascomplete")
    public String markTaskAsComplete(@PathVariable String id) {
        return "Task with ID: " + id + " marked as complete";
    }

    @GetMapping("/status/{status}")
    public String getTasksByStatus(@PathVariable String status) {
        return "List of tasks with status: " + status;
    }

    @PostMapping("/search/")
    public String searchTasks(@RequestBody String criteria) {
        return "Search results for criteria: " + criteria;
    }

    @PostMapping("/{id}/comments/")
    public String addComment(@PathVariable String id, @RequestBody String comment) {
        return "Comment added to task with ID: " + id;
    }

    @PostMapping("/{id}/attachments/")
    public String addAttachment(@PathVariable String id, @RequestBody String attachment) {
        return "Attachment added to task with ID: " + id;
    }
}
