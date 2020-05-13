
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

    private fun apply(monthlyIncome: java.math.BigDecimal?, monthlyRepayments: java.math.BigDecimal?, monthlyExpenses: java.math.BigDecimal?, riskCategory: String?, requiredMonthlyInstallment: java.math.BigDecimal?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor): Boolean? {
        try {
            // Start BKM 'AffordabilityCalculation'
            val affordabilityCalculationStartTime_ = System.currentTimeMillis()
            val affordabilityCalculationArguments_ = com.gs.dmn.runtime.listener.Arguments()
            affordabilityCalculationArguments_.put("monthlyIncome", monthlyIncome)
            affordabilityCalculationArguments_.put("monthlyRepayments", monthlyRepayments)
            affordabilityCalculationArguments_.put("monthlyExpenses", monthlyExpenses)
            affordabilityCalculationArguments_.put("riskCategory", riskCategory)
            affordabilityCalculationArguments_.put("requiredMonthlyInstallment", requiredMonthlyInstallment)
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, affordabilityCalculationArguments_)

            // Evaluate BKM 'AffordabilityCalculation'
            val output_: Boolean? = evaluate(monthlyIncome, monthlyRepayments, monthlyExpenses, riskCategory, requiredMonthlyInstallment, annotationSet_, eventListener_, externalExecutor_)

            // End BKM 'AffordabilityCalculation'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, affordabilityCalculationArguments_, output_, (System.currentTimeMillis() - affordabilityCalculationStartTime_))

            return output_
        } catch (e: Exception) {
            logError("Exception caught in 'AffordabilityCalculation' evaluation", e)
            return null
        }
    }

    private fun evaluate(monthlyIncome: java.math.BigDecimal?, monthlyRepayments: java.math.BigDecimal?, monthlyExpenses: java.math.BigDecimal?, riskCategory: String?, requiredMonthlyInstallment: java.math.BigDecimal?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor): Boolean? {
        val disposableIncome: java.math.BigDecimal? = numericSubtract(monthlyIncome, numericAdd(monthlyExpenses, monthlyRepayments)) as java.math.BigDecimal?
        val creditContingencyFactor: java.math.BigDecimal? = CreditContingencyFactorTable.CreditContingencyFactorTable(riskCategory, annotationSet_, eventListener_, externalExecutor_) as java.math.BigDecimal?
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

        fun AffordabilityCalculation(monthlyIncome: java.math.BigDecimal?, monthlyRepayments: java.math.BigDecimal?, monthlyExpenses: java.math.BigDecimal?, riskCategory: String?, requiredMonthlyInstallment: java.math.BigDecimal?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor): Boolean? {
            return INSTANCE.apply(monthlyIncome, monthlyRepayments, monthlyExpenses, riskCategory, requiredMonthlyInstallment, annotationSet_, eventListener_, externalExecutor_)
        }
    }
}
