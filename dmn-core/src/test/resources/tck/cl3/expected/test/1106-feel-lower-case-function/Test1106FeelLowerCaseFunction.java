
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"junit.ftl", "1106-feel-lower-case-function.dmn"})
public class Test1106FeelLowerCaseFunction extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    @org.junit.Test
    public void testCase001_f6ff05bcfa() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();

        // Check feelLowerCaseFunction_001_f6ff05bcfa
        String feelLowerCaseFunction_001_f6ff05bcfaOutput = new FeelLowerCaseFunction_001_f6ff05bcfa().apply(annotationSet_, eventListener_, externalExecutor_);
        checkValues("a", feelLowerCaseFunction_001_f6ff05bcfaOutput);
    }

    @org.junit.Test
    public void testCase002_0ecb21e1d8() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();

        // Check feelLowerCaseFunction_002_0ecb21e1d8
        String feelLowerCaseFunction_002_0ecb21e1d8Output = new FeelLowerCaseFunction_002_0ecb21e1d8().apply(annotationSet_, eventListener_, externalExecutor_);
        checkValues("abc", feelLowerCaseFunction_002_0ecb21e1d8Output);
    }

    @org.junit.Test
    public void testCase003_af9f3a8dab() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();

        // Check feelLowerCaseFunction_003_af9f3a8dab
        String feelLowerCaseFunction_003_af9f3a8dabOutput = new FeelLowerCaseFunction_003_af9f3a8dab().apply(annotationSet_, eventListener_, externalExecutor_);
        checkValues("abc", feelLowerCaseFunction_003_af9f3a8dabOutput);
    }

    @org.junit.Test
    public void testCase004_2dbf99e8c0() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();

        // Check feelLowerCaseFunction_004_2dbf99e8c0
        String feelLowerCaseFunction_004_2dbf99e8c0Output = new FeelLowerCaseFunction_004_2dbf99e8c0().apply(annotationSet_, eventListener_, externalExecutor_);
        checkValues("abc4", feelLowerCaseFunction_004_2dbf99e8c0Output);
    }

    @org.junit.Test
    public void testCase005_ad33325968() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();

        // Check feelLowerCaseFunction_005_ad33325968
        String feelLowerCaseFunction_005_ad33325968Output = new FeelLowerCaseFunction_005_ad33325968().apply(annotationSet_, eventListener_, externalExecutor_);
        checkValues("", feelLowerCaseFunction_005_ad33325968Output);
    }

    @org.junit.Test
    public void testCase006_d8257b3b92() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();

        // Check feelLowerCaseFunction_006_d8257b3b92
        String feelLowerCaseFunction_006_d8257b3b92Output = new FeelLowerCaseFunction_006_d8257b3b92().apply(annotationSet_, eventListener_, externalExecutor_);
        checkValues("?@{", feelLowerCaseFunction_006_d8257b3b92Output);
    }

    @org.junit.Test
    public void testCase007_a2ce59499a() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();

        // Check feelLowerCaseFunction_007_a2ce59499a
        String feelLowerCaseFunction_007_a2ce59499aOutput = new FeelLowerCaseFunction_007_a2ce59499a().apply(annotationSet_, eventListener_, externalExecutor_);
        checkValues("abdcf", feelLowerCaseFunction_007_a2ce59499aOutput);
    }

    @org.junit.Test
    public void testCase008_9913ad454f() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();

        // Check feelLowerCaseFunction_008_9913ad454f
        String feelLowerCaseFunction_008_9913ad454fOutput = new FeelLowerCaseFunction_008_9913ad454f().apply(annotationSet_, eventListener_, externalExecutor_);
        checkValues("xyz ", feelLowerCaseFunction_008_9913ad454fOutput);
    }

    @org.junit.Test
    public void testCase009_78e6b2969b() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();

        // Check feelLowerCaseFunction_009_78e6b2969b
        String feelLowerCaseFunction_009_78e6b2969bOutput = new FeelLowerCaseFunction_009_78e6b2969b().apply(annotationSet_, eventListener_, externalExecutor_);
        checkValues("123abc", feelLowerCaseFunction_009_78e6b2969bOutput);
    }

    private void checkValues(Object expected, Object actual) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual);
    }
}
