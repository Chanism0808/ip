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

    public static void main(String[] args) {
        String greetings = "____________________\n"
                + "Hello! I'm Dupe";
        System.out.println(greetings);
        query();
        loadList();

        Scanner sc = new Scanner(System.in);
        while (sc.hasNextLine()) {
            String input = sc.nextLine();
            String[] parts = input.split(" ", 2); // split into at most 2 parts
            String command = parts[0];
            String argument = parts.length > 1 ? parts[1] : "";

            if (command.equals("bye")) {
                exit();
                break;

            } else if (command.equals("list")) {
                listTasks();

            } else if (command.equals("mark")) {
                if (!isArgumentEmpty(argument)) { //if it is not empty the whole statement is true
                    int taskID = Integer.parseInt(parts[1]);
                    if (isInRange(taskID)) {
                        mark(taskID);
                    }
                }
                saveList();

            } else if (command.equals("unmark")) {
                if (!isArgumentEmpty(argument)) {
                    int taskID = Integer.parseInt(parts[1]);
                    if (isInRange(taskID)) {
                        unmark(taskID);
                    }
                }
                saveList();

            } else if (command.equals("todo")) {
                if (!isArgumentEmptyTask(argument)) {
                    ToDos task = new ToDos(parts[1]);
                    taskArrayList.add(task);
                    taskOutputMsg(task);
                }
                saveList();

            } else if (command.equals("deadline")) {
                //String[] arguments = input.split(" ",2);
                if (!isArgumentEmptyTask(argument)) {
                    String[] subparts = argument.split("/by ", 2);
                    String description = subparts[0];
                    String deadline = subparts.length > 1 ? subparts[1] : "";
                    if (!deadline.isEmpty()) {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
                        LocalDateTime dateTime = LocalDateTime.parse(deadline, formatter);
                        Deadlines task  = new Deadlines(description, dateTime);
                        taskArrayList.add(task);
                        taskOutputMsg(task);
                    } else {
                        System.out.println("Please enter a valid deadline for the task | Format: deadline description /by deadline.");
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
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
                            LocalDateTime dateTimeFrom = LocalDateTime.parse(from, formatter);
                            LocalDateTime dateTimeTo = LocalDateTime.parse(to, formatter);
                            Events task = new Events(description, dateTimeFrom, dateTimeTo);
                            taskArrayList.add(task);
                            taskOutputMsg(task);
                        }
                        System.out.println("Please enter a valid datetime for the task | Format: event description /from datetime /to datetime.");
                    } else{
                        System.out.println("Please enter a valid datetime for the task | Format: event description /from datetime /to datetime.");
                    }
                }
                saveList();

            } else if (command.equals("delete")) {
                if (!isArgumentEmpty(argument)) {
                    int taskID = Integer.parseInt(argument);
                    if (isInRange(taskID)) {
                        deleteTask(taskID);
                    }
                }
                saveList();
            }
            else {
                System.out.println("____________________\n"
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
                System.out.println("File not found. Created new file at: " + filePath);
                return;
            }
            FileWriter fw = new FileWriter("data/tasks.txt"); // 'true' = append mode
            for (Task task : taskArrayList) {
                fw.write(task.savedListFormat() + "\n");
            }
            fw.close();
        } catch (IOException e) {
            System.out.println("An error occurred while saving tasks: " + e.getMessage());
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
                System.out.println("File not found. Created new file at: " + filePath);
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
            System.out.println("____________________\n"
                    + "Loaded " + taskArrayList.size() + " tasks from file."
                    + "\n____________________");

        } catch (IOException e) {
            System.out.println("An error occurred while loading tasks: " + e.getMessage());
        }

    }

    public static boolean isInRange(int taskID){
        if (taskID <= 0 || taskID > taskArrayList.size()) {
            System.out.println("Please enter a valid task ID");
            return false;
        }
        return true;
    }

    public static boolean isArgumentEmpty(String input) {
        if (input.isEmpty()) {
            System.out.println("Please enter a task number.");
            return true;
        }
        return false;
    }

    public static boolean isArgumentEmptyTask(String input) {
        if (input.isEmpty()) {
            System.out.println("Please enter description.");
            return true;
        }
        return false;
    }

    public static void taskOutputMsg(Task task) {
        System.out.println("____________________\n"
                + "Got it. I've added this task:\n"
                + task
                + "\nNow you have " + taskArrayList.size() + " tasks in the list."
                + "\n____________________");
    }

    public static void query() {
        System.out.println("What can I do for you?\n"
                + "____________________");
    }

    public static void exit() {
        System.out.println("____________________\n"
                        + "Goodbye! Hope to see you again soon!\n"
                        + "____________________");
    }

    public static void listTasks() {
        System.out.println("____________________\n"
                        +"Here are the list of tasks:\n");
        for (int i = 0; i < taskArrayList.size(); i++) {
            System.out.println(i+1 + "." + taskArrayList.get(i));
        }
        System.out.println("____________________\n");
    }

    public static void mark(int option) {
        System.out.println("Nice! I've marked this task as done:");
        Task selectedTask = taskArrayList.get(option-1);
        selectedTask.markAsDone();
        System.out.println(selectedTask + "\n____________________");
    }

    public static void unmark(int option) {
        System.out.println("OK, I've marked this task as not done yet:");
        Task selectedTask = taskArrayList.get(option-1);
        selectedTask.markAsNotDone();
        System.out.println(selectedTask + "\n____________________");
    }

    public static void deleteTask(int option) {
        Task selectedTask = taskArrayList.get(option-1);
        taskArrayList.remove(option-1);
        System.out.println("____________________\n"
                + selectedTask.toString()
                + "\nNow you have " + taskArrayList.size() + " tasks in the list."
                + "\n____________________");
    }
}
