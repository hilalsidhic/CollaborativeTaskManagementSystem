package com.hilal.TaskMaster.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @PostMapping("/")
    public String createTask() {
        return "Task created";
    }

    @GetMapping("/{id}")
    public String getTask(@PathVariable String id) {
        return "Task details for ID: " + id;
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

    @PutMapping("/{id}/assign/{userId}")
    public String assignTask(@PathVariable String id, @PathVariable String userId) {
        return "Task with ID: " + id + " assigned to user with ID: " + userId;
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
