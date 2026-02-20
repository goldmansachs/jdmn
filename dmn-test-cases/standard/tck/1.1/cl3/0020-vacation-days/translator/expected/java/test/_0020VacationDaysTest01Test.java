
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"junit.ftl", "0020-vacation-days.dmn"})
public class _0020VacationDaysTest01Test extends com.gs.dmn.runtime.JavaTimeDMNBaseDecision<Object> {
    private static final com.gs.dmn.runtime.coverage.trace.CoverageTraceListener listener = new com.gs.dmn.runtime.coverage.trace.CoverageTraceListener("https://www.drools.org/kie-dmn", "0020-vacation-days", 5);

    @org.junit.jupiter.api.Test
    public void testCase001_1() {
        // Initialize inputs
        java.lang.Number age = number("16");
        java.lang.Number yearsOfService = number("1");

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();
        input_.add("Age", age);
        input_.add("Years of Service", yearsOfService);

        // Check 'Total Vacation Days'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().withEventListener(listener).build();
        checkValues(number("27"), new TotalVacationDays().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase002_1() {
        // Initialize inputs
        java.lang.Number age = number("25");
        java.lang.Number yearsOfService = number("5");

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();
        input_.add("Age", age);
        input_.add("Years of Service", yearsOfService);

        // Check 'Total Vacation Days'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().withEventListener(listener).build();
        checkValues(number("22"), new TotalVacationDays().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase003_1() {
        // Initialize inputs
        java.lang.Number age = number("25");
        java.lang.Number yearsOfService = number("20");

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();
        input_.add("Age", age);
        input_.add("Years of Service", yearsOfService);

        // Check 'Total Vacation Days'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().withEventListener(listener).build();
        checkValues(number("24"), new TotalVacationDays().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase004_1() {
        // Initialize inputs
        java.lang.Number age = number("44");
        java.lang.Number yearsOfService = number("30");

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();
        input_.add("Age", age);
        input_.add("Years of Service", yearsOfService);

        // Check 'Total Vacation Days'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().withEventListener(listener).build();
        checkValues(number("30"), new TotalVacationDays().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase005_1() {
        // Initialize inputs
        java.lang.Number age = number("50");
        java.lang.Number yearsOfService = number("20");

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();
        input_.add("Age", age);
        input_.add("Years of Service", yearsOfService);

        // Check 'Total Vacation Days'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().withEventListener(listener).build();
        checkValues(number("24"), new TotalVacationDays().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase006_1() {
        // Initialize inputs
        java.lang.Number age = number("50");
        java.lang.Number yearsOfService = number("30");

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();
        input_.add("Age", age);
        input_.add("Years of Service", yearsOfService);

        // Check 'Total Vacation Days'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().withEventListener(listener).build();
        checkValues(number("30"), new TotalVacationDays().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase007_1() {
        // Initialize inputs
        java.lang.Number age = number("60");
        java.lang.Number yearsOfService = number("20");

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();
        input_.add("Age", age);
        input_.add("Years of Service", yearsOfService);

        // Check 'Total Vacation Days'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().withEventListener(listener).build();
        checkValues(number("30"), new TotalVacationDays().applyContext(input_, context_));
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
        java.io.File traceFile = new java.io.File(traceDir, "0020-vacation-days-test-01.json");
        try (java.io.FileWriter writer = new java.io.FileWriter(traceFile)) {
            com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValue(writer, listener.getModelTraces());
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
