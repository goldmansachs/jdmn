
import java.util.*
import java.util.stream.Collectors

@javax.annotation.Generated(value = ["junit.ftl", "0004-lending.dmn"])
class Test0004Lending : com.gs.dmn.runtime.DefaultDMNBaseDecision() {
    @org.junit.Test
    fun testCase001() {
        val annotationSet_ = com.gs.dmn.runtime.annotation.AnnotationSet()
        val eventListener_ = com.gs.dmn.runtime.listener.NopEventListener()
        val externalExecutor_ = com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor()
        val cache_ = com.gs.dmn.runtime.cache.DefaultCache()
        // Initialize input data
        val applicantData: type.TApplicantData? = type.TApplicantDataImpl(number("35"), "EMPLOYED", true, "M", type.MonthlyImpl(number("2000"), number("6000"), number("0")))
        val requestedProduct: type.TRequestedProduct? = type.TRequestedProductImpl(number("350000"), "STANDARD LOAN", number("0.0395"), number("360"))
        val bureauData: type.TBureauData? = type.TBureauDataImpl(false, number("649"))
        val supportingDocuments: String? = "YES"

        // Check Adjudication
        checkValues("ACCEPT", Adjudication().apply(applicantData, bureauData, supportingDocuments, annotationSet_, eventListener_, externalExecutor_, cache_))
        // Check ApplicationRiskScore
        checkValues(number("130"), ApplicationRiskScore().apply(applicantData, annotationSet_, eventListener_, externalExecutor_, cache_))
        // Check PreBureauRiskCategory
        checkValues("LOW", PreBureauRiskCategory().apply(applicantData, annotationSet_, eventListener_, externalExecutor_, cache_))
        // Check BureauCallType
        checkValues("MINI", BureauCallType().apply(applicantData, annotationSet_, eventListener_, externalExecutor_, cache_))
        // Check PostBureauRiskCategory
        checkValues("LOW", PostBureauRiskCategory().apply(applicantData, bureauData, annotationSet_, eventListener_, externalExecutor_, cache_))
        // Check RequiredMonthlyInstallment
        checkValues(number("1680.880325608555"), RequiredMonthlyInstallment().apply(requestedProduct, annotationSet_, eventListener_, externalExecutor_, cache_))
        // Check PreBureauAffordability
        checkValues(true, PreBureauAffordability().apply(applicantData, requestedProduct, annotationSet_, eventListener_, externalExecutor_, cache_))
        // Check Eligibility
        checkValues("ELIGIBLE", Eligibility().apply(applicantData, requestedProduct, annotationSet_, eventListener_, externalExecutor_, cache_))
        // Check Strategy
        checkValues("BUREAU", Strategy().apply(applicantData, requestedProduct, annotationSet_, eventListener_, externalExecutor_, cache_))
        // Check PostBureauAffordability
        checkValues(true, PostBureauAffordability().apply(applicantData, bureauData, requestedProduct, annotationSet_, eventListener_, externalExecutor_, cache_))
        // Check Routing
        checkValues("ACCEPT", Routing().apply(applicantData, bureauData, requestedProduct, annotationSet_, eventListener_, externalExecutor_, cache_))
    }

    private fun checkValues(expected: Any?, actual: Any?) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual)
    }
}
