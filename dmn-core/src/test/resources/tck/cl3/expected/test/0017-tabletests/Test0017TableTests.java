
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"junit.ftl", "0017-tableTests.dmn"})
public class Test0017TableTests extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    @org.junit.Test
    public void testCase001() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();
        // Initialize input data
        type.TA structA = new type.TAImpl("widget", number("20"));

        // Check priceGt10
        Boolean priceGt10Output = new PriceGt10().apply(structA, annotationSet_, eventListener_, externalExecutor_);
        checkValues(true, priceGt10Output);
    }

    @org.junit.Test
    public void testCase002() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();
        // Initialize input data
        type.TA structA = new type.TAImpl("widget", number("20"));
        java.math.BigDecimal numB = number("9");
        java.math.BigDecimal numC = number("10");

        // Check priceInRange
        String priceInRangeOutput = new PriceInRange().apply(numB, numC, structA, annotationSet_, eventListener_, externalExecutor_);
        checkValues("Not in range", priceInRangeOutput);
    }

    @org.junit.Test
    public void testCase003() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();
        // Initialize input data
        javax.xml.datatype.XMLGregorianCalendar dateD = date("2016-11-01");

        // Check dateCompare1
        Boolean dateCompare1Output = new DateCompare1().apply(dateD, annotationSet_, eventListener_, externalExecutor_);
        checkValues(true, dateCompare1Output);
    }

    @org.junit.Test
    public void testCase004() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();
        // Initialize input data
        javax.xml.datatype.XMLGregorianCalendar dateD = date("2016-11-01");
        javax.xml.datatype.XMLGregorianCalendar dateE = date("2016-11-02");

        // Check dateCompare2
        Boolean dateCompare2Output = new DateCompare2().apply(dateD, dateE, annotationSet_, eventListener_, externalExecutor_);
        checkValues(false, dateCompare2Output);
    }

    private void checkValues(Object expected, Object actual) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual);
    }
}
