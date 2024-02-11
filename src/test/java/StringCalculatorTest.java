import com.tdd.stringcalculator.StringCalculator;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class StringCalculatorTest {

    final private StringCalculator calculator = new StringCalculator();

    @Test
    public void testEmptyString() {
        assertEquals(0, calculator.Add(""));
    }

    @Test
    public void testSingleNumber() {
        assertEquals(1, calculator.Add("1"));
    }

    @Test
    public void testTwoNumbers() {
        assertEquals(3, calculator.Add("1,2"));
    }
    @Test
    public void testNewLinesBetweenNumbers() {
        StringCalculator calculator = new StringCalculator();
        assertEquals(6, calculator.Add("1\n2,3"));
    }

    @Test
    public void testNegativeNumbers() {
        StringCalculator calculator = new StringCalculator();
        assertThrows(IllegalArgumentException.class, () -> calculator.Add("-1,2,-3"));
    }

    @Test
    public void testGetCalledCount() {
        StringCalculator calculator = new StringCalculator();
        calculator.Add("1,2");
        calculator.Add("//;\n1;2");
        assertEquals(2, calculator.GetCalledCount());
    }

    @Test
    public void testCustomDelimiter() {
        StringCalculator calculator = new StringCalculator();
        assertEquals(3, calculator.Add("//;\n1;2"));
    }

}
