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
import org.junit.jupiter.api.io.TempDir; // <--- Import for @TempDir

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class OpenRFileStmtTest {
    private PrgState state;

    @BeforeEach
    void setUp() {
        state = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new FileTable(), new NopStmt());
        try {
            state.getExeStack().pop(); // Remove initial NopStmt
        } catch (Exception ignored) {}
    }

    @Test
    void testOpenRFileNotAStringThrows() {
        // Case 1: Expression evaluates to a non-string type (e.g., int)
        IStmt stmt = new OpenRFileStmt(new ValueExp(new IntValue(123)));
        var ex = assertThrows(StatementException.class, () -> stmt.execute(state));
        assertEquals("Expression evaluation in OpenRFile is not a string type", ex.getMessage());
    }

    @Test
    void testOpenRFileAlreadyExistsThrows(@TempDir Path tempDir) throws IOException {
        // Case 2: The file is already opened / already exists in the FileTable
        File tempFile = tempDir.resolve("duplicate.txt").toFile();
        assertTrue(tempFile.createNewFile());

        String filePath = tempFile.getAbsolutePath();

        // Open it once manually or via statement
        IStmt stmt = new OpenRFileStmt(new ValueExp(new StringValue(filePath)));
        stmt.execute(state);

        // Trying to open the same file again should throw an exception
        var ex = assertThrows(StatementException.class, () -> stmt.execute(state));
        assertTrue(ex.getMessage().contains("is already open"));
    }

    @Test
    void testOpenRFileDoesNotExistThrows() {
        // Case 3: The file does not exist on disk
        String nonExistentPath = "non_existent_file_12345.txt";
        IStmt stmt = new OpenRFileStmt(new ValueExp(new StringValue(nonExistentPath)));

        var ex = assertThrows(StatementException.class, () -> stmt.execute(state));
        assertTrue(ex.getMessage().contains("Could not open file"));
    }

    @Test
    void testValidOpenRFile(@TempDir Path tempDir) throws IOException {
        // Additional test: Successfully opening a valid temporary file using @TempDir
        File tempFile = tempDir.resolve("valid_test.in").toFile();
        assertTrue(tempFile.createNewFile());

        String filePath = tempFile.getAbsolutePath();
        IStmt stmt = new OpenRFileStmt(new ValueExp(new StringValue(filePath)));

        stmt.execute(state);

        // Verify that the file was successfully added to the FileTable
        assertTrue(state.getFileTable().isDefined(filePath));
    }
}