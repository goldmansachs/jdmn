
import java.util.*
import java.util.stream.Collectors

@jakarta.annotation.Generated(value = ["decision.ftl", "CompositeDateTime"])
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "CompositeDateTime",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
class CompositeDateTime() : com.gs.dmn.runtime.DefaultDMNBaseDecision() {
    override fun applyMap(input_: MutableMap<String, String>, context_: com.gs.dmn.runtime.ExecutionContext): type.CompositeDateTime? {
        try {
            return apply(input_.get("CompositeInputDateTime")?.let({ com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(it, object : com.fasterxml.jackson.core.type.TypeReference<type.CompositeDateTimeImpl>() {}) }), context_)
        } catch (e: Exception) {
            logError("Cannot apply decision 'CompositeDateTime'", e)
            return null
        }
    }

    fun apply(compositeInputDateTime: type.CompositeDateTime?, context_: com.gs.dmn.runtime.ExecutionContext): type.CompositeDateTime? {
        try {
            // Start decision 'CompositeDateTime'
            var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
            var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
            var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
            var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
            val compositeDateTimeStartTime_ = System.currentTimeMillis()
            val compositeDateTimeArguments_ = com.gs.dmn.runtime.listener.Arguments()
            compositeDateTimeArguments_.put("CompositeInputDateTime", compositeInputDateTime)
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, compositeDateTimeArguments_)

            // Evaluate decision 'CompositeDateTime'
            val output_: type.CompositeDateTime? = evaluate(compositeInputDateTime, context_)

            // End decision 'CompositeDateTime'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, compositeDateTimeArguments_, output_, (System.currentTimeMillis() - compositeDateTimeStartTime_))

            return output_
        } catch (e: Exception) {
            logError("Exception caught in 'CompositeDateTime' evaluation", e)
            return null
        }
    }

    fun applyProto(compositeDateTimeRequest_: proto.CompositeDateTimeRequest, context_: com.gs.dmn.runtime.ExecutionContext): proto.CompositeDateTimeResponse {
        // Create arguments from Request Message
        val compositeInputDateTime: type.CompositeDateTime? = type.CompositeDateTime.toCompositeDateTime(compositeDateTimeRequest_.getCompositeInputDateTime())

        // Invoke apply method
        val output_: type.CompositeDateTime? = apply(compositeInputDateTime, context_)

        // Convert output to Response Message
        val builder_: proto.CompositeDateTimeResponse.Builder = proto.CompositeDateTimeResponse.newBuilder()
        val outputProto_ = type.CompositeDateTime.toProto(output_)
        if (outputProto_ != null) {
            builder_.setCompositeDateTime(outputProto_)
        }
        return builder_.build()
    }

    private inline fun evaluate(compositeInputDateTime: type.CompositeDateTime?, context_: com.gs.dmn.runtime.ExecutionContext): type.CompositeDateTime? {
        var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
        var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
        var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
        var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
        return type.CompositeDateTime.toCompositeDateTime(compositeInputDateTime) as type.CompositeDateTime?
    }

    companion object {
        val DRG_ELEMENT_METADATA : com.gs.dmn.runtime.listener.DRGElement = com.gs.dmn.runtime.listener.DRGElement(
            "",
            "CompositeDateTime",
            "",
            com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
            com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
            com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
            -1
        )

        @JvmStatic
        fun requestToMap(compositeDateTimeRequest_: proto.CompositeDateTimeRequest): kotlin.collections.Map<String, Any?> {
            // Create arguments from Request Message
            val compositeInputDateTime: type.CompositeDateTime? = type.CompositeDateTime.toCompositeDateTime(compositeDateTimeRequest_.getCompositeInputDateTime())

            // Create map
            val map_: kotlin.collections.MutableMap<String, Any?> = mutableMapOf()
            map_.put("CompositeInputDateTime", compositeInputDateTime)
            return map_
        }

        @JvmStatic
        fun responseToOutput(compositeDateTimeResponse_: proto.CompositeDateTimeResponse): type.CompositeDateTime? {
            // Extract and convert output
            return type.CompositeDateTime.toCompositeDateTime(compositeDateTimeResponse_.getCompositeDateTime())
        }
    }
}
