
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"junit.ftl", "7a41c638739441ef88d9fe7501233ef8/sid-2ABA9455-1BAD-4552-BE44-9FE612EA1D10"})
public class AllTogetherTest extends com.gs.dmn.signavio.runtime.DefaultSignavioBaseDecision {
    private final AllTogether allTogether = new AllTogether();

    @org.junit.Test
    public void testCase1() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        Boolean booleanA = Boolean.TRUE;
        java.math.BigDecimal numberB = number("34");
        javax.xml.datatype.XMLGregorianCalendar dateTime = dateAndTime("2015-01-01T12:00:00+00:00");
        String string = "00.00";
        Boolean booleanB = Boolean.FALSE;
        List<Boolean> booleanList = asList(Boolean.TRUE, Boolean.FALSE, Boolean.TRUE);
        List<java.math.BigDecimal> numberList = asList(number("10"), number("20"), number("30"), number("40"));
        java.math.BigDecimal numberA = number("12");
        javax.xml.datatype.XMLGregorianCalendar time = time("13:00:00+00:00");
        List<String> stringList = asList("Foo", "Bar");
        javax.xml.datatype.XMLGregorianCalendar date = date("2015-01-01");
        String allTogether = this.allTogether.apply(booleanA, booleanB, booleanList, date, dateTime, numberA, numberB, numberList, string, stringList, time, annotationSet_);

        checkValues("NotNull", allTogether);
    }

    @org.junit.Test
    public void testCase2() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        Boolean booleanA = Boolean.TRUE;
        java.math.BigDecimal numberB = number("43");
        javax.xml.datatype.XMLGregorianCalendar dateTime = dateAndTime("2016-11-16T12:00:00+00:00");
        String string = "0.0";
        Boolean booleanB = Boolean.TRUE;
        List<Boolean> booleanList = asList();
        List<java.math.BigDecimal> numberList = asList(null, null, null);
        java.math.BigDecimal numberA = number("12");
        javax.xml.datatype.XMLGregorianCalendar time = time("12:00:00+00:00");
        List<String> stringList = asList("Some", "Thing");
        javax.xml.datatype.XMLGregorianCalendar date = date("2016-11-16");
        String allTogether = this.allTogether.apply(booleanA, booleanB, booleanList, date, dateTime, numberA, numberB, numberList, string, stringList, time, annotationSet_);

        checkValues(null, allTogether);
    }

    @org.junit.Test
    public void testCase3() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        Boolean booleanA = Boolean.FALSE;
        java.math.BigDecimal numberB = number("1");
        javax.xml.datatype.XMLGregorianCalendar dateTime = dateAndTime("2105-11-11T00:00:00+00:00");
        String string = "X";
        Boolean booleanB = Boolean.TRUE;
        List<Boolean> booleanList = asList(Boolean.TRUE, Boolean.TRUE, Boolean.FALSE, Boolean.TRUE, Boolean.FALSE);
        List<java.math.BigDecimal> numberList = asList(number("1"), number("2"), number("1"));
        java.math.BigDecimal numberA = number("23");
        javax.xml.datatype.XMLGregorianCalendar time = time("11:11:11+00:00");
        List<String> stringList = asList("1", "2", "3");
        javax.xml.datatype.XMLGregorianCalendar date = date("2016-11-09");
        String allTogether = this.allTogether.apply(booleanA, booleanB, booleanList, date, dateTime, numberA, numberB, numberList, string, stringList, time, annotationSet_);

        checkValues(null, allTogether);
    }

    @org.junit.Test
    public void testCase4() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        Boolean booleanA = Boolean.FALSE;
        java.math.BigDecimal numberB = number("22");
        javax.xml.datatype.XMLGregorianCalendar dateTime = dateAndTime("2016-11-01T01:01:01+00:00");
        String string = "0.0##";
        Boolean booleanB = Boolean.TRUE;
        List<Boolean> booleanList = asList(Boolean.TRUE);
        List<java.math.BigDecimal> numberList = asList(number("1"), number("2"), number("3"));
        java.math.BigDecimal numberA = number("11");
        javax.xml.datatype.XMLGregorianCalendar time = time("12:12:12+00:00");
        List<String> stringList = asList("a", "d", "s");
        javax.xml.datatype.XMLGregorianCalendar date = date("2016-11-01");
        String allTogether = this.allTogether.apply(booleanA, booleanB, booleanList, date, dateTime, numberA, numberB, numberList, string, stringList, time, annotationSet_);

        checkValues("NotNull", allTogether);
    }

    @org.junit.Test
    public void testCase5() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        Boolean booleanA = Boolean.FALSE;
        java.math.BigDecimal numberB = number("90");
        javax.xml.datatype.XMLGregorianCalendar dateTime = dateAndTime("2017-01-01T10:00:00+00:00");
        String string = "000";
        Boolean booleanB = Boolean.FALSE;
        List<Boolean> booleanList = asList(Boolean.TRUE, Boolean.FALSE, Boolean.TRUE, Boolean.FALSE);
        List<java.math.BigDecimal> numberList = asList(number("9"), number("8"), number("7"));
        java.math.BigDecimal numberA = number("90");
        javax.xml.datatype.XMLGregorianCalendar time = time("12:12:12+00:00");
        List<String> stringList = asList("A", "B", "C");
        javax.xml.datatype.XMLGregorianCalendar date = date("2016-11-18");
        String allTogether = this.allTogether.apply(booleanA, booleanB, booleanList, date, dateTime, numberA, numberB, numberList, string, stringList, time, annotationSet_);

        checkValues("NotNull", allTogether);
    }

    @org.junit.Test
    public void testCase6() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        Boolean booleanA = Boolean.TRUE;
        java.math.BigDecimal numberB = number("42");
        javax.xml.datatype.XMLGregorianCalendar dateTime = dateAndTime("2016-11-03T04:00:00+00:00");
        String string = "#";
        Boolean booleanB = Boolean.TRUE;
        List<Boolean> booleanList = asList(Boolean.FALSE);
        List<java.math.BigDecimal> numberList = asList(number("4"), number("24"), number("4"));
        java.math.BigDecimal numberA = number("12");
        javax.xml.datatype.XMLGregorianCalendar time = time("11:11:11+00:00");
        List<String> stringList = asList("123", "234", "345");
        javax.xml.datatype.XMLGregorianCalendar date = date("2016-11-03");
        String allTogether = this.allTogether.apply(booleanA, booleanB, booleanList, date, dateTime, numberA, numberB, numberList, string, stringList, time, annotationSet_);

        checkValues("NotNull", allTogether);
    }

    private void checkValues(Object expected, Object actual) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual);
    }
}
