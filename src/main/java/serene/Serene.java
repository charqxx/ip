package serene;

import serene.Gui.Gui;
import serene.command.Command;
import serene.exception.SereneException;
import serene.parser.Parser;
import serene.storage.Storage;
import serene.task.Task;
import serene.task.ToDo;
import serene.task.Deadline;
import serene.task.Event;
import serene.task.TaskList;
import serene.ui.Ui;

import java.util.List;



public class Serene {

    private static final String DEFAULT_FILE_PATH = "data/serene.txt";
    private Storage storage;
    private TaskList taskList;
    private Ui ui;
    private Gui gui;
    private String commandType;

    public Serene(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        storage.createSaveFile();
        taskList = storage.load();
    }

    public Serene() {
        this(DEFAULT_FILE_PATH);
    }
    /**
     * Starts main execution loop of Serene and continuously reads user input until exit command is received.
     * Prints out results on the terminal.
     */
    public void run() {
        boolean isRunning = true;
        ui.showWelcome();
        while (isRunning) {
            try {
                String input = ui.getUserInput();
                Command command = Parser.parse(input);
                commandType = command.getType().name();
                System.out.println(commandType);
                switch (command.getType()) {
                case EMPTY:
                    throw new SereneException("Don't be lazy, you have to do something!");
                case BYE:
                    ui.exitMessage();
                    isRunning = false;
                    break;
                case LIST:
                    ui.showList(taskList);
                    break;
                case DELETE: {
                    int indexToDelete = Integer.parseInt(command.getArguments().get(0)) - 1;
                    Task toDelete = taskList.get(indexToDelete);
                    taskList.remove(indexToDelete);
                    ui.showDelete(toDelete);
                    storage.save(taskList);
                    break;
                }
                case MARK: {
                    int indexToMark = Integer.parseInt(command.getArguments().get(0)) - 1;
                    Task toMark = taskList.get(indexToMark);
                    toMark.mark();
                    ui.showMark(toMark);
                    storage.save(taskList);
                    break;
                }
                case UNMARK: {
                    int indexToUnmark = Integer.parseInt(command.getArguments().get(0)) - 1;
                    Task toUnmark = taskList.get(indexToUnmark);
                    toUnmark.unmark();
                    ui.showUnmark(toUnmark);
                    storage.save(taskList);
                    break;
                }
                case TODO: {
                    Task task = new ToDo(command.getArguments().get(0));
                    taskList.add(task);
                    ui.showAdded(task, taskList);
                    storage.save(taskList);
                    break;
                }
                case DEADLINE: {
                    List<String> parts = command.getArguments();
                    Task task = new Deadline(parts.get(0), parts.get(1));
                    taskList.add(task);
                    ui.showAdded(task, taskList);
                    storage.save(taskList);
                    break;
                }
                case EVENT: {
                    List<String> parts = command.getArguments();
                    Task task = new Event(parts.get(0), parts.get(1), parts.get(2));
                    taskList.add(task);
                    ui.showAdded(task, taskList);
                    storage.save(taskList);
                    break;
                }
                case FIND: {
                    String[] keywords = command.getArguments().toArray(new String[0]);
                    TaskList tasks = taskList.find(keywords);
                    ui.showFind(taskList);
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


    public static void main(String[] args) {
        new Serene("data/serene.txt").run();
    }

    /**
     * Generates a response for the user's chat message.
     */
    public String getResponse(String input) {
        try {
            Command command = Parser.parse(input);
            commandType = command.getType().name();
            switch (command.getType()) {
            case EMPTY:
                throw new SereneException("Don't be lazy, you have to do something!");
            case BYE:
                return gui.exitMessage();
            case LIST: {
                StringBuilder message = new StringBuilder("Here are the tasks in your list:\n");
                for (int i = 0; i < history.size(); i++) {
                    message.append(i + 1).append(". ").append(history.get(i).toString()).append("\n");
                }
                return message.toString();
            }
            case DELETE: {
                int indexToDelete = Integer.parseInt(command.getArguments().get(0)) - 1;
                Task toDelete = history.get(indexToDelete);
                history.remove(indexToDelete);
                storage.save(history);
                String message = "Noted. I've removed this task:\n";
                return message + toDelete.toString();
            }
            case MARK: {
                int indexToMark = Integer.parseInt(command.getArguments().get(0)) - 1;
                Task toMark = history.get(indexToMark);
                toMark.mark();
                storage.save(history);
                String message = "Nice! I've marked this task as done:\n";
                return message + toMark.toString();
            }
            case UNMARK: {
                int indexToUnmark = Integer.parseInt(command.getArguments().get(0)) - 1;
                Task toUnmark = history.get(indexToUnmark);
                toUnmark.unmark();
                storage.save(history);
                String message = "Ok, I've marked this task as not done yet:\n";
                return message + toUnmark.toString();
            }
            case TODO: {
                Task task = new ToDo(command.getArguments().get(0));
                history.add(task);
                String addedMessage = "Got it. I've added this task:\n";
                String countMessage = String.format("Now you have %d tasks in the list.", history.size());
                storage.save(history);
                return addedMessage + task.toString() + "\n" + countMessage;
            }
            case DEADLINE: {
                List<String> parts = command.getArguments();
                Task task = new Deadline(parts.get(0), parts.get(1));
                history.add(task);
                String addedMessage = "Got it. I've added this task:\n";
                String countMessage = String.format("Now you have %d tasks in the list.", history.size());
                storage.save(history);
                return addedMessage + task.toString() + "\n" + countMessage;
            }
            case EVENT: {
                List<String> parts = command.getArguments();
                Task task = new Event(parts.get(0), parts.get(1), parts.get(2));
                history.add(task);
                String addedMessage = "Got it. I've added this task:\n";
                String countMessage = String.format("Now you have %d tasks in the list.", history.size());
                storage.save(history);
                return addedMessage + task.toString() + "\n" + countMessage;
            }
            case FIND: {
                String[] keywords = command.getArguments().toArray(new String[0]);
                TaskList tasks = history.find(keywords);
                StringBuilder sb = new StringBuilder("Here are the matching tasks in your list:\n");
                for (int i = 0; i < tasks.size(); i++) {
                    sb.append(i + 1 + ". " + tasks.get(i).toString() + "\n");
                }
                return sb.toString();
            }
            default:
                throw new SereneException("um...what?");
            }

        } catch (SereneException e) {
            return e.getMessage();
        }
    }

   public String getCommandType() {
       return commandType;
   }

}
