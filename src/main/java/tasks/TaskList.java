package tasks;

import java.util.ArrayList;
import java.util.List;

public class TaskList {
    private final List<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(List<Task> initial) {
        this.tasks = new ArrayList<>(initial);
    }

    public List<Task> getTasks() {
        return this.tasks;
    }

    public void add(Task t) {
        tasks.add(t);
    }
    public Task delete(int index) {
        return tasks.remove(index - 1);
    }


    public int size() {
        return tasks.size();
    }

    public List<Task> asList() {
        return tasks;
    }

    public void clear() {
        this.tasks.clear();
    }

    public void addAll(List<Task> tasks) {
        this.tasks.addAll(tasks);
    }

    public Task get(int index) {
        return this.tasks.get(index);
    }


}
