package dupe.ui;

import java.util.ArrayList;
import dupe.tasks.Task;

/**
 * Handles user interface operations such as displaying messages
 * to the console, showing tasks, and printing errors.
 */
public class Ui {

    /**
     * Prints a greeting message when the program starts.
     */
    public void showGreeting() {
        System.out.println("____________________\n"
                + "Hello! I'm Dupe\n"
                + "What can I do for you?\n"
                + "____________________");
    }

    /**
     * Prints a goodbye message when the program exits.
     */
    public void showExit() {
        System.out.println("____________________\n"
                + "Goodbye! Hope to see you again soon!\n"
                + "____________________");
    }

    /**
     * Prints an error message to the console.
     *
     * @param message The error message to display.
     */
    public void showError(String message) {
        System.out.println("Error!! " + message);
    }

    /**
     * Prints a message showing that a task has been added.
     *
     * @param task The task that was added.
     * @param taskCount The current number of tasks in the list.
     */
    public void showTaskAdded(Task task, int taskCount) {
        System.out.println("____________________\n"
                + "Got it. I've added this task:\n"
                + task
                + "\nNow you have " + taskCount + " tasks in the list."
                + "\n____________________");
    }

    /**
     * Prints the current list of tasks.
     *
     * @param tasks The list of tasks to display.
     */
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

    /**
     * Prints a message indicating that a task has been marked as done.
     *
     * @param task The task that was marked done.
     */
    public void showTaskMarked(Task task) {
        System.out.println("Nice! I've marked this task as done:\n"
                + task
                + "\n____________________");
    }

    /**
     * Prints a message indicating that a task has been marked as not done.
     *
     * @param task The task that was marked not done.
     */
    public void showTaskUnmarked(Task task) {
        System.out.println("OK, I've marked this task as not done yet:\n"
                + task
                + "\n____________________");
    }

    /**
     * Prints a message showing that a task has been deleted.
     *
     * @param task The task that was deleted.
     * @param taskCount The current number of tasks remaining in the list.
     */
    public void showTaskDeleted(Task task, int taskCount) {
        System.out.println("____________________\n"
                + task
                + "\nNow you have " + taskCount + " tasks in the list."
                + "\n____________________");
    }

    /**
     * Prints a message indicating that a list of tasks has been loaded from a file.
     *
     * @param tasks The list of tasks that were loaded.
     */
    public void showListLoaded(ArrayList<Task> tasks) {
        System.out.println("____________________\n"
                + "Loaded " + tasks.size() + " tasks from file.");
    }
}
