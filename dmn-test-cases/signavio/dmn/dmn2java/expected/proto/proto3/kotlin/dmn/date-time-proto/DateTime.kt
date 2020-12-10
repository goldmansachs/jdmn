
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
class DateTime() : com.gs.dmn.signavio.runtime.DefaultSignavioBaseDecision() {
    fun apply(compositeInputDateTime: String?, inputDate: String?, inputDateTime: String?, inputTime: String?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet): javax.xml.datatype.XMLGregorianCalendar? {
        return try {
            apply(compositeInputDateTime?.let({ com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(it, object : com.fasterxml.jackson.core.type.TypeReference<type.TCompositeDateTimeImpl>() {}) }), inputDate?.let({ date(it) }), inputDateTime?.let({ dateAndTime(it) }), inputTime?.let({ time(it) }), annotationSet_, com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), com.gs.dmn.runtime.cache.DefaultCache())
        } catch (e: Exception) {
            logError("Cannot apply decision 'DateTime'", e)
            null
        }
    }

    fun apply(compositeInputDateTime: String?, inputDate: String?, inputDateTime: String?, inputTime: String?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): javax.xml.datatype.XMLGregorianCalendar? {
        return try {
            apply(compositeInputDateTime?.let({ com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(it, object : com.fasterxml.jackson.core.type.TypeReference<type.TCompositeDateTimeImpl>() {}) }), inputDate?.let({ date(it) }), inputDateTime?.let({ dateAndTime(it) }), inputTime?.let({ time(it) }), annotationSet_, eventListener_, externalExecutor_, cache_)
        } catch (e: Exception) {
            logError("Cannot apply decision 'DateTime'", e)
            null
        }
    }

    fun apply(compositeInputDateTime: type.TCompositeDateTime?, inputDate: javax.xml.datatype.XMLGregorianCalendar?, inputDateTime: javax.xml.datatype.XMLGregorianCalendar?, inputTime: javax.xml.datatype.XMLGregorianCalendar?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet): javax.xml.datatype.XMLGregorianCalendar? {
        return apply(compositeInputDateTime, inputDate, inputDateTime, inputTime, annotationSet_, com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), com.gs.dmn.runtime.cache.DefaultCache())
    }

    fun apply(compositeInputDateTime: type.TCompositeDateTime?, inputDate: javax.xml.datatype.XMLGregorianCalendar?, inputDateTime: javax.xml.datatype.XMLGregorianCalendar?, inputTime: javax.xml.datatype.XMLGregorianCalendar?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): javax.xml.datatype.XMLGregorianCalendar? {
        try {
            // Start decision 'DateTime'
            val dateTimeStartTime_ = System.currentTimeMillis()
            val dateTimeArguments_ = com.gs.dmn.runtime.listener.Arguments()
            dateTimeArguments_.put("CompositeInputDateTime", compositeInputDateTime)
            dateTimeArguments_.put("InputDate", inputDate)
            dateTimeArguments_.put("InputDateTime", inputDateTime)
            dateTimeArguments_.put("InputTime", inputTime)
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, dateTimeArguments_)

            // Evaluate decision 'DateTime'
            val output_: javax.xml.datatype.XMLGregorianCalendar? = evaluate(compositeInputDateTime, inputDate, inputDateTime, inputTime, annotationSet_, eventListener_, externalExecutor_, cache_)

            // End decision 'DateTime'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, dateTimeArguments_, output_, (System.currentTimeMillis() - dateTimeStartTime_))

            return output_
        } catch (e: Exception) {
            logError("Exception caught in 'DateTime' evaluation", e)
            return null
        }
    }

    fun apply(dateTimeRequest_: proto.DateTimeRequest, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet): proto.DateTimeResponse {
        return apply(dateTimeRequest_, annotationSet_, com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), com.gs.dmn.runtime.cache.DefaultCache())
    }

    fun apply(dateTimeRequest_: proto.DateTimeRequest, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): proto.DateTimeResponse {
        // Create arguments from Request Message
        val compositeInputDateTime: type.TCompositeDateTime? = type.TCompositeDateTime.toTCompositeDateTime(dateTimeRequest_.getCompositeInputDateTime())
        val inputDate: javax.xml.datatype.XMLGregorianCalendar? = date(dateTimeRequest_.getInputDate())
        val inputDateTime: javax.xml.datatype.XMLGregorianCalendar? = dateAndTime(dateTimeRequest_.getInputDateTime())
        val inputTime: javax.xml.datatype.XMLGregorianCalendar? = time(dateTimeRequest_.getInputTime())

        // Invoke apply method
        val output_: javax.xml.datatype.XMLGregorianCalendar? = apply(compositeInputDateTime, inputDate, inputDateTime, inputTime, annotationSet_, eventListener_, externalExecutor_, cache_)

        // Convert output to Response Message
        val builder_: proto.DateTimeResponse.Builder = proto.DateTimeResponse.newBuilder()
        val outputProto_ = string(output_)
        builder_.setDateTime(outputProto_)
        return builder_.build()
    }

    private inline fun evaluate(compositeInputDateTime: type.TCompositeDateTime?, inputDate: javax.xml.datatype.XMLGregorianCalendar?, inputDateTime: javax.xml.datatype.XMLGregorianCalendar?, inputTime: javax.xml.datatype.XMLGregorianCalendar?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): javax.xml.datatype.XMLGregorianCalendar? {
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
            val compositeInputDateTime: type.TCompositeDateTime? = type.TCompositeDateTime.toTCompositeDateTime(dateTimeRequest_.getCompositeInputDateTime())
            val inputDate: javax.xml.datatype.XMLGregorianCalendar? = com.gs.dmn.signavio.feel.lib.DefaultSignavioLib.INSTANCE.date(dateTimeRequest_.getInputDate())
            val inputDateTime: javax.xml.datatype.XMLGregorianCalendar? = com.gs.dmn.signavio.feel.lib.DefaultSignavioLib.INSTANCE.dateAndTime(dateTimeRequest_.getInputDateTime())
            val inputTime: javax.xml.datatype.XMLGregorianCalendar? = com.gs.dmn.signavio.feel.lib.DefaultSignavioLib.INSTANCE.time(dateTimeRequest_.getInputTime())

            // Create map
            val map_: kotlin.collections.MutableMap<String, Any?> = mutableMapOf()
            map_.put("CompositeInputDateTime", compositeInputDateTime)
            map_.put("InputDate", inputDate)
            map_.put("InputDateTime", inputDateTime)
            map_.put("InputTime", inputTime)
            return map_
        }

        @JvmStatic
        fun responseToOutput(dateTimeResponse_: proto.DateTimeResponse): javax.xml.datatype.XMLGregorianCalendar? {
            // Extract and convert output
            return com.gs.dmn.signavio.feel.lib.DefaultSignavioLib.INSTANCE.dateAndTime(dateTimeResponse_.getDateTime())
        }
    }
}
