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
        decisioninputs.type.TA decisioninputs_structA = new decisioninputs.type.TAImpl("widget", number("20"));

        // Check priceGt10
        checkValues(true, new decisiontables.PriceGt10().apply(decisioninputs_structA, annotationSet_, eventListener_, externalExecutor_, cache_));
    }

    @org.junit.Test
    public void testCase002() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();
        com.gs.dmn.runtime.cache.Cache cache_ = new com.gs.dmn.runtime.cache.DefaultCache();
        // Initialize input data
        decisioninputs.type.TA decisioninputs_structA = new decisioninputs.type.TAImpl("widget", number("20"));
        java.math.BigDecimal decisioninputs_numB = number("9");
        java.math.BigDecimal decisioninputs_numC = number("10");

        // Check priceInRange
        checkValues("Not in range", new decisiontables.PriceInRange().apply(decisioninputs_numB, decisioninputs_numC, decisioninputs_structA, annotationSet_, eventListener_, externalExecutor_, cache_));
    }

    @org.junit.Test
    public void testCase003() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();
        com.gs.dmn.runtime.cache.Cache cache_ = new com.gs.dmn.runtime.cache.DefaultCache();
        // Initialize input data
        javax.xml.datatype.XMLGregorianCalendar decisioninputs_dateD = date("2016-11-01");

        // Check dateCompare1
        checkValues(true, new decisiontables.DateCompare1().apply(decisioninputs_dateD, annotationSet_, eventListener_, externalExecutor_, cache_));
    }

    @org.junit.Test
    public void testCase004() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();
        com.gs.dmn.runtime.cache.Cache cache_ = new com.gs.dmn.runtime.cache.DefaultCache();
        // Initialize input data
        javax.xml.datatype.XMLGregorianCalendar decisioninputs_dateD = date("2016-11-01");
        javax.xml.datatype.XMLGregorianCalendar decisioninputs_dateE = date("2016-11-02");

        // Check dateCompare2
        checkValues(false, new decisiontables.DateCompare2().apply(decisioninputs_dateD, decisioninputs_dateE, annotationSet_, eventListener_, externalExecutor_, cache_));
    }

    private void checkValues(Object expected, Object actual) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual);
    }
}
