package tux.tasks;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class Event extends Task {
    private LocalDate from;
    private LocalDate to;

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
        return "[E]" + super.getTaskDescription() + " from: %s to: %s".formatted(this.from.format(DateTimeFormatter.ofPattern("MMM dd yyyy")), this.to.format(DateTimeFormatter.ofPattern("MMM dd yyyy")));
    }
}
