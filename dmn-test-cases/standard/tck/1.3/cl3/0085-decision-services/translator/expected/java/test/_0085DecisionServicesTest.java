
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"junit.ftl", "0085-decision-services.dmn"})
public class _0085DecisionServicesTest extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    @org.junit.Test
    public void testCase001() {
        com.gs.dmn.runtime.ExecutionContext context_ = new com.gs.dmn.runtime.ExecutionContext();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();

        // Check 'decision_001'
        checkValues("foo", DecisionService_001.instance().apply(context_));
    }

    @org.junit.Test
    public void testCase002() {
        com.gs.dmn.runtime.ExecutionContext context_ = new com.gs.dmn.runtime.ExecutionContext();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();
        // Initialize input data
        String decision_002_input = "baz";
        cache_.bind("decision_002_input", decision_002_input);

        // Check 'decision_002'
        checkValues("foo baz", DecisionService_002.instance().apply(decision_002_input, context_));
    }

    @org.junit.Test
    public void testCase003() {
        com.gs.dmn.runtime.ExecutionContext context_ = new com.gs.dmn.runtime.ExecutionContext();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();
        // Initialize input data
        String decision_003_input_1 = "B";
        cache_.bind("decision_003_input_1", decision_003_input_1);
        String decision_003_input_2 = "C";
        cache_.bind("decision_003_input_2", decision_003_input_2);
        String inputData_003 = "D";

        // Check 'decision_003'
        checkValues("A B C D", DecisionService_003.instance().apply(inputData_003, decision_003_input_1, decision_003_input_2, context_));
    }

    @org.junit.Test
    public void testCase004() {
        com.gs.dmn.runtime.ExecutionContext context_ = new com.gs.dmn.runtime.ExecutionContext();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();

        // Check 'decision_004_1'
        checkValues("foo", new Decision_004_1().apply(context_));
    }

    @org.junit.Test
    public void testCase006() {
        com.gs.dmn.runtime.ExecutionContext context_ = new com.gs.dmn.runtime.ExecutionContext();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();

        // Check 'decision_006_1'
        checkValues("foo bar", new Decision_006_1().apply(context_));
    }

    @org.junit.Test
    public void testCase009() {
        com.gs.dmn.runtime.ExecutionContext context_ = new com.gs.dmn.runtime.ExecutionContext();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();

        // Check 'decision_009_1'
        checkValues("foo bar", new Decision_009_1().apply(context_));
    }

    @org.junit.Test
    public void testCase011() {
        com.gs.dmn.runtime.ExecutionContext context_ = new com.gs.dmn.runtime.ExecutionContext();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();

        // Check 'decision_011_1'
        checkValues("A B C D", new Decision_011_1().apply(context_));
    }

    @org.junit.Test
    public void testCase012() {
        com.gs.dmn.runtime.ExecutionContext context_ = new com.gs.dmn.runtime.ExecutionContext();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();

        // Check 'decision_012_1'
        checkValues("A B C D", new Decision_012_1().apply(context_));
    }

    @org.junit.Test
    public void testCase013() {
        com.gs.dmn.runtime.ExecutionContext context_ = new com.gs.dmn.runtime.ExecutionContext();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();
        // Initialize input data
        String inputData_013_1 = "C";

        // Check 'decision_013_1'
        checkValues(new com.gs.dmn.runtime.Context().add("decisionService_013", "A B").add("decision_013_3", "D").add("inputData_013_1", "C"), new Decision_013_1().apply(inputData_013_1, context_));
    }

    @org.junit.Test
    public void testCase014() {
        com.gs.dmn.runtime.ExecutionContext context_ = new com.gs.dmn.runtime.ExecutionContext();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();
        // Initialize input data
        String inputData_014_1 = "C";

        // Check 'decision_014_1'
        checkValues(new com.gs.dmn.runtime.Context().add("decisionService_014", "A B").add("decision_014_3", "D").add("inputData_014_1", "C"), new Decision_014_1().apply(inputData_014_1, context_));
    }

    private void checkValues(Object expected, Object actual) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual);
    }
}
