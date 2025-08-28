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
import dupe.ui.Ui;
import dupe.tasks.Event;

public class Dupe {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    public Dupe(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.load());
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
                } else {
                    int taskID = Integer.parseInt(argument);
                    if (Parser.isValidIndex(taskID, tasks.getTasks())) {
                        Task selectedTask =  tasks.markTaskDone(taskID);
                        ui.showTaskMarked(selectedTask);
                    } else {
                        ui.showError("Please enter a valid task ID");
                    }
                }
                storage.save(tasks.getTasks(), ui);

            } else if (command.equals("unmark")) {
                if (argument.isEmpty()) {
                    ui.showError("Please enter a task number.");
                } else {
                    int taskID = Integer.parseInt(argument);
                    if (Parser.isValidIndex(taskID, tasks.getTasks())) {
                        Task selectedTask =  tasks.markTaskUndone(taskID);
                        ui.showTaskUnmarked(selectedTask);
                    } else {
                        ui.showError("Please enter a valid task ID");
                    }
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
                } else {
                    String[] subparts = Parser.parseBy(argument);
                    String description = subparts[0];
                    String deadline = subparts[1];
                    if (!deadline.isEmpty()) {
                        try {
                            LocalDateTime dateTime = Parser.parseDateTime(deadline);
                            Deadline task  = new Deadline(description, dateTime);
                            tasks.addTask(task);
                            ui.showTaskAdded(task,tasks.size()); //HERE
                        } catch (DateTimeParseException e) {
                            ui.showError("Invalid date format. Please use dd-MM-yyyy HH:mm");
                        }
                    } else {
                        ui.showError("Please enter a valid deadline for the task | Format: deadline description /by deadline.");
                    }
                }
                storage.save(tasks.getTasks(), ui);

            } else if (command.equals("event")) {
                if  (argument.isEmpty()) {
                    ui.showError("Please enter description.");
                } else {
                    String[] subparts = Parser.parseFrom(argument);
                    String description = subparts[0];
                    String dateTime = subparts[1];
                    if (!dateTime.isEmpty()) {
                        String[] subDateTime = Parser.parseTo(dateTime);
                        String from = subDateTime[0];
                        String to = subDateTime[1];
                        if (!to.isEmpty()) {
                            try {
                                LocalDateTime dateTimeFrom = Parser.parseDateTime(from);
                                LocalDateTime dateTimeTo = Parser.parseDateTime(to);
                                Event task = new Event(description, dateTimeFrom, dateTimeTo);
                                tasks.addTask(task);
                                ui.showTaskAdded(task, tasks.size());
                            } catch (DateTimeParseException e) {
                                ui.showError("Invalid date format. Please use dd-MM-yyyy HH:mm");
                            }
                        }
                    } else{
                        ui.showError("Please enter a valid datetime for the task | Format: event description /from datetime /to datetime.");
                    }
                }
                storage.save(tasks.getTasks(), ui);

            } else if (command.equals("delete")) {
                if (argument.isEmpty()) {
                    ui.showError("Please enter a task number.");
                } else {
                    int taskID = Integer.parseInt(argument);
                    if (Parser.isValidIndex(taskID, tasks.getTasks())) {
                        Task deleteTask = tasks.deleteTask(taskID);
                        ui.showTaskDeleted(deleteTask, tasks.size());
                    } else {
                        ui.showError("Please enter a valid task ID");
                    }
                }
                storage.save(tasks.getTasks(), ui);

            } else if (command.equals("find")) {
                if (argument.isEmpty()) {
                    ui.showError("Please enter a description.");
                } else {
                    if (tasks.isFound(argument)) {
                        ui.printFoundTasks(argument, tasks.getTasks());
                    } else {
                        ui.showError("Sorry keyword: \"" + argument + "\" not found");
                    }
                }
            }
            else {
                ui.showError("\n____________________\n"
                        + "Invalid Command\n"
                        + "____________________");
            }
        }
    }

    public static void main(String[] args) {
        new Dupe("data/tasks.txt").run();
    }
}
