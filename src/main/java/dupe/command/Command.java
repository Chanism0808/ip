package dupe.command;

import dupe.tasks.Task;
import dupe.tasks.Deadline;
import dupe.tasks.TaskList;
import dupe.tasks.ToDo;
import dupe.parser.Parser;
import dupe.storage.Storage;
import dupe.ui.GuiUi;
import dupe.tasks.Event;
import dupe.priority.Priority;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

public class Command {
    private final TaskList tasks;
    private final GuiUi guiUi;
    private final Storage storage;

    public Command(TaskList tasks, GuiUi guiUi, Storage storage) {
        this.tasks = tasks;
        this.guiUi = guiUi;
        this.storage = storage;
    }

    public String list() {
        if (tasks.isEmpty()) {
            return guiUi.showError("You have no tasks in your list.");
        }
        return guiUi.showTaskList(tasks.getTaskList());
    }

    public String todo(String argument) {
        if (argument.isEmpty()) return guiUi.showError("Please enter description.");
        ToDo task = new ToDo(argument);
        tasks.addTask(task);
        save();
        return guiUi.showTaskAdded(task, tasks.size());
    }

    public String deadline(String argument) {
        if (argument.isEmpty()) return guiUi.showError("Please enter description.");
        String[] parts = Parser.parseBy(argument);
        String description = parts[0];
        String deadline = parts[1];
        if (deadline.isEmpty()) return guiUi.showError("Please enter a valid deadline.");
        try {
            LocalDateTime dateTime = Parser.parseDateTime(deadline);
            Deadline task = new Deadline(description, dateTime);
            tasks.addTask(task);
            save();
            return guiUi.showTaskAdded(task, tasks.size());
        } catch (DateTimeParseException e) {
            return guiUi.showError("Invalid date format. Use dd-MM-yyyy HH:mm");
        }
    }

    public String event(String argument) {
        if (argument.isEmpty()) return guiUi.showError("Please enter description.");
        String[] parts = Parser.parseFrom(argument);
        String description = parts[0];
        String dateTime = parts[1];
        String[] subParts = Parser.parseTo(dateTime);
        String from = subParts[0];
        String to = subParts[1];
        if (to.isEmpty()) return guiUi.showError("Please enter both start and end datetime.");
        try {
            LocalDateTime start = Parser.parseDateTime(from);
            LocalDateTime end = Parser.parseDateTime(to);
            Event task = new Event(description, start, end);
            tasks.addTask(task);
            save();
            return guiUi.showTaskAdded(task, tasks.size());
        } catch (DateTimeParseException e) {
            return guiUi.showError("Invalid date format. Use dd-MM-yyyy HH:mm");
        }
    }

    public String mark(String argument, boolean done) {
        if (argument.isEmpty()) return guiUi.showError("Please enter a task number.");
        int taskId = Integer.parseInt(argument);
        if (!Parser.isValidIndex(taskId, tasks.getTaskList())) return guiUi.showError("Please enter a valid task ID");
        Task task = done ? tasks.markTaskDone(taskId) : tasks.markTaskUndone(taskId);
        save();
        return done ? guiUi.showTaskMarked(task) : guiUi.showTaskUnmarked(task);
    }

    public String delete(String argument) {
        if (argument.isEmpty()) return guiUi.showError("Please enter a task number.");
        int taskId = Integer.parseInt(argument);
        if (!Parser.isValidIndex(taskId, tasks.getTaskList())) return guiUi.showError("Please enter a valid task ID");
        Task deleted = tasks.deleteTask(taskId);
        save();
        return guiUi.showTaskDeleted(deleted, tasks.size());
    }

    public String find(String argument) {
        if (argument.isEmpty()) return guiUi.showError("Please enter a description.");
        if (tasks.isFound(argument)) return guiUi.printFoundTasks(argument, tasks.getTaskList());
        return guiUi.showError("Sorry keyword: \"" + argument + "\" not found");
    }

    public String setPriority(String argument) {
        if (argument.isEmpty()) return guiUi.showError("Please enter a task number followed by priority.");
        String[] parts = Parser.parse(argument);
        int taskId = Integer.parseInt(parts[0]);
        if (!Parser.isValidIndex(taskId, tasks.getTaskList())) return guiUi.showError("Please enter a valid task ID");
        try {
            Priority priority = Priority.valueOf(parts[1].toUpperCase());
            Task task = tasks.getTask(taskId);
            tasks.setTaskPriority(taskId, priority);
            save();
            return guiUi.showPrioritySet(task);
        } catch (IllegalArgumentException e) {
            return guiUi.showError("Invalid priority. Use LOW, MEDIUM, or HIGH.");
        }
    }

    private void save() {
        storage.save(tasks.getTaskList(), guiUi);
    }
}