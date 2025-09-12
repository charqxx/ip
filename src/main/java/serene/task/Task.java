package serene.task;

public abstract class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Constructs a new task with the given description.
     * Task is initially marked as not done.
     * Used by subclasses since it is an abstract class.
     *
     * @param description Description of task.
     */
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

    /**
     * Returns the task in a string representation format suitable for saving into save file.
     * @return Task in string representation.
     */
    public abstract String toSaveFormat();


    public String getDescription() {
        return description;
    }

    /**
     * Updates the task as done.
     */
    public void mark() {
        isDone = true;
    }

    /**
     * Updates the task as not done.
     */
    public void unmark() {
        isDone = false;
    }

    public abstract boolean checkDuplicate(Task AddedTask);

    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) { return true;}
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        return ((Task) obj).getDescription().equals(this.description);
    }
}

