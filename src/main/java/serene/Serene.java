package serene;

import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;
//ui, storage, parser, tasklist
public class Serene {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Storage storage = new Storage("./data/serene.txt");
        storage.createSaveFile();
        //ArrayList<Task> history = new ArrayList<>(); //for debugging, rmbr to change back
        TaskList history = storage.load();

        System.out.println("Hello! I'm Serene\nWhat can I do for you?");
        boolean running = true;
        while(running) {
            try {
                String input = sc.nextLine();
                Command command = Parser.parse(input);
                //String[] parts = input.split(" ");
                switch (command.getType()) {
                    case EMPTY:
                        throw new SereneException("Don't be lazy, you have to do something!");
                    case BYE:
                        System.out.println("Bye. Hope to see you again soon!");
                        running = false;
                        break;
                    case LIST:
                        System.out.println("Here are the tasks in your list:");
                        for (int i = 0; i < history.size(); i++) {
                            System.out.println((i + 1) + ". " + history.get(i));
                        }
                        break;
                    case DELETE:
                        int indexToDelete = Integer.parseInt(command.getArguments().get(0)) - 1;
                        Task toDelete = history.get(indexToDelete);
                        history.remove(indexToDelete);
                        System.out.println("Noted. I've removed this task:");
                        System.out.println(toDelete);
                        storage.save(history);
                    case MARK: {
                        int indexToMark = Integer.parseInt(command.getArguments().get(0)) - 1;
                        Task toMark = history.get(indexToMark);
                        toMark.mark();
                        System.out.println("Nice! I've marked this task as done:");
                        System.out.println(toMark.toString());
                        storage.save(history);
                        break;
                    }
                    case UNMARK: {
                        int indexToUnmark = Integer.parseInt(command.getArguments().get(0)) - 1;
                        Task toUnmark = history.get(indexToUnmark);
                        toUnmark.unmark();
                        System.out.println("Ok, I've marked this task as not done yet:");
                        System.out.println(toUnmark);
                        storage.save(history);
                        break;
                    }
                    case TODO: {
                        Task task = new ToDo(command.getArguments().get(0));
                        history.add(task);
                        System.out.println("Got it. I've added this task:");
                        System.out.println(task);
                        String message = String.format("Now you have %d tasks in the list.", history.size());
                        System.out.println(message);
                        storage.save(history);
                        break;
                    }
                    case DEADLINE: {
                        List<String> parts = command.getArguments();
                        Task task = new Deadline(parts.get(0), parts.get(1));
                        history.add(task);
                        System.out.println("Got it. I've added this task:");
                        System.out.println(task);
                        String message = String.format("Now you have %d tasks in the list.", history.size());
                        System.out.println(message);
                        storage.save(history);
                        break;
                    }
                    case EVENT: {
                        List<String> parts = command.getArguments();
                        Task task = new Event(parts.get(0), parts.get(1), parts.get(2));
                        history.add(task);
                        System.out.println("Got it. I've added this task:");
                        System.out.println(task);
                        String message = String.format("Now you have %d tasks in the list.", history.size());
                        System.out.println(message);
                        storage.save(history);
                        break;
                    }
                    default:
                        throw new SereneException("um...what?");
                }

            } catch (SereneException e) {
                System.out.println(e);
            }

        }

    }
}
