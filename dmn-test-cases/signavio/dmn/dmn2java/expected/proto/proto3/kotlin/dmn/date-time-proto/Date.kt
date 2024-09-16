
import java.util.*
import java.util.stream.Collectors

@javax.annotation.Generated(value = ["signavio-decision.ftl", "Date"])
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "Date",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
class Date() : com.gs.dmn.signavio.runtime.JavaTimeSignavioBaseDecision() {
    override fun applyMap(input_: MutableMap<String, String>, context_: com.gs.dmn.runtime.ExecutionContext): java.time.LocalDate? {
        try {
            return apply(input_.get("CompositeInputDateTime")?.let({ com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(it, object : com.fasterxml.jackson.core.type.TypeReference<type.TCompositeDateTimeImpl>() {}) }), input_.get("InputDate")?.let({ date(it) }), input_.get("InputDateTime")?.let({ dateAndTime(it) }), input_.get("InputTime")?.let({ time(it) }), context_)
        } catch (e: Exception) {
            logError("Cannot apply decision 'Date'", e)
            return null
        }
    }

    fun apply(compositeInputDateTime: type.TCompositeDateTime?, inputDate: java.time.LocalDate?, inputDateTime: java.time.temporal.TemporalAccessor?, inputTime: java.time.temporal.TemporalAccessor?, context_: com.gs.dmn.runtime.ExecutionContext): java.time.LocalDate? {
        try {
            // Start decision 'Date'
            var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
            var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
            var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
            var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
            val dateStartTime_ = System.currentTimeMillis()
            val dateArguments_ = com.gs.dmn.runtime.listener.Arguments()
            dateArguments_.put("CompositeInputDateTime", compositeInputDateTime)
            dateArguments_.put("InputDate", inputDate)
            dateArguments_.put("InputDateTime", inputDateTime)
            dateArguments_.put("InputTime", inputTime)
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, dateArguments_)

            // Evaluate decision 'Date'
            val output_: java.time.LocalDate? = evaluate(compositeInputDateTime, inputDate, inputDateTime, inputTime, context_)

            // End decision 'Date'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, dateArguments_, output_, (System.currentTimeMillis() - dateStartTime_))

            return output_
        } catch (e: Exception) {
            logError("Exception caught in 'Date' evaluation", e)
            return null
        }
    }

    fun applyProto(dateRequest_: proto.DateRequest, context_: com.gs.dmn.runtime.ExecutionContext): proto.DateResponse {
        // Create arguments from Request Message
        val compositeInputDateTime: type.TCompositeDateTime? = type.TCompositeDateTime.toTCompositeDateTime(dateRequest_.getCompositeInputDateTime())
        val inputDate: java.time.LocalDate? = date(dateRequest_.getInputDate())
        val inputDateTime: java.time.temporal.TemporalAccessor? = dateAndTime(dateRequest_.getInputDateTime())
        val inputTime: java.time.temporal.TemporalAccessor? = time(dateRequest_.getInputTime())

        // Invoke apply method
        val output_: java.time.LocalDate? = apply(compositeInputDateTime, inputDate, inputDateTime, inputTime, context_)

        // Convert output to Response Message
        val builder_: proto.DateResponse.Builder = proto.DateResponse.newBuilder()
        val outputProto_ = string(output_)
        builder_.setDate(outputProto_)
        return builder_.build()
    }

    private inline fun evaluate(compositeInputDateTime: type.TCompositeDateTime?, inputDate: java.time.LocalDate?, inputDateTime: java.time.temporal.TemporalAccessor?, inputTime: java.time.temporal.TemporalAccessor?, context_: com.gs.dmn.runtime.ExecutionContext): java.time.LocalDate? {
        var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
        var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
        var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
        var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
        return inputDate as java.time.LocalDate?
    }

    companion object {
        val DRG_ELEMENT_METADATA : com.gs.dmn.runtime.listener.DRGElement = com.gs.dmn.runtime.listener.DRGElement(
            "",
            "Date",
            "",
            com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
            com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
            com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
            -1
        )

        @JvmStatic
        fun requestToMap(dateRequest_: proto.DateRequest): kotlin.collections.Map<String, Any?> {
            // Create arguments from Request Message
            val compositeInputDateTime: type.TCompositeDateTime? = type.TCompositeDateTime.toTCompositeDateTime(dateRequest_.getCompositeInputDateTime())
            val inputDate: java.time.LocalDate? = com.gs.dmn.signavio.feel.lib.JavaTimeSignavioLib.INSTANCE.date(dateRequest_.getInputDate())
            val inputDateTime: java.time.temporal.TemporalAccessor? = com.gs.dmn.signavio.feel.lib.JavaTimeSignavioLib.INSTANCE.dateAndTime(dateRequest_.getInputDateTime())
            val inputTime: java.time.temporal.TemporalAccessor? = com.gs.dmn.signavio.feel.lib.JavaTimeSignavioLib.INSTANCE.time(dateRequest_.getInputTime())

            // Create map
            val map_: kotlin.collections.MutableMap<String, Any?> = mutableMapOf()
            map_.put("CompositeInputDateTime", compositeInputDateTime)
            map_.put("InputDate", inputDate)
            map_.put("InputDateTime", inputDateTime)
            map_.put("InputTime", inputTime)
            return map_
        }

        @JvmStatic
        fun responseToOutput(dateResponse_: proto.DateResponse): java.time.LocalDate? {
            // Extract and convert output
            return com.gs.dmn.signavio.feel.lib.JavaTimeSignavioLib.INSTANCE.date(dateResponse_.getDate())
        }
    }
}
