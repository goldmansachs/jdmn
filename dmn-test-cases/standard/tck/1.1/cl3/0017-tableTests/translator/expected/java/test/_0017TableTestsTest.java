
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"junit.ftl", "0017-tableTests.dmn"})
public class _0017TableTestsTest extends com.gs.dmn.runtime.JavaTimeDMNBaseDecision {
    @org.junit.jupiter.api.Test
    public void testCase001() {
        com.gs.dmn.runtime.ExecutionContext context_ = new com.gs.dmn.runtime.ExecutionContext();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();
        // Initialize input data
        type.TA structA = new type.TAImpl("widget", number("20"));

        // Check 'priceGt10'
        checkValues(Boolean.TRUE, new PriceGt10().apply(structA, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase002() {
        com.gs.dmn.runtime.ExecutionContext context_ = new com.gs.dmn.runtime.ExecutionContext();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();
        // Initialize input data
        type.TA structA = new type.TAImpl("widget", number("20"));
        java.lang.Number numB = number("9");
        java.lang.Number numC = number("10");

        // Check 'priceInRange'
        checkValues("Not in range", new PriceInRange().apply(numB, numC, structA, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase003() {
        com.gs.dmn.runtime.ExecutionContext context_ = new com.gs.dmn.runtime.ExecutionContext();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();
        // Initialize input data
        java.time.LocalDate dateD = date("2016-11-01");

        // Check 'dateCompare1'
        checkValues(Boolean.TRUE, new DateCompare1().apply(dateD, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase004() {
        com.gs.dmn.runtime.ExecutionContext context_ = new com.gs.dmn.runtime.ExecutionContext();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();
        // Initialize input data
        java.time.LocalDate dateD = date("2016-11-01");
        java.time.LocalDate dateE = date("2016-11-02");

        // Check 'dateCompare2'
        checkValues(Boolean.FALSE, new DateCompare2().apply(dateD, dateE, context_));
    }

    private void checkValues(Object expected, Object actual) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual);
    }
}
