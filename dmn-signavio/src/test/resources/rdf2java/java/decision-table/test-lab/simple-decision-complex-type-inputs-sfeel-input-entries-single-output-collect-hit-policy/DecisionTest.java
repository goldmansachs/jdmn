
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"junit.ftl", "bd1194901b2149b8a8251ab2cc298992/sid-C52224F3-4EC1-4AAC-88AE-F5F51F285ED0"})
public class DecisionTest extends com.gs.dmn.signavio.runtime.DefaultSignavioBaseDecision {
    private final Decision decision = new Decision();

    @org.junit.Test
    public void testCase1() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        Boolean employed = null;
        type.Person person = new type.PersonImpl(null, null, null, null, null, null, asList(), null, null);
        List<String> decision = this.decision.apply(employed, person, annotationSet_);

        checkValues(asList(), decision);
    }

    @org.junit.Test
    public void testCase2() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        Boolean employed = Boolean.TRUE;
        type.Person person = new type.PersonImpl(date("2016-11-01"), null, "John", "male", number("1"), "Smith", asList(), Boolean.TRUE, null);
        List<String> decision = this.decision.apply(employed, person, annotationSet_);

        checkValues(asList(), decision);
    }

    @org.junit.Test
    public void testCase3() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        Boolean employed = Boolean.TRUE;
        type.Person person = new type.PersonImpl(date("2016-10-01"), dateAndTime("2016-10-01T01:00:00+01:00"), "Peter", "male", number("4"), "Sellers", asList("abc"), Boolean.FALSE, time("01:00:00+00:00"));
        List<String> decision = this.decision.apply(employed, person, annotationSet_);

        checkValues(asList(), decision);
    }

    private void checkValues(Object expected, Object actual) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual);
    }
}
