package serene;

import serene.gui.Gui;
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
    private boolean isRunning;

    public Serene(String filePath) {
        ui = new Ui();
        gui = new Gui();
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
        isRunning = true;
        ui.showWelcome();
        while (isRunning) {
            try {
                String input = ui.getUserInput();
                Command command = Parser.parse(input);
                commandType = command.getType().name();
                handleCommand(command);
                storage.save(taskList);
            } catch (SereneException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void handleCommand(Command command) throws SereneException {
        switch (command.getType()) {
        case EMPTY -> handleEmpty();
        case BYE -> handleBye();
        case LIST -> handleList();
        case DELETE -> handleDelete(command);
        case MARK -> handleMark(command);
        case UNMARK -> handleUnmark(command);
        case TODO, DEADLINE, EVENT -> handleAddTask(command);
        case FIND -> handleFind(command);
        default -> throw new SereneException("um...what?");
        }
    }

    private void handleEmpty() throws SereneException {
        throw new SereneException("Don't be lazy, you have to do something!");
    }

    private void handleBye() {
        ui.showExit();
        isRunning = false;
    }

    private void handleList() {
        ui.showList(taskList);
    }

    private void handleDelete(Command command) throws SereneException {
        int index = Integer.parseInt(command.getArguments().get(0)) - 1;
        Task task = taskList.get(index);
        taskList.remove(index);
        ui.showDeleted(task);
    }

    private void handleMark(Command command) throws SereneException {
        int index = Integer.parseInt(command.getArguments().get(0)) - 1;
        Task task = taskList.get(index);
        task.mark();
        ui.showMarked(task);
    }

    private void handleUnmark(Command command) throws SereneException {
        int index = Integer.parseInt(command.getArguments().get(0)) - 1;
        Task task = taskList.get(index);
        task.unmark();
        ui.showUnmarked(task);
    }

    private void handleAddTask(Command command) throws SereneException {
        Task task;
        List<String> args = command.getArguments();
        switch (command.getType()) {
        case TODO -> task = new ToDo(args.get(0));
        case DEADLINE -> task = new Deadline(args.get(0), args.get(1));
        case EVENT -> task = new Event(args.get(0), args.get(1), args.get(2));
        default -> throw new SereneException("Invalid task type");
        }
        taskList.add(task);
        ui.showAdded(task, taskList);
    }

    private void handleFind(Command command) {
        String[] keywords = command.getArguments().toArray(new String[0]);
        TaskList result = taskList.find(keywords);
        ui.showFound(result);
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
