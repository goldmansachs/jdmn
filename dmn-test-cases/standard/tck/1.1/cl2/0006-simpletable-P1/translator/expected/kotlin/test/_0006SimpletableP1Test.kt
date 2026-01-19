
import java.util.*
import java.util.stream.Collectors

@javax.annotation.Generated(value = ["junit.ftl", "0006-simpletable-P1.dmn"])
class _0006SimpletableP1Test : com.gs.dmn.runtime.JavaTimeDMNBaseDecision<Object>() {
    @org.junit.jupiter.api.Test
    fun testCase001_1() {
        val context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build()
        val cache_ = context_.getCache()
        // Initialize arguments
        val age: kotlin.Number? = number("18")
        val riskCategory: String? = "Medium"
        val isAffordable: Boolean? = true

        // Check 'Approval Status'
        checkValues("Approved", ApprovalStatus().apply(age, riskCategory, isAffordable, context_))
    }

    @org.junit.jupiter.api.Test
    fun testCase002_1() {
        val context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build()
        val cache_ = context_.getCache()
        // Initialize arguments
        val age: kotlin.Number? = number("17")
        val riskCategory: String? = "Medium"
        val isAffordable: Boolean? = true

        // Check 'Approval Status'
        checkValues("Declined", ApprovalStatus().apply(age, riskCategory, isAffordable, context_))
    }

    @org.junit.jupiter.api.Test
    fun testCase003_1() {
        val context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build()
        val cache_ = context_.getCache()
        // Initialize arguments
        val age: kotlin.Number? = number("18")
        val riskCategory: String? = "High"
        val isAffordable: Boolean? = true

        // Check 'Approval Status'
        checkValues("Declined", ApprovalStatus().apply(age, riskCategory, isAffordable, context_))
    }

    private fun checkValues(expected: Any?, actual: Any?) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual)
    }
}
