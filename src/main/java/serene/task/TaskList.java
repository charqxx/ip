package serene.task;

import serene.command.Command;
import serene.command.TaskListExecution;
import serene.parser.Parser;
import serene.ui.Ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static serene.command.TaskListExecution.BOTH;

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

    public void remove(Task task) {
        tasks.remove(task);
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

    public void addWithDuplicateCheck(Task newTask, Ui ui) {
        for (Task originalTask : tasks) {
            if (originalTask.isDuplicate(newTask)) {
                return;
            }
            if (originalTask.isDuplicateDescription(newTask)) {
                String input = ui.askWhichToChoose(originalTask, newTask);
                TaskListExecution executionType = Parser.parseDuplicateSelection(input);
                execute(executionType, originalTask, newTask);
                return;
            }
        }
        tasks.add(newTask);
    }

    public void execute(TaskListExecution type, Task originalTask, Task newTask) {
        System.out.println(type);
        switch (type) {
        case KEEP:
            break;
        case REPLACE:
            tasks.remove(originalTask);
            tasks.add(newTask);
            break;
        case BOTH:
            tasks.add(newTask);
            break;
        }
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
