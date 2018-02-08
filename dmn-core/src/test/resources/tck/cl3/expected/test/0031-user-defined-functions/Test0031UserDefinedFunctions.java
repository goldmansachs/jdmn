
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
        type.TFnInvocationPositionalResult fnInvocationPositionalParametersOutput = new FnInvocationPositionalParameters().apply(inputA, inputB, annotationSet_, eventListener_, externalExecutor_);
        checkValues(new type.TFnInvocationPositionalResultImpl(number("2"), number("50"), number("15")), fnInvocationPositionalParametersOutput);
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
        type.TFnInvocationNamedResult fnInvocationNamedParametersOutput = new FnInvocationNamedParameters().apply(inputA, inputB, annotationSet_, eventListener_, externalExecutor_);
        checkValues(new type.TFnInvocationNamedResultImpl(number("2"), number("50"), number("5"), number("-5")), fnInvocationNamedParametersOutput);
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
        type.TFnInvocationComplexParamsResult fnInvocationComplexParametersOutput = new FnInvocationComplexParameters().apply(inputA, inputB, annotationSet_, eventListener_, externalExecutor_);
        checkValues(new type.TFnInvocationComplexParamsResultImpl(number("94.247760"), number("200"), number("500")), fnInvocationComplexParametersOutput);
    }

    private void checkValues(Object expected, Object actual) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual);
    }
}
