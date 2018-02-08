
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"junit.ftl", "0105-feel-math.dmn"})
public class Test0105FeelMath extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    @org.junit.Test
    public void testCase001() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();

        // Check Decision1
        java.math.BigDecimal decision1Output = new Decision1().apply(annotationSet_, eventListener_, externalExecutor_);
        checkValues(number("15"), decision1Output);
    }

    @org.junit.Test
    public void testCase002() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();

        // Check Decision2
        java.math.BigDecimal decision2Output = new Decision2().apply(annotationSet_, eventListener_, externalExecutor_);
        checkValues(number("-15"), decision2Output);
    }

    @org.junit.Test
    public void testCase003() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();

        // Check Decision3
        java.math.BigDecimal decision3Output = new Decision3().apply(annotationSet_, eventListener_, externalExecutor_);
        checkValues(number("-15"), decision3Output);
    }

    @org.junit.Test
    public void testCase004() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();

        // Check Decision4
        java.math.BigDecimal decision4Output = new Decision4().apply(annotationSet_, eventListener_, externalExecutor_);
        checkValues(number("5"), decision4Output);
    }

    @org.junit.Test
    public void testCase005() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();

        // Check Decision5
        java.math.BigDecimal decision5Output = new Decision5().apply(annotationSet_, eventListener_, externalExecutor_);
        checkValues(number("-5"), decision5Output);
    }

    @org.junit.Test
    public void testCase006() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();

        // Check Decision6
        java.math.BigDecimal decision6Output = new Decision6().apply(annotationSet_, eventListener_, externalExecutor_);
        checkValues(number("-5"), decision6Output);
    }

    @org.junit.Test
    public void testCase007() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();

        // Check Decision7
        java.math.BigDecimal decision7Output = new Decision7().apply(annotationSet_, eventListener_, externalExecutor_);
        checkValues(number("32"), decision7Output);
    }

    @org.junit.Test
    public void testCase008() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();

        // Check Decision8
        java.math.BigDecimal decision8Output = new Decision8().apply(annotationSet_, eventListener_, externalExecutor_);
        checkValues(number("50"), decision8Output);
    }

    @org.junit.Test
    public void testCase009() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();

        // Check Decision9
        java.math.BigDecimal decision9Output = new Decision9().apply(annotationSet_, eventListener_, externalExecutor_);
        checkValues(number("50"), decision9Output);
    }

    @org.junit.Test
    public void testCase010() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();

        // Check Decision10
        java.math.BigDecimal decision10Output = new Decision10().apply(annotationSet_, eventListener_, externalExecutor_);
        checkValues(number("50"), decision10Output);
    }

    @org.junit.Test
    public void testCase011() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();

        // Check Decision11
        java.math.BigDecimal decision11Output = new Decision11().apply(annotationSet_, eventListener_, externalExecutor_);
        checkValues(number("-225"), decision11Output);
    }

    @org.junit.Test
    public void testCase012() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();

        // Check Decision12
        java.math.BigDecimal decision12Output = new Decision12().apply(annotationSet_, eventListener_, externalExecutor_);
        checkValues(number("2"), decision12Output);
    }

    @org.junit.Test
    public void testCase013() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();

        // Check Decision13
        java.math.BigDecimal decision13Output = new Decision13().apply(annotationSet_, eventListener_, externalExecutor_);
        checkValues(number("2"), decision13Output);
    }

    @org.junit.Test
    public void testCase014() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();

        // Check Decision14
        java.math.BigDecimal decision14Output = new Decision14().apply(annotationSet_, eventListener_, externalExecutor_);
        checkValues(number("2"), decision14Output);
    }

    @org.junit.Test
    public void testCase015() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();

        // Check Decision15
        java.math.BigDecimal decision15Output = new Decision15().apply(annotationSet_, eventListener_, externalExecutor_);
        checkValues(number("-2"), decision15Output);
    }

    @org.junit.Test
    public void testCase016() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();

        // Check Decision16
        java.math.BigDecimal decision16Output = new Decision16().apply(annotationSet_, eventListener_, externalExecutor_);
        checkValues(null, decision16Output);
    }

    @org.junit.Test
    public void testCase017() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();

        // Check Decision17
        java.math.BigDecimal decision17Output = new Decision17().apply(annotationSet_, eventListener_, externalExecutor_);
        checkValues(number("100000"), decision17Output);
    }

    @org.junit.Test
    public void testCase018() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();

        // Check Decision18
        java.math.BigDecimal decision18Output = new Decision18().apply(annotationSet_, eventListener_, externalExecutor_);
        checkValues(number("0.00001"), decision18Output);
    }

    @org.junit.Test
    public void testCase019() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();

        // Check Decision19
        java.math.BigDecimal decision19Output = new Decision19().apply(annotationSet_, eventListener_, externalExecutor_);
        checkValues(number("16807"), decision19Output);
    }

    @org.junit.Test
    public void testCase020() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();

        // Check Decision20
        java.math.BigDecimal decision20Output = new Decision20().apply(annotationSet_, eventListener_, externalExecutor_);
        checkValues(number("37"), decision20Output);
    }

    @org.junit.Test
    public void testCase021() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();

        // Check Decision21
        java.math.BigDecimal decision21Output = new Decision21().apply(annotationSet_, eventListener_, externalExecutor_);
        checkValues(number("40"), decision21Output);
    }

    @org.junit.Test
    public void testCase022() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();

        // Check Decision22
        java.math.BigDecimal decision22Output = new Decision22().apply(annotationSet_, eventListener_, externalExecutor_);
        checkValues(number("261"), decision22Output);
    }

    @org.junit.Test
    public void testCase023() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();

        // Check Decision23
        java.math.BigDecimal decision23Output = new Decision23().apply(annotationSet_, eventListener_, externalExecutor_);
        checkValues(null, decision23Output);
    }

    @org.junit.Test
    public void testCase024() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();

        // Check Decision24
        java.math.BigDecimal decision24Output = new Decision24().apply(annotationSet_, eventListener_, externalExecutor_);
        checkValues(null, decision24Output);
    }

    @org.junit.Test
    public void testCase025() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();

        // Check Decision25
        java.math.BigDecimal decision25Output = new Decision25().apply(annotationSet_, eventListener_, externalExecutor_);
        checkValues(null, decision25Output);
    }

    @org.junit.Test
    public void testCase026() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();

        // Check Decision26
        java.math.BigDecimal decision26Output = new Decision26().apply(annotationSet_, eventListener_, externalExecutor_);
        checkValues(null, decision26Output);
    }

    @org.junit.Test
    public void testCase027() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();

        // Check Decision27
        java.math.BigDecimal decision27Output = new Decision27().apply(annotationSet_, eventListener_, externalExecutor_);
        checkValues(null, decision27Output);
    }

    @org.junit.Test
    public void testCase028() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();

        // Check Decision28
        java.math.BigDecimal decision28Output = new Decision28().apply(annotationSet_, eventListener_, externalExecutor_);
        checkValues(null, decision28Output);
    }

    @org.junit.Test
    public void testCase029() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();

        // Check Decision29
        java.math.BigDecimal decision29Output = new Decision29().apply(annotationSet_, eventListener_, externalExecutor_);
        checkValues(null, decision29Output);
    }

    @org.junit.Test
    public void testCase030() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();

        // Check Decision30
        java.math.BigDecimal decision30Output = new Decision30().apply(annotationSet_, eventListener_, externalExecutor_);
        checkValues(null, decision30Output);
    }

    @org.junit.Test
    public void testCase031() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();

        // Check Decision31
        java.math.BigDecimal decision31Output = new Decision31().apply(annotationSet_, eventListener_, externalExecutor_);
        checkValues(number("3"), decision31Output);
    }

    @org.junit.Test
    public void testCase032() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();

        // Check Decision32
        java.math.BigDecimal decision32Output = new Decision32().apply(annotationSet_, eventListener_, externalExecutor_);
        checkValues(number("7.5"), decision32Output);
    }

    @org.junit.Test
    public void testCase033() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();

        // Check Decision33
        java.math.BigDecimal decision33Output = new Decision33().apply(annotationSet_, eventListener_, externalExecutor_);
        checkValues(number("1200"), decision33Output);
    }

    private void checkValues(Object expected, Object actual) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual);
    }
}
