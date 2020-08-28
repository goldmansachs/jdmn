package decisiontables;

import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"junit.ftl", "decisionTables"})
public class TestdecisionTables extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    @org.junit.Test
    public void testCase001() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();
        com.gs.dmn.runtime.cache.Cache cache_ = new com.gs.dmn.runtime.cache.DefaultCache();
        // Initialize input data
        decisioninputs1.type.TA decisioninputs1_structA = new decisioninputs1.type.TAImpl("widget", number("20"));

        // Check priceGt10
        checkValues(true, new decisiontables.PriceGt10().apply(decisioninputs1_structA, annotationSet_, eventListener_, externalExecutor_, cache_));
    }

    @org.junit.Test
    public void testCase002() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();
        com.gs.dmn.runtime.cache.Cache cache_ = new com.gs.dmn.runtime.cache.DefaultCache();
        // Initialize input data
        decisioninputs1.type.TA decisioninputs1_structA = new decisioninputs1.type.TAImpl("widget", number("20"));
        java.math.BigDecimal decisioninputs1_numB = number("9");
        java.math.BigDecimal decisioninputs1_numC = number("10");

        // Check priceInRange
        checkValues("Not in range", new decisiontables.PriceInRange().apply(decisioninputs1_numB, decisioninputs1_numC, decisioninputs1_structA, annotationSet_, eventListener_, externalExecutor_, cache_));
    }

    @org.junit.Test
    public void testCase003() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();
        com.gs.dmn.runtime.cache.Cache cache_ = new com.gs.dmn.runtime.cache.DefaultCache();
        // Initialize input data
        javax.xml.datatype.XMLGregorianCalendar decisioninputs1_dateD = date("2016-11-01");

        // Check dateCompare1
        checkValues(true, new decisiontables.DateCompare1().apply(decisioninputs1_dateD, annotationSet_, eventListener_, externalExecutor_, cache_));
    }

    @org.junit.Test
    public void testCase004() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();
        com.gs.dmn.runtime.cache.Cache cache_ = new com.gs.dmn.runtime.cache.DefaultCache();
        // Initialize input data
        javax.xml.datatype.XMLGregorianCalendar decisioninputs1_dateD = date("2016-11-01");
        javax.xml.datatype.XMLGregorianCalendar decisioninputs2_dateD = date("2016-11-02");

        // Check dateCompare2
        checkValues(false, new decisiontables.DateCompare2().apply(decisioninputs1_dateD, decisioninputs2_dateD, annotationSet_, eventListener_, externalExecutor_, cache_));
    }

    private void checkValues(Object expected, Object actual) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual);
    }
}
