
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"junit.ftl", "0031-user-defined-functions.dmn"})
public class _0031UserDefinedFunctionsTest extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    @org.junit.Test
    public void testCase001() {
        com.gs.dmn.runtime.ExecutionContext context_ = new com.gs.dmn.runtime.ExecutionContext();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();
        // Initialize input data
        java.math.BigDecimal inputA = number("10");
        java.math.BigDecimal inputB = number("5");

        // Check 'fn invocation positional parameters'
        checkValues(new type.TFnInvocationPositionalResultImpl(number("2"), number("50"), number("15")), new FnInvocationPositionalParameters().apply(inputA, inputB, context_));
    }

    @org.junit.Test
    public void testCase002() {
        com.gs.dmn.runtime.ExecutionContext context_ = new com.gs.dmn.runtime.ExecutionContext();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();
        // Initialize input data
        java.math.BigDecimal inputA = number("10");
        java.math.BigDecimal inputB = number("5");

        // Check 'fn invocation named parameters'
        checkValues(new type.TFnInvocationNamedResultImpl(number("2"), number("50"), number("5"), number("-5")), new FnInvocationNamedParameters().apply(inputA, inputB, context_));
    }

    @org.junit.Test
    public void testCase003() {
        com.gs.dmn.runtime.ExecutionContext context_ = new com.gs.dmn.runtime.ExecutionContext();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();
        // Initialize input data
        java.math.BigDecimal inputA = number("10");
        java.math.BigDecimal inputB = number("5");

        // Check 'fn invocation complex parameters'
        checkValues(new type.TFnInvocationComplexParamsResultImpl(number("94.247760"), number("200"), number("500")), new FnInvocationComplexParameters().apply(inputA, inputB, context_));
    }

    private void checkValues(Object expected, Object actual) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual);
    }
}
