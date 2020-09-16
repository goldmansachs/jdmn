
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

        // Check Adjudication with proto request
        val adjudicationBuilder_: proto.AdjudicationRequest.Builder = proto.AdjudicationRequest.newBuilder()
        val applicantDataProto0: proto.TApplicantData = type.TApplicantData.toProto(applicantData)
        if (applicantDataProto0 != null) {
            adjudicationBuilder_.setApplicantData(applicantDataProto0)
        }
        val bureauDataProto0: proto.TBureauData = type.TBureauData.toProto(bureauData)
        if (bureauDataProto0 != null) {
            adjudicationBuilder_.setBureauData(bureauDataProto0)
        }
        val supportingDocumentsProto0: String = (if (supportingDocuments == null) "" else supportingDocuments!!)
        adjudicationBuilder_.setSupportingDocuments(supportingDocumentsProto0)
        val adjudicationRequest_: proto.AdjudicationRequest = adjudicationBuilder_.build()
        checkValues("ACCEPT", Adjudication().apply(adjudicationRequest_, annotationSet_, eventListener_, externalExecutor_, cache_).getAdjudication())
        // Check ApplicationRiskScore with proto request
        val applicationRiskScoreBuilder_: proto.ApplicationRiskScoreRequest.Builder = proto.ApplicationRiskScoreRequest.newBuilder()
        val applicantDataProto1: proto.TApplicantData = type.TApplicantData.toProto(applicantData)
        if (applicantDataProto1 != null) {
            applicationRiskScoreBuilder_.setApplicantData(applicantDataProto1)
        }
        val applicationRiskScoreRequest_: proto.ApplicationRiskScoreRequest = applicationRiskScoreBuilder_.build()
        checkValues(number("130"), ApplicationRiskScore().apply(applicationRiskScoreRequest_, annotationSet_, eventListener_, externalExecutor_, cache_).getApplicationRiskScore())
        // Check PreBureauRiskCategory with proto request
        val preBureauRiskCategoryBuilder_: proto.PreBureauRiskCategoryRequest.Builder = proto.PreBureauRiskCategoryRequest.newBuilder()
        val applicantDataProto2: proto.TApplicantData = type.TApplicantData.toProto(applicantData)
        if (applicantDataProto2 != null) {
            preBureauRiskCategoryBuilder_.setApplicantData(applicantDataProto2)
        }
        val preBureauRiskCategoryRequest_: proto.PreBureauRiskCategoryRequest = preBureauRiskCategoryBuilder_.build()
        checkValues("LOW", PreBureauRiskCategory().apply(preBureauRiskCategoryRequest_, annotationSet_, eventListener_, externalExecutor_, cache_).getPreBureauRiskCategory())
        // Check BureauCallType with proto request
        val bureauCallTypeBuilder_: proto.BureauCallTypeRequest.Builder = proto.BureauCallTypeRequest.newBuilder()
        val applicantDataProto3: proto.TApplicantData = type.TApplicantData.toProto(applicantData)
        if (applicantDataProto3 != null) {
            bureauCallTypeBuilder_.setApplicantData(applicantDataProto3)
        }
        val bureauCallTypeRequest_: proto.BureauCallTypeRequest = bureauCallTypeBuilder_.build()
        checkValues("MINI", BureauCallType().apply(bureauCallTypeRequest_, annotationSet_, eventListener_, externalExecutor_, cache_).getBureauCallType())
        // Check PostBureauRiskCategory with proto request
        val postBureauRiskCategoryBuilder_: proto.PostBureauRiskCategoryRequest.Builder = proto.PostBureauRiskCategoryRequest.newBuilder()
        val applicantDataProto4: proto.TApplicantData = type.TApplicantData.toProto(applicantData)
        if (applicantDataProto4 != null) {
            postBureauRiskCategoryBuilder_.setApplicantData(applicantDataProto4)
        }
        val bureauDataProto4: proto.TBureauData = type.TBureauData.toProto(bureauData)
        if (bureauDataProto4 != null) {
            postBureauRiskCategoryBuilder_.setBureauData(bureauDataProto4)
        }
        val postBureauRiskCategoryRequest_: proto.PostBureauRiskCategoryRequest = postBureauRiskCategoryBuilder_.build()
        checkValues("LOW", PostBureauRiskCategory().apply(postBureauRiskCategoryRequest_, annotationSet_, eventListener_, externalExecutor_, cache_).getPostBureauRiskCategory())
        // Check RequiredMonthlyInstallment with proto request
        val requiredMonthlyInstallmentBuilder_: proto.RequiredMonthlyInstallmentRequest.Builder = proto.RequiredMonthlyInstallmentRequest.newBuilder()
        val requestedProductProto5: proto.TRequestedProduct = type.TRequestedProduct.toProto(requestedProduct)
        if (requestedProductProto5 != null) {
            requiredMonthlyInstallmentBuilder_.setRequestedProduct(requestedProductProto5)
        }
        val requiredMonthlyInstallmentRequest_: proto.RequiredMonthlyInstallmentRequest = requiredMonthlyInstallmentBuilder_.build()
        checkValues(number("1680.880325608555"), RequiredMonthlyInstallment().apply(requiredMonthlyInstallmentRequest_, annotationSet_, eventListener_, externalExecutor_, cache_).getRequiredMonthlyInstallment())
        // Check PreBureauAffordability with proto request
        val preBureauAffordabilityBuilder_: proto.PreBureauAffordabilityRequest.Builder = proto.PreBureauAffordabilityRequest.newBuilder()
        val applicantDataProto6: proto.TApplicantData = type.TApplicantData.toProto(applicantData)
        if (applicantDataProto6 != null) {
            preBureauAffordabilityBuilder_.setApplicantData(applicantDataProto6)
        }
        val requestedProductProto6: proto.TRequestedProduct = type.TRequestedProduct.toProto(requestedProduct)
        if (requestedProductProto6 != null) {
            preBureauAffordabilityBuilder_.setRequestedProduct(requestedProductProto6)
        }
        val preBureauAffordabilityRequest_: proto.PreBureauAffordabilityRequest = preBureauAffordabilityBuilder_.build()
        checkValues(true, PreBureauAffordability().apply(preBureauAffordabilityRequest_, annotationSet_, eventListener_, externalExecutor_, cache_).getPreBureauAffordability())
        // Check Eligibility with proto request
        val eligibilityBuilder_: proto.EligibilityRequest.Builder = proto.EligibilityRequest.newBuilder()
        val applicantDataProto7: proto.TApplicantData = type.TApplicantData.toProto(applicantData)
        if (applicantDataProto7 != null) {
            eligibilityBuilder_.setApplicantData(applicantDataProto7)
        }
        val requestedProductProto7: proto.TRequestedProduct = type.TRequestedProduct.toProto(requestedProduct)
        if (requestedProductProto7 != null) {
            eligibilityBuilder_.setRequestedProduct(requestedProductProto7)
        }
        val eligibilityRequest_: proto.EligibilityRequest = eligibilityBuilder_.build()
        checkValues("ELIGIBLE", Eligibility().apply(eligibilityRequest_, annotationSet_, eventListener_, externalExecutor_, cache_).getEligibility())
        // Check Strategy with proto request
        val strategyBuilder_: proto.StrategyRequest.Builder = proto.StrategyRequest.newBuilder()
        val applicantDataProto8: proto.TApplicantData = type.TApplicantData.toProto(applicantData)
        if (applicantDataProto8 != null) {
            strategyBuilder_.setApplicantData(applicantDataProto8)
        }
        val requestedProductProto8: proto.TRequestedProduct = type.TRequestedProduct.toProto(requestedProduct)
        if (requestedProductProto8 != null) {
            strategyBuilder_.setRequestedProduct(requestedProductProto8)
        }
        val strategyRequest_: proto.StrategyRequest = strategyBuilder_.build()
        checkValues("BUREAU", Strategy().apply(strategyRequest_, annotationSet_, eventListener_, externalExecutor_, cache_).getStrategy())
        // Check PostBureauAffordability with proto request
        val postBureauAffordabilityBuilder_: proto.PostBureauAffordabilityRequest.Builder = proto.PostBureauAffordabilityRequest.newBuilder()
        val applicantDataProto9: proto.TApplicantData = type.TApplicantData.toProto(applicantData)
        if (applicantDataProto9 != null) {
            postBureauAffordabilityBuilder_.setApplicantData(applicantDataProto9)
        }
        val bureauDataProto9: proto.TBureauData = type.TBureauData.toProto(bureauData)
        if (bureauDataProto9 != null) {
            postBureauAffordabilityBuilder_.setBureauData(bureauDataProto9)
        }
        val requestedProductProto9: proto.TRequestedProduct = type.TRequestedProduct.toProto(requestedProduct)
        if (requestedProductProto9 != null) {
            postBureauAffordabilityBuilder_.setRequestedProduct(requestedProductProto9)
        }
        val postBureauAffordabilityRequest_: proto.PostBureauAffordabilityRequest = postBureauAffordabilityBuilder_.build()
        checkValues(true, PostBureauAffordability().apply(postBureauAffordabilityRequest_, annotationSet_, eventListener_, externalExecutor_, cache_).getPostBureauAffordability())
        // Check Routing with proto request
        val routingBuilder_: proto.RoutingRequest.Builder = proto.RoutingRequest.newBuilder()
        val applicantDataProto10: proto.TApplicantData = type.TApplicantData.toProto(applicantData)
        if (applicantDataProto10 != null) {
            routingBuilder_.setApplicantData(applicantDataProto10)
        }
        val bureauDataProto10: proto.TBureauData = type.TBureauData.toProto(bureauData)
        if (bureauDataProto10 != null) {
            routingBuilder_.setBureauData(bureauDataProto10)
        }
        val requestedProductProto10: proto.TRequestedProduct = type.TRequestedProduct.toProto(requestedProduct)
        if (requestedProductProto10 != null) {
            routingBuilder_.setRequestedProduct(requestedProductProto10)
        }
        val routingRequest_: proto.RoutingRequest = routingBuilder_.build()
        checkValues("ACCEPT", Routing().apply(routingRequest_, annotationSet_, eventListener_, externalExecutor_, cache_).getRouting())
    }

    private fun checkValues(expected: Any?, actual: Any?) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual)
    }
}
