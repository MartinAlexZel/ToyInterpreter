package interpreter.model.exp;
import interpreter.exceptions.MyException;
import interpreter.model.adt.IDictionary;
import interpreter.model.value.IValue;
public class VarExp implements IExp{
    private final String key;
    public VarExp(String key){
        this.key=key;
    }
    @Override
    public IValue eval(IDictionary<String,IValue> tbl) throws MyException{
        if(!tbl.isDefined(key)){
            throw new MyException("Variable "+key+" is not defined!");
        }
        return tbl.get(key);
    }
    @Override
    public IExp deepCopy(){
        return new VarExp(key);
    }
    @Override
    public String toString(){
        return key;
    }
}
