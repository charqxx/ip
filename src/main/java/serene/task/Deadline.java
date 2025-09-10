package serene.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {

    protected LocalDateTime by;

    /**
     * Creates new Deadline task with specified description and deadline
     *
     * @param description Description of Deadline task.
     * @param by Due date and time of the task, in the format "yyyy-MM-dd HH:mm".
     */
    public Deadline(String description, String by) {
        super(description);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        this.by = LocalDateTime.parse(by, formatter);
    }

    @Override
    public String toSaveFormat() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String byOutput = by.format(formatter);
        return "D" + " , " + this.getIsDone() + " , " + description + " , " + byOutput;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm");
        String dateOutput = by.format(formatter);
        return "[D]" + super.toString() + " (by: " + dateOutput + ")";
    }
}
