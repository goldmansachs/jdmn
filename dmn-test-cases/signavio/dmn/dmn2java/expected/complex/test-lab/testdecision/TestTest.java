
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"junit.ftl", "85f2dd29e4774c2f84b883545afdd8cc/sid-F141013E-DA3F-4C5C-BC0A-60C28CB995B3"})
public class TestTest extends com.gs.dmn.signavio.runtime.DefaultSignavioBaseDecision {
    private final Test test = new Test();

    @org.junit.Test
    public void testCase1() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        String stringInput = "a";
        List<String> test = this.test.apply(stringInput, annotationSet_);

        checkValues(asList("a", "b"), test);
    }

    @org.junit.Test
    public void testCase2() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        String stringInput = "c";
        List<String> test = this.test.apply(stringInput, annotationSet_);

        checkValues(asList("a", "b"), test);
    }

    private void checkValues(Object expected, Object actual) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual);
    }
}
