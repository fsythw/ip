package tux.tasks;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents a Deadline task in Tux.
 */
public class Deadline extends Task {
    private LocalDate by;

    /**
     * Constructs a Deadline task with the given description and deadline.
     *
     * @param description The description of the Deadline task.
     * @param by          The deadline.
     */
    public Deadline(String description, LocalDate by) {
        super(description);
        this.by = by;
    }

    public LocalDate getBy() {
        return this.by;
    }

    @Override
    public String getTaskDescription() {
        return "[D]" + super.getTaskDescription()
                + " (by: %s)".formatted(this.by.format(DateTimeFormatter.ofPattern("MMM dd yyyy")));
    }
}
