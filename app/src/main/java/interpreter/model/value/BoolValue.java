package interpreter.model.value;
import interpreter.model.type.IType;
import interpreter.model.type.BoolType;
public class BoolValue implements IValue{
    private final boolean val;
    public BoolValue(boolean val){
        this.val = val;
    }
    public boolean getVal(){
        return val;
    }
    @Override
    public IType getType(){
        return new BoolType();
    }
    @Override
    public IValue deepCopy(){
        return new BoolValue(val);
    }
    @Override
    public boolean equals(Object another){
        if(!(another instanceof BoolValue)){
            return false;
        }
        BoolValue cast = (BoolValue) another;
        return cast.getVal() == this.val;
    }
    @Override
    public String toString(){
        return String.valueOf(val);
    }

}
