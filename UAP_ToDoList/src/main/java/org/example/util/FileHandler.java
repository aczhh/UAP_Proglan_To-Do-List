package org.example.util;

import org.example.model.Task;

import java.io.*;
import java.util.ArrayList;

public class FileHandler {
    private static final String TASKS_FILE = "data/tasks.csv";

    public static void saveTasks(ArrayList<Task> tasks) {
        try {
            new File("data").mkdirs();
            BufferedWriter writer = new BufferedWriter(new FileWriter(TASKS_FILE));
            for (Task task : tasks) {
                writer.write(task.toCSV());
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            System.err.println("Error saving tasks: " + e.getMessage());
        }
    }

    public static ArrayList<Task> loadTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        try {
            File file = new File(TASKS_FILE);
            if (!file.exists()) return tasks;

            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    tasks.add(Task.fromCSV(line));
                } catch (Exception e) {
                    System.err.println("Error parsing task: " + e.getMessage());
                }
            }
            reader.close();
        } catch (IOException e) {
            System.err.println("Error loading tasks: " + e.getMessage());
        }
        return tasks;
    }
}
