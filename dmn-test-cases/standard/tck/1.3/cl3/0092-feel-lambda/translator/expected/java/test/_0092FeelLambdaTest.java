
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"junit.ftl", "0092-feel-lambda.dmn"})
public class _0092FeelLambdaTest extends com.gs.dmn.runtime.JavaTimeDMNBaseDecision<Object> {
    @org.junit.jupiter.api.Test
    public void testCase001_1() {

        // Check 'decision_001_1'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues(number("3"), new Decision_001_1().apply(context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase002_1() {

        // Check 'decision_002_1'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues(number("4"), new Decision_002_1().apply(context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase003_1() {

        // Check 'decision_003_1'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues(number("5"), new Decision_003_1().apply(context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase004_1() {

        // Check 'decision_004_1'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues(number("6"), new Decision_004_1().apply(context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase005_1() {

        // Check 'decision_005_1'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues(number("20"), new Decision_005_1().apply(context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase006_1() {

        // Check 'decision_006_1'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues(number("30"), new Decision_006_1().apply(context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase007_1() {
        // Initialize arguments
        java.lang.Number input_007_1 = number("20");

        // Check 'decision_007_1'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues(number("100"), new Decision_007_1().apply(input_007_1, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase008_1() {

        // Check 'decision_008_1'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues(number("6"), new Decision_008_1().apply(context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase009_1() {

        // Check 'decision_009_1'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues(number("200"), new Decision_009_1().apply(context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase010_1() {

        // Check 'decision_010_1'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues(number("120"), new Decision_010_1().apply(context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase010_a_1() {

        // Check 'decision_010_1_a'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues(number("120"), new Decision_010_1_a().apply(context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase011_1() {
        // Initialize arguments
        java.lang.Number input_011_1 = number("10");

        // Check 'decision_011_1'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues(number("5000"), new Decision_011_1().apply(input_011_1, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase012_1() {

        // Check 'decision_012_1'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues(number("5000"), new Decision_012_1().apply(context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase013_1() {

        // Check 'decision_013_1'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues(number("5000"), new Decision_013_1().apply(context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase017_1() {
        // Initialize arguments
        String input_017_1 = "a";

        // Check 'decision_017_1'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues(asList("a", "a", "z", "z"), new Decision_017_1().apply(input_017_1, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase018_1() {

        // Check 'decision_018'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues(asList("a", "a", "z", "z"), new Decision_018().apply(context_));
    }

    private void checkValues(Object expected, Object actual) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual);
    }
}
