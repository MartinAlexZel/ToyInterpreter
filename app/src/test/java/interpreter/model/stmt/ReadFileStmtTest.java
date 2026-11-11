package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.adt.FileTable;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.exp.ValueExp;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import interpreter.model.value.StringValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir; // <--- Import for @TempDir

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class ReadFileStmtTest {
    private PrgState state;

    @BeforeEach
    void setUp() {
        state = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new FileTable(), new NopStmt());
        try {
            state.getExeStack().pop(); // Remove initial NopStmt
        } catch (Exception ignored) {}
    }

    @Test
    void testVarNotDeclaredOrNotIntThrows() throws MyException {
        // Case 1a: Variable is not declared in SymTable
        IStmt stmt1 = new ReadFileStmt(new ValueExp(new StringValue("test.txt")), "x");
        var ex1 = assertThrows(StatementException.class, () -> stmt1.execute(state));
        assertEquals("Variable x was not declared", ex1.getMessage());

        // Case 1b: Variable is declared, but it's not of type int (e.g., bool)
        state.getSymTable().put("flag", new BoolValue(false));
        IStmt stmt2 = new ReadFileStmt(new ValueExp(new StringValue("test.txt")), "flag");
        var ex2 = assertThrows(StatementException.class, () -> stmt2.execute(state));
        assertEquals("Variable flag is not of type int", ex2.getMessage());
    }

    @Test
    void testFileExpressionNotAStringThrows() {
        // Case 2: Expression evaluating the file path is not a string type (e.g., int)
        state.getSymTable().put("varInt", new IntValue(10));
        IStmt stmt = new ReadFileStmt(new ValueExp(new IntValue(123)), "varInt");
        var ex = assertThrows(StatementException.class, () -> stmt.execute(state));
        assertEquals("Expression evaluation in ReadFile is not a string type", ex.getMessage());
    }

    @Test
    void testFileNotInFileTableThrows() {
        // Case 3: The file path is not present in the FileTable (file not opened)
        state.getSymTable().put("varInt", new IntValue(0));
        IStmt stmt = new ReadFileStmt(new ValueExp(new StringValue("unopened.txt")), "varInt");
        var ex = assertThrows(StatementException.class, () -> stmt.execute(state));
        assertEquals("File unopened.txt is not open", ex.getMessage());
    }

    @Test
    void testLineNotAnIntegerThrows(@TempDir Path tempDir) throws IOException {
        // Case 4: The line read from file is not a valid integer (e.g., "abc") using @TempDir
        File tempFile = tempDir.resolve("invalid_number.txt").toFile();
        java.nio.file.Files.writeString(tempFile.toPath(), "abc\n");

        String filePath = tempFile.getAbsolutePath();
        BufferedReader reader = new BufferedReader(new FileReader(tempFile));
        state.getFileTable().put(filePath, reader);
        state.getSymTable().put("varInt", new IntValue(0));

        IStmt stmt = new ReadFileStmt(new ValueExp(new StringValue(filePath)), "varInt");
        var ex = assertThrows(StatementException.class, () -> stmt.execute(state));
        assertEquals("Line read from file is not a valid integer", ex.getMessage());
    }

    @Test
    void testValidReadAndEofAsZero(@TempDir Path tempDir) throws Exception {
        // Additional test: Successfully reading an integer and handling EOF (returning 0) using @TempDir
        File tempFile = tempDir.resolve("numbers.txt").toFile();
        java.nio.file.Files.writeString(tempFile.toPath(), "42\n");

        String filePath = tempFile.getAbsolutePath();
        BufferedReader reader = new BufferedReader(new FileReader(tempFile));
        state.getFileTable().put(filePath, reader);
        state.getSymTable().put("res", new IntValue(0));

        IStmt stmt = new ReadFileStmt(new ValueExp(new StringValue(filePath)), "res");

        // First read: should read 42
        stmt.execute(state);
        assertEquals(new IntValue(42), state.getSymTable().lookup("res"));

        // Second read : should set variable to 0
        stmt.execute(state);
        assertEquals(new IntValue(0), state.getSymTable().lookup("res"));
    }
}