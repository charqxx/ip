package serene;

public class ToDo extends Task {

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
