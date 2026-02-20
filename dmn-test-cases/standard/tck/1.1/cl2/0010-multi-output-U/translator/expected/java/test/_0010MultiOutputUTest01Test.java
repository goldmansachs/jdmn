
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"junit.ftl", "0010-multi-output-U.dmn"})
public class _0010MultiOutputUTest01Test extends com.gs.dmn.runtime.JavaTimeDMNBaseDecision<Object> {
    private static final com.gs.dmn.runtime.coverage.trace.CoverageTraceListener listener = new com.gs.dmn.runtime.coverage.trace.CoverageTraceListener("http://www.trisotech.com/definitions/_a3ebbd98-af09-42f3-9099-4ae2204a1f54", "0010-multi-output-U", 1);

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

        // Check 'Approval'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().withEventListener(listener).build();
        checkValues(type.TApproval.toTApproval(new com.gs.dmn.runtime.Context().add("Rate", "Standard").add("Status", "Approved")), new Approval().applyContext(input_, context_));
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

        // Check 'Approval'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().withEventListener(listener).build();
        checkValues(type.TApproval.toTApproval(new com.gs.dmn.runtime.Context().add("Rate", "Standard").add("Status", "Declined")), new Approval().applyContext(input_, context_));
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

        // Check 'Approval'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().withEventListener(listener).build();
        checkValues(type.TApproval.toTApproval(new com.gs.dmn.runtime.Context().add("Rate", "Standard").add("Status", "Declined")), new Approval().applyContext(input_, context_));
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
        java.io.File traceFile = new java.io.File(traceDir, "0010-multi-output-U-test-01.json");
        try (java.io.FileWriter writer = new java.io.FileWriter(traceFile)) {
            com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValue(writer, listener.getModelTraces());
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
