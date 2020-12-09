
import java.util.*
import java.util.stream.Collectors

@javax.annotation.Generated(value = ["bkm.ftl", "equity36Mo"])
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "equity36Mo",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
class Equity36Mo : com.gs.dmn.runtime.DefaultDMNBaseDecision {
    private constructor() {}

    private fun apply(p: java.math.BigDecimal?, r: java.math.BigDecimal?, n: java.math.BigDecimal?, pmt: java.math.BigDecimal?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): java.math.BigDecimal? {
        try {
            // Start BKM 'equity36Mo'
            val equity36MoStartTime_ = System.currentTimeMillis()
            val equity36MoArguments_ = com.gs.dmn.runtime.listener.Arguments()
            equity36MoArguments_.put("p", p)
            equity36MoArguments_.put("r", r)
            equity36MoArguments_.put("n", n)
            equity36MoArguments_.put("pmt", pmt)
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, equity36MoArguments_)

            // Evaluate BKM 'equity36Mo'
            val output_: java.math.BigDecimal? = evaluate(p, r, n, pmt, annotationSet_, eventListener_, externalExecutor_, cache_)

            // End BKM 'equity36Mo'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, equity36MoArguments_, output_, (System.currentTimeMillis() - equity36MoStartTime_))

            return output_
        } catch (e: Exception) {
            logError("Exception caught in 'equity36Mo' evaluation", e)
            return null
        }
    }

    private inline fun evaluate(p: java.math.BigDecimal?, r: java.math.BigDecimal?, n: java.math.BigDecimal?, pmt: java.math.BigDecimal?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): java.math.BigDecimal? {
        return numericSubtract(numericMultiply(p, numericExponentiation(numericAdd(number("1"), numericDivide(r, number("12"))), n)), numericDivide(numericMultiply(pmt, numericAdd(numericUnaryMinus(number("1")), numericExponentiation(numericAdd(number("1"), numericDivide(r, number("12"))), n))), r)) as java.math.BigDecimal?
    }

    companion object {
        val DRG_ELEMENT_METADATA = com.gs.dmn.runtime.listener.DRGElement(
            "",
            "equity36Mo",
            "",
            com.gs.dmn.runtime.annotation.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
            com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
            com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
            -1
        )

        val INSTANCE = Equity36Mo()

        fun equity36Mo(p: java.math.BigDecimal?, r: java.math.BigDecimal?, n: java.math.BigDecimal?, pmt: java.math.BigDecimal?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): java.math.BigDecimal? {
            return INSTANCE.apply(p, r, n, pmt, annotationSet_, eventListener_, externalExecutor_, cache_)
        }
    }
}
