
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
class Date() : com.gs.dmn.runtime.DefaultDMNBaseDecision() {
    fun apply(inputDate: String?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet): javax.xml.datatype.XMLGregorianCalendar? {
        return try {
            apply(inputDate?.let({ date(it) }), annotationSet_, com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), com.gs.dmn.runtime.cache.DefaultCache())
        } catch (e: Exception) {
            logError("Cannot apply decision 'Date'", e)
            null
        }
    }

    fun apply(inputDate: String?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): javax.xml.datatype.XMLGregorianCalendar? {
        return try {
            apply(inputDate?.let({ date(it) }), annotationSet_, eventListener_, externalExecutor_, cache_)
        } catch (e: Exception) {
            logError("Cannot apply decision 'Date'", e)
            null
        }
    }

    fun apply(inputDate: javax.xml.datatype.XMLGregorianCalendar?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet): javax.xml.datatype.XMLGregorianCalendar? {
        return apply(inputDate, annotationSet_, com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), com.gs.dmn.runtime.cache.DefaultCache())
    }

    fun apply(inputDate: javax.xml.datatype.XMLGregorianCalendar?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): javax.xml.datatype.XMLGregorianCalendar? {
        try {
            // Start decision 'Date'
            val dateStartTime_ = System.currentTimeMillis()
            val dateArguments_ = com.gs.dmn.runtime.listener.Arguments()
            dateArguments_.put("InputDate", inputDate)
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, dateArguments_)

            // Evaluate decision 'Date'
            val output_: javax.xml.datatype.XMLGregorianCalendar? = evaluate(inputDate, annotationSet_, eventListener_, externalExecutor_, cache_)

            // End decision 'Date'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, dateArguments_, output_, (System.currentTimeMillis() - dateStartTime_))

            return output_
        } catch (e: Exception) {
            logError("Exception caught in 'Date' evaluation", e)
            return null
        }
    }

    fun apply(dateRequest_: proto.DateRequest, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet): proto.DateResponse {
        return apply(dateRequest_, annotationSet_, com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), com.gs.dmn.runtime.cache.DefaultCache())
    }

    fun apply(dateRequest_: proto.DateRequest, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): proto.DateResponse {
        // Create arguments from Request Message
        val inputDate: javax.xml.datatype.XMLGregorianCalendar? = date(dateRequest_.getInputDate())

        // Invoke apply method
        val output_: javax.xml.datatype.XMLGregorianCalendar? = apply(inputDate, annotationSet_, eventListener_, externalExecutor_, cache_)

        // Convert output to Response Message
        val builder_: proto.DateResponse.Builder = proto.DateResponse.newBuilder()
        val outputProto_ = string(output_)
        builder_.setDate(outputProto_)
        return builder_.build()
    }

    private inline fun evaluate(inputDate: javax.xml.datatype.XMLGregorianCalendar?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): javax.xml.datatype.XMLGregorianCalendar? {
        return inputDate as javax.xml.datatype.XMLGregorianCalendar?
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
            val inputDate: javax.xml.datatype.XMLGregorianCalendar? = com.gs.dmn.feel.lib.DefaultFEELLib.INSTANCE.date(dateRequest_.getInputDate())

            // Create map
            val map_: kotlin.collections.MutableMap<String, Any?> = mutableMapOf()
            map_.put("InputDate", inputDate)
            return map_
        }

        @JvmStatic
        fun responseToOutput(dateResponse_: proto.DateResponse): javax.xml.datatype.XMLGregorianCalendar? {
            // Extract and convert output
            return com.gs.dmn.feel.lib.DefaultFEELLib.INSTANCE.date(dateResponse_.getDate())
        }
    }
}
