package interpreter.model.exp;
import interpreter.exceptions.MyException;
import interpreter.model.adt.IDictionary;
import interpreter.model.value.IValue;
public class ValueExp implements IExp{
    private final IValue e;
    public ValueExp(IValue e){
        this.e = e;
    }
    @Override
    public IValue eval(IDictionary<String,IValue> tbl) throws MyException{
        return e;
    }
    @Override
    public IExp deepCopy(){
        return new ValueExp(e.deepCopy());
    }
    @Override
    public String toString(){
        return e.toString();
    }

}
