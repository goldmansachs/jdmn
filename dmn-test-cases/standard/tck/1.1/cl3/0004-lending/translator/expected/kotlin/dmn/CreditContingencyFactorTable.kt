
import java.util.*
import java.util.stream.Collectors

@javax.annotation.Generated(value = ["bkm.ftl", "CreditContingencyFactorTable"])
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "http://www.trisotech.com/definitions/_4e0f0b70-d31c-471c-bd52-5ca709ed362b",
    name = "CreditContingencyFactorTable",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNIQUE,
    rulesCount = 3
)
class CreditContingencyFactorTable : com.gs.dmn.runtime.JavaTimeDMNBaseDecision<kotlin.Number?> {
    private constructor() {}

    override fun applyMap(input_: MutableMap<String, String>, context_: com.gs.dmn.runtime.ExecutionContext): kotlin.Number? {
        try {
            return apply(input_.get("RiskCategory"), context_)
        } catch (e: Exception) {
            logError("Cannot apply decision 'CreditContingencyFactorTable'", e)
            return null
        }
    }

    fun apply(riskCategory: String?, context_: com.gs.dmn.runtime.ExecutionContext): kotlin.Number? {
        try {
            // Start BKM 'CreditContingencyFactorTable'
            var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
            var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
            var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
            var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
            val creditContingencyFactorTableStartTime_ = System.currentTimeMillis()
            val creditContingencyFactorTableArguments_ = com.gs.dmn.runtime.listener.Arguments()
            creditContingencyFactorTableArguments_.put("RiskCategory", riskCategory)
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, creditContingencyFactorTableArguments_)

            // Evaluate BKM 'CreditContingencyFactorTable'
            val output_: kotlin.Number? = evaluate(riskCategory, context_)

            // End BKM 'CreditContingencyFactorTable'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, creditContingencyFactorTableArguments_, output_, (System.currentTimeMillis() - creditContingencyFactorTableStartTime_))

            return output_
        } catch (e: Exception) {
            logError("Exception caught in 'CreditContingencyFactorTable' evaluation", e)
            return null
        }
    }

    private inline fun evaluate(riskCategory: String?, context_: com.gs.dmn.runtime.ExecutionContext): kotlin.Number? {
        var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
        var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
        var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
        var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
        // Apply rules and collect results
        val ruleOutputList_ = com.gs.dmn.runtime.RuleOutputList()
        ruleOutputList_.add(rule0(riskCategory, context_))
        ruleOutputList_.add(rule1(riskCategory, context_))
        ruleOutputList_.add(rule2(riskCategory, context_))

        // Return results based on hit policy
        var output_: kotlin.Number?
        if (ruleOutputList_.noMatchedRules()) {
            // Default value
            output_ = null
        } else {
            val ruleOutput_: com.gs.dmn.runtime.RuleOutput? = ruleOutputList_.applySingle(com.gs.dmn.runtime.annotation.HitPolicy.UNIQUE)
            output_ = ruleOutput_?.let({ (ruleOutput_ as CreditContingencyFactorTableRuleOutput).creditContingencyFactorTable })
        }

        return output_
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 0, annotation = "")
    private fun rule0(riskCategory: String?, context_: com.gs.dmn.runtime.ExecutionContext): com.gs.dmn.runtime.RuleOutput {
        // Rule metadata
        val drgRuleMetadata: com.gs.dmn.runtime.listener.Rule = com.gs.dmn.runtime.listener.Rule(0, "")

        // Rule start
        var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
        var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
        var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
        var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

        // Apply rule
        var output_: CreditContingencyFactorTableRuleOutput = CreditContingencyFactorTableRuleOutput(false)
        if (ruleMatches(eventListener_, drgRuleMetadata,
            booleanOr(stringEqual(riskCategory, "HIGH"), stringEqual(riskCategory, "DECLINE"))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

            // Compute output
            output_.setMatched(true)
            output_.creditContingencyFactorTable = number("0.6")
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 1, annotation = "")
    private fun rule1(riskCategory: String?, context_: com.gs.dmn.runtime.ExecutionContext): com.gs.dmn.runtime.RuleOutput {
        // Rule metadata
        val drgRuleMetadata: com.gs.dmn.runtime.listener.Rule = com.gs.dmn.runtime.listener.Rule(1, "")

        // Rule start
        var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
        var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
        var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
        var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

        // Apply rule
        var output_: CreditContingencyFactorTableRuleOutput = CreditContingencyFactorTableRuleOutput(false)
        if (ruleMatches(eventListener_, drgRuleMetadata,
            stringEqual(riskCategory, "MEDIUM")
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

            // Compute output
            output_.setMatched(true)
            output_.creditContingencyFactorTable = number("0.7")
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 2, annotation = "")
    private fun rule2(riskCategory: String?, context_: com.gs.dmn.runtime.ExecutionContext): com.gs.dmn.runtime.RuleOutput {
        // Rule metadata
        val drgRuleMetadata: com.gs.dmn.runtime.listener.Rule = com.gs.dmn.runtime.listener.Rule(2, "")

        // Rule start
        var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
        var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
        var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
        var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

        // Apply rule
        var output_: CreditContingencyFactorTableRuleOutput = CreditContingencyFactorTableRuleOutput(false)
        if (ruleMatches(eventListener_, drgRuleMetadata,
            booleanOr(stringEqual(riskCategory, "LOW"), stringEqual(riskCategory, "VERY LOW"))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

            // Compute output
            output_.setMatched(true)
            output_.creditContingencyFactorTable = number("0.8")
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_
    }


    companion object {
        val DRG_ELEMENT_METADATA : com.gs.dmn.runtime.listener.DRGElement = com.gs.dmn.runtime.listener.DRGElement(
            "",
            "CreditContingencyFactorTable",
            "",
            com.gs.dmn.runtime.annotation.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
            com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
            com.gs.dmn.runtime.annotation.HitPolicy.UNIQUE,
            3
        )

        private val INSTANCE = CreditContingencyFactorTable()

        @JvmStatic
        fun instance(): CreditContingencyFactorTable {
            return INSTANCE
        }
    }
}
