
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"junit.ftl", "0020-vacation-days.dmn"})
public class _0020VacationDaysTest extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    @org.junit.Test
    public void testCase001() {
        com.gs.dmn.runtime.ExecutionContext context_ = new com.gs.dmn.runtime.ExecutionContext();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();
        // Initialize input data
        java.math.BigDecimal age = number("16");
        java.math.BigDecimal yearsOfService = number("1");

        // Check ''Total Vacation Days''
        checkValues(number("27"), new TotalVacationDays().apply(age, yearsOfService, context_));
    }

    @org.junit.Test
    public void testCase002() {
        com.gs.dmn.runtime.ExecutionContext context_ = new com.gs.dmn.runtime.ExecutionContext();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();
        // Initialize input data
        java.math.BigDecimal age = number("25");
        java.math.BigDecimal yearsOfService = number("5");

        // Check ''Total Vacation Days''
        checkValues(number("22"), new TotalVacationDays().apply(age, yearsOfService, context_));
    }

    @org.junit.Test
    public void testCase003() {
        com.gs.dmn.runtime.ExecutionContext context_ = new com.gs.dmn.runtime.ExecutionContext();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();
        // Initialize input data
        java.math.BigDecimal age = number("25");
        java.math.BigDecimal yearsOfService = number("20");

        // Check ''Total Vacation Days''
        checkValues(number("24"), new TotalVacationDays().apply(age, yearsOfService, context_));
    }

    @org.junit.Test
    public void testCase004() {
        com.gs.dmn.runtime.ExecutionContext context_ = new com.gs.dmn.runtime.ExecutionContext();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();
        // Initialize input data
        java.math.BigDecimal age = number("44");
        java.math.BigDecimal yearsOfService = number("30");

        // Check ''Total Vacation Days''
        checkValues(number("30"), new TotalVacationDays().apply(age, yearsOfService, context_));
    }

    @org.junit.Test
    public void testCase005() {
        com.gs.dmn.runtime.ExecutionContext context_ = new com.gs.dmn.runtime.ExecutionContext();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();
        // Initialize input data
        java.math.BigDecimal age = number("50");
        java.math.BigDecimal yearsOfService = number("20");

        // Check ''Total Vacation Days''
        checkValues(number("24"), new TotalVacationDays().apply(age, yearsOfService, context_));
    }

    @org.junit.Test
    public void testCase006() {
        com.gs.dmn.runtime.ExecutionContext context_ = new com.gs.dmn.runtime.ExecutionContext();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();
        // Initialize input data
        java.math.BigDecimal age = number("50");
        java.math.BigDecimal yearsOfService = number("30");

        // Check ''Total Vacation Days''
        checkValues(number("30"), new TotalVacationDays().apply(age, yearsOfService, context_));
    }

    @org.junit.Test
    public void testCase007() {
        com.gs.dmn.runtime.ExecutionContext context_ = new com.gs.dmn.runtime.ExecutionContext();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();
        // Initialize input data
        java.math.BigDecimal age = number("60");
        java.math.BigDecimal yearsOfService = number("20");

        // Check ''Total Vacation Days''
        checkValues(number("30"), new TotalVacationDays().apply(age, yearsOfService, context_));
    }

    private void checkValues(Object expected, Object actual) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual);
    }
}
