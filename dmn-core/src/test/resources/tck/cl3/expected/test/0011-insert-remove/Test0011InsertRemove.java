
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"junit.ftl", "0011-insert-remove.dmn"})
public class Test0011InsertRemove extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    @org.junit.Test
    public void testCase001() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();
        // Initialize input data
        List<String> simpleList = asList("a", "b", "c");
        List<List<String>> nestedList = asList(asList("o"), asList("p", "q"));
        java.math.BigDecimal position = number("2");

        // Check literalNestedList
        List<List<String>> literalNestedListOutput = new LiteralNestedList().apply(annotationSet_, eventListener_, externalExecutor_);
        checkValues(asList(asList("a", "b"), asList("b", "c")), literalNestedListOutput);
        // Check remove1
        List<String> remove1Output = new Remove1().apply(position, simpleList, annotationSet_, eventListener_, externalExecutor_);
        checkValues(asList("a", "c"), remove1Output);
        // Check insert3
        List<List<String>> insert3Output = new Insert3().apply(nestedList, position, simpleList, annotationSet_, eventListener_, externalExecutor_);
        checkValues(asList(asList("o"), asList("a", "b", "c"), asList("p", "q")), insert3Output);
        // Check insert2
        List<List<String>> insert2Output = new Insert2().apply(position, simpleList, annotationSet_, eventListener_, externalExecutor_);
        checkValues(asList(asList("a", "b"), asList("a", "b", "c"), asList("b", "c")), insert2Output);
        // Check remove2
        List<List<String>> remove2Output = new Remove2().apply(position, annotationSet_, eventListener_, externalExecutor_);
        checkValues(asList(asList("a", "b")), remove2Output);
        // Check insert1
        List<String> insert1Output = new Insert1().apply(position, simpleList, annotationSet_, eventListener_, externalExecutor_);
        checkValues(asList("a", "x", "b", "c"), insert1Output);
    }

    private void checkValues(Object expected, Object actual) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual);
    }
}
