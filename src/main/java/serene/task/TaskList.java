package serene.task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    public TaskList(Task ...tasks) {
        this.tasks = new ArrayList<>(List.of(tasks));
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

    public TaskList find(String ...keywords) {
        TaskList result = new TaskList();
        tasks.stream()
                .filter(task -> Arrays.stream(keywords)
                                            .anyMatch(keyword -> task.getDescription().contains(keyword)))
                .forEach(result::add);
        return result;
    }

    public String checkDuplicate(TaskList taskList, Task newTask) {
        for (Task originalTask : tasks) {
            if (originalTask.duplicateExists(newTask)) {
                //dont add
            } else if (originalTask.duplicateDescriptionExists(newTask)) {
                //true add both 3
                //true add, add and remove other 2
                //false dont add 1
                //System.out.println("Which task do you want to choose?");
                //System.out.println(list + "\n3. Both");
                //String input = ui.getUserInput();
                //parse(input);
                //add Task
            }
        }
    }

}
