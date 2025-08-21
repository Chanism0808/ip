import java.util.ArrayList;
import java.util.Scanner;

public class Dupe {
    static ArrayList<Task>  taskArrayList = new ArrayList<>();

    public static void main(String[] args) {
        String greetings = "____________________\n"
                + "Hello! I'm Dupe";
        System.out.println(greetings);
        query();

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
                    mark(taskID);
                }

            } else if (command.equals("unmark")) {
                if (!isArgumentEmpty(argument)) {
                    int taskID = Integer.parseInt(parts[1]);
                    unmark(taskID);
                }

            } else if (command.equals("todo")) {
                if (!isArgumentEmptyTask(argument)) {
                    ToDos task = new ToDos(parts[1]);
                    taskArrayList.add(task);
                    taskOutputMsg(task);
                }

            } else if (command.equals("deadline")) {
                //String[] arguments = input.split(" ",2);
                if (!isArgumentEmptyTask(argument)) {
                    String[] subparts = argument.split("/by ", 2);
                    String description = subparts[0];
                    String deadline = subparts.length > 1 ? subparts[1] : "";
                    if (!deadline.isEmpty()) {
                        Deadlines task  = new Deadlines(description, deadline);
                        taskArrayList.add(task);
                        taskOutputMsg(task);
                    } else {
                        System.out.println("Please enter a valid deadline for the task | Format: deadline description /by deadline.");
                    }

                }

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
                            Events task = new Events(description, from, to);
                            taskArrayList.add(task);
                            taskOutputMsg(task);
                        }
                        System.out.println("Please enter a valid datetime for the task | Format: event description /from datetime /to datetime.");
                    } else{
                        System.out.println("Please enter a valid datetime for the task | Format: event description /from datetime /to datetime.");
                    }
                }
            } else if (command.equals("delete")) {
                if (!isArgumentEmpty(argument)) {
                    deleteTask(Integer.parseInt(argument));
                }
            }
            else {
                System.out.println("____________________\n"
                        + "Invalid Command\n"
                        + "____________________");
            }
        }
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
