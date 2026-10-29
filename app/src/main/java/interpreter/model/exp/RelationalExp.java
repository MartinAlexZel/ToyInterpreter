package interpreter.model.exp;

import interpreter.exceptions.MyException;
import interpreter.model.adt.IDictionary;
import interpreter.model.type.IntType;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;

public class RelationalExp implements IExp {
    private final IExp e1;
    private final IExp e2;
    private final String op;

    public RelationalExp(String op, IExp e1, IExp e2) {
        this.op = op;
        this.e1 = e1;
        this.e2 = e2;
    }

    @Override
    public IValue eval(IDictionary<String, IValue> tbl) throws MyException {
        IValue v1 = e1.eval(tbl);
        if (!v1.getType().equals(new IntType())) {
            throw new MyException("First operand is not an integer.");
        }

        IValue v2 = e2.eval(tbl);
        if (!v2.getType().equals(new IntType())) {
            throw new MyException("Second operand is not an integer.");
        }

        int int1 = ((IntValue) v1).getVal();
        int int2 = ((IntValue) v2).getVal();

        switch (op) {
            case "<":
                return new BoolValue(int1 < int2);
            case "<=":
                return new BoolValue(int1 <= int2);
            case "==":
                return new BoolValue(int1 == int2);
            case "!=":
                return new BoolValue(int1 != int2);
            case ">":
                return new BoolValue(int1 > int2);
            case ">=":
                return new BoolValue(int1 >= int2);
            default:
                throw new MyException("Invalid relational operator: " + op);
        }
    }

    @Override
    public IExp deepCopy() {
        return new RelationalExp(op, e1.deepCopy(), e2.deepCopy());
    }

    @Override
    public String toString() {
        return e1.toString() + " " + op + " " + e2.toString();
    }
}