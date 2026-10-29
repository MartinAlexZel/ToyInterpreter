package interpreter.model.exp;
import interpreter.exceptions.MyException;
import interpreter.model.adt.IDictionary;
import interpreter.model.adt.MyDictionary;
import interpreter.model.type.IntType;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;

public class ArithExp implements IExp {
    private final IExp e1;
    private final IExp e2;
    private final char op;
    public ArithExp(char op, IExp e1, IExp e2){
        this.e1 = e1;
        this.e2 = e2;
        this.op = op;
    }
    @Override
    public IValue eval(IDictionary<String,IValue> tbl) throws MyException{
        IValue v1,v2;
        v1 = e1.eval(tbl);
        if(v1.getType().equals(new IntType())){
            v2 = e2.eval(tbl);
            if(v2.getType().equals(new IntType())){
                int n1 = ((IntValue) v1).getVal();
                int n2 = ((IntValue) v2).getVal();
                switch (op){
                    case '+':return new IntValue(n1+n2);
                    case '-':return new IntValue(n1-n2);
                    case '*':return new IntValue(n1*n2);
                    case '/':
                        if(n2 == 0) throw new MyException("Division by zero!");
                        else return new IntValue((n1/n2));
                    default: throw new MyException("Invalid operator!");
                }
            }
            else{
                throw new MyException("Second operand is not an integer");
            }
        }
        else{
            throw new MyException("First operand is not an integer");
        }
    }
    @Override
    public IExp deepCopy(){
        return new ArithExp(op,e1,e2);
    }
    @Override
    public String toString(){
        return e1.toString()+" "+op+" "+e2.toString();
    }
}
