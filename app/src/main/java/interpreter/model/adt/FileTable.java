package interpreter.model.adt;

import interpreter.exceptions.ADTException;
import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;
public class FileTable implements IFileTable<String,BufferedReader>{
    private final Map<String,BufferedReader> fileTable;
    public FileTable(){
        this.fileTable = new HashMap<>();
    }
    @Override
    public boolean isDefined(String key){
        return fileTable.containsKey(key);
    }
    @Override
    public BufferedReader lookup(String key) throws ADTException{
        if(!isDefined(key)){
            throw new ADTException("File "+key+" is not open.");
        }
        return fileTable.get(key);
    }
    @Override
    public void put(String key, BufferedReader value) {
        fileTable.put(key, value);
    }

    @Override
    public void update(String key, BufferedReader value) throws ADTException {
        if (!isDefined(key)) {
            throw new ADTException("File " + key + " is not defined for update.");
        }
        fileTable.put(key, value);
    }

    @Override
    public void remove(String key) throws ADTException {
        if (!isDefined(key)) {
            throw new ADTException("File " + key + " is not open, cannot close.");
        }
        fileTable.remove(key);
    }

    @Override
    public Map<String, BufferedReader> getContent() {
        return fileTable;
    }

    @Override
    public String toString() {
        return fileTable.toString();
    }
}

