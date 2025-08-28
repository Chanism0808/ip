package dupe.tasks;

public class ToDo extends Task{
    /**
     * Creates a new to-do task with the given description
     *
     * @param description Description of the task.
     */
    public ToDo(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    @Override
    public String savedListFormat() {
        return "T | " + super.savedListFormat();
    }
}