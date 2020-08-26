
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"junit.ftl", "0030-user-defined-functions.dmn"})
public class Test0030UserDefinedFunctions extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    @org.junit.Test
    public void testCase001() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();
        com.gs.dmn.runtime.cache.Cache cache_ = new com.gs.dmn.runtime.cache.DefaultCache();
        // Initialize input data
        String stringInputA = "feel";
        String stringInputB = "#";

        // Check simpleFunctionInvocation
        checkValues("feel#feel#", new SimpleFunctionInvocation().apply(stringInputA, stringInputB, annotationSet_, eventListener_, externalExecutor_, cache_));
        // Check namedFunctionInvocation
        checkValues("#feel#feel", new NamedFunctionInvocation().apply(stringInputA, stringInputB, annotationSet_, eventListener_, externalExecutor_, cache_));
    }

    private void checkValues(Object expected, Object actual) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual);
    }
}
