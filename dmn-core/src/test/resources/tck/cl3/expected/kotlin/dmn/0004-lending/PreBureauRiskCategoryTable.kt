
import java.util.*
import java.util.stream.Collectors

@javax.annotation.Generated(value = ["bkm.ftl", "PreBureauRiskCategoryTable"])
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "PreBureauRiskCategoryTable",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNIQUE,
    rulesCount = 8
)
class PreBureauRiskCategoryTable : com.gs.dmn.runtime.DefaultDMNBaseDecision {
    private constructor() {}

    private fun apply(existingCustomer: Boolean?, applicationRiskScore: java.math.BigDecimal?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): String? {
        try {
            // Start BKM 'PreBureauRiskCategoryTable'
            val preBureauRiskCategoryTableStartTime_ = System.currentTimeMillis()
            val preBureauRiskCategoryTableArguments_ = com.gs.dmn.runtime.listener.Arguments()
            preBureauRiskCategoryTableArguments_.put("ExistingCustomer", existingCustomer)
            preBureauRiskCategoryTableArguments_.put("ApplicationRiskScore", applicationRiskScore)
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, preBureauRiskCategoryTableArguments_)

            // Evaluate BKM 'PreBureauRiskCategoryTable'
            val output_: String? = evaluate(existingCustomer, applicationRiskScore, annotationSet_, eventListener_, externalExecutor_, cache_)

            // End BKM 'PreBureauRiskCategoryTable'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, preBureauRiskCategoryTableArguments_, output_, (System.currentTimeMillis() - preBureauRiskCategoryTableStartTime_))

            return output_
        } catch (e: Exception) {
            logError("Exception caught in 'PreBureauRiskCategoryTable' evaluation", e)
            return null
        }
    }

    private inline fun evaluate(existingCustomer: Boolean?, applicationRiskScore: java.math.BigDecimal?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): String? {
        // Apply rules and collect results
        val ruleOutputList_ = com.gs.dmn.runtime.RuleOutputList()
        ruleOutputList_.add(rule0(existingCustomer, applicationRiskScore, annotationSet_, eventListener_, externalExecutor_))
        ruleOutputList_.add(rule1(existingCustomer, applicationRiskScore, annotationSet_, eventListener_, externalExecutor_))
        ruleOutputList_.add(rule2(existingCustomer, applicationRiskScore, annotationSet_, eventListener_, externalExecutor_))
        ruleOutputList_.add(rule3(existingCustomer, applicationRiskScore, annotationSet_, eventListener_, externalExecutor_))
        ruleOutputList_.add(rule4(existingCustomer, applicationRiskScore, annotationSet_, eventListener_, externalExecutor_))
        ruleOutputList_.add(rule5(existingCustomer, applicationRiskScore, annotationSet_, eventListener_, externalExecutor_))
        ruleOutputList_.add(rule6(existingCustomer, applicationRiskScore, annotationSet_, eventListener_, externalExecutor_))
        ruleOutputList_.add(rule7(existingCustomer, applicationRiskScore, annotationSet_, eventListener_, externalExecutor_))

        // Return results based on hit policy
        var output_: String?
        if (ruleOutputList_.noMatchedRules()) {
            // Default value
            output_ = null
        } else {
            val ruleOutput_: com.gs.dmn.runtime.RuleOutput? = ruleOutputList_.applySingle(com.gs.dmn.runtime.annotation.HitPolicy.UNIQUE)
            output_ = ruleOutput_?.let({ (ruleOutput_ as PreBureauRiskCategoryTableRuleOutput).preBureauRiskCategoryTable })
        }

        return output_
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 0, annotation = "")
    private fun rule0(existingCustomer: Boolean?, applicationRiskScore: java.math.BigDecimal?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor): com.gs.dmn.runtime.RuleOutput {
        // Rule metadata
        val drgRuleMetadata: com.gs.dmn.runtime.listener.Rule = com.gs.dmn.runtime.listener.Rule(0, "")

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

        // Apply rule
        var output_: PreBureauRiskCategoryTableRuleOutput = PreBureauRiskCategoryTableRuleOutput(false)
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (booleanEqual(existingCustomer, false)),
            (numericLessThan(applicationRiskScore, number("100")))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

            // Compute output
            output_.setMatched(true)
            output_.preBureauRiskCategoryTable = "HIGH"

            // Add annotation
            annotationSet_.addAnnotation("PreBureauRiskCategoryTable", 0, "")
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 1, annotation = "")
    private fun rule1(existingCustomer: Boolean?, applicationRiskScore: java.math.BigDecimal?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor): com.gs.dmn.runtime.RuleOutput {
        // Rule metadata
        val drgRuleMetadata: com.gs.dmn.runtime.listener.Rule = com.gs.dmn.runtime.listener.Rule(1, "")

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

        // Apply rule
        var output_: PreBureauRiskCategoryTableRuleOutput = PreBureauRiskCategoryTableRuleOutput(false)
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (booleanEqual(existingCustomer, false)),
            (booleanAnd(numericGreaterEqualThan(applicationRiskScore, number("100")), numericLessThan(applicationRiskScore, number("120"))))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

            // Compute output
            output_.setMatched(true)
            output_.preBureauRiskCategoryTable = "MEDIUM"

            // Add annotation
            annotationSet_.addAnnotation("PreBureauRiskCategoryTable", 1, "")
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 2, annotation = "")
    private fun rule2(existingCustomer: Boolean?, applicationRiskScore: java.math.BigDecimal?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor): com.gs.dmn.runtime.RuleOutput {
        // Rule metadata
        val drgRuleMetadata: com.gs.dmn.runtime.listener.Rule = com.gs.dmn.runtime.listener.Rule(2, "")

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

        // Apply rule
        var output_: PreBureauRiskCategoryTableRuleOutput = PreBureauRiskCategoryTableRuleOutput(false)
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (booleanEqual(existingCustomer, false)),
            (booleanAnd(numericGreaterEqualThan(applicationRiskScore, number("120")), numericLessThan(applicationRiskScore, number("130"))))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

            // Compute output
            output_.setMatched(true)
            output_.preBureauRiskCategoryTable = "LOW"

            // Add annotation
            annotationSet_.addAnnotation("PreBureauRiskCategoryTable", 2, "")
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 3, annotation = "")
    private fun rule3(existingCustomer: Boolean?, applicationRiskScore: java.math.BigDecimal?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor): com.gs.dmn.runtime.RuleOutput {
        // Rule metadata
        val drgRuleMetadata: com.gs.dmn.runtime.listener.Rule = com.gs.dmn.runtime.listener.Rule(3, "")

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

        // Apply rule
        var output_: PreBureauRiskCategoryTableRuleOutput = PreBureauRiskCategoryTableRuleOutput(false)
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (booleanEqual(existingCustomer, false)),
            (numericGreaterThan(applicationRiskScore, number("130")))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

            // Compute output
            output_.setMatched(true)
            output_.preBureauRiskCategoryTable = "VERY LOW"

            // Add annotation
            annotationSet_.addAnnotation("PreBureauRiskCategoryTable", 3, "")
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 4, annotation = "")
    private fun rule4(existingCustomer: Boolean?, applicationRiskScore: java.math.BigDecimal?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor): com.gs.dmn.runtime.RuleOutput {
        // Rule metadata
        val drgRuleMetadata: com.gs.dmn.runtime.listener.Rule = com.gs.dmn.runtime.listener.Rule(4, "")

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

        // Apply rule
        var output_: PreBureauRiskCategoryTableRuleOutput = PreBureauRiskCategoryTableRuleOutput(false)
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (booleanEqual(existingCustomer, true)),
            (numericLessThan(applicationRiskScore, number("80")))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

            // Compute output
            output_.setMatched(true)
            output_.preBureauRiskCategoryTable = "DECLINE"

            // Add annotation
            annotationSet_.addAnnotation("PreBureauRiskCategoryTable", 4, "")
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 5, annotation = "")
    private fun rule5(existingCustomer: Boolean?, applicationRiskScore: java.math.BigDecimal?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor): com.gs.dmn.runtime.RuleOutput {
        // Rule metadata
        val drgRuleMetadata: com.gs.dmn.runtime.listener.Rule = com.gs.dmn.runtime.listener.Rule(5, "")

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

        // Apply rule
        var output_: PreBureauRiskCategoryTableRuleOutput = PreBureauRiskCategoryTableRuleOutput(false)
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (booleanEqual(existingCustomer, true)),
            (booleanAnd(numericGreaterEqualThan(applicationRiskScore, number("80")), numericLessThan(applicationRiskScore, number("90"))))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

            // Compute output
            output_.setMatched(true)
            output_.preBureauRiskCategoryTable = "HIGH"

            // Add annotation
            annotationSet_.addAnnotation("PreBureauRiskCategoryTable", 5, "")
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 6, annotation = "")
    private fun rule6(existingCustomer: Boolean?, applicationRiskScore: java.math.BigDecimal?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor): com.gs.dmn.runtime.RuleOutput {
        // Rule metadata
        val drgRuleMetadata: com.gs.dmn.runtime.listener.Rule = com.gs.dmn.runtime.listener.Rule(6, "")

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

        // Apply rule
        var output_: PreBureauRiskCategoryTableRuleOutput = PreBureauRiskCategoryTableRuleOutput(false)
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (booleanEqual(existingCustomer, true)),
            (booleanAnd(numericGreaterEqualThan(applicationRiskScore, number("90")), numericLessEqualThan(applicationRiskScore, number("110"))))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

            // Compute output
            output_.setMatched(true)
            output_.preBureauRiskCategoryTable = "MEDIUM"

            // Add annotation
            annotationSet_.addAnnotation("PreBureauRiskCategoryTable", 6, "")
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 7, annotation = "")
    private fun rule7(existingCustomer: Boolean?, applicationRiskScore: java.math.BigDecimal?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor): com.gs.dmn.runtime.RuleOutput {
        // Rule metadata
        val drgRuleMetadata: com.gs.dmn.runtime.listener.Rule = com.gs.dmn.runtime.listener.Rule(7, "")

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

        // Apply rule
        var output_: PreBureauRiskCategoryTableRuleOutput = PreBureauRiskCategoryTableRuleOutput(false)
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (booleanEqual(existingCustomer, true)),
            (numericGreaterThan(applicationRiskScore, number("110")))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

            // Compute output
            output_.setMatched(true)
            output_.preBureauRiskCategoryTable = "LOW"

            // Add annotation
            annotationSet_.addAnnotation("PreBureauRiskCategoryTable", 7, "")
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_
    }


    companion object {
        val DRG_ELEMENT_METADATA = com.gs.dmn.runtime.listener.DRGElement(
            "",
            "PreBureauRiskCategoryTable",
            "",
            com.gs.dmn.runtime.annotation.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
            com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
            com.gs.dmn.runtime.annotation.HitPolicy.UNIQUE,
            8
        )

        val INSTANCE = PreBureauRiskCategoryTable()

        fun PreBureauRiskCategoryTable(existingCustomer: Boolean?, applicationRiskScore: java.math.BigDecimal?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): String? {
            return INSTANCE.apply(existingCustomer, applicationRiskScore, annotationSet_, eventListener_, externalExecutor_, cache_)
        }
    }
}
