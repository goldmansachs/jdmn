
import java.util.*
import java.util.stream.Collectors

@javax.annotation.Generated(value = ["signavio-decision.ftl", "CompositeDateTime"])
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "CompositeDateTime",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
class CompositeDateTime() : com.gs.dmn.signavio.runtime.JavaTimeSignavioBaseDecision() {
    override fun applyMap(input_: MutableMap<String, String>, context_: com.gs.dmn.runtime.ExecutionContext): type.TCompositeDateTime? {
        try {
            return apply(input_.get("CompositeInputDateTime")?.let({ com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(it, object : com.fasterxml.jackson.core.type.TypeReference<type.TCompositeDateTimeImpl>() {}) }), input_.get("InputDate")?.let({ date(it) }), input_.get("InputDateTime")?.let({ dateAndTime(it) }), input_.get("InputTime")?.let({ time(it) }), context_)
        } catch (e: Exception) {
            logError("Cannot apply decision 'CompositeDateTime'", e)
            return null
        }
    }

    fun apply(compositeInputDateTime: type.TCompositeDateTime?, inputDate: java.time.LocalDate?, inputDateTime: java.time.temporal.TemporalAccessor?, inputTime: java.time.temporal.TemporalAccessor?, context_: com.gs.dmn.runtime.ExecutionContext): type.TCompositeDateTime? {
        try {
            // Start decision 'CompositeDateTime'
            var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
            var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
            var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
            var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
            val compositeDateTimeStartTime_ = System.currentTimeMillis()
            val compositeDateTimeArguments_ = com.gs.dmn.runtime.listener.Arguments()
            compositeDateTimeArguments_.put("CompositeInputDateTime", compositeInputDateTime)
            compositeDateTimeArguments_.put("InputDate", inputDate)
            compositeDateTimeArguments_.put("InputDateTime", inputDateTime)
            compositeDateTimeArguments_.put("InputTime", inputTime)
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, compositeDateTimeArguments_)

            // Evaluate decision 'CompositeDateTime'
            val output_: type.TCompositeDateTime? = evaluate(compositeInputDateTime, inputDate, inputDateTime, inputTime, context_)

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
        val compositeInputDateTime: type.TCompositeDateTime? = type.TCompositeDateTime.toTCompositeDateTime(compositeDateTimeRequest_.getCompositeInputDateTime())
        val inputDate: java.time.LocalDate? = date(compositeDateTimeRequest_.getInputDate())
        val inputDateTime: java.time.temporal.TemporalAccessor? = dateAndTime(compositeDateTimeRequest_.getInputDateTime())
        val inputTime: java.time.temporal.TemporalAccessor? = time(compositeDateTimeRequest_.getInputTime())

        // Invoke apply method
        val output_: type.TCompositeDateTime? = apply(compositeInputDateTime, inputDate, inputDateTime, inputTime, context_)

        // Convert output to Response Message
        val builder_: proto.CompositeDateTimeResponse.Builder = proto.CompositeDateTimeResponse.newBuilder()
        val outputProto_ = type.TCompositeDateTime.toProto(output_)
        if (outputProto_ != null) {
            builder_.setCompositeDateTime(outputProto_)
        }
        return builder_.build()
    }

    private inline fun evaluate(compositeInputDateTime: type.TCompositeDateTime?, inputDate: java.time.LocalDate?, inputDateTime: java.time.temporal.TemporalAccessor?, inputTime: java.time.temporal.TemporalAccessor?, context_: com.gs.dmn.runtime.ExecutionContext): type.TCompositeDateTime? {
        var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
        var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
        var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
        var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
        return compositeInputDateTime as type.TCompositeDateTime?
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
            val compositeInputDateTime: type.TCompositeDateTime? = type.TCompositeDateTime.toTCompositeDateTime(compositeDateTimeRequest_.getCompositeInputDateTime())
            val inputDate: java.time.LocalDate? = com.gs.dmn.signavio.feel.lib.JavaTimeSignavioLib.INSTANCE.date(compositeDateTimeRequest_.getInputDate())
            val inputDateTime: java.time.temporal.TemporalAccessor? = com.gs.dmn.signavio.feel.lib.JavaTimeSignavioLib.INSTANCE.dateAndTime(compositeDateTimeRequest_.getInputDateTime())
            val inputTime: java.time.temporal.TemporalAccessor? = com.gs.dmn.signavio.feel.lib.JavaTimeSignavioLib.INSTANCE.time(compositeDateTimeRequest_.getInputTime())

            // Create map
            val map_: kotlin.collections.MutableMap<String, Any?> = mutableMapOf()
            map_.put("CompositeInputDateTime", compositeInputDateTime)
            map_.put("InputDate", inputDate)
            map_.put("InputDateTime", inputDateTime)
            map_.put("InputTime", inputTime)
            return map_
        }

        @JvmStatic
        fun responseToOutput(compositeDateTimeResponse_: proto.CompositeDateTimeResponse): type.TCompositeDateTime? {
            // Extract and convert output
            return type.TCompositeDateTime.toTCompositeDateTime(compositeDateTimeResponse_.getCompositeDateTime())
        }
    }
}
