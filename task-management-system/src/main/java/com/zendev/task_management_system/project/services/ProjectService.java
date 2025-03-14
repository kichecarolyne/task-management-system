package com.zendev.task_management_system.project.services;

import com.zendev.task_management_system.dto.ReqRes;
import com.zendev.task_management_system.entity.Project;
import com.zendev.task_management_system.project.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepo;

    // Create a new project
    public ReqRes createProject(Project project) {
        ReqRes response = new ReqRes();
        try {
            Project savedProject = projectRepo.save(project);
            response.setStatusCode(200);
            response.setMessage("Project created successfully");
            response.setProject(savedProject);  // Assuming you have set a 'project' field in ReqRes class
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error creating project: " + e.getMessage());
        }
        return response;
    }

    // Get project by ID
    public ReqRes getProjectById(Integer projectId) {
        ReqRes response = new ReqRes();
        try {
            Optional<Project> project = projectRepo.findById(projectId);
            if (project.isPresent()) {
                response.setStatusCode(200);
                response.setMessage("Project found");
                response.setProject(project.get());  // Assuming you have set a 'project' field in ReqRes class
            } else {
                response.setStatusCode(404);
                response.setMessage("Project not found");
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error retrieving project: " + e.getMessage());
        }
        return response;
    }

    // Get all projects
    public ReqRes getAllProjects() {
        ReqRes response = new ReqRes();
        try {
            List<Project> projects = projectRepo.findAll();
            if (!projects.isEmpty()) {
                response.setStatusCode(200);
                response.setMessage("Projects retrieved successfully");
                response.setProjectList(projects);  // Assuming you have set a 'projectList' field in ReqRes class
            } else {
                response.setStatusCode(404);
                response.setMessage("No projects found");
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error retrieving projects: " + e.getMessage());
        }
        return response;
    }

    // Update project by ID
    public ReqRes updateProject(Integer projectId, Project updatedProject) {
        ReqRes response = new ReqRes();
        try {
            Optional<Project> projectOptional = projectRepo.findById(projectId);
            if (projectOptional.isPresent()) {
                Project existingProject = projectOptional.get();
                existingProject.setTitle(updatedProject.getTitle());
                existingProject.setDescription(updatedProject.getDescription());
                existingProject.setDueDate(updatedProject.getDueDate());

                Project savedProject = projectRepo.save(existingProject);
                response.setStatusCode(200);
                response.setMessage("Project updated successfully");
                response.setProject(savedProject);  // Assuming you have set a 'project' field in ReqRes class
            } else {
                response.setStatusCode(404);
                response.setMessage("Project not found");
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error updating project: " + e.getMessage());
        }
        return response;
    }

    // Delete project by ID
    public ReqRes deleteProject(Integer projectId) {
        ReqRes response = new ReqRes();
        try {
            Optional<Project> project = projectRepo.findById(projectId);
            if (project.isPresent()) {
                projectRepo.delete(project.get());
                response.setStatusCode(200);
                response.setMessage("Project deleted successfully");
            } else {
                response.setStatusCode(404);
                response.setMessage("Project not found");
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error deleting project: " + e.getMessage());
        }
        return response;
    }
}
