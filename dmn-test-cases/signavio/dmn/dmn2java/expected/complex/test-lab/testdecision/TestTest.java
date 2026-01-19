
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"junit.ftl", "85f2dd29e4774c2f84b883545afdd8cc/sid-F141013E-DA3F-4C5C-BC0A-60C28CB995B3"})
public class TestTest extends com.gs.dmn.signavio.runtime.JavaTimeSignavioBaseDecision<Object> {
    private final Test test = new Test();

    @org.junit.jupiter.api.Test
    public void testCase1() {
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        String stringInput = "a";
        List<String> test = this.test.apply(stringInput, context_);

        checkValues(asList("a", "b"), test);
    }

    @org.junit.jupiter.api.Test
    public void testCase2() {
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        String stringInput = "c";
        List<String> test = this.test.apply(stringInput, context_);

        checkValues(asList("a", "b"), test);
    }

    private void checkValues(Object expected, Object actual) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual);
    }
}
