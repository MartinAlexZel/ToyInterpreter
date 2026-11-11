package interpreter.model.exp;

import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.model.adt.IDictionary;
import interpreter.model.adt.MyDictionary;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RelationalExpTest {
    private IDictionary<String, IValue> symTable;

    @BeforeEach
    void setUp() {
        symTable = new MyDictionary<>();
    }

    private static IExp num(int n) {
        return new ValueExp(new IntValue(n));
    }

    @Test
    void testRelationalOperators() throws MyException {
        // 2 < 5 -> true
        IExp e1 = new RelationalExp(RelationalOp.LESS, num(2), num(5));
        assertEquals(new BoolValue(true), e1.eval(symTable));

        // 5 >= 5 -> true
        IExp e2 = new RelationalExp(RelationalOp.GREATER_EQUAL, num(5), num(5));
        assertEquals(new BoolValue(true), e2.eval(symTable));

        // 3 == 4 -> false
        IExp e3 = new RelationalExp(RelationalOp.EQUAL, num(3), num(4));
        assertEquals(new BoolValue(false), e3.eval(symTable));
    }

    @Test
    void testNonIntegerOperandThrows() {
        IExp e = new RelationalExp(RelationalOp.LESS, num(1), new ValueExp(new BoolValue(true)));
        var ex = assertThrows(ExpressionException.class, () -> e.eval(symTable));
        assertEquals("second operand is not an integer", ex.getMessage());
    }
}