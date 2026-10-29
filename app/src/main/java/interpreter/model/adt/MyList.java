package interpreter.model.adt;
import java.util.ArrayList;
import java.util.List;
public class MyList<T> implements IList<T>{
    private List<T> list;
    public MyList(){
        list = new ArrayList<>();
    }
    @Override
    public void add(T elem){
        list.add(elem);
    }
    @Override
    public T get(int index){
        return list.get(index);
    }
    @Override
    public int size(){
        return list.size();
    }
    @Override
    public List<T> getContent(){
        return list;
    }
    @Override
    public String toString(){
        return list.toString();
    }
    @Override
    public T remove(int index) {
        return list.remove(index);
    }

    @Override
    public boolean remove(T elem) {
        return list.remove(elem);
    }

}
