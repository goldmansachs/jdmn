
import java.util.*
import java.util.stream.Collectors

@javax.annotation.Generated(value = ["decision.ftl", "Eligibility"])
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "Eligibility",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.INVOCATION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
class Eligibility(val preBureauAffordability : PreBureauAffordability = PreBureauAffordability(), val preBureauRiskCategory : PreBureauRiskCategory = PreBureauRiskCategory()) : com.gs.dmn.runtime.JavaTimeDMNBaseDecision() {
    override fun applyMap(input_: MutableMap<String, String>, context_: com.gs.dmn.runtime.ExecutionContext): String? {
        try {
            return apply(input_.get("ApplicantData")?.let({ com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(it, object : com.fasterxml.jackson.core.type.TypeReference<type.TApplicantDataImpl>() {}) }), input_.get("RequestedProduct")?.let({ com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(it, object : com.fasterxml.jackson.core.type.TypeReference<type.TRequestedProductImpl>() {}) }), context_)
        } catch (e: Exception) {
            logError("Cannot apply decision 'Eligibility'", e)
            return null
        }
    }

    fun apply(applicantData: type.TApplicantData?, requestedProduct: type.TRequestedProduct?, context_: com.gs.dmn.runtime.ExecutionContext): String? {
        try {
            // Start decision 'Eligibility'
            var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
            var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
            var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
            var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
            val eligibilityStartTime_ = System.currentTimeMillis()
            val eligibilityArguments_ = com.gs.dmn.runtime.listener.Arguments()
            eligibilityArguments_.put("ApplicantData", applicantData)
            eligibilityArguments_.put("RequestedProduct", requestedProduct)
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, eligibilityArguments_)

            // Evaluate decision 'Eligibility'
            val output_: String? = evaluate(applicantData, requestedProduct, context_)

            // End decision 'Eligibility'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, eligibilityArguments_, output_, (System.currentTimeMillis() - eligibilityStartTime_))

            return output_
        } catch (e: Exception) {
            logError("Exception caught in 'Eligibility' evaluation", e)
            return null
        }
    }

    fun applyProto(eligibilityRequest_: proto.EligibilityRequest, context_: com.gs.dmn.runtime.ExecutionContext): proto.EligibilityResponse {
        // Create arguments from Request Message
        val applicantData: type.TApplicantData? = type.TApplicantData.toTApplicantData(eligibilityRequest_.getApplicantData())
        val requestedProduct: type.TRequestedProduct? = type.TRequestedProduct.toTRequestedProduct(eligibilityRequest_.getRequestedProduct())

        // Invoke apply method
        val output_: String? = apply(applicantData, requestedProduct, context_)

        // Convert output to Response Message
        val builder_: proto.EligibilityResponse.Builder = proto.EligibilityResponse.newBuilder()
        val outputProto_ = (if (output_ == null) "" else output_!!)
        builder_.setEligibility(outputProto_)
        return builder_.build()
    }

    private inline fun evaluate(applicantData: type.TApplicantData?, requestedProduct: type.TRequestedProduct?, context_: com.gs.dmn.runtime.ExecutionContext): String? {
        var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
        var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
        var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
        var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
        // Apply child decisions
        val preBureauAffordability: Boolean? = this@Eligibility.preBureauAffordability.apply(applicantData, requestedProduct, context_)
        val preBureauRiskCategory: String? = this@Eligibility.preBureauRiskCategory.apply(applicantData, context_)

        return EligibilityRules.instance().apply(preBureauRiskCategory, preBureauAffordability, applicantData?.let({ it.age as kotlin.Number? }), context_) as String?
    }

    companion object {
        val DRG_ELEMENT_METADATA : com.gs.dmn.runtime.listener.DRGElement = com.gs.dmn.runtime.listener.DRGElement(
            "",
            "Eligibility",
            "",
            com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
            com.gs.dmn.runtime.annotation.ExpressionKind.INVOCATION,
            com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
            -1
        )

        @JvmStatic
        fun requestToMap(eligibilityRequest_: proto.EligibilityRequest): kotlin.collections.Map<String, Any?> {
            // Create arguments from Request Message
            val applicantData: type.TApplicantData? = type.TApplicantData.toTApplicantData(eligibilityRequest_.getApplicantData())
            val requestedProduct: type.TRequestedProduct? = type.TRequestedProduct.toTRequestedProduct(eligibilityRequest_.getRequestedProduct())

            // Create map
            val map_: kotlin.collections.MutableMap<String, Any?> = mutableMapOf()
            map_.put("ApplicantData", applicantData)
            map_.put("RequestedProduct", requestedProduct)
            return map_
        }

        @JvmStatic
        fun responseToOutput(eligibilityResponse_: proto.EligibilityResponse): String? {
            // Extract and convert output
            return eligibilityResponse_.getEligibility()
        }
    }
}
