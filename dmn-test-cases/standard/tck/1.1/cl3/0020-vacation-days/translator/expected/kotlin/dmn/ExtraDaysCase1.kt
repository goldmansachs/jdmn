
import java.util.*
import java.util.stream.Collectors

@javax.annotation.Generated(value = ["decision.ftl", "Extra days case 1"])
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "Extra days case 1",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.COLLECT,
    rulesCount = 2
)
class ExtraDaysCase1() : com.gs.dmn.runtime.DefaultDMNBaseDecision() {
    override fun applyMap(input_: MutableMap<String, String>, context_: com.gs.dmn.runtime.ExecutionContext): java.math.BigDecimal? {
        try {
            return apply(input_.get("Age")?.let({ number(it) }), input_.get("Years of Service")?.let({ number(it) }), context_)
        } catch (e: Exception) {
            logError("Cannot apply decision 'ExtraDaysCase1'", e)
            return null
        }
    }

    fun apply(age: java.math.BigDecimal?, yearsOfService: java.math.BigDecimal?, context_: com.gs.dmn.runtime.ExecutionContext): java.math.BigDecimal? {
        try {
            // Start decision 'Extra days case 1'
            var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
            var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
            var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
            var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
            val extraDaysCase1StartTime_ = System.currentTimeMillis()
            val extraDaysCase1Arguments_ = com.gs.dmn.runtime.listener.Arguments()
            extraDaysCase1Arguments_.put("Age", age)
            extraDaysCase1Arguments_.put("Years of Service", yearsOfService)
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, extraDaysCase1Arguments_)

            // Evaluate decision 'Extra days case 1'
            val output_: java.math.BigDecimal? = evaluate(age, yearsOfService, context_)

            // End decision 'Extra days case 1'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, extraDaysCase1Arguments_, output_, (System.currentTimeMillis() - extraDaysCase1StartTime_))

            return output_
        } catch (e: Exception) {
            logError("Exception caught in 'Extra days case 1' evaluation", e)
            return null
        }
    }

    private inline fun evaluate(age: java.math.BigDecimal?, yearsOfService: java.math.BigDecimal?, context_: com.gs.dmn.runtime.ExecutionContext): java.math.BigDecimal? {
        var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
        var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
        var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
        var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
        // Apply rules and collect results
        val ruleOutputList_ = com.gs.dmn.runtime.RuleOutputList()
        ruleOutputList_.add(rule0(age, yearsOfService, context_))
        ruleOutputList_.add(rule1(age, yearsOfService, context_))

        // Return results based on hit policy
        var output_: java.math.BigDecimal?
        if (ruleOutputList_.noMatchedRules()) {
            // Default value
            output_ = number("0")
        } else {
            val ruleOutputs_: List<com.gs.dmn.runtime.RuleOutput> = ruleOutputList_.applyMultiple(com.gs.dmn.runtime.annotation.HitPolicy.COLLECT)
            output_ = max(ruleOutputs_?.map({ o -> (o as ExtraDaysCase1RuleOutput)?.extraDaysCase1 }))
        }

        return output_
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 0, annotation = "")
    private fun rule0(age: java.math.BigDecimal?, yearsOfService: java.math.BigDecimal?, context_: com.gs.dmn.runtime.ExecutionContext): com.gs.dmn.runtime.RuleOutput {
        // Rule metadata
        val drgRuleMetadata: com.gs.dmn.runtime.listener.Rule = com.gs.dmn.runtime.listener.Rule(0, "")

        // Rule start
        var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
        var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
        var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
        var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

        // Apply rule
        var output_: ExtraDaysCase1RuleOutput = ExtraDaysCase1RuleOutput(false)
        if (ruleMatches(eventListener_, drgRuleMetadata,
            booleanOr(numericLessThan(age, number("18")), numericGreaterEqualThan(age, number("60"))),
            true
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

            // Compute output
            output_.setMatched(true)
            output_.extraDaysCase1 = number("5")

            // Add annotation
            annotationSet_.addAnnotation("Extra days case 1", 0, "")
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 1, annotation = "")
    private fun rule1(age: java.math.BigDecimal?, yearsOfService: java.math.BigDecimal?, context_: com.gs.dmn.runtime.ExecutionContext): com.gs.dmn.runtime.RuleOutput {
        // Rule metadata
        val drgRuleMetadata: com.gs.dmn.runtime.listener.Rule = com.gs.dmn.runtime.listener.Rule(1, "")

        // Rule start
        var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
        var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
        var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
        var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

        // Apply rule
        var output_: ExtraDaysCase1RuleOutput = ExtraDaysCase1RuleOutput(false)
        if (ruleMatches(eventListener_, drgRuleMetadata,
            true,
            numericGreaterEqualThan(yearsOfService, number("30"))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

            // Compute output
            output_.setMatched(true)
            output_.extraDaysCase1 = number("5")

            // Add annotation
            annotationSet_.addAnnotation("Extra days case 1", 1, "")
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_
    }


    companion object {
        val DRG_ELEMENT_METADATA : com.gs.dmn.runtime.listener.DRGElement = com.gs.dmn.runtime.listener.DRGElement(
            "",
            "Extra days case 1",
            "",
            com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
            com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
            com.gs.dmn.runtime.annotation.HitPolicy.COLLECT,
            2
        )
    }
}
