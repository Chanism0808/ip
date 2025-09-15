package dupe.tasks;

import dupe.priority.Priority;

/**
 * Represents a generic task with a description and a completion status.
 * A task can be marked as done or not done, and provides different
 * string representations for display and saving.
 */
public class Task {
    private final String description;
    private boolean isDone;
    private Priority priority;

    /**
     * Creates a new task with the specified description.
     * The task is initially marked as not done.
     *
     * @param description Description of the task.
     */
    public Task(String description) {
        assert description != null : "description should not be null";
        this.description = description;
        this.isDone = false;
        this.priority = Priority.MEDIUM; //default
    }

    /**
     * Marks this task as done.
     */
    public void markAsDone() {
        isDone = true;
    }

    /**
     * Marks this task as not done.
     */
    public void markAsNotDone() {
        isDone = false;
    }

    /**
     * Set the priority of the task.
     *
     * @param priority Priority of the task.
     */
    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Priority getPriority() {
        assert priority != null : "priority should not be null";
        return this.priority;
    }

    @Override
    public String toString() {
        if (isDone) {
            return "["+ priority + "]" + "[X] " + description;
        }
        return "["+ priority + "]" + "[ ] " + description;
    }

    /**
     * Checks if the description contains the given string.
     * @param string The string that user wants to find.
     * @return {@code true} if the description contains the given string, {@code false} otherwise.
     */
    public boolean hasString(String string) {
        return this.description.toLowerCase().contains(string.toLowerCase());
    }

    /**
     * Returns a string representation of this task in the save-file format.
     * "1 | description" if done, or "0 | description" if not done.
     *
     * @return Save file format string for this task.
     */
    public String savedListFormat() {
        if (isDone) {
            return priority + " | " + "1 | " + description;
        }
        return priority + " | " + "0 | " + description;
    }
}