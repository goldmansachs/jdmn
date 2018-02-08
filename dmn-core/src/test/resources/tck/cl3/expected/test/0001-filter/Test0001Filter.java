
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"junit.ftl", "0001-filter.dmn"})
public class Test0001Filter extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    @org.junit.Test
    public void testCase001() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();
        // Initialize input data
        List<type.TEmployees> employees = asList(new type.TEmployeesImpl(number("10"), number("7792"), "Clark"), new type.TEmployeesImpl(number("10"), number("7934"), "Miller"), new type.TEmployeesImpl(number("20"), number("7976"), "Adams"), new type.TEmployeesImpl(number("20"), number("7902"), "Ford"), new type.TEmployeesImpl(number("30"), number("7900"), "James"));

        // Check filter01
        List<String> filter01Output = new Filter01().apply(employees, annotationSet_, eventListener_, externalExecutor_);
        checkValues(asList("Adams", "Ford"), filter01Output);
    }

    private void checkValues(Object expected, Object actual) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual);
    }
}
