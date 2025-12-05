
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"junit.ftl", "9001-recursive-function.dmn"})
public class _9001RecursiveFunctionTest extends com.gs.dmn.runtime.JavaTimeDMNBaseDecision {
    @org.junit.jupiter.api.Test
    public void testCase001_1() {
        // Initialize arguments
        java.lang.Number n = number("0");

        // Check 'main'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues(number("1"), new Main().apply(n, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase002_1() {
        // Initialize arguments
        java.lang.Number n = number("1");

        // Check 'main'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues(number("1"), new Main().apply(n, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase003_1() {
        // Initialize arguments
        java.lang.Number n = number("3");

        // Check 'main'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues(number("6"), new Main().apply(n, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase004_1() {
        // Initialize arguments
        java.lang.Number n = number("-1");

        // Check 'main'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues(null, new Main().apply(n, context_));
    }

    private void checkValues(Object expected, Object actual) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual);
    }
}
