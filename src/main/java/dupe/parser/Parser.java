package dupe.parser;

import dupe.tasks.Task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Parser {

    public static String[] parse(String input) {
        String[] parts = input.split(" ", 2);
        String command = parts[0];
        String argument = parts.length > 1 ? parts[1] : "";
        return new String[] { command, argument };
    }

    public static String[] parseBy(String input) {
        String[] parts = input.split(" /by ", 2);
        String command = parts[0];
        String argument = parts.length > 1 ? parts[1] : "";
        return new String[] { command, argument };
    }

    public static String[] parseFrom(String input) {
        String[] parts = input.split(" /from ", 2);
        String command = parts[0];
        String argument = parts.length > 1 ? parts[1] : "";
        return new String[] { command, argument };
    }

    public static String[] parseTo(String input) {
        String[] parts = input.split(" /to ", 2);
        String command = parts[0];
        String argument = parts.length > 1 ? parts[1] : "";
        return new String[] { command, argument };
    }

    public static int parseInt(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public static boolean isValidIndex(int taskId, ArrayList<Task> tasks) {
        return taskId > 0 && taskId <= tasks.size();
    }

    public static LocalDateTime parseDateTime(String deadline) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        return LocalDateTime.parse(deadline, formatter);
    }

    public static LocalDateTime parseDateTimeFile(String dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm");
        return LocalDateTime.parse(dateTime, formatter);
    }
}
