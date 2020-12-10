
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
class CompositeDateTime() : com.gs.dmn.signavio.runtime.DefaultSignavioBaseDecision() {
    fun apply(compositeInputDateTime: String?, inputDate: String?, inputDateTime: String?, inputTime: String?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet): type.TCompositeDateTime? {
        return try {
            apply(compositeInputDateTime?.let({ com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(it, object : com.fasterxml.jackson.core.type.TypeReference<type.TCompositeDateTimeImpl>() {}) }), inputDate?.let({ date(it) }), inputDateTime?.let({ dateAndTime(it) }), inputTime?.let({ time(it) }), annotationSet_, com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), com.gs.dmn.runtime.cache.DefaultCache())
        } catch (e: Exception) {
            logError("Cannot apply decision 'CompositeDateTime'", e)
            null
        }
    }

    fun apply(compositeInputDateTime: String?, inputDate: String?, inputDateTime: String?, inputTime: String?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): type.TCompositeDateTime? {
        return try {
            apply(compositeInputDateTime?.let({ com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(it, object : com.fasterxml.jackson.core.type.TypeReference<type.TCompositeDateTimeImpl>() {}) }), inputDate?.let({ date(it) }), inputDateTime?.let({ dateAndTime(it) }), inputTime?.let({ time(it) }), annotationSet_, eventListener_, externalExecutor_, cache_)
        } catch (e: Exception) {
            logError("Cannot apply decision 'CompositeDateTime'", e)
            null
        }
    }

    fun apply(compositeInputDateTime: type.TCompositeDateTime?, inputDate: javax.xml.datatype.XMLGregorianCalendar?, inputDateTime: javax.xml.datatype.XMLGregorianCalendar?, inputTime: javax.xml.datatype.XMLGregorianCalendar?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet): type.TCompositeDateTime? {
        return apply(compositeInputDateTime, inputDate, inputDateTime, inputTime, annotationSet_, com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), com.gs.dmn.runtime.cache.DefaultCache())
    }

    fun apply(compositeInputDateTime: type.TCompositeDateTime?, inputDate: javax.xml.datatype.XMLGregorianCalendar?, inputDateTime: javax.xml.datatype.XMLGregorianCalendar?, inputTime: javax.xml.datatype.XMLGregorianCalendar?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): type.TCompositeDateTime? {
        try {
            // Start decision 'CompositeDateTime'
            val compositeDateTimeStartTime_ = System.currentTimeMillis()
            val compositeDateTimeArguments_ = com.gs.dmn.runtime.listener.Arguments()
            compositeDateTimeArguments_.put("CompositeInputDateTime", compositeInputDateTime)
            compositeDateTimeArguments_.put("InputDate", inputDate)
            compositeDateTimeArguments_.put("InputDateTime", inputDateTime)
            compositeDateTimeArguments_.put("InputTime", inputTime)
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, compositeDateTimeArguments_)

            // Evaluate decision 'CompositeDateTime'
            val output_: type.TCompositeDateTime? = evaluate(compositeInputDateTime, inputDate, inputDateTime, inputTime, annotationSet_, eventListener_, externalExecutor_, cache_)

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
        val compositeInputDateTime: type.TCompositeDateTime? = type.TCompositeDateTime.toTCompositeDateTime(compositeDateTimeRequest_.getCompositeInputDateTime())
        val inputDate: javax.xml.datatype.XMLGregorianCalendar? = date(compositeDateTimeRequest_.getInputDate())
        val inputDateTime: javax.xml.datatype.XMLGregorianCalendar? = dateAndTime(compositeDateTimeRequest_.getInputDateTime())
        val inputTime: javax.xml.datatype.XMLGregorianCalendar? = time(compositeDateTimeRequest_.getInputTime())

        // Invoke apply method
        val output_: type.TCompositeDateTime? = apply(compositeInputDateTime, inputDate, inputDateTime, inputTime, annotationSet_, eventListener_, externalExecutor_, cache_)

        // Convert output to Response Message
        val builder_: proto.CompositeDateTimeResponse.Builder = proto.CompositeDateTimeResponse.newBuilder()
        val outputProto_ = type.TCompositeDateTime.toProto(output_)
        if (outputProto_ != null) {
            builder_.setCompositeDateTime(outputProto_)
        }
        return builder_.build()
    }

    private inline fun evaluate(compositeInputDateTime: type.TCompositeDateTime?, inputDate: javax.xml.datatype.XMLGregorianCalendar?, inputDateTime: javax.xml.datatype.XMLGregorianCalendar?, inputTime: javax.xml.datatype.XMLGregorianCalendar?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): type.TCompositeDateTime? {
        return type.TCompositeDateTime.toTCompositeDateTime(compositeInputDateTime) as type.TCompositeDateTime?
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
            val inputDate: javax.xml.datatype.XMLGregorianCalendar? = com.gs.dmn.signavio.feel.lib.DefaultSignavioLib.INSTANCE.date(compositeDateTimeRequest_.getInputDate())
            val inputDateTime: javax.xml.datatype.XMLGregorianCalendar? = com.gs.dmn.signavio.feel.lib.DefaultSignavioLib.INSTANCE.dateAndTime(compositeDateTimeRequest_.getInputDateTime())
            val inputTime: javax.xml.datatype.XMLGregorianCalendar? = com.gs.dmn.signavio.feel.lib.DefaultSignavioLib.INSTANCE.time(compositeDateTimeRequest_.getInputTime())

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
