package interpreter.model.stmt;
import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.adt.IDictionary;
import interpreter.model.exp.IExp;
import interpreter.model.type.IType;
import interpreter.model.value.IValue;

import javax.print.DocFlavor;

public class AssignStmt implements IStmt{
    private final String id;
    private final IExp exp;
    public AssignStmt(String id,IExp exp){
        this.id = id;
        this.exp = exp;
    }
    @Override
    public PrgState execute(PrgState state) throws MyException{
        IDictionary<String,IValue> symTbl = state.getSymTable();
        if(!symTbl.isDefined(id)){
            throw new MyException("The used variable "+id+" was not declared before");
        }
        IValue val = exp.eval(symTbl);
        IType typId = symTbl.get(id).getType();
        if(!val.getType().equals(typId)){
            throw new MyException("Declared type of variable " + id + " and type of the assigned expression do not match.");
        }
        symTbl.update(id,val);
        return null;
    }
    @Override
    public IStmt deepCopy(){
        return new AssignStmt(id,exp.deepCopy());
    }
    @Override
    public String toString(){
        return id+" = " + exp.toString();
    }


}
