package com.zendev.task_management_system.project.models;

// Enum to represent the status of a Project
public enum ProjectStatus {
    NOT_STARTED("Not Started"),
    IN_PROGRESS("In Progress"),
    COMPLETED("Completed");

    private final String status;

    // Constructor to assign the string value to the status
    ProjectStatus(String status) {
        this.status = status;
    }

    // Getter to retrieve the status as a string
    public String getStatus() {
        return status;
    }

    // Optionally, you can add a method to convert a string to ProjectStatus
    public static ProjectStatus fromString(String status) {
        for (ProjectStatus projectStatus : ProjectStatus.values()) {
            if (projectStatus.getStatus().equalsIgnoreCase(status)) {
                return projectStatus;
            }
        }
        throw new IllegalArgumentException("Unexpected status: " + status);
    }
}
