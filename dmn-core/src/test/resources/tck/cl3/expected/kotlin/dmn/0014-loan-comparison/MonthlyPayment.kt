
import java.util.*
import java.util.stream.Collectors

@javax.annotation.Generated(value = ["bkm.ftl", "monthlyPayment"])
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "monthlyPayment",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
class MonthlyPayment : com.gs.dmn.runtime.DefaultDMNBaseDecision {
    private constructor() {}

    private fun apply(p: java.math.BigDecimal?, r: java.math.BigDecimal?, n: java.math.BigDecimal?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): java.math.BigDecimal? {
        try {
            // Start BKM 'monthlyPayment'
            val monthlyPaymentStartTime_ = System.currentTimeMillis()
            val monthlyPaymentArguments_ = com.gs.dmn.runtime.listener.Arguments()
            monthlyPaymentArguments_.put("p", p)
            monthlyPaymentArguments_.put("r", r)
            monthlyPaymentArguments_.put("n", n)
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, monthlyPaymentArguments_)

            // Evaluate BKM 'monthlyPayment'
            val output_: java.math.BigDecimal? = evaluate(p, r, n, annotationSet_, eventListener_, externalExecutor_, cache_)

            // End BKM 'monthlyPayment'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, monthlyPaymentArguments_, output_, (System.currentTimeMillis() - monthlyPaymentStartTime_))

            return output_
        } catch (e: Exception) {
            logError("Exception caught in 'monthlyPayment' evaluation", e)
            return null
        }
    }

    private inline fun evaluate(p: java.math.BigDecimal?, r: java.math.BigDecimal?, n: java.math.BigDecimal?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): java.math.BigDecimal? {
        return numericDivide(numericDivide(numericMultiply(p, r), number("12")), numericSubtract(number("1"), numericExponentiation(numericAdd(number("1"), numericDivide(r, number("12"))), numericUnaryMinus(n)))) as java.math.BigDecimal?
    }

    companion object {
        val DRG_ELEMENT_METADATA = com.gs.dmn.runtime.listener.DRGElement(
            "",
            "monthlyPayment",
            "",
            com.gs.dmn.runtime.annotation.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
            com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
            com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
            -1
        )

        val INSTANCE = MonthlyPayment()

        fun monthlyPayment(p: java.math.BigDecimal?, r: java.math.BigDecimal?, n: java.math.BigDecimal?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): java.math.BigDecimal? {
            return INSTANCE.apply(p, r, n, annotationSet_, eventListener_, externalExecutor_, cache_)
        }
    }
}
