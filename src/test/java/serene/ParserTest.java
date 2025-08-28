package serene;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class ParserTest {
    @Test
    public void parser_emptyInput() throws SereneException{
        assertEquals(new Command(CommandType.EMPTY).getType(), Parser.parse(" ").getType());
    }

    @Test
    public void parse_listInput() throws SereneException  {
        assertEquals(new Command(CommandType.LIST).getType(), Parser.parse("list").getType());
    }

    @Test
    public void parse_byeInput() throws SereneException  {
        assertEquals(new Command(CommandType.BYE).getType(), Parser.parse("bye").getType());
    }

    @Test
    public void parse_markInput() throws SereneException  {
        Command command = new Command(CommandType.MARK, List.of("2"));
        assertEquals(command.getType(), Parser.parse("mark 2").getType());
        assertEquals(command.getArguments(), Parser.parse("mark 2").getArguments());
    }

    @Test
    public void parse_unmarkInput() throws SereneException  {
        Command command = new Command(CommandType.UNMARK, List.of("2"));
        assertEquals(command.getType(), Parser.parse("unmark 2").getType());
        assertEquals(command.getArguments(), Parser.parse("unmark 2").getArguments());
    }

    @Test
    public void parse_deleteInput() throws SereneException  {
        Command command = new Command(CommandType.DELETE, List.of("2"));
        assertEquals(command.getType(), Parser.parse("delete 2").getType());
        assertEquals(command.getArguments(), Parser.parse("delete 2").getArguments());
    }

    @Test
    public void parse_todoInput() throws SereneException  {
        Command command = new Command(CommandType.TODO, List.of("Return books"));
        assertEquals(command.getType(), Parser.parse("todo Return books").getType());
        assertEquals(command.getArguments(), Parser.parse("todo Return books").getArguments());
    }

    @Test
    public void parse_deadlineInput() throws SereneException  {
        Command command = new Command(CommandType.DEADLINE, List.of("Return books", "2019-08-28 12:00"));
        assertEquals(command.getType(), Parser.parse("deadline Return books /by 2019-08-28 12:00").getType());
        assertEquals(command.getArguments(), Parser.parse("deadline Return books /by 2019-08-28 12:00").getArguments());
    }

    @Test
    public void parse_eventInput() throws SereneException  {
        Command command = new Command(CommandType.EVENT, List.of("Meeting", "2019-08-28 12:00", "2019-08-28 13:00"));
        assertEquals(command.getType(), Parser.parse("event Meeting /from 2019-08-28 12:00 /to 2019-08-28 13:00").getType());
        assertEquals(command.getArguments(), Parser.parse("event Meeting /from 2019-08-28 12:00 /to 2019-08-28 13:00").getArguments());
    }

    @Test
    public void parse_invalidInput_exceptionThrown() {
        try {
            assertEquals(0, Parser.parse("what"));
            fail();
        } catch (SereneException e) {
            assertEquals("um...what?", e.getMessage());
        }
    }
 }
