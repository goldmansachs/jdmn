
import java.util.*
import java.util.stream.Collectors

@javax.annotation.Generated(value = ["decision.ftl", "priceInRange"])
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "priceInRange",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.PRIORITY,
    rulesCount = 2
)
class PriceInRange() : com.gs.dmn.runtime.DefaultDMNBaseDecision() {
    fun apply(numB: String?, numC: String?, structA: String?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet): String? {
        return try {
            apply(numB?.let({ number(it) }), numC?.let({ number(it) }), structA?.let({ com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(it, object : com.fasterxml.jackson.core.type.TypeReference<type.TAImpl>() {}) }), annotationSet_, com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), com.gs.dmn.runtime.cache.DefaultCache())
        } catch (e: Exception) {
            logError("Cannot apply decision 'PriceInRange'", e)
            null
        }
    }

    fun apply(numB: String?, numC: String?, structA: String?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): String? {
        return try {
            apply(numB?.let({ number(it) }), numC?.let({ number(it) }), structA?.let({ com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(it, object : com.fasterxml.jackson.core.type.TypeReference<type.TAImpl>() {}) }), annotationSet_, eventListener_, externalExecutor_, cache_)
        } catch (e: Exception) {
            logError("Cannot apply decision 'PriceInRange'", e)
            null
        }
    }

    fun apply(numB: java.math.BigDecimal?, numC: java.math.BigDecimal?, structA: type.TA?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet): String? {
        return apply(numB, numC, structA, annotationSet_, com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), com.gs.dmn.runtime.cache.DefaultCache())
    }

    fun apply(numB: java.math.BigDecimal?, numC: java.math.BigDecimal?, structA: type.TA?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): String? {
        try {
            // Start decision 'priceInRange'
            val priceInRangeStartTime_ = System.currentTimeMillis()
            val priceInRangeArguments_ = com.gs.dmn.runtime.listener.Arguments()
            priceInRangeArguments_.put("numB", numB)
            priceInRangeArguments_.put("numC", numC)
            priceInRangeArguments_.put("structA", structA)
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, priceInRangeArguments_)

            // Evaluate decision 'priceInRange'
            val output_: String? = evaluate(numB, numC, structA, annotationSet_, eventListener_, externalExecutor_, cache_)

            // End decision 'priceInRange'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, priceInRangeArguments_, output_, (System.currentTimeMillis() - priceInRangeStartTime_))

            return output_
        } catch (e: Exception) {
            logError("Exception caught in 'priceInRange' evaluation", e)
            return null
        }
    }

    private inline fun evaluate(numB: java.math.BigDecimal?, numC: java.math.BigDecimal?, structA: type.TA?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): String? {
        // Apply rules and collect results
        val ruleOutputList_ = com.gs.dmn.runtime.RuleOutputList()
        ruleOutputList_.add(rule0(numB, numC, structA, annotationSet_, eventListener_, externalExecutor_))
        ruleOutputList_.add(rule1(numB, numC, structA, annotationSet_, eventListener_, externalExecutor_))

        // Return results based on hit policy
        var output_: String?
        if (ruleOutputList_.noMatchedRules()) {
            // Default value
            output_ = null
        } else {
            val ruleOutput_: com.gs.dmn.runtime.RuleOutput? = ruleOutputList_.applySingle(com.gs.dmn.runtime.annotation.HitPolicy.PRIORITY)
            output_ = ruleOutput_?.let({ (ruleOutput_ as PriceInRangeRuleOutput).priceInRange })
        }

        return output_
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 0, annotation = "")
    private fun rule0(numB: java.math.BigDecimal?, numC: java.math.BigDecimal?, structA: type.TA?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor): com.gs.dmn.runtime.RuleOutput {
        // Rule metadata
        val drgRuleMetadata: com.gs.dmn.runtime.listener.Rule = com.gs.dmn.runtime.listener.Rule(0, "")

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

        // Apply rule
        var output_: PriceInRangeRuleOutput = PriceInRangeRuleOutput(false)
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (booleanAnd(numericGreaterEqualThan(structA?.let({ it.price as java.math.BigDecimal? }), numB), numericLessEqualThan(structA?.let({ it.price as java.math.BigDecimal? }), numC)))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

            // Compute output
            output_.setMatched(true)
            output_.priceInRange = "In range"
            output_.priceInRangePriority = 2

            // Add annotation
            annotationSet_.addAnnotation("priceInRange", 0, "")
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 1, annotation = "")
    private fun rule1(numB: java.math.BigDecimal?, numC: java.math.BigDecimal?, structA: type.TA?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor): com.gs.dmn.runtime.RuleOutput {
        // Rule metadata
        val drgRuleMetadata: com.gs.dmn.runtime.listener.Rule = com.gs.dmn.runtime.listener.Rule(1, "")

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

        // Apply rule
        var output_: PriceInRangeRuleOutput = PriceInRangeRuleOutput(false)
        if (ruleMatches(eventListener_, drgRuleMetadata,
            true
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

            // Compute output
            output_.setMatched(true)
            output_.priceInRange = "Not in range"
            output_.priceInRangePriority = 1

            // Add annotation
            annotationSet_.addAnnotation("priceInRange", 1, "")
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_
    }


    companion object {
        val DRG_ELEMENT_METADATA : com.gs.dmn.runtime.listener.DRGElement = com.gs.dmn.runtime.listener.DRGElement(
            "",
            "priceInRange",
            "",
            com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
            com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
            com.gs.dmn.runtime.annotation.HitPolicy.PRIORITY,
            2
        )
    }
}
