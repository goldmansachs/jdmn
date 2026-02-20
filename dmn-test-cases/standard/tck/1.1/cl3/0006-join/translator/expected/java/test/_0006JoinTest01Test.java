
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"junit.ftl", "0006-join.dmn"})
public class _0006JoinTest01Test extends com.gs.dmn.runtime.JavaTimeDMNBaseDecision<Object> {
    private static final com.gs.dmn.runtime.coverage.trace.CoverageTraceListener listener = new com.gs.dmn.runtime.coverage.trace.CoverageTraceListener("http://www.trisotech.com/definitions/_16bf03c7-8f3d-46d0-a921-6e335ccc7e29", "0006-join", 1);

    @org.junit.jupiter.api.Test
    public void testCase001_1() {
        // Initialize inputs
        List<type.TEmployeeTable> employeeTable = asList(new type.TEmployeeTableImpl(number("10"), "7792", "Clark"), new type.TEmployeeTableImpl(number("10"), "7934", "Miller"), new type.TEmployeeTableImpl(number("20"), "7976", "Adams"), new type.TEmployeeTableImpl(number("20"), "7902", "Ford"), new type.TEmployeeTableImpl(number("30"), "7900", "James"));
        List<type.TDeptTable> deptTable = asList(new type.TDeptTableImpl("Smith", "Sales", number("10")), new type.TDeptTableImpl("Jones", "Finance", number("20")), new type.TDeptTableImpl("King", "Engineering", number("30")));
        String lastName = "Clark";

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();
        input_.add("EmployeeTable", employeeTable);
        input_.add("DeptTable", deptTable);
        input_.add("LastName", lastName);

        // Check 'Join'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().withEventListener(listener).build();
        checkValues("Smith", new Join().applyContext(input_, context_));
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
        java.io.File traceFile = new java.io.File(traceDir, "0006-join-test-01.json");
        try (java.io.FileWriter writer = new java.io.FileWriter(traceFile)) {
            com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValue(writer, listener.getModelTraces());
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
