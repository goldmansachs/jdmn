
import java.util.*
import java.util.stream.Collectors

@javax.annotation.Generated(value = ["decision.ftl", "ExtraDaysCase3"])
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "ExtraDaysCase3",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.COLLECT,
    rulesCount = 2
)
class ExtraDaysCase3() : com.gs.dmn.runtime.DefaultDMNBaseDecision() {
    fun apply(age: String?, yearsOfService: String?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet): java.math.BigDecimal? {
        return try {
            apply(age?.let({ number(it) }), yearsOfService?.let({ number(it) }), annotationSet_, com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), com.gs.dmn.runtime.cache.DefaultCache())
        } catch (e: Exception) {
            logError("Cannot apply decision 'ExtraDaysCase3'", e)
            null
        }
    }

    fun apply(age: String?, yearsOfService: String?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): java.math.BigDecimal? {
        return try {
            apply(age?.let({ number(it) }), yearsOfService?.let({ number(it) }), annotationSet_, eventListener_, externalExecutor_, cache_)
        } catch (e: Exception) {
            logError("Cannot apply decision 'ExtraDaysCase3'", e)
            null
        }
    }

    fun apply(age: java.math.BigDecimal?, yearsOfService: java.math.BigDecimal?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet): java.math.BigDecimal? {
        return apply(age, yearsOfService, annotationSet_, com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), com.gs.dmn.runtime.cache.DefaultCache())
    }

    fun apply(age: java.math.BigDecimal?, yearsOfService: java.math.BigDecimal?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): java.math.BigDecimal? {
        try {
            // Start decision 'ExtraDaysCase3'
            val extraDaysCase3StartTime_ = System.currentTimeMillis()
            val extraDaysCase3Arguments_ = com.gs.dmn.runtime.listener.Arguments()
            extraDaysCase3Arguments_.put("Age", age)
            extraDaysCase3Arguments_.put("YearsOfService", yearsOfService)
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, extraDaysCase3Arguments_)

            // Evaluate decision 'ExtraDaysCase3'
            val output_: java.math.BigDecimal? = evaluate(age, yearsOfService, annotationSet_, eventListener_, externalExecutor_, cache_)

            // End decision 'ExtraDaysCase3'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, extraDaysCase3Arguments_, output_, (System.currentTimeMillis() - extraDaysCase3StartTime_))

            return output_
        } catch (e: Exception) {
            logError("Exception caught in 'ExtraDaysCase3' evaluation", e)
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
            output_ = max(ruleOutputs_?.map({ o -> (o as ExtraDaysCase3RuleOutput)?.extraDaysCase3 }))
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
        var output_: ExtraDaysCase3RuleOutput = ExtraDaysCase3RuleOutput(false)
        if (ruleMatches(eventListener_, drgRuleMetadata,
            true,
            (booleanAnd(numericGreaterEqualThan(yearsOfService, number("15")), numericLessThan(yearsOfService, number("30"))))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

            // Compute output
            output_.setMatched(true)
            output_.extraDaysCase3 = number("2")

            // Add annotation
            annotationSet_.addAnnotation("ExtraDaysCase3", 0, "")
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
        var output_: ExtraDaysCase3RuleOutput = ExtraDaysCase3RuleOutput(false)
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (numericGreaterEqualThan(age, number("45"))),
            true
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

            // Compute output
            output_.setMatched(true)
            output_.extraDaysCase3 = number("2")

            // Add annotation
            annotationSet_.addAnnotation("ExtraDaysCase3", 1, "")
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_
    }


    companion object {
        val DRG_ELEMENT_METADATA : com.gs.dmn.runtime.listener.DRGElement = com.gs.dmn.runtime.listener.DRGElement(
            "",
            "ExtraDaysCase3",
            "",
            com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
            com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
            com.gs.dmn.runtime.annotation.HitPolicy.COLLECT,
            2
        )
    }
}
