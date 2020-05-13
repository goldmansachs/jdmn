
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"junit.ftl", "0021-singleton-list.dmn"})
public class Test0021SingletonList extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    @org.junit.Test
    public void testCase001() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();
        // Initialize input data
        List<String> employees = asList("Jack", "John", "Bob", "Zack");

        // Check decision1
        checkValues(asList("John"), new Decision1().apply(employees, annotationSet_, eventListener_, externalExecutor_));
        // Check decision2
        checkValues("John", new Decision2().apply(employees, annotationSet_, eventListener_, externalExecutor_));
        // Check decision3
        checkValues(asList("Bob"), new Decision3().apply(employees, annotationSet_, eventListener_, externalExecutor_));
        // Check decision4
        checkValues("Bob", new Decision4().apply(employees, annotationSet_, eventListener_, externalExecutor_));
        // Check decision5
        checkValues("BOB", new Decision5().apply(employees, annotationSet_, eventListener_, externalExecutor_));
    }

    private void checkValues(Object expected, Object actual) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual);
    }
}
