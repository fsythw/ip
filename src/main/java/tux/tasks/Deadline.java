package tux.tasks;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {
    private LocalDate by;

    public Deadline(String description, LocalDate by) {
        super(description);
        this.by = by;
    }

    public LocalDate getBy() {
        return this.by;
    }

    @Override
    public String getTaskDescription() {
        return "[D]" + super.getTaskDescription() + " (by: %s)".formatted(this.by.format(DateTimeFormatter.ofPattern("MMM dd yyyy")));
    }
}
