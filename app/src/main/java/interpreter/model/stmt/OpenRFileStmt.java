package interpreter.model.stmt;

import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.IExp;
import interpreter.model.type.StringType;
import interpreter.model.value.IValue;
import interpreter.model.value.StringValue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public record OpenRFileStmt(IExp exp) implements IStmt {
    @Override
    public PrgState execute(PrgState state) throws MyException {
        var symTable = state.getSymTable();
        var fileTable = state.getFileTable();

        IValue val = exp.eval(symTable);

        if (!val.getType().equals(new StringType())) {
            throw new StatementException("Expression evaluation in OpenRFile is not a string type");
        }

        StringValue strVal = (StringValue) val;
        String filePath = strVal.val();

        if (fileTable.isDefined(filePath)) {
            throw new StatementException("File " + filePath + " is already opened");
        }

        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            fileTable.put(filePath, reader);
        } catch (IOException e) {
            throw new StatementException("Could not open file: " + filePath + " (" + e.getMessage() + ")");
        }

        return state;
    }

    @Override
    public String toString() {
        return "openRFile(" + exp + ")";
    }
}