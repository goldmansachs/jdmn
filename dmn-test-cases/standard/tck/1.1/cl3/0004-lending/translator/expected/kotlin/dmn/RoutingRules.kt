
import java.util.*
import java.util.stream.Collectors

@jakarta.annotation.Generated(value = ["bkm.ftl", "RoutingRules"])
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "RoutingRules",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.PRIORITY,
    rulesCount = 5
)
class RoutingRules : com.gs.dmn.runtime.DefaultDMNBaseDecision {
    private constructor() {}

    override fun applyMap(input_: MutableMap<String, String>, context_: com.gs.dmn.runtime.ExecutionContext): String? {
        try {
            return apply(input_.get("Post-bureauRiskCategory"), input_.get("Post-bureauAffordability")?.let({ it.toBoolean() }), input_.get("Bankrupt")?.let({ it.toBoolean() }), input_.get("CreditScore")?.let({ number(it) }), context_)
        } catch (e: Exception) {
            logError("Cannot apply decision 'RoutingRules'", e)
            return null
        }
    }

    fun apply(postBureauRiskCategory: String?, postBureauAffordability: Boolean?, bankrupt: Boolean?, creditScore: java.math.BigDecimal?, context_: com.gs.dmn.runtime.ExecutionContext): String? {
        try {
            // Start BKM 'RoutingRules'
            var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
            var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
            var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
            var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
            val routingRulesStartTime_ = System.currentTimeMillis()
            val routingRulesArguments_ = com.gs.dmn.runtime.listener.Arguments()
            routingRulesArguments_.put("Post-bureauRiskCategory", postBureauRiskCategory)
            routingRulesArguments_.put("Post-bureauAffordability", postBureauAffordability)
            routingRulesArguments_.put("Bankrupt", bankrupt)
            routingRulesArguments_.put("CreditScore", creditScore)
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, routingRulesArguments_)

            // Evaluate BKM 'RoutingRules'
            val output_: String? = evaluate(postBureauRiskCategory, postBureauAffordability, bankrupt, creditScore, context_)

            // End BKM 'RoutingRules'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, routingRulesArguments_, output_, (System.currentTimeMillis() - routingRulesStartTime_))

            return output_
        } catch (e: Exception) {
            logError("Exception caught in 'RoutingRules' evaluation", e)
            return null
        }
    }

    private inline fun evaluate(postBureauRiskCategory: String?, postBureauAffordability: Boolean?, bankrupt: Boolean?, creditScore: java.math.BigDecimal?, context_: com.gs.dmn.runtime.ExecutionContext): String? {
        var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
        var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
        var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
        var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
        // Apply rules and collect results
        val ruleOutputList_ = com.gs.dmn.runtime.RuleOutputList()
        ruleOutputList_.add(rule0(postBureauRiskCategory, postBureauAffordability, bankrupt, creditScore, context_))
        ruleOutputList_.add(rule1(postBureauRiskCategory, postBureauAffordability, bankrupt, creditScore, context_))
        ruleOutputList_.add(rule2(postBureauRiskCategory, postBureauAffordability, bankrupt, creditScore, context_))
        ruleOutputList_.add(rule3(postBureauRiskCategory, postBureauAffordability, bankrupt, creditScore, context_))
        ruleOutputList_.add(rule4(postBureauRiskCategory, postBureauAffordability, bankrupt, creditScore, context_))

        // Return results based on hit policy
        var output_: String?
        if (ruleOutputList_.noMatchedRules()) {
            // Default value
            output_ = null
        } else {
            val ruleOutput_: com.gs.dmn.runtime.RuleOutput? = ruleOutputList_.applySingle(com.gs.dmn.runtime.annotation.HitPolicy.PRIORITY)
            output_ = ruleOutput_?.let({ (ruleOutput_ as RoutingRulesRuleOutput).routingRules })
        }

        return output_
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 0, annotation = "")
    private fun rule0(postBureauRiskCategory: String?, postBureauAffordability: Boolean?, bankrupt: Boolean?, creditScore: java.math.BigDecimal?, context_: com.gs.dmn.runtime.ExecutionContext): com.gs.dmn.runtime.RuleOutput {
        // Rule metadata
        val drgRuleMetadata: com.gs.dmn.runtime.listener.Rule = com.gs.dmn.runtime.listener.Rule(0, "")

        // Rule start
        var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
        var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
        var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
        var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

        // Apply rule
        var output_: RoutingRulesRuleOutput = RoutingRulesRuleOutput(false)
        if (ruleMatches(eventListener_, drgRuleMetadata,
            true,
            booleanEqual(postBureauAffordability, false),
            true,
            true
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

            // Compute output
            output_.setMatched(true)
            output_.routingRules = "DECLINE"
            output_.routingRulesPriority = 3

            // Add annotation
            annotationSet_.addAnnotation("RoutingRules", 0, "")
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 1, annotation = "")
    private fun rule1(postBureauRiskCategory: String?, postBureauAffordability: Boolean?, bankrupt: Boolean?, creditScore: java.math.BigDecimal?, context_: com.gs.dmn.runtime.ExecutionContext): com.gs.dmn.runtime.RuleOutput {
        // Rule metadata
        val drgRuleMetadata: com.gs.dmn.runtime.listener.Rule = com.gs.dmn.runtime.listener.Rule(1, "")

        // Rule start
        var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
        var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
        var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
        var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

        // Apply rule
        var output_: RoutingRulesRuleOutput = RoutingRulesRuleOutput(false)
        if (ruleMatches(eventListener_, drgRuleMetadata,
            true,
            true,
            booleanEqual(bankrupt, true),
            true
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

            // Compute output
            output_.setMatched(true)
            output_.routingRules = "DECLINE"
            output_.routingRulesPriority = 3

            // Add annotation
            annotationSet_.addAnnotation("RoutingRules", 1, "")
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 2, annotation = "")
    private fun rule2(postBureauRiskCategory: String?, postBureauAffordability: Boolean?, bankrupt: Boolean?, creditScore: java.math.BigDecimal?, context_: com.gs.dmn.runtime.ExecutionContext): com.gs.dmn.runtime.RuleOutput {
        // Rule metadata
        val drgRuleMetadata: com.gs.dmn.runtime.listener.Rule = com.gs.dmn.runtime.listener.Rule(2, "")

        // Rule start
        var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
        var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
        var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
        var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

        // Apply rule
        var output_: RoutingRulesRuleOutput = RoutingRulesRuleOutput(false)
        if (ruleMatches(eventListener_, drgRuleMetadata,
            stringEqual(postBureauRiskCategory, "HIGH"),
            true,
            true,
            true
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

            // Compute output
            output_.setMatched(true)
            output_.routingRules = "REFER"
            output_.routingRulesPriority = 2

            // Add annotation
            annotationSet_.addAnnotation("RoutingRules", 2, "")
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 3, annotation = "")
    private fun rule3(postBureauRiskCategory: String?, postBureauAffordability: Boolean?, bankrupt: Boolean?, creditScore: java.math.BigDecimal?, context_: com.gs.dmn.runtime.ExecutionContext): com.gs.dmn.runtime.RuleOutput {
        // Rule metadata
        val drgRuleMetadata: com.gs.dmn.runtime.listener.Rule = com.gs.dmn.runtime.listener.Rule(3, "")

        // Rule start
        var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
        var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
        var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
        var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

        // Apply rule
        var output_: RoutingRulesRuleOutput = RoutingRulesRuleOutput(false)
        if (ruleMatches(eventListener_, drgRuleMetadata,
            true,
            true,
            true,
            numericLessThan(creditScore, number("580"))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

            // Compute output
            output_.setMatched(true)
            output_.routingRules = "REFER"
            output_.routingRulesPriority = 2

            // Add annotation
            annotationSet_.addAnnotation("RoutingRules", 3, "")
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 4, annotation = "")
    private fun rule4(postBureauRiskCategory: String?, postBureauAffordability: Boolean?, bankrupt: Boolean?, creditScore: java.math.BigDecimal?, context_: com.gs.dmn.runtime.ExecutionContext): com.gs.dmn.runtime.RuleOutput {
        // Rule metadata
        val drgRuleMetadata: com.gs.dmn.runtime.listener.Rule = com.gs.dmn.runtime.listener.Rule(4, "")

        // Rule start
        var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
        var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
        var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
        var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

        // Apply rule
        var output_: RoutingRulesRuleOutput = RoutingRulesRuleOutput(false)
        if (ruleMatches(eventListener_, drgRuleMetadata,
            true,
            true,
            true,
            true
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

            // Compute output
            output_.setMatched(true)
            output_.routingRules = "ACCEPT"
            output_.routingRulesPriority = 1

            // Add annotation
            annotationSet_.addAnnotation("RoutingRules", 4, "")
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_
    }


    companion object {
        val DRG_ELEMENT_METADATA : com.gs.dmn.runtime.listener.DRGElement = com.gs.dmn.runtime.listener.DRGElement(
            "",
            "RoutingRules",
            "",
            com.gs.dmn.runtime.annotation.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
            com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
            com.gs.dmn.runtime.annotation.HitPolicy.PRIORITY,
            5
        )

        private val INSTANCE = RoutingRules()

        @JvmStatic
        fun instance(): RoutingRules {
            return INSTANCE
        }
    }
}
