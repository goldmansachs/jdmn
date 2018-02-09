
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"junit.ftl", "0010-concatenate.dmn"})
public class Test0010Concatenate extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    @org.junit.Test
    public void testCase001() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();
        // Initialize input data
        List<String> simpleList = asList("a", "b", "c");
        List<List<String>> nestedList = asList(asList("w", "x"), asList("y"), asList("z"));

        // Check literalSimpleList
        List<String> literalSimpleListOutput = new LiteralSimpleList().apply(annotationSet_, eventListener_, externalExecutor_);
        checkValues(asList("a", "b", "c"), literalSimpleListOutput);
        // Check literalNestedList
        List<List<String>> literalNestedListOutput = new LiteralNestedList().apply(annotationSet_, eventListener_, externalExecutor_);
        checkValues(asList(asList("w", "x"), asList("y"), asList("z")), literalNestedListOutput);
        // Check concatenate1
        List<String> concatenate1Output = new Concatenate1().apply(simpleList, annotationSet_, eventListener_, externalExecutor_);
        checkValues(asList("a", "b", "c", "a", "b", "c"), concatenate1Output);
        // Check concatenate2
        List<String> concatenate2Output = new Concatenate2().apply(nestedList, simpleList, annotationSet_, eventListener_, externalExecutor_);
        checkValues(asList("a", "b", "c", "w", "x", "y", "z"), concatenate2Output);
        // Check concatenate3
        List<String> concatenate3Output = new Concatenate3().apply(nestedList, annotationSet_, eventListener_, externalExecutor_);
        checkValues(asList("a", "b", "c", "w", "x", "y", "z"), concatenate3Output);
        // Check concatenate4
        List<List<String>> concatenate4Output = new Concatenate4().apply(annotationSet_, eventListener_, externalExecutor_);
        checkValues(asList(asList("a", "b", "c"), asList("w", "x"), asList("y"), asList("z")), concatenate4Output);
    }

    private void checkValues(Object expected, Object actual) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual);
    }
}
