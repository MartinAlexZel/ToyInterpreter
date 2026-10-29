package interpreter;

import interpreter.exceptions.MyException;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.IDictionary;
import interpreter.model.exp.ArithExp;
import interpreter.model.exp.ValueExp;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ArithExpTest {

    @Test
    public void testAddition() throws MyException {
        ArithExp exp = new ArithExp('+', new ValueExp(new IntValue(10)), new ValueExp(new IntValue(5)));
        IDictionary<String, IValue> tbl = new MyDictionary<>();
        IValue result = exp.eval(tbl);
        assertEquals(new IntValue(15), result);
    }

    @Test
    public void testDivisionByZero() {
        ArithExp exp = new ArithExp('/', new ValueExp(new IntValue(10)), new ValueExp(new IntValue(0)));
        IDictionary<String, IValue> tbl = new MyDictionary();

        Exception exception = assertThrows(MyException.class, () -> exp.eval(tbl));
        assertEquals("Division by zero!", exception.getMessage());
    }
}