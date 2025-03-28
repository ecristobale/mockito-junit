package com.ecristobale.testing.junit5;

import org.junit.jupiter.api.Test;

public class TestCodeCoverageDemoTest {

    private TestCodeCoverageDemo testInstance = new TestCodeCoverageDemo();

    @Test
    void testStringNull() { // Run with coverage, open class and see 1 line in green, another in yellow and another in red
        testInstance.testCodeCoverage(null);
    }
}
