package serene.Gui;

import serene.task.Task;
import serene.task.TaskList;

import java.util.Scanner;

public class Gui {
    private Scanner scanner;

    public Gui() {
        scanner = new Scanner(System.in);
    }

    public String showWelcome() {
        return "Hello! I'm Serene\nWhat can I do for you?";
    }

    public String showExit() {
        return "Bye. Hope to see you again soon!";
    }

    public String getUserInput() {
        return scanner.nextLine();
    }

    public String getList(TaskList taskList) {
        StringBuilder sb = new StringBuilder("Here are the tasks in your list:\n");
        for (int i = 0; i < taskList.size(); i++) {
            sb.append(i + 1).append(". ").append(taskList.get(i).toString()).append("\n");
        }
        return sb.toString();
    }

    public String getDeleted(Task task) {
        String message = "Noted. I've removed this task:\n";
        return message + task;
    }

    public String getMarked(Task task) {
        String message = "Nice! I've marked this task as done:\n";
        return message + task.toString();
    }

    public String getUnmarked(Task task) {
        String message = "Ok, I've marked this task as not done yet:\n";
        return message + task;
    }

    public String getAdded(Task task, TaskList taskList) {
        String addedMessage = "Got it. I've added this task:\n";
        String countMessage = String.format("Now you have %d tasks in the list.", taskList.size());
        return addedMessage + task.toString() + "\n" + countMessage;
    }

    public String getFound(TaskList taskList) {
        StringBuilder sb = new StringBuilder("Here are the matching tasks in your list:\n");
        for (int i = 0; i < taskList.size(); i++) {
            sb.append(i + 1 + ". " + taskList.get(i).toString() + "\n");
        }
        return sb.toString();
    }
}
