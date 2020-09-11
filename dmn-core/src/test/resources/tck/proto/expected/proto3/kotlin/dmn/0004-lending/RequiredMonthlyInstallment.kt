
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
class RequiredMonthlyInstallment() : com.gs.dmn.runtime.DefaultDMNBaseDecision() {
    fun apply(requestedProduct: String?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet): java.math.BigDecimal? {
        return try {
            apply(requestedProduct?.let({ com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(it, object : com.fasterxml.jackson.core.type.TypeReference<type.TRequestedProductImpl>() {}) }), annotationSet_, com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), com.gs.dmn.runtime.cache.DefaultCache())
        } catch (e: Exception) {
            logError("Cannot apply decision 'RequiredMonthlyInstallment'", e)
            null
        }
    }

    fun apply(requestedProduct: String?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): java.math.BigDecimal? {
        return try {
            apply(requestedProduct?.let({ com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(it, object : com.fasterxml.jackson.core.type.TypeReference<type.TRequestedProductImpl>() {}) }), annotationSet_, eventListener_, externalExecutor_, cache_)
        } catch (e: Exception) {
            logError("Cannot apply decision 'RequiredMonthlyInstallment'", e)
            null
        }
    }

    fun apply(requestedProduct: type.TRequestedProduct?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet): java.math.BigDecimal? {
        return apply(requestedProduct, annotationSet_, com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), com.gs.dmn.runtime.cache.DefaultCache())
    }

    fun apply(requestedProduct: type.TRequestedProduct?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): java.math.BigDecimal? {
        try {
            // Start decision 'RequiredMonthlyInstallment'
            val requiredMonthlyInstallmentStartTime_ = System.currentTimeMillis()
            val requiredMonthlyInstallmentArguments_ = com.gs.dmn.runtime.listener.Arguments()
            requiredMonthlyInstallmentArguments_.put("RequestedProduct", requestedProduct)
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, requiredMonthlyInstallmentArguments_)

            if (cache_.contains("RequiredMonthlyInstallment")) {
                // Retrieve value from cache
                var output_:java.math.BigDecimal? = cache_.lookup("RequiredMonthlyInstallment") as java.math.BigDecimal?

                // End decision 'RequiredMonthlyInstallment'
                eventListener_.endDRGElement(DRG_ELEMENT_METADATA, requiredMonthlyInstallmentArguments_, output_, (System.currentTimeMillis() - requiredMonthlyInstallmentStartTime_))

                return output_
            } else {
                // Evaluate decision 'RequiredMonthlyInstallment'
                val output_: java.math.BigDecimal? = evaluate(requestedProduct, annotationSet_, eventListener_, externalExecutor_, cache_)
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

    fun apply(requiredMonthlyInstallmentRequest_: proto.RequiredMonthlyInstallmentRequest, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet): proto.RequiredMonthlyInstallmentResponse {
        return apply(requiredMonthlyInstallmentRequest_, annotationSet_, com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), com.gs.dmn.runtime.cache.DefaultCache())
    }

    fun apply(requiredMonthlyInstallmentRequest_: proto.RequiredMonthlyInstallmentRequest, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): proto.RequiredMonthlyInstallmentResponse {
        // Create arguments from Request Message
        val requestedProduct: type.TRequestedProduct? = type.TRequestedProduct.toTRequestedProduct(requiredMonthlyInstallmentRequest_.getRequestedProduct())

        // Invoke apply method
        val output_: java.math.BigDecimal? = apply(requestedProduct, annotationSet_, eventListener_, externalExecutor_, cache_)

        // Convert output to Response Message
        val builder_: proto.RequiredMonthlyInstallmentResponse.Builder = proto.RequiredMonthlyInstallmentResponse.newBuilder()
        val outputProto_ = (if (output_ == null) 0.0 else output_!!.toDouble())
        builder_.setRequiredMonthlyInstallment(outputProto_)
        return builder_.build()
    }

    private inline fun evaluate(requestedProduct: type.TRequestedProduct?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): java.math.BigDecimal? {
        return InstallmentCalculation.InstallmentCalculation(requestedProduct?.let({ it.productType as String? }), requestedProduct?.let({ it.rate as java.math.BigDecimal? }), requestedProduct?.let({ it.term as java.math.BigDecimal? }), requestedProduct?.let({ it.amount as java.math.BigDecimal? }), annotationSet_, eventListener_, externalExecutor_, cache_) as java.math.BigDecimal?
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
        fun responseToOutput(requiredMonthlyInstallmentResponse_: proto.RequiredMonthlyInstallmentResponse): java.math.BigDecimal? {
            // Extract and convert output
            return java.math.BigDecimal.valueOf(requiredMonthlyInstallmentResponse_.getRequiredMonthlyInstallment())
        }
    }
}
