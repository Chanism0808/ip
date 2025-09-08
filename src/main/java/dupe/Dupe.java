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

public class Dupe {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;
    private GuiUi guiUi;

    public Dupe(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        guiUi = new GuiUi();
        try {
            tasks = storage.load();
            ui.showListLoaded(tasks.getTasks());

        } catch (IOException e) {
            ui.showError("Error loading file");
            tasks = new TaskList();
        }
    }

    public void run() {
        ui.showGreeting();

        Scanner sc = new Scanner(System.in);
        while (sc.hasNextLine()) {
            String input = sc.nextLine();
            String[] parsed = Parser.parse(input);
            String command = parsed[0];
            String argument = parsed[1];

            if (command.equals("bye")) {
                ui.showExit();
                break;

            } else if (command.equals("list")) {
                if (tasks.isEmpty()) {
                    ui.showError("You have no tasks in your list.");
                } else {
                    ui.showTaskList(tasks.getTasks());
                }

            } else if (command.equals("mark")) {
                if (argument.isEmpty()) {
                    ui.showError("Please enter a task number.");
                    return;
                }
                int taskID = Integer.parseInt(argument);
                if (Parser.isValidIndex(taskID, tasks.getTasks())) {
                    Task selectedTask = tasks.markTaskDone(taskID);
                    ui.showTaskMarked(selectedTask);
                } else {
                    ui.showError("Please enter a valid task ID");
                }
                storage.save(tasks.getTasks(), ui);

            } else if (command.equals("unmark")) {
                if (argument.isEmpty()) {
                    ui.showError("Please enter a task number.");
                    return;
                }
                int taskID = Integer.parseInt(argument);
                if (Parser.isValidIndex(taskID, tasks.getTasks())) {
                    Task selectedTask = tasks.markTaskUndone(taskID);
                    ui.showTaskUnmarked(selectedTask);
                } else {
                    ui.showError("Please enter a valid task ID");
                }
                storage.save(tasks.getTasks(), ui);

            } else if (command.equals("todo")) {
                if (argument.isEmpty()) {
                    ui.showError("Please enter description.");
                } else {
                    ToDo task = new ToDo(argument);
                    tasks.addTask(task);
                    ui.showTaskAdded(task, tasks.size());
                }
                storage.save(tasks.getTasks(), ui);

            } else if (command.equals("deadline")) {
                if (argument.isEmpty()) {
                    ui.showError("Please enter description.");
                    storage.save(tasks.getTasks(), ui);
                    return;
                }

                String[] subparts = Parser.parseBy(argument);
                String description = subparts[0];
                String deadline = subparts[1];

                if (deadline.isEmpty()) {
                    ui.showError("Please enter a valid deadline for the task | Format: deadline description /by deadline.");
                    storage.save(tasks.getTasks(), ui);
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

                storage.save(tasks.getTasks(), ui);

            } else if (command.equals("event")) {
                if (argument.isEmpty()) {
                    ui.showError("Please enter description.");
                    storage.save(tasks.getTasks(), ui);
                    return;
                }

                String[] subparts = Parser.parseFrom(argument);
                String description = subparts[0];
                String dateTime = subparts[1];

                if (dateTime.isEmpty()) {
                    ui.showError("Please enter a valid datetime for the task | Format: event description /from datetime /to datetime.");
                    storage.save(tasks.getTasks(), ui);
                    return;
                }

                String[] subDateTime = Parser.parseTo(dateTime);
                String from = subDateTime[0];
                String to = subDateTime[1];

                if (to.isEmpty()) {
                    ui.showError("Please enter both start and end datetime | Format: event description /from datetime /to datetime.");
                    storage.save(tasks.getTasks(), ui);
                    return;
                }

                try {
                    LocalDateTime dateTimeFrom = Parser.parseDateTime(from);
                    LocalDateTime dateTimeTo = Parser.parseDateTime(to);
                    Event task = new Event(description, dateTimeFrom, dateTimeTo);
                    tasks.addTask(task);
                    ui.showTaskAdded(task, tasks.size());
                } catch (DateTimeParseException e) {
                    ui.showError("Invalid date format. Please use dd-MM-yyyy HH:mm");
                }

                storage.save(tasks.getTasks(), ui);

            } else if (command.equals("delete")) {
                if (argument.isEmpty()) {
                    ui.showError("Please enter a task number.");
                    return;
                }
                int taskID = Integer.parseInt(argument);
                if (Parser.isValidIndex(taskID, tasks.getTasks())) {
                    Task deleteTask = tasks.deleteTask(taskID);
                    ui.showTaskDeleted(deleteTask, tasks.size());
                } else {
                    ui.showError("Please enter a valid task ID");
                }

                storage.save(tasks.getTasks(), ui);

            } else if (command.equals("find")) {
                if (argument.isEmpty()) {
                    ui.showError("Please enter a description.");
                    return;
                }
                if (tasks.isFound(argument)) {
                    ui.printFoundTasks(argument, tasks.getTasks());
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
        String command = parsed[0];
        String argument = parsed[1];
        StringBuilder reply = new StringBuilder();

        if (command.equals("bye")) {
            reply.append(guiUi.showExit());

        } else if (command.equals("list")) {
            if (tasks.isEmpty()) {
                reply.append(guiUi.showError("You have no tasks in your list."));
            } else {
                reply.append(guiUi.showTaskList(tasks.getTasks()));
            }

        } else if (command.equals("mark")) {
            if (argument.isEmpty()) {
                reply.append(guiUi.showError("Please enter a task number."));
            } else {
                int taskID = Integer.parseInt(argument);
                if (Parser.isValidIndex(taskID, tasks.getTasks())) {
                    Task selectedTask = tasks.markTaskDone(taskID);
                    reply.append(guiUi.showTaskMarked(selectedTask));
                } else {
                    reply.append(guiUi.showError("Please enter a valid task ID"));
                }
            }
            storage.save(tasks.getTasks(), ui);

        } else if (command.equals("unmark")) {
            if (argument.isEmpty()) {
                reply.append(guiUi.showError("Please enter a task number."));
            } else {
                int taskID = Integer.parseInt(argument);
                if (Parser.isValidIndex(taskID, tasks.getTasks())) {
                    Task selectedTask = tasks.markTaskUndone(taskID);
                    reply.append(guiUi.showTaskUnmarked(selectedTask));
                } else {
                    reply.append(guiUi.showError("Please enter a valid task ID"));
                }
            }
            storage.save(tasks.getTasks(), ui);

        } else if (command.equals("todo")) {
            if (argument.isEmpty()) {
                reply.append(guiUi.showError("Please enter description."));
            } else {
                ToDo task = new ToDo(argument);
                tasks.addTask(task);
                reply.append(guiUi.showTaskAdded(task, tasks.size()));
            }
            storage.save(tasks.getTasks(), ui);

        } else if (command.equals("deadline")) {
            if (argument.isEmpty()) {
                reply.append(guiUi.showError("Please enter description."));
                storage.save(tasks.getTasks(), ui);
                return reply.toString();
            }

            String[] subparts = Parser.parseBy(argument);
            String description = subparts[0];
            String deadline = subparts[1];

            if (deadline.isEmpty()) {
                reply.append(guiUi.showError(
                        "Please enter a valid deadline | Format: deadline description /by deadline."
                ));
                storage.save(tasks.getTasks(), ui);
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

            storage.save(tasks.getTasks(), ui);

        } else if (command.equals("event")) {
            if (argument.isEmpty()) {
                reply.append(guiUi.showError("Please enter description."));
                storage.save(tasks.getTasks(), ui);
                return reply.toString();
            }

            String[] subparts = Parser.parseFrom(argument);
            String description = subparts[0];
            String dateTime = subparts[1];

            if (dateTime.isEmpty()) {
                reply.append(guiUi.showError(
                        "Please enter a valid datetime | Format: event description /from datetime /to datetime."
                ));
                storage.save(tasks.getTasks(), ui);
                return reply.toString();
            }

            String[] subDateTime = Parser.parseTo(dateTime);
            String from = subDateTime[0];
            String to = subDateTime[1];

            if (to.isEmpty()) {
                reply.append(guiUi.showError(
                        "Please enter both start and end datetime | Format: event description /from datetime /to datetime."
                ));
                storage.save(tasks.getTasks(), ui);
                return reply.toString();
            }

            try {
                LocalDateTime dateTimeFrom = Parser.parseDateTime(from);
                LocalDateTime dateTimeTo = Parser.parseDateTime(to);
                Event task = new Event(description, dateTimeFrom, dateTimeTo);
                tasks.addTask(task);
                reply.append(guiUi.showTaskAdded(task, tasks.size()));
            } catch (DateTimeParseException e) {
                reply.append(guiUi.showError("Invalid date format. Please use dd-MM-yyyy HH:mm"));
            }

            storage.save(tasks.getTasks(), ui);

        } else if (command.equals("delete")) {
            if (argument.isEmpty()) {
                reply.append(guiUi.showError("Please enter a task number."));
                return reply.toString();
            }
            int taskID = Integer.parseInt(argument);
            if (Parser.isValidIndex(taskID, tasks.getTasks())) {
                Task deleteTask = tasks.deleteTask(taskID);
                reply.append(guiUi.showTaskDeleted(deleteTask, tasks.size()));
            } else {
                reply.append(guiUi.showError("Please enter a valid task ID"));
            }

            storage.save(tasks.getTasks(), ui);

        } else if (command.equals("find")) {
            if (argument.isEmpty()) {
                reply.append(guiUi.showError("Please enter a description."));
                return reply.toString();
            }
            if (tasks.isFound(argument)) {
                reply.append(guiUi.printFoundTasks(argument, tasks.getTasks()));
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
            ui.showListLoaded(tasks.getTasks());
            reply.append(guiUi.showGreeting());
            reply.append(guiUi.showListLoaded(tasks.getTasks()));

        } catch (IOException e) {
            ui.showError("Error loading file");
            reply.append((guiUi.showError("Error loading file")));
            tasks = new TaskList();
        }
        return reply.toString();
    }


}



