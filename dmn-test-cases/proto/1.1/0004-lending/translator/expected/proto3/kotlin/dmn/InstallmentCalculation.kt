
import java.util.*
import java.util.stream.Collectors

@javax.annotation.Generated(value = ["bkm.ftl", "InstallmentCalculation"])
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "InstallmentCalculation",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.CONTEXT,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
class InstallmentCalculation : com.gs.dmn.runtime.DefaultDMNBaseDecision {
    private constructor() {}

    private fun apply(productType: String?, rate: java.math.BigDecimal?, term: java.math.BigDecimal?, amount: java.math.BigDecimal?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): java.math.BigDecimal? {
        try {
            // Start BKM 'InstallmentCalculation'
            val installmentCalculationStartTime_ = System.currentTimeMillis()
            val installmentCalculationArguments_ = com.gs.dmn.runtime.listener.Arguments()
            installmentCalculationArguments_.put("ProductType", productType)
            installmentCalculationArguments_.put("Rate", rate)
            installmentCalculationArguments_.put("Term", term)
            installmentCalculationArguments_.put("Amount", amount)
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, installmentCalculationArguments_)

            // Evaluate BKM 'InstallmentCalculation'
            val output_: java.math.BigDecimal? = evaluate(productType, rate, term, amount, annotationSet_, eventListener_, externalExecutor_, cache_)

            // End BKM 'InstallmentCalculation'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, installmentCalculationArguments_, output_, (System.currentTimeMillis() - installmentCalculationStartTime_))

            return output_
        } catch (e: Exception) {
            logError("Exception caught in 'InstallmentCalculation' evaluation", e)
            return null
        }
    }

    private inline fun evaluate(productType: String?, rate: java.math.BigDecimal?, term: java.math.BigDecimal?, amount: java.math.BigDecimal?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): java.math.BigDecimal? {
        val monthlyFee: java.math.BigDecimal? = (if (booleanEqual(stringEqual(productType, "STANDARD LOAN"), true)) number("20.00") else (if (booleanEqual(stringEqual(productType, "SPECIAL LOAN"), true)) number("25.00") else null)) as java.math.BigDecimal?
        val monthlyRepayment: java.math.BigDecimal? = numericDivide(numericDivide(numericMultiply(amount, rate), number("12")), numericSubtract(number("1"), numericExponentiation(numericAdd(number("1"), numericDivide(rate, number("12"))), numericUnaryMinus(term)))) as java.math.BigDecimal?
        return numericAdd(monthlyRepayment, monthlyFee)
    }

    companion object {
        val DRG_ELEMENT_METADATA = com.gs.dmn.runtime.listener.DRGElement(
            "",
            "InstallmentCalculation",
            "",
            com.gs.dmn.runtime.annotation.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
            com.gs.dmn.runtime.annotation.ExpressionKind.CONTEXT,
            com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
            -1
        )

        val INSTANCE = InstallmentCalculation()

        fun InstallmentCalculation(productType: String?, rate: java.math.BigDecimal?, term: java.math.BigDecimal?, amount: java.math.BigDecimal?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): java.math.BigDecimal? {
            return INSTANCE.apply(productType, rate, term, amount, annotationSet_, eventListener_, externalExecutor_, cache_)
        }
    }
}
