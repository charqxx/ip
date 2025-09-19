package serene;

import org.junit.jupiter.api.Test;
import serene.task.Event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class EventTest {
    @Test
    public void toSaveFormat() {
        Event e = new Event("Meeting", "2025-08-28 18:09", "2025-08-28 19:09");

        assertEquals("E , 0 , Meeting , 2025-08-28 18:09 /to 2025-08-28 19:09", e.toSaveFormat());
    }

    @Test
    void testToString() {
        Event event = new Event("Meeting", "2025-09-19 14:00", "2025-09-19 15:00");
        String expected = "E |   | Meeting (from: Sep 19 2025 14:00 to: Sep 19 2025 15:00)";
        assertEquals(expected, event.toString());
    }

    @Test
    void isDuplicate_DuplicateInput() {
        Event event1 = new Event("Meeting", "2025-09-19 14:00", "2025-09-19 15:00");
        Event event2 = new Event("Meeting", "2025-09-19 14:00", "2025-09-19 15:00");

        assertEquals(true, event1.isDuplicate(event2));
    }

    @Test
    void isDuplicate_NonDuplicateInput() {
        Event event1 = new Event("Meeting", "2025-09-19 14:00", "2025-09-19 15:00");
        Event event2 = new Event("Meeting", "2025-09-19 14:00", "2025-09-19 16:00");

        assertEquals(false, event1.isDuplicate(event2));
    }
}
