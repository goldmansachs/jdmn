
import java.util.*
import java.util.stream.Collectors

@javax.annotation.Generated(value = ["junit.ftl", "0010-multi-output-U.dmn"])
class Test0010MultiOutputU : com.gs.dmn.runtime.DefaultDMNBaseDecision() {
    @org.junit.Test
    fun testCase001() {
        val annotationSet_ = com.gs.dmn.runtime.annotation.AnnotationSet()
        val eventListener_ = com.gs.dmn.runtime.listener.NopEventListener()
        val externalExecutor_ = com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor()
        val cache_ = com.gs.dmn.runtime.cache.DefaultCache()
        // Initialize input data
        val age: java.math.BigDecimal? = number("18")
        val riskCategory: String? = "Medium"
        val isAffordable: Boolean? = true

        // Check Approval
        checkValues(type.TApprovalImpl("Standard", "Approved"), Approval().apply(age, riskCategory, isAffordable, annotationSet_, eventListener_, externalExecutor_, cache_))
    }

    @org.junit.Test
    fun testCase002() {
        val annotationSet_ = com.gs.dmn.runtime.annotation.AnnotationSet()
        val eventListener_ = com.gs.dmn.runtime.listener.NopEventListener()
        val externalExecutor_ = com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor()
        val cache_ = com.gs.dmn.runtime.cache.DefaultCache()
        // Initialize input data
        val age: java.math.BigDecimal? = number("17")
        val riskCategory: String? = "Medium"
        val isAffordable: Boolean? = true

        // Check Approval
        checkValues(type.TApprovalImpl("Standard", "Declined"), Approval().apply(age, riskCategory, isAffordable, annotationSet_, eventListener_, externalExecutor_, cache_))
    }

    @org.junit.Test
    fun testCase003() {
        val annotationSet_ = com.gs.dmn.runtime.annotation.AnnotationSet()
        val eventListener_ = com.gs.dmn.runtime.listener.NopEventListener()
        val externalExecutor_ = com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor()
        val cache_ = com.gs.dmn.runtime.cache.DefaultCache()
        // Initialize input data
        val age: java.math.BigDecimal? = number("18")
        val riskCategory: String? = "High"
        val isAffordable: Boolean? = true

        // Check Approval
        checkValues(type.TApprovalImpl("Standard", "Declined"), Approval().apply(age, riskCategory, isAffordable, annotationSet_, eventListener_, externalExecutor_, cache_))
    }

    private fun checkValues(expected: Any?, actual: Any?) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual)
    }
}
