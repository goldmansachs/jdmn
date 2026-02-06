
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"junit.ftl", "0010-multi-output-U.dmn"})
public class _0010MultiOutputUTest extends com.gs.dmn.runtime.JavaTimeDMNBaseDecision<Object> {
    @org.junit.jupiter.api.Test
    public void testCase001_1() {
        // Initialize input
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();
        input_.add("Age", number("18"));
        input_.add("RiskCategory", "Medium");
        input_.add("isAffordable", Boolean.TRUE);

        // Check 'Approval'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues(type.TApproval.toTApproval(new com.gs.dmn.runtime.Context().add("Rate", "Standard").add("Status", "Approved")), new Approval().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase002_1() {
        // Initialize input
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();
        input_.add("Age", number("17"));
        input_.add("RiskCategory", "Medium");
        input_.add("isAffordable", Boolean.TRUE);

        // Check 'Approval'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues(type.TApproval.toTApproval(new com.gs.dmn.runtime.Context().add("Rate", "Standard").add("Status", "Declined")), new Approval().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase003_1() {
        // Initialize input
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();
        input_.add("Age", number("18"));
        input_.add("RiskCategory", "High");
        input_.add("isAffordable", Boolean.TRUE);

        // Check 'Approval'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues(type.TApproval.toTApproval(new com.gs.dmn.runtime.Context().add("Rate", "Standard").add("Status", "Declined")), new Approval().applyContext(input_, context_));
    }

    private void checkValues(Object expected, Object actual) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual);
    }
}
