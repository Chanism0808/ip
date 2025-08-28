package dupe.ui;

import java.util.ArrayList;
import dupe.tasks.Task;

public class Ui {
    // Print greeting when the program starts
    public void showGreeting() {
        System.out.println("____________________\n"
                + "Hello! I'm Dupe\n"
                + "What can I do for you?\n"
                + "____________________");
    }

    // Print goodbye message
    public void showExit() {
        System.out.println("____________________\n"
                + "Goodbye! Hope to see you again soon!\n"
                + "____________________");
    }

    // Print error message
    public void showError(String message) {
        System.out.println("Error!! " + message);
    }

    public void showTaskAdded(Task task, int taskCount) {
        System.out.println("____________________\n"
                + "Got it. I've added this task:\n"
                + task
                + "\nNow you have " + taskCount + " tasks in the list."
                + "\n____________________");
    }

    // Print list of tasks
    public void showTaskList(ArrayList<Task> tasks) {
        System.out.println("____________________\nHere are the list of tasks:\n");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i));
        }
        System.out.println("____________________");
    }

    public void printFoundTasks(String keyword, ArrayList<Task> tasks) {
        System.out.println("____________________\nHere are the matching tasks in your list:");
        int x = 1;
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).hasString(keyword)) {
                System.out.println(x + "." + tasks.get(i));
                x += 1;
            }
        }
    }

    // Print task marked as done
    public void showTaskMarked(Task task) {
        System.out.println("Nice! I've marked this task as done:\n"
                + task
                + "\n____________________");
    }

    // Print task marked as not done
    public void showTaskUnmarked(Task task) {
        System.out.println("OK, I've marked this task as not done yet:\n"
                + task
                + "\n____________________");
    }

    // Print task deletion
    public void showTaskDeleted(Task task, int taskCount) {
        System.out.println("____________________\n"
                + task
                + "\nNow you have " + taskCount + " tasks in the list."
                + "\n____________________");
    }

    public void showListLoaded(ArrayList<Task> tasks) {
        System.out.println("____________________\n"
                + "Loaded " + tasks.size() + " tasks from file.");
    }
}
