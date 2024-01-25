package com.gs.dmn.transformation;

import com.gs.dmn.ast.TUnaryTests;
import com.gs.dmn.error.NopErrorHandler;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

public class SpecialVariableTransformerVisitorTest {
    private final SpecialVariableTransformerVisitor<?> visitor = new SpecialVariableTransformerVisitor<>(new NopErrorHandler());

    @Test
    public void testUpdateUnaryTests() {
        // TODO Add more tests
        doTest("contains(x.y.z, \"123\")", "x.y.z", "contains(?, \"123\")");
        doTest("review.nextActionNew", "review.nextAction", "review.nextActionNew");
    }

    private void doTest(String unaryTestsText, String inputExpression, String expectedValue) {
        TUnaryTests unaryTests = new TUnaryTests();
        unaryTests.setText(unaryTestsText);
        visitor.updateUnaryTests(unaryTests, inputExpression);
        assertEquals(expectedValue, unaryTests.getText());
    }
}