
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"junit.ftl", "41866ecb67af433192e1d722d74450b6/sid-20352F11-3D8C-44D4-8878-CAA7ACB9FCF8"})
public class MidTest extends com.gs.dmn.signavio.runtime.DefaultSignavioBaseDecision {
    private final Mid mid = new Mid();

    @org.junit.Test
    public void testCase1() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        List<java.math.BigDecimal> numz = asList(number("1"), number("2"));
        List<String> mid = this.mid.apply(numz, annotationSet_);

        checkValues(asList("child", "child"), mid);
    }

    @org.junit.Test
    public void testCase2() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        List<java.math.BigDecimal> numz = asList(number("50"), number("100"));
        List<String> mid = this.mid.apply(numz, annotationSet_);

        checkValues(asList("adult", "adult"), mid);
    }

    private void checkValues(Object expected, Object actual) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual);
    }
}
