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
import dupe.parser.Parser;
import dupe.ui.Ui;
import dupe.tasks.Event;

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
    public ArrayList<Task> load() throws IOException {
        File file = new File(filePath);

        // Ensure file exists
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
            return new ArrayList<>(); // return empty list if no file
        }

        TaskList taskList = new TaskList();
        Scanner fileScanner = new Scanner(file);

        while (fileScanner.hasNextLine()) {
            String line = fileScanner.nextLine();
            String[] parts = line.split(" \\| ");
            String type = parts[0];

            if (type.equals("T")) {
                ToDo task = new ToDo(parts[2]);
                if (parts[1].equals("1")) {
                    task.markAsDone();
                }
                taskList.addTask(task);
            } else if (type.equals("D")) {
                LocalDateTime dateTime = Parser.parseDateTimeFile(parts[3]);
                Deadline task = new Deadline(parts[2], dateTime);
                if (parts[1].equals("1")) {
                    task.markAsDone();
                }
                taskList.addTask(task);
            } else if (type.equals("E")) {
                LocalDateTime dateTimeFrom = Parser.parseDateTimeFile(parts[3]);
                LocalDateTime dateTimeTo = Parser.parseDateTimeFile(parts[4]);
                Event task = new Event(parts[2], dateTimeFrom, dateTimeTo);
                if (parts[1].equals("1")) {
                    task.markAsDone();
                }
                taskList.addTask(task);
            }
        }
        fileScanner.close();

        return taskList.getTasks();
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
                file.getParentFile().mkdirs(); // create directories if needed
                file.createNewFile();
                ui.showError("File not found. Created new file at: " + filePath);
            }

            FileWriter fw = new FileWriter(file); // overwrite file
            for (Task task : tasks) {
                fw.write(task.savedListFormat() + "\n");
            }
            fw.close();
        } catch (IOException e) {
            ui.showError("An error occurred while saving tasks: " + e.getMessage());
        }
    }
}
