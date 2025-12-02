
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"junit.ftl", "0083-feel-unicode.dmn"})
public class _0083FeelUnicodeTest extends com.gs.dmn.runtime.JavaTimeDMNBaseDecision {
    @org.junit.jupiter.api.Test
    public void testCasedecision_001() {
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();

        // Check 'decision_001'
        checkValues(number("1"), new Decision_001().apply(context_));
    }

    @org.junit.jupiter.api.Test
    public void testCasedecision_001_a() {
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();

        // Check 'decision_001_a'
        checkValues(number("1"), new Decision_001_a().apply(context_));
    }

    @org.junit.jupiter.api.Test
    public void testCasedecision_001_b() {
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();

        // Check 'decision_001_b'
        checkValues(Boolean.TRUE, new Decision_001_b().apply(context_));
    }

    @org.junit.jupiter.api.Test
    public void testCasedecision_002() {
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();

        // Check 'decision_002'
        checkValues(number("6"), new Decision_002().apply(context_));
    }

    @org.junit.jupiter.api.Test
    public void testCasedecision_003() {
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();

        // Check 'decision_003'
        checkValues(number("1"), new Decision_003().apply(context_));
    }

    @org.junit.jupiter.api.Test
    public void testCasedecision_003_a() {
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();

        // Check 'decision_003_a'
        checkValues(number("1"), new Decision_003_a().apply(context_));
    }

    @org.junit.jupiter.api.Test
    public void testCasedecision_004() {
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();

        // Check 'decision_004'
        checkValues(number("2"), new Decision_004().apply(context_));
    }

    @org.junit.jupiter.api.Test
    public void testCasedecision_004_a() {
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();

        // Check 'decision_004_a'
        checkValues(number("2"), new Decision_004_a().apply(context_));
    }

    @org.junit.jupiter.api.Test
    public void testCasedecision_005() {
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();

        // Check 'decision_005'
        checkValues(Boolean.TRUE, new Decision_005().apply(context_));
    }

    @org.junit.jupiter.api.Test
    public void testCasedecision_005_a() {
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();

        // Check 'decision_005_a'
        checkValues(Boolean.TRUE, new Decision_005_a().apply(context_));
    }

    @org.junit.jupiter.api.Test
    public void testCasedecision_006() {
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();

        // Check 'decision_006'
        checkValues(new com.gs.dmn.runtime.Context().add("🐎", "bar"), new Decision_006().apply(context_));
    }

    @org.junit.jupiter.api.Test
    public void testCasedecision_007() {
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();

        // Check 'decision_007'
        checkValues(new com.gs.dmn.runtime.Context().add("🐎", "😀"), new Decision_007().apply(context_));
    }

    @org.junit.jupiter.api.Test
    public void testCaseendswith_001() {
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();

        // Check 'endswith_001'
        checkValues(Boolean.TRUE, new Endswith_001().apply(context_));
    }

    @org.junit.jupiter.api.Test
    public void testCasesubstring_004() {
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();

        // Check 'substring_004'
        checkValues(Boolean.TRUE, new Substring_004().apply(context_));
    }

    private void checkValues(Object expected, Object actual) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual);
    }
}
