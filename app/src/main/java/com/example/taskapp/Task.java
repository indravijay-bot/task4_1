package com.example.taskapp;

public class Task {

    private int id;  // Unique identifier for each task
    private String title;  // Title of the task
    private String description;  // Description of the task
    private String dueDate;  // Due date of the task

    // Constructor to initialize a new task with an id, title, description, and due date
    public Task(int id, String title, String description, String dueDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
    }

    // Constructor to initialize a new task without an id (for creating new tasks)
    public Task(String title, String description, String dueDate) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
    }

    // Getter methods to access the task's details
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDueDate() {
        return dueDate;
    }

    // Setter method to update the task's id (used when updating tasks)
    public void setId(int id) {
        this.id = id;
    }
}
