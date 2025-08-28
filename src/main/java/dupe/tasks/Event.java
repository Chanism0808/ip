package dupe.tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents an Event task that has a description,
 * a start time, and an end time.
 * Inherits from {@link Task}.
 */
public class Event extends Task {
    private LocalDateTime from;
    private LocalDateTime to;

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
