package dupe.ui;

import java.util.ArrayList;
import dupe.tasks.Task;

/**
 * Handles user interface operations for GUI mode.
 * Instead of printing to the console, this class returns messages as strings
 * so that they can be displayed inside the JavaFX application.
 */
public class GuiUi {

    /**
     * Return a greeting message when the program starts.
     */
    public String showGreeting() {
        return "____________________\n"
                + "Hello! I'm Dupe\n"
                + "What can I do for you?\n"
                + "____________________";
    }

    /**
     * Return a goodbye message when the program exits.
     */
    public String showExit() {
        new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {

            }
            javafx.application.Platform.exit();
        }).start();
        return "____________________\n"
                + "Goodbye! Hope to see you again soon!\n"
                + "____________________";
    }

    /**
     * Return an error message to the console.
     *
     * @param message The error message to display.
     */
    public String showError(String message) {
        return "Error!! " + message;
    }

    /**
     * Return a message showing that a task has been added.
     *
     * @param task The task that was added.
     * @param taskCount The current number of tasks in the list.
     */
    public String showTaskAdded(Task task, int taskCount) {
        return "____________________\n"
                + "Got it. I've added this task:\n"
                + task
                + "\nNow you have " + taskCount + " tasks in the list."
                + "\n____________________";
    }

    /**
     * Return the current list of tasks.
     *
     * @param tasks The list of tasks to display.
     */
    public String showTaskList(ArrayList<Task> tasks) {
        if (tasks.isEmpty()) {
            return "____________________\nYou have no tasks in your list.\n____________________";
        }
        StringBuilder sb = new StringBuilder("____________________\nHere are the list of tasks:\n\n");
        for (int i = 0; i < tasks.size(); i++) {
            sb.append((i + 1)).append(". ").append(tasks.get(i)).append("\n");
        }
        sb.append("____________________");
        return sb.toString();
    }

    /**
     * Return the list of tasks that has the keyword.
     * @param keyword The string that user wants to find.
     * @param tasks The list of tasks to display.
     */
    public String printFoundTasks(String keyword, ArrayList<Task> tasks) {
        StringBuilder sb = new StringBuilder("____________________\nHere are the matching tasks in your list:\n");
        int x = 1;
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).hasString(keyword)) {
                sb.append(x).append(". ").append(tasks.get(i)).append("\n");
                x++;
            }
        }
        if (x == 1) {
            return "Sorry, no tasks match the keyword: \"" + keyword + "\"";
        }
        sb.append("____________________");
        return sb.toString();
    }

    /**
     * Return a message indicating that a task has been marked as done.
     *
     * @param task The task that was marked done.
     */
    public String showTaskMarked(Task task) {
        return "Nice! I've marked this task as done:\n"
                + task
                + "\n____________________";
    }

    /**
     * Prints a message indicating that a task has been marked as not done.
     *
     * @param task The task that was marked not done.
     */
    public String showTaskUnmarked(Task task) {
        return "OK, I've marked this task as not done yet:\n"
                + task
                + "\n____________________";
    }

    /**
     * Return a confirmation message when the priority of a task is set.
     *
     * @param task the {@link Task} whose priority was updated
     */
    public String showPrioritySet(Task task) {
        return "OK, I've set this task as [" + task.getPriority() + "]:\n"
                + task
                + "\n____________________";
    }

    /**
     * Return a message showing that a task has been deleted.
     *
     * @param task The task that was deleted.
     * @param taskCount The current number of tasks remaining in the list.
     */
    public String showTaskDeleted(Task task, int taskCount) {
        return "____________________\n"
                + task
                + "\nNow you have " + taskCount + " tasks in the list."
                + "\n____________________";
    }

    /**
     * Return a message indicating that a list of tasks has been loaded from a file.
     *
     * @param tasks The list of tasks that were loaded.
     */
    public String showListLoaded(ArrayList<Task> tasks) {
        return "____________________\n"
                + "Loaded " + tasks.size() + " tasks from file.";
    }

}
