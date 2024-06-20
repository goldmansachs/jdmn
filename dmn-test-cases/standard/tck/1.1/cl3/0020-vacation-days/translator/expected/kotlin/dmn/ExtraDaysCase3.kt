
import java.util.*
import java.util.stream.Collectors

@javax.annotation.Generated(value = ["decision.ftl", "Extra days case 3"])
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "Extra days case 3",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.COLLECT,
    rulesCount = 2
)
class ExtraDaysCase3() : com.gs.dmn.runtime.JavaTimeDMNBaseDecision() {
    override fun applyMap(input_: MutableMap<String, String>, context_: com.gs.dmn.runtime.ExecutionContext): java.lang.Number? {
        try {
            return apply(input_.get("Age")?.let({ number(it) }), input_.get("Years of Service")?.let({ number(it) }), context_)
        } catch (e: Exception) {
            logError("Cannot apply decision 'ExtraDaysCase3'", e)
            return null
        }
    }

    fun apply(age: java.lang.Number?, yearsOfService: java.lang.Number?, context_: com.gs.dmn.runtime.ExecutionContext): java.lang.Number? {
        try {
            // Start decision 'Extra days case 3'
            var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
            var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
            var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
            var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
            val extraDaysCase3StartTime_ = System.currentTimeMillis()
            val extraDaysCase3Arguments_ = com.gs.dmn.runtime.listener.Arguments()
            extraDaysCase3Arguments_.put("Age", age)
            extraDaysCase3Arguments_.put("Years of Service", yearsOfService)
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, extraDaysCase3Arguments_)

            // Evaluate decision 'Extra days case 3'
            val output_: java.lang.Number? = evaluate(age, yearsOfService, context_)

            // End decision 'Extra days case 3'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, extraDaysCase3Arguments_, output_, (System.currentTimeMillis() - extraDaysCase3StartTime_))

            return output_
        } catch (e: Exception) {
            logError("Exception caught in 'Extra days case 3' evaluation", e)
            return null
        }
    }

    private inline fun evaluate(age: java.lang.Number?, yearsOfService: java.lang.Number?, context_: com.gs.dmn.runtime.ExecutionContext): java.lang.Number? {
        var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
        var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
        var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
        var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
        // Apply rules and collect results
        val ruleOutputList_ = com.gs.dmn.runtime.RuleOutputList()
        ruleOutputList_.add(rule0(age, yearsOfService, context_))
        ruleOutputList_.add(rule1(age, yearsOfService, context_))

        // Return results based on hit policy
        var output_: java.lang.Number?
        if (ruleOutputList_.noMatchedRules()) {
            // Default value
            output_ = number("0")
        } else {
            val ruleOutputs_: List<com.gs.dmn.runtime.RuleOutput> = ruleOutputList_.applyMultiple(com.gs.dmn.runtime.annotation.HitPolicy.COLLECT)
            output_ = max(ruleOutputs_?.map({ o -> (o as ExtraDaysCase3RuleOutput)?.extraDaysCase3 }))
        }

        return output_
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 0, annotation = "")
    private fun rule0(age: java.lang.Number?, yearsOfService: java.lang.Number?, context_: com.gs.dmn.runtime.ExecutionContext): com.gs.dmn.runtime.RuleOutput {
        // Rule metadata
        val drgRuleMetadata: com.gs.dmn.runtime.listener.Rule = com.gs.dmn.runtime.listener.Rule(0, "")

        // Rule start
        var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
        var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
        var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
        var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

        // Apply rule
        var output_: ExtraDaysCase3RuleOutput = ExtraDaysCase3RuleOutput(false)
        if (ruleMatches(eventListener_, drgRuleMetadata,
            true,
            booleanAnd(numericGreaterEqualThan(yearsOfService, number("15")), numericLessThan(yearsOfService, number("30")))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

            // Compute output
            output_.setMatched(true)
            output_.extraDaysCase3 = number("2")
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 1, annotation = "")
    private fun rule1(age: java.lang.Number?, yearsOfService: java.lang.Number?, context_: com.gs.dmn.runtime.ExecutionContext): com.gs.dmn.runtime.RuleOutput {
        // Rule metadata
        val drgRuleMetadata: com.gs.dmn.runtime.listener.Rule = com.gs.dmn.runtime.listener.Rule(1, "")

        // Rule start
        var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
        var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
        var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
        var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

        // Apply rule
        var output_: ExtraDaysCase3RuleOutput = ExtraDaysCase3RuleOutput(false)
        if (ruleMatches(eventListener_, drgRuleMetadata,
            numericGreaterEqualThan(age, number("45")),
            true
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

            // Compute output
            output_.setMatched(true)
            output_.extraDaysCase3 = number("2")
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_
    }


    companion object {
        val DRG_ELEMENT_METADATA : com.gs.dmn.runtime.listener.DRGElement = com.gs.dmn.runtime.listener.DRGElement(
            "",
            "Extra days case 3",
            "",
            com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
            com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
            com.gs.dmn.runtime.annotation.HitPolicy.COLLECT,
            2
        )
    }
}
