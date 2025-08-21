import java.util.ArrayList;
import java.util.Scanner;

public class Dupe {
    static ArrayList<Task>  taskArrayList = new ArrayList<>();

    public static void main(String[] args) {
        //initialising Task
        initialiseTask();
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
                String[] parts = input.split(" ");
                if(parts.length > 1) {
                    int taskID = Integer.parseInt(parts[1]);
                    mark(taskID);
                }
            } else if (input.startsWith("unmark")) {
                String[] parts = input.split(" ");
                if(parts.length > 1) {
                    int taskID = Integer.parseInt(parts[1]);
                    unmark(taskID);
                }
            } else {
                System.out.println("____________________\n"
                        + input
                        + "\n____________________");
            }
        }

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

    public static void initialiseTask() {

        Task t1 = new Task("read book");
        Task t2 = new Task("return book");
        Task t3 = new Task("buy book");
        taskArrayList.add(t1);
        taskArrayList.add(t2);
        taskArrayList.add(t3);
        t1.markAsDone();
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
