package interpreter.model.exp;

import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.model.adt.IDictionary;
import interpreter.model.adt.MyDictionary;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LogicExpTest {
    private IDictionary<String, IValue> symTable;

    @BeforeEach
    void setUp() {
        symTable = new MyDictionary<>();
    }

    @Test
    void testLogicAndOr() throws MyException {
        IExp e1 = new LogicExp(LogicOp.AND, new ValueExp(new BoolValue(true)), new ValueExp(new BoolValue(false)));
        assertEquals(new BoolValue(false), e1.eval(symTable));

        IExp e2 = new LogicExp(LogicOp.OR, new ValueExp(new BoolValue(true)), new ValueExp(new BoolValue(false)));
        assertEquals(new BoolValue(true), e2.eval(symTable));
    }

    @Test
    void testNonBooleanOperandThrows() {
        IExp e = new LogicExp(LogicOp.AND, new ValueExp(new BoolValue(true)), new VarExp("missing"));
        var ex = assertThrows(ExpressionException.class, () -> e.eval(symTable));
        assertEquals("variable missing is not defined", ex.getMessage());
    }
}