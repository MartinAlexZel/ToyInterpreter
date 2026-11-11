package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.IExp;
import interpreter.model.type.StringType;
import interpreter.model.value.IValue;
import interpreter.model.value.StringValue;

import java.io.BufferedReader;
import java.io.IOException;

public record CloseRFileStmt(IExp exp) implements IStmt {
    @Override
    public PrgState execute(PrgState state) throws MyException {
        var symTable = state.getSymTable();
        var fileTable = state.getFileTable();

        // Eval to get the file path
        IValue val = exp.eval(symTable);
        if (!val.getType().equals(new StringType())) {
            throw new StatementException("Expression evaluation in CloseRFile is not a string type");
        }

        StringValue strVal = (StringValue) val;
        String filePath = strVal.val();

        // Check if the file is open
        if (!fileTable.isDefined(filePath)) {
            throw new StatementException("File " + filePath + " is not open");
        }

        BufferedReader reader = fileTable.lookup(filePath);

        // Close the reader and remove it from FileTable
        try {
            reader.close();
            fileTable.remove(filePath);
        } catch (IOException e) {
            throw new StatementException("Could not close file: " + filePath + " (" + e.getMessage() + ")");
        }

        return state;
    }

    @Override
    public String toString() {
        return "closeRFile(" + exp + ")";
    }
}