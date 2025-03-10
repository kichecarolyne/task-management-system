package com.zendev.task_management_system.task.services;

import com.zendev.task_management_system.entity.Task;
import com.zendev.task_management_system.task.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    public List<Task> getTasksByUser(Long userId) {
        return taskRepository.findAll();  // Implement specific filtering by user, project, etc.
    }
}
