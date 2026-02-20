
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"junit.ftl", "0009-invocation-arithmetic.dmn"})
public class _0009InvocationArithmeticTest01Test extends com.gs.dmn.runtime.JavaTimeDMNBaseDecision<Object> {
    private static final com.gs.dmn.runtime.coverage.trace.CoverageTraceListener listener = new com.gs.dmn.runtime.coverage.trace.CoverageTraceListener("http://www.trisotech.com/definitions/_cb28c255-91cd-4c01-ac7b-1a9cb1ecdb11", "0009-invocation-arithmetic", 2);

    @org.junit.jupiter.api.Test
    public void testCase001_1() {
        // Initialize inputs
        type.TLoan loan = new type.TLoanImpl(number("600000"), number("0.0375"), number("360"));
        java.lang.Number fee = number("100");

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();
        input_.add("Loan", loan);
        input_.add("fee", fee);

        // Check 'MonthlyPayment'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().withEventListener(listener).build();
        checkValues(number("2878.69354943277"), new MonthlyPayment().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase002_1() {
        // Initialize inputs
        type.TLoan loan = new type.TLoanImpl(number("30000"), number("0.0475"), number("60"));
        java.lang.Number fee = number("100");

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();
        input_.add("Loan", loan);
        input_.add("fee", fee);

        // Check 'MonthlyPayment'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().withEventListener(listener).build();
        checkValues(number("662.707359373292"), new MonthlyPayment().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase003_1() {
        // Initialize inputs
        type.TLoan loan = new type.TLoanImpl(number("600000"), number("0.0399"), number("360"));
        java.lang.Number fee = number("100");

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();
        input_.add("Loan", loan);
        input_.add("fee", fee);

        // Check 'MonthlyPayment'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().withEventListener(listener).build();
        checkValues(number("2961.03377700389"), new MonthlyPayment().applyContext(input_, context_));
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
        java.io.File traceFile = new java.io.File(traceDir, "0009-invocation-arithmetic-test-01.json");
        try (java.io.FileWriter writer = new java.io.FileWriter(traceFile)) {
            com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValue(writer, listener.getModelTraces());
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
