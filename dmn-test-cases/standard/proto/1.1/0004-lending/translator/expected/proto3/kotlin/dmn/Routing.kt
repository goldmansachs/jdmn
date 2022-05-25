
import java.util.*
import java.util.stream.Collectors

@javax.annotation.Generated(value = ["decision.ftl", "Routing"])
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "Routing",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.INVOCATION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
class Routing(val postBureauAffordability : PostBureauAffordability = PostBureauAffordability(), val postBureauRiskCategory : PostBureauRiskCategory = PostBureauRiskCategory()) : com.gs.dmn.runtime.DefaultDMNBaseDecision() {
    override fun apply(input_: MutableMap<String, String>, context_: com.gs.dmn.runtime.ExecutionContext): String? {
        try {
            return apply(input_.get("ApplicantData"), input_.get("BureauData"), input_.get("RequestedProduct"), context_.getAnnotations(), context_.getEventListener(), context_.getExternalFunctionExecutor(), context_.getCache())
        } catch (e: Exception) {
            logError("Cannot apply decision 'Routing'", e)
            return null
        }
    }

    fun apply(applicantData: String?, bureauData: String?, requestedProduct: String?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): String? {
        return try {
            apply(applicantData?.let({ com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(it, object : com.fasterxml.jackson.core.type.TypeReference<type.TApplicantDataImpl>() {}) }), bureauData?.let({ com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(it, object : com.fasterxml.jackson.core.type.TypeReference<type.TBureauDataImpl>() {}) }), requestedProduct?.let({ com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(it, object : com.fasterxml.jackson.core.type.TypeReference<type.TRequestedProductImpl>() {}) }), annotationSet_, eventListener_, externalExecutor_, cache_)
        } catch (e: Exception) {
            logError("Cannot apply decision 'Routing'", e)
            null
        }
    }

    fun apply(applicantData: type.TApplicantData?, bureauData: type.TBureauData?, requestedProduct: type.TRequestedProduct?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): String? {
        try {
            // Start decision 'Routing'
            val routingStartTime_ = System.currentTimeMillis()
            val routingArguments_ = com.gs.dmn.runtime.listener.Arguments()
            routingArguments_.put("ApplicantData", applicantData)
            routingArguments_.put("BureauData", bureauData)
            routingArguments_.put("RequestedProduct", requestedProduct)
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, routingArguments_)

            // Evaluate decision 'Routing'
            val output_: String? = evaluate(applicantData, bureauData, requestedProduct, annotationSet_, eventListener_, externalExecutor_, cache_)

            // End decision 'Routing'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, routingArguments_, output_, (System.currentTimeMillis() - routingStartTime_))

            return output_
        } catch (e: Exception) {
            logError("Exception caught in 'Routing' evaluation", e)
            return null
        }
    }

    fun apply(routingRequest_: proto.RoutingRequest, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): proto.RoutingResponse {
        // Create arguments from Request Message
        val applicantData: type.TApplicantData? = type.TApplicantData.toTApplicantData(routingRequest_.getApplicantData())
        val bureauData: type.TBureauData? = type.TBureauData.toTBureauData(routingRequest_.getBureauData())
        val requestedProduct: type.TRequestedProduct? = type.TRequestedProduct.toTRequestedProduct(routingRequest_.getRequestedProduct())

        // Invoke apply method
        val output_: String? = apply(applicantData, bureauData, requestedProduct, annotationSet_, eventListener_, externalExecutor_, cache_)

        // Convert output to Response Message
        val builder_: proto.RoutingResponse.Builder = proto.RoutingResponse.newBuilder()
        val outputProto_ = (if (output_ == null) "" else output_!!)
        builder_.setRouting(outputProto_)
        return builder_.build()
    }

    private inline fun evaluate(applicantData: type.TApplicantData?, bureauData: type.TBureauData?, requestedProduct: type.TRequestedProduct?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): String? {
        // Apply child decisions
        val postBureauAffordability: Boolean? = this@Routing.postBureauAffordability.apply(applicantData, bureauData, requestedProduct, annotationSet_, eventListener_, externalExecutor_, cache_)
        val postBureauRiskCategory: String? = this@Routing.postBureauRiskCategory.apply(applicantData, bureauData, annotationSet_, eventListener_, externalExecutor_, cache_)

        return RoutingRules.instance().apply(postBureauRiskCategory, postBureauAffordability, bureauData?.let({ it.bankrupt as Boolean? }), bureauData?.let({ it.creditScore as java.math.BigDecimal? }), annotationSet_, eventListener_, externalExecutor_, cache_) as String?
    }

    companion object {
        val DRG_ELEMENT_METADATA : com.gs.dmn.runtime.listener.DRGElement = com.gs.dmn.runtime.listener.DRGElement(
            "",
            "Routing",
            "",
            com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
            com.gs.dmn.runtime.annotation.ExpressionKind.INVOCATION,
            com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
            -1
        )

        @JvmStatic
        fun requestToMap(routingRequest_: proto.RoutingRequest): kotlin.collections.Map<String, Any?> {
            // Create arguments from Request Message
            val applicantData: type.TApplicantData? = type.TApplicantData.toTApplicantData(routingRequest_.getApplicantData())
            val bureauData: type.TBureauData? = type.TBureauData.toTBureauData(routingRequest_.getBureauData())
            val requestedProduct: type.TRequestedProduct? = type.TRequestedProduct.toTRequestedProduct(routingRequest_.getRequestedProduct())

            // Create map
            val map_: kotlin.collections.MutableMap<String, Any?> = mutableMapOf()
            map_.put("ApplicantData", applicantData)
            map_.put("BureauData", bureauData)
            map_.put("RequestedProduct", requestedProduct)
            return map_
        }

        @JvmStatic
        fun responseToOutput(routingResponse_: proto.RoutingResponse): String? {
            // Extract and convert output
            return routingResponse_.getRouting()
        }
    }
}
