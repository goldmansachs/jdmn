
import java.util.*
import java.util.stream.Collectors

@javax.annotation.Generated(value = ["decision.ftl", "ApprovalStatus"])
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "ApprovalStatus",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNIQUE,
    rulesCount = 4
)
class ApprovalStatus() : com.gs.dmn.runtime.DefaultDMNBaseDecision() {
    fun apply(age: String?, riskCategory: String?, isAffordable: String?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet): String? {
        return try {
            apply(age?.let({ number(it) }), riskCategory, isAffordable?.let({ it.toBoolean() }), annotationSet_, com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), com.gs.dmn.runtime.cache.DefaultCache())
        } catch (e: Exception) {
            logError("Cannot apply decision 'ApprovalStatus'", e)
            null
        }
    }

    fun apply(age: String?, riskCategory: String?, isAffordable: String?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): String? {
        return try {
            apply(age?.let({ number(it) }), riskCategory, isAffordable?.let({ it.toBoolean() }), annotationSet_, eventListener_, externalExecutor_, cache_)
        } catch (e: Exception) {
            logError("Cannot apply decision 'ApprovalStatus'", e)
            null
        }
    }

    fun apply(age: java.math.BigDecimal?, riskCategory: String?, isAffordable: Boolean?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet): String? {
        return apply(age, riskCategory, isAffordable, annotationSet_, com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), com.gs.dmn.runtime.cache.DefaultCache())
    }

    fun apply(age: java.math.BigDecimal?, riskCategory: String?, isAffordable: Boolean?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): String? {
        try {
            // Start decision 'ApprovalStatus'
            val approvalStatusStartTime_ = System.currentTimeMillis()
            val approvalStatusArguments_ = com.gs.dmn.runtime.listener.Arguments()
            approvalStatusArguments_.put("Age", age)
            approvalStatusArguments_.put("RiskCategory", riskCategory)
            approvalStatusArguments_.put("isAffordable", isAffordable)
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, approvalStatusArguments_)

            // Evaluate decision 'ApprovalStatus'
            val output_: String? = evaluate(age, riskCategory, isAffordable, annotationSet_, eventListener_, externalExecutor_, cache_)

            // End decision 'ApprovalStatus'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, approvalStatusArguments_, output_, (System.currentTimeMillis() - approvalStatusStartTime_))

            return output_
        } catch (e: Exception) {
            logError("Exception caught in 'ApprovalStatus' evaluation", e)
            return null
        }
    }

    private inline fun evaluate(age: java.math.BigDecimal?, riskCategory: String?, isAffordable: Boolean?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): String? {
        // Apply rules and collect results
        val ruleOutputList_ = com.gs.dmn.runtime.RuleOutputList()
        ruleOutputList_.add(rule0(age, riskCategory, isAffordable, annotationSet_, eventListener_, externalExecutor_))
        ruleOutputList_.add(rule1(age, riskCategory, isAffordable, annotationSet_, eventListener_, externalExecutor_))
        ruleOutputList_.add(rule2(age, riskCategory, isAffordable, annotationSet_, eventListener_, externalExecutor_))
        ruleOutputList_.add(rule3(age, riskCategory, isAffordable, annotationSet_, eventListener_, externalExecutor_))

        // Return results based on hit policy
        var output_: String?
        if (ruleOutputList_.noMatchedRules()) {
            // Default value
            output_ = null
        } else {
            val ruleOutput_: com.gs.dmn.runtime.RuleOutput? = ruleOutputList_.applySingle(com.gs.dmn.runtime.annotation.HitPolicy.UNIQUE)
            output_ = ruleOutput_?.let({ (ruleOutput_ as ApprovalStatusRuleOutput).approvalStatus })
        }

        return output_
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 0, annotation = "")
    private fun rule0(age: java.math.BigDecimal?, riskCategory: String?, isAffordable: Boolean?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor): com.gs.dmn.runtime.RuleOutput {
        // Rule metadata
        val drgRuleMetadata: com.gs.dmn.runtime.listener.Rule = com.gs.dmn.runtime.listener.Rule(0, "")

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

        // Apply rule
        var output_: ApprovalStatusRuleOutput = ApprovalStatusRuleOutput(false)
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (numericGreaterEqualThan(age, number("18"))),
            booleanOr((stringEqual(riskCategory, "Medium")), (stringEqual(riskCategory, "Low"))),
            (booleanEqual(isAffordable, true))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

            // Compute output
            output_.setMatched(true)
            output_.approvalStatus = "Approved"

            // Add annotation
            annotationSet_.addAnnotation("ApprovalStatus", 0, "")
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 1, annotation = "")
    private fun rule1(age: java.math.BigDecimal?, riskCategory: String?, isAffordable: Boolean?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor): com.gs.dmn.runtime.RuleOutput {
        // Rule metadata
        val drgRuleMetadata: com.gs.dmn.runtime.listener.Rule = com.gs.dmn.runtime.listener.Rule(1, "")

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

        // Apply rule
        var output_: ApprovalStatusRuleOutput = ApprovalStatusRuleOutput(false)
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (numericLessThan(age, number("18"))),
            booleanOr((stringEqual(riskCategory, "Medium")), (stringEqual(riskCategory, "Low"))),
            (booleanEqual(isAffordable, true))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

            // Compute output
            output_.setMatched(true)
            output_.approvalStatus = "Declined"

            // Add annotation
            annotationSet_.addAnnotation("ApprovalStatus", 1, "")
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 2, annotation = "")
    private fun rule2(age: java.math.BigDecimal?, riskCategory: String?, isAffordable: Boolean?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor): com.gs.dmn.runtime.RuleOutput {
        // Rule metadata
        val drgRuleMetadata: com.gs.dmn.runtime.listener.Rule = com.gs.dmn.runtime.listener.Rule(2, "")

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

        // Apply rule
        var output_: ApprovalStatusRuleOutput = ApprovalStatusRuleOutput(false)
        if (ruleMatches(eventListener_, drgRuleMetadata,
            true,
            (stringEqual(riskCategory, "High")),
            (booleanEqual(isAffordable, true))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

            // Compute output
            output_.setMatched(true)
            output_.approvalStatus = "Declined"

            // Add annotation
            annotationSet_.addAnnotation("ApprovalStatus", 2, "")
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 3, annotation = "")
    private fun rule3(age: java.math.BigDecimal?, riskCategory: String?, isAffordable: Boolean?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor): com.gs.dmn.runtime.RuleOutput {
        // Rule metadata
        val drgRuleMetadata: com.gs.dmn.runtime.listener.Rule = com.gs.dmn.runtime.listener.Rule(3, "")

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

        // Apply rule
        var output_: ApprovalStatusRuleOutput = ApprovalStatusRuleOutput(false)
        if (ruleMatches(eventListener_, drgRuleMetadata,
            true,
            true,
            (booleanEqual(isAffordable, false))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

            // Compute output
            output_.setMatched(true)
            output_.approvalStatus = "Declined"

            // Add annotation
            annotationSet_.addAnnotation("ApprovalStatus", 3, "")
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_
    }


    companion object {
        val DRG_ELEMENT_METADATA : com.gs.dmn.runtime.listener.DRGElement = com.gs.dmn.runtime.listener.DRGElement(
            "",
            "ApprovalStatus",
            "",
            com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
            com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
            com.gs.dmn.runtime.annotation.HitPolicy.UNIQUE,
            4
        )
    }
}
