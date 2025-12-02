
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"junit.ftl", "e3ea989c6b0b4b05950b7d24ade1b624/sid-4A7C793A-882C-4867-94B9-AD88D6D6970D"})
public class DecisionTest extends com.gs.dmn.signavio.runtime.JavaTimeSignavioBaseDecision {
    private final Decision decision = new Decision();

    @org.junit.jupiter.api.Test
    public void testCase1() {
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        java.time.temporal.TemporalAccessor timeInput = null;
        String textInput = null;
        java.lang.Number numberInput = numericUnaryMinus(number("1"));
        java.time.LocalDate dateInput = date("2016-08-01");
        String enumerationInput = "e1";
        java.time.temporal.TemporalAccessor dateAndTimeInput = null;
        Boolean booleanInput = Boolean.TRUE;
        String decision = this.decision.apply(booleanInput, dateAndTimeInput, dateInput, enumerationInput, numberInput, textInput, timeInput, context_);

        checkValues("r7", decision);
    }

    @org.junit.jupiter.api.Test
    public void testCase2() {
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        java.time.temporal.TemporalAccessor timeInput = null;
        String textInput = null;
        java.lang.Number numberInput = null;
        java.time.LocalDate dateInput = null;
        String enumerationInput = null;
        java.time.temporal.TemporalAccessor dateAndTimeInput = null;
        Boolean booleanInput = null;
        String decision = this.decision.apply(booleanInput, dateAndTimeInput, dateInput, enumerationInput, numberInput, textInput, timeInput, context_);

        checkValues("r9", decision);
    }

    private void checkValues(Object expected, Object actual) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual);
    }
}
