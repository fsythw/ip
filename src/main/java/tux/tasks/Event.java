package tux.tasks;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents an Event in Tux.
 */
public class Event extends Task {
    private LocalDate from;
    private LocalDate to;

    /**
     * Constructs an Event task with the given description, start date, and end date.
     *
     * @param description The description of the event task.
     * @param from The start date of the event.
     * @param to          The end date of the event.
     */
    public Event(String description, LocalDate from, LocalDate to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    public LocalDate getFrom() {
        return this.from;
    }

    public LocalDate getTo() {
        return this.to;
    }

    @Override
    public String getTaskDescription() {
        return "[E]" + super.getTaskDescription()
                + " from: %s to: %s".formatted(this.from.format(DateTimeFormatter.ofPattern("MMM dd yyyy")),
                this.to.format(DateTimeFormatter.ofPattern("MMM dd yyyy")));
    }
}
