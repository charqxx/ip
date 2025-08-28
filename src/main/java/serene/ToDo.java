package serene;

public class ToDo extends Task {

    /**
     * Constructs a ToDo task with the given desciption.
     * @param description Description of Todo task.
     */
    public ToDo(String description) {
        super(description);
    }

    @Override
    public String toSaveFormat() {
        return "T" + " , " + this.getIsDone() + " , " + description;
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
