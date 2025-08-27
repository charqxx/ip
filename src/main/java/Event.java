import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Event extends Task{
    protected LocalDateTime from;
    protected LocalDateTime to;

    public Event(String description, String from, String to) {
        super(description);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        this.from = LocalDateTime.parse(from, formatter);
        this.to = LocalDateTime.parse(to, formatter);
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
        String fromOutput  = from.format(formatter);
        String toOutput  = to.format(formatter);
        return "[E]" + super.toString() + " (from: " + fromOutput + " to: " + toOutput + ")";
    }
}
