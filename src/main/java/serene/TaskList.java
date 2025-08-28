package serene;

import java.util.ArrayList;

public class TaskList {
    private ArrayList<Task> tasks;

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Creates a new empty TaskList.
     */
    public TaskList() {
        this.tasks = new ArrayList<Task>();
    }

    /**
     * Adds a task into the TaskList.
     *
     * @param task Task to be added.
     */
    public void add(Task task) {
        tasks.add(task);
    }

    /**
     * Removes the task at the index from the TaskList.
     *
     * @param index Index in the TaskList
     */
    public void remove(int index) {
        tasks.remove((index));
    }

    public Task get(int index) {
        return tasks.get(index);
    }

    public int size() {
        return tasks.size();
    }

    public TaskList find(String keyword) {
        TaskList result = new TaskList();
        for(int i = 0; i < tasks.size(); i++) {
            Task curr = tasks.get(i);
            if (curr.getDescription().contains(keyword)) {
                result.add(curr);
            }
        }
        return result;
    }

}
