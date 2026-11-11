package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.adt.FileTable;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.exp.ValueExp;
import interpreter.model.type.BoolType;
import interpreter.model.type.IntType;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StmtTest {
    private PrgState state;

    @BeforeEach
    void setUp() {
        state = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(),new FileTable(), new NopStmt());
        try {
            state.getExeStack().pop(); // Clear initial nop
        } catch (Exception ignored) {}
    }

    @Test
    void testRedeclaredVariableThrows() throws MyException {
        IStmt stmt1 = new VarDeclStmt("a", new IntType());
        stmt1.execute(state);
        IStmt stmt2 = new VarDeclStmt("a", new BoolType());
        var ex = assertThrows(StatementException.class, () -> stmt2.execute(state));
        assertEquals("variable a is already declared", ex.getMessage());
    }

    @Test
    void testAssignTypeMismatchThrows() throws MyException {
        new VarDeclStmt("a", new IntType()).execute(state);
        IStmt assign = new AssignStmt("a", new ValueExp(new BoolValue(true)));
        var ex = assertThrows(StatementException.class, () -> assign.execute(state));
        assertEquals("declared type of variable a and type of the assigned expression do not match", ex.getMessage());
    }
}
