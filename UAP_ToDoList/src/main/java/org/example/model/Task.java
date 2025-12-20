package org.example.model;

import java.time.LocalDate;

public class Task {
    private int id;
    private String title;
    private String description;
    private LocalDate deadline;
    private String priority;
    private boolean completed;

    public Task(int id, String title, String description, LocalDate deadline, String priority, boolean completed) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.priority = priority;
        this.completed = completed;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDate getDeadline() { return deadline; }
    public void setDeadline(LocalDate deadline) { this.deadline = deadline; }

    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }

    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }

    public String toCSV() {
        return id + "," + title + "," + description + "," + deadline + "," + priority + "," + completed;
    }

    public static Task fromCSV(String csv) {
        String[] parts = csv.split(",");
        return new Task(
                Integer.parseInt(parts[0]),
                parts[1],
                parts[2],
                LocalDate.parse(parts[3]),
                parts[4],
                Boolean.parseBoolean(parts[5])
        );
    }

}
