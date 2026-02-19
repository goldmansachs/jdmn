
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"junit.ftl", "0005-simpletable-A.dmn"})
public class _0005SimpletableATest01Test extends com.gs.dmn.runtime.JavaTimeDMNBaseDecision<Object> {
    @org.junit.jupiter.api.Test
    public void testCase001_1() {
        // Initialize inputs
        java.lang.Number age = number("18");
        String riskCategory = "Medium";
        Boolean isAffordable = Boolean.TRUE;

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();
        input_.add("Age", age);
        input_.add("RiskCategory", riskCategory);
        input_.add("isAffordable", isAffordable);

        // Check 'Approval Status'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues("Approved", new ApprovalStatus().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase002_1() {
        // Initialize inputs
        java.lang.Number age = number("17");
        String riskCategory = "Medium";
        Boolean isAffordable = Boolean.TRUE;

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();
        input_.add("Age", age);
        input_.add("RiskCategory", riskCategory);
        input_.add("isAffordable", isAffordable);

        // Check 'Approval Status'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues("Declined", new ApprovalStatus().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase003_1() {
        // Initialize inputs
        java.lang.Number age = number("18");
        String riskCategory = "High";
        Boolean isAffordable = Boolean.TRUE;

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();
        input_.add("Age", age);
        input_.add("RiskCategory", riskCategory);
        input_.add("isAffordable", isAffordable);

        // Check 'Approval Status'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues("Declined", new ApprovalStatus().applyContext(input_, context_));
    }

    private void checkValues(Object expected, Object actual) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual);
    }
}
