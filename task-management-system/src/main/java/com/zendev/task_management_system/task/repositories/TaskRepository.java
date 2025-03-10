package com.zendev.task_management_system.task.repositories;


import com.zendev.task_management_system.entity.Task;
import com.zendev.task_management_system.entity.Project;
import com.zendev.task_management_system.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Integer> {

    // Find tasks by status (e.g., "completed", "pending")
    List<Task> findByStatus(String status);

    // Find tasks assigned to a specific user by their userId
    List<Task> findByAssignedToId(Integer userId);

    // Find task by its title
    Optional<Task> findByTitle(String title);

    // Find tasks linked to a specific project (Project)
    List<Task> findByProject(Project project);

    // Find tasks linked to a specific projectId
    List<Task> findByProjectId(Integer projectId);
}


