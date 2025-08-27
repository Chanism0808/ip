package dupe.tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Event extends Task{
    private LocalDateTime from;
    private LocalDateTime to;

    public Event(String description, LocalDateTime from, LocalDateTime to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm");
        String deadlineFromString = this.from.format(formatter); // Aug 08 2001 14:30
        String deadlineToString = this.to.format(formatter); // Aug 08 2001 14:30
        return "[E]" + super.toString() + " (from: " + deadlineFromString + ", to: " + deadlineToString + ")";
    }

    @Override
    public String savedListFormat() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm");
        String deadlineFromString = this.from.format(formatter); // Aug 08 2001 14:30
        String deadlineToString = this.to.format(formatter); // Aug 08 2001 14:30
        return "E | " + super.savedListFormat() + " | " + deadlineFromString + " | " + deadlineToString;
    }
}