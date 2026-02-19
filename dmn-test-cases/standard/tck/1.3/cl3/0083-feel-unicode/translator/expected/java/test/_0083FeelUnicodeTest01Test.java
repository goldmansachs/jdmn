
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"junit.ftl", "0083-feel-unicode.dmn"})
public class _0083FeelUnicodeTest01Test extends com.gs.dmn.runtime.JavaTimeDMNBaseDecision<Object> {
    @org.junit.jupiter.api.Test
    public void testCasedecision_001_1() {

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();

        // Check 'decision_001'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues(number("1"), new Decision_001().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCasedecision_001_a_1() {

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();

        // Check 'decision_001_a'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues(number("1"), new Decision_001_a().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCasedecision_001_b_1() {

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();

        // Check 'decision_001_b'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues(Boolean.TRUE, new Decision_001_b().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCasedecision_002_1() {

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();

        // Check 'decision_002'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues(number("6"), new Decision_002().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCasedecision_003_1() {

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();

        // Check 'decision_003'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues(number("1"), new Decision_003().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCasedecision_003_a_1() {

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();

        // Check 'decision_003_a'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues(number("1"), new Decision_003_a().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCasedecision_004_1() {

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();

        // Check 'decision_004'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues(number("2"), new Decision_004().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCasedecision_004_a_1() {

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();

        // Check 'decision_004_a'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues(number("2"), new Decision_004_a().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCasedecision_005_1() {

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();

        // Check 'decision_005'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues(Boolean.TRUE, new Decision_005().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCasedecision_005_a_1() {

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();

        // Check 'decision_005_a'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues(Boolean.TRUE, new Decision_005_a().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCasedecision_006_1() {

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();

        // Check 'decision_006'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues(new com.gs.dmn.runtime.Context().add("🐎", "bar"), new Decision_006().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCasedecision_007_1() {

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();

        // Check 'decision_007'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues(new com.gs.dmn.runtime.Context().add("🐎", "😀"), new Decision_007().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCaseendswith_001_1() {

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();

        // Check 'endswith_001'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues(Boolean.TRUE, new Endswith_001().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCasesubstring_004_1() {

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();

        // Check 'substring_004'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues(Boolean.TRUE, new Substring_004().applyContext(input_, context_));
    }

    private void checkValues(Object expected, Object actual) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual);
    }
}
