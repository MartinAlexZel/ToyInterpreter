package interpreter.model;
import interpreter.exceptions.MyException;
import interpreter.model.adt.IDictionary;
import interpreter.model.adt.IList;
import interpreter.model.adt.IStack;
import interpreter.model.adt.MyStack;
import interpreter.model.stmt.IStmt;
import interpreter.model.value.IValue;

public class PrgState {
    private IStack<IStmt> exeStack;
    private IDictionary<String,IValue> symTable;
    private IList<IValue> out;
    private IStmt originalProgram;

    public PrgState(IStack<IStmt> exeStack,IDictionary<String,IValue> symTable,IList<IValue> out,IStmt prg){
        this.exeStack = exeStack;
        this.symTable = symTable;
        this.out = out;
        this.originalProgram = prg.deepCopy();
        this.exeStack.push(prg);
    }
    public IStack<IStmt> getExeStack(){
        return exeStack;
    }
    public IDictionary<String,IValue> getSymTable(){
        return symTable;
    }
    public IList<IValue> getOut(){
        return out;
    }
    @Override
    public String toString(){
        return "ExeStack:\n" + exeStack.toString() + "\nSymTable:\n" + symTable.toString() + "\nOut:\n" + out.toString() + "\n-----------------\n";
    }
}
