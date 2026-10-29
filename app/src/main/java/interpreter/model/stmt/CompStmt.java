package interpreter.model.stmt;
import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.adt.IStack;
public class CompStmt implements IStmt {
    private final IStmt first;
    private final IStmt second;

    public CompStmt(IStmt fst,IStmt snd){
        first = fst;
        second=snd;
    }
    @Override
    public PrgState execute(PrgState state) throws MyException{
        IStack<IStmt> stk = state.getExeStack();
        stk.push(second);
        stk.push(first);
        return null;
    }
    @Override
    public IStmt deepCopy(){
        return new CompStmt(first.deepCopy(),second.deepCopy());
    }
    @Override
    public String toString(){
        return "("+first.toString()+";"+second.toString()+")";
    }
}
