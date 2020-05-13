
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"junit.ftl", "0006-join.dmn"})
public class Test0006Join extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    @org.junit.Test
    public void testCase001() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();
        // Initialize input data
        List<type.TEmployeeTable> employeeTable = asList(new type.TEmployeeTableImpl(number("10"), "7792", "Clark"), new type.TEmployeeTableImpl(number("10"), "7934", "Miller"), new type.TEmployeeTableImpl(number("20"), "7976", "Adams"), new type.TEmployeeTableImpl(number("20"), "7902", "Ford"), new type.TEmployeeTableImpl(number("30"), "7900", "James"));
        List<type.TDeptTable> deptTable = asList(new type.TDeptTableImpl("Smith", "Sales", number("10")), new type.TDeptTableImpl("Jones", "Finance", number("20")), new type.TDeptTableImpl("King", "Engineering", number("30")));
        String lastName = "Clark";

        // Check Join
        checkValues("Smith", new Join().apply(deptTable, employeeTable, lastName, annotationSet_, eventListener_, externalExecutor_));
    }

    private void checkValues(Object expected, Object actual) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual);
    }
}
