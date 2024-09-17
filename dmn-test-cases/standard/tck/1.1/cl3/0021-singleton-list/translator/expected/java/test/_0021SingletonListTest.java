
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"junit.ftl", "0021-singleton-list.dmn"})
public class _0021SingletonListTest extends com.gs.dmn.runtime.JavaTimeDMNBaseDecision {
    @org.junit.jupiter.api.Test
    public void testCase001() {
        com.gs.dmn.runtime.ExecutionContext context_ = new com.gs.dmn.runtime.ExecutionContext();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();
        // Initialize input data
        List<String> employees = asList("Jack", "John", "Bob", "Zack");

        // Check 'decision1'
        checkValues(asList("John"), new Decision1().apply(employees, context_));
        // Check 'decision2'
        checkValues("John", new Decision2().apply(employees, context_));
        // Check 'decision3'
        checkValues(asList("Bob"), new Decision3().apply(employees, context_));
        // Check 'decision4'
        checkValues("Bob", new Decision4().apply(employees, context_));
        // Check 'decision5'
        checkValues("BOB", new Decision5().apply(employees, context_));
    }

    private void checkValues(Object expected, Object actual) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual);
    }
}
