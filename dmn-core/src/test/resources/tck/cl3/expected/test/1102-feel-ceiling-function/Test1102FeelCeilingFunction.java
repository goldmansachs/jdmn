
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"junit.ftl", "1102-feel-ceiling-function.dmn"})
public class Test1102FeelCeilingFunction extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    @org.junit.Test
    public void testCase001_3df249d9c6() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();

        // Check feelCeilingFunction_001_3df249d9c6
        java.math.BigDecimal feelCeilingFunction_001_3df249d9c6Output = new FeelCeilingFunction_001_3df249d9c6().apply(annotationSet_, eventListener_, externalExecutor_);
        checkValues(number("2"), feelCeilingFunction_001_3df249d9c6Output);
    }

    @org.junit.Test
    public void testCase002_1052993cd8() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();

        // Check feelCeilingFunction_002_1052993cd8
        java.math.BigDecimal feelCeilingFunction_002_1052993cd8Output = new FeelCeilingFunction_002_1052993cd8().apply(annotationSet_, eventListener_, externalExecutor_);
        checkValues(number("-1"), feelCeilingFunction_002_1052993cd8Output);
    }

    @org.junit.Test
    public void testCase003_ca33989df5() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();

        // Check feelCeilingFunction_003_ca33989df5
        java.math.BigDecimal feelCeilingFunction_003_ca33989df5Output = new FeelCeilingFunction_003_ca33989df5().apply(annotationSet_, eventListener_, externalExecutor_);
        checkValues(number("1"), feelCeilingFunction_003_ca33989df5Output);
    }

    @org.junit.Test
    public void testCase004_be4a3e809c() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();

        // Check feelCeilingFunction_004_be4a3e809c
        java.math.BigDecimal feelCeilingFunction_004_be4a3e809cOutput = new FeelCeilingFunction_004_be4a3e809c().apply(annotationSet_, eventListener_, externalExecutor_);
        checkValues(number("-10"), feelCeilingFunction_004_be4a3e809cOutput);
    }

    @org.junit.Test
    public void testCase005_cc56ed5373() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();

        // Check feelCeilingFunction_005_cc56ed5373
        java.math.BigDecimal feelCeilingFunction_005_cc56ed5373Output = new FeelCeilingFunction_005_cc56ed5373().apply(annotationSet_, eventListener_, externalExecutor_);
        checkValues(number("6"), feelCeilingFunction_005_cc56ed5373Output);
    }

    @org.junit.Test
    public void testCase006_bbdf3bf8d7() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();

        // Check feelCeilingFunction_006_bbdf3bf8d7
        java.math.BigDecimal feelCeilingFunction_006_bbdf3bf8d7Output = new FeelCeilingFunction_006_bbdf3bf8d7().apply(annotationSet_, eventListener_, externalExecutor_);
        checkValues(number("0"), feelCeilingFunction_006_bbdf3bf8d7Output);
    }

    private void checkValues(Object expected, Object actual) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual);
    }
}
