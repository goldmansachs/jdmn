
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
        List<String> decision1Output = new Decision1().apply(employees, annotationSet_, eventListener_, externalExecutor_);
        checkValues(asList("John"), decision1Output);
        // Check decision2
        String decision2Output = new Decision2().apply(employees, annotationSet_, eventListener_, externalExecutor_);
        checkValues("John", decision2Output);
        // Check decision3
        List<String> decision3Output = new Decision3().apply(employees, annotationSet_, eventListener_, externalExecutor_);
        checkValues(asList("Bob"), decision3Output);
        // Check decision4
        String decision4Output = new Decision4().apply(employees, annotationSet_, eventListener_, externalExecutor_);
        checkValues("Bob", decision4Output);
        // Check decision5
        String decision5Output = new Decision5().apply(employees, annotationSet_, eventListener_, externalExecutor_);
        checkValues("BOB", decision5Output);
    }

    private void checkValues(Object expected, Object actual) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual);
    }
}
