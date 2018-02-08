
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"junit.ftl", "0002-string-functions.dmn"})
public class Test0002StringFunctions extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    @org.junit.Test
    public void testCase001() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();
        // Initialize input data
        String a = "banana";
        String b = "a";
        java.math.BigDecimal numC = number("2");

        // Check Basic
        type.TBasic basicOutput = new Basic().apply(a, b, numC, annotationSet_, eventListener_, externalExecutor_);
        checkValues(new type.TBasicImpl(true, false, true, false, "a", false, false, number("6"), "nana", "b", "a", "BANANA"), basicOutput);
    }

    @org.junit.Test
    public void testCase002() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();
        // Initialize input data
        String a = "banana";

        // Check Matches
        Boolean matchesOutput = new Matches().apply(a, annotationSet_, eventListener_, externalExecutor_);
        checkValues(true, matchesOutput);
    }

    @org.junit.Test
    public void testCase003() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();
        // Initialize input data
        String a = "banana";

        // Check Replace
        type.TReplace replaceOutput = new Replace().apply(a, annotationSet_, eventListener_, externalExecutor_);
        checkValues(new type.TReplaceImpl("b**a", "bonono", "b[a]n[a]n[a]"), replaceOutput);
    }

    @org.junit.Test
    public void testCase004() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();
        // Initialize input data
        java.math.BigDecimal numC = number("2");

        // Check Constructor
        String constructorOutput = new Constructor().apply(numC, annotationSet_, eventListener_, externalExecutor_);
        checkValues("2", constructorOutput);
    }

    private void checkValues(Object expected, Object actual) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual);
    }
}
