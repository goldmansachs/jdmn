
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"junit.ftl", "0008-LX-arithmetic.dmn"})
public class _0008LXArithmeticTest extends com.gs.dmn.runtime.JavaTimeDMNBaseDecision {
    @org.junit.jupiter.api.Test
    public void testCase001_1() {
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();
        // Initialize arguments
        type.TLoan loan = new type.TLoanImpl(number("600000"), number("0.0375"), number("360"));

        // Check 'payment'
        checkValues(number("2778.69354943277"), new Payment().apply(loan, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase002_1() {
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();
        // Initialize arguments
        type.TLoan loan = new type.TLoanImpl(number("30000"), number("0.0475"), number("60"));

        // Check 'payment'
        checkValues(number("562.707359373292"), new Payment().apply(loan, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase003_1() {
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();
        // Initialize arguments
        type.TLoan loan = new type.TLoanImpl(number("600000"), number("0.0399"), number("360"));

        // Check 'payment'
        checkValues(number("2861.03377700389"), new Payment().apply(loan, context_));
    }

    private void checkValues(Object expected, Object actual) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual);
    }
}
