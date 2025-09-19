package serene.task;

import serene.exception.SereneException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TaskList {
    private ArrayList<Task> tasks;

    /**
     * Creates a new empty TaskList.
     */
    public TaskList() {
        this.tasks = new ArrayList<Task>();
    }

    /**
     * Creates a new TaskList containing the given tasks.
     *
     * @param tasks One or more Task objects to initialize the list.
     */

    public TaskList(Task ...tasks) {
        this.tasks = new ArrayList<>(List.of(tasks));
    }

    /**
     * Adds a task to the TaskList.
     *
     * @param task The Task to be added.
     * @throws SereneException If the task already exists in the list.
     */
    public void add(Task task) throws SereneException{
        if (tasks.contains(task)) {
            throw new SereneException("This task already exists!");
        }
        tasks.add(task);
    }

    /**
     * Adds a task to the TaskList without checking for duplicates.
     *
     * @param task The Task to be added.
     */
    public void addWithoutCheck(Task task) {
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

    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    /**
     * Searches for tasks whose descriptions contain any of the given keywords.
     *
     * @param keywords One or more keywords to search for.
     * @return A new TaskList containing all matching tasks.
     */
    public TaskList find(String ...keywords) {
            TaskList result = new TaskList();
            tasks.stream()
                    .filter(task -> Arrays.stream(keywords)
                            .anyMatch(keyword -> task.getDescription().contains(keyword)))
                    .forEach(result::addWithoutCheck);
            return result;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        for (int i = 0; i < tasks.size(); i++) {
            string.append((i + 1)).append(". ").append(tasks.get(i)).append("\n");
        }
        return string.toString();
    }
}
