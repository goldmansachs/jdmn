
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"junit.ftl", "ec84b81482a64a2fbfcec8b1c831507a/sid-C13631AF-EEAB-44EE-BB39-63D825570B60"})
public class DecisionLitexpTest extends com.gs.dmn.signavio.runtime.DefaultSignavioBaseDecision {
    private final DecisionLitexp decisionLitexp = new DecisionLitexp();

    @org.junit.Test
    public void testCase1() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        List<java.math.BigDecimal> numz = asList(number("1"), number("43"));
        List<String> redgreenbluelist1 = asList("Red", "Green");
        String redgreenblue1 = "Green";
        List<String> redgreenbluelist2 = asList("Blue", "Red");
        List<String> censored = asList("abc", "xyz");
        List<String> labels = asList("abc", "def", "stu", "xyz");
        String redgreenblue2 = "Blue";
        List<type.Zip> decisionLitexp = this.decisionLitexp.apply(censored, labels, numz, redgreenblue1, redgreenblue2, redgreenbluelist1, redgreenbluelist2, annotationSet_);

        checkValues(asList(new type.ZipImpl("Green", number("1"), "def"), new type.ZipImpl("Blue", number("43"), "stu"), new type.ZipImpl("Red", number("1"), null), new type.ZipImpl("Red", number("3"), null), new type.ZipImpl(null, number("5"), null), new type.ZipImpl(null, number("7"), null), new type.ZipImpl(null, number("9"), null)), decisionLitexp);
    }

    private void checkValues(Object expected, Object actual) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual);
    }
}
