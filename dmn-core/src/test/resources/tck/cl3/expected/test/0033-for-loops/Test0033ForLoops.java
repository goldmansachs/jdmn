
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"junit.ftl", "0033-for-loops.dmn"})
public class Test0033ForLoops extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    @org.junit.Test
    public void testCase001() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();
        // Initialize input data
        List<java.math.BigDecimal> heights = asList(number("10"), number("20"), number("30"));

        // Check increase1
        List<java.math.BigDecimal> increase1Output = new Increase1().apply(heights, annotationSet_, eventListener_, externalExecutor_);
        checkValues(asList(number("11"), number("21"), number("31")), increase1Output);
    }

    @org.junit.Test
    public void testCase002() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();
        // Initialize input data
        List<java.math.BigDecimal> heights = asList(number("10"), number("20"), number("30"));
        List<java.math.BigDecimal> widths = asList(number("2"), number("3"));

        // Check areas
        List<java.math.BigDecimal> areasOutput = new Areas().apply(heights, widths, annotationSet_, eventListener_, externalExecutor_);
        checkValues(asList(number("20"), number("30"), number("40"), number("60"), number("60"), number("90")), areasOutput);
    }

    @org.junit.Test
    public void testCase003() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();
        // Initialize input data
        java.math.BigDecimal value = number("35");
        List<java.math.BigDecimal> factors = asList(number("2"), number("3"), number("5"), number("7"), number("11"));

        // Check checkFactors
        List<Boolean> checkFactorsOutput = new CheckFactors().apply(factors, value, annotationSet_, eventListener_, externalExecutor_);
        checkValues(asList(false, false, true, true, false), checkFactorsOutput);
    }

    @org.junit.Test
    public void testCase004() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();
        // Initialize input data
        java.math.BigDecimal value = number("10");

        // Check multiples
        List<java.math.BigDecimal> multiplesOutput = new Multiples().apply(value, annotationSet_, eventListener_, externalExecutor_);
        checkValues(asList(number("20"), number("30"), number("40"), number("50")), multiplesOutput);
    }

    private void checkValues(Object expected, Object actual) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual);
    }
}
