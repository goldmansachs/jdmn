
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"junit.ftl", "0009-append-flatten.dmn"})
public class Test0009AppendFlatten extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
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
        // Check append1
        List<List<String>> append1Output = new Append1().apply(annotationSet_, eventListener_, externalExecutor_);
        checkValues(asList(asList("w", "x"), asList("y"), asList("z"), asList("t")), append1Output);
        // Check append2
        List<List<String>> append2Output = new Append2().apply(nestedList, simpleList, annotationSet_, eventListener_, externalExecutor_);
        checkValues(asList(asList("w", "x"), asList("y"), asList("z"), asList("a", "b", "c")), append2Output);
        // Check append3
        List<List<String>> append3Output = new Append3().apply(nestedList, annotationSet_, eventListener_, externalExecutor_);
        checkValues(asList(asList("w", "x"), asList("y"), asList("z"), asList("a", "b", "c")), append3Output);
        // Check append4
        List<List<String>> append4Output = new Append4().apply(annotationSet_, eventListener_, externalExecutor_);
        checkValues(asList(asList("w", "x"), asList("y"), asList("z"), asList("a", "b", "c")), append4Output);
        // Check flatten1
        List<String> flatten1Output = new Flatten1().apply(annotationSet_, eventListener_, externalExecutor_);
        checkValues(asList("w", "x", "y", "z", "t"), flatten1Output);
        // Check flatten2
        List<String> flatten2Output = new Flatten2().apply(nestedList, simpleList, annotationSet_, eventListener_, externalExecutor_);
        checkValues(asList("w", "x", "y", "z", "a", "b", "c"), flatten2Output);
        // Check flatten3
        List<String> flatten3Output = new Flatten3().apply(nestedList, annotationSet_, eventListener_, externalExecutor_);
        checkValues(asList("w", "x", "y", "z", "a", "b", "c"), flatten3Output);
        // Check flatten4
        List<String> flatten4Output = new Flatten4().apply(annotationSet_, eventListener_, externalExecutor_);
        checkValues(asList("w", "x", "y", "z", "a", "b", "c"), flatten4Output);
    }

    private void checkValues(Object expected, Object actual) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual);
    }
}
