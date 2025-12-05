
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"junit.ftl", "0020-vacation-days.dmn"})
public class _0020VacationDaysTest extends com.gs.dmn.runtime.JavaTimeDMNBaseDecision {
    @org.junit.jupiter.api.Test
    public void testCase001_1() {
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();
        // Initialize arguments
        java.lang.Number age = number("16");
        java.lang.Number yearsOfService = number("1");

        // Check 'Total Vacation Days'
        checkValues(number("27"), new TotalVacationDays().apply(age, yearsOfService, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase002_1() {
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();
        // Initialize arguments
        java.lang.Number age = number("25");
        java.lang.Number yearsOfService = number("5");

        // Check 'Total Vacation Days'
        checkValues(number("22"), new TotalVacationDays().apply(age, yearsOfService, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase003_1() {
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();
        // Initialize arguments
        java.lang.Number age = number("25");
        java.lang.Number yearsOfService = number("20");

        // Check 'Total Vacation Days'
        checkValues(number("24"), new TotalVacationDays().apply(age, yearsOfService, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase004_1() {
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();
        // Initialize arguments
        java.lang.Number age = number("44");
        java.lang.Number yearsOfService = number("30");

        // Check 'Total Vacation Days'
        checkValues(number("30"), new TotalVacationDays().apply(age, yearsOfService, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase005_1() {
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();
        // Initialize arguments
        java.lang.Number age = number("50");
        java.lang.Number yearsOfService = number("20");

        // Check 'Total Vacation Days'
        checkValues(number("24"), new TotalVacationDays().apply(age, yearsOfService, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase006_1() {
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();
        // Initialize arguments
        java.lang.Number age = number("50");
        java.lang.Number yearsOfService = number("30");

        // Check 'Total Vacation Days'
        checkValues(number("30"), new TotalVacationDays().apply(age, yearsOfService, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase007_1() {
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();
        // Initialize arguments
        java.lang.Number age = number("60");
        java.lang.Number yearsOfService = number("20");

        // Check 'Total Vacation Days'
        checkValues(number("30"), new TotalVacationDays().apply(age, yearsOfService, context_));
    }

    private void checkValues(Object expected, Object actual) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual);
    }
}
