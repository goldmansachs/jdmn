
import java.util.*
import java.util.stream.Collectors

@javax.annotation.Generated(value = ["bkm.ftl", "Circumference"])
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "Circumference",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
class Circumference : com.gs.dmn.runtime.DefaultDMNBaseDecision {
    private constructor() {}

    private fun apply(radius: java.math.BigDecimal?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): java.math.BigDecimal? {
        try {
            // Start BKM 'Circumference'
            val circumferenceStartTime_ = System.currentTimeMillis()
            val circumferenceArguments_ = com.gs.dmn.runtime.listener.Arguments()
            circumferenceArguments_.put("radius", radius)
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, circumferenceArguments_)

            // Evaluate BKM 'Circumference'
            val output_: java.math.BigDecimal? = evaluate(radius, annotationSet_, eventListener_, externalExecutor_, cache_)

            // End BKM 'Circumference'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, circumferenceArguments_, output_, (System.currentTimeMillis() - circumferenceStartTime_))

            return output_
        } catch (e: Exception) {
            logError("Exception caught in 'Circumference' evaluation", e)
            return null
        }
    }

    private inline fun evaluate(radius: java.math.BigDecimal?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): java.math.BigDecimal? {
        return numericMultiply(numericMultiply(number("2"), number("3.141592")), radius) as java.math.BigDecimal?
    }

    companion object {
        val DRG_ELEMENT_METADATA = com.gs.dmn.runtime.listener.DRGElement(
            "",
            "Circumference",
            "",
            com.gs.dmn.runtime.annotation.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
            com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
            com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
            -1
        )

        val INSTANCE = Circumference()

        fun Circumference(radius: java.math.BigDecimal?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): java.math.BigDecimal? {
            return INSTANCE.apply(radius, annotationSet_, eventListener_, externalExecutor_, cache_)
        }
    }
}
