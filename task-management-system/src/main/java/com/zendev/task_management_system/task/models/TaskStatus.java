package com.zendev.task_management_system.task.models;

// Enum to represent the status of a Task
public enum TaskStatus {
    TO_DO("To Do"),
    IN_PROGRESS("In Progress"),
    DONE("Done");

    private final String status;

    // Constructor to assign the string value to the status
    TaskStatus(String status) {
        this.status = status;
    }

    // Getter to retrieve the status as a string
    public String getStatus() {
        return status;
    }

    // Optionally, you can add a method to convert a string to TaskStatus
    public static TaskStatus fromString(String status) {
        for (TaskStatus taskStatus : TaskStatus.values()) {
            if (taskStatus.getStatus().equalsIgnoreCase(status)) {
                return taskStatus;
            }
        }
        throw new IllegalArgumentException("Unexpected status: " + status);
    }
}
