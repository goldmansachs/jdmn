
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"junit.ftl", "0032-conditionals.dmn"})
public class Test0032Conditionals extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    @org.junit.Test
    public void testCase001() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();
        // Initialize input data
        Boolean bool = true;
        java.math.BigDecimal num = number("100");

        // Check simpleIf
        java.math.BigDecimal simpleIfOutput = new SimpleIf().apply(bool, num, annotationSet_, eventListener_, externalExecutor_);
        checkValues(number("110"), simpleIfOutput);
    }

    @org.junit.Test
    public void testCase002() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();
        // Initialize input data
        Boolean bool = false;
        java.math.BigDecimal num = number("100");

        // Check simpleIf
        java.math.BigDecimal simpleIfOutput = new SimpleIf().apply(bool, num, annotationSet_, eventListener_, externalExecutor_);
        checkValues(number("90"), simpleIfOutput);
    }

    @org.junit.Test
    public void testCase003() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();
        // Initialize input data
        Boolean bool = null;
        java.math.BigDecimal num = number("100");

        // Check simpleIf
        java.math.BigDecimal simpleIfOutput = new SimpleIf().apply(bool, num, annotationSet_, eventListener_, externalExecutor_);
        checkValues(number("90"), simpleIfOutput);
    }

    @org.junit.Test
    public void testCase004() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();
        // Initialize input data
        javax.xml.datatype.XMLGregorianCalendar aDate = date("2017-01-02");
        String aString = "Hello World";

        // Check conditionWithFunctions
        String conditionWithFunctionsOutput = new ConditionWithFunctions().apply(aDate, aString, annotationSet_, eventListener_, externalExecutor_);
        checkValues("Hello", conditionWithFunctionsOutput);
    }

    @org.junit.Test
    public void testCase005() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();
        // Initialize input data
        javax.xml.datatype.XMLGregorianCalendar aDate = date("2017-01-01");
        String aString = "Hello World";

        // Check conditionWithFunctions
        String conditionWithFunctionsOutput = new ConditionWithFunctions().apply(aDate, aString, annotationSet_, eventListener_, externalExecutor_);
        checkValues("World", conditionWithFunctionsOutput);
    }

    @org.junit.Test
    public void testCase006() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();
        // Initialize input data
        javax.xml.datatype.XMLGregorianCalendar aDate = null;
        String aString = "Hello World";

        // Check conditionWithFunctions
        String conditionWithFunctionsOutput = new ConditionWithFunctions().apply(aDate, aString, annotationSet_, eventListener_, externalExecutor_);
        checkValues("World", conditionWithFunctionsOutput);
    }

    private void checkValues(Object expected, Object actual) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual);
    }
}
