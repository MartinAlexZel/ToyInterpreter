package interpreter.repository;

import interpreter.exceptions.MyException;
import interpreter.model.PrgState;

import java.util.List;

public interface IRepo {
    void add(PrgState state);
    List<PrgState> getPrgList();
    void setPrgList(List<PrgState> prgStates);
    void logPrgStateExec(PrgState state) throws MyException;
}
