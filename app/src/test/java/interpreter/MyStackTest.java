package interpreter;

import interpreter.model.adt.IStack;
import interpreter.model.adt.MyStack;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MyStackTest {

    @Test
    public void testPushAndPop() {
        IStack<String> stack = new MyStack<>();
        assertTrue(stack.isEmpty());

        stack.push("first");
        stack.push("second");

        assertFalse(stack.isEmpty());
        assertEquals("second", stack.pop());
        assertEquals("first", stack.pop());
        assertTrue(stack.isEmpty());
    }
}