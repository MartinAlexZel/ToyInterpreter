package interpreter.model.adt;
import java.util.List;
public interface IStack<T>{
    T pop();
    void push(T v);
    boolean isEmpty();
    List<T> getReversed();
}
