
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"junit.ftl", "0085-decision-services.dmn"})
@com.gs.dmn.runtime.annotation.TestCases(
    testCasesName = "",
    modelName = "0085-decision-services.dmn"
)
public class _0085DecisionServicesTest01Test extends com.gs.dmn.runtime.JavaTimeDMNBaseDecision<Object> {
    private static final com.gs.dmn.runtime.coverage.trace.CoverageTraceListener listener = new com.gs.dmn.runtime.coverage.trace.CoverageTraceListener("http://www.montera.com.au/spec/DMN/0085-decision-services", "0085-decision-services", 44);

    @org.junit.jupiter.api.Test
    @com.gs.dmn.runtime.annotation.TestCase(id = "001", resultNode = "decisionService_001")
    public void testCase001_1() {

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();

        // Check 'decisionService_001'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().withEventListener(listener).build();
        checkValues("foo", DecisionService_001.instance().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    @com.gs.dmn.runtime.annotation.TestCase(id = "001a", resultNode = "decisionService_001")
    public void testCase001a_1() {

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();

        // Check 'decisionService_001'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().withEventListener(listener).build();
        checkValues("foo", DecisionService_001.instance().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    @com.gs.dmn.runtime.annotation.TestCase(id = "002", resultNode = "decisionService_002")
    public void testCase002_1() {
        // Initialize inputs
        String decision_002_input = "baz";

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();
        input_.add("http://www.montera.com.au/spec/DMN/0085-decision-services#decision_002_input", decision_002_input);

        // Check 'decisionService_002'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().withEventListener(listener).build();
        checkValues("foo baz", DecisionService_002.instance().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    @com.gs.dmn.runtime.annotation.TestCase(id = "003", resultNode = "decisionService_003")
    public void testCase003_1() {
        // Initialize inputs
        String decision_003_input_1 = "B";
        String decision_003_input_2 = "C";
        String inputData_003 = "D";

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();
        input_.add("http://www.montera.com.au/spec/DMN/0085-decision-services#decision_003_input_1", decision_003_input_1);
        input_.add("http://www.montera.com.au/spec/DMN/0085-decision-services#decision_003_input_2", decision_003_input_2);
        input_.add("http://www.montera.com.au/spec/DMN/0085-decision-services#inputData_003", inputData_003);

        // Check 'decisionService_003'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().withEventListener(listener).build();
        checkValues("A B C D", DecisionService_003.instance().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    @com.gs.dmn.runtime.annotation.TestCase(id = "004", resultNode = "decision_004_1")
    public void testCase004_1() {

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();

        // Check 'decision_004_1'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().withEventListener(listener).build();
        checkValues("foo", new Decision_004_1().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    @com.gs.dmn.runtime.annotation.TestCase(id = "006", resultNode = "decision_006_1")
    public void testCase006_1() {

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();

        // Check 'decision_006_1'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().withEventListener(listener).build();
        checkValues("foo bar", new Decision_006_1().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    @com.gs.dmn.runtime.annotation.TestCase(id = "009", resultNode = "decision_009_1")
    public void testCase009_1() {

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();

        // Check 'decision_009_1'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().withEventListener(listener).build();
        checkValues("foo bar", new Decision_009_1().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    @com.gs.dmn.runtime.annotation.TestCase(id = "011", resultNode = "decision_011_1")
    public void testCase011_1() {

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();

        // Check 'decision_011_1'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().withEventListener(listener).build();
        checkValues("A B C D", new Decision_011_1().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    @com.gs.dmn.runtime.annotation.TestCase(id = "012", resultNode = "decision_012_1")
    public void testCase012_1() {

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();

        // Check 'decision_012_1'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().withEventListener(listener).build();
        checkValues("A B C D", new Decision_012_1().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    @com.gs.dmn.runtime.annotation.TestCase(id = "013", resultNode = "decision_013_1")
    public void testCase013_1() {
        // Initialize inputs
        String inputData_013_1 = "C";

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();
        input_.add("http://www.montera.com.au/spec/DMN/0085-decision-services#inputData_013_1", inputData_013_1);

        // Check 'decision_013_1'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().withEventListener(listener).build();
        checkValues(new com.gs.dmn.runtime.Context().add("decisionService_013", "A B").add("decision_013_3", "D").add("inputData_013_1", "C"), new Decision_013_1().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    @com.gs.dmn.runtime.annotation.TestCase(id = "014", resultNode = "decision_014_1")
    public void testCase014_1() {
        // Initialize inputs
        String inputData_014_1 = "C";

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();
        input_.add("http://www.montera.com.au/spec/DMN/0085-decision-services#inputData_014_1", inputData_014_1);

        // Check 'decision_014_1'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().withEventListener(listener).build();
        checkValues(new com.gs.dmn.runtime.Context().add("decisionService_014", "A B").add("decision_014_3", "D").add("inputData_014_1", "C"), new Decision_014_1().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    @com.gs.dmn.runtime.annotation.TestCase(id = "015", resultNode = "decisionService_015")
    public void testCase015_1() {

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();

        // Check 'decisionService_015'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().withEventListener(listener).build();
        checkValues(new com.gs.dmn.runtime.Context().add("decision_015_1", "15_1").add("decision_015_2", "15_2"), DecisionService_015.instance().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    @com.gs.dmn.runtime.annotation.TestCase(id = "015_a", resultNode = "decisionService_015")
    public void testCase015_a_1() {

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();

        // Check 'decisionService_015'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().withEventListener(listener).build();
        checkValues(new com.gs.dmn.runtime.Context().add("decision_015_1", "15_1").add("decision_015_2", "15_2"), DecisionService_015.instance().applyContext(input_, context_));
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
        java.io.File traceFile = new java.io.File(traceDir, "0085-decision-services-test-01.json");
        try (java.io.FileWriter writer = new java.io.FileWriter(traceFile)) {
            com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValue(writer, listener.getModelTraces());
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
