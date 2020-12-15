
import java.util.*
import java.util.stream.Collectors

@javax.annotation.Generated(value = ["decision.ftl", "ExtraDaysCase2"])
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "ExtraDaysCase2",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.COLLECT,
    rulesCount = 2
)
class ExtraDaysCase2() : com.gs.dmn.runtime.DefaultDMNBaseDecision() {
    fun apply(age: String?, yearsOfService: String?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet): java.math.BigDecimal? {
        return try {
            apply(age?.let({ number(it) }), yearsOfService?.let({ number(it) }), annotationSet_, com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), com.gs.dmn.runtime.cache.DefaultCache())
        } catch (e: Exception) {
            logError("Cannot apply decision 'ExtraDaysCase2'", e)
            null
        }
    }

    fun apply(age: String?, yearsOfService: String?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): java.math.BigDecimal? {
        return try {
            apply(age?.let({ number(it) }), yearsOfService?.let({ number(it) }), annotationSet_, eventListener_, externalExecutor_, cache_)
        } catch (e: Exception) {
            logError("Cannot apply decision 'ExtraDaysCase2'", e)
            null
        }
    }

    fun apply(age: java.math.BigDecimal?, yearsOfService: java.math.BigDecimal?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet): java.math.BigDecimal? {
        return apply(age, yearsOfService, annotationSet_, com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), com.gs.dmn.runtime.cache.DefaultCache())
    }

    fun apply(age: java.math.BigDecimal?, yearsOfService: java.math.BigDecimal?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): java.math.BigDecimal? {
        try {
            // Start decision 'ExtraDaysCase2'
            val extraDaysCase2StartTime_ = System.currentTimeMillis()
            val extraDaysCase2Arguments_ = com.gs.dmn.runtime.listener.Arguments()
            extraDaysCase2Arguments_.put("Age", age)
            extraDaysCase2Arguments_.put("YearsOfService", yearsOfService)
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, extraDaysCase2Arguments_)

            // Evaluate decision 'ExtraDaysCase2'
            val output_: java.math.BigDecimal? = evaluate(age, yearsOfService, annotationSet_, eventListener_, externalExecutor_, cache_)

            // End decision 'ExtraDaysCase2'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, extraDaysCase2Arguments_, output_, (System.currentTimeMillis() - extraDaysCase2StartTime_))

            return output_
        } catch (e: Exception) {
            logError("Exception caught in 'ExtraDaysCase2' evaluation", e)
            return null
        }
    }

    private inline fun evaluate(age: java.math.BigDecimal?, yearsOfService: java.math.BigDecimal?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): java.math.BigDecimal? {
        // Apply rules and collect results
        val ruleOutputList_ = com.gs.dmn.runtime.RuleOutputList()
        ruleOutputList_.add(rule0(age, yearsOfService, annotationSet_, eventListener_, externalExecutor_))
        ruleOutputList_.add(rule1(age, yearsOfService, annotationSet_, eventListener_, externalExecutor_))

        // Return results based on hit policy
        var output_: java.math.BigDecimal?
        if (ruleOutputList_.noMatchedRules()) {
            // Default value
            output_ = number("0")
        } else {
            val ruleOutputs_: List<com.gs.dmn.runtime.RuleOutput> = ruleOutputList_.applyMultiple(com.gs.dmn.runtime.annotation.HitPolicy.COLLECT)
            output_ = max(ruleOutputs_?.map({ o -> (o as ExtraDaysCase2RuleOutput)?.extraDaysCase2 }))
        }

        return output_
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 0, annotation = "")
    private fun rule0(age: java.math.BigDecimal?, yearsOfService: java.math.BigDecimal?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor): com.gs.dmn.runtime.RuleOutput {
        // Rule metadata
        val drgRuleMetadata: com.gs.dmn.runtime.listener.Rule = com.gs.dmn.runtime.listener.Rule(0, "")

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

        // Apply rule
        var output_: ExtraDaysCase2RuleOutput = ExtraDaysCase2RuleOutput(false)
        if (ruleMatches(eventListener_, drgRuleMetadata,
            true,
            (numericGreaterEqualThan(yearsOfService, number("30")))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

            // Compute output
            output_.setMatched(true)
            output_.extraDaysCase2 = number("3")

            // Add annotation
            annotationSet_.addAnnotation("ExtraDaysCase2", 0, "")
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 1, annotation = "")
    private fun rule1(age: java.math.BigDecimal?, yearsOfService: java.math.BigDecimal?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor): com.gs.dmn.runtime.RuleOutput {
        // Rule metadata
        val drgRuleMetadata: com.gs.dmn.runtime.listener.Rule = com.gs.dmn.runtime.listener.Rule(1, "")

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

        // Apply rule
        var output_: ExtraDaysCase2RuleOutput = ExtraDaysCase2RuleOutput(false)
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (numericGreaterEqualThan(age, number("60"))),
            true
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

            // Compute output
            output_.setMatched(true)
            output_.extraDaysCase2 = number("3")

            // Add annotation
            annotationSet_.addAnnotation("ExtraDaysCase2", 1, "")
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_
    }


    companion object {
        val DRG_ELEMENT_METADATA : com.gs.dmn.runtime.listener.DRGElement = com.gs.dmn.runtime.listener.DRGElement(
            "",
            "ExtraDaysCase2",
            "",
            com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
            com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
            com.gs.dmn.runtime.annotation.HitPolicy.COLLECT,
            2
        )
    }
}
