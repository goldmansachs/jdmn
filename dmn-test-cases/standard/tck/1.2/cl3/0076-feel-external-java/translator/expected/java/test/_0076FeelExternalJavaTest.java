
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"junit.ftl", "0076-feel-external-java.dmn"})
public class _0076FeelExternalJavaTest extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    @org.junit.jupiter.api.Test
    public void testCaseboxed_001() {
        com.gs.dmn.runtime.ExecutionContext context_ = new com.gs.dmn.runtime.ExecutionContext();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();

        // Check 'boxed_001'
        checkValues(number("456"), new Boxed_001().apply(context_));
    }

    @org.junit.jupiter.api.Test
    public void testCaseincorrect_001() {
        com.gs.dmn.runtime.ExecutionContext context_ = new com.gs.dmn.runtime.ExecutionContext();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();

        // Check 'incorrect_001'
        checkValues(null, new Incorrect_001().apply(context_));
    }

    @org.junit.jupiter.api.Test
    public void testCaseincorrect_002() {
        com.gs.dmn.runtime.ExecutionContext context_ = new com.gs.dmn.runtime.ExecutionContext();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();

        // Check 'incorrect_002'
        checkValues(null, new Incorrect_002().apply(context_));
    }

    @org.junit.jupiter.api.Test
    public void testCaseincorrect_003() {
        com.gs.dmn.runtime.ExecutionContext context_ = new com.gs.dmn.runtime.ExecutionContext();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();

        // Check 'incorrect_003'
        checkValues(null, new Incorrect_003().apply(context_));
    }

    @org.junit.jupiter.api.Test
    public void testCaseliteral_001() {
        com.gs.dmn.runtime.ExecutionContext context_ = new com.gs.dmn.runtime.ExecutionContext();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();

        // Check 'literal_001'
        checkValues(number("-0.88796890"), new Literal_001().apply(context_));
    }

    @org.junit.jupiter.api.Test
    public void testCaseliteral_002() {
        com.gs.dmn.runtime.ExecutionContext context_ = new com.gs.dmn.runtime.ExecutionContext();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();

        // Check 'literal_002'
        checkValues(number("456.78"), new Literal_002().apply(context_));
    }

    @org.junit.jupiter.api.Test
    public void testCaseliteral_003() {
        com.gs.dmn.runtime.ExecutionContext context_ = new com.gs.dmn.runtime.ExecutionContext();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();

        // Check 'literal_003'
        checkValues(number("456"), new Literal_003().apply(context_));
    }

    @org.junit.jupiter.api.Test
    public void testCaseliteral_004() {
        com.gs.dmn.runtime.ExecutionContext context_ = new com.gs.dmn.runtime.ExecutionContext();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();

        // Check 'literal_004'
        checkValues(number("456"), new Literal_004().apply(context_));
    }

    @org.junit.jupiter.api.Test
    public void testCaseliteral_005() {
        com.gs.dmn.runtime.ExecutionContext context_ = new com.gs.dmn.runtime.ExecutionContext();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();

        // Check 'literal_005'
        checkValues(number("123"), new Literal_005().apply(context_));
    }

    @org.junit.jupiter.api.Test
    public void testCaseliteral_006() {
        com.gs.dmn.runtime.ExecutionContext context_ = new com.gs.dmn.runtime.ExecutionContext();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();

        // Check 'literal_006'
        checkValues(number("3"), new Literal_006().apply(context_));
    }

    @org.junit.jupiter.api.Test
    public void testCaseliteral_007() {
        com.gs.dmn.runtime.ExecutionContext context_ = new com.gs.dmn.runtime.ExecutionContext();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();

        // Check 'literal_007'
        checkValues("a", new Literal_007().apply(context_));
    }

    @org.junit.jupiter.api.Test
    public void testCaseliteral_007_a() {
        com.gs.dmn.runtime.ExecutionContext context_ = new com.gs.dmn.runtime.ExecutionContext();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();

        // Check 'literal_007_a'
        checkValues(null, new Literal_007_a().apply(context_));
    }

    @org.junit.jupiter.api.Test
    public void testCaseliteral_008() {
        com.gs.dmn.runtime.ExecutionContext context_ = new com.gs.dmn.runtime.ExecutionContext();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();

        // Check 'literal_008'
        checkValues(number("456"), new Literal_008().apply(context_));
    }

    @org.junit.jupiter.api.Test
    public void testCaseliteral_009() {
        com.gs.dmn.runtime.ExecutionContext context_ = new com.gs.dmn.runtime.ExecutionContext();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();

        // Check 'literal_009'
        checkValues(number("456.78"), new Literal_009().apply(context_));
    }

    @org.junit.jupiter.api.Test
    public void testCaseliteral_010() {
        com.gs.dmn.runtime.ExecutionContext context_ = new com.gs.dmn.runtime.ExecutionContext();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();

        // Check 'literal_010'
        checkValues(number("123"), new Literal_010().apply(context_));
    }

    @org.junit.jupiter.api.Test
    public void testCaseliteral_011() {
        com.gs.dmn.runtime.ExecutionContext context_ = new com.gs.dmn.runtime.ExecutionContext();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();

        // Check 'literal_011'
        checkValues(number("1234.56"), new Literal_011().apply(context_));
    }

    @org.junit.jupiter.api.Test
    public void testCaseliteral_012() {
        com.gs.dmn.runtime.ExecutionContext context_ = new com.gs.dmn.runtime.ExecutionContext();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();

        // Check 'literal_012'
        checkValues(number("1234.56"), new Literal_012().apply(context_));
    }

    @org.junit.jupiter.api.Test
    public void testCasevarargs_001() {
        com.gs.dmn.runtime.ExecutionContext context_ = new com.gs.dmn.runtime.ExecutionContext();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();

        // Check 'varargs_001'
        checkValues("foo bar", new Varargs_001().apply(context_));
    }

    private void checkValues(Object expected, Object actual) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual);
    }
}
