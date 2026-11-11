package interpreter.model;

import interpreter.model.adt.FileTable;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.stmt.NopStmt;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PrgStateTest {

    @Test
    void testPrgStateInitialization() {
        NopStmt program = new NopStmt();
        PrgState state = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(),new FileTable(), program);

        assertNotNull(state.getExeStack());
        assertNotNull(state.getSymTable());
        assertNotNull(state.getOut());
        assertEquals(program, state.getOriginalProgram());

        assertFalse(state.getExeStack().isEmpty());
    }
}