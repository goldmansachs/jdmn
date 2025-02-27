
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"junit.ftl", "adc9746fc84f49ceabcfad773a2cb4b2/sid-4A7C793A-882C-4867-94B9-AD88D6D6970D"})
public class CompoundOutputCompoundDecisionTest extends com.gs.dmn.signavio.runtime.JavaTimeSignavioBaseDecision {
    private final CompoundOutputCompoundDecision compoundOutputCompoundDecision = new CompoundOutputCompoundDecision();

    @org.junit.jupiter.api.Test
    public void testCase1() {
        com.gs.dmn.runtime.ExecutionContext context_ = new com.gs.dmn.runtime.ExecutionContext();
        java.lang.Number dD2NumberInput = number("1");
        String enumerationInput = "e1";
        Boolean booleanInput = Boolean.TRUE;
        String dD1TextInput = "a";
        type.CompoundOutputCompoundDecision compoundOutputCompoundDecision = this.compoundOutputCompoundDecision.apply(booleanInput, dD1TextInput, dD2NumberInput, enumerationInput, context_);

        checkValues("r11", compoundOutputCompoundDecision == null ? null : compoundOutputCompoundDecision.getFirstOutput());
        checkValues("r12", compoundOutputCompoundDecision == null ? null : compoundOutputCompoundDecision.getSecondOutput());
    }

    private void checkValues(Object expected, Object actual) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual);
    }
}
