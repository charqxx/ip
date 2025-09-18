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


    public String getResponse(String input) {
        try {
            Command command = Parser.parse(input);
            commandType = command.getType().name();
            return handleCommand(command);
        } catch (SereneException e) {
            return e.getMessage();
        }
    }

    /*
     * Refactored getResponse method with the assistance of ChatGPT (GPT-5 mini).
     *
     * How ChatGPT helped:
     * - Suggested creating helper methods like handleUnmark and handleAddTask
     *   to simplify and reduce repetition in the switch-case structure.
     */
    private String handleCommand(Command command) throws SereneException {
        switch (command.getType()) {
        case EMPTY -> { return handleEmpty(); }
        case BYE -> { return handleBye(); }
        case LIST -> { return handleList(); }
        case DELETE -> { return handleDelete(command); }
        case MARK -> { return handleMark(command); }
        case UNMARK -> { return handleUnmark(command); }
        case TODO, DEADLINE, EVENT -> { return handleAddTask(command); }
        case FIND -> { return handleFind(command); }
        default -> throw new SereneException("um...what?");
        }
    }

    private String handleEmpty() throws SereneException {
        throw new SereneException("Don't be lazy, you have to do something!");
    }

    private String handleBye() {
        return gui.showExit();
    }

    private String handleList() {
        return gui.getList(taskList);
    }

    private String handleDelete(Command command) throws SereneException {
        int index = Integer.parseInt(command.getArguments().get(0)) - 1;
        Task task = taskList.get(index);
        taskList.remove(index);
        storage.save(taskList);
        return gui.getDeleted(task);
    }

    private String handleMark(Command command) throws SereneException {
        int index = Integer.parseInt(command.getArguments().get(0)) - 1;
        Task task = taskList.get(index);
        task.mark();
        storage.save(taskList);
        return gui.getMarked(task);
    }

    private String handleUnmark(Command command) throws SereneException {
        int index = Integer.parseInt(command.getArguments().get(0)) - 1;
        Task task = taskList.get(index);
        task.unmark();
        storage.save(taskList);
        return gui.getUnmarked(task);
    }

    private String handleAddTask(Command command) throws SereneException {
        Task task;
        List<String> args = command.getArguments();
        switch (command.getType()) {
        case TODO -> task = new ToDo(args.get(0));
        case DEADLINE -> task = new Deadline(args.get(0), args.get(1));
        case EVENT -> task = new Event(args.get(0), args.get(1), args.get(2));
        default -> throw new SereneException("Invalid task type");
        }
        taskList.add(task);
        storage.save(taskList);
        return gui.getAdded(task, taskList);
    }

    private String handleFind(Command command) {
        String[] keywords = command.getArguments().toArray(new String[0]);
        TaskList result = taskList.find(keywords);
        return gui.getFound(result);
    }

   public String getCommandType() {
       return commandType;
   }

}
