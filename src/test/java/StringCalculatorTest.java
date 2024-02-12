import com.tdd.stringcalculator.StringCalculator;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class StringCalculatorTest {
    @Test
    public void a_testEmptyString() {
        assertEquals(0, StringCalculator.add(""));
    }

    @Test
    public void b_testSingleNumber() {
        assertEquals(1, StringCalculator.add("1"));
    }

    @Test
    public void c_testTwoNumbers() {
        assertEquals(3, StringCalculator.add("1,2"));
    }

    @Test
    public void d_testNewLinesBetweenNumbers() {
        assertEquals(6, StringCalculator.add("1\n2,3"));
    }

    @Test
    public void e_testNegativeNumbers() {
        assertThrows(IllegalArgumentException.class, () -> StringCalculator.add("-1,2,-3"));
    }

    @Test
    public void f_testCustomDelimiter() {
        assertEquals(3, StringCalculator.add("//;\n1;2"));
    }

    @Test
    public void g_testCustomDelimiterWithAnyLength() {
        assertEquals(6, StringCalculator.add("//[***]\n1***2***3"));
    }

    @Test
    public void h_testMultipleDelimiters() {
        assertEquals(6, StringCalculator.add("//[*][%]\n1*2%3"));
    }

    @Test
    public void i_testMultipleDelimitersWithLengthGreaterThanOne() {
        assertEquals(6, StringCalculator.add("//[**][%%]\n1**2%%3"));
    }

    @Test
    public void j_testGetCalledCount() {
        assertEquals(9, StringCalculator.getCalledCount());
    }
}
