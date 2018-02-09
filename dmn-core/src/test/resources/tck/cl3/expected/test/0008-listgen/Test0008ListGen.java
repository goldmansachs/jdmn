
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"junit.ftl", "0008-listGen.dmn"})
public class Test0008ListGen extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    @org.junit.Test
    public void testCase001() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();

        // Check listGen1
        List<String> listGen1Output = new ListGen1().apply(annotationSet_, eventListener_, externalExecutor_);
        checkValues(asList("a", "b", "c"), listGen1Output);
    }

    @org.junit.Test
    public void testCase002() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();
        // Initialize input data
        String a = "a";
        String b = "b";
        String c = "c";

        // Check listGen2
        List<String> listGen2Output = new ListGen2().apply(a, b, c, annotationSet_, eventListener_, externalExecutor_);
        checkValues(asList("a", "b", "c"), listGen2Output);
    }

    @org.junit.Test
    public void testCase003() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();
        // Initialize input data
        String b = "b";
        String c = "c";

        // Check listGen3
        List<String> listGen3Output = new ListGen3().apply(b, c, annotationSet_, eventListener_, externalExecutor_);
        checkValues(asList("a", "b", "c"), listGen3Output);
    }

    @org.junit.Test
    public void testCase004() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();
        // Initialize input data
        String c = "c";

        // Check listGen4
        List<String> listGen4Output = new ListGen4().apply(c, annotationSet_, eventListener_, externalExecutor_);
        checkValues(asList("a", "b", "c"), listGen4Output);
    }

    @org.junit.Test
    public void testCase005() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();
        // Initialize input data
        String a = "a";
        String b = "b";
        String c = "c";

        // Check listGen5
        List<String> listGen5Output = new ListGen5().apply(a, b, c, annotationSet_, eventListener_, externalExecutor_);
        checkValues(asList("a", "b", "c"), listGen5Output);
    }

    @org.junit.Test
    public void testCase006() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();

        // Check listGen6
        List<String> listGen6Output = new ListGen6().apply(annotationSet_, eventListener_, externalExecutor_);
        checkValues(asList("w", "x", "y", "z"), listGen6Output);
    }

    @org.junit.Test
    public void testCase007() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();
        // Initialize input data
        List<String> wx = asList("w", "x");

        // Check listGen7
        List<String> listGen7Output = new ListGen7().apply(wx, annotationSet_, eventListener_, externalExecutor_);
        checkValues(asList("w", "x", "y", "z"), listGen7Output);
    }

    @org.junit.Test
    public void testCase008() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();
        // Initialize input data
        String a = "a";
        String b = "b";

        // Check listGen8
        List<String> listGen8Output = new ListGen8().apply(a, b, annotationSet_, eventListener_, externalExecutor_);
        checkValues(asList("a", "b", "w", "x", "y", "z"), listGen8Output);
    }

    @org.junit.Test
    public void testCase009() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();
        // Initialize input data
        String a = "a";
        String b = "b";
        List<String> wx = asList("w", "x");

        // Check listGen9
        List<String> listGen9Output = new ListGen9().apply(a, b, wx, annotationSet_, eventListener_, externalExecutor_);
        checkValues(asList("a", "b", "w", "x", "y", "z"), listGen9Output);
    }

    @org.junit.Test
    public void testCase010() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();
        // Initialize input data
        String c = "c";
        List<String> wx = asList("w", "x");

        // Check listGen10
        List<String> listGen10Output = new ListGen10().apply(c, wx, annotationSet_, eventListener_, externalExecutor_);
        checkValues(asList("a", "b", "c", "w", "x", "y", "z"), listGen10Output);
    }

    private void checkValues(Object expected, Object actual) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual);
    }
}
