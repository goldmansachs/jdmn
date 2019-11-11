
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"junit.ftl", "e3ea989c6b0b4b05950b7d24ade1b624/sid-4A7C793A-882C-4867-94B9-AD88D6D6970D"})
public class DecisionTest extends com.gs.dmn.signavio.runtime.DefaultSignavioBaseDecision {
    private final Decision decision = new Decision();

    @org.junit.Test
    public void testCase1() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        javax.xml.datatype.XMLGregorianCalendar timeInput = null;
        String textInput = null;
        java.math.BigDecimal numberInput = numericUnaryMinus(number("1"));
        javax.xml.datatype.XMLGregorianCalendar dateInput = date("2016-08-01");
        String enumerationInput = "e1";
        javax.xml.datatype.XMLGregorianCalendar dateAndTimeInput = null;
        Boolean booleanInput = Boolean.TRUE;
        String decision = this.decision.apply(booleanInput, dateAndTimeInput, dateInput, enumerationInput, numberInput, textInput, timeInput, annotationSet_);

        checkValues("r7", decision);
    }

    @org.junit.Test
    public void testCase2() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        javax.xml.datatype.XMLGregorianCalendar timeInput = null;
        String textInput = null;
        java.math.BigDecimal numberInput = null;
        javax.xml.datatype.XMLGregorianCalendar dateInput = null;
        String enumerationInput = null;
        javax.xml.datatype.XMLGregorianCalendar dateAndTimeInput = null;
        Boolean booleanInput = null;
        String decision = this.decision.apply(booleanInput, dateAndTimeInput, dateInput, enumerationInput, numberInput, textInput, timeInput, annotationSet_);

        checkValues("r9", decision);
    }

    private void checkValues(Object expected, Object actual) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual);
    }
}
