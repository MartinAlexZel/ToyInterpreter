package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.adt.FileTable;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.exp.ValueExp;
import interpreter.model.exp.VarExp;
import interpreter.model.type.BoolType;
import interpreter.model.type.IntType;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import interpreter.model.value.StringValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.*;

class ReadFileStmtTest {
    private PrgState state;

    @BeforeEach
    void setUp() {
        state = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new FileTable(), new NopStmt());
        try {
            state.getExeStack().pop();
        } catch (Exception ignored) {}
    }

    @Test
    void testVarNotDeclaredOrNotIntThrows() throws MyException {

        IStmt stmt1 = new ReadFileStmt(new ValueExp(new StringValue("test.txt")), "x");
        var ex1 = assertThrows(StatementException.class, () -> stmt1.execute(state));
        assertEquals("Variable x was not declared", ex1.getMessage());


        state.getSymTable().put("flag", new BoolValue(true));

        state.getSymTable().put("flag", new BoolValue(false));
        IStmt stmt2 = new ReadFileStmt(new ValueExp(new StringValue("test.txt")), "flag");
        var ex2 = assertThrows(StatementException.class, () -> stmt2.execute(state));
        assertEquals("Variable flag is not of type int", ex2.getMessage());
    }

    @Test
    void testFileExpressionNotAStringThrows() {

        state.getSymTable().put("varInt", new IntValue(10));
        IStmt stmt = new ReadFileStmt(new ValueExp(new IntValue(123)), "varInt");
        var ex = assertThrows(StatementException.class, () -> stmt.execute(state));
        assertEquals("Expression evaluation in ReadFile is not a string type", ex.getMessage());
    }

    @Test
    void testFileNotInFileTableThrows() {

        state.getSymTable().put("varInt", new IntValue(0));
        IStmt stmt = new ReadFileStmt(new ValueExp(new StringValue("unopened.txt")), "varInt");
        var ex = assertThrows(StatementException.class, () -> stmt.execute(state));
        assertEquals("File unopened.txt is not open", ex.getMessage());
    }

    @Test
    void testLineNotAnIntegerThrows() throws MyException {

        state.getSymTable().put("varInt", new IntValue(0));
        String filePath = "data.txt";
        BufferedReader reader = new BufferedReader(new StringReader("abc\n"));
        state.getFileTable().put(filePath, reader);

        IStmt stmt = new ReadFileStmt(new ValueExp(new StringValue(filePath)), "varInt");
        var ex = assertThrows(StatementException.class, () -> stmt.execute(state));
        assertEquals("Line read from file is not a valid integer", ex.getMessage());
    }

    @Test
    void testValidReadAndEofAsZero() throws MyException {

        state.getSymTable().put("res", new IntValue(0));
        String filePath = "numbers.txt";
        BufferedReader reader = new BufferedReader(new StringReader("42\n"));
        state.getFileTable().put(filePath, reader);

        IStmt stmt = new ReadFileStmt(new ValueExp(new StringValue(filePath)), "res");
        stmt.execute(state);
        assertEquals(new IntValue(42), state.getSymTable().lookup("res"));


        stmt.execute(state);
        assertEquals(new IntValue(0), state.getSymTable().lookup("res"));
    }
}