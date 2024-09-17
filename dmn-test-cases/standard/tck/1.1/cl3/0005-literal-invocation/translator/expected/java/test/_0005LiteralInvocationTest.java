
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"junit.ftl", "0005-literal-invocation.dmn"})
public class _0005LiteralInvocationTest extends com.gs.dmn.runtime.JavaTimeDMNBaseDecision {
    @org.junit.jupiter.api.Test
    public void testCase001() {
        com.gs.dmn.runtime.ExecutionContext context_ = new com.gs.dmn.runtime.ExecutionContext();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();
        // Initialize input data
        type.TLoan loan = new type.TLoanImpl(number("600000"), number("0.0375"), number("360"));
        java.lang.Number fee = number("100");

        // Check 'MonthlyPayment'
        checkValues(number("2878.69354943277"), new MonthlyPayment().apply(loan, fee, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase002() {
        com.gs.dmn.runtime.ExecutionContext context_ = new com.gs.dmn.runtime.ExecutionContext();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();
        // Initialize input data
        type.TLoan loan = new type.TLoanImpl(number("30000"), number("0.0475"), number("60"));
        java.lang.Number fee = number("100");

        // Check 'MonthlyPayment'
        checkValues(number("662.707359373292"), new MonthlyPayment().apply(loan, fee, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase003() {
        com.gs.dmn.runtime.ExecutionContext context_ = new com.gs.dmn.runtime.ExecutionContext();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();
        // Initialize input data
        type.TLoan loan = new type.TLoanImpl(number("600000"), number("0.0399"), number("360"));
        java.lang.Number fee = number("100");

        // Check 'MonthlyPayment'
        checkValues(number("2961.03377700389"), new MonthlyPayment().apply(loan, fee, context_));
    }

    private void checkValues(Object expected, Object actual) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual);
    }
}
