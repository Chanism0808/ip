package dupe.tasks;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task{
    private LocalDateTime deadline;

    public Deadline(String description, LocalDateTime deadline){
        super(description);
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm");
        String deadlineString = this.deadline.format(formatter); // Aug 08 2001 14:30
        return "[D]" + super.toString() + " (by: " + deadlineString + ")";
    }

    @Override
    public String savedListFormat() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm");
        String deadlineString = this.deadline.format(formatter); // Aug 08 2001 14:30
        return "D | " + super.savedListFormat() + " | " + deadlineString;
    }
}
