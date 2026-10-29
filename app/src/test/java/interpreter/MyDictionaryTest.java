package interpreter;

import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.IDictionary;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MyDictionaryTest {

    @Test
    public void testPutAndGet() {
        IDictionary<String, Integer> dict = new MyDictionary<>();
        dict.put("key1", 100);

        assertTrue(dict.isDefined("key1"));
        assertEquals(100, dict.get("key1"));
    }

    @Test
    public void testRemove() {
        IDictionary<String, String> dict = new MyDictionary<>();
        dict.put("lang", "Java");
        dict.remove("lang");

        assertFalse(dict.isDefined("lang"));
    }
}