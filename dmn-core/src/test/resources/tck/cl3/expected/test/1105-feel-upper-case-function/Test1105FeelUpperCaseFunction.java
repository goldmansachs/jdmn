
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"junit.ftl", "1105-feel-upper-case-function.dmn"})
public class Test1105FeelUpperCaseFunction extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    @org.junit.Test
    public void testCase001_2395aaad55() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();

        // Check feelUpperCaseFunction_001_2395aaad55
        String feelUpperCaseFunction_001_2395aaad55Output = new FeelUpperCaseFunction_001_2395aaad55().apply(annotationSet_, eventListener_, externalExecutor_);
        checkValues("A", feelUpperCaseFunction_001_2395aaad55Output);
    }

    @org.junit.Test
    public void testCase002_991789dded() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();

        // Check feelUpperCaseFunction_002_991789dded
        String feelUpperCaseFunction_002_991789ddedOutput = new FeelUpperCaseFunction_002_991789dded().apply(annotationSet_, eventListener_, externalExecutor_);
        checkValues("ABC", feelUpperCaseFunction_002_991789ddedOutput);
    }

    @org.junit.Test
    public void testCase003_d8306d8d00() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();

        // Check feelUpperCaseFunction_003_d8306d8d00
        String feelUpperCaseFunction_003_d8306d8d00Output = new FeelUpperCaseFunction_003_d8306d8d00().apply(annotationSet_, eventListener_, externalExecutor_);
        checkValues("", feelUpperCaseFunction_003_d8306d8d00Output);
    }

    @org.junit.Test
    public void testCase004_310caf7262() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();

        // Check feelUpperCaseFunction_004_310caf7262
        String feelUpperCaseFunction_004_310caf7262Output = new FeelUpperCaseFunction_004_310caf7262().apply(annotationSet_, eventListener_, externalExecutor_);
        checkValues("1", feelUpperCaseFunction_004_310caf7262Output);
    }

    @org.junit.Test
    public void testCase005_b316d773ac() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();

        // Check feelUpperCaseFunction_005_b316d773ac
        String feelUpperCaseFunction_005_b316d773acOutput = new FeelUpperCaseFunction_005_b316d773ac().apply(annotationSet_, eventListener_, externalExecutor_);
        checkValues("?@{", feelUpperCaseFunction_005_b316d773acOutput);
    }

    @org.junit.Test
    public void testCase006_d9bd3c14bc() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();

        // Check feelUpperCaseFunction_006_d9bd3c14bc
        String feelUpperCaseFunction_006_d9bd3c14bcOutput = new FeelUpperCaseFunction_006_d9bd3c14bc().apply(annotationSet_, eventListener_, externalExecutor_);
        checkValues("ABDCF", feelUpperCaseFunction_006_d9bd3c14bcOutput);
    }

    @org.junit.Test
    public void testCase007_31fc6c1967() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();

        // Check feelUpperCaseFunction_007_31fc6c1967
        String feelUpperCaseFunction_007_31fc6c1967Output = new FeelUpperCaseFunction_007_31fc6c1967().apply(annotationSet_, eventListener_, externalExecutor_);
        checkValues("XYZ ", feelUpperCaseFunction_007_31fc6c1967Output);
    }

    @org.junit.Test
    public void testCase008_26e369a9d9() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();

        // Check feelUpperCaseFunction_008_26e369a9d9
        String feelUpperCaseFunction_008_26e369a9d9Output = new FeelUpperCaseFunction_008_26e369a9d9().apply(annotationSet_, eventListener_, externalExecutor_);
        checkValues("123ABC", feelUpperCaseFunction_008_26e369a9d9Output);
    }

    private void checkValues(Object expected, Object actual) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual);
    }
}
