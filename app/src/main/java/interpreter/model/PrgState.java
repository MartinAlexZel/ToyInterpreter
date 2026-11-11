package interpreter.model;

import interpreter.model.adt.IDictionary;
import interpreter.model.adt.IFileTable;
import interpreter.model.adt.IList;
import interpreter.model.adt.IStack;
import interpreter.model.stmt.IStmt;
import interpreter.model.value.IValue;

import java.io.BufferedReader;

public class PrgState {
    private IStack<IStmt> exeStack;
    private IDictionary<String, IValue> symTable;
    private IList<IValue> out;
    private final IStmt originalProgram;
    private IFileTable<String, BufferedReader> fileTable;

    public PrgState(IStack<IStmt> exeStack, IDictionary<String, IValue> symTable, IList<IValue> out,IFileTable<String,BufferedReader> fileTable, IStmt program) {
        this.exeStack = exeStack;
        this.symTable = symTable;
        this.out = out;
        this.originalProgram = program;
        this.exeStack.push(program);
        this.fileTable = fileTable;

    }

    public IFileTable<String, BufferedReader> getFileTable() {
        return fileTable;
    }

    public IStack<IStmt> getExeStack() { return exeStack; }
    public void setExeStack(IStack<IStmt> exeStack) { this.exeStack = exeStack; }

    public IDictionary<String, IValue> getSymTable() { return symTable; }
    public void setSymTable(IDictionary<String, IValue> symTable) { this.symTable = symTable; }

    public IList<IValue> getOut() { return out; }
    public void setOut(IList<IValue> out) { this.out = out; }

    public IStmt getOriginalProgram() { return originalProgram; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("---------------------------------------\n");
        sb.append("ExeStack:\n").append(exeStack).append("\n");
        sb.append("SymTable:\n").append(symTable).append("\n");
        sb.append("Out:\n").append(out).append("\n");
        return sb.toString();
    }
}
