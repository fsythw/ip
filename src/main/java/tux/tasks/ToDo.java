package tux.tasks;

/**
 * Represents a ToDo task in Tux.
 */
public class ToDo extends Task {

    /**
     * Creates a ToDo object with the given description.
     * @param description String description of the task.
     */
    public ToDo(String description) {
        super(description);
    }

    @Override
    public String getTaskDescription() {
        return "[T]" + super.getTaskDescription();
    }
}
