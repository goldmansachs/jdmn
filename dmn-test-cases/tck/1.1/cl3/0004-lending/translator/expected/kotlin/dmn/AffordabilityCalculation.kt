
import java.util.*
import java.util.stream.Collectors

@javax.annotation.Generated(value = ["bkm.ftl", "AffordabilityCalculation"])
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "AffordabilityCalculation",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.CONTEXT,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
class AffordabilityCalculation : com.gs.dmn.runtime.DefaultDMNBaseDecision {
    private constructor() {}

    private fun apply(monthlyIncome: java.math.BigDecimal?, monthlyRepayments: java.math.BigDecimal?, monthlyExpenses: java.math.BigDecimal?, riskCategory: String?, requiredMonthlyInstallment: java.math.BigDecimal?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): Boolean? {
        try {
            // Start BKM 'AffordabilityCalculation'
            val affordabilityCalculationStartTime_ = System.currentTimeMillis()
            val affordabilityCalculationArguments_ = com.gs.dmn.runtime.listener.Arguments()
            affordabilityCalculationArguments_.put("MonthlyIncome", monthlyIncome)
            affordabilityCalculationArguments_.put("MonthlyRepayments", monthlyRepayments)
            affordabilityCalculationArguments_.put("MonthlyExpenses", monthlyExpenses)
            affordabilityCalculationArguments_.put("RiskCategory", riskCategory)
            affordabilityCalculationArguments_.put("RequiredMonthlyInstallment", requiredMonthlyInstallment)
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, affordabilityCalculationArguments_)

            // Evaluate BKM 'AffordabilityCalculation'
            val output_: Boolean? = evaluate(monthlyIncome, monthlyRepayments, monthlyExpenses, riskCategory, requiredMonthlyInstallment, annotationSet_, eventListener_, externalExecutor_, cache_)

            // End BKM 'AffordabilityCalculation'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, affordabilityCalculationArguments_, output_, (System.currentTimeMillis() - affordabilityCalculationStartTime_))

            return output_
        } catch (e: Exception) {
            logError("Exception caught in 'AffordabilityCalculation' evaluation", e)
            return null
        }
    }

    private inline fun evaluate(monthlyIncome: java.math.BigDecimal?, monthlyRepayments: java.math.BigDecimal?, monthlyExpenses: java.math.BigDecimal?, riskCategory: String?, requiredMonthlyInstallment: java.math.BigDecimal?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): Boolean? {
        val disposableIncome: java.math.BigDecimal? = numericSubtract(monthlyIncome, numericAdd(monthlyExpenses, monthlyRepayments)) as java.math.BigDecimal?
        val creditContingencyFactor: java.math.BigDecimal? = CreditContingencyFactorTable.CreditContingencyFactorTable(riskCategory, annotationSet_, eventListener_, externalExecutor_, cache_) as java.math.BigDecimal?
        val affordability: Boolean? = (if (booleanEqual(numericGreaterThan(numericMultiply(disposableIncome, creditContingencyFactor), requiredMonthlyInstallment), true)) true else false) as Boolean?
        return affordability
    }

    companion object {
        val DRG_ELEMENT_METADATA = com.gs.dmn.runtime.listener.DRGElement(
            "",
            "AffordabilityCalculation",
            "",
            com.gs.dmn.runtime.annotation.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
            com.gs.dmn.runtime.annotation.ExpressionKind.CONTEXT,
            com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
            -1
        )

        val INSTANCE = AffordabilityCalculation()

        fun AffordabilityCalculation(monthlyIncome: java.math.BigDecimal?, monthlyRepayments: java.math.BigDecimal?, monthlyExpenses: java.math.BigDecimal?, riskCategory: String?, requiredMonthlyInstallment: java.math.BigDecimal?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): Boolean? {
            return INSTANCE.apply(monthlyIncome, monthlyRepayments, monthlyExpenses, riskCategory, requiredMonthlyInstallment, annotationSet_, eventListener_, externalExecutor_, cache_)
        }
    }
}
