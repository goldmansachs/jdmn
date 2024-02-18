
import java.util.*
import java.util.stream.Collectors

@jakarta.annotation.Generated(value = ["junit.ftl", "0005-simpletable-A.dmn"])
class _0005SimpletableATest : com.gs.dmn.runtime.DefaultDMNBaseDecision() {
    @org.junit.jupiter.api.Test
    fun testCase001() {
        val context_ = com.gs.dmn.runtime.ExecutionContext()
        val cache_ = context_.getCache()
        // Initialize input data
        val age: java.math.BigDecimal? = number("18")
        val riskCategory: String? = "Medium"
        val isAffordable: Boolean? = true

        // Check 'Approval Status'
        checkValues("Approved", ApprovalStatus().apply(age, riskCategory, isAffordable, context_))
    }

    @org.junit.jupiter.api.Test
    fun testCase002() {
        val context_ = com.gs.dmn.runtime.ExecutionContext()
        val cache_ = context_.getCache()
        // Initialize input data
        val age: java.math.BigDecimal? = number("17")
        val riskCategory: String? = "Medium"
        val isAffordable: Boolean? = true

        // Check 'Approval Status'
        checkValues("Declined", ApprovalStatus().apply(age, riskCategory, isAffordable, context_))
    }

    @org.junit.jupiter.api.Test
    fun testCase003() {
        val context_ = com.gs.dmn.runtime.ExecutionContext()
        val cache_ = context_.getCache()
        // Initialize input data
        val age: java.math.BigDecimal? = number("18")
        val riskCategory: String? = "High"
        val isAffordable: Boolean? = true

        // Check 'Approval Status'
        checkValues("Declined", ApprovalStatus().apply(age, riskCategory, isAffordable, context_))
    }

    private fun checkValues(expected: Any?, actual: Any?) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual)
    }
}
