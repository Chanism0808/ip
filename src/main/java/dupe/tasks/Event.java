package dupe.tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Event extends Task {
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
        String fromString = this.from.format(formatter);  // e.g., Aug 08 2001 14:30
        String toString = this.to.format(formatter);      // e.g., Aug 08 2001 14:30
        return "[E]" + super.toString() + " (from: " + fromString + ", to: " + toString + ")";
    }

    @Override
    public String savedListFormat() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm");
        String fromString = this.from.format(formatter);  // e.g., Aug 08 2001 14:30
        String toString = this.to.format(formatter);      // e.g., Aug 08 2001 14:30
        return "E | " + super.savedListFormat() + " | " + fromString + " | " + toString;
    }
}
