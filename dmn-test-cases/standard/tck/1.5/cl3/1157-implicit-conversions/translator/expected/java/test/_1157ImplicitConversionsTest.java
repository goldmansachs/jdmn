
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"junit.ftl", "1157-implicit-conversions.dmn"})
public class _1157ImplicitConversionsTest extends com.gs.dmn.runtime.JavaTimeDMNBaseDecision {
    @org.junit.jupiter.api.Test
    public void testCase001() {
        com.gs.dmn.runtime.ExecutionContext context_ = new com.gs.dmn.runtime.ExecutionContext();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();

        // Check 'To Singleton List'
        checkValues(asList("abc"), new ToSingletonList().apply(context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase002() {
        com.gs.dmn.runtime.ExecutionContext context_ = new com.gs.dmn.runtime.ExecutionContext();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();

        // Check 'From Singleton List'
        checkValues("abc", new FromSingletonList().apply(context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase002_a() {
        com.gs.dmn.runtime.ExecutionContext context_ = new com.gs.dmn.runtime.ExecutionContext();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();

        // Check 'From Singleton List Error'
        checkValues(null, new FromSingletonListError().apply(context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase003() {
        com.gs.dmn.runtime.ExecutionContext context_ = new com.gs.dmn.runtime.ExecutionContext();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();

        // Check 'From Date To Date and Time'
        checkValues(dateAndTime("2000-01-02T00:00:00Z"), new FromDateToDateAndTime().apply(context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase004() {
        com.gs.dmn.runtime.ExecutionContext context_ = new com.gs.dmn.runtime.ExecutionContext();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();

        // Check 'To Singleton List BKM'
        checkValues(asList(number("1")), ToSingletonListBKM.instance().apply(context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase005() {
        com.gs.dmn.runtime.ExecutionContext context_ = new com.gs.dmn.runtime.ExecutionContext();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();

        // Check 'From Singleton List BKM'
        checkValues(number("1"), FromSingletonListBKM.instance().apply(context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase006() {
        com.gs.dmn.runtime.ExecutionContext context_ = new com.gs.dmn.runtime.ExecutionContext();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();

        // Check 'From Date To Date and Time BKM'
        checkValues(dateAndTime("2000-01-02T00:00:00Z"), FromDateToDateAndTimeBKM.instance().apply(context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase007() {
        com.gs.dmn.runtime.ExecutionContext context_ = new com.gs.dmn.runtime.ExecutionContext();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();

        // Check 'To Singleton List DS'
        checkValues(asList(date("2000-01-02")), ToSingletonListDS.instance().apply(context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase008() {
        com.gs.dmn.runtime.ExecutionContext context_ = new com.gs.dmn.runtime.ExecutionContext();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();

        // Check 'From Singleton List DS'
        checkValues(date("2000-01-02"), FromSingletonListDS.instance().apply(context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase009() {
        com.gs.dmn.runtime.ExecutionContext context_ = new com.gs.dmn.runtime.ExecutionContext();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();

        // Check 'From Date to Date and Time DS'
        checkValues(dateAndTime("2000-01-02T00:00:00Z"), FromDateToDateAndTimeDS.instance().apply(context_));
    }

    private void checkValues(Object expected, Object actual) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual);
    }
}
