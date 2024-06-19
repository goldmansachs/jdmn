
import java.util.*
import java.util.stream.Collectors

@javax.annotation.Generated(value = ["decision.ftl", "Date"])
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "Date",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
class Date() : com.gs.dmn.runtime.JavaTimeDMNBaseDecision() {
    override fun applyMap(input_: MutableMap<String, String>, context_: com.gs.dmn.runtime.ExecutionContext): java.time.LocalDate? {
        try {
            return apply(input_.get("InputDate")?.let({ date(it) }), context_)
        } catch (e: Exception) {
            logError("Cannot apply decision 'Date'", e)
            return null
        }
    }

    fun apply(inputDate: java.time.LocalDate?, context_: com.gs.dmn.runtime.ExecutionContext): java.time.LocalDate? {
        try {
            // Start decision 'Date'
            var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
            var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
            var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
            var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
            val dateStartTime_ = System.currentTimeMillis()
            val dateArguments_ = com.gs.dmn.runtime.listener.Arguments()
            dateArguments_.put("InputDate", inputDate)
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, dateArguments_)

            // Evaluate decision 'Date'
            val output_: java.time.LocalDate? = evaluate(inputDate, context_)

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
        val inputDate: java.time.LocalDate? = date(dateRequest_.getInputDate())

        // Invoke apply method
        val output_: java.time.LocalDate? = apply(inputDate, context_)

        // Convert output to Response Message
        val builder_: proto.DateResponse.Builder = proto.DateResponse.newBuilder()
        val outputProto_ = string(output_)
        builder_.setDate(outputProto_)
        return builder_.build()
    }

    private inline fun evaluate(inputDate: java.time.LocalDate?, context_: com.gs.dmn.runtime.ExecutionContext): java.time.LocalDate? {
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
            val inputDate: java.time.LocalDate? = com.gs.dmn.feel.lib.JavaTimeFEELLib.INSTANCE.date(dateRequest_.getInputDate())

            // Create map
            val map_: kotlin.collections.MutableMap<String, Any?> = mutableMapOf()
            map_.put("InputDate", inputDate)
            return map_
        }

        @JvmStatic
        fun responseToOutput(dateResponse_: proto.DateResponse): java.time.LocalDate? {
            // Extract and convert output
            return com.gs.dmn.feel.lib.JavaTimeFEELLib.INSTANCE.date(dateResponse_.getDate())
        }
    }
}
