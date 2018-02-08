
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"junit.ftl", "1103-feel-substring-function.dmn"})
public class Test1103FeelSubstringFunction extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    @org.junit.Test
    public void testCase001_53051b5628() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();

        // Check feelSubstringFunction_001_53051b5628
        String feelSubstringFunction_001_53051b5628Output = new FeelSubstringFunction_001_53051b5628().apply(annotationSet_, eventListener_, externalExecutor_);
        checkValues("f", feelSubstringFunction_001_53051b5628Output);
    }

    @org.junit.Test
    public void testCase002_03d12b93f0() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();

        // Check feelSubstringFunction_002_03d12b93f0
        String feelSubstringFunction_002_03d12b93f0Output = new FeelSubstringFunction_002_03d12b93f0().apply(annotationSet_, eventListener_, externalExecutor_);
        checkValues("f", feelSubstringFunction_002_03d12b93f0Output);
    }

    @org.junit.Test
    public void testCase003_6d06b1d2ec() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();

        // Check feelSubstringFunction_003_6d06b1d2ec
        String feelSubstringFunction_003_6d06b1d2ecOutput = new FeelSubstringFunction_003_6d06b1d2ec().apply(annotationSet_, eventListener_, externalExecutor_);
        checkValues("r", feelSubstringFunction_003_6d06b1d2ecOutput);
    }

    @org.junit.Test
    public void testCase004_938a24e7af() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();

        // Check feelSubstringFunction_004_938a24e7af
        String feelSubstringFunction_004_938a24e7afOutput = new FeelSubstringFunction_004_938a24e7af().apply(annotationSet_, eventListener_, externalExecutor_);
        checkValues("foobar", feelSubstringFunction_004_938a24e7afOutput);
    }

    @org.junit.Test
    public void testCase005_21fcca286c() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();

        // Check feelSubstringFunction_005_21fcca286c
        String feelSubstringFunction_005_21fcca286cOutput = new FeelSubstringFunction_005_21fcca286c().apply(annotationSet_, eventListener_, externalExecutor_);
        checkValues("obar", feelSubstringFunction_005_21fcca286cOutput);
    }

    @org.junit.Test
    public void testCase006_253e533bc6() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();

        // Check feelSubstringFunction_006_253e533bc6
        String feelSubstringFunction_006_253e533bc6Output = new FeelSubstringFunction_006_253e533bc6().apply(annotationSet_, eventListener_, externalExecutor_);
        checkValues("oba", feelSubstringFunction_006_253e533bc6Output);
    }

    @org.junit.Test
    public void testCase007_3e021f33a8() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();

        // Check feelSubstringFunction_007_3e021f33a8
        String feelSubstringFunction_007_3e021f33a8Output = new FeelSubstringFunction_007_3e021f33a8().apply(annotationSet_, eventListener_, externalExecutor_);
        checkValues("a", feelSubstringFunction_007_3e021f33a8Output);
    }

    @org.junit.Test
    public void testCase008_ddeffdc93e() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();

        // Check feelSubstringFunction_008_ddeffdc93e
        String feelSubstringFunction_008_ddeffdc93eOutput = new FeelSubstringFunction_008_ddeffdc93e().apply(annotationSet_, eventListener_, externalExecutor_);
        checkValues(" ", feelSubstringFunction_008_ddeffdc93eOutput);
    }

    @org.junit.Test
    public void testCase009_b52405c384() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();

        // Check feelSubstringFunction_009_b52405c384
        String feelSubstringFunction_009_b52405c384Output = new FeelSubstringFunction_009_b52405c384().apply(annotationSet_, eventListener_, externalExecutor_);
        checkValues("foobar", feelSubstringFunction_009_b52405c384Output);
    }

    @org.junit.Test
    public void testCase010_fbf9a89fde() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();

        // Check feelSubstringFunction_010_fbf9a89fde
        String feelSubstringFunction_010_fbf9a89fdeOutput = new FeelSubstringFunction_010_fbf9a89fde().apply(annotationSet_, eventListener_, externalExecutor_);
        checkValues("oba", feelSubstringFunction_010_fbf9a89fdeOutput);
    }

    @org.junit.Test
    public void testCase011_a559ce6410() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();

        // Check feelSubstringFunction_011_a559ce6410
        String feelSubstringFunction_011_a559ce6410Output = new FeelSubstringFunction_011_a559ce6410().apply(annotationSet_, eventListener_, externalExecutor_);
        checkValues("obar", feelSubstringFunction_011_a559ce6410Output);
    }

    private void checkValues(Object expected, Object actual) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual);
    }
}
