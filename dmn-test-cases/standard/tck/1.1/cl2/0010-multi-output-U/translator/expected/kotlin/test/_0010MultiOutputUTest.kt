
import java.util.*
import java.util.stream.Collectors

@javax.annotation.Generated(value = ["junit.ftl", "0010-multi-output-U.dmn"])
class _0010MultiOutputUTest : com.gs.dmn.runtime.DefaultDMNBaseDecision() {
    @org.junit.Test
    fun testCase001() {
        val context_ = com.gs.dmn.runtime.ExecutionContext()
        val cache_ = context_.getCache()
        // Initialize input data
        val age: java.math.BigDecimal? = number("18")
        val riskCategory: String? = "Medium"
        val isAffordable: Boolean? = true

        // Check 'Approval'
        checkValues(type.TApproval.toTApproval(com.gs.dmn.runtime.Context().add("Rate", "Standard").add("Status", "Approved")), Approval().apply(age, riskCategory, isAffordable, context_))
    }

    @org.junit.Test
    fun testCase002() {
        val context_ = com.gs.dmn.runtime.ExecutionContext()
        val cache_ = context_.getCache()
        // Initialize input data
        val age: java.math.BigDecimal? = number("17")
        val riskCategory: String? = "Medium"
        val isAffordable: Boolean? = true

        // Check 'Approval'
        checkValues(type.TApproval.toTApproval(com.gs.dmn.runtime.Context().add("Rate", "Standard").add("Status", "Declined")), Approval().apply(age, riskCategory, isAffordable, context_))
    }

    @org.junit.Test
    fun testCase003() {
        val context_ = com.gs.dmn.runtime.ExecutionContext()
        val cache_ = context_.getCache()
        // Initialize input data
        val age: java.math.BigDecimal? = number("18")
        val riskCategory: String? = "High"
        val isAffordable: Boolean? = true

        // Check 'Approval'
        checkValues(type.TApproval.toTApproval(com.gs.dmn.runtime.Context().add("Rate", "Standard").add("Status", "Declined")), Approval().apply(age, riskCategory, isAffordable, context_))
    }

    private fun checkValues(expected: Any?, actual: Any?) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual)
    }
}
