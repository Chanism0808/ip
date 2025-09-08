package dupe;

import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.io.IOException;
import java.time.LocalDateTime;
import dupe.tasks.Task;
import dupe.tasks.Deadline;
import dupe.tasks.TaskList;
import dupe.tasks.ToDo;
import dupe.parser.Parser;
import dupe.storage.Storage;
import dupe.ui.GuiUi;
import dupe.ui.Ui;
import dupe.tasks.Event;
import dupe.priority.Priority;

public class Dupe {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;
    private GuiUi guiUi;

    public Dupe(String filePath) {
        assert filePath != null && !filePath.trim().isEmpty()
                : "File path must not be null or empty";
        ui = new Ui();
        storage = new Storage(filePath);
        guiUi = new GuiUi();
        try {
            tasks = storage.load();
            assert tasks != null : "TaskList should not be null after loading";
            ui.showListLoaded(tasks.getTaskList());

        } catch (IOException e) {
            ui.showError("Error loading file");
            tasks = new TaskList();
            assert tasks != null : "TaskList must be initialized even after IOException";
        }
    }

    public void run() {
        ui.showGreeting();

        Scanner sc = new Scanner(System.in);
        while (sc.hasNextLine()) {
            String input = sc.nextLine();
            String[] parsed = Parser.parse(input);
            assert parsed.length == 2 : "Parser should always return exactly 2 parts";
            String command = parsed[0];
            assert command != null : "Command must not be null";
            String argument = parsed[1];
            assert argument != null : "Argument must not be null";

            if (command.equals("bye")) {
                ui.showExit();
                break;

            } else if (command.equals("list")) {
                if (tasks.isEmpty()) {
                    ui.showError("You have no tasks in your list.");
                } else {
                    ui.showTaskList(tasks.getTaskList());
                }

            } else if (command.equals("setPriority")) {
                if (argument.isEmpty()) {
                    ui.showError("Please enter a task number followed by the priority [LOW, MEDIUM, HIGH]. ");
                    return;
                }
                String[] subparts = Parser.parse(argument);
                int taskId = Integer.parseInt(subparts[0]);
                Task task = tasks.getTask(taskId - 1);
                Priority priority;

                if(!Parser.isValidIndex(taskId, tasks.getTaskList())) {
                    ui.showError("Please enter a valid task ID");
                    return;
                }

                try {
                    priority = Priority.valueOf(subparts[1].toUpperCase());
                } catch (IllegalArgumentException e) {
                    ui.showError("Invalid priority. Use LOW, MEDIUM, or HIGH.");
                    return;
                }
                tasks.setTaskPriority(taskId, priority);
                ui.showPrioritySet(task);

                storage.save(tasks.getTaskList(), ui);

            } else if (command.equals("mark")) {
                if (argument.isEmpty()) {
                    ui.showError("Please enter a task number.");
                    return;
                }
                int taskId = Integer.parseInt(argument);
                assert taskId > 0 : "Task ID should be positive";
                assert taskId <= tasks.size() : "Task ID should not exceed number of tasks";
                if (Parser.isValidIndex(taskId, tasks.getTaskList())) {
                    Task selectedTask = tasks.markTaskDone(taskId);
                    ui.showTaskMarked(selectedTask);
                } else {
                    ui.showError("Please enter a valid task ID");
                }
                storage.save(tasks.getTaskList(), ui);

            } else if (command.equals("unmark")) {
                if (argument.isEmpty()) {
                    ui.showError("Please enter a task number.");
                    return;
                }
                int taskId = Integer.parseInt(argument);
                assert taskId > 0 : "Task ID should be positive";
                assert taskId <= tasks.size() : "Task ID should not exceed number of tasks";
                if (Parser.isValidIndex(taskId, tasks.getTaskList())) {
                    Task selectedTask = tasks.markTaskUndone(taskId);
                    ui.showTaskUnmarked(selectedTask);
                } else {
                    ui.showError("Please enter a valid task ID");
                }
                storage.save(tasks.getTaskList(), ui);

            } else if (command.equals("todo")) {
                if (argument.isEmpty()) {
                    ui.showError("Please enter description.");
                } else {
                    ToDo task = new ToDo(argument);
                    tasks.addTask(task);
                    ui.showTaskAdded(task, tasks.size());
                }
                storage.save(tasks.getTaskList(), ui);

            } else if (command.equals("deadline")) {
                if (argument.isEmpty()) {
                    ui.showError("Please enter description.");
                    storage.save(tasks.getTaskList(), ui);
                    return;
                }

                String[] subparts = Parser.parseBy(argument);
                String description = subparts[0];
                String deadline = subparts[1];

                if (deadline.isEmpty()) {
                    ui.showError("Please enter a valid deadline for the task | Format: deadline description /by deadline.");
                    storage.save(tasks.getTaskList(), ui);
                    return;
                }

                try {
                    LocalDateTime dateTime = Parser.parseDateTime(deadline);
                    Deadline task = new Deadline(description, dateTime);
                    tasks.addTask(task);
                    ui.showTaskAdded(task, tasks.size());
                } catch (DateTimeParseException e) {
                    ui.showError("Invalid date format. Please use dd-MM-yyyy HH:mm");
                }
                storage.save(tasks.getTaskList(), ui);

            } else if (command.equals("event")) {
                if (argument.isEmpty()) {
                    ui.showError("Please enter description.");
                    storage.save(tasks.getTaskList(), ui);
                    return;
                }

                String[] subparts = Parser.parseFrom(argument);
                String description = subparts[0];
                String dateTime = subparts[1];

                if (dateTime.isEmpty()) {
                    ui.showError("Please enter a valid datetime for the task | Format: event description /from datetime /to datetime.");
                    storage.save(tasks.getTaskList(), ui);
                    return;
                }

                String[] subDateTime = Parser.parseTo(dateTime);
                String from = subDateTime[0];
                String to = subDateTime[1];

                if (to.isEmpty()) {
                    ui.showError("Please enter both start and end datetime | Format: event description /from datetime /to datetime.");
                    storage.save(tasks.getTaskList(), ui);
                    return;
                }

                try {
                    LocalDateTime dateTimeFrom = Parser.parseDateTime(from);
                    LocalDateTime dateTimeTo = Parser.parseDateTime(to);
                    assert dateTimeFrom.isBefore(dateTimeTo)
                            : "Event start time must be before end time";
                    Event task = new Event(description, dateTimeFrom, dateTimeTo);
                    tasks.addTask(task);
                    ui.showTaskAdded(task, tasks.size());
                } catch (DateTimeParseException e) {
                    ui.showError("Invalid date format. Please use dd-MM-yyyy HH:mm");
                }

                storage.save(tasks.getTaskList(), ui);

            } else if (command.equals("delete")) {
                if (argument.isEmpty()) {
                    ui.showError("Please enter a task number.");
                    return;
                }
                int taskID = Integer.parseInt(argument);
                if (Parser.isValidIndex(taskID, tasks.getTaskList())) {
                    Task deleteTask = tasks.deleteTask(taskID);
                    ui.showTaskDeleted(deleteTask, tasks.size());
                } else {
                    ui.showError("Please enter a valid task ID");
                }

                storage.save(tasks.getTaskList(), ui);

            } else if (command.equals("find")) {
                if (argument.isEmpty()) {
                    ui.showError("Please enter a description.");
                    return;
                }
                if (tasks.isFound(argument)) {
                    ui.printFoundTasks(argument, tasks.getTaskList());
                } else {
                    ui.showError("Sorry keyword: \"" + argument + "\" not found");
                }

            } else {
                ui.showError("\n____________________\n"
                        + "Invalid Command\n"
                        + "____________________");
            }
        }
    }

