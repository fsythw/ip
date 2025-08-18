public class Deadline extends Task {
    private String by;

    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    @Override
    public String getTaskDescription() {
        return "[D]" + super.getTaskDescription() + " (by: %s)".formatted(this.by);
    }
}
