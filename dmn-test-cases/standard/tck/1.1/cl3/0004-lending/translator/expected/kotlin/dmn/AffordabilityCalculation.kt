
import java.util.*
import java.util.stream.Collectors

@javax.annotation.Generated(value = ["bkm.ftl", "AffordabilityCalculation"])
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "http://www.trisotech.com/definitions/_4e0f0b70-d31c-471c-bd52-5ca709ed362b",
    name = "AffordabilityCalculation",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.CONTEXT,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
class AffordabilityCalculation : com.gs.dmn.runtime.JavaTimeDMNBaseDecision<Boolean?> {
    private constructor() {}

    override fun applyMap(input_: MutableMap<String, String>, context_: com.gs.dmn.runtime.ExecutionContext): Boolean? {
        try {
            return apply(input_.get("MonthlyIncome")?.let({ number(it) }), input_.get("MonthlyRepayments")?.let({ number(it) }), input_.get("MonthlyExpenses")?.let({ number(it) }), input_.get("RiskCategory"), input_.get("RequiredMonthlyInstallment")?.let({ number(it) }), context_)
        } catch (e: Exception) {
            logError("Cannot apply decision 'AffordabilityCalculation'", e)
            return null
        }
    }

    fun apply(monthlyIncome: kotlin.Number?, monthlyRepayments: kotlin.Number?, monthlyExpenses: kotlin.Number?, riskCategory: String?, requiredMonthlyInstallment: kotlin.Number?, context_: com.gs.dmn.runtime.ExecutionContext): Boolean? {
        try {
            // Start BKM 'AffordabilityCalculation'
            var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
            var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
            var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
            var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
            val affordabilityCalculationStartTime_ = System.currentTimeMillis()
            val affordabilityCalculationArguments_ = com.gs.dmn.runtime.listener.Arguments()
            affordabilityCalculationArguments_.put("MonthlyIncome", monthlyIncome)
            affordabilityCalculationArguments_.put("MonthlyRepayments", monthlyRepayments)
            affordabilityCalculationArguments_.put("MonthlyExpenses", monthlyExpenses)
            affordabilityCalculationArguments_.put("RiskCategory", riskCategory)
            affordabilityCalculationArguments_.put("RequiredMonthlyInstallment", requiredMonthlyInstallment)
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, affordabilityCalculationArguments_)

            // Evaluate BKM 'AffordabilityCalculation'
            val output_: Boolean? = evaluate(monthlyIncome, monthlyRepayments, monthlyExpenses, riskCategory, requiredMonthlyInstallment, context_)

            // End BKM 'AffordabilityCalculation'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, affordabilityCalculationArguments_, output_, (System.currentTimeMillis() - affordabilityCalculationStartTime_))

            return output_
        } catch (e: Exception) {
            logError("Exception caught in 'AffordabilityCalculation' evaluation", e)
            return null
        }
    }

    private inline fun evaluate(monthlyIncome: kotlin.Number?, monthlyRepayments: kotlin.Number?, monthlyExpenses: kotlin.Number?, riskCategory: String?, requiredMonthlyInstallment: kotlin.Number?, context_: com.gs.dmn.runtime.ExecutionContext): Boolean? {
        var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
        var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
        var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
        var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
        val disposableIncome: kotlin.Number? = numericSubtract(monthlyIncome, numericAdd(monthlyExpenses, monthlyRepayments)) as kotlin.Number?
        val creditContingencyFactor: kotlin.Number? = CreditContingencyFactorTable.instance().apply(riskCategory, context_) as kotlin.Number?
        val affordability: Boolean? = (if (booleanEqual(numericGreaterThan(numericMultiply(disposableIncome, creditContingencyFactor), requiredMonthlyInstallment), true)) true else false) as Boolean?
        return affordability
    }

    companion object {
        val DRG_ELEMENT_METADATA : com.gs.dmn.runtime.listener.DRGElement = com.gs.dmn.runtime.listener.DRGElement(
            "",
            "AffordabilityCalculation",
            "",
            com.gs.dmn.runtime.annotation.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
            com.gs.dmn.runtime.annotation.ExpressionKind.CONTEXT,
            com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
            -1
        )

        private val INSTANCE = AffordabilityCalculation()

        @JvmStatic
        fun instance(): AffordabilityCalculation {
            return INSTANCE
        }
    }
}
