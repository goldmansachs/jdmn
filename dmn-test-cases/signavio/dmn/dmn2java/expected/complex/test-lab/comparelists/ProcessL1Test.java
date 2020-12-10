
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"junit.ftl", "b01e263f2b324caab26b2040a56f8ed1/sid-ECF0E605-F9BA-4794-BB2D-6598A81C7424"})
public class ProcessL1Test extends com.gs.dmn.signavio.runtime.DefaultSignavioBaseDecision {
    private final ProcessL1 processL1 = new ProcessL1();

    @org.junit.Test
    public void testCase1() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        List<java.math.BigDecimal> l1 = asList(number("1"), number("2"), number("3"), number("4"));
        List<java.math.BigDecimal> l23 = asList(number("1"), number("1"), number("2"), number("3"), number("5"), number("8"));
        List<java.math.BigDecimal> processL1 = this.processL1.apply(l1, l23, annotationSet_);

        checkValues(asList(number("2"), number("1"), number("1"), number("0")), processL1);
    }

    @org.junit.Test
    public void testCase2() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        List<java.math.BigDecimal> l1 = asList(number("10"), number("20"), number("30"));
        List<java.math.BigDecimal> l23 = asList(number("5"), number("10"), number("15"), number("20"));
        List<java.math.BigDecimal> processL1 = this.processL1.apply(l1, l23, annotationSet_);

        checkValues(asList(number("1"), number("1"), number("0")), processL1);
    }

    @org.junit.Test
    public void testCase3() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        List<java.math.BigDecimal> l1 = asList(numericUnaryMinus(number("1")), number("0"), number("1"));
        List<java.math.BigDecimal> l23 = asList(number("2"), number("3"), number("4"));
        List<java.math.BigDecimal> processL1 = this.processL1.apply(l1, l23, annotationSet_);

        checkValues(asList(number("0"), number("0"), number("0")), processL1);
    }

    private void checkValues(Object expected, Object actual) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual);
    }
}
