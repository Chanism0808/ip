package dupe.tasks;

import java.util.ArrayList;

/**
 * Represents a list of {@link Task} objects and provides operations
 * to manage tasks such as adding, deleting, and marking them done/undone.
 */
public class TaskList {
    private final ArrayList<Task> tasks;

    /**
     * Creates an empty {@code TaskList}.
     */
    public TaskList() {
        tasks = new ArrayList<>();
    }

    /**
     * Creates a {@code TaskList} with the given list of tasks.
     *
     * @param tasks the initial list of tasks
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Checks if the given index is valid for accessing a task in the list.
     *
     * @param taskId the index of the task (1-based)
     * @return {@code true} if the index is valid, {@code false} otherwise
     */
    public boolean isValidIndex(int taskId) {
        return taskId > 0 && taskId <= tasks.size();
    }

    /**
     * Marks the task at the given index as done.
     *
     * @param option the index of the task
     * @return the task that was marked as not done
     * @throws IllegalArgumentException if the index is invalid
     */
    public Task markTaskDone(int option) {
        if (isValidIndex(option)) {
            Task selectedTask = tasks.get(option - 1);
            selectedTask.markAsDone();
            return selectedTask;
        }
        throw new IllegalArgumentException("Invalid task ID");
    }

    /**
     * Marks the task at the given index as not done.
     *
     * @param option the index of the task
     * @return the task that was marked as not done
     * @throws IllegalArgumentException if the index is invalid
     */
    public Task markTaskUndone(int option) {
        if (isValidIndex(option)) {
            Task selectedTask = tasks.get(option - 1);
            selectedTask.markAsNotDone();
            return selectedTask;
        }
        throw new IllegalArgumentException("Invalid task ID");
    }

    /**
     * Adds a task to the task list.
     *
     * @param task the task to add
     */
    public void addTask(Task task) {
        tasks.add(task);
    }

    /**
     * Deletes the task at the given index from the list.
     *
     * @param option the index of the task (1-based)
     * @return the task that was deleted
     * @throws IndexOutOfBoundsException if the index is invalid
     */
    public Task deleteTask(int option) {
        Task selectedTask = tasks.get(option - 1);
        tasks.remove(selectedTask);
        return selectedTask;
    }

    /**
     * Checks if the keyword exists in the list of task.
     * @param keyword The string that user wants to find.
     * @return {@code true} if the keyword exists in the list of task, {@code false} otherwise.
     */
    public boolean isFound(String keyword) {
        for (Task task : tasks) {
            if (task.hasString(keyword)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns a defensive copy of the list of tasks.
     *
     * @return a copy of the task list
     */
    public ArrayList<Task> getTasks() {
        return new ArrayList<>(tasks); // defensive copy
    }

    /**
     * Checks if the task list is empty.
     *
     * @return {@code true} if the task list has no tasks, {@code false} otherwise
     */
    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return the size of the task list
     */
    public int size() {
        return tasks.size();
    }
}
