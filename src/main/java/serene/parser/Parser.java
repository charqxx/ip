package serene.parser;

import serene.exception.EmptyDescriptionException;
import serene.exception.NoMatchingKeywordException;
import serene.exception.SereneException;
import serene.command.Command;
import serene.command.CommandType;

import java.util.Arrays;
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
        String[] parts = input.trim().split(" ", 2);
        String command = parts[0];

        switch (command) {
        case "":
            return new Command(CommandType.EMPTY);
        case "list":
            return new Command(CommandType.LIST);
        case "bye":
            return new Command(CommandType.BYE);
        case "mark": {
            return parseMark(parts);
        }
        case "unmark": {
            return parseUnmark(parts);
        }
        case "delete": {
            return parseDelete(parts);
        }
        case "todo":
            return parseTodo(parts);
        case "deadline": {
            return parseDeadline(parts);
        }
        case "event": {
            return parseEvent(parts);
        }

        case "find": {
            return parseFind(parts);
        }
        default:
            throw new SereneException("um...what?");
        }
    }

    private static Command parseTodo(String[] parts) throws SereneException {
        if (parts.length < 2) {
            throw new EmptyDescriptionException("What do you want to do?");
        }

        String desc = parts[1].trim();
        if (desc.isEmpty()) throw new EmptyDescriptionException("What do you want to do?");
        return new Command(CommandType.TODO, List.of(desc));

    }

    private static Command parseDeadline(String[] parts) throws SereneException {
        if (parts.length < 2) {
            throw new EmptyDescriptionException("Deadline must have a description and a date.");
        }

        String[] taskAndDate = parts[1].split(" /by ", 2);
        if (taskAndDate.length < 2 || taskAndDate[0].trim().isEmpty() || taskAndDate[1].trim().isEmpty()) {
            throw new EmptyDescriptionException("Deadline must have a description and a date.");
        }
        return new Command(CommandType.DEADLINE, List.of(taskAndDate[0].trim(), taskAndDate[1].trim()));
    }

    private static Command parseEvent(String[] parts) throws SereneException {
        if (parts.length < 2) {
            throw new EmptyDescriptionException("Event must have a description, a from and a to date.");
        }

        String[] taskAndFrom = parts[1].split(" /from ", 2);
        if (taskAndFrom.length < 2 || taskAndFrom[0].trim().isEmpty() || taskAndFrom[1].trim().isEmpty()) {
            throw new EmptyDescriptionException("Event must have a description, a from and a to date.");
        }
        String[] fromTo = taskAndFrom[1].split(" /to ", 2);
        if (fromTo.length < 2 || fromTo[0].trim().isEmpty() || fromTo[1].trim().isEmpty()) {
            throw new EmptyDescriptionException("Event must have both from and to dates.");
        }
        return new Command(CommandType.EVENT,
                List.of(taskAndFrom[0].trim(), fromTo[0].trim(), fromTo[1].trim()));
    }

    private static Command parseMark(String[] parts) throws SereneException {
        if (parts.length < 2) {
            throw new EmptyDescriptionException("What do you want to mark?");
        }
        String index = parts[1].trim();
        return new Command(CommandType.MARK, List.of(index));
    }

    private static Command parseUnmark(String[] parts) throws SereneException {
        if (parts.length < 2) {
            throw new EmptyDescriptionException("What do you want to unmark?");
        }
        String index = parts[1].trim();
        return new Command(CommandType.UNMARK, List.of(index));
    }

    private static Command parseDelete(String[] parts) throws SereneException {
        if (parts.length < 2) {
            throw new EmptyDescriptionException("What do you want to delete?");
        }
        String index = parts[1].trim();
        return new Command(CommandType.DELETE, List.of(index));
    }

    private static Command parseFind(String[] parts) throws SereneException {
        if (parts.length < 2) {
            throw new EmptyDescriptionException("What do you want to find?");
        }

        String[] keywords = parts[1].trim().split(" ");
        if (keywords.length == 0 || keywords.length == 1 && keywords[0].isEmpty()) {
            throw new NoMatchingKeywordException("Find what exactly?");
        }
        return new Command(CommandType.FIND, Arrays.asList(keywords));
    }
}
