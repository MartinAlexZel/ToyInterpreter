package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.adt.IDictionary;
import interpreter.model.type.IType;
import interpreter.model.value.IValue;

public class VarDeclStmt implements IStmt {
    private final String name;
    private final IType typ;
    public VarDeclStmt(String name,IType typ){
        this.name=name;
        this.typ=typ;
    }
    @Override
    public PrgState execute(PrgState state) throws MyException{
        IDictionary<String,IValue> symTable = state.getSymTable();
        if(symTable.isDefined(name)){
            throw new MyException("Variable "+name+ " already exists.");
        }
        symTable.put(name,typ.defaultValue());
        return null;
    }
    @Override
    public IStmt deepCopy(){
        return new VarDeclStmt(name,typ.deepCopy());
    }
    @Override
    public String toString(){
        return typ.toString() + " " +name;
    }
}
