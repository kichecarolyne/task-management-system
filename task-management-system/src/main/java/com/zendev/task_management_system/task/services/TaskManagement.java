package com.zendev.task_management_system.task.services;

import com.zendev.task_management_system.dto.ReqRes;
import com.zendev.task_management_system.entity.Task;
import com.zendev.task_management_system.task.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskManagement {

    @Autowired
    private TaskRepository taskRepo;

    // Create a new task
    public ReqRes createTask(Task task) {
        ReqRes response = new ReqRes();
        try {
            Task savedTask = taskRepo.save(task);
            response.setStatusCode(200);
            response.setMessage("Task created successfully");
            response.setTask(savedTask);  // Assuming you have set a 'task' field in ReqRes class
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error creating task: " + e.getMessage());
        }
        return response;
    }

    // Get task by ID
    public ReqRes getTaskById(Integer taskId) {
        ReqRes response = new ReqRes();
        try {
            Optional<Task> task = taskRepo.findById(taskId);
            if (task.isPresent()) {
                response.setStatusCode(200);
                response.setMessage("Task found");
                response.setTask(task.get());  // Assuming you have set a 'task' field in ReqRes class
            } else {
                response.setStatusCode(404);
                response.setMessage("Task not found");
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error retrieving task: " + e.getMessage());
        }
        return response;
    }

    // Get all tasks
    public ReqRes getAllTasks() {
        ReqRes response = new ReqRes();
        try {
            List<Task> tasks = taskRepo.findAll();
            if (!tasks.isEmpty()) {
                response.setStatusCode(200);
                response.setMessage("Tasks retrieved successfully");
                response.setTaskList(tasks);  // Assuming you have set a 'taskList' field in ReqRes class
            } else {
                response.setStatusCode(404);
                response.setMessage("No tasks found");
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error retrieving tasks: " + e.getMessage());
        }
        return response;
    }

    // Update task by ID
    public ReqRes updateTask(Integer taskId, Task updatedTask) {
        ReqRes response = new ReqRes();
        try {
            Optional<Task> taskOptional = taskRepo.findById(taskId);
            if (taskOptional.isPresent()) {
                Task existingTask = taskOptional.get();
                existingTask.setTitle(updatedTask.getTitle());
                existingTask.setDescription(updatedTask.getDescription());
                existingTask.setDueDate(updatedTask.getDueDate());

                Task savedTask = taskRepo.save(existingTask);
                response.setStatusCode(200);
                response.setMessage("Task updated successfully");
                response.setTask(savedTask);  // Assuming you have set a 'task' field in ReqRes class
            } else {
                response.setStatusCode(404);
                response.setMessage("Task not found");
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error updating task: " + e.getMessage());
        }
        return response;
    }

    // Delete task by ID
    public ReqRes deleteTask(Integer taskId) {
        ReqRes response = new ReqRes();
        try {
            Optional<Task> task = taskRepo.findById(taskId);
            if (task.isPresent()) {
                taskRepo.delete(task.get());
                response.setStatusCode(200);
                response.setMessage("Task deleted successfully");
            } else {
                response.setStatusCode(404);
                response.setMessage("Task not found");
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error deleting task: " + e.getMessage());
        }
        return response;
    }
}

