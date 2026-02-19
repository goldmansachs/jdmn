
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"junit.ftl", "0102-feel-constants.dmn"})
public class _0102FeelConstantsTest01Test extends com.gs.dmn.runtime.JavaTimeDMNBaseDecision<Object> {
    @org.junit.jupiter.api.Test
    public void testCase001_1() {

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();

        // Check 'Decision1'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues("foo bar", new Decision1().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase002_1() {

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();

        // Check 'Decision2'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues("šomeÚnicodeŠtriňg", new Decision2().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase003_1() {

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();

        // Check 'Decision3'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues("横綱", new Decision3().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase004_1() {

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();

        // Check 'Decision4'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues("thisIsSomeLongStringThatMustBeProcessedSoHopefullyThisTestPassWithItAndIMustWriteSomethingMoreSoItIsLongerAndLongerAndLongerAndLongerAndLongerTillItIsReallyLong", new Decision4().applyContext(input_, context_));
    }

    private void checkValues(Object expected, Object actual) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual);
    }
}
