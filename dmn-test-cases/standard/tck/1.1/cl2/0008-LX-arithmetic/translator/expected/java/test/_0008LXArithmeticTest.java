
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"junit.ftl", "0008-LX-arithmetic.dmn"})
public class _0008LXArithmeticTest extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    @org.junit.Test
    public void testCase001() {
        com.gs.dmn.runtime.ExecutionContext context_ = new com.gs.dmn.runtime.ExecutionContext();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();
        // Initialize input data
        type.TLoan loan = new type.TLoanImpl(number("600000"), number("0.0375"), number("360"));

        // Check 'payment'
        checkValues(number("2778.69354943277"), new Payment().apply(loan, context_));
    }

    @org.junit.Test
    public void testCase002() {
        com.gs.dmn.runtime.ExecutionContext context_ = new com.gs.dmn.runtime.ExecutionContext();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();
        // Initialize input data
        type.TLoan loan = new type.TLoanImpl(number("30000"), number("0.0475"), number("60"));

        // Check 'payment'
        checkValues(number("562.707359373292"), new Payment().apply(loan, context_));
    }

    @org.junit.Test
    public void testCase003() {
        com.gs.dmn.runtime.ExecutionContext context_ = new com.gs.dmn.runtime.ExecutionContext();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();
        // Initialize input data
        type.TLoan loan = new type.TLoanImpl(number("600000"), number("0.0399"), number("360"));

        // Check 'payment'
        checkValues(number("2861.03377700389"), new Payment().apply(loan, context_));
    }

    private void checkValues(Object expected, Object actual) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual);
    }
}
