
import java.util.*
import java.util.stream.Collectors

@javax.annotation.Generated(value = ["junit.ftl", "0008-LX-arithmetic.dmn"])
class Test0008LXArithmetic : com.gs.dmn.runtime.DefaultDMNBaseDecision() {
    @org.junit.Test
    fun testCase001() {
        val annotationSet_ = com.gs.dmn.runtime.annotation.AnnotationSet()
        val eventListener_ = com.gs.dmn.runtime.listener.NopEventListener()
        val externalExecutor_ = com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor()
        val cache_ = com.gs.dmn.runtime.cache.DefaultCache()
        // Initialize input data
        val loan: type.TLoan? = type.TLoanImpl(number("600000"), number("0.0375"), number("360"))

        // Check payment
        checkValues(number("2778.69354943277"), Payment().apply(loan, annotationSet_, eventListener_, externalExecutor_, cache_))
    }

    @org.junit.Test
    fun testCase002() {
        val annotationSet_ = com.gs.dmn.runtime.annotation.AnnotationSet()
        val eventListener_ = com.gs.dmn.runtime.listener.NopEventListener()
        val externalExecutor_ = com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor()
        val cache_ = com.gs.dmn.runtime.cache.DefaultCache()
        // Initialize input data
        val loan: type.TLoan? = type.TLoanImpl(number("30000"), number("0.0475"), number("60"))

        // Check payment
        checkValues(number("562.707359373292"), Payment().apply(loan, annotationSet_, eventListener_, externalExecutor_, cache_))
    }

    @org.junit.Test
    fun testCase003() {
        val annotationSet_ = com.gs.dmn.runtime.annotation.AnnotationSet()
        val eventListener_ = com.gs.dmn.runtime.listener.NopEventListener()
        val externalExecutor_ = com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor()
        val cache_ = com.gs.dmn.runtime.cache.DefaultCache()
        // Initialize input data
        val loan: type.TLoan? = type.TLoanImpl(number("600000"), number("0.0399"), number("360"))

        // Check payment
        checkValues(number("2861.03377700389"), Payment().apply(loan, annotationSet_, eventListener_, externalExecutor_, cache_))
    }

    private fun checkValues(expected: Any?, actual: Any?) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual)
    }
}
