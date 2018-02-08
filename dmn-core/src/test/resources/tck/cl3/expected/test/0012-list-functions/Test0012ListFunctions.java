
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"junit.ftl", "0012-list-functions.dmn"})
public class Test0012ListFunctions extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    @org.junit.Test
    public void testCase001() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();
        // Initialize input data
        List<String> list1 = asList("a", "b", "c");
        List<String> list2 = asList("x", "y", "z");

        // Check listContainsList
        Boolean listContainsListOutput = new ListContainsList().apply(list1, list2, annotationSet_, eventListener_, externalExecutor_);
        checkValues(false, listContainsListOutput);
    }

    @org.junit.Test
    public void testCase002() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();
        // Initialize input data
        String string1 = "OK";
        List<String> list2 = asList("x", "y", "z");

        // Check listContains
        Boolean listContainsOutput = new ListContains().apply(list2, string1, annotationSet_, eventListener_, externalExecutor_);
        checkValues(false, listContainsOutput);
    }

    @org.junit.Test
    public void testCase003() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();
        // Initialize input data
        List<String> list1 = asList("a", "b", "c");

        // Check count1
        java.math.BigDecimal count1Output = new Count1().apply(list1, annotationSet_, eventListener_, externalExecutor_);
        checkValues(number("3"), count1Output);
    }

    @org.junit.Test
    public void testCase004() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();
        // Initialize input data
        List<java.math.BigDecimal> numList = asList(number("6"), number("14"), number("-3"));

        // Check min1
        java.math.BigDecimal min1Output = new Min1().apply(numList, annotationSet_, eventListener_, externalExecutor_);
        checkValues(number("-3"), min1Output);
    }

    @org.junit.Test
    public void testCase005() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();
        // Initialize input data
        List<java.math.BigDecimal> numList = asList(number("6"), number("14"), number("-3"));

        // Check sum1
        java.math.BigDecimal sum1Output = new Sum1().apply(numList, annotationSet_, eventListener_, externalExecutor_);
        checkValues(number("17"), sum1Output);
    }

    @org.junit.Test
    public void testCase006() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();
        // Initialize input data
        List<java.math.BigDecimal> numList = asList(number("6"), number("14"), number("-3"));

        // Check mean1
        java.math.BigDecimal mean1Output = new Mean1().apply(numList, annotationSet_, eventListener_, externalExecutor_);
        checkValues(number("5.66666666666667"), mean1Output);
    }

    @org.junit.Test
    public void testCase007() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();
        // Initialize input data
        java.math.BigDecimal num1 = number("11");
        java.math.BigDecimal num2 = number("2");
        java.math.BigDecimal num3 = number("10");

        // Check mean2
        java.math.BigDecimal mean2Output = new Mean2().apply(num1, num2, num3, annotationSet_, eventListener_, externalExecutor_);
        checkValues(number("7.66666666666667"), mean2Output);
    }

    @org.junit.Test
    public void testCase008() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();
        // Initialize input data
        List<String> list1 = asList("a", "b", "c");

        // Check sublist12
        List<String> sublist12Output = new Sublist12().apply(list1, annotationSet_, eventListener_, externalExecutor_);
        checkValues(asList("a", "b"), sublist12Output);
    }

    @org.junit.Test
    public void testCase009() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();
        // Initialize input data
        List<String> list1 = asList("a", "b", "c");

        // Check sublistLast
        List<String> sublistLastOutput = new SublistLast().apply(list1, annotationSet_, eventListener_, externalExecutor_);
        checkValues(asList("c"), sublistLastOutput);
    }

    @org.junit.Test
    public void testCase010() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();
        // Initialize input data
        java.math.BigDecimal num1 = number("11");
        java.math.BigDecimal num2 = number("2");
        java.math.BigDecimal num3 = number("10");
        List<java.math.BigDecimal> numList = asList(number("6"), number("14"), number("-3"));

        // Check append1
        List<java.math.BigDecimal> append1Output = new Append1().apply(num1, num2, numList, annotationSet_, eventListener_, externalExecutor_);
        checkValues(asList(number("6"), number("14"), number("-3"), number("11"), number("2")), append1Output);
    }

    @org.junit.Test
    public void testCase011() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();
        // Initialize input data
        List<String> list1 = asList("a", "b", "c");
        List<String> list2 = asList("x", "y", "z");

        // Check concatenate1
        List<String> concatenate1Output = new Concatenate1().apply(list1, list2, annotationSet_, eventListener_, externalExecutor_);
        checkValues(asList("a", "b", "c", "x", "y", "z"), concatenate1Output);
    }

    @org.junit.Test
    public void testCase012() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();
        // Initialize input data
        String string1 = "OK";
        List<String> list2 = asList("x", "y", "z");

        // Check insertBefore1
        List<String> insertBefore1Output = new InsertBefore1().apply(list2, string1, annotationSet_, eventListener_, externalExecutor_);
        checkValues(asList("x", "OK", "y", "z"), insertBefore1Output);
    }

    @org.junit.Test
    public void testCase013() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();
        // Initialize input data
        List<String> list2 = asList("x", "y", "z");

        // Check remove2nd
        List<String> remove2ndOutput = new Remove2nd().apply(list2, annotationSet_, eventListener_, externalExecutor_);
        checkValues(asList("x", "z"), remove2ndOutput);
    }

    @org.junit.Test
    public void testCase014() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();
        // Initialize input data
        List<String> list1 = asList("a", "b", "c");
        List<String> list2 = asList("x", "y", "z");

        // Check reverse1
        List<String> reverse1Output = new Reverse1().apply(list1, list2, annotationSet_, eventListener_, externalExecutor_);
        checkValues(asList("z", "y", "x", "c", "b", "a"), reverse1Output);
    }

    @org.junit.Test
    public void testCase015() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();
        // Initialize input data
        List<String> list1 = asList("a", "b", "c");
        List<String> list2 = asList("x", "y", "z");

        // Check appendListItem
        List<List<String>> appendListItemOutput = new AppendListItem().apply(list1, list2, annotationSet_, eventListener_, externalExecutor_);
        checkValues(asList("a", "b", "c", asList("x", "y", "z")), appendListItemOutput);
    }

    @org.junit.Test
    public void testCase016() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();
        // Initialize input data
        String string1 = "OK";
        List<String> list2 = asList("x", "y", "z");

        // Check indexOf1
        List<java.math.BigDecimal> indexOf1Output = new IndexOf1().apply(list2, string1, annotationSet_, eventListener_, externalExecutor_);
        checkValues(asList(), indexOf1Output);
    }

    @org.junit.Test
    public void testCase017() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();
        // Initialize input data
        String string1 = "OK";
        List<String> list1 = asList("a", "b", "c");
        List<String> list2 = asList("x", "y", "z");

        // Check union1
        List<String> union1Output = new Union1().apply(list1, list2, string1, annotationSet_, eventListener_, externalExecutor_);
        checkValues(asList("x", "OK", "y", "z", "a", "b", "c"), union1Output);
    }

    @org.junit.Test
    public void testCase018() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();
        // Initialize input data
        String string1 = "OK";
        List<String> list2 = asList("x", "y", "z");

        // Check distinctVals
        List<String> distinctValsOutput = new DistinctVals().apply(list2, string1, annotationSet_, eventListener_, externalExecutor_);
        checkValues(asList("x", "OK", "y", "z"), distinctValsOutput);
    }

    @org.junit.Test
    public void testCase019() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();
        // Initialize input data
        List<String> list1 = asList("a", "b", "c");
        List<String> list2 = asList("x", "y", "z");

        // Check flatten1
        List<String> flatten1Output = new Flatten1().apply(list1, list2, annotationSet_, eventListener_, externalExecutor_);
        checkValues(asList("a", "b", "c", "x", "y", "z"), flatten1Output);
    }

    private void checkValues(Object expected, Object actual) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual);
    }
}
