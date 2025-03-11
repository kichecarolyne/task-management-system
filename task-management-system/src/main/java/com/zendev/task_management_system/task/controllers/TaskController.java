package com.zendev.task_management_system.task.controllers;

import com.zendev.task_management_system.dto.ReqRes;
import com.zendev.task_management_system.entity.Task;
import com.zendev.task_management_system.task.services.TaskService;
import com.zendev.task_management_system.user.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private JwtUtils jwtUtils;

    // Task-related endpoints

    // Create a new task
    @PostMapping
    public ResponseEntity<ReqRes> createTask(@RequestBody Task task) {
        ReqRes response = taskService.createTask(task);
        return ResponseEntity.ok(response);
    }

    // Get task by ID
    @GetMapping("/{taskId}")
    public ResponseEntity<ReqRes> getTaskById(@PathVariable Integer taskId) {
        ReqRes response = taskService.getTaskById(taskId);
        return ResponseEntity.ok(response);
    }

    // Get all tasks
    @GetMapping
    public ResponseEntity<ReqRes> getAllTasks() {
        ReqRes response = taskService.getAllTasks();
        return ResponseEntity.ok(response);
    }

    // Update task by ID
    @PutMapping("/{taskId}")
    public ResponseEntity<ReqRes> updateTask(@PathVariable Integer taskId, @RequestBody Task updatedTask) {
        ReqRes response = taskService.updateTask(taskId, updatedTask);
        return ResponseEntity.ok(response);
    }

    // Delete task by ID
    @DeleteMapping("/{taskId}")
    public ResponseEntity<ReqRes> deleteTask(@PathVariable Integer taskId) {
        ReqRes response = taskService.deleteTask(taskId);
        return ResponseEntity.ok(response);
    }
}
