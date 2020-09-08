
import java.util.*
import java.util.stream.Collectors

@javax.annotation.Generated(value = ["decision.ftl", "priceGt10"])
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "priceGt10",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNIQUE,
    rulesCount = 2
)
class PriceGt10() : com.gs.dmn.runtime.DefaultDMNBaseDecision() {
    fun apply(structA: String?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet): Boolean? {
        return try {
            apply(structA?.let({ com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(it, object : com.fasterxml.jackson.core.type.TypeReference<type.TAImpl>() {}) }), annotationSet_, com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), com.gs.dmn.runtime.cache.DefaultCache())
        } catch (e: Exception) {
            logError("Cannot apply decision 'PriceGt10'", e)
            null
        }
    }

    fun apply(structA: String?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): Boolean? {
        return try {
            apply(structA?.let({ com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(it, object : com.fasterxml.jackson.core.type.TypeReference<type.TAImpl>() {}) }), annotationSet_, eventListener_, externalExecutor_, cache_)
        } catch (e: Exception) {
            logError("Cannot apply decision 'PriceGt10'", e)
            null
        }
    }

    fun apply(structA: type.TA?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet): Boolean? {
        return apply(structA, annotationSet_, com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), com.gs.dmn.runtime.cache.DefaultCache())
    }

    fun apply(structA: type.TA?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): Boolean? {
        try {
            // Start decision 'priceGt10'
            val priceGt10StartTime_ = System.currentTimeMillis()
            val priceGt10Arguments_ = com.gs.dmn.runtime.listener.Arguments()
            priceGt10Arguments_.put("structA", structA)
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, priceGt10Arguments_)

            // Evaluate decision 'priceGt10'
            val output_: Boolean? = evaluate(structA, annotationSet_, eventListener_, externalExecutor_, cache_)

            // End decision 'priceGt10'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, priceGt10Arguments_, output_, (System.currentTimeMillis() - priceGt10StartTime_))

            return output_
        } catch (e: Exception) {
            logError("Exception caught in 'priceGt10' evaluation", e)
            return null
        }
    }

    private inline fun evaluate(structA: type.TA?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): Boolean? {
        // Apply rules and collect results
        val ruleOutputList_ = com.gs.dmn.runtime.RuleOutputList()
        ruleOutputList_.add(rule0(structA, annotationSet_, eventListener_, externalExecutor_))
        ruleOutputList_.add(rule1(structA, annotationSet_, eventListener_, externalExecutor_))

        // Return results based on hit policy
        var output_: Boolean?
        if (ruleOutputList_.noMatchedRules()) {
            // Default value
            output_ = null
        } else {
            val ruleOutput_: com.gs.dmn.runtime.RuleOutput? = ruleOutputList_.applySingle(com.gs.dmn.runtime.annotation.HitPolicy.UNIQUE)
            output_ = ruleOutput_?.let({ (ruleOutput_ as PriceGt10RuleOutput).priceGt10 })
        }

        return output_
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 0, annotation = "")
    private fun rule0(structA: type.TA?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor): com.gs.dmn.runtime.RuleOutput {
        // Rule metadata
        val drgRuleMetadata: com.gs.dmn.runtime.listener.Rule = com.gs.dmn.runtime.listener.Rule(0, "")

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

        // Apply rule
        var output_: PriceGt10RuleOutput = PriceGt10RuleOutput(false)
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (numericGreaterThan(structA?.let({ it.price as java.math.BigDecimal? }), number("10")))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

            // Compute output
            output_.setMatched(true)
            output_.priceGt10 = true

            // Add annotation
            annotationSet_.addAnnotation("priceGt10", 0, "")
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 1, annotation = "")
    private fun rule1(structA: type.TA?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor): com.gs.dmn.runtime.RuleOutput {
        // Rule metadata
        val drgRuleMetadata: com.gs.dmn.runtime.listener.Rule = com.gs.dmn.runtime.listener.Rule(1, "")

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

        // Apply rule
        var output_: PriceGt10RuleOutput = PriceGt10RuleOutput(false)
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (numericLessEqualThan(structA?.let({ it.price as java.math.BigDecimal? }), number("10")))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

            // Compute output
            output_.setMatched(true)
            output_.priceGt10 = false

            // Add annotation
            annotationSet_.addAnnotation("priceGt10", 1, "")
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_
    }


    companion object {
        val DRG_ELEMENT_METADATA : com.gs.dmn.runtime.listener.DRGElement = com.gs.dmn.runtime.listener.DRGElement(
            "",
            "priceGt10",
            "",
            com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
            com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
            com.gs.dmn.runtime.annotation.HitPolicy.UNIQUE,
            2
        )
    }
}
