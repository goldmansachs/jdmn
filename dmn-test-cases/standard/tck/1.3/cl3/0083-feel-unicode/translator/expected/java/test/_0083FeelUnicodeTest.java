
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"junit.ftl", "0083-feel-unicode.dmn"})
public class _0083FeelUnicodeTest extends com.gs.dmn.runtime.JavaTimeDMNBaseDecision<Object> {
    @org.junit.jupiter.api.Test
    public void testCasedecision_001_1() {

        // Check 'decision_001'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues(number("1"), new Decision_001().apply(context_));
    }

    @org.junit.jupiter.api.Test
    public void testCasedecision_001_a_1() {

        // Check 'decision_001_a'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues(number("1"), new Decision_001_a().apply(context_));
    }

    @org.junit.jupiter.api.Test
    public void testCasedecision_001_b_1() {

        // Check 'decision_001_b'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues(Boolean.TRUE, new Decision_001_b().apply(context_));
    }

    @org.junit.jupiter.api.Test
    public void testCasedecision_002_1() {

        // Check 'decision_002'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues(number("6"), new Decision_002().apply(context_));
    }

    @org.junit.jupiter.api.Test
    public void testCasedecision_003_1() {

        // Check 'decision_003'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues(number("1"), new Decision_003().apply(context_));
    }

    @org.junit.jupiter.api.Test
    public void testCasedecision_003_a_1() {

        // Check 'decision_003_a'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues(number("1"), new Decision_003_a().apply(context_));
    }

    @org.junit.jupiter.api.Test
    public void testCasedecision_004_1() {

        // Check 'decision_004'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues(number("2"), new Decision_004().apply(context_));
    }

    @org.junit.jupiter.api.Test
    public void testCasedecision_004_a_1() {

        // Check 'decision_004_a'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues(number("2"), new Decision_004_a().apply(context_));
    }

    @org.junit.jupiter.api.Test
    public void testCasedecision_005_1() {

        // Check 'decision_005'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues(Boolean.TRUE, new Decision_005().apply(context_));
    }

    @org.junit.jupiter.api.Test
    public void testCasedecision_005_a_1() {

        // Check 'decision_005_a'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues(Boolean.TRUE, new Decision_005_a().apply(context_));
    }

    @org.junit.jupiter.api.Test
    public void testCasedecision_006_1() {

        // Check 'decision_006'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues(new com.gs.dmn.runtime.Context().add("🐎", "bar"), new Decision_006().apply(context_));
    }

    @org.junit.jupiter.api.Test
    public void testCasedecision_007_1() {

        // Check 'decision_007'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues(new com.gs.dmn.runtime.Context().add("🐎", "😀"), new Decision_007().apply(context_));
    }

    @org.junit.jupiter.api.Test
    public void testCaseendswith_001_1() {

        // Check 'endswith_001'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues(Boolean.TRUE, new Endswith_001().apply(context_));
    }

    @org.junit.jupiter.api.Test
    public void testCasesubstring_004_1() {

        // Check 'substring_004'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues(Boolean.TRUE, new Substring_004().apply(context_));
    }

    private void checkValues(Object expected, Object actual) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual);
    }
}
