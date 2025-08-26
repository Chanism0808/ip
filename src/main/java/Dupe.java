import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
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
            String input = sc.nextLine();
            String[] parts = input.split(" ", 2); // split into at most 2 parts
            String command = parts[0];
            String argument = parts.length > 1 ? parts[1] : "";

            if (command.equals("bye")) {
                ui.showExit();
                break;

            } else if (command.equals("list")) {
                taskList.listTasks();

            } else if (command.equals("mark")) {
                if (!isArgumentEmpty(argument)) { //if it is not empty the whole statement is true
                    int taskID = Integer.parseInt(parts[1]);
                    taskList.markTaskDone(taskID);
                }
                saveList();

            } else if (command.equals("unmark")) {
                if (!isArgumentEmpty(argument)) {
                    int taskID = Integer.parseInt(parts[1]);
                    taskList.markTaskUndone(taskID);
                }
                saveList();

            } else if (command.equals("todo")) {
                if (!isArgumentEmptyTask(argument)) {
                    ToDos task = new ToDos(parts[1]);
                    taskList.addTask(task); //addTask() in TaskList.java
                }
                saveList();

            } else if (command.equals("deadline")) {
                //String[] arguments = input.split(" ",2);
                if (!isArgumentEmptyTask(argument)) {
                    String[] subparts = argument.split("/by ", 2);
                    String description = subparts[0];
                    String deadline = subparts.length > 1 ? subparts[1] : "";
                    if (!deadline.isEmpty()) {
                        try {
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
                            LocalDateTime dateTime = LocalDateTime.parse(deadline, formatter);
                            Deadlines task  = new Deadlines(description, dateTime);
                            taskList.addTask(task); //addTask() in TaskList.java
                        } catch (DateTimeParseException e) {
                            ui.showError("Invalid date format. Please use dd-MM-yyyy HH:mm");
                        }
                    } else {
                        ui.showError("Please enter a valid deadline for the task | Format: deadline description /by deadline.");
                    }
                }
                saveList();

            } else if (command.equals("event")) {
                if (!isArgumentEmptyTask(argument)) {
                    String[] subparts = argument.split("/from ", 2);
                    String description = subparts[0];
                    String dateTime = subparts.length > 1 ? subparts[1] : "";
                    if (!dateTime.isEmpty()) {
                        String[] subdateTime = dateTime.split(" /to ", 2);
                        String from = subdateTime[0];
                        String to = subdateTime.length > 1 ? subdateTime[1] : "";
                        if (!to.isEmpty()) {
                            try {
                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
                                LocalDateTime dateTimeFrom = LocalDateTime.parse(from, formatter);
                                LocalDateTime dateTimeTo = LocalDateTime.parse(to, formatter);
                                Events task = new Events(description, dateTimeFrom, dateTimeTo);
                                taskList.addTask(task); //addTask() in TaskList.java
                            } catch (DateTimeParseException e) {
                                ui.showError("Invalid date format. Please use dd-MM-yyyy HH:mm");
                            }
                        }
                        ui.showError("Please enter a valid datetime for the task | Format: event description /from datetime /to datetime.");
                    } else{
                        ui.showError("Please enter a valid datetime for the task | Format: event description /from datetime /to datetime.");
                    }
                }
                saveList();

            } else if (command.equals("delete")) {
                if (!isArgumentEmpty(argument)) {
                    int taskID = Integer.parseInt(argument);
                    taskList.deleteTask(taskID);
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
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm");
                    LocalDateTime dateTime = LocalDateTime.parse(parts[3], formatter);
                    Deadlines task = new Deadlines(parts[2], dateTime);
                    if (parts[1].equals("1")) {
                        task.markAsDone();
                    }
                    taskArrayList.add(task);
                }
                else if (type.equals("E")) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm");
                    LocalDateTime dateTimeFrom = LocalDateTime.parse(parts[3], formatter);
                    LocalDateTime dateTimeTo = LocalDateTime.parse(parts[4], formatter);
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

    public static boolean isArgumentEmpty(String input) {
        if (input.isEmpty()) {
            ui.showError("Please enter a task number.");
            return true;
        }
        return false;
    }

    public static boolean isArgumentEmptyTask(String input) {
        if (input.isEmpty()) {
            ui.showError("Please enter description.");
            return true;
        }
        return false;
    }
}
