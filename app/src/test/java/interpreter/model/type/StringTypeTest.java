package interpreter.model.type;

import interpreter.model.value.IValue;
import interpreter.model.value.StringValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringTypeTest {

    @Test
    void testDefaultValue() {
        IType stringType = new StringType();
        IValue defaultValue = stringType.defaultValue();

       //default value
        assertEquals(new StringValue(""), defaultValue);
        assertEquals("string", stringType.toString());
    }

    @Test
    void testStringValueEqualsAndHashCode() {
        StringValue val1 = new StringValue("hello");
        StringValue val2 = new StringValue("hello");
        StringValue val3 = new StringValue("world");

        //equals
        assertEquals(val1, val2);
        assertNotEquals(val1, val3);
        assertNotEquals(val1, null);
        assertNotEquals(val1, "hello"); // other type of object

        //hashcode
        assertEquals(val1.hashCode(), val2.hashCode());
    }

    @Test
    void testStringTypeEqualsAndHashCode() {
        IType type1 = new StringType();
        IType type2 = new StringType();
        IType intType = new IntType();

        assertEquals(type1, type2);
        assertNotEquals(type1, intType);
        assertEquals(type1.hashCode(), type2.hashCode());
    }
}