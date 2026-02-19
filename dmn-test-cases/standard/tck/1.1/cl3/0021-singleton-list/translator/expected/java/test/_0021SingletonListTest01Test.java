
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"junit.ftl", "0021-singleton-list.dmn"})
public class _0021SingletonListTest01Test extends com.gs.dmn.runtime.JavaTimeDMNBaseDecision<Object> {
    @org.junit.jupiter.api.Test
    public void testCase001_1() {
        // Initialize inputs
        List<String> employees = asList("Jack", "John", "Bob", "Zack");

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();
        input_.add("Employees", employees);

        // Check 'decision1'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues(asList("John"), new Decision1().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase001_2() {
        // Initialize inputs
        List<String> employees = asList("Jack", "John", "Bob", "Zack");

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();
        input_.add("Employees", employees);

        // Check 'decision2'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues("John", new Decision2().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase001_3() {
        // Initialize inputs
        List<String> employees = asList("Jack", "John", "Bob", "Zack");

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();
        input_.add("Employees", employees);

        // Check 'decision3'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues(asList("Bob"), new Decision3().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase001_4() {
        // Initialize inputs
        List<String> employees = asList("Jack", "John", "Bob", "Zack");

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();
        input_.add("Employees", employees);

        // Check 'decision4'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues("Bob", new Decision4().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase001_5() {
        // Initialize inputs
        List<String> employees = asList("Jack", "John", "Bob", "Zack");

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();
        input_.add("Employees", employees);

        // Check 'decision5'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues("BOB", new Decision5().applyContext(input_, context_));
    }

    private void checkValues(Object expected, Object actual) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual);
    }
}
