package dupe.tasks;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeadlineTest {

    @Test
    public void testToString_notDone() {
        LocalDateTime deadline = LocalDateTime.of(2025, 8, 28, 14, 30);
        Deadline task = new Deadline("submit report", deadline);

        // format date the same way as Deadline.toString()
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm");
        String DeadlineString = deadline.format(formatter);

        assertEquals("[D][ ] submit report (by: " + DeadlineString + ")", task.toString());
    }

    @Test
    public void testToString_done() {
        LocalDateTime deadline = LocalDateTime.of(2025, 8, 28, 14, 30);
        Deadline task = new Deadline("submit report", deadline);
        task.markAsDone();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm");
        String DeadlineString = deadline.format(formatter);

        assertEquals("[D][X] submit report (by: " + DeadlineString + ")", task.toString());
    }

    @Test
    public void testSavedListFormat_notDone() {
        LocalDateTime deadline = LocalDateTime.of(2025, 8, 28, 14, 30);
        Deadline task = new Deadline("submit report", deadline);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm");
        String DeadlineString = deadline.format(formatter);

        assertEquals("D | 0 | submit report | " + DeadlineString, task.savedListFormat());
    }

    @Test
    public void testSavedListFormat_done() {
        LocalDateTime deadline = LocalDateTime.of(2025, 8, 28, 14, 30);
        Deadline task = new Deadline("submit report", deadline);
        task.markAsDone();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm");
        String DeadlineString = deadline.format(formatter);

        assertEquals("D | 1 | submit report | " + DeadlineString, task.savedListFormat());
    }
}
