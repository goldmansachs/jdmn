
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"junit.ftl", "0030-user-defined-functions.dmn"})
public class _0030UserDefinedFunctionsTest01Test extends com.gs.dmn.runtime.JavaTimeDMNBaseDecision<Object> {
    @org.junit.jupiter.api.Test
    public void testCase001_1() {
        // Initialize inputs
        String stringInputA = "feel";
        String stringInputB = "#";

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();
        input_.add("stringInputA", stringInputA);
        input_.add("stringInputB", stringInputB);

        // Check 'simple function invocation'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues("feel#feel#", new SimpleFunctionInvocation().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase001_2() {
        // Initialize inputs
        String stringInputA = "feel";
        String stringInputB = "#";

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();
        input_.add("stringInputA", stringInputA);
        input_.add("stringInputB", stringInputB);

        // Check 'named function invocation'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues("#feel#feel", new NamedFunctionInvocation().applyContext(input_, context_));
    }

    private void checkValues(Object expected, Object actual) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual);
    }
}
