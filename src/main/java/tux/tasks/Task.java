package tux.tasks;

import java.io.Serializable;

/**
 * Represents a generic base class for Task in Tux.
 */
public class Task implements Serializable {
    protected String description;
    protected boolean isDone;

    /**
     * Takes the given description and constructs a Task, with default isDone set to false.
     * @param description
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getDescription() {
        return this.description;
    }

    public boolean getDone() {
        return this.isDone;
    }

    public void markDone() {
        this.isDone = true;
    }

    public void markUndone() {
        this.isDone = false;
    }


    public String getTaskDescription() {
        String status = this.isDone ? "[X]" : "[ ]";
        return "%s %s".formatted(status, this.description);
    }
}
