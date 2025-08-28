package dupe.tasks;

public class ToDo extends Task {
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
