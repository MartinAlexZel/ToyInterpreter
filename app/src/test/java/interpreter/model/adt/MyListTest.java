package interpreter.model.adt;

import interpreter.exceptions.ADTException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MyListTest {
    private IList<String> list;

    @BeforeEach
    void setUp() {
        list = new MyList<>();
    }

    @Test
    void testAddAndGet() throws ADTException {
        list.add("hello");
        list.add("world");
        assertEquals(2, list.size());
        assertEquals("hello", list.get(0));
        assertEquals("world", list.get(1));
    }

    @Test
    void testGetOutOfBoundsThrows() {
        var ex = assertThrows(ADTException.class, () -> list.get(0));
        assertEquals("index out of bounds in list", ex.getMessage());
    }
}