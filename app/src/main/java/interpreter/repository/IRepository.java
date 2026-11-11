package interpreter.repository;

import interpreter.exceptions.RepoException;
import interpreter.model.PrgState;

public interface IRepository {
    PrgState getCrtPrg();
    void logPrgStateExec() throws RepoException;
}
