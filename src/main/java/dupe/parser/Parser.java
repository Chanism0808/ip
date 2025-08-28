package dupe.parser;

import dupe.tasks.Task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Provides utility methods for parsing user input and task-related strings.
 * <p>
 * Supports splitting commands and arguments, parsing integers, validating task indices,
 * and converting strings to {@link LocalDateTime} objects in different formats.
 */
public class Parser {

    /**
     * Splits the input string into a command and its argument, separated by the first space.
     *
     * @param input The user input string.
     * @return An array of two strings: { command, argument }.
     */
    public static String[] parse(String input) {
        String[] parts = input.split(" ", 2);
        String command = parts[0];
        String argument = parts.length > 1 ? parts[1] : "";
        return new String[] { command, argument };
    }

    /**
     * Splits the input string into command and argument by the delimiter " /by ".
     *
     * @param input The user input string.
     * @return An array of two strings: { command, argument }.
     */
    public static String[] parseBy(String input) {
        String[] parts = input.split(" /by ", 2);
        String command = parts[0];
        String argument = parts.length > 1 ? parts[1] : "";
        return new String[] { command, argument };
    }

    /**
     * Splits the input string into command and argument by the delimiter " /from ".
     *
     * @param input The user input string.
     * @return An array of two strings: { command, argument }.
     */
    public static String[] parseFrom(String input) {
        String[] parts = input.split(" /from ", 2);
        String command = parts[0];
        String argument = parts.length > 1 ? parts[1] : "";
        return new String[] { command, argument };
    }

    /**
     * Splits the input string into command and argument by the delimiter " /to ".
     *
     * @param input The user input string.
     * @return An array of two strings: { command, argument }.
     */
    public static String[] parseTo(String input) {
        String[] parts = input.split(" /to ", 2);
        String command = parts[0];
        String argument = parts.length > 1 ? parts[1] : "";
        return new String[] { command, argument };
    }

    /**
     * Parses an integer from a string.
     *
     * @param input The string to parse.
     * @return The integer value, or -1 if invalid.
     */
    public static int parseInt(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /**
     * Checks if the given task index is valid within the provided task list.
     *
     * @param taskId The 1-based task index.
     * @param tasks  The list of tasks.
     * @return {@code true} if the index is valid, {@code false} otherwise.
     */
    public static boolean isValidIndex(int taskId, ArrayList<Task> tasks) {
        return taskId > 0 && taskId <= tasks.size();
    }

    /**
     * Parses a string representing a deadline into a {@link LocalDateTime} object.
     * <p>
     * The expected format is "dd-MM-yyyy HH:mm".
     *
     * @param deadline The deadline string.
     * @return The parsed {@link LocalDateTime}.
     */
    public static LocalDateTime parseDateTime(String deadline) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        return LocalDateTime.parse(deadline, formatter);
    }

    /**
     * Parses a string from a saved file into a {@link LocalDateTime} object.
     * <p>
     * The expected format is "MMM dd yyyy HH:mm", e.g., "Aug 08 2001 14:30".
     *
     * @param dateTime The string from the saved file.
     * @return The parsed {@link LocalDateTime}.
     */
    public static LocalDateTime parseDateTimeFile(String dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm");
        return LocalDateTime.parse(dateTime, formatter);
    }
}
