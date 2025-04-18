
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"junit.ftl", "af75837563be485d941eba0f9bf7a5f4/sid-268199D3-3811-4523-A39F-C24DAFA4A9AA"})
public class DecisionTest extends com.gs.dmn.signavio.runtime.JavaTimeSignavioBaseDecision {
    private final Decision decision = new Decision();

    @org.junit.jupiter.api.Test
    public void testCase1() {
        com.gs.dmn.runtime.ExecutionContext context_ = new com.gs.dmn.runtime.ExecutionContext();
        java.time.temporal.TemporalAccessor t = time("12:00:00+01:00");
        java.time.LocalDate d = date("2017-06-09");
        String decision = this.decision.apply(d, t, context_);

        checkValues("good", decision);
    }

    private void checkValues(Object expected, Object actual) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual);
    }
}
