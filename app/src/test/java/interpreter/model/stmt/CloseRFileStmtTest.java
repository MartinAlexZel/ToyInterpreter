package interpreter.model.stmt;

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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.*;

class CloseRFileStmtTest {
    private PrgState state;

    @BeforeEach
    void setUp() {
        state = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new FileTable(), new NopStmt());
        try {
            state.getExeStack().pop(); // Remove initial NopStmt
        } catch (Exception ignored) {}
    }

    @Test
    void testCloseRFileNotAStringThrows() {
        // Case 1: Expression evaluates to a non-string type
        IStmt stmt = new CloseRFileStmt(new ValueExp(new IntValue(123)));
        var ex = assertThrows(StatementException.class, () -> stmt.execute(state));
        assertEquals("Expression evaluation in CloseRFile is not a string type", ex.getMessage());
    }

    @Test
    void testCloseRFileNotInFileTableThrows() {
        // Case 2: The file is not present in the FileTable
        String filePath = "unopened_file.txt";
        IStmt stmt = new CloseRFileStmt(new ValueExp(new StringValue(filePath)));
        var ex = assertThrows(StatementException.class, () -> stmt.execute(state));
        assertEquals("File " + filePath + " is not open", ex.getMessage());
    }

    @Test
    void testCloseRFileIoErrorThrows() throws IOException {
        // Case 3: Simulating an I/O error when closing the reader
        String filePath = "error_file.txt";

        // Create a custom BufferedReader whose close method throws an IOException
        BufferedReader faultyReader = new BufferedReader(new StringReader("test")) {
            @Override
            public void close() throws IOException {
                throw new IOException("Simulated disk error");
            }
        };

        state.getFileTable().put(filePath, faultyReader);

        IStmt stmt = new CloseRFileStmt(new ValueExp(new StringValue(filePath)));
        var ex = assertThrows(StatementException.class, () -> stmt.execute(state));
        assertTrue(ex.getMessage().contains("Could not close file"));
    }

    @Test
    void testValidCloseRFile() throws Exception {
        // Additional test: verifying successful closure and removal from FileTable
        String filePath = "valid_file.txt";
        BufferedReader reader = new BufferedReader(new StringReader("data"));
        state.getFileTable().put(filePath, reader);

        assertTrue(state.getFileTable().isDefined(filePath));

        IStmt stmt = new CloseRFileStmt(new ValueExp(new StringValue(filePath)));
        stmt.execute(state);

        assertFalse(state.getFileTable().isDefined(filePath));
    }
}