
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"junit.ftl", "9001-recursive-function.dmn"})
public class _9001RecursiveFunctionTest extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    @org.junit.jupiter.api.Test
    public void testCase001() {
        com.gs.dmn.runtime.ExecutionContext context_ = new com.gs.dmn.runtime.ExecutionContext();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();
        // Initialize input data
        java.math.BigDecimal n = number("0");

        // Check 'main'
        checkValues(number("1"), new Main().apply(n, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase002() {
        com.gs.dmn.runtime.ExecutionContext context_ = new com.gs.dmn.runtime.ExecutionContext();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();
        // Initialize input data
        java.math.BigDecimal n = number("1");

        // Check 'main'
        checkValues(number("1"), new Main().apply(n, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase003() {
        com.gs.dmn.runtime.ExecutionContext context_ = new com.gs.dmn.runtime.ExecutionContext();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();
        // Initialize input data
        java.math.BigDecimal n = number("3");

        // Check 'main'
        checkValues(number("6"), new Main().apply(n, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase004() {
        com.gs.dmn.runtime.ExecutionContext context_ = new com.gs.dmn.runtime.ExecutionContext();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();
        // Initialize input data
        java.math.BigDecimal n = number("-1");

        // Check 'main'
        checkValues(null, new Main().apply(n, context_));
    }

    private void checkValues(Object expected, Object actual) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual);
    }
}
