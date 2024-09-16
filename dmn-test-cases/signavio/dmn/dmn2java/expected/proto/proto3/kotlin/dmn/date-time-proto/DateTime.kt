
import java.util.*
import java.util.stream.Collectors

@javax.annotation.Generated(value = ["signavio-decision.ftl", "DateTime"])
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "DateTime",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
class DateTime() : com.gs.dmn.signavio.runtime.JavaTimeSignavioBaseDecision() {
    override fun applyMap(input_: MutableMap<String, String>, context_: com.gs.dmn.runtime.ExecutionContext): java.time.temporal.TemporalAccessor? {
        try {
            return apply(input_.get("CompositeInputDateTime")?.let({ com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(it, object : com.fasterxml.jackson.core.type.TypeReference<type.TCompositeDateTimeImpl>() {}) }), input_.get("InputDate")?.let({ date(it) }), input_.get("InputDateTime")?.let({ dateAndTime(it) }), input_.get("InputTime")?.let({ time(it) }), context_)
        } catch (e: Exception) {
            logError("Cannot apply decision 'DateTime'", e)
            return null
        }
    }

    fun apply(compositeInputDateTime: type.TCompositeDateTime?, inputDate: java.time.LocalDate?, inputDateTime: java.time.temporal.TemporalAccessor?, inputTime: java.time.temporal.TemporalAccessor?, context_: com.gs.dmn.runtime.ExecutionContext): java.time.temporal.TemporalAccessor? {
        try {
            // Start decision 'DateTime'
            var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
            var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
            var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
            var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
            val dateTimeStartTime_ = System.currentTimeMillis()
            val dateTimeArguments_ = com.gs.dmn.runtime.listener.Arguments()
            dateTimeArguments_.put("CompositeInputDateTime", compositeInputDateTime)
            dateTimeArguments_.put("InputDate", inputDate)
            dateTimeArguments_.put("InputDateTime", inputDateTime)
            dateTimeArguments_.put("InputTime", inputTime)
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, dateTimeArguments_)

            // Evaluate decision 'DateTime'
            val output_: java.time.temporal.TemporalAccessor? = evaluate(compositeInputDateTime, inputDate, inputDateTime, inputTime, context_)

            // End decision 'DateTime'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, dateTimeArguments_, output_, (System.currentTimeMillis() - dateTimeStartTime_))

            return output_
        } catch (e: Exception) {
            logError("Exception caught in 'DateTime' evaluation", e)
            return null
        }
    }

    fun applyProto(dateTimeRequest_: proto.DateTimeRequest, context_: com.gs.dmn.runtime.ExecutionContext): proto.DateTimeResponse {
        // Create arguments from Request Message
        val compositeInputDateTime: type.TCompositeDateTime? = type.TCompositeDateTime.toTCompositeDateTime(dateTimeRequest_.getCompositeInputDateTime())
        val inputDate: java.time.LocalDate? = date(dateTimeRequest_.getInputDate())
        val inputDateTime: java.time.temporal.TemporalAccessor? = dateAndTime(dateTimeRequest_.getInputDateTime())
        val inputTime: java.time.temporal.TemporalAccessor? = time(dateTimeRequest_.getInputTime())

        // Invoke apply method
        val output_: java.time.temporal.TemporalAccessor? = apply(compositeInputDateTime, inputDate, inputDateTime, inputTime, context_)

        // Convert output to Response Message
        val builder_: proto.DateTimeResponse.Builder = proto.DateTimeResponse.newBuilder()
        val outputProto_ = string(output_)
        builder_.setDateTime(outputProto_)
        return builder_.build()
    }

    private inline fun evaluate(compositeInputDateTime: type.TCompositeDateTime?, inputDate: java.time.LocalDate?, inputDateTime: java.time.temporal.TemporalAccessor?, inputTime: java.time.temporal.TemporalAccessor?, context_: com.gs.dmn.runtime.ExecutionContext): java.time.temporal.TemporalAccessor? {
        var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
        var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
        var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
        var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
        return inputDateTime as java.time.temporal.TemporalAccessor?
    }

    companion object {
        val DRG_ELEMENT_METADATA : com.gs.dmn.runtime.listener.DRGElement = com.gs.dmn.runtime.listener.DRGElement(
            "",
            "DateTime",
            "",
            com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
            com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
            com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
            -1
        )

        @JvmStatic
        fun requestToMap(dateTimeRequest_: proto.DateTimeRequest): kotlin.collections.Map<String, Any?> {
            // Create arguments from Request Message
            val compositeInputDateTime: type.TCompositeDateTime? = type.TCompositeDateTime.toTCompositeDateTime(dateTimeRequest_.getCompositeInputDateTime())
            val inputDate: java.time.LocalDate? = com.gs.dmn.signavio.feel.lib.JavaTimeSignavioLib.INSTANCE.date(dateTimeRequest_.getInputDate())
            val inputDateTime: java.time.temporal.TemporalAccessor? = com.gs.dmn.signavio.feel.lib.JavaTimeSignavioLib.INSTANCE.dateAndTime(dateTimeRequest_.getInputDateTime())
            val inputTime: java.time.temporal.TemporalAccessor? = com.gs.dmn.signavio.feel.lib.JavaTimeSignavioLib.INSTANCE.time(dateTimeRequest_.getInputTime())

            // Create map
            val map_: kotlin.collections.MutableMap<String, Any?> = mutableMapOf()
            map_.put("CompositeInputDateTime", compositeInputDateTime)
            map_.put("InputDate", inputDate)
            map_.put("InputDateTime", inputDateTime)
            map_.put("InputTime", inputTime)
            return map_
        }

        @JvmStatic
        fun responseToOutput(dateTimeResponse_: proto.DateTimeResponse): java.time.temporal.TemporalAccessor? {
            // Extract and convert output
            return com.gs.dmn.signavio.feel.lib.JavaTimeSignavioLib.INSTANCE.dateAndTime(dateTimeResponse_.getDateTime())
        }
    }
}
