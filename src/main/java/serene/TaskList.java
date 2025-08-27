package serene;

import java.util.ArrayList;

public class TaskList {
    private ArrayList<Task> tasks;

    public TaskList(Task task) {
        tasks = new ArrayList<>();
        tasks.add(task);
    }

    public void add(Task task) {
        tasks.add(task);
    }

    public void remove(int index) {
        tasks.remove((index));
    }

}
