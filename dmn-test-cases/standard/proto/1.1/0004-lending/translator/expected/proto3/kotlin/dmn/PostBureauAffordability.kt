
import java.util.*
import java.util.stream.Collectors

@jakarta.annotation.Generated(value = ["decision.ftl", "Post-bureauAffordability"])
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "Post-bureauAffordability",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.INVOCATION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
class PostBureauAffordability(val postBureauRiskCategory : PostBureauRiskCategory = PostBureauRiskCategory(), val requiredMonthlyInstallment : RequiredMonthlyInstallment = RequiredMonthlyInstallment()) : com.gs.dmn.runtime.DefaultDMNBaseDecision() {
    override fun applyMap(input_: MutableMap<String, String>, context_: com.gs.dmn.runtime.ExecutionContext): Boolean? {
        try {
            return apply(input_.get("ApplicantData")?.let({ com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(it, object : com.fasterxml.jackson.core.type.TypeReference<type.TApplicantDataImpl>() {}) }), input_.get("BureauData")?.let({ com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(it, object : com.fasterxml.jackson.core.type.TypeReference<type.TBureauDataImpl>() {}) }), input_.get("RequestedProduct")?.let({ com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(it, object : com.fasterxml.jackson.core.type.TypeReference<type.TRequestedProductImpl>() {}) }), context_)
        } catch (e: Exception) {
            logError("Cannot apply decision 'PostBureauAffordability'", e)
            return null
        }
    }

    fun apply(applicantData: type.TApplicantData?, bureauData: type.TBureauData?, requestedProduct: type.TRequestedProduct?, context_: com.gs.dmn.runtime.ExecutionContext): Boolean? {
        try {
            // Start decision 'Post-bureauAffordability'
            var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
            var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
            var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
            var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
            val postBureauAffordabilityStartTime_ = System.currentTimeMillis()
            val postBureauAffordabilityArguments_ = com.gs.dmn.runtime.listener.Arguments()
            postBureauAffordabilityArguments_.put("ApplicantData", applicantData)
            postBureauAffordabilityArguments_.put("BureauData", bureauData)
            postBureauAffordabilityArguments_.put("RequestedProduct", requestedProduct)
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, postBureauAffordabilityArguments_)

            // Evaluate decision 'Post-bureauAffordability'
            val output_: Boolean? = evaluate(applicantData, bureauData, requestedProduct, context_)

            // End decision 'Post-bureauAffordability'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, postBureauAffordabilityArguments_, output_, (System.currentTimeMillis() - postBureauAffordabilityStartTime_))

            return output_
        } catch (e: Exception) {
            logError("Exception caught in 'Post-bureauAffordability' evaluation", e)
            return null
        }
    }

    fun applyProto(postBureauAffordabilityRequest_: proto.PostBureauAffordabilityRequest, context_: com.gs.dmn.runtime.ExecutionContext): proto.PostBureauAffordabilityResponse {
        // Create arguments from Request Message
        val applicantData: type.TApplicantData? = type.TApplicantData.toTApplicantData(postBureauAffordabilityRequest_.getApplicantData())
        val bureauData: type.TBureauData? = type.TBureauData.toTBureauData(postBureauAffordabilityRequest_.getBureauData())
        val requestedProduct: type.TRequestedProduct? = type.TRequestedProduct.toTRequestedProduct(postBureauAffordabilityRequest_.getRequestedProduct())

        // Invoke apply method
        val output_: Boolean? = apply(applicantData, bureauData, requestedProduct, context_)

        // Convert output to Response Message
        val builder_: proto.PostBureauAffordabilityResponse.Builder = proto.PostBureauAffordabilityResponse.newBuilder()
        val outputProto_ = (if (output_ == null) false else output_!!)
        builder_.setPostBureauAffordability(outputProto_)
        return builder_.build()
    }

    private inline fun evaluate(applicantData: type.TApplicantData?, bureauData: type.TBureauData?, requestedProduct: type.TRequestedProduct?, context_: com.gs.dmn.runtime.ExecutionContext): Boolean? {
        var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
        var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
        var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
        var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
        // Apply child decisions
        val postBureauRiskCategory: String? = this@PostBureauAffordability.postBureauRiskCategory.apply(applicantData, bureauData, context_)
        val requiredMonthlyInstallment: java.math.BigDecimal? = this@PostBureauAffordability.requiredMonthlyInstallment.apply(requestedProduct, context_)

        return AffordabilityCalculation.instance().apply(applicantData?.let({ it.monthly as type.Monthly? })?.let({ it.income as java.math.BigDecimal? }), applicantData?.let({ it.monthly as type.Monthly? })?.let({ it.repayments as java.math.BigDecimal? }), applicantData?.let({ it.monthly as type.Monthly? })?.let({ it.expenses as java.math.BigDecimal? }), postBureauRiskCategory, requiredMonthlyInstallment, context_) as Boolean?
    }

    companion object {
        val DRG_ELEMENT_METADATA : com.gs.dmn.runtime.listener.DRGElement = com.gs.dmn.runtime.listener.DRGElement(
            "",
            "Post-bureauAffordability",
            "",
            com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
            com.gs.dmn.runtime.annotation.ExpressionKind.INVOCATION,
            com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
            -1
        )

        @JvmStatic
        fun requestToMap(postBureauAffordabilityRequest_: proto.PostBureauAffordabilityRequest): kotlin.collections.Map<String, Any?> {
            // Create arguments from Request Message
            val applicantData: type.TApplicantData? = type.TApplicantData.toTApplicantData(postBureauAffordabilityRequest_.getApplicantData())
            val bureauData: type.TBureauData? = type.TBureauData.toTBureauData(postBureauAffordabilityRequest_.getBureauData())
            val requestedProduct: type.TRequestedProduct? = type.TRequestedProduct.toTRequestedProduct(postBureauAffordabilityRequest_.getRequestedProduct())

            // Create map
            val map_: kotlin.collections.MutableMap<String, Any?> = mutableMapOf()
            map_.put("ApplicantData", applicantData)
            map_.put("BureauData", bureauData)
            map_.put("RequestedProduct", requestedProduct)
            return map_
        }

        @JvmStatic
        fun responseToOutput(postBureauAffordabilityResponse_: proto.PostBureauAffordabilityResponse): Boolean? {
            // Extract and convert output
            return postBureauAffordabilityResponse_.getPostBureauAffordability()
        }
    }
}
