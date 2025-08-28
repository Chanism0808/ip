package dupe.tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task that has a specific deadline.
 * A deadline task stores the due date and time,
 * and provides string representations for display and saving.
 */
public class Deadline extends Task {
    private LocalDateTime deadline;

    /**
     * Creates a new deadline task with the given description and due date/time.
     *
     * @param description Description of the task.
     * @param deadline Date and time by which the task must be completed.
     */
    public Deadline(String description, LocalDateTime deadline) {
        super(description);
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm");
        String deadlineString = this.deadline.format(formatter); // e.g., Aug 08 2001 14:30
        return "[D]" + super.toString() + " (by: " + deadlineString + ")";
    }

    @Override
    public String savedListFormat() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm");
        String deadlineString = this.deadline.format(formatter); // e.g., Aug 08 2001 14:30
        return "D | " + super.savedListFormat() + " | " + deadlineString;
    }
}
