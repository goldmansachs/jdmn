
import java.util.*
import java.util.stream.Collectors

@javax.annotation.Generated(value = ["decision.ftl", "PostBureauAffordability"])
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "PostBureauAffordability",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.INVOCATION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
class PostBureauAffordability(val postBureauRiskCategory : PostBureauRiskCategory = PostBureauRiskCategory(), val requiredMonthlyInstallment : RequiredMonthlyInstallment = RequiredMonthlyInstallment()) : com.gs.dmn.runtime.DefaultDMNBaseDecision() {
    fun apply(applicantData: String?, bureauData: String?, requestedProduct: String?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet): Boolean? {
        return try {
            apply(applicantData?.let({ com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(it, object : com.fasterxml.jackson.core.type.TypeReference<type.TApplicantDataImpl>() {}) }), bureauData?.let({ com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(it, object : com.fasterxml.jackson.core.type.TypeReference<type.TBureauDataImpl>() {}) }), requestedProduct?.let({ com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(it, object : com.fasterxml.jackson.core.type.TypeReference<type.TRequestedProductImpl>() {}) }), annotationSet_, com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), com.gs.dmn.runtime.cache.DefaultCache())
        } catch (e: Exception) {
            logError("Cannot apply decision 'PostBureauAffordability'", e)
            null
        }
    }

    fun apply(applicantData: String?, bureauData: String?, requestedProduct: String?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): Boolean? {
        return try {
            apply(applicantData?.let({ com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(it, object : com.fasterxml.jackson.core.type.TypeReference<type.TApplicantDataImpl>() {}) }), bureauData?.let({ com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(it, object : com.fasterxml.jackson.core.type.TypeReference<type.TBureauDataImpl>() {}) }), requestedProduct?.let({ com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(it, object : com.fasterxml.jackson.core.type.TypeReference<type.TRequestedProductImpl>() {}) }), annotationSet_, eventListener_, externalExecutor_, cache_)
        } catch (e: Exception) {
            logError("Cannot apply decision 'PostBureauAffordability'", e)
            null
        }
    }

    fun apply(applicantData: type.TApplicantData?, bureauData: type.TBureauData?, requestedProduct: type.TRequestedProduct?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet): Boolean? {
        return apply(applicantData, bureauData, requestedProduct, annotationSet_, com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), com.gs.dmn.runtime.cache.DefaultCache())
    }

    fun apply(applicantData: type.TApplicantData?, bureauData: type.TBureauData?, requestedProduct: type.TRequestedProduct?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): Boolean? {
        try {
            // Start decision 'PostBureauAffordability'
            val postBureauAffordabilityStartTime_ = System.currentTimeMillis()
            val postBureauAffordabilityArguments_ = com.gs.dmn.runtime.listener.Arguments()
            postBureauAffordabilityArguments_.put("ApplicantData", applicantData)
            postBureauAffordabilityArguments_.put("BureauData", bureauData)
            postBureauAffordabilityArguments_.put("RequestedProduct", requestedProduct)
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, postBureauAffordabilityArguments_)

            // Apply child decisions
            val postBureauRiskCategory: String? = this.postBureauRiskCategory.apply(applicantData, bureauData, annotationSet_, eventListener_, externalExecutor_, cache_)
            val requiredMonthlyInstallment: java.math.BigDecimal? = this.requiredMonthlyInstallment.apply(requestedProduct, annotationSet_, eventListener_, externalExecutor_, cache_)

            // Evaluate decision 'PostBureauAffordability'
            val output_: Boolean? = evaluate(applicantData, postBureauRiskCategory, requiredMonthlyInstallment, annotationSet_, eventListener_, externalExecutor_, cache_)

            // End decision 'PostBureauAffordability'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, postBureauAffordabilityArguments_, output_, (System.currentTimeMillis() - postBureauAffordabilityStartTime_))

            return output_
        } catch (e: Exception) {
            logError("Exception caught in 'PostBureauAffordability' evaluation", e)
            return null
        }
    }

    private inline fun evaluate(applicantData: type.TApplicantData?, postBureauRiskCategory: String?, requiredMonthlyInstallment: java.math.BigDecimal?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): Boolean? {
        return AffordabilityCalculation.AffordabilityCalculation(applicantData?.let({ it.monthly as type.Monthly? })?.let({ it.income as java.math.BigDecimal? }), applicantData?.let({ it.monthly as type.Monthly? })?.let({ it.repayments as java.math.BigDecimal? }), applicantData?.let({ it.monthly as type.Monthly? })?.let({ it.expenses as java.math.BigDecimal? }), postBureauRiskCategory, requiredMonthlyInstallment, annotationSet_, eventListener_, externalExecutor_, cache_) as Boolean?
    }

    companion object {
        val DRG_ELEMENT_METADATA : com.gs.dmn.runtime.listener.DRGElement = com.gs.dmn.runtime.listener.DRGElement(
            "",
            "PostBureauAffordability",
            "",
            com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
            com.gs.dmn.runtime.annotation.ExpressionKind.INVOCATION,
            com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
            -1
        )
    }
}
