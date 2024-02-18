
import java.util.*
import java.util.stream.Collectors

@jakarta.annotation.Generated(value = ["decision.ftl", "Post-bureauRiskCategory"])
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "Post-bureauRiskCategory",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.INVOCATION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
class PostBureauRiskCategory(val applicationRiskScore : ApplicationRiskScore = ApplicationRiskScore()) : com.gs.dmn.runtime.DefaultDMNBaseDecision() {
    override fun applyMap(input_: MutableMap<String, String>, context_: com.gs.dmn.runtime.ExecutionContext): String? {
        try {
            return apply(input_.get("ApplicantData")?.let({ com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(it, object : com.fasterxml.jackson.core.type.TypeReference<type.TApplicantDataImpl>() {}) }), input_.get("BureauData")?.let({ com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(it, object : com.fasterxml.jackson.core.type.TypeReference<type.TBureauDataImpl>() {}) }), context_)
        } catch (e: Exception) {
            logError("Cannot apply decision 'PostBureauRiskCategory'", e)
            return null
        }
    }

    fun apply(applicantData: type.TApplicantData?, bureauData: type.TBureauData?, context_: com.gs.dmn.runtime.ExecutionContext): String? {
        try {
            // Start decision 'Post-bureauRiskCategory'
            var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
            var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
            var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
            var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
            val postBureauRiskCategoryStartTime_ = System.currentTimeMillis()
            val postBureauRiskCategoryArguments_ = com.gs.dmn.runtime.listener.Arguments()
            postBureauRiskCategoryArguments_.put("ApplicantData", applicantData)
            postBureauRiskCategoryArguments_.put("BureauData", bureauData)
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, postBureauRiskCategoryArguments_)

            // Evaluate decision 'Post-bureauRiskCategory'
            val output_: String? = evaluate(applicantData, bureauData, context_)

            // End decision 'Post-bureauRiskCategory'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, postBureauRiskCategoryArguments_, output_, (System.currentTimeMillis() - postBureauRiskCategoryStartTime_))

            return output_
        } catch (e: Exception) {
            logError("Exception caught in 'Post-bureauRiskCategory' evaluation", e)
            return null
        }
    }

    private inline fun evaluate(applicantData: type.TApplicantData?, bureauData: type.TBureauData?, context_: com.gs.dmn.runtime.ExecutionContext): String? {
        var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
        var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
        var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
        var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
        // Apply child decisions
        val applicationRiskScore: java.math.BigDecimal? = this@PostBureauRiskCategory.applicationRiskScore.apply(applicantData, context_)

        return PostBureauRiskCategoryTable.instance().apply(applicantData?.let({ it.existingCustomer as Boolean? }), applicationRiskScore, bureauData?.let({ it.creditScore as java.math.BigDecimal? }), context_) as String?
    }

    companion object {
        val DRG_ELEMENT_METADATA : com.gs.dmn.runtime.listener.DRGElement = com.gs.dmn.runtime.listener.DRGElement(
            "",
            "Post-bureauRiskCategory",
            "",
            com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
            com.gs.dmn.runtime.annotation.ExpressionKind.INVOCATION,
            com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
            -1
        )
    }
}
