package interpreter.model.type;
import interpreter.model.value.IValue;
public interface IType {
    boolean equals(Object another);
    IValue defaultValue();
    IType deepCopy();
}
