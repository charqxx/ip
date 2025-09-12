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

    public LocalDateTime getBy() {
        return this.by;
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

    @Override
    public boolean checkDuplicate(Task addedTask) {
        return this.getClass() == addedTask.getClass() &&
                checkSameValues((Deadline) addedTask);
    }

    public boolean checkSameValues(Deadline addedTask) {
        return this.getDescription().equals(addedTask.getDescription()) &&
                this.by == addedTask.getBy();
    }
}
