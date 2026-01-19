
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"junit.ftl", "0076-feel-external-java.dmn"})
public class _0076FeelExternalJavaTest extends com.gs.dmn.runtime.JavaTimeDMNBaseDecision<Object> {
    @org.junit.jupiter.api.Test
    public void testCaseboxed_001_1() {

        // Check 'boxed_001'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues(number("456"), new Boxed_001().apply(context_));
    }

    @org.junit.jupiter.api.Test
    public void testCaseincorrect_001_1() {

        // Check 'incorrect_001'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues(null, new Incorrect_001().apply(context_));
    }

    @org.junit.jupiter.api.Test
    public void testCaseincorrect_002_1() {

        // Check 'incorrect_002'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues(null, new Incorrect_002().apply(context_));
    }

    @org.junit.jupiter.api.Test
    public void testCaseincorrect_003_1() {

        // Check 'incorrect_003'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues(null, new Incorrect_003().apply(context_));
    }

    @org.junit.jupiter.api.Test
    public void testCaseliteral_001_1() {

        // Check 'literal_001'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues(number("-0.88796890"), new Literal_001().apply(context_));
    }

    @org.junit.jupiter.api.Test
    public void testCaseliteral_002_1() {

        // Check 'literal_002'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues(number("456.78"), new Literal_002().apply(context_));
    }

    @org.junit.jupiter.api.Test
    public void testCaseliteral_003_1() {

        // Check 'literal_003'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues(number("456"), new Literal_003().apply(context_));
    }

    @org.junit.jupiter.api.Test
    public void testCaseliteral_004_1() {

        // Check 'literal_004'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues(number("456"), new Literal_004().apply(context_));
    }

    @org.junit.jupiter.api.Test
    public void testCaseliteral_005_1() {

        // Check 'literal_005'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues(number("123"), new Literal_005().apply(context_));
    }

    @org.junit.jupiter.api.Test
    public void testCaseliteral_006_1() {

        // Check 'literal_006'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues(number("3"), new Literal_006().apply(context_));
    }

    @org.junit.jupiter.api.Test
    public void testCaseliteral_007_1() {

        // Check 'literal_007'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues("a", new Literal_007().apply(context_));
    }

    @org.junit.jupiter.api.Test
    public void testCaseliteral_007_a_1() {

        // Check 'literal_007_a'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues(null, new Literal_007_a().apply(context_));
    }

    @org.junit.jupiter.api.Test
    public void testCaseliteral_008_1() {

        // Check 'literal_008'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues(number("456"), new Literal_008().apply(context_));
    }

    @org.junit.jupiter.api.Test
    public void testCaseliteral_009_1() {

        // Check 'literal_009'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues(number("456.78"), new Literal_009().apply(context_));
    }

    @org.junit.jupiter.api.Test
    public void testCaseliteral_010_1() {

        // Check 'literal_010'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues(number("123"), new Literal_010().apply(context_));
    }

    @org.junit.jupiter.api.Test
    public void testCaseliteral_011_1() {

        // Check 'literal_011'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues(number("1234.56"), new Literal_011().apply(context_));
    }

    @org.junit.jupiter.api.Test
    public void testCaseliteral_012_1() {

        // Check 'literal_012'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues(number("1234.56"), new Literal_012().apply(context_));
    }

    @org.junit.jupiter.api.Test
    public void testCasevarargs_001_1() {

        // Check 'varargs_001'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues("foo bar", new Varargs_001().apply(context_));
    }

    private void checkValues(Object expected, Object actual) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual);
    }
}
