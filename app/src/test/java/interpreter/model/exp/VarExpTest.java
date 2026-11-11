package interpreter.model.exp;

import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.model.adt.IDictionary;
import interpreter.model.adt.MyDictionary;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VarExpTest {
    private IDictionary<String, IValue> symTable;

    @BeforeEach
    void setUp() {
        symTable = new MyDictionary<>();
    }

    @Test
    void testVarExpEval() throws MyException {
        symTable.put("x", new IntValue(50));
        IExp exp = new VarExp("x");
        assertEquals(new IntValue(50), exp.eval(symTable));
    }

    @Test
    void testUndefinedVarThrows() {
        IExp exp = new VarExp("y");
        var ex = assertThrows(ExpressionException.class, () -> exp.eval(symTable));
        assertEquals("variable y is not defined", ex.getMessage());
    }
}