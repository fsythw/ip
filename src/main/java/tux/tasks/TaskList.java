package tux.tasks;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a list of tasks in Tux.
 */
public class TaskList {
    private final List<Task> tasks;

    /**
     * Creates a TaskList object with an empty list.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Alternative constructor for TaskList with a list of tasks.
     * @param initial List of tasks to be added.
     */
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
        return tasks.remove(index);
    }

    public int size() {
        return tasks.size();
    }

    public List<Task> asList() {
        return tasks;
    }

    public Task get(int index) {
        return this.tasks.get(index);
    }


}
