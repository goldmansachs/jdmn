
import java.util.*
import java.util.stream.Collectors

@javax.annotation.Generated(value = ["bkm.ftl", "FACT"])
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "FACT",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
class FACT : com.gs.dmn.runtime.DefaultDMNBaseDecision {
    private constructor() {}

    private fun apply(n: java.math.BigDecimal?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): java.math.BigDecimal? {
        try {
            // Start BKM 'FACT'
            val fACTStartTime_ = System.currentTimeMillis()
            val fACTArguments_ = com.gs.dmn.runtime.listener.Arguments()
            fACTArguments_.put("n", n)
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, fACTArguments_)

            // Evaluate BKM 'FACT'
            val output_: java.math.BigDecimal? = evaluate(n, annotationSet_, eventListener_, externalExecutor_, cache_)

            // End BKM 'FACT'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, fACTArguments_, output_, (System.currentTimeMillis() - fACTStartTime_))

            return output_
        } catch (e: Exception) {
            logError("Exception caught in 'FACT' evaluation", e)
            return null
        }
    }

    private inline fun evaluate(n: java.math.BigDecimal?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): java.math.BigDecimal? {
        return (if (booleanEqual(numericLessThan(n, number("0")), true)) null else (if (booleanEqual(numericEqual(n, number("0")), true)) number("1") else numericMultiply(n, FACT.FACT(numericSubtract(n, number("1")), annotationSet_, eventListener_, externalExecutor_, cache_)))) as java.math.BigDecimal?
    }

    companion object {
        val DRG_ELEMENT_METADATA = com.gs.dmn.runtime.listener.DRGElement(
            "",
            "FACT",
            "",
            com.gs.dmn.runtime.annotation.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
            com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
            com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
            -1
        )

        val INSTANCE = FACT()

        fun FACT(n: java.math.BigDecimal?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): java.math.BigDecimal? {
            return INSTANCE.apply(n, annotationSet_, eventListener_, externalExecutor_, cache_)
        }
    }
}
