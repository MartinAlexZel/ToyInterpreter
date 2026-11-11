package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.IExp;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;

public record IfStmt(IExp cond, IStmt thenStmt, IStmt elseStmt) implements IStmt {
    @Override
    public PrgState execute(PrgState state) throws MyException {
        IValue val = cond.eval(state.getSymTable());
        if (!(val instanceof BoolValue(boolean b))) {
            throw new StatementException("conditional expression is not a boolean");
        }

        if (b) {
            state.getExeStack().push(thenStmt);
        } else {
            state.getExeStack().push(elseStmt);
        }
        return state;
    }

    @Override
    public String toString() {
        return "(if " + cond + " then " + thenStmt + " else " + elseStmt + ")";
    }
}
