
import java.util.*
import java.util.stream.Collectors

@javax.annotation.Generated(value = ["decision.ftl", "Pre-bureauAffordability"])
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "Pre-bureauAffordability",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.INVOCATION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
class PreBureauAffordability(val preBureauRiskCategory : PreBureauRiskCategory = PreBureauRiskCategory(), val requiredMonthlyInstallment : RequiredMonthlyInstallment = RequiredMonthlyInstallment()) : com.gs.dmn.runtime.JavaTimeDMNBaseDecision() {
    override fun applyMap(input_: MutableMap<String, String>, context_: com.gs.dmn.runtime.ExecutionContext): Boolean? {
        try {
            return apply(input_.get("ApplicantData")?.let({ com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(it, object : com.fasterxml.jackson.core.type.TypeReference<type.TApplicantDataImpl>() {}) }), input_.get("RequestedProduct")?.let({ com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(it, object : com.fasterxml.jackson.core.type.TypeReference<type.TRequestedProductImpl>() {}) }), context_)
        } catch (e: Exception) {
            logError("Cannot apply decision 'PreBureauAffordability'", e)
            return null
        }
    }

    fun apply(applicantData: type.TApplicantData?, requestedProduct: type.TRequestedProduct?, context_: com.gs.dmn.runtime.ExecutionContext): Boolean? {
        try {
            // Start decision 'Pre-bureauAffordability'
            var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
            var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
            var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
            var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
            val preBureauAffordabilityStartTime_ = System.currentTimeMillis()
            val preBureauAffordabilityArguments_ = com.gs.dmn.runtime.listener.Arguments()
            preBureauAffordabilityArguments_.put("ApplicantData", applicantData)
            preBureauAffordabilityArguments_.put("RequestedProduct", requestedProduct)
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, preBureauAffordabilityArguments_)

            // Evaluate decision 'Pre-bureauAffordability'
            val output_: Boolean? = evaluate(applicantData, requestedProduct, context_)

            // End decision 'Pre-bureauAffordability'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, preBureauAffordabilityArguments_, output_, (System.currentTimeMillis() - preBureauAffordabilityStartTime_))

            return output_
        } catch (e: Exception) {
            logError("Exception caught in 'Pre-bureauAffordability' evaluation", e)
            return null
        }
    }

    fun applyProto(preBureauAffordabilityRequest_: proto.PreBureauAffordabilityRequest, context_: com.gs.dmn.runtime.ExecutionContext): proto.PreBureauAffordabilityResponse {
        // Create arguments from Request Message
        val applicantData: type.TApplicantData? = type.TApplicantData.toTApplicantData(preBureauAffordabilityRequest_.getApplicantData())
        val requestedProduct: type.TRequestedProduct? = type.TRequestedProduct.toTRequestedProduct(preBureauAffordabilityRequest_.getRequestedProduct())

        // Invoke apply method
        val output_: Boolean? = apply(applicantData, requestedProduct, context_)

        // Convert output to Response Message
        val builder_: proto.PreBureauAffordabilityResponse.Builder = proto.PreBureauAffordabilityResponse.newBuilder()
        val outputProto_ = (if (output_ == null) false else output_!!)
        builder_.setPreBureauAffordability(outputProto_)
        return builder_.build()
    }

    private inline fun evaluate(applicantData: type.TApplicantData?, requestedProduct: type.TRequestedProduct?, context_: com.gs.dmn.runtime.ExecutionContext): Boolean? {
        var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
        var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
        var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
        var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
        // Apply child decisions
        val preBureauRiskCategory: String? = this@PreBureauAffordability.preBureauRiskCategory.apply(applicantData, context_)
        val requiredMonthlyInstallment: java.math.BigDecimal? = this@PreBureauAffordability.requiredMonthlyInstallment.apply(requestedProduct, context_)

        return AffordabilityCalculation.instance().apply(applicantData?.let({ it.monthly as type.Monthly? })?.let({ it.income as java.math.BigDecimal? }), applicantData?.let({ it.monthly as type.Monthly? })?.let({ it.repayments as java.math.BigDecimal? }), applicantData?.let({ it.monthly as type.Monthly? })?.let({ it.expenses as java.math.BigDecimal? }), preBureauRiskCategory, requiredMonthlyInstallment, context_) as Boolean?
    }

    companion object {
        val DRG_ELEMENT_METADATA : com.gs.dmn.runtime.listener.DRGElement = com.gs.dmn.runtime.listener.DRGElement(
            "",
            "Pre-bureauAffordability",
            "",
            com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
            com.gs.dmn.runtime.annotation.ExpressionKind.INVOCATION,
            com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
            -1
        )

        @JvmStatic
        fun requestToMap(preBureauAffordabilityRequest_: proto.PreBureauAffordabilityRequest): kotlin.collections.Map<String, Any?> {
            // Create arguments from Request Message
            val applicantData: type.TApplicantData? = type.TApplicantData.toTApplicantData(preBureauAffordabilityRequest_.getApplicantData())
            val requestedProduct: type.TRequestedProduct? = type.TRequestedProduct.toTRequestedProduct(preBureauAffordabilityRequest_.getRequestedProduct())

            // Create map
            val map_: kotlin.collections.MutableMap<String, Any?> = mutableMapOf()
            map_.put("ApplicantData", applicantData)
            map_.put("RequestedProduct", requestedProduct)
            return map_
        }

        @JvmStatic
        fun responseToOutput(preBureauAffordabilityResponse_: proto.PreBureauAffordabilityResponse): Boolean? {
            // Extract and convert output
            return preBureauAffordabilityResponse_.getPreBureauAffordability()
        }
    }
}
