
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"junit.ftl", "Model B1"})
public class _0089ModelB1Test01Test extends com.gs.dmn.runtime.JavaTimeDMNBaseDecision<Object> {
    @org.junit.jupiter.api.Test
    public void testCase001_1() {
        // Initialize inputs
        String personName = "B.A.John";

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();
        input_.add("personName", personName);

        // Check 'greetThePerson'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues("Hello, B.A.John", new GreetThePerson().applyContext(input_, context_));
    }

    private void checkValues(Object expected, Object actual) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual);
    }
}
