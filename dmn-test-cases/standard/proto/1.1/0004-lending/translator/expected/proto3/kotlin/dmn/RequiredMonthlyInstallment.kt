
import java.util.*
import java.util.stream.Collectors

@javax.annotation.Generated(value = ["decision.ftl", "RequiredMonthlyInstallment"])
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "RequiredMonthlyInstallment",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.INVOCATION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
class RequiredMonthlyInstallment() : com.gs.dmn.runtime.JavaTimeDMNBaseDecision() {
    override fun applyMap(input_: MutableMap<String, String>, context_: com.gs.dmn.runtime.ExecutionContext): kotlin.Number? {
        try {
            return apply(input_.get("RequestedProduct")?.let({ com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(it, object : com.fasterxml.jackson.core.type.TypeReference<type.TRequestedProductImpl>() {}) }), context_)
        } catch (e: Exception) {
            logError("Cannot apply decision 'RequiredMonthlyInstallment'", e)
            return null
        }
    }

    fun apply(requestedProduct: type.TRequestedProduct?, context_: com.gs.dmn.runtime.ExecutionContext): kotlin.Number? {
        try {
            // Start decision 'RequiredMonthlyInstallment'
            var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
            var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
            var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
            var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
            val requiredMonthlyInstallmentStartTime_ = System.currentTimeMillis()
            val requiredMonthlyInstallmentArguments_ = com.gs.dmn.runtime.listener.Arguments()
            requiredMonthlyInstallmentArguments_.put("RequestedProduct", requestedProduct)
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, requiredMonthlyInstallmentArguments_)

            if (cache_.contains("RequiredMonthlyInstallment")) {
                // Retrieve value from cache
                var output_:kotlin.Number? = cache_.lookup("RequiredMonthlyInstallment") as kotlin.Number?

                // End decision 'RequiredMonthlyInstallment'
                eventListener_.endDRGElement(DRG_ELEMENT_METADATA, requiredMonthlyInstallmentArguments_, output_, (System.currentTimeMillis() - requiredMonthlyInstallmentStartTime_))

                return output_
            } else {
                // Evaluate decision 'RequiredMonthlyInstallment'
                val output_: kotlin.Number? = evaluate(requestedProduct, context_)
                cache_.bind("RequiredMonthlyInstallment", output_)

                // End decision 'RequiredMonthlyInstallment'
                eventListener_.endDRGElement(DRG_ELEMENT_METADATA, requiredMonthlyInstallmentArguments_, output_, (System.currentTimeMillis() - requiredMonthlyInstallmentStartTime_))

                return output_
            }
        } catch (e: Exception) {
            logError("Exception caught in 'RequiredMonthlyInstallment' evaluation", e)
            return null
        }
    }

    fun applyProto(requiredMonthlyInstallmentRequest_: proto.RequiredMonthlyInstallmentRequest, context_: com.gs.dmn.runtime.ExecutionContext): proto.RequiredMonthlyInstallmentResponse {
        // Create arguments from Request Message
        val requestedProduct: type.TRequestedProduct? = type.TRequestedProduct.toTRequestedProduct(requiredMonthlyInstallmentRequest_.getRequestedProduct())

        // Invoke apply method
        val output_: kotlin.Number? = apply(requestedProduct, context_)

        // Convert output to Response Message
        val builder_: proto.RequiredMonthlyInstallmentResponse.Builder = proto.RequiredMonthlyInstallmentResponse.newBuilder()
        val outputProto_ = (if (output_ == null) 0.0 else output_!!.toDouble())
        builder_.setRequiredMonthlyInstallment(outputProto_)
        return builder_.build()
    }

    private inline fun evaluate(requestedProduct: type.TRequestedProduct?, context_: com.gs.dmn.runtime.ExecutionContext): kotlin.Number? {
        var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
        var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
        var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
        var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
        return InstallmentCalculation.instance().apply(requestedProduct?.let({ it.productType as String? }), requestedProduct?.let({ it.rate as kotlin.Number? }), requestedProduct?.let({ it.term as kotlin.Number? }), requestedProduct?.let({ it.amount as kotlin.Number? }), context_) as kotlin.Number?
    }

    companion object {
        val DRG_ELEMENT_METADATA : com.gs.dmn.runtime.listener.DRGElement = com.gs.dmn.runtime.listener.DRGElement(
            "",
            "RequiredMonthlyInstallment",
            "",
            com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
            com.gs.dmn.runtime.annotation.ExpressionKind.INVOCATION,
            com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
            -1
        )

        @JvmStatic
        fun requestToMap(requiredMonthlyInstallmentRequest_: proto.RequiredMonthlyInstallmentRequest): kotlin.collections.Map<String, Any?> {
            // Create arguments from Request Message
            val requestedProduct: type.TRequestedProduct? = type.TRequestedProduct.toTRequestedProduct(requiredMonthlyInstallmentRequest_.getRequestedProduct())

            // Create map
            val map_: kotlin.collections.MutableMap<String, Any?> = mutableMapOf()
            map_.put("RequestedProduct", requestedProduct)
            return map_
        }

        @JvmStatic
        fun responseToOutput(requiredMonthlyInstallmentResponse_: proto.RequiredMonthlyInstallmentResponse): kotlin.Number? {
            // Extract and convert output
            return java.math.BigDecimal.valueOf(requiredMonthlyInstallmentResponse_.getRequiredMonthlyInstallment())
        }
    }
}
