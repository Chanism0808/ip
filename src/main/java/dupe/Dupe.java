package dupe;

import java.io.IOException;
import dupe.command.Command;
import dupe.tasks.TaskList;
import dupe.parser.Parser;
import dupe.storage.Storage;
import dupe.ui.GuiUi;

public class Dupe {
    private final Storage storage;
    private TaskList tasks;
    private final GuiUi guiUi;
    private Command commands;

    public Dupe(String filePath) {
        storage = new Storage(filePath);
        guiUi = new GuiUi();
        try {
            tasks = storage.load();
        } catch (IOException e) {
            tasks = new TaskList();
        }
        commands = new Command(tasks, guiUi, storage);
    }

    public String getResponse(String input) {
        String[] parsed = Parser.parse(input);
        String command = parsed[0];
        String argument = parsed[1];

        switch (command) {
            case "bye": return guiUi.showExit();
            case "list": return commands.list();
            case "todo": return commands.todo(argument);
            case "deadline": return commands.deadline(argument);
            case "event": return commands.event(argument);
            case "mark": return commands.mark(argument, true);
            case "unmark": return commands.mark(argument, false);
            case "delete": return commands.delete(argument);
            case "find": return commands.find(argument);
            case "setPriority": return commands.setPriority(argument);
            default: return guiUi.showError("Invalid Command");
        }
    }

    public String getGreetings() {
        StringBuilder reply = new StringBuilder();
        try {
            tasks = storage.load();
            reply.append(guiUi.showGreeting())
                    .append(guiUi.showListLoaded(tasks.getTaskList()));
        } catch (IOException e) {
            reply.append(guiUi.showError("Error loading file"));
            tasks = new TaskList();
        }
        return reply.toString();
    }
}