    public static void main(String[] args) {
        new Dupe("data/tasks.txt").run();
    }

    /**
     * Generates a response for the user's chat message.
     */
    public String getResponse(String input) {
        String[] parsed = Parser.parse(input);
        assert parsed.length == 2 : "Parser should always return exactly 2 parts";
        String command = parsed[0];
        assert command != null : "Command must not be null";
        String argument = parsed[1];
        assert argument != null : "Argument must not be null";
        StringBuilder reply = new StringBuilder();


        if (command.equals("bye")) {
            reply.append(guiUi.showExit());

        } else if (command.equals("list")) {
            if (tasks.isEmpty()) {
                reply.append(guiUi.showError("You have no tasks in your list."));
            } else {
                reply.append(guiUi.showTaskList(tasks.getTaskList()));
            }

        } else if (command.equals("setPriority")) {
            if (argument.isEmpty()) {
                reply.append(guiUi.showError("Please enter a task number followed by the priority [LOW, MEDIUM, HIGH]. "));
                return reply.toString();
            }

            String[] subparts = Parser.parse(argument);
            int taskId = Integer.parseInt(subparts[0]);

            if (!Parser.isValidIndex(taskId, tasks.getTaskList())) {
                reply.append(guiUi.showError("Please enter a valid task ID"));
                return reply.toString();
            }

            Priority priority;
            try {
                priority = Priority.valueOf(subparts[1].toUpperCase());
            } catch (IllegalArgumentException e) {
                reply.append(guiUi.showError("Invalid priority. Use LOW, MEDIUM, or HIGH."));
                return reply.toString();
            }

            Task task = tasks.getTask(taskId);
            tasks.setTaskPriority(taskId, priority);
            reply.append(guiUi.showPrioritySet(task));

            storage.save(tasks.getTaskList(), guiUi);

        } else if (command.equals("mark")) {
            if (argument.isEmpty()) {
                reply.append(guiUi.showError("Please enter a task number."));
            } else {
                int taskID = Integer.parseInt(argument);
                assert taskID > 0 : "Task ID should be positive";
                assert taskID <= tasks.size() : "Task ID should not exceed number of tasks";
                if (Parser.isValidIndex(taskID, tasks.getTaskList())) {
                    Task selectedTask = tasks.markTaskDone(taskID);
                    reply.append(guiUi.showTaskMarked(selectedTask));
                } else {
                    reply.append(guiUi.showError("Please enter a valid task ID"));
                }
            }
            storage.save(tasks.getTaskList(), guiUi);

        } else if (command.equals("unmark")) {
            if (argument.isEmpty()) {
                reply.append(guiUi.showError("Please enter a task number."));
            } else {
                int taskID = Integer.parseInt(argument);
                assert taskID > 0 : "Task ID should be positive";
                assert taskID <= tasks.size() : "Task ID should not exceed number of tasks";
                if (Parser.isValidIndex(taskID, tasks.getTaskList())) {
                    Task selectedTask = tasks.markTaskUndone(taskID);
                    reply.append(guiUi.showTaskUnmarked(selectedTask));
                } else {
                    reply.append(guiUi.showError("Please enter a valid task ID"));
                }
            }
            storage.save(tasks.getTaskList(), guiUi);

        } else if (command.equals("todo")) {
            if (argument.isEmpty()) {
                reply.append(guiUi.showError("Please enter description."));
            } else {
                ToDo task = new ToDo(argument);
                tasks.addTask(task);
                reply.append(guiUi.showTaskAdded(task, tasks.size()));
            }
            storage.save(tasks.getTaskList(), guiUi);

        } else if (command.equals("deadline")) {
            if (argument.isEmpty()) {
                reply.append(guiUi.showError("Please enter description."));
                storage.save(tasks.getTaskList(), guiUi);
                return reply.toString();
            }

            String[] subparts = Parser.parseBy(argument);
            String description = subparts[0];
            String deadline = subparts[1];

            if (deadline.isEmpty()) {
                reply.append(guiUi.showError(
                        "Please enter a valid deadline | Format: deadline description /by deadline."
                ));
                storage.save(tasks.getTaskList(), guiUi);
                return reply.toString();
            }

            try {
                LocalDateTime dateTime = Parser.parseDateTime(deadline);
                Deadline task = new Deadline(description, dateTime);
                tasks.addTask(task);
                reply.append(guiUi.showTaskAdded(task, tasks.size()));
            } catch (DateTimeParseException e) {
                reply.append(guiUi.showError("Invalid date format. Please use dd-MM-yyyy HH:mm"));
            }

            storage.save(tasks.getTaskList(), guiUi);

        } else if (command.equals("event")) {
            if (argument.isEmpty()) {
                reply.append(guiUi.showError("Please enter description."));
                storage.save(tasks.getTaskList(), guiUi);
                return reply.toString();
            }

            String[] subparts = Parser.parseFrom(argument);
            String description = subparts[0];
            String dateTime = subparts[1];

            if (dateTime.isEmpty()) {
                reply.append(guiUi.showError(
                        "Please enter a valid datetime | Format: event description /from datetime /to datetime."
                ));
                storage.save(tasks.getTaskList(), guiUi);
                return reply.toString();
            }

            String[] subDateTime = Parser.parseTo(dateTime);
            String from = subDateTime[0];
            String to = subDateTime[1];

            if (to.isEmpty()) {
                reply.append(guiUi.showError(
                        "Please enter both start and end datetime | Format: event description /from datetime /to datetime."
                ));
                storage.save(tasks.getTaskList(), guiUi);
                return reply.toString();
            }

            try {
                LocalDateTime dateTimeFrom = Parser.parseDateTime(from);
                LocalDateTime dateTimeTo = Parser.parseDateTime(to);
                assert dateTimeFrom.isBefore(dateTimeTo)
                        : "Event start time must be before end time";
                Event task = new Event(description, dateTimeFrom, dateTimeTo);
                tasks.addTask(task);
                reply.append(guiUi.showTaskAdded(task, tasks.size()));
            } catch (DateTimeParseException e) {
                reply.append(guiUi.showError("Invalid date format. Please use dd-MM-yyyy HH:mm"));
            }

            storage.save(tasks.getTaskList(), guiUi);

        } else if (command.equals("delete")) {
            if (argument.isEmpty()) {
                reply.append(guiUi.showError("Please enter a task number."));
                return reply.toString();
            }
            int taskID = Integer.parseInt(argument);
            if (Parser.isValidIndex(taskID, tasks.getTaskList())) {
                Task deleteTask = tasks.deleteTask(taskID);
                reply.append(guiUi.showTaskDeleted(deleteTask, tasks.size()));
            } else {
                reply.append(guiUi.showError("Please enter a valid task ID"));
            }

            storage.save(tasks.getTaskList(), guiUi);

        } else if (command.equals("find")) {
            if (argument.isEmpty()) {
                reply.append(guiUi.showError("Please enter a description."));
                return reply.toString();
            }
            if (tasks.isFound(argument)) {
                reply.append(guiUi.printFoundTasks(argument, tasks.getTaskList()));
            } else {
                reply.append(guiUi.showError("Sorry keyword: \"" + argument + "\" not found"));
            }

        } else {
            reply.append(guiUi.showError(
                    "\n____________________\nInvalid Command\n____________________"
            ));
        }
        return reply.toString();
    }

    public String getGreetings() {
        StringBuilder reply = new StringBuilder();

        try {
            tasks = storage.load();
            reply.append(guiUi.showGreeting());
            reply.append(guiUi.showListLoaded(tasks.getTaskList()));

        } catch (IOException e) {
            ui.showError("Error loading file");
            reply.append((guiUi.showError("Error loading file")));
            tasks = new TaskList();
        }
        return reply.toString();
    }


}



