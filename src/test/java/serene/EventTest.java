package serene;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EventTest {
    @Test
    public void toSaveFormat() {
        Event e = new Event("Meeting", "2025-08-28 18:09", "2025-08-28 19:09");

        assertEquals("E , 0 , Meeting , 2025-08-28 18:09 /to 2025-08-28 19:09", e.toSaveFormat());
    }
}
