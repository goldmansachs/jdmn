
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"junit.ftl", "0100-feel-constants.dmn"})
public class Test0100FeelConstants extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    @org.junit.Test
    public void testCase001() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();

        // Check Decision1
        Boolean decision1Output = new Decision1().apply(annotationSet_, eventListener_, externalExecutor_);
        checkValues(true, decision1Output);
        // Check Decision2
        Boolean decision2Output = new Decision2().apply(annotationSet_, eventListener_, externalExecutor_);
        checkValues(false, decision2Output);
    }

    private void checkValues(Object expected, Object actual) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual);
    }
}
