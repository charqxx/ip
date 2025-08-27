import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {

    protected LocalDateTime by;

    //accept yyy-mm-dd HH:mm, print MMM dd yyy
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
        String dateOutput  = by.format(formatter);
        return "[D]" + super.toString() + " (by: " + dateOutput + ")";
    }
}
