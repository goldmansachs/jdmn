
import java.util.*
import java.util.stream.Collectors

@javax.annotation.Generated(value = ["junit.ftl", "0009-invocation-arithmetic.dmn"])
class _0009InvocationArithmeticTest : com.gs.dmn.runtime.JavaTimeDMNBaseDecision() {
    @org.junit.jupiter.api.Test
    fun testCase001() {
        val context_ = com.gs.dmn.runtime.ExecutionContext()
        val cache_ = context_.getCache()
        // Initialize input data
        val loan: type.TLoan? = type.TLoanImpl(number("600000"), number("0.0375"), number("360"))
        val fee: kotlin.Number? = number("100")

        // Check 'MonthlyPayment'
        checkValues(number("2878.69354943277"), MonthlyPayment().apply(loan, fee, context_))
    }

    @org.junit.jupiter.api.Test
    fun testCase002() {
        val context_ = com.gs.dmn.runtime.ExecutionContext()
        val cache_ = context_.getCache()
        // Initialize input data
        val loan: type.TLoan? = type.TLoanImpl(number("30000"), number("0.0475"), number("60"))
        val fee: kotlin.Number? = number("100")

        // Check 'MonthlyPayment'
        checkValues(number("662.707359373292"), MonthlyPayment().apply(loan, fee, context_))
    }

    @org.junit.jupiter.api.Test
    fun testCase003() {
        val context_ = com.gs.dmn.runtime.ExecutionContext()
        val cache_ = context_.getCache()
        // Initialize input data
        val loan: type.TLoan? = type.TLoanImpl(number("600000"), number("0.0399"), number("360"))
        val fee: kotlin.Number? = number("100")

        // Check 'MonthlyPayment'
        checkValues(number("2961.03377700389"), MonthlyPayment().apply(loan, fee, context_))
    }

    private fun checkValues(expected: Any?, actual: Any?) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual)
    }
}
