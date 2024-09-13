
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
class InstallmentCalculation : com.gs.dmn.runtime.JavaTimeDMNBaseDecision {
    private constructor() {}

    override fun applyMap(input_: MutableMap<String, String>, context_: com.gs.dmn.runtime.ExecutionContext): kotlin.Number? {
        try {
            return apply(input_.get("ProductType"), input_.get("Rate")?.let({ number(it) }), input_.get("Term")?.let({ number(it) }), input_.get("Amount")?.let({ number(it) }), context_)
        } catch (e: Exception) {
            logError("Cannot apply decision 'InstallmentCalculation'", e)
            return null
        }
    }

    fun apply(productType: String?, rate: kotlin.Number?, term: kotlin.Number?, amount: kotlin.Number?, context_: com.gs.dmn.runtime.ExecutionContext): kotlin.Number? {
        try {
            // Start BKM 'InstallmentCalculation'
            var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
            var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
            var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
            var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
            val installmentCalculationStartTime_ = System.currentTimeMillis()
            val installmentCalculationArguments_ = com.gs.dmn.runtime.listener.Arguments()
            installmentCalculationArguments_.put("ProductType", productType)
            installmentCalculationArguments_.put("Rate", rate)
            installmentCalculationArguments_.put("Term", term)
            installmentCalculationArguments_.put("Amount", amount)
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, installmentCalculationArguments_)

            // Evaluate BKM 'InstallmentCalculation'
            val output_: kotlin.Number? = evaluate(productType, rate, term, amount, context_)

            // End BKM 'InstallmentCalculation'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, installmentCalculationArguments_, output_, (System.currentTimeMillis() - installmentCalculationStartTime_))

            return output_
        } catch (e: Exception) {
            logError("Exception caught in 'InstallmentCalculation' evaluation", e)
            return null
        }
    }

    private inline fun evaluate(productType: String?, rate: kotlin.Number?, term: kotlin.Number?, amount: kotlin.Number?, context_: com.gs.dmn.runtime.ExecutionContext): kotlin.Number? {
        var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
        var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
        var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
        var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
        val monthlyFee: kotlin.Number? = (if (booleanEqual(stringEqual(productType, "STANDARD LOAN"), true)) number("20.00") else (if (booleanEqual(stringEqual(productType, "SPECIAL LOAN"), true)) number("25.00") else null)) as kotlin.Number?
        val monthlyRepayment: kotlin.Number? = numericDivide(numericDivide(numericMultiply(amount, rate), number("12")), numericSubtract(number("1"), numericExponentiation(numericAdd(number("1"), numericDivide(rate, number("12"))), numericUnaryMinus(term)))) as kotlin.Number?
        return numericAdd(monthlyRepayment, monthlyFee)
    }

    companion object {
        val DRG_ELEMENT_METADATA : com.gs.dmn.runtime.listener.DRGElement = com.gs.dmn.runtime.listener.DRGElement(
            "",
            "InstallmentCalculation",
            "",
            com.gs.dmn.runtime.annotation.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
            com.gs.dmn.runtime.annotation.ExpressionKind.CONTEXT,
            com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
            -1
        )

        private val INSTANCE = InstallmentCalculation()

        @JvmStatic
        fun instance(): InstallmentCalculation {
            return INSTANCE
        }
    }
}
