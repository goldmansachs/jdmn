
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"junit.ftl", "0006-join.dmn"})
public class _0006JoinTest extends com.gs.dmn.runtime.JavaTimeDMNBaseDecision<Object> {
    @org.junit.jupiter.api.Test
    public void testCase001_1() {
        // Initialize arguments
        List<type.TEmployeeTable> employeeTable = asList(new type.TEmployeeTableImpl(number("10"), "7792", "Clark"), new type.TEmployeeTableImpl(number("10"), "7934", "Miller"), new type.TEmployeeTableImpl(number("20"), "7976", "Adams"), new type.TEmployeeTableImpl(number("20"), "7902", "Ford"), new type.TEmployeeTableImpl(number("30"), "7900", "James"));
        List<type.TDeptTable> deptTable = asList(new type.TDeptTableImpl("Smith", "Sales", number("10")), new type.TDeptTableImpl("Jones", "Finance", number("20")), new type.TDeptTableImpl("King", "Engineering", number("30")));
        String lastName = "Clark";

        // Check 'Join'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues("Smith", new Join().apply(deptTable, employeeTable, lastName, context_));
    }

    private void checkValues(Object expected, Object actual) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual);
    }
}
