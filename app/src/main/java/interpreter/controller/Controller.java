package interpreter.controller;

import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.repository.IRepo;

public class Controller {
    private IRepo repo;
    public Controller(IRepo repo){
        this.repo =repo;
    }
    public PrgState oneStep(PrgState state) throws MyException{
        var stk = state.getExeStack();
        if(stk.isEmpty()){
            throw new MyException("The execution stack is empty!");
        }
        var crtStmt = stk.pop();
        return crtStmt.execute(state);
    }
    public void allStep() throws MyException {
        PrgState prg = repo.getPrgList().get(0);
        repo.logPrgStateExec(prg);
        while (!prg.getExeStack().isEmpty()) {
            oneStep(prg);
            repo.logPrgStateExec(prg);
        }
    }
}
