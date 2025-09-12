package serene.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Event extends Task {
    protected LocalDateTime from;
    protected LocalDateTime to;

    /**
     * Creates a new Event task with specified description, start time, and end time.
     * This task is initially marked as not done.
     *
     * @param description Description of Event task.
     * @param from Start date and time of the event, in the format "yyyy-MM-dd HH:mm".
     * @param to End date and time of the event, in the format "yyyy-MM-dd HH:mm".
     */
    public Event(String description, String from, String to) {
        super(description);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        this.from = LocalDateTime.parse(from, formatter);
        this.to = LocalDateTime.parse(to, formatter);
    }

    public LocalDateTime getFrom() {
        return this.from;
    }

    public LocalDateTime getTo() {
        return this.to;
    }

    @Override
    public String toSaveFormat() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String fromOutput = from.format(formatter);
        String toOutput = to.format(formatter);
        return "E" + " , " + this.getIsDone() + " , " + description + " , " + fromOutput + " /to " + toOutput;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm");
        String fromOutput = from.format(formatter);
        String toOutput = to.format(formatter);
        return "[E]" + super.toString() + " (from: " + fromOutput + " to: " + toOutput + ")";
    }

    @Override
    public boolean checkDuplicate(Task addedTask) {
        return this.getClass() == addedTask.getClass() &&
                checkSameValues((Event) addedTask);
    }

    public boolean checkSameValues(Event addedTask) {
        return this.getDescription().equals(addedTask.getDescription()) &&
                this.from == addedTask.getFrom() &&
                this.to == addedTask.getTo();
    }
}
