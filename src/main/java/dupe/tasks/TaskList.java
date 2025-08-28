package dupe.tasks;

import java.util.ArrayList;

public class TaskList {
    private final ArrayList<Task> tasks;

    public TaskList() {
        tasks = new ArrayList<>();
    }
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public boolean isValidIndex(int taskId) {
        return taskId > 0 && taskId <= tasks.size();
    }

    public Task markTaskDone(int option) {
        if (isValidIndex(option)) {
            Task selectedTask = tasks.get(option - 1);
            selectedTask.markAsDone();
            return selectedTask;
        }
        throw new IllegalArgumentException("Invalid task ID");
    }

    public Task markTaskUndone(int option) {
        if (isValidIndex(option)) {
            Task selectedTask = tasks.get(option - 1);
            selectedTask.markAsNotDone();
            return selectedTask;
        }
        throw new IllegalArgumentException("Invalid task ID");
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public Task deleteTask(int option) {
        Task selectedTask = tasks.get(option-1);
        tasks.remove(selectedTask);
        return selectedTask;
    }

    public boolean isFound(String keyword) {
        for (Task task : tasks) {
            if (task.hasString(keyword)) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<Task> getTasks() {
        return new ArrayList<>(tasks); // defensive copy
    }

    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    public int size() {
        return tasks.size();
    }
}
