package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.adt.FileTable;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.exp.ValueExp;
import interpreter.model.value.IntValue;
import interpreter.model.value.StringValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OpenRFileStmtTest {
    private PrgState state;

    @BeforeEach
    void setUp() {
        state = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new FileTable(), new NopStmt());
        try {
            state.getExeStack().pop();
        } catch (Exception ignored) {}
    }

    @Test
    void testOpenRFileNotAStringThrows() {
        IStmt stmt = new OpenRFileStmt(new ValueExp(new IntValue(123)));
        var ex = assertThrows(StatementException.class, () -> stmt.execute(state));
        assertEquals("Expression evaluation in OpenRFile is not a string type", ex.getMessage());
    }

    @Test
    void testOpenRFileAlreadyExistsThrows() throws MyException {
        String filePath = "test_file.txt";
        state.getFileTable().put(filePath, null);

        IStmt stmt = new OpenRFileStmt(new ValueExp(new StringValue(filePath)));
        var ex = assertThrows(StatementException.class, () -> stmt.execute(state));
        assertEquals("File " + filePath + " is already opened", ex.getMessage());
    }

    @Test
    void testOpenNonExistentFileThrows() {
        String filePath = "inexistent_abc_123.txt";
        IStmt stmt = new OpenRFileStmt(new ValueExp(new StringValue(filePath)));
        var ex = assertThrows(StatementException.class, () -> stmt.execute(state));
        assertTrue(ex.getMessage().contains("Could not open file"));
    }
}