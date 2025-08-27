package dupe.tasks;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ToDoTest {

    @Test
    public void testToString_notDone() {
        ToDo todo = new ToDo("read book");
        assertEquals("[T][ ] read book", todo.toString());
    }

    @Test
    public void testToString_done() {
        ToDo todo = new ToDo("read book");
        todo.markAsDone();
        assertEquals("[T][X] read book", todo.toString());
    }

    @Test
    public void testSavedListFormat_notDone() {
        ToDo todo = new ToDo("read book");
        assertEquals("T | 0 | read book", todo.savedListFormat());
    }

    @Test
    public void testSavedListFormat_done() {
        ToDo todo = new ToDo("read book");
        todo.markAsDone();
        assertEquals("T | 1 | read book", todo.savedListFormat());
    }
}
