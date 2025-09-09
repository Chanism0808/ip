package dupe.tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents an Event task that has a description,
 * a start time, and an end time.
 * Inherits from {@link Task}.
 */
public class Event extends Task {
    private final LocalDateTime from;
    private final LocalDateTime to;

    /**
     * Creates a new Event with the given description,
     * start date and time, and end date and time.
     *
     * @param description The description of the event.
     * @param from The starting date and time of the event.
     * @param to The ending date and time of the event.
     */
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
