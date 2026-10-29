package interpreter.model.value;
import interpreter.model.type.IntType;
import interpreter.model.type.IType;
public class IntValue implements IValue{
    private final int val;
    public IntValue(int val){
        this.val = val;
    }
    public int getVal() {
        return val;
    }
    @Override
    public IType getType(){
        return new IntType();
    }
    @Override
    public IValue deepCopy(){
        return new IntValue(val);
    }
    @Override
    public boolean equals(Object another){
        if(!(another instanceof IntValue)){
            return false;
        }
        IntValue casted = (IntValue) another;
        return casted.getVal() == this.val;
    }
    @Override
    public String toString(){
        return String.valueOf(val);
    }
}
