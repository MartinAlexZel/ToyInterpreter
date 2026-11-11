package interpreter.controller;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.adt.FileTable;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.stmt.IStmt;
import interpreter.model.value.IntValue;
import interpreter.repository.Repository;
import interpreter.view.Examples;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir; // <--- Import for JUnit 5 TempDir

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {

    private PrgState run(IStmt program, Path tempDir) throws MyException {
        String logFilePath = tempDir.resolve("test_log.txt").toString();

        PrgState state = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new FileTable(), program);
        new Controller(new Repository(state, logFilePath), false).allStep();
        return state;
    }

    @Test
    void example1PrintsTwo(@TempDir Path tempDir) throws MyException {
        PrgState state = run(Examples.EX1, tempDir);
        assertTrue(state.getExeStack().isEmpty());
        assertEquals(new IntValue(2), state.getOut().get(0));
    }

    @Test
    void example2PrintsEighteen(@TempDir Path tempDir) throws MyException {
        PrgState state = run(Examples.EX2, tempDir);
        assertTrue(state.getExeStack().isEmpty());
        assertEquals(new IntValue(18), state.getOut().get(0));
    }

    @Test
    void example3PrintsTwo(@TempDir Path tempDir) throws MyException {
        PrgState state = run(Examples.EX3, tempDir);
        assertTrue(state.getExeStack().isEmpty());
        assertEquals(new IntValue(2), state.getOut().get(0));
    }

    @Test
    void oneStepOnEmptyStackThrows(@TempDir Path tempDir) {
        String logFilePath = tempDir.resolve("empty_stack_log.txt").toString();

        PrgState state = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new FileTable(), Examples.EX1);
        try {
            state.getExeStack().pop();
        } catch (Exception ignored) {}

        Controller ctr = new Controller(new Repository(state, logFilePath), false);
        var ex = assertThrows(StatementException.class, () -> ctr.oneStep(state));
        assertEquals("program state stack is empty", ex.getMessage());
    }
}