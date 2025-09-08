package dupe.storage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

import dupe.tasks.Task;
import dupe.tasks.Deadline;
import dupe.tasks.TaskList;
import dupe.tasks.ToDo;
import dupe.tasks.Event;
import dupe.parser.Parser;
import dupe.ui.Ui;

/**
 * Handles loading tasks from and saving tasks to a file.
 */
public class Storage {
    private final String filePath;

    /**
     * Creates a new {@code Storage} instance with the given file path.
     *
     * @param filePath Path to the save file.
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads tasks from the save file into memory.
     * If the file does not exist, a new file is created and an empty task list is returned.
     *
     * @return A list of tasks loaded from the file.
     * @throws IOException If an error occurs while creating or reading the file.
     */
    public TaskList load() throws IOException {
        File file = new File(filePath);

        if (!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
            return new TaskList(); // return empty task list
        }

        TaskList taskList = new TaskList();

        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                Task task = parseTask(line);
                if (task != null) {
                    taskList.addTask(task);
                }
            }
        }

        return taskList;
    }

    /**
     * Parses a line from the save file into a Task object.
     */
    private Task parseTask(String line) {
        String[] parts = line.split(" \\| ");
        String type = parts[0];
        String doneFlag = parts[1];
        String description = parts[2];

        Task task = null;

        switch (type) {
            case "T":
                task = new ToDo(description);
                break;

            case "D":
                LocalDateTime deadline = Parser.parseDateTimeFile(parts[3]);
                task = new Deadline(description, deadline);
                break;

            case "E":
                LocalDateTime from = Parser.parseDateTimeFile(parts[3]);
                LocalDateTime to = Parser.parseDateTimeFile(parts[4]);
                task = new Event(description, from, to);
                break;

            default:
                System.err.println("Unknown task type in save file: " + type);
        }

        if (task != null) {
            markDoneIfNeeded(task, doneFlag);
        }

        return task;
    }

    /**
     * Marks a task as done if the save file indicates so.
     */
    private void markDoneIfNeeded(Task task, String doneFlag) {
        if ("1".equals(doneFlag)) {
            task.markAsDone();
        }
    }

    /**
     * Saves the given tasks to the save file.
     * If the file does not exist, a new file is created.
     *
     * @param tasks The list of tasks to be saved.
     * @param ui    The {@code Ui} instance used to display error messages if saving fails.
     */
    public void save(ArrayList<Task> tasks, Ui ui) {
        File file = new File(filePath);

        try {
            if (!file.exists()) {
                file.getParentFile().mkdirs();  // create directories if needed
                file.createNewFile();
                ui.showError("File not found. Created new file at: " + filePath);
            }

            FileWriter fw = new FileWriter(file);  // overwrite file
            for (Task task : tasks) {
                fw.write(task.savedListFormat() + "\n");
            }
            fw.close();

        } catch (IOException e) {
            ui.showError("An error occurred while saving tasks: " + e.getMessage());
        }
    }
}
