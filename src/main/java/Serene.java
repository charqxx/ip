import java.awt.*;
import java.util.Scanner;
import java.util.ArrayList;
public class Serene {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Storage storage = new Storage("./data/serene.txt");
        storage.createSaveFile();
        ArrayList<Task> history = new ArrayList<>(); //for debugging, rmbr to change back
        //ArrayList<Task> history = storage.load();

        System.out.println("Hello! I'm Serene\nWhat can I do for you?");
        while(true) {
            try {
                String input = sc.nextLine();
                String[] parts = input.split(" ");
                if (input.isEmpty()) {
                    throw new SereneException("Don't be lazy, you have to do something!");
                } else if (input.equals("bye")) {
                    System.out.println("Bye. Hope to see you again soon!");
                    break;
                } else if (input.equals("list")) {
                    System.out.println("Here are the tasks in your list:");
                    for (int i = 0; i < history.size(); i++) {
                        System.out.println((i + 1) + ". " + history.get(i));
                    }
                } else if (parts[0].equals("mark")) {
                    Task toMark = history.get(Integer.parseInt(parts[1]) - 1);
                    toMark.mark();
                    System.out.println("Nice! I've marked this task as done:");
                    System.out.println(toMark.toString());
                    storage.save(history);
                } else if (parts[0].equals("unmark")) {
                    Task toUnmark = history.get(Integer.parseInt(parts[1]) - 1);
                    toUnmark.unmark();
                    System.out.println("Ok, I've marked this task as not done yet:");
                    System.out.println(toUnmark.toString());
                    storage.save(history);
                } else if (parts[0].equals("todo")) {
                    ToDo newTask = new ToDo(input.split(" ", 2)[1]);
                    history.add(newTask);
                    System.out.println("Got it. I've added this task:");
                    System.out.println(newTask);
                    String message = String.format("Now you have %d tasks in the list.", history.size());
                    System.out.println(message);
                    storage.save(history);
                } else if (parts[0].equals("deadline")) {
                    String taskWithDate = input.split(" ", 2)[1];
                    String task = taskWithDate.split(" /by ")[0];
                    String date = taskWithDate.split(" /by ")[1];
                    Deadline newTask = new Deadline(task, date);
                    history.add(newTask);
                    System.out.println("Got it. I've added this task:");
                    System.out.println(newTask);
                    String message = String.format("Now you have %d tasks in the list.", history.size());
                    System.out.println(message);
                    storage.save(history);
                } else if (parts[0].equals("event")) {
                    String taskFromTo = input.split(" ", 2)[1];
                    String task = taskFromTo.split(" /from ")[0];
                    String fromTo = taskFromTo.split(" /from ")[1];
                    String from = fromTo.split(" /to ")[0];
                    String to = fromTo.split(" /to ")[1];
                    Event newTask = new Event(task, from, to);
                    history.add(newTask);
                    System.out.println("Got it. I've added this task:");
                    System.out.println(newTask);
                    String message = String.format("Now you have %d tasks in the list.", history.size());
                    System.out.println(message);
                    storage.save(history);
                } else if (parts[0].equals("delete")) {
                    Task toDelete = history.get(Integer.parseInt(parts[1]) - 1);
                    history.remove(Integer.parseInt(parts[1]) - 1);
                    System.out.println("Noted. I've removed this task:");
                    System.out.println(toDelete);
                    String message = String.format("Now you have %d tasks in the list.", history.size());
                    System.out.println(message);
                    storage.save(history);
                } else {
                    throw new SereneException("um...what?");
                }
            } catch (SereneException e) {
                System.out.println(e.toString());
            }
        }

    }
}
