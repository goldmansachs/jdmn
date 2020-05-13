
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"junit.ftl", "0031-user-defined-functions.dmn"})
public class Test0031UserDefinedFunctions extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    @org.junit.Test
    public void testCase001() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();
        // Initialize input data
        java.math.BigDecimal inputA = number("10");
        java.math.BigDecimal inputB = number("5");

        // Check fnInvocationPositionalParameters
        checkValues(new type.TFnInvocationPositionalResultImpl(number("2"), number("50"), number("15")), new FnInvocationPositionalParameters().apply(inputA, inputB, annotationSet_, eventListener_, externalExecutor_));
    }

    @org.junit.Test
    public void testCase002() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();
        // Initialize input data
        java.math.BigDecimal inputA = number("10");
        java.math.BigDecimal inputB = number("5");

        // Check fnInvocationNamedParameters
        checkValues(new type.TFnInvocationNamedResultImpl(number("2"), number("50"), number("5"), number("-5")), new FnInvocationNamedParameters().apply(inputA, inputB, annotationSet_, eventListener_, externalExecutor_));
    }

    @org.junit.Test
    public void testCase003() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();
        // Initialize input data
        java.math.BigDecimal inputA = number("10");
        java.math.BigDecimal inputB = number("5");

        // Check fnInvocationComplexParameters
        checkValues(new type.TFnInvocationComplexParamsResultImpl(number("94.247760"), number("200"), number("500")), new FnInvocationComplexParameters().apply(inputA, inputB, annotationSet_, eventListener_, externalExecutor_));
    }

    private void checkValues(Object expected, Object actual) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual);
    }
}
