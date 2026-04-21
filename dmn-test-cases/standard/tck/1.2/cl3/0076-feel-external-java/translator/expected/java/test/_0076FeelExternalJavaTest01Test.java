
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"junit.ftl", "0076-feel-external-java.dmn"})
@com.gs.dmn.runtime.annotation.TestCases(
    testCasesName = "",
    modelName = "0076-feel-external-java.dmn"
)
public class _0076FeelExternalJavaTest01Test extends com.gs.dmn.runtime.JavaTimeDMNBaseDecision<Object> {
    private static final com.gs.dmn.runtime.coverage.trace.CoverageTraceListener listener = new com.gs.dmn.runtime.coverage.trace.CoverageTraceListener("http://www.montera.com.au/spec/DMN/0076-feel-external-java", "0076-feel-external-java", 19);

    @org.junit.jupiter.api.Test
    @com.gs.dmn.runtime.annotation.TestCase(id = "boxed_001", resultNode = "boxed_001")
    public void testCaseboxed_001_1() {

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();

        // Check 'boxed_001'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().withEventListener(listener).build();
        checkValues(number("456"), new Boxed_001().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    @com.gs.dmn.runtime.annotation.TestCase(id = "incorrect_001", resultNode = "incorrect_001")
    public void testCaseincorrect_001_1() {

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();

        // Check 'incorrect_001'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().withEventListener(listener).build();
        checkValues(null, new Incorrect_001().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    @com.gs.dmn.runtime.annotation.TestCase(id = "incorrect_002", resultNode = "incorrect_002")
    public void testCaseincorrect_002_1() {

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();

        // Check 'incorrect_002'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().withEventListener(listener).build();
        checkValues(null, new Incorrect_002().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    @com.gs.dmn.runtime.annotation.TestCase(id = "incorrect_003", resultNode = "incorrect_003")
    public void testCaseincorrect_003_1() {

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();

        // Check 'incorrect_003'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().withEventListener(listener).build();
        checkValues(null, new Incorrect_003().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    @com.gs.dmn.runtime.annotation.TestCase(id = "literal_001", resultNode = "literal_001")
    public void testCaseliteral_001_1() {

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();

        // Check 'literal_001'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().withEventListener(listener).build();
        checkValues(number("-0.88796890"), new Literal_001().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    @com.gs.dmn.runtime.annotation.TestCase(id = "literal_002", resultNode = "literal_002")
    public void testCaseliteral_002_1() {

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();

        // Check 'literal_002'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().withEventListener(listener).build();
        checkValues(number("456.78"), new Literal_002().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    @com.gs.dmn.runtime.annotation.TestCase(id = "literal_003", resultNode = "literal_003")
    public void testCaseliteral_003_1() {

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();

        // Check 'literal_003'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().withEventListener(listener).build();
        checkValues(number("456"), new Literal_003().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    @com.gs.dmn.runtime.annotation.TestCase(id = "literal_004", resultNode = "literal_004")
    public void testCaseliteral_004_1() {

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();

        // Check 'literal_004'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().withEventListener(listener).build();
        checkValues(number("456"), new Literal_004().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    @com.gs.dmn.runtime.annotation.TestCase(id = "literal_005", resultNode = "literal_005")
    public void testCaseliteral_005_1() {

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();

        // Check 'literal_005'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().withEventListener(listener).build();
        checkValues(number("123"), new Literal_005().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    @com.gs.dmn.runtime.annotation.TestCase(id = "literal_006", resultNode = "literal_006")
    public void testCaseliteral_006_1() {

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();

        // Check 'literal_006'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().withEventListener(listener).build();
        checkValues(number("3"), new Literal_006().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    @com.gs.dmn.runtime.annotation.TestCase(id = "literal_007", resultNode = "literal_007")
    public void testCaseliteral_007_1() {

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();

        // Check 'literal_007'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().withEventListener(listener).build();
        checkValues("a", new Literal_007().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    @com.gs.dmn.runtime.annotation.TestCase(id = "literal_007_a", resultNode = "literal_007_a")
    public void testCaseliteral_007_a_1() {

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();

        // Check 'literal_007_a'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().withEventListener(listener).build();
        checkValues(null, new Literal_007_a().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    @com.gs.dmn.runtime.annotation.TestCase(id = "literal_008", resultNode = "literal_008")
    public void testCaseliteral_008_1() {

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();

        // Check 'literal_008'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().withEventListener(listener).build();
        checkValues(number("456"), new Literal_008().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    @com.gs.dmn.runtime.annotation.TestCase(id = "literal_009", resultNode = "literal_009")
    public void testCaseliteral_009_1() {

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();

        // Check 'literal_009'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().withEventListener(listener).build();
        checkValues(number("456.78"), new Literal_009().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    @com.gs.dmn.runtime.annotation.TestCase(id = "literal_010", resultNode = "literal_010")
    public void testCaseliteral_010_1() {

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();

        // Check 'literal_010'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().withEventListener(listener).build();
        checkValues(number("123"), new Literal_010().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    @com.gs.dmn.runtime.annotation.TestCase(id = "literal_011", resultNode = "literal_011")
    public void testCaseliteral_011_1() {

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();

        // Check 'literal_011'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().withEventListener(listener).build();
        checkValues(number("1234.56"), new Literal_011().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    @com.gs.dmn.runtime.annotation.TestCase(id = "literal_012", resultNode = "literal_012")
    public void testCaseliteral_012_1() {

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();

        // Check 'literal_012'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().withEventListener(listener).build();
        checkValues(number("1234.56"), new Literal_012().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    @com.gs.dmn.runtime.annotation.TestCase(id = "varargs_001", resultNode = "varargs_001")
    public void testCasevarargs_001_1() {

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();

        // Check 'varargs_001'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().withEventListener(listener).build();
        checkValues("foo bar", new Varargs_001().applyContext(input_, context_));
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
        java.io.File traceFile = new java.io.File(traceDir, "0076-feel-external-java-test-01.json");
        try (java.io.FileWriter writer = new java.io.FileWriter(traceFile)) {
            com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValue(writer, listener.getModelTraces());
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
