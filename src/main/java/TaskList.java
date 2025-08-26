import java.util.ArrayList;

public class TaskList {
    private final ArrayList<Task> tasks;
    private final Ui ui;

    public TaskList(Ui ui,  ArrayList<Task> tasks) {
        this.tasks = tasks;
        this.ui = ui;
    }

    private boolean isValidIndex(int taskId) {
        return taskId > 0 || taskId < tasks.size();
    }

    public void markTaskDone(int option) {
        if (isValidIndex(option)) {
            Task selectedTask = tasks.get(option-1);
            selectedTask.markAsDone();
            ui.showTaskMarked(selectedTask);
            return;
        }
        ui.showError("Please enter a valid task ID");
        return;
    }

    public void markTaskUndone(int option) {
        if (isValidIndex(option)) {
            Task selectedTask = tasks.get(option-1);
            selectedTask.markAsNotDone();
            ui.showTaskUnmarked(selectedTask);
            return;
        }
        ui.showError("Please enter a valid task ID");
        return;
    }

    public void addTask(Task task) {
        tasks.add(task);
        ui.showTaskAdded(task,tasks.size());
    }

    public void deleteTask(int option) {
        if (isValidIndex(option)) {
            Task selectedTask = tasks.get(option-1);
            tasks.remove(selectedTask);
            ui.showTaskDeleted(selectedTask, tasks.size());
        }
        ui.showError("Please enter a valid task ID");
        return;
    }

    public void listTasks() {
        if (tasks.isEmpty()) {
            ui.showError("You have no tasks in your list.");
            return;
        }
        ui.showTaskList(tasks);
    }





}
