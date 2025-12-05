package decisiontables;

import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"junit.ftl", "decisionTables"})
public class DecisionTablesTest extends com.gs.dmn.runtime.JavaTimeDMNBaseDecision {
    @org.junit.jupiter.api.Test
    public void testCase001_1() {
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();
        // Initialize arguments
        decisioninputs1.type.TA decisioninputs1_structA = new decisioninputs1.type.TAImpl("widget", number("20"));

        // Check 'priceGt10'
        checkValues(Boolean.TRUE, new decisiontables.PriceGt10().apply(decisioninputs1_structA, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase002_1() {
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();
        // Initialize arguments
        decisioninputs1.type.TA decisioninputs1_structA = new decisioninputs1.type.TAImpl("widget", number("20"));
        java.lang.Number decisioninputs1_numB = number("9");
        java.lang.Number decisioninputs1_numC = number("10");

        // Check 'priceInRange'
        checkValues("Not in range", new decisiontables.PriceInRange().apply(decisioninputs1_numB, decisioninputs1_numC, decisioninputs1_structA, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase003_1() {
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();
        // Initialize arguments
        java.time.LocalDate decisioninputs1_dateD = date("2016-11-01");

        // Check 'dateCompare1'
        checkValues(Boolean.TRUE, new decisiontables.DateCompare1().apply(decisioninputs1_dateD, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase004_1() {
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();
        // Initialize arguments
        java.time.LocalDate decisioninputs1_dateD = date("2016-11-01");
        java.time.LocalDate decisioninputs2_dateD = date("2016-11-02");

        // Check 'dateCompare2'
        checkValues(Boolean.FALSE, new decisiontables.DateCompare2().apply(decisioninputs1_dateD, decisioninputs2_dateD, context_));
    }

    private void checkValues(Object expected, Object actual) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual);
    }
}
