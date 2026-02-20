
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"junit.ftl", "0013-sort.dmn"})
public class _0013SortTest01Test extends com.gs.dmn.runtime.JavaTimeDMNBaseDecision<Object> {
    private static final com.gs.dmn.runtime.coverage.trace.CoverageTraceListener listener = new com.gs.dmn.runtime.coverage.trace.CoverageTraceListener("http://www.trisotech.com/definitions/_ac1acfdd-6baa-4f30-9cac-5d23957b4217", "0013-sort", 3);

    @org.junit.jupiter.api.Test
    public void testCase001_1() {
        // Initialize inputs
        List<java.lang.Number> listA = asList(number("3"), number("1"), number("5"), number("4"));
        List<String> stringList = asList("a", "8", "Aa", "A", "10", "9");
        List<type.TRow> tableB = asList(new type.TRowImpl(number("16"), number("4"), number("25"), number("1")), new type.TRowImpl(number("16"), number("43"), number("2"), number("10")), new type.TRowImpl(number("1"), number("0"), number("1"), number("1")));

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();
        input_.add("listA", listA);
        input_.add("stringList", stringList);
        input_.add("tableB", tableB);

        // Check 'sort1'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().withEventListener(listener).build();
        checkValues(asList(number("5"), number("4"), number("3"), number("1")), new Sort1().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase001_2() {
        // Initialize inputs
        List<java.lang.Number> listA = asList(number("3"), number("1"), number("5"), number("4"));
        List<String> stringList = asList("a", "8", "Aa", "A", "10", "9");
        List<type.TRow> tableB = asList(new type.TRowImpl(number("16"), number("4"), number("25"), number("1")), new type.TRowImpl(number("16"), number("43"), number("2"), number("10")), new type.TRowImpl(number("1"), number("0"), number("1"), number("1")));

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();
        input_.add("listA", listA);
        input_.add("stringList", stringList);
        input_.add("tableB", tableB);

        // Check 'sort2'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().withEventListener(listener).build();
        checkValues(asList(new type.TRowImpl(number("1"), number("0"), number("1"), number("1")), new type.TRowImpl(number("16"), number("4"), number("25"), number("1")), new type.TRowImpl(number("16"), number("43"), number("2"), number("10"))), new Sort2().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase001_3() {
        // Initialize inputs
        List<java.lang.Number> listA = asList(number("3"), number("1"), number("5"), number("4"));
        List<String> stringList = asList("a", "8", "Aa", "A", "10", "9");
        List<type.TRow> tableB = asList(new type.TRowImpl(number("16"), number("4"), number("25"), number("1")), new type.TRowImpl(number("16"), number("43"), number("2"), number("10")), new type.TRowImpl(number("1"), number("0"), number("1"), number("1")));

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();
        input_.add("listA", listA);
        input_.add("stringList", stringList);
        input_.add("tableB", tableB);

        // Check 'sort3'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().withEventListener(listener).build();
        checkValues(asList("10", "8", "9", "A", "Aa", "a"), new Sort3().applyContext(input_, context_));
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
        java.io.File traceFile = new java.io.File(traceDir, "0013-sort-test-01.json");
        try (java.io.FileWriter writer = new java.io.FileWriter(traceFile)) {
            com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValue(writer, listener.getModelTraces());
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
