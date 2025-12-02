
import java.util.*
import java.util.stream.Collectors

@javax.annotation.Generated(value = ["junit.ftl", "0008-LX-arithmetic.dmn"])
class _0008LXArithmeticTest : com.gs.dmn.runtime.JavaTimeDMNBaseDecision() {
    @org.junit.jupiter.api.Test
    fun testCase001() {
        val context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build()
        val cache_ = context_.getCache()
        // Initialize input data
        val loan: type.TLoan? = type.TLoanImpl(number("600000"), number("0.0375"), number("360"))

        // Check 'payment'
        checkValues(number("2778.69354943277"), Payment().apply(loan, context_))
    }

    @org.junit.jupiter.api.Test
    fun testCase002() {
        val context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build()
        val cache_ = context_.getCache()
        // Initialize input data
        val loan: type.TLoan? = type.TLoanImpl(number("30000"), number("0.0475"), number("60"))

        // Check 'payment'
        checkValues(number("562.707359373292"), Payment().apply(loan, context_))
    }

    @org.junit.jupiter.api.Test
    fun testCase003() {
        val context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build()
        val cache_ = context_.getCache()
        // Initialize input data
        val loan: type.TLoan? = type.TLoanImpl(number("600000"), number("0.0399"), number("360"))

        // Check 'payment'
        checkValues(number("2861.03377700389"), Payment().apply(loan, context_))
    }

    private fun checkValues(expected: Any?, actual: Any?) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual)
    }
}
