import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Dupe {
    static ArrayList<Task>  taskArrayList = new ArrayList<>();
    static Ui ui = new Ui();
    //static TaskList taskList = new TaskList(ui, taskArrayList);

    public static void main(String[] args) {
        ui.showGreeting();
        loadList();
        TaskList taskList =  new TaskList(ui, taskArrayList);

        Scanner sc = new Scanner(System.in);
        while (sc.hasNextLine()) {
//            String input = sc.nextLine();
//            String[] parts = input.split(" ", 2); // split into at most 2 parts
//            String command = parts[0];
//            String argument = parts.length > 1 ? parts[1] : "";

            String input = sc.nextLine();
            String[] parsed = Parser.parse(input);
            String command = parsed[0];
            String argument = parsed[1];

            if (command.equals("bye")) {
                ui.showExit();
                break;

            } else if (command.equals("list")) {
                if (taskList.isEmpty()) {
                    ui.showError("You have no tasks in your list.");
                } else {
                    ui.showTaskList(taskList.getTasks());
                }

            } else if (command.equals("mark")) {
                if (argument.isEmpty()) {
                    ui.showError("Please enter a task number.");
                } else {
                    int taskID = Parser.parseInt(argument);
                    Task selectedTask =  taskList.markTaskDone(taskID);
                    ui.showTaskMarked(selectedTask);
                }
                saveList();

            } else if (command.equals("unmark")) {
                if (argument.isEmpty()) {
                    ui.showError("Please enter a task number.");
                } else {
                    int taskID = Parser.parseInt(argument);
                    Task selectedTask =  taskList.markTaskUndone(taskID);
                    ui.showTaskMarked(selectedTask);
                }
                saveList();

            } else if (command.equals("todo")) {
                if (argument.isEmpty()) {
                    ui.showError("Please enter description.");
                } else {
                    ToDos task = new ToDos(argument);
                    taskList.addTask(task);
                    ui.showTaskAdded(task, taskList.size());
                }
                saveList();

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
                            Deadlines task  = new Deadlines(description, dateTime);
                            taskList.addTask(task);
                            ui.showTaskAdded(task,taskList.size()); //HERE
                        } catch (DateTimeParseException e) {
                            ui.showError("Invalid date format. Please use dd-MM-yyyy HH:mm");
                        }
                    } else {
                        ui.showError("Please enter a valid deadline for the task | Format: deadline description /by deadline.");
                    }
                }
                saveList();

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
                                Events task = new Events(description, dateTimeFrom, dateTimeTo);
                                taskList.addTask(task);
                                ui.showTaskAdded(task, taskList.size()); //HERE
                            } catch (DateTimeParseException e) {
                                ui.showError("Invalid date format. Please use dd-MM-yyyy HH:mm");
                            }
                        }
                    } else{
                        ui.showError("Please enter a valid datetime for the task | Format: event description /from datetime /to datetime.");
                    }
                }
                saveList();

            } else if (command.equals("delete")) {
                if (argument.isEmpty()) {
                    ui.showError("Please enter a task number.");
                } else {
                    int taskID = Integer.parseInt(argument);
                    if (Parser.isValidIndex(taskID, taskList.getTasks())) { //HERE
                        Task deleteTask = taskList.deleteTask(taskID);
                        ui.showTaskDeleted(deleteTask, taskList.size());
                    } else {
                        ui.showError("Please enter a valid task ID");
                    }
                }
                saveList();
            }
            else {
                ui.showError("\n____________________\n"
                        + "Invalid Command\n"
                        + "____________________");
            }
        }
    }


    public static void saveList() {
        String filePath = "./data/tasks.txt";
        File file = new File(filePath);

        try {
            // If file doesn't exist, create it
            if (!file.exists()) {
                file.getParentFile().mkdirs(); // create "data" folder if not exist
                file.createNewFile();
                ui.showError("File not found. Created new file at: " + filePath);
                return;
            }
            FileWriter fw = new FileWriter("data/tasks.txt"); // 'true' = append mode
            for (Task task : taskArrayList) {
                fw.write(task.savedListFormat() + "\n");
            }
            fw.close();
        } catch (IOException e) {
            ui.showError("An error occurred while saving tasks: " + e.getMessage());
        }
    }

    public static void loadList() {
        String filePath = "./data/tasks.txt"; // relative path
        File file = new File(filePath);

        try {
            // If file doesn't exist, create it
            if (!file.exists()) {
                file.getParentFile().mkdirs(); // create "data" folder if not exist
                file.createNewFile();
                ui.showError("File not found. Created new file at: " + filePath);
                return;
            }
            // Read from the file and load tasks
            Scanner fileScanner = new Scanner(file);

            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split(" \\| ");
                String type = parts[0];

                if (type.equals("T")) {
                    ToDos task = new ToDos(parts[2]);
                    if (parts[1].equals("1")) {
                        task.markAsDone();
                    }
                    taskArrayList.add(task);
                }
                else if (type.equals("D")) {
                    LocalDateTime dateTime = Parser.parseDateTimeFile(parts[3]);
                    Deadlines task = new Deadlines(parts[2], dateTime);
                    if (parts[1].equals("1")) {
                        task.markAsDone();
                    }
                    taskArrayList.add(task);
                }
                else if (type.equals("E")) {
                    LocalDateTime dateTimeFrom = Parser.parseDateTimeFile(parts[3]);
                    LocalDateTime dateTimeTo = Parser.parseDateTimeFile(parts[4]);
                    Events task = new Events(parts[2], dateTimeFrom, dateTimeTo);
                    if (parts[1].equals("1")) {
                        task.markAsDone();
                    }
                    taskArrayList.add(task);
                }
            }
            fileScanner.close();
            ui.showListLoaded(taskArrayList);

        } catch (IOException e) {
            ui.showError("An error occurred while loading tasks: " + e.getMessage());
        }

    }

}
