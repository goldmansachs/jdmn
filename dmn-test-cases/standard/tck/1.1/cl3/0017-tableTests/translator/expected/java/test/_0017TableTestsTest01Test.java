
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"junit.ftl", "0017-tableTests.dmn"})
@com.gs.dmn.runtime.annotation.TestCases(
    testCasesName = "",
    modelName = "0017-tableTests.dmn"
)
public class _0017TableTestsTest01Test extends com.gs.dmn.runtime.JavaTimeDMNBaseDecision<Object> {
    private static final com.gs.dmn.runtime.coverage.trace.CoverageTraceListener listener = new com.gs.dmn.runtime.coverage.trace.CoverageTraceListener("http://www.trisotech.com/definitions/_92a0c25f-707e-4fc8-ae2d-2ab51ebe6bb6", "0017-tableTests", 4);

    @org.junit.jupiter.api.Test
    @com.gs.dmn.runtime.annotation.TestCase(id = "001", resultNode = "priceGt10")
    public void testCase001_1() {
        // Initialize inputs
        type.TA structA = new type.TAImpl("widget", number("20"));

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();
        input_.add("http://www.trisotech.com/definitions/_92a0c25f-707e-4fc8-ae2d-2ab51ebe6bb6#structA", structA);

        // Check 'priceGt10'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().withEventListener(listener).build();
        checkValues(Boolean.TRUE, new PriceGt10().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    @com.gs.dmn.runtime.annotation.TestCase(id = "002", resultNode = "priceInRange")
    public void testCase002_1() {
        // Initialize inputs
        type.TA structA = new type.TAImpl("widget", number("20"));
        java.lang.Number numB = number("9");
        java.lang.Number numC = number("10");

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();
        input_.add("http://www.trisotech.com/definitions/_92a0c25f-707e-4fc8-ae2d-2ab51ebe6bb6#structA", structA);
        input_.add("http://www.trisotech.com/definitions/_92a0c25f-707e-4fc8-ae2d-2ab51ebe6bb6#numB", numB);
        input_.add("http://www.trisotech.com/definitions/_92a0c25f-707e-4fc8-ae2d-2ab51ebe6bb6#numC", numC);

        // Check 'priceInRange'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().withEventListener(listener).build();
        checkValues("Not in range", new PriceInRange().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    @com.gs.dmn.runtime.annotation.TestCase(id = "003", resultNode = "dateCompare1")
    public void testCase003_1() {
        // Initialize inputs
        java.time.LocalDate dateD = date("2016-11-01");

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();
        input_.add("http://www.trisotech.com/definitions/_92a0c25f-707e-4fc8-ae2d-2ab51ebe6bb6#dateD", dateD);

        // Check 'dateCompare1'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().withEventListener(listener).build();
        checkValues(Boolean.TRUE, new DateCompare1().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    @com.gs.dmn.runtime.annotation.TestCase(id = "004", resultNode = "dateCompare2")
    public void testCase004_1() {
        // Initialize inputs
        java.time.LocalDate dateD = date("2016-11-01");
        java.time.LocalDate dateE = date("2016-11-02");

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();
        input_.add("http://www.trisotech.com/definitions/_92a0c25f-707e-4fc8-ae2d-2ab51ebe6bb6#dateD", dateD);
        input_.add("http://www.trisotech.com/definitions/_92a0c25f-707e-4fc8-ae2d-2ab51ebe6bb6#dateE", dateE);

        // Check 'dateCompare2'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().withEventListener(listener).build();
        checkValues(Boolean.FALSE, new DateCompare2().applyContext(input_, context_));
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
        java.io.File traceFile = new java.io.File(traceDir, "0017-tableTests-test-01.json");
        try (java.io.FileWriter writer = new java.io.FileWriter(traceFile)) {
            com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValue(writer, listener.getModelTraces());
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
