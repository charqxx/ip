package serene;

import java.util.List;

public class Parser {
    /**
     * Parses the user's input string and returns the Corresponding Command object.
     *
     * @param input String to be parsed.
     * @return Command object.
     * @throws SereneException If user gives an invalid input.
     */
    public static Command parse(String input) throws SereneException {
        String[] parts = input.split(" ", 2);
        String command = parts[0];

        switch (command) {
        case "":
            return new Command(CommandType.EMPTY);
        case "list":
            return new Command(CommandType.LIST);
        case "bye":
            return new Command(CommandType.BYE);
        case "mark": {
            String index = parts[1];
            return new Command(CommandType.MARK, List.of(index));
        }
        case "unmark": {
            String index = parts[1];
            return new Command(CommandType.UNMARK, List.of(index));
        }
        case "delete": {
            String index = parts[1];
            return new Command(CommandType.DELETE, List.of(index));
        }
        case "todo":
            return new Command(CommandType.TODO, List.of(parts[1]));
        case "deadline": {
            String task = parts[1].split(" /by ")[0];
            String date = parts[1].split(" /by ")[1];
            return new Command(CommandType.DEADLINE, List.of(task, date));
        }
        case "event": {
            String task = parts[1].split(" /from ")[0];
            String fromTo = parts[1].split(" /from ")[1];
            String from = fromTo.split(" /to ")[0];
            String to = fromTo.split(" /to ")[1];
            return new Command(CommandType.EVENT, List.of(task, from, to));
        }
        default:
            throw new SereneException("um...what?");
        }
    }
}
