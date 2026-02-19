
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"junit.ftl", "0005-literal-invocation.dmn"})
public class _0005LiteralInvocationTest01Test extends com.gs.dmn.runtime.JavaTimeDMNBaseDecision<Object> {
    @org.junit.jupiter.api.Test
    public void testCase001_1() {
        // Initialize inputs
        type.TLoan loan = new type.TLoanImpl(number("600000"), number("0.0375"), number("360"));
        java.lang.Number fee = number("100");

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();
        input_.add("Loan", loan);
        input_.add("fee", fee);

        // Check 'MonthlyPayment'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues(number("2878.69354943277"), new MonthlyPayment().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase002_1() {
        // Initialize inputs
        type.TLoan loan = new type.TLoanImpl(number("30000"), number("0.0475"), number("60"));
        java.lang.Number fee = number("100");

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();
        input_.add("Loan", loan);
        input_.add("fee", fee);

        // Check 'MonthlyPayment'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues(number("662.707359373292"), new MonthlyPayment().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase003_1() {
        // Initialize inputs
        type.TLoan loan = new type.TLoanImpl(number("600000"), number("0.0399"), number("360"));
        java.lang.Number fee = number("100");

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();
        input_.add("Loan", loan);
        input_.add("fee", fee);

        // Check 'MonthlyPayment'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues(number("2961.03377700389"), new MonthlyPayment().applyContext(input_, context_));
    }

    private void checkValues(Object expected, Object actual) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual);
    }
}
