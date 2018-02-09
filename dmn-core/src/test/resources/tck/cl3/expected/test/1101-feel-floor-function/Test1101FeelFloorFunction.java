
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"junit.ftl", "1101-feel-floor-function.dmn"})
public class Test1101FeelFloorFunction extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    @org.junit.Test
    public void testCase001_75592d0dee() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();

        // Check feelFloorFunction_001_75592d0dee
        java.math.BigDecimal feelFloorFunction_001_75592d0deeOutput = new FeelFloorFunction_001_75592d0dee().apply(annotationSet_, eventListener_, externalExecutor_);
        checkValues(number("1"), feelFloorFunction_001_75592d0deeOutput);
    }

    @org.junit.Test
    public void testCase002_6fea586853() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();

        // Check feelFloorFunction_002_6fea586853
        java.math.BigDecimal feelFloorFunction_002_6fea586853Output = new FeelFloorFunction_002_6fea586853().apply(annotationSet_, eventListener_, externalExecutor_);
        checkValues(number("-2"), feelFloorFunction_002_6fea586853Output);
    }

    @org.junit.Test
    public void testCase003_cbae05445d() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();

        // Check feelFloorFunction_003_cbae05445d
        java.math.BigDecimal feelFloorFunction_003_cbae05445dOutput = new FeelFloorFunction_003_cbae05445d().apply(annotationSet_, eventListener_, externalExecutor_);
        checkValues(number("1"), feelFloorFunction_003_cbae05445dOutput);
    }

    @org.junit.Test
    public void testCase004_30f6d26798() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();

        // Check feelFloorFunction_004_30f6d26798
        java.math.BigDecimal feelFloorFunction_004_30f6d26798Output = new FeelFloorFunction_004_30f6d26798().apply(annotationSet_, eventListener_, externalExecutor_);
        checkValues(number("-11"), feelFloorFunction_004_30f6d26798Output);
    }

    @org.junit.Test
    public void testCase005_dd970ad275() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();

        // Check feelFloorFunction_005_dd970ad275
        java.math.BigDecimal feelFloorFunction_005_dd970ad275Output = new FeelFloorFunction_005_dd970ad275().apply(annotationSet_, eventListener_, externalExecutor_);
        checkValues(number("5"), feelFloorFunction_005_dd970ad275Output);
    }

    @org.junit.Test
    public void testCase006_1223620d9c() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();

        // Check feelFloorFunction_006_1223620d9c
        java.math.BigDecimal feelFloorFunction_006_1223620d9cOutput = new FeelFloorFunction_006_1223620d9c().apply(annotationSet_, eventListener_, externalExecutor_);
        checkValues(number("-1"), feelFloorFunction_006_1223620d9cOutput);
    }

    private void checkValues(Object expected, Object actual) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual);
    }
}
