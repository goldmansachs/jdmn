
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"junit.ftl", "0085-decision-services.dmn"})
public class _0085DecisionServicesTest extends com.gs.dmn.runtime.JavaTimeDMNBaseDecision<Object> {
    @org.junit.jupiter.api.Test
    public void testCase001_1() {
        // Initialize input
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();

        // Check 'decision_001'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues("foo", DecisionService_001.instance().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase002_1() {
        // Initialize input
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();
        input_.add("decision_002_input", "baz");

        // Check 'decision_002'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues("foo baz", DecisionService_002.instance().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase003_1() {
        // Initialize input
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();
        input_.add("decision_003_input_1", "B");
        input_.add("decision_003_input_2", "C");
        input_.add("inputData_003", "D");

        // Check 'decision_003'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues("A B C D", DecisionService_003.instance().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase004_1() {
        // Initialize input
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();

        // Check 'decision_004_1'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues("foo", new Decision_004_1().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase006_1() {
        // Initialize input
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();

        // Check 'decision_006_1'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues("foo bar", new Decision_006_1().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase009_1() {
        // Initialize input
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();

        // Check 'decision_009_1'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues("foo bar", new Decision_009_1().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase011_1() {
        // Initialize input
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();

        // Check 'decision_011_1'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues("A B C D", new Decision_011_1().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase012_1() {
        // Initialize input
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();

        // Check 'decision_012_1'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues("A B C D", new Decision_012_1().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase013_1() {
        // Initialize input
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();
        input_.add("inputData_013_1", "C");

        // Check 'decision_013_1'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues(new com.gs.dmn.runtime.Context().add("decisionService_013", "A B").add("decision_013_3", "D").add("inputData_013_1", "C"), new Decision_013_1().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase014_1() {
        // Initialize input
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();
        input_.add("inputData_014_1", "C");

        // Check 'decision_014_1'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues(new com.gs.dmn.runtime.Context().add("decisionService_014", "A B").add("decision_014_3", "D").add("inputData_014_1", "C"), new Decision_014_1().applyContext(input_, context_));
    }

    private void checkValues(Object expected, Object actual) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual);
    }
}
