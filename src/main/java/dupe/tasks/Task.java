package dupe.tasks;

/**
 * Represents a generic task with a description and a completion status.
 * A task can be marked as done or not done, and provides different
 * string representations for display and saving.
 */
public class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Creates a new task with the specified description.
     * The task is initially marked as not done.
     *
     * @param description Description of the task.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns the status icon of this task.
     * "X" if the task is done, or a space if not done.
     *
     * @return Status icon of the task.
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
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

    @Override
    public String toString() {
        if (isDone) {
            return "[X] " + description;
        }
        return "[ ] " + description;
    }

    /**
     * Checks if the description contains the given string.
     * @param string The string that user wants to find.
     * @return {@code true} if the description contains the given string, {@code false} otherwise.
     */
    public boolean hasString(String string) {
        return description.contains(string);
    }

    /**
     * Returns a string representation of this task in the save-file format.
     * "1 | description" if done, or "0 | description" if not done.
     *
     * @return Save file format string for this task.
     */
    public String savedListFormat() {
        if (isDone) {
            return "1 | " + description;
        }
        return "0 | " + description;
    }
}
