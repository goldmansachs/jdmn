
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"junit.ftl", "0021-singleton-list.dmn"})
public class _0021SingletonListTest01Test extends com.gs.dmn.runtime.JavaTimeDMNBaseDecision<Object> {
    private static final com.gs.dmn.runtime.coverage.trace.CoverageTraceListener listener = new com.gs.dmn.runtime.coverage.trace.CoverageTraceListener("http://www.trisotech.com/definitions/_f52ca843-504b-4c3b-a6bc-4d377bffef7a", "0021-singleton-list", 5);

    @org.junit.jupiter.api.Test
    public void testCase001_1() {
        // Initialize inputs
        List<String> employees = asList("Jack", "John", "Bob", "Zack");

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();
        input_.add("Employees", employees);

        // Check 'decision1'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().withEventListener(listener).build();
        checkValues(asList("John"), new Decision1().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase001_2() {
        // Initialize inputs
        List<String> employees = asList("Jack", "John", "Bob", "Zack");

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();
        input_.add("Employees", employees);

        // Check 'decision2'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().withEventListener(listener).build();
        checkValues("John", new Decision2().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase001_3() {
        // Initialize inputs
        List<String> employees = asList("Jack", "John", "Bob", "Zack");

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();
        input_.add("Employees", employees);

        // Check 'decision3'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().withEventListener(listener).build();
        checkValues(asList("Bob"), new Decision3().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase001_4() {
        // Initialize inputs
        List<String> employees = asList("Jack", "John", "Bob", "Zack");

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();
        input_.add("Employees", employees);

        // Check 'decision4'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().withEventListener(listener).build();
        checkValues("Bob", new Decision4().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase001_5() {
        // Initialize inputs
        List<String> employees = asList("Jack", "John", "Bob", "Zack");

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();
        input_.add("Employees", employees);

        // Check 'decision5'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().withEventListener(listener).build();
        checkValues("BOB", new Decision5().applyContext(input_, context_));
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
        java.io.File traceFile = new java.io.File(traceDir, "0021-singleton-list-test-01.json");
        try (java.io.FileWriter writer = new java.io.FileWriter(traceFile)) {
            com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValue(writer, listener.getModelTraces());
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
