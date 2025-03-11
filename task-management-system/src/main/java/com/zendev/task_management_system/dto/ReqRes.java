package com.zendev.task_management_system.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.zendev.task_management_system.entity.User;
import com.zendev.task_management_system.entity.Task;
import com.zendev.task_management_system.entity.Project;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReqRes {

    private int statusCode;  // To capture HTTP status code (200, 404, etc.)
    private String error;    // To capture any error message if occurred
    private String message;  // To capture the response message
    private String token;    // To capture JWT token (if applicable)
    private String refreshToken;  // To capture the refresh token
    private String expirationTime;  // To capture token expiration time

    private String name;
    private String role;
    private String email;
    private String password;

    private User user;  // A single User object
    private Task task;  // A single Task object
    private List<Task> taskList;  // List of Tasks
    private Project project;  // A single Project object
    private List<Project> projectList;  // List of Projects

    // Additional constructor overload to handle different cases
    public ReqRes(int statusCode, String error, String message) {
        this.statusCode = statusCode;
        this.error = error;
        this.message = message;
    }

    public ReqRes(int statusCode, String message, User user) {
        this.statusCode = statusCode;
        this.message = message;
        this.user = user;
    }

    public ReqRes(int statusCode, String message, Task task) {
        this.statusCode = statusCode;
        this.message = message;
        this.task = task;
    }

    public ReqRes(int statusCode, String message, Project project) {
        this.statusCode = statusCode;
        this.message = message;
        this.project = project;
    }

    public ReqRes(int statusCode, String message, List<Task> taskList) {
        this.statusCode = statusCode;
        this.message = message;
        this.taskList = taskList;
    }

    public ReqRes(int statusCode, String message, List<Project> projectList) {
        this.statusCode = statusCode;
        this.message = message;
        this.projectList = projectList;
    }

}
