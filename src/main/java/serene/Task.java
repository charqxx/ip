package serene;

public abstract class Task {
    protected String description;
    protected boolean isDone;


    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }


    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    public int getIsDone() {
        return isDone ? 1 : 0;
    }

    public abstract String toSaveFormat();


    public String getDescription() {
        return description;
    }


    public void mark() {
        isDone = true;
    }


    public void unmark() {
        isDone = false;
    }


    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }
}

