
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"junit.ftl", "Model C"})
public class ModelCTest extends com.gs.dmn.runtime.JavaTimeDMNBaseDecision {
    @org.junit.jupiter.api.Test
    public void testCase001() {
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();
        // Initialize input data
        java.lang.Number aa = number("1000");
        String ba = "$";

        // Check 'c'
        checkValues("AA: 1000; BA: $", new C().apply(aa, ba, context_));
    }

    private void checkValues(Object expected, Object actual) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual);
    }
}
