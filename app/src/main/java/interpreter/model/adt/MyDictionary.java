package interpreter.model.adt;
import java.util.HashMap;
import java.util.Map;

public class MyDictionary<K,V> implements IDictionary<K,V>{
    private Map<K,V> dictionary;
    public MyDictionary(){
        this.dictionary = new HashMap<>();
    }
    @Override
    public void put(K key,V value){
        dictionary.put(key,value);
    }
    @Override
    public V get(K key){
        return dictionary.get(key);
    }
    @Override
    public boolean isDefined(K key){
        return dictionary.containsKey(key);
    }
    @Override
    public void update(K key,V value){
        dictionary.put(key,value);
    }
    @Override
    public void remove(K key){
        dictionary.remove(key);
    }
    @Override
    public Map<K,V> getContent(){
        return dictionary;
    }
    @Override
    public String toString(){
        return dictionary.toString();
    }
}
