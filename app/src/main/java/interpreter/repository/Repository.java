package interpreter.repository;

import interpreter.exceptions.RepoException;
import interpreter.model.PrgState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository {
    private final List<PrgState> states;
    private final String logFilePath;
    public Repository(PrgState initialState, String logFilePath) {
        this.states = new ArrayList<>();
        this.states.add(initialState);
        this.logFilePath = logFilePath;
    }

    @Override
    public PrgState getCrtPrg() {
        return states.get(0);
    }

    @Override
    public void logPrgStateExec() throws RepoException {
        try (PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)))) {
            logFile.println(getCrtPrg().toString());
        } catch (IOException e) {
            throw new RepoException("Error writing to log file: " + e.getMessage());
        }
    }
}
