
import java.util.*
import java.util.stream.Collectors

@javax.annotation.Generated(value = ["bkm.ftl", "EligibilityRules"])
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "EligibilityRules",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.PRIORITY,
    rulesCount = 4
)
class EligibilityRules : com.gs.dmn.runtime.DefaultDMNBaseDecision {
    private constructor() {}

    private fun apply(preBureauRiskCategory: String?, preBureauAffordability: Boolean?, age: java.math.BigDecimal?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): String? {
        try {
            // Start BKM 'EligibilityRules'
            val eligibilityRulesStartTime_ = System.currentTimeMillis()
            val eligibilityRulesArguments_ = com.gs.dmn.runtime.listener.Arguments()
            eligibilityRulesArguments_.put("PreBureauRiskCategory", preBureauRiskCategory)
            eligibilityRulesArguments_.put("PreBureauAffordability", preBureauAffordability)
            eligibilityRulesArguments_.put("Age", age)
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, eligibilityRulesArguments_)

            // Evaluate BKM 'EligibilityRules'
            val output_: String? = evaluate(preBureauRiskCategory, preBureauAffordability, age, annotationSet_, eventListener_, externalExecutor_, cache_)

            // End BKM 'EligibilityRules'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, eligibilityRulesArguments_, output_, (System.currentTimeMillis() - eligibilityRulesStartTime_))

            return output_
        } catch (e: Exception) {
            logError("Exception caught in 'EligibilityRules' evaluation", e)
            return null
        }
    }

    private inline fun evaluate(preBureauRiskCategory: String?, preBureauAffordability: Boolean?, age: java.math.BigDecimal?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): String? {
        // Apply rules and collect results
        val ruleOutputList_ = com.gs.dmn.runtime.RuleOutputList()
        ruleOutputList_.add(rule0(preBureauRiskCategory, preBureauAffordability, age, annotationSet_, eventListener_, externalExecutor_))
        ruleOutputList_.add(rule1(preBureauRiskCategory, preBureauAffordability, age, annotationSet_, eventListener_, externalExecutor_))
        ruleOutputList_.add(rule2(preBureauRiskCategory, preBureauAffordability, age, annotationSet_, eventListener_, externalExecutor_))
        ruleOutputList_.add(rule3(preBureauRiskCategory, preBureauAffordability, age, annotationSet_, eventListener_, externalExecutor_))

        // Return results based on hit policy
        var output_: String?
        if (ruleOutputList_.noMatchedRules()) {
            // Default value
            output_ = null
        } else {
            val ruleOutput_: com.gs.dmn.runtime.RuleOutput? = ruleOutputList_.applySingle(com.gs.dmn.runtime.annotation.HitPolicy.PRIORITY)
            output_ = ruleOutput_?.let({ (ruleOutput_ as EligibilityRulesRuleOutput).eligibilityRules })
        }

        return output_
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 0, annotation = "")
    private fun rule0(preBureauRiskCategory: String?, preBureauAffordability: Boolean?, age: java.math.BigDecimal?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor): com.gs.dmn.runtime.RuleOutput {
        // Rule metadata
        val drgRuleMetadata: com.gs.dmn.runtime.listener.Rule = com.gs.dmn.runtime.listener.Rule(0, "")

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

        // Apply rule
        var output_: EligibilityRulesRuleOutput = EligibilityRulesRuleOutput(false)
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (stringEqual(preBureauRiskCategory, "DECLINE")),
            true,
            true
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

            // Compute output
            output_.setMatched(true)
            output_.eligibilityRules = "INELIGIBLE"
            output_.eligibilityRulesPriority = 2

            // Add annotation
            annotationSet_.addAnnotation("EligibilityRules", 0, "")
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 1, annotation = "")
    private fun rule1(preBureauRiskCategory: String?, preBureauAffordability: Boolean?, age: java.math.BigDecimal?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor): com.gs.dmn.runtime.RuleOutput {
        // Rule metadata
        val drgRuleMetadata: com.gs.dmn.runtime.listener.Rule = com.gs.dmn.runtime.listener.Rule(1, "")

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

        // Apply rule
        var output_: EligibilityRulesRuleOutput = EligibilityRulesRuleOutput(false)
        if (ruleMatches(eventListener_, drgRuleMetadata,
            true,
            (booleanEqual(preBureauAffordability, false)),
            true
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

            // Compute output
            output_.setMatched(true)
            output_.eligibilityRules = "INELIGIBLE"
            output_.eligibilityRulesPriority = 2

            // Add annotation
            annotationSet_.addAnnotation("EligibilityRules", 1, "")
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 2, annotation = "")
    private fun rule2(preBureauRiskCategory: String?, preBureauAffordability: Boolean?, age: java.math.BigDecimal?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor): com.gs.dmn.runtime.RuleOutput {
        // Rule metadata
        val drgRuleMetadata: com.gs.dmn.runtime.listener.Rule = com.gs.dmn.runtime.listener.Rule(2, "")

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

        // Apply rule
        var output_: EligibilityRulesRuleOutput = EligibilityRulesRuleOutput(false)
        if (ruleMatches(eventListener_, drgRuleMetadata,
            true,
            true,
            (numericLessThan(age, number("18")))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

            // Compute output
            output_.setMatched(true)
            output_.eligibilityRules = "INELIGIBLE"
            output_.eligibilityRulesPriority = 2

            // Add annotation
            annotationSet_.addAnnotation("EligibilityRules", 2, "")
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 3, annotation = "")
    private fun rule3(preBureauRiskCategory: String?, preBureauAffordability: Boolean?, age: java.math.BigDecimal?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor): com.gs.dmn.runtime.RuleOutput {
        // Rule metadata
        val drgRuleMetadata: com.gs.dmn.runtime.listener.Rule = com.gs.dmn.runtime.listener.Rule(3, "")

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

        // Apply rule
        var output_: EligibilityRulesRuleOutput = EligibilityRulesRuleOutput(false)
        if (ruleMatches(eventListener_, drgRuleMetadata,
            true,
            true,
            true
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

            // Compute output
            output_.setMatched(true)
            output_.eligibilityRules = "ELIGIBLE"
            output_.eligibilityRulesPriority = 1

            // Add annotation
            annotationSet_.addAnnotation("EligibilityRules", 3, "")
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_
    }


    companion object {
        val DRG_ELEMENT_METADATA = com.gs.dmn.runtime.listener.DRGElement(
            "",
            "EligibilityRules",
            "",
            com.gs.dmn.runtime.annotation.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
            com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
            com.gs.dmn.runtime.annotation.HitPolicy.PRIORITY,
            4
        )

        val INSTANCE = EligibilityRules()

        fun EligibilityRules(preBureauRiskCategory: String?, preBureauAffordability: Boolean?, age: java.math.BigDecimal?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): String? {
            return INSTANCE.apply(preBureauRiskCategory, preBureauAffordability, age, annotationSet_, eventListener_, externalExecutor_, cache_)
        }
    }
}
