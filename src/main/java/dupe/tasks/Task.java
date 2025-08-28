package dupe.tasks;

public class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    public void markAsDone() {
        isDone = true;
    }

    public void markAsNotDone() {
        isDone = false;
    }

    public String toString() {
        if(isDone){
            return "[X] " + description;
        }
        return "[ ] " + description;
    }

    public boolean hasString(String string) {
        return description.contains(string);
    }

    public String savedListFormat() {
        if(isDone){
            return "1 | " + description;
        }
        return "0 | " + description;
    }

}
