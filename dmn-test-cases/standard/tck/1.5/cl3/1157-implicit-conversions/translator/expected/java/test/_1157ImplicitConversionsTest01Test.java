
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"junit.ftl", "1157-implicit-conversions.dmn"})
@com.gs.dmn.runtime.annotation.TestCases(
    testCasesName = "",
    modelName = "1157-implicit-conversions.dmn"
)
public class _1157ImplicitConversionsTest01Test extends com.gs.dmn.runtime.JavaTimeDMNBaseDecision<Object> {
    private static final com.gs.dmn.runtime.coverage.trace.CoverageTraceListener listener = new com.gs.dmn.runtime.coverage.trace.CoverageTraceListener("https://kie.org/dmn/_F9BB5760-8BCA-4216-AAD9-8BD4FB70802D", "1157-implicit-conversions", 16);

    @org.junit.jupiter.api.Test
    @com.gs.dmn.runtime.annotation.TestCase(id = "001", resultNode = "To Singleton List")
    public void testCase001_1() {

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();

        // Check 'To Singleton List'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().withEventListener(listener).build();
        checkValues(asList("abc"), new ToSingletonList().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    @com.gs.dmn.runtime.annotation.TestCase(id = "002", resultNode = "From Singleton List")
    public void testCase002_1() {

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();

        // Check 'From Singleton List'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().withEventListener(listener).build();
        checkValues("abc", new FromSingletonList().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    @com.gs.dmn.runtime.annotation.TestCase(id = "002_a", resultNode = "From Singleton List Error")
    public void testCase002_a_1() {

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();

        // Check 'From Singleton List Error'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().withEventListener(listener).build();
        checkValues(null, new FromSingletonListError().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    @com.gs.dmn.runtime.annotation.TestCase(id = "003", resultNode = "From Date To Date and Time")
    public void testCase003_1() {

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();

        // Check 'From Date To Date and Time'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().withEventListener(listener).build();
        checkValues(dateAndTime("2000-01-02T00:00:00Z"), new FromDateToDateAndTime().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    @com.gs.dmn.runtime.annotation.TestCase(id = "004", resultNode = "To Singleton List BKM")
    public void testCase004_1() {

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();

        // Check 'To Singleton List BKM'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().withEventListener(listener).build();
        checkValues(asList(number("1")), ToSingletonListBKM.instance().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    @com.gs.dmn.runtime.annotation.TestCase(id = "005", resultNode = "From Singleton List BKM")
    public void testCase005_1() {

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();

        // Check 'From Singleton List BKM'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().withEventListener(listener).build();
        checkValues(number("1"), FromSingletonListBKM.instance().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    @com.gs.dmn.runtime.annotation.TestCase(id = "006", resultNode = "From Date To Date and Time BKM")
    public void testCase006_1() {

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();

        // Check 'From Date To Date and Time BKM'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().withEventListener(listener).build();
        checkValues(dateAndTime("2000-01-02T00:00:00Z"), FromDateToDateAndTimeBKM.instance().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    @com.gs.dmn.runtime.annotation.TestCase(id = "007", resultNode = "To Singleton List DS")
    public void testCase007_1() {

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();

        // Check 'To Singleton List DS'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().withEventListener(listener).build();
        checkValues(asList(date("2000-01-02")), ToSingletonListDS.instance().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    @com.gs.dmn.runtime.annotation.TestCase(id = "008", resultNode = "From Singleton List DS")
    public void testCase008_1() {

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();

        // Check 'From Singleton List DS'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().withEventListener(listener).build();
        checkValues(date("2000-01-02"), FromSingletonListDS.instance().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    @com.gs.dmn.runtime.annotation.TestCase(id = "009", resultNode = "From Date to Date and Time DS")
    public void testCase009_1() {

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();

        // Check 'From Date to Date and Time DS'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().withEventListener(listener).build();
        checkValues(dateAndTime("2000-01-02T00:00:00Z"), FromDateToDateAndTimeDS.instance().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    @com.gs.dmn.runtime.annotation.TestCase(id = "010", resultNode = "Implicit Conversions CE")
    public void testCase010_1() {

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();

        // Check 'Implicit Conversions CE'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().withEventListener(listener).build();
        checkValues(asList(asList(number("1")), number("2"), dateAndTime("2000-12-01T00:00:00Z")), new ImplicitConversionsCE().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    @com.gs.dmn.runtime.annotation.TestCase(id = "011", resultNode = "Implicit Conversions FUNCT")
    public void testCase011_1() {

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();

        // Check 'Implicit Conversions FUNCT'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().withEventListener(listener).build();
        checkValues(asList(asList(number("1")), number("2"), dateAndTime("2000-02-01T00:00:00Z")), new ImplicitConversionsFUNCT().applyContext(input_, context_));
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
        java.io.File traceFile = new java.io.File(traceDir, "1157-implicit-conversions-test-01.json");
        try (java.io.FileWriter writer = new java.io.FileWriter(traceFile)) {
            com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValue(writer, listener.getModelTraces());
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
