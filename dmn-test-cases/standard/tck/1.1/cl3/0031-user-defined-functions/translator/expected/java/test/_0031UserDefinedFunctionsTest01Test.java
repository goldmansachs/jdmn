
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"junit.ftl", "0031-user-defined-functions.dmn"})
public class _0031UserDefinedFunctionsTest01Test extends com.gs.dmn.runtime.JavaTimeDMNBaseDecision<Object> {
    private static final com.gs.dmn.runtime.coverage.trace.CoverageTraceListener listener = new com.gs.dmn.runtime.coverage.trace.CoverageTraceListener("http://www.actico.com/spec/DMN/0.1.0/0031-user-defined-functions", "0031-user-defined-functions", 5);

    @org.junit.jupiter.api.Test
    public void testCase001_1() {
        // Initialize inputs
        java.lang.Number inputA = number("10");
        java.lang.Number inputB = number("5");

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();
        input_.add("inputA", inputA);
        input_.add("inputB", inputB);

        // Check 'fn invocation positional parameters'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().withEventListener(listener).build();
        checkValues(type.TFnInvocationPositionalResult.toTFnInvocationPositionalResult(new com.gs.dmn.runtime.Context().add("divisionResultPositional", number("2")).add("multiplicationResultPositional", number("50")).add("sumResult", number("15"))), new FnInvocationPositionalParameters().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase002_1() {
        // Initialize inputs
        java.lang.Number inputA = number("10");
        java.lang.Number inputB = number("5");

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();
        input_.add("inputA", inputA);
        input_.add("inputB", inputB);

        // Check 'fn invocation named parameters'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().withEventListener(listener).build();
        checkValues(type.TFnInvocationNamedResult.toTFnInvocationNamedResult(new com.gs.dmn.runtime.Context().add("divisionResultNamed", number("2")).add("multiplicationResultNamed", number("50")).add("subResult", number("5")).add("subResultMixed", number("-5"))), new FnInvocationNamedParameters().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase003_1() {
        // Initialize inputs
        java.lang.Number inputA = number("10");
        java.lang.Number inputB = number("5");

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();
        input_.add("inputA", inputA);
        input_.add("inputB", inputB);

        // Check 'fn invocation complex parameters'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().withEventListener(listener).build();
        checkValues(type.TFnInvocationComplexParamsResult.toTFnInvocationComplexParamsResult(new com.gs.dmn.runtime.Context().add("circumference", number("94.247760")).add("functionInvocationInParameter", number("200")).add("functionInvocationLiteralExpressionInParameter", number("500"))), new FnInvocationComplexParameters().applyContext(input_, context_));
    }

    private void checkValues(Object expected, Object actual) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.AfterAll
    static void saveTrace() {
        java.io.File traceDir = new java.io.File("target/coverage-traces");
        if (!traceDir.exists()) {
            traceDir.mkdirs();
        }
        java.io.File traceFile = new java.io.File(traceDir, "0031-user-defined-functions-test-01.json");
        try (java.io.FileWriter writer = new java.io.FileWriter(traceFile)) {
            com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValue(writer, listener.getModelTraces());
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
