package interpreter.model.exp;

import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.exceptions.ExpressionException;
import interpreter.model.adt.IDictionary;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;

public class LogicExp implements IExp {
    private final IExp left;
    private final IExp right;
    private final String op;

    public LogicExp(String op,IExp left,IExp right){
        this.op = op;
        this.left = left;
        this.right = right;
    }

    @Override
    public IValue eval(IDictionary<String, IValue> symTable) throws MyException {
        IValue v1 = left.eval(symTable);
        if (!(v1 instanceof BoolValue(boolean b1))) {
            throw new ExpressionException("first operand is not a boolean");
        }

        IValue v2 = right.eval(symTable);
        if (!(v2 instanceof BoolValue(boolean b2))) {
            throw new ExpressionException("second operand is not a boolean");
        }

        switch (op) {
            case "AND": return new BoolValue(b1 && b2);
            case "OR": return new BoolValue(b1 || b2);
            default:
                throw new ExpressionException("Invalid logical operator: "+op);
        };
    }

    @Override
    public IExp deepCopy(){
        return new LogicExp(op,left.deepCopy(),right.deepCopy());
    }

    @Override
    public String toString() {
        return "(" + left + " " + op + " " + right + ")";
    }
}
