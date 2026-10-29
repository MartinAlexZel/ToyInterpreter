package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.exp.IExp;

public class PrintStmt implements IStmt{
    private final IExp exp;
    public PrintStmt(IExp exp){
        this.exp = exp;
    }
    @Override
    public PrgState execute(PrgState state) throws MyException {
        state.getOut().add(exp.eval(state.getSymTable()));
        return null;
    }
    @Override
    public IStmt deepCopy(){
        return new PrintStmt(exp.deepCopy());
    }
    @Override
    public String toString(){
        return "print("+exp.toString()+")";
    }
}
