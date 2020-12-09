
import java.util.*
import java.util.stream.Collectors

@javax.annotation.Generated(value = ["bkm.ftl", "PMT"])
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "PMT",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
class PMT : com.gs.dmn.runtime.DefaultDMNBaseDecision {
    private constructor() {}

    private fun apply(p: java.math.BigDecimal?, r: java.math.BigDecimal?, n: java.math.BigDecimal?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): java.math.BigDecimal? {
        try {
            // Start BKM 'PMT'
            val pMTStartTime_ = System.currentTimeMillis()
            val pMTArguments_ = com.gs.dmn.runtime.listener.Arguments()
            pMTArguments_.put("p", p)
            pMTArguments_.put("r", r)
            pMTArguments_.put("n", n)
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, pMTArguments_)

            // Evaluate BKM 'PMT'
            val output_: java.math.BigDecimal? = evaluate(p, r, n, annotationSet_, eventListener_, externalExecutor_, cache_)

            // End BKM 'PMT'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, pMTArguments_, output_, (System.currentTimeMillis() - pMTStartTime_))

            return output_
        } catch (e: Exception) {
            logError("Exception caught in 'PMT' evaluation", e)
            return null
        }
    }

    private inline fun evaluate(p: java.math.BigDecimal?, r: java.math.BigDecimal?, n: java.math.BigDecimal?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): java.math.BigDecimal? {
        return numericDivide(numericDivide(numericMultiply(p, r), number("12")), numericSubtract(number("1"), numericExponentiation(numericAdd(number("1"), numericDivide(r, number("12"))), numericUnaryMinus(n)))) as java.math.BigDecimal?
    }

    companion object {
        val DRG_ELEMENT_METADATA = com.gs.dmn.runtime.listener.DRGElement(
            "",
            "PMT",
            "",
            com.gs.dmn.runtime.annotation.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
            com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
            com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
            -1
        )

        val INSTANCE = PMT()

        fun PMT(p: java.math.BigDecimal?, r: java.math.BigDecimal?, n: java.math.BigDecimal?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): java.math.BigDecimal? {
            return INSTANCE.apply(p, r, n, annotationSet_, eventListener_, externalExecutor_, cache_)
        }
    }
}
