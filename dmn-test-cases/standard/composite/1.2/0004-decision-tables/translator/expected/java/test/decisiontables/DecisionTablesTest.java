package decisiontables;

import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"junit.ftl", "decisionTables"})
public class DecisionTablesTest extends com.gs.dmn.runtime.JavaTimeDMNBaseDecision {
    @org.junit.jupiter.api.Test
    public void testCase001_1() {
        // Initialize arguments
        decisioninputs.type.TA decisioninputs_structA = new decisioninputs.type.TAImpl("widget", number("20"));

        // Check 'priceGt10'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues(Boolean.TRUE, new decisiontables.PriceGt10().apply(decisioninputs_structA, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase002_1() {
        // Initialize arguments
        decisioninputs.type.TA decisioninputs_structA = new decisioninputs.type.TAImpl("widget", number("20"));
        java.lang.Number decisioninputs_numB = number("9");
        java.lang.Number decisioninputs_numC = number("10");

        // Check 'priceInRange'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues("Not in range", new decisiontables.PriceInRange().apply(decisioninputs_numB, decisioninputs_numC, decisioninputs_structA, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase003_1() {
        // Initialize arguments
        java.time.LocalDate decisioninputs_dateD = date("2016-11-01");

        // Check 'dateCompare1'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues(Boolean.TRUE, new decisiontables.DateCompare1().apply(decisioninputs_dateD, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase004_1() {
        // Initialize arguments
        java.time.LocalDate decisioninputs_dateD = date("2016-11-01");
        java.time.LocalDate decisioninputs_dateE = date("2016-11-02");

        // Check 'dateCompare2'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues(Boolean.FALSE, new decisiontables.DateCompare2().apply(decisioninputs_dateD, decisioninputs_dateE, context_));
    }

    private void checkValues(Object expected, Object actual) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual);
    }
}
