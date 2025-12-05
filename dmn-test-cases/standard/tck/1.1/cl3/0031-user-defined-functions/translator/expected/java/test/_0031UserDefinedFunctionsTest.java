
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"junit.ftl", "0031-user-defined-functions.dmn"})
public class _0031UserDefinedFunctionsTest extends com.gs.dmn.runtime.JavaTimeDMNBaseDecision {
    @org.junit.jupiter.api.Test
    public void testCase001_1() {
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();
        // Initialize arguments
        java.lang.Number inputA = number("10");
        java.lang.Number inputB = number("5");

        // Check 'fn invocation positional parameters'
        checkValues(type.TFnInvocationPositionalResult.toTFnInvocationPositionalResult(new com.gs.dmn.runtime.Context().add("divisionResultPositional", number("2")).add("multiplicationResultPositional", number("50")).add("sumResult", number("15"))), new FnInvocationPositionalParameters().apply(inputA, inputB, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase002_1() {
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();
        // Initialize arguments
        java.lang.Number inputA = number("10");
        java.lang.Number inputB = number("5");

        // Check 'fn invocation named parameters'
        checkValues(type.TFnInvocationNamedResult.toTFnInvocationNamedResult(new com.gs.dmn.runtime.Context().add("divisionResultNamed", number("2")).add("multiplicationResultNamed", number("50")).add("subResult", number("5")).add("subResultMixed", number("-5"))), new FnInvocationNamedParameters().apply(inputA, inputB, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase003_1() {
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();
        // Initialize arguments
        java.lang.Number inputA = number("10");
        java.lang.Number inputB = number("5");

        // Check 'fn invocation complex parameters'
        checkValues(type.TFnInvocationComplexParamsResult.toTFnInvocationComplexParamsResult(new com.gs.dmn.runtime.Context().add("circumference", number("94.247760")).add("functionInvocationInParameter", number("200")).add("functionInvocationLiteralExpressionInParameter", number("500"))), new FnInvocationComplexParameters().apply(inputA, inputB, context_));
    }

    private void checkValues(Object expected, Object actual) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual);
    }
}
