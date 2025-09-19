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
        return (isDone ? "✅" : "   "); // mark done task with X
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

    @Override
    public String toString() {
        return "|" + getStatusIcon() + "| " + description;
    }

    /**
     * Checks whether this task is equal to another task.
     * Two tasks are considered equal if they are of the same class and have the same description.
     *
     * @param obj The object to compare with this task.
     * @return true if the given object is a Task of the same type and has the same description, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) { return true;}
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        return ((Task) obj).getDescription().equals(this.description);
    }

    /**
     * Checks whether another task is a duplicate of this task.
     * A task is considered a duplicate if it has the same description and is of the same class.
     *
     * @param other The task to compare with this task.
     * @return true if the other task is considered a duplicate, false otherwise.
     */
    public boolean isDuplicate(Task other) {
        return this.description.equals(other.description) && this.getClass() == other.getClass();
    }

}

