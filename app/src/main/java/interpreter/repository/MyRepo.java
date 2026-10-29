package interpreter.repository;

import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class MyRepo implements IRepo{
    private List<PrgState> prgStates;
    private String logFilePath;

    public MyRepo(String logFilePath){
        this.prgStates = new ArrayList<>();
        this.logFilePath = logFilePath;
    }
    @Override
    public void add(PrgState state){
        prgStates.add(state);
    }
    @Override
    public List<PrgState> getPrgList(){
        return prgStates;
    }
    @Override
    public void setPrgList(List<PrgState> prgStates){
        this.prgStates = prgStates;
    }
    @Override
    public void logPrgStateExec(PrgState state) throws MyException{
        try(PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath,true)))){
            logFile.println(state.toString());
        }
        catch (IOException e){
            throw new MyException("Error writing to log file: "+e.getMessage());
        }

    }
}
