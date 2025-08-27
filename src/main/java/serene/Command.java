package serene;

import java.util.List;

public class Command {
    private CommandType type;
    private List<String> arguments;

    public Command(CommandType type, List<String> arguments) {
        this.type = type;
        this.arguments = arguments;
    }

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
}
