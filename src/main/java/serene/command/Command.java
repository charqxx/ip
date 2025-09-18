package serene.command;

import java.util.List;

public class Command {
    private CommandType type;
    private List<String> arguments;

    /**
     * Creates command of given type and relevant arguments.
     *
     * @param type Type of command.
     * @param arguments Arguments of the command.
     */
    public Command(CommandType type, List<String> arguments) {
        this.type = type;
        this.arguments = arguments;
    }

    /**
     * Creates command of given type and no arguments.
     *
     * @param type Type of command.
     */
    public Command(CommandType type) {
        this.type = type;
        this.arguments = List.of();
    }

    public CommandType getType() {
        return type;
    }

    public List<String> getArguments() {
        return arguments;
    }

    //public void execute(Command command, Gui gui, Storage storage);
}
