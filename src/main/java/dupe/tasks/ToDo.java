package dupe.tasks;

/**
 * Represents a to-do task that only has a description.
 */
public class ToDo extends Task {
    /**
     * Creates a new to-do task with the given description.
     *
     * @param description Description of the task.
     */
    public ToDo(String description) {
        super(description);
    }

    /**
     * Returns the string representation of this to-do task.
     * Format: [T] followed by the parent string representation.
     *
     * @return Formatted string representation of this to-do task.
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    /**
     * Returns the string representation of this to-do task
     * in the format used for saving to a file.
     *
     * @return Formatted save string for this to-do task.
     */
    @Override
    public String savedListFormat() {
        return "T | " + super.savedListFormat();
    }
}
