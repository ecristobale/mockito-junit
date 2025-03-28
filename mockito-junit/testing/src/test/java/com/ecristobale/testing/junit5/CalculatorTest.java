package com.ecristobale.testing.junit5;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class CalculatorTest {

    @Test
    void testGenerated() {
        Calculator calculator = new Calculator();
        int result = calculator.add(1, 2);
        assert result == 3;
    }

    @Test
    void testEquals() {
        var calculator = new Calculator();
        int actual = calculator.add(2, 3);
        assertEquals(5, actual);
    }

    @Test
    void testNotEquals() {
        var calculator = new Calculator();
        int actual = calculator.add(2, 3);
        assertNotEquals(6, actual);
    }


}
