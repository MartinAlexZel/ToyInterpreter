package interpreter.model.type;
import interpreter.model.value.IntValue;
import interpreter.model.value.IValue;

public class IntType implements IType{
    @Override
    public boolean equals(Object another){
        return another instanceof IntType;
    }
    @Override
    public IValue defaultValue(){
        return new IntValue(0);
    }
    @Override
    public IType deepCopy(){
        return new IntType();
    }
    @Override
    public String toString(){
        return "int";
    }

}
