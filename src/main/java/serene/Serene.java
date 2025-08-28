package serene;

import java.util.List;

//ui, storage, parser, tasklist
public class Serene {

    private Storage storage;
    private TaskList history;
    private Ui ui;

    public Serene(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        storage.createSaveFile();
        history = storage.load();
        //ui.showLoadingError();
        //history = new TaskList();
    }

    /**
     * Starts main execution loop of Serene and continuously reads user input until exit command is received.
     */
    public void run() {
        boolean isRunning = true;
        ui.showWelcome();
        while (isRunning) {
            try {
                String input = ui.getUserInput();
                Command command = Parser.parse(input);
                //String[] parts = input.split(" ");
                switch (command.getType()) {
                case EMPTY:
                    throw new SereneException("Don't be lazy, you have to do something!");
                case BYE:
                    ui.exitMessage();
                    isRunning = false;
                    break;
                case LIST:
                    ui.showMessage("Here are the tasks in your list:");
                    for (int i = 0; i < history.size(); i++) {
                        System.out.println((i + 1) + ". " + history.get(i));
                    }
<<<<<<< HEAD
                    break;
                case DELETE: {
                    int indexToDelete = Integer.parseInt(command.getArguments().get(0)) - 1;
                    Task toDelete = history.get(indexToDelete);
                    history.remove(indexToDelete);
                    ui.showMessage("Noted. I've removed this task:");
                    System.out.println(toDelete);
                    storage.save(history);
                    break;
                }
                case MARK: {
                    int indexToMark = Integer.parseInt(command.getArguments().get(0)) - 1;
                    Task toMark = history.get(indexToMark);
                    toMark.mark();
                    ui.showMessage("Nice! I've marked this task as done:");
                    System.out.println(toMark.toString());
                    storage.save(history);
                    break;
                }
                case UNMARK: {
                    int indexToUnmark = Integer.parseInt(command.getArguments().get(0)) - 1;
                    Task toUnmark = history.get(indexToUnmark);
                    toUnmark.unmark();
                    ui.showMessage("Ok, I've marked this task as not done yet:");
                    System.out.println(toUnmark);
                    storage.save(history);
                    break;
                }
                case TODO: {
                    Task task = new ToDo(command.getArguments().get(0));
                    history.add(task);
                    ui.showMessage("Got it. I've added this task:");
                    System.out.println(task);
                    String message = String.format("Now you have %d tasks in the list.", history.size());
                    ui.showMessage(message);
                    storage.save(history);
                    break;
                }
                case DEADLINE: {
                    List<String> parts = command.getArguments();
                    Task task = new Deadline(parts.get(0), parts.get(1));
                    history.add(task);
                    ui.showMessage("Got it. I've added this task:");
                    System.out.println(task);
                    String message = String.format("Now you have %d tasks in the list.", history.size());
                    ui.showMessage(message);
                    storage.save(history);
                    break;
                }
                case EVENT: {
                    List<String> parts = command.getArguments();
                    Task task = new Event(parts.get(0), parts.get(1), parts.get(2));
                    history.add(task);
                    ui.showMessage("Got it. I've added this task:");
                    System.out.println(task);
                    String message = String.format("Now you have %d tasks in the list.", history.size());
                    ui.showMessage(message);
                    storage.save(history);
                    break;
                }
                default:
                    throw new SereneException("um...what?");
=======
                    case MARK: {
                        int indexToMark = Integer.parseInt(command.getArguments().get(0)) - 1;
                        Task toMark = history.get(indexToMark);
                        toMark.mark();
                        ui.showMessage("Nice! I've marked this task as done:");
                        System.out.println(toMark.toString());
                        storage.save(history);
                        break;
                    }
                    case UNMARK: {
                        int indexToUnmark = Integer.parseInt(command.getArguments().get(0)) - 1;
                        Task toUnmark = history.get(indexToUnmark);
                        toUnmark.unmark();
                        ui.showMessage("Ok, I've marked this task as not done yet:");
                        System.out.println(toUnmark);
                        storage.save(history);
                        break;
                    }
                    case TODO: {
                        Task task = new ToDo(command.getArguments().get(0));
                        history.add(task);
                        ui.showMessage("Got it. I've added this task:");
                        System.out.println(task);
                        String message = String.format("Now you have %d tasks in the list.", history.size());
                        ui.showMessage(message);
                        storage.save(history);
                        break;
                    }
                    case DEADLINE: {
                        List<String> parts = command.getArguments();
                        Task task = new Deadline(parts.get(0), parts.get(1));
                        history.add(task);
                        ui.showMessage("Got it. I've added this task:");
                        System.out.println(task);
                        String message = String.format("Now you have %d tasks in the list.", history.size());
                        ui.showMessage(message);
                        storage.save(history);
                        break;
                    }
                    case EVENT: {
                        List<String> parts = command.getArguments();
                        Task task = new Event(parts.get(0), parts.get(1), parts.get(2));
                        history.add(task);
                        ui.showMessage("Got it. I've added this task:");
                        System.out.println(task);
                        String message = String.format("Now you have %d tasks in the list.", history.size());
                        ui.showMessage(message);
                        storage.save(history);
                        break;
                    }
                    case FIND: {
                        String keyword = command.getArguments().get(0);
                        TaskList tasks = history.find(keyword);
                        ui.showMessage("Here are the matching tasks in your list:");
                        for (int i = 0; i < tasks.size(); i++) {
                            System.out.println((i + 1) + ". " + tasks.get(i));
                        }
                        break;
                    }
                    default:
                        throw new SereneException("um...what?");
>>>>>>> branch-Level-9
                }

            } catch (SereneException e) {
                System.out.println(e);
            }
        }
    }


    public static void main(String[] args) {
        new Serene("data/serene.txt").run();
    }

}
