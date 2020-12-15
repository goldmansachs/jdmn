
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"junit.ftl", "7bf105649e8445b39cb4d936497fbc1c/sid-2D02F64C-925A-483C-8D24-01D2F0FBAF75"})
public class MidTest extends com.gs.dmn.signavio.runtime.DefaultSignavioBaseDecision {
    private final Mid mid = new Mid();

    @org.junit.Test
    public void testCase1() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        List<java.math.BigDecimal> inputB = asList(number("34"), number("3"));
        List<String> inputA = asList("a", null);
        List<String> mid = this.mid.apply(inputA, inputB, annotationSet_);

        checkValues(asList("both defined", "number defined"), mid);
    }

    @org.junit.Test
    public void testCase2() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        List<java.math.BigDecimal> inputB = asList(number("12"));
        List<String> inputA = asList("b", "c");
        List<String> mid = this.mid.apply(inputA, inputB, annotationSet_);

        checkValues(asList("both defined", "text defined"), mid);
    }

    @org.junit.Test
    public void testCase3() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        List<java.math.BigDecimal> inputB = asList(number("213"), null, number("43"));
        List<String> inputA = asList("d", "e", "f");
        List<String> mid = this.mid.apply(inputA, inputB, annotationSet_);

        checkValues(asList("both defined", "text defined", "both defined"), mid);
    }

    @org.junit.Test
    public void testCase4() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        List<java.math.BigDecimal> inputB = asList(null);
        List<String> inputA = asList(null);
        List<String> mid = this.mid.apply(inputA, inputB, annotationSet_);

        checkValues(asList("neither defined"), mid);
    }

    private void checkValues(Object expected, Object actual) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual);
    }
}
