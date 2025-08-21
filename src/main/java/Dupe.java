import java.util.ArrayList;
import java.util.Scanner;

public class Dupe {
    static ArrayList<Task>  taskArrayList = new ArrayList<>();

    public static void main(String[] args) {
        String greetings = "____________________\n"
                + "Hello! I'm Dupe";
        System.out.println(greetings);
        query();

        while (true) {
            Scanner sc = new Scanner(System.in);
            String input = sc.nextLine();

            if (input.equals("bye")) {
                exit();
                break;
            } else if (input.equals("list")) {
                listTasks();
            } else if (input.startsWith("mark")) {
                String[] parts = input.split(" ",2);
                int taskID = Integer.parseInt(parts[1]);
                mark(taskID);
            } else if (input.startsWith("unmark")) {
                String[] parts = input.split(" ",2);
                int taskID = Integer.parseInt(parts[1]);
                unmark(taskID);
            } else if (input.startsWith("todo")) {
                String[] parts = input.split(" ",2);
                ToDos task = new ToDos(parts[1]);
                taskArrayList.add(task);
                taskOutputMsg(task);

            } else if (input.startsWith("deadline")) {
                String[] arguments = input.split(" ",2);
                String[] parts = arguments[1].split("by ", 2);
                String description = parts[0];
                String deadline = parts[1];
                Deadlines task  = new Deadlines(description, deadline);
                taskArrayList.add(task);
                taskOutputMsg(task);

            }
            else {
                System.out.println("____________________\n"
                        + input
                        + "\n____________________");
            }
        }
    }

    public static void taskOutputMsg(Task task) {
        System.out.println("____________________\n"
                + "Got it. I've added this task:\n"
                + task.toString()
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
        System.out.println(selectedTask+"\n____________________");
    }

    public static void unmark(int option) {
        System.out.println("OK, I've marked this task as not done yet:");
        Task selectedTask = taskArrayList.get(option-1);
        selectedTask.markAsNotDone();
        System.out.println(selectedTask+"\n____________________");
    }
}
