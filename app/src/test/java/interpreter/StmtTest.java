package interpreter;

import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.exp.ValueExp;
import interpreter.model.stmt.AssignStmt;
import interpreter.model.stmt.IStmt;
import interpreter.model.stmt.IfStmt;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StmtTest {

    @Test
    public void testIfExecutionTrue() throws MyException {
        // if (true) then v = 10 else v = 20
        IStmt ifStmt = new IfStmt(
                new ValueExp(new BoolValue(true)),
                new AssignStmt("v", new ValueExp(new IntValue(10))),
                new AssignStmt("v", new ValueExp(new IntValue(20)))
        );

        PrgState state = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), ifStmt);

        IStmt crt = state.getExeStack().pop();
        crt.execute(state);

        assertFalse(state.getExeStack().isEmpty());

        IStmt branch = state.getExeStack().pop();
        branch.execute(state);

        assertEquals(new IntValue(10), state.getSymTable().get("v"));
    }
}