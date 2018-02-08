
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"junit.ftl", "1104-feel-string-length-function.dmn"})
public class Test1104FeelStringLengthFunction extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    @org.junit.Test
    public void testCase001_1afe6930d1() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();

        // Check feelStringLengthFunction_001_1afe6930d1
        java.math.BigDecimal feelStringLengthFunction_001_1afe6930d1Output = new FeelStringLengthFunction_001_1afe6930d1().apply(annotationSet_, eventListener_, externalExecutor_);
        checkValues(number("0"), feelStringLengthFunction_001_1afe6930d1Output);
    }

    @org.junit.Test
    public void testCase002_249c23050d() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();

        // Check feelStringLengthFunction_002_249c23050d
        java.math.BigDecimal feelStringLengthFunction_002_249c23050dOutput = new FeelStringLengthFunction_002_249c23050d().apply(annotationSet_, eventListener_, externalExecutor_);
        checkValues(number("1"), feelStringLengthFunction_002_249c23050dOutput);
    }

    @org.junit.Test
    public void testCase003_e1df507dee() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();

        // Check feelStringLengthFunction_003_e1df507dee
        java.math.BigDecimal feelStringLengthFunction_003_e1df507deeOutput = new FeelStringLengthFunction_003_e1df507dee().apply(annotationSet_, eventListener_, externalExecutor_);
        checkValues(number("3"), feelStringLengthFunction_003_e1df507deeOutput);
    }

    @org.junit.Test
    public void testCase004_f4c02fac3d() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();

        // Check feelStringLengthFunction_004_f4c02fac3d
        java.math.BigDecimal feelStringLengthFunction_004_f4c02fac3dOutput = new FeelStringLengthFunction_004_f4c02fac3d().apply(annotationSet_, eventListener_, externalExecutor_);
        checkValues(number("6"), feelStringLengthFunction_004_f4c02fac3dOutput);
    }

    @org.junit.Test
    public void testCase005_ca834dabac() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();

        // Check feelStringLengthFunction_005_ca834dabac
        java.math.BigDecimal feelStringLengthFunction_005_ca834dabacOutput = new FeelStringLengthFunction_005_ca834dabac().apply(annotationSet_, eventListener_, externalExecutor_);
        checkValues(number("10"), feelStringLengthFunction_005_ca834dabacOutput);
    }

    @org.junit.Test
    public void testCase006_6c4930a0eb() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();

        // Check feelStringLengthFunction_006_6c4930a0eb
        java.math.BigDecimal feelStringLengthFunction_006_6c4930a0ebOutput = new FeelStringLengthFunction_006_6c4930a0eb().apply(annotationSet_, eventListener_, externalExecutor_);
        checkValues(number("11"), feelStringLengthFunction_006_6c4930a0ebOutput);
    }

    private void checkValues(Object expected, Object actual) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual);
    }
}
