
import java.util.*
import java.util.stream.Collectors

@javax.annotation.Generated(value = ["bkm.ftl", "gtTen"])
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "gtTen",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
class GtTen : com.gs.dmn.runtime.DefaultDMNBaseDecision {
    private constructor() {}

    private fun apply(theNumber: java.math.BigDecimal?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): Boolean? {
        try {
            // Start BKM 'gtTen'
            val gtTenStartTime_ = System.currentTimeMillis()
            val gtTenArguments_ = com.gs.dmn.runtime.listener.Arguments()
            gtTenArguments_.put("theNumber", theNumber)
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, gtTenArguments_)

            // Evaluate BKM 'gtTen'
            val output_: Boolean? = evaluate(theNumber, annotationSet_, eventListener_, externalExecutor_, cache_)

            // End BKM 'gtTen'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, gtTenArguments_, output_, (System.currentTimeMillis() - gtTenStartTime_))

            return output_
        } catch (e: Exception) {
            logError("Exception caught in 'gtTen' evaluation", e)
            return null
        }
    }

    private inline fun evaluate(theNumber: java.math.BigDecimal?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): Boolean? {
        return numericGreaterThan(theNumber, number("10")) as Boolean?
    }

    companion object {
        val DRG_ELEMENT_METADATA = com.gs.dmn.runtime.listener.DRGElement(
            "",
            "gtTen",
            "",
            com.gs.dmn.runtime.annotation.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
            com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
            com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
            -1
        )

        val INSTANCE = GtTen()

        fun gtTen(theNumber: java.math.BigDecimal?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): Boolean? {
            return INSTANCE.apply(theNumber, annotationSet_, eventListener_, externalExecutor_, cache_)
        }
    }
}
