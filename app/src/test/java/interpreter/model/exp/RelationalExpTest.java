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
        // 5 > 2 -> true
        IExp e50 = new RelationalExp(RelationalOp.GREATER, num(5), num(2));
        assertEquals(new BoolValue(true), e50.eval(symTable));
        // 1 > 3 -> false
        IExp e51 = new RelationalExp(RelationalOp.GREATER,num(1),num(3));
        assertEquals(new BoolValue(false),e51.eval(symTable));
        // 2 < 5 -> true
        IExp e1 = new RelationalExp(RelationalOp.LESS, num(2), num(5));
        assertEquals(new BoolValue(true), e1.eval(symTable));
        // 3 < 1 -> false
        IExp e12 = new RelationalExp(RelationalOp.LESS,num(3),num(1));
        assertEquals(new BoolValue(false),e12.eval(symTable));
        // 5 >= 5 -> true
        IExp e2 = new RelationalExp(RelationalOp.GREATER_EQUAL, num(5), num(5));
        assertEquals(new BoolValue(true), e2.eval(symTable));
        // 5 >= 6 -> false
        IExp e22 = new RelationalExp(RelationalOp.GREATER_EQUAL, num(5), num(6));
        assertEquals(new BoolValue(false), e22.eval(symTable));

        // 5 <= 5 -> true
        IExp e99 = new RelationalExp(RelationalOp.LESS_EQUAL, num(5), num(5));
        assertEquals(new BoolValue(true), e99.eval(symTable));
        // 5 <= 4 -> false
        IExp e98 = new RelationalExp(RelationalOp.LESS_EQUAL, num(5), num(4));
        assertEquals(new BoolValue(false), e98.eval(symTable));

        // 3 == 4 -> false
        IExp e3 = new RelationalExp(RelationalOp.EQUAL, num(3), num(4));
        assertEquals(new BoolValue(false), e3.eval(symTable));
        // 4 == 4 -> true
        IExp e31 = new RelationalExp(RelationalOp.EQUAL, num(4), num(4));
        assertEquals(new BoolValue(true), e31.eval(symTable));
    }

    @Test
    void testNonIntegerOperandThrows() {
        IExp e = new RelationalExp(RelationalOp.LESS, num(1), new ValueExp(new BoolValue(true)));
        var ex = assertThrows(ExpressionException.class, () -> e.eval(symTable));
        assertEquals("second operand is not an integer", ex.getMessage());
    }
}