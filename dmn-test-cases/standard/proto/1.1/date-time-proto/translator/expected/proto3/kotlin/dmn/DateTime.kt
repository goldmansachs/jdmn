
import java.util.*
import java.util.stream.Collectors

@jakarta.annotation.Generated(value = ["decision.ftl", "DateTime"])
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "DateTime",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
class DateTime() : com.gs.dmn.runtime.DefaultDMNBaseDecision() {
    override fun applyMap(input_: MutableMap<String, String>, context_: com.gs.dmn.runtime.ExecutionContext): javax.xml.datatype.XMLGregorianCalendar? {
        try {
            return apply(input_.get("InputDateTime")?.let({ dateAndTime(it) }), context_)
        } catch (e: Exception) {
            logError("Cannot apply decision 'DateTime'", e)
            return null
        }
    }

    fun apply(inputDateTime: javax.xml.datatype.XMLGregorianCalendar?, context_: com.gs.dmn.runtime.ExecutionContext): javax.xml.datatype.XMLGregorianCalendar? {
        try {
            // Start decision 'DateTime'
            var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
            var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
            var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
            var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
            val dateTimeStartTime_ = System.currentTimeMillis()
            val dateTimeArguments_ = com.gs.dmn.runtime.listener.Arguments()
            dateTimeArguments_.put("InputDateTime", inputDateTime)
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, dateTimeArguments_)

            // Evaluate decision 'DateTime'
            val output_: javax.xml.datatype.XMLGregorianCalendar? = evaluate(inputDateTime, context_)

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
        val inputDateTime: javax.xml.datatype.XMLGregorianCalendar? = dateAndTime(dateTimeRequest_.getInputDateTime())

        // Invoke apply method
        val output_: javax.xml.datatype.XMLGregorianCalendar? = apply(inputDateTime, context_)

        // Convert output to Response Message
        val builder_: proto.DateTimeResponse.Builder = proto.DateTimeResponse.newBuilder()
        val outputProto_ = string(output_)
        builder_.setDateTime(outputProto_)
        return builder_.build()
    }

    private inline fun evaluate(inputDateTime: javax.xml.datatype.XMLGregorianCalendar?, context_: com.gs.dmn.runtime.ExecutionContext): javax.xml.datatype.XMLGregorianCalendar? {
        var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
        var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
        var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
        var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
        return inputDateTime as javax.xml.datatype.XMLGregorianCalendar?
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
            val inputDateTime: javax.xml.datatype.XMLGregorianCalendar? = com.gs.dmn.feel.lib.DefaultFEELLib.INSTANCE.dateAndTime(dateTimeRequest_.getInputDateTime())

            // Create map
            val map_: kotlin.collections.MutableMap<String, Any?> = mutableMapOf()
            map_.put("InputDateTime", inputDateTime)
            return map_
        }

        @JvmStatic
        fun responseToOutput(dateTimeResponse_: proto.DateTimeResponse): javax.xml.datatype.XMLGregorianCalendar? {
            // Extract and convert output
            return com.gs.dmn.feel.lib.DefaultFEELLib.INSTANCE.dateAndTime(dateTimeResponse_.getDateTime())
        }
    }
}
