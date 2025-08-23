import java.io.Serializable;

public class Task implements Serializable {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public void markDone() {
        this.isDone = true;
    }

    public void markUndone() {
        this.isDone = false;
    }

    public String getStatus() {
        return this.isDone ? "[X]" : "[ ]";
    }
    public String getTaskDescription() {
        String status = this.isDone ? "[X]" : "[ ]";
        return "%s %s".formatted(status, this.description);
    }
}
