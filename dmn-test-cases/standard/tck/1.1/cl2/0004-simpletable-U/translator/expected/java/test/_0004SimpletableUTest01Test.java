
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"junit.ftl", "0004-simpletable-U.dmn"})
public class _0004SimpletableUTest01Test extends com.gs.dmn.runtime.JavaTimeDMNBaseDecision<Object> {
    private static final com.gs.dmn.runtime.coverage.trace.CoverageTraceListener listener = new com.gs.dmn.runtime.coverage.trace.CoverageTraceListener("http://www.trisotech.com/definitions/_edbd2d8e-a5a8-4660-9bb9-adaa792d900c", "0004-simpletable-U", 1);

    @org.junit.jupiter.api.Test
    public void testCase001_1() {
        // Initialize inputs
        java.lang.Number age = number("18");
        String riskCategory = "Medium";
        Boolean isAffordable = Boolean.TRUE;

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();
        input_.add("Age", age);
        input_.add("RiskCategory", riskCategory);
        input_.add("isAffordable", isAffordable);

        // Check 'Approval Status'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().withEventListener(listener).build();
        checkValues("Approved", new ApprovalStatus().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase002_1() {
        // Initialize inputs
        java.lang.Number age = number("17");
        String riskCategory = "Medium";
        Boolean isAffordable = Boolean.TRUE;

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();
        input_.add("Age", age);
        input_.add("RiskCategory", riskCategory);
        input_.add("isAffordable", isAffordable);

        // Check 'Approval Status'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().withEventListener(listener).build();
        checkValues("Declined", new ApprovalStatus().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase003_1() {
        // Initialize inputs
        java.lang.Number age = number("18");
        String riskCategory = "High";
        Boolean isAffordable = Boolean.TRUE;

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();
        input_.add("Age", age);
        input_.add("RiskCategory", riskCategory);
        input_.add("isAffordable", isAffordable);

        // Check 'Approval Status'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().withEventListener(listener).build();
        checkValues("Declined", new ApprovalStatus().applyContext(input_, context_));
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
        java.io.File traceFile = new java.io.File(traceDir, "0004-simpletable-U-test-01.json");
        try (java.io.FileWriter writer = new java.io.FileWriter(traceFile)) {
            com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValue(writer, listener.getModelTraces());
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
