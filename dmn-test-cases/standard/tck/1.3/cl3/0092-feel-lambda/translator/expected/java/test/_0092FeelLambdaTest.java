
import java.util.*;
import java.util.stream.Collectors;

@jakarta.annotation.Generated(value = {"junit.ftl", "0092-feel-lambda.dmn"})
public class _0092FeelLambdaTest extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    @org.junit.jupiter.api.Test
    public void testCase001() {
        com.gs.dmn.runtime.ExecutionContext context_ = new com.gs.dmn.runtime.ExecutionContext();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();

        // Check 'decision_001_1'
        checkValues(number("3"), new Decision_001_1().apply(context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase002() {
        com.gs.dmn.runtime.ExecutionContext context_ = new com.gs.dmn.runtime.ExecutionContext();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();

        // Check 'decision_002_1'
        checkValues(number("4"), new Decision_002_1().apply(context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase003() {
        com.gs.dmn.runtime.ExecutionContext context_ = new com.gs.dmn.runtime.ExecutionContext();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();

        // Check 'decision_003_1'
        checkValues(number("5"), new Decision_003_1().apply(context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase004() {
        com.gs.dmn.runtime.ExecutionContext context_ = new com.gs.dmn.runtime.ExecutionContext();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();

        // Check 'decision_004_1'
        checkValues(number("6"), new Decision_004_1().apply(context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase005() {
        com.gs.dmn.runtime.ExecutionContext context_ = new com.gs.dmn.runtime.ExecutionContext();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();

        // Check 'decision_005_1'
        checkValues(number("20"), new Decision_005_1().apply(context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase006() {
        com.gs.dmn.runtime.ExecutionContext context_ = new com.gs.dmn.runtime.ExecutionContext();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();

        // Check 'decision_006_1'
        checkValues(number("30"), new Decision_006_1().apply(context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase007() {
        com.gs.dmn.runtime.ExecutionContext context_ = new com.gs.dmn.runtime.ExecutionContext();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();
        // Initialize input data
        java.math.BigDecimal input_007_1 = number("20");

        // Check 'decision_007_1'
        checkValues(number("100"), new Decision_007_1().apply(input_007_1, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase008() {
        com.gs.dmn.runtime.ExecutionContext context_ = new com.gs.dmn.runtime.ExecutionContext();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();

        // Check 'decision_008_1'
        checkValues(number("6"), new Decision_008_1().apply(context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase009() {
        com.gs.dmn.runtime.ExecutionContext context_ = new com.gs.dmn.runtime.ExecutionContext();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();

        // Check 'decision_009_1'
        checkValues(number("200"), new Decision_009_1().apply(context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase010() {
        com.gs.dmn.runtime.ExecutionContext context_ = new com.gs.dmn.runtime.ExecutionContext();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();

        // Check 'decision_010_1'
        checkValues(number("120"), new Decision_010_1().apply(context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase010_a() {
        com.gs.dmn.runtime.ExecutionContext context_ = new com.gs.dmn.runtime.ExecutionContext();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();

        // Check 'decision_010_1_a'
        checkValues(number("120"), new Decision_010_1_a().apply(context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase011() {
        com.gs.dmn.runtime.ExecutionContext context_ = new com.gs.dmn.runtime.ExecutionContext();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();
        // Initialize input data
        java.math.BigDecimal input_011_1 = number("10");

        // Check 'decision_011_1'
        checkValues(number("5000"), new Decision_011_1().apply(input_011_1, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase012() {
        com.gs.dmn.runtime.ExecutionContext context_ = new com.gs.dmn.runtime.ExecutionContext();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();

        // Check 'decision_012_1'
        checkValues(number("5000"), new Decision_012_1().apply(context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase013() {
        com.gs.dmn.runtime.ExecutionContext context_ = new com.gs.dmn.runtime.ExecutionContext();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();

        // Check 'decision_013_1'
        checkValues(number("5000"), new Decision_013_1().apply(context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase017() {
        com.gs.dmn.runtime.ExecutionContext context_ = new com.gs.dmn.runtime.ExecutionContext();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();
        // Initialize input data
        String input_017_1 = "a";

        // Check 'decision_017_1'
        checkValues(asList("a", "a", "z", "z"), new Decision_017_1().apply(input_017_1, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase018() {
        com.gs.dmn.runtime.ExecutionContext context_ = new com.gs.dmn.runtime.ExecutionContext();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();

        // Check 'decision_018'
        checkValues(asList("a", "a", "z", "z"), new Decision_018().apply(context_));
    }

    private void checkValues(Object expected, Object actual) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual);
    }
}
