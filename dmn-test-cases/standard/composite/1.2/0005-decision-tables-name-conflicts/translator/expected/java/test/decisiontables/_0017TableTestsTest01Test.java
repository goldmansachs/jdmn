package decisiontables;

import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"junit.ftl", "decisionTables"})
public class _0017TableTestsTest01Test extends com.gs.dmn.runtime.JavaTimeDMNBaseDecision<Object> {
    @org.junit.jupiter.api.Test
    public void testCase001_1() {
        // Initialize inputs
        decisioninputs1.type.TA decisioninputs1_structA = new decisioninputs1.type.TAImpl("widget", number("20"));

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();
        input_.add("decisionInputs1.structA", decisioninputs1_structA);

        // Check 'priceGt10'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues(Boolean.TRUE, new decisiontables.PriceGt10().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase002_1() {
        // Initialize inputs
        decisioninputs1.type.TA decisioninputs1_structA = new decisioninputs1.type.TAImpl("widget", number("20"));
        java.lang.Number decisioninputs1_numB = number("9");
        java.lang.Number decisioninputs1_numC = number("10");

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();
        input_.add("decisionInputs1.structA", decisioninputs1_structA);
        input_.add("decisionInputs1.numB", decisioninputs1_numB);
        input_.add("decisionInputs1.numC", decisioninputs1_numC);

        // Check 'priceInRange'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues("Not in range", new decisiontables.PriceInRange().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase003_1() {
        // Initialize inputs
        java.time.LocalDate decisioninputs1_dateD = date("2016-11-01");

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();
        input_.add("decisionInputs1.dateD", decisioninputs1_dateD);

        // Check 'dateCompare1'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues(Boolean.TRUE, new decisiontables.DateCompare1().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase004_1() {
        // Initialize inputs
        java.time.LocalDate decisioninputs1_dateD = date("2016-11-01");
        java.time.LocalDate decisioninputs2_dateD = date("2016-11-02");

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();
        input_.add("decisionInputs1.dateD", decisioninputs1_dateD);
        input_.add("decisionInputs2.dateD", decisioninputs2_dateD);

        // Check 'dateCompare2'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues(Boolean.FALSE, new decisiontables.DateCompare2().applyContext(input_, context_));
    }

    private void checkValues(Object expected, Object actual) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual);
    }
}
