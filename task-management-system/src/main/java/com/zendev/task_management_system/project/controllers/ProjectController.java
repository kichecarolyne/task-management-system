package com.zendev.task_management_system.project.controllers;

import com.zendev.task_management_system.dto.ReqRes;
import com.zendev.task_management_system.entity.Project;
import com.zendev.task_management_system.project.services.ProjectService;
import com.zendev.task_management_system.user.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private JwtUtils jwtUtils;

    // Create a new project
    @PostMapping
    public ResponseEntity<ReqRes> createProject(@RequestBody Project project) {
        ReqRes response = projectService.createProject(project);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Get a specific project by ID
    @GetMapping("/{projectId}")
    public ResponseEntity<ReqRes> getProjectById(@PathVariable Integer projectId) {
        ReqRes response = projectService.getProjectById(projectId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Get all projects
    @GetMapping
    public ResponseEntity<ReqRes> getAllProjects() {
        ReqRes response = projectService.getAllProjects();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Update a specific project
    @PutMapping("/{projectId}")
    public ResponseEntity<ReqRes> updateProject(@PathVariable Integer projectId, @RequestBody Project updatedProject) {
        ReqRes response = projectService.updateProject(projectId, updatedProject);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Delete a specific project
    @DeleteMapping("/{projectId}")
    public ResponseEntity<ReqRes> deleteProject(@PathVariable Integer projectId) {
        ReqRes response = projectService.deleteProject(projectId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
