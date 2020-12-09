
import java.util.*
import java.util.stream.Collectors

@javax.annotation.Generated(value = ["decision.ftl", "CompositeDateTime"])
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
    fun apply(compositeInputDateTime: String?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet): type.CompositeDateTime? {
        return try {
            apply(compositeInputDateTime?.let({ com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(it, object : com.fasterxml.jackson.core.type.TypeReference<type.CompositeDateTimeImpl>() {}) }), annotationSet_, com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), com.gs.dmn.runtime.cache.DefaultCache())
        } catch (e: Exception) {
            logError("Cannot apply decision 'CompositeDateTime'", e)
            null
        }
    }

    fun apply(compositeInputDateTime: String?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): type.CompositeDateTime? {
        return try {
            apply(compositeInputDateTime?.let({ com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(it, object : com.fasterxml.jackson.core.type.TypeReference<type.CompositeDateTimeImpl>() {}) }), annotationSet_, eventListener_, externalExecutor_, cache_)
        } catch (e: Exception) {
            logError("Cannot apply decision 'CompositeDateTime'", e)
            null
        }
    }

    fun apply(compositeInputDateTime: type.CompositeDateTime?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet): type.CompositeDateTime? {
        return apply(compositeInputDateTime, annotationSet_, com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), com.gs.dmn.runtime.cache.DefaultCache())
    }

    fun apply(compositeInputDateTime: type.CompositeDateTime?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): type.CompositeDateTime? {
        try {
            // Start decision 'CompositeDateTime'
            val compositeDateTimeStartTime_ = System.currentTimeMillis()
            val compositeDateTimeArguments_ = com.gs.dmn.runtime.listener.Arguments()
            compositeDateTimeArguments_.put("CompositeInputDateTime", compositeInputDateTime)
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, compositeDateTimeArguments_)

            // Evaluate decision 'CompositeDateTime'
            val output_: type.CompositeDateTime? = evaluate(compositeInputDateTime, annotationSet_, eventListener_, externalExecutor_, cache_)

            // End decision 'CompositeDateTime'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, compositeDateTimeArguments_, output_, (System.currentTimeMillis() - compositeDateTimeStartTime_))

            return output_
        } catch (e: Exception) {
            logError("Exception caught in 'CompositeDateTime' evaluation", e)
            return null
        }
    }

    fun apply(compositeDateTimeRequest_: proto.CompositeDateTimeRequest, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet): proto.CompositeDateTimeResponse {
        return apply(compositeDateTimeRequest_, annotationSet_, com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), com.gs.dmn.runtime.cache.DefaultCache())
    }

    fun apply(compositeDateTimeRequest_: proto.CompositeDateTimeRequest, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): proto.CompositeDateTimeResponse {
        // Create arguments from Request Message
        val compositeInputDateTime: type.CompositeDateTime? = type.CompositeDateTime.toCompositeDateTime(compositeDateTimeRequest_.getCompositeInputDateTime())

        // Invoke apply method
        val output_: type.CompositeDateTime? = apply(compositeInputDateTime, annotationSet_, eventListener_, externalExecutor_, cache_)

        // Convert output to Response Message
        val builder_: proto.CompositeDateTimeResponse.Builder = proto.CompositeDateTimeResponse.newBuilder()
        val outputProto_ = type.CompositeDateTime.toProto(output_)
        if (outputProto_ != null) {
            builder_.setCompositeDateTime(outputProto_)
        }
        return builder_.build()
    }

    private inline fun evaluate(compositeInputDateTime: type.CompositeDateTime?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): type.CompositeDateTime? {
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
