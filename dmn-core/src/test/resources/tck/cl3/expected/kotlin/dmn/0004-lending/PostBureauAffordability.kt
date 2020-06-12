
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
            apply(applicantData?.let({ com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(it, type.TApplicantDataImpl::class.java) }), bureauData?.let({ com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(it, type.TBureauDataImpl::class.java) }), requestedProduct?.let({ com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(it, type.TRequestedProductImpl::class.java) }), annotationSet_, com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor())
        } catch (e: Exception) {
            logError("Cannot apply decision 'PostBureauAffordability'", e)
            null
        }
    }

    fun apply(applicantData: String?, bureauData: String?, requestedProduct: String?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor): Boolean? {
        return try {
            apply(applicantData?.let({ com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(it, type.TApplicantDataImpl::class.java) }), bureauData?.let({ com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(it, type.TBureauDataImpl::class.java) }), requestedProduct?.let({ com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(it, type.TRequestedProductImpl::class.java) }), annotationSet_, eventListener_, externalExecutor_)
        } catch (e: Exception) {
            logError("Cannot apply decision 'PostBureauAffordability'", e)
            null
        }
    }

    fun apply(applicantData: type.TApplicantData?, bureauData: type.TBureauData?, requestedProduct: type.TRequestedProduct?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet): Boolean? {
        return apply(applicantData, bureauData, requestedProduct, annotationSet_, com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor())
    }

    fun apply(applicantData: type.TApplicantData?, bureauData: type.TBureauData?, requestedProduct: type.TRequestedProduct?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor): Boolean? {
        try {
            // Start decision 'PostBureauAffordability'
            val postBureauAffordabilityStartTime_ = System.currentTimeMillis()
            val postBureauAffordabilityArguments_ = com.gs.dmn.runtime.listener.Arguments()
            postBureauAffordabilityArguments_.put("applicantData", applicantData)
            postBureauAffordabilityArguments_.put("bureauData", bureauData)
            postBureauAffordabilityArguments_.put("requestedProduct", requestedProduct)
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, postBureauAffordabilityArguments_)

            // Apply child decisions
            val postBureauRiskCategory: String? = this.postBureauRiskCategory.apply(applicantData, bureauData, annotationSet_, eventListener_, externalExecutor_)
            val requiredMonthlyInstallment: java.math.BigDecimal? = this.requiredMonthlyInstallment.apply(requestedProduct, annotationSet_, eventListener_, externalExecutor_)

            // Evaluate decision 'PostBureauAffordability'
            val output_: Boolean? = evaluate(applicantData, postBureauRiskCategory, requiredMonthlyInstallment, annotationSet_, eventListener_, externalExecutor_)

            // End decision 'PostBureauAffordability'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, postBureauAffordabilityArguments_, output_, (System.currentTimeMillis() - postBureauAffordabilityStartTime_))

            return output_
        } catch (e: Exception) {
            logError("Exception caught in 'PostBureauAffordability' evaluation", e)
            return null
        }
    }

    private inline fun evaluate(applicantData: type.TApplicantData?, postBureauRiskCategory: String?, requiredMonthlyInstallment: java.math.BigDecimal?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor): Boolean? {
        return AffordabilityCalculation.AffordabilityCalculation(applicantData?.let({ it.monthly as type.Monthly? })?.let({ it.income as java.math.BigDecimal? }), applicantData?.let({ it.monthly as type.Monthly? })?.let({ it.repayments as java.math.BigDecimal? }), applicantData?.let({ it.monthly as type.Monthly? })?.let({ it.expenses as java.math.BigDecimal? }), postBureauRiskCategory, requiredMonthlyInstallment, annotationSet_, eventListener_, externalExecutor_) as Boolean?
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
