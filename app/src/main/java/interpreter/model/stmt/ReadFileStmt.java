package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.IExp;
import interpreter.model.type.IntType;
import interpreter.model.type.StringType;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import interpreter.model.value.StringValue;

import java.io.BufferedReader;
import java.io.IOException;

public record ReadFileStmt(IExp exp, String varName) implements IStmt {
    @Override
    public PrgState execute(PrgState state) throws MyException {
        var symTable = state.getSymTable();
        var fileTable = state.getFileTable();

        // Check if the var is declared in the symTable and if it is an int
        if (!symTable.isDefined(varName)) {
            throw new StatementException("Variable " + varName + " was not declared");
        }
        if (!symTable.lookup(varName).getType().equals(new IntType())) {
            throw new StatementException("Variable " + varName + " is not of type int");
        }

        //Eval expression to get the file path f
        IValue val = exp.eval(symTable);
        if (!val.getType().equals(new StringType())) {
            throw new StatementException("Expression evaluation in ReadFile is not a string type");
        }

        StringValue strVal = (StringValue) val;
        String filePath = strVal.val();

        // Check if the file is open
        if (!fileTable.isDefined(filePath)) {
            throw new StatementException("File " + filePath + " is not open");
        }

        BufferedReader reader = fileTable.lookup(filePath);

        // Read a line and convert it into an int
        int n;
        try {
            String line = reader.readLine();
            if (line == null) {
                n = 0;
            } else {
                n = Integer.parseInt(line.trim());
            }
        } catch (IOException e) {
            throw new StatementException("Could not read from file: " + filePath + " (" + e.getMessage() + ")");
        } catch (NumberFormatException e) {
            throw new StatementException("Line read from file is not a valid integer");
        }

        symTable.update(varName, new IntValue(n));

        return state;
    }

    @Override
    public String toString() {
        return "readFile(" + exp + ", " + varName + ")";
    }
}