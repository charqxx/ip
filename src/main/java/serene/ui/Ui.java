package serene.ui;

import serene.task.Task;
import serene.task.TaskList;

import java.util.List;
import java.util.Scanner;

public class Ui {
    private Scanner scanner;

    public Ui() {
        scanner = new Scanner(System.in);
    }

    public void showWelcome() {
        System.out.println("Hello! I'm Serene\nWhat can I do for you?");
    }

    public void showExit() {
        System.out.println("Bye. Hope to see you again soon!");
    }

    public String getUserInput() {
        return scanner.nextLine();
    }

    public void showList(TaskList taskList) {
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < taskList.size(); i++) {
            System.out.println((i + 1) + ". " + taskList.get(i));
        }
    }

    public void showDeleted(Task task) {
        System.out.println("Noted. I've removed this task:");
        System.out.println(task);
    }

    public void showMarked(Task task) {
        System.out.println("Nice! I've marked this task as done:");
        System.out.println(task);
    }

    public void showUnmarked(Task task) {
        System.out.println("Ok, I've marked this task as not done yet:");
        System.out.println(task);
    }

    public void showAdded(Task task, TaskList taskList) {
        System.out.println("Got it. I've added this task:");
        System.out.println(task);
        String message = String.format("Now you have %d tasks in the list.", taskList.size());
        System.out.println(message);
    }

    public void showFound(TaskList taskList) {
        System.out.println("Here are the matching tasks in your list:\n");
        for (int i = 0; i < taskList.size(); i++) {
            System.out.println(i + 1 + ". " + taskList.get(i).toString() + "\n");
        }
    }

    public String askWhichToChoose(Task originalTask, Task newTask) {
        TaskList tasks = new TaskList(originalTask, newTask);
        System.out.println("Which task do you want to keep?");
        System.out.println(tasks + "3. Both");
        String input = getUserInput();
        return input;
    }
}
