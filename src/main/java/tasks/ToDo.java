package tasks;

public class ToDo extends Task {
    public ToDo(String description) {
        super(description);
    }

    @Override
    public String getTaskDescription() {
        return "[T]" + super.getTaskDescription();
    }
}
