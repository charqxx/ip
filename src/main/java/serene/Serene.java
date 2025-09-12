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
                switch (command.getType()) {
                case EMPTY:
                    throw new SereneException("Don't be lazy, you have to do something!");
                case BYE:
                    ui.showExit();
                    isRunning = false;
                    break;
                case LIST:
                    ui.showList(taskList);
                    break;
                case DELETE: {
                    int indexToDelete = Integer.parseInt(command.getArguments().get(0)) - 1;
                    Task toDelete = taskList.get(indexToDelete);
                    taskList.remove(indexToDelete);
                    ui.showDeleted(toDelete);
                    break;
                }
                case MARK: {
                    int indexToMark = Integer.parseInt(command.getArguments().get(0)) - 1;
                    Task toMark = taskList.get(indexToMark);
                    toMark.mark();
                    ui.showMarked(toMark);
                    break;
                }
                case UNMARK: {
                    int indexToUnmark = Integer.parseInt(command.getArguments().get(0)) - 1;
                    Task toUnmark = taskList.get(indexToUnmark);
                    toUnmark.unmark();
                    ui.showUnmarked(toUnmark);
                    break;
                }
                case TODO: {
                    Task task = new ToDo(command.getArguments().get(0));
                    taskList.addWithDuplicateCheck(task, ui);
                    ui.showAdded(task, taskList);
                    break;
                }
                case DEADLINE: {
                    List<String> parts = command.getArguments();
                    Task task = new Deadline(parts.get(0), parts.get(1));
                    taskList.addWithDuplicateCheck(task, ui);
                    ui.showAdded(task, taskList);
                    break;
                }
                case EVENT: {
                    List<String> parts = command.getArguments();
                    Task task = new Event(parts.get(0), parts.get(1), parts.get(2));
                    taskList.addWithDuplicateCheck(task, ui);
                    ui.showAdded(task, taskList);
                    break;
                }
                case FIND: {
                    String[] keywords = command.getArguments().toArray(new String[0]);
                    TaskList tasks = taskList.find(keywords);
                    ui.showFound(tasks);
                    break;
                }
                default:
                    throw new SereneException("um...what?");
                }
                storage.save(taskList);

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
                return gui.showExit();
            case LIST: {
                return gui.getList(taskList);
            }
            case DELETE: {
                int indexToDelete = Integer.parseInt(command.getArguments().get(0)) - 1;
                assert(indexToDelete) >= 0;
                Task toDelete = taskList.get(indexToDelete);
                taskList.remove(indexToDelete);
                storage.save(taskList);
                return gui.getDeleted(toDelete);
            }
            case MARK: {
                int indexToMark = Integer.parseInt(command.getArguments().get(0)) - 1;
                assert(indexToMark) >= 0;
                Task toMark = taskList.get(indexToMark);
                toMark.mark();
                storage.save(taskList);
                return gui.getMarked(toMark);
            }
            case UNMARK: {
                int indexToUnmark = Integer.parseInt(command.getArguments().get(0)) - 1;
                assert(indexToUnmark) >= 0;
                Task toUnmark = taskList.get(indexToUnmark);
                storage.save(taskList);
                return gui.getUnmarked(toUnmark);
            }
            case TODO: {
                Task task = new ToDo(command.getArguments().get(0));
                //if (task.checkDuplicate(taskList, task)) {
                //    break;
                //}
                taskList.add(task);
                storage.save(taskList);

                return gui.getAdded(task, taskList);

            }
            case DEADLINE: {
                List<String> parts = command.getArguments();
                Task task = new Deadline(parts.get(0), parts.get(1));
                taskList.add(task);
                storage.save(taskList);
                return gui.getAdded(task, taskList);
            }
            case EVENT: {
                List<String> parts = command.getArguments();
                Task task = new Event(parts.get(0), parts.get(1), parts.get(2));
                taskList.add(task);
                storage.save(taskList);
                return gui.getAdded(task, taskList);
            }
            case FIND: {
                String[] keywords = command.getArguments().toArray(new String[0]);
                TaskList tasks = taskList.find(keywords);
                return gui.getFound(tasks);
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
