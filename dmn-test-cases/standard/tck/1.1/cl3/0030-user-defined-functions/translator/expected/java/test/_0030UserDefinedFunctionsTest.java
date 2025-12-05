
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"junit.ftl", "0030-user-defined-functions.dmn"})
public class _0030UserDefinedFunctionsTest extends com.gs.dmn.runtime.JavaTimeDMNBaseDecision {
    @org.junit.jupiter.api.Test
    public void testCase001_1() {
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();
        // Initialize arguments
        String stringInputA = "feel";
        String stringInputB = "#";

        // Check 'simple function invocation'
        checkValues("feel#feel#", new SimpleFunctionInvocation().apply(stringInputA, stringInputB, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase001_2() {
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();
        // Initialize arguments
        String stringInputA = "feel";
        String stringInputB = "#";

        // Check 'named function invocation'
        checkValues("#feel#feel", new NamedFunctionInvocation().apply(stringInputA, stringInputB, context_));
    }

    private void checkValues(Object expected, Object actual) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual);
    }
}
