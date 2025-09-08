package dupe.ui;

import java.util.ArrayList;
import dupe.tasks.Task;

/**
 * Handles user interface operations for GUI mode.
 * Instead of printing to the console, this class returns messages as strings
 * so that they can be displayed inside the JavaFX application.
 */
public class GuiUi {

    public String showGreeting() {
        return "____________________\n"
                + "Hello! I'm Dupe\n"
                + "What can I do for you?\n"
                + "____________________";
    }

    public String showExit() {
        return "____________________\n"
                + "Goodbye! Hope to see you again soon!\n"
                + "____________________";
    }

    public String showError(String message) {
        return "Error!! " + message;
    }

    public String showTaskAdded(Task task, int taskCount) {
        return "____________________\n"
                + "Got it. I've added this task:\n"
                + task
                + "\nNow you have " + taskCount + " tasks in the list."
                + "\n____________________";
    }

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

    public String showTaskMarked(Task task) {
        return "Nice! I've marked this task as done:\n"
                + task
                + "\n____________________";
    }

    public String showTaskUnmarked(Task task) {
        return "OK, I've marked this task as not done yet:\n"
                + task
                + "\n____________________";
    }

    public String showTaskDeleted(Task task, int taskCount) {
        return "____________________\n"
                + task
                + "\nNow you have " + taskCount + " tasks in the list."
                + "\n____________________";
    }

    public String showListLoaded(ArrayList<Task> tasks) {
        return "____________________\n"
                + "Loaded " + tasks.size() + " tasks from file.";
    }

    /**
     * Displays a confirmation message when the priority of a task is set.
     *
     * @param task the {@link Task} whose priority was updated
     */
    public String showPrioritySet(Task task) {
        return "OK, I've set this task as [" + task.getPriority() + "]:\n"
                + task
                + "\n____________________";
    }
}
