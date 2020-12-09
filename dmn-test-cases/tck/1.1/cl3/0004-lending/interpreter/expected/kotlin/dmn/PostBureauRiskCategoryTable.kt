
import java.util.*
import java.util.stream.Collectors

@javax.annotation.Generated(value = ["bkm.ftl", "PostBureauRiskCategoryTable"])
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "PostBureauRiskCategoryTable",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNIQUE,
    rulesCount = 13
)
class PostBureauRiskCategoryTable : com.gs.dmn.runtime.DefaultDMNBaseDecision {
    private constructor() {}

    private fun apply(existingCustomer: Boolean?, applicationRiskScore: java.math.BigDecimal?, creditScore: java.math.BigDecimal?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): String? {
        try {
            // Start BKM 'PostBureauRiskCategoryTable'
            val postBureauRiskCategoryTableStartTime_ = System.currentTimeMillis()
            val postBureauRiskCategoryTableArguments_ = com.gs.dmn.runtime.listener.Arguments()
            postBureauRiskCategoryTableArguments_.put("ExistingCustomer", existingCustomer)
            postBureauRiskCategoryTableArguments_.put("ApplicationRiskScore", applicationRiskScore)
            postBureauRiskCategoryTableArguments_.put("CreditScore", creditScore)
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, postBureauRiskCategoryTableArguments_)

            // Evaluate BKM 'PostBureauRiskCategoryTable'
            val output_: String? = evaluate(existingCustomer, applicationRiskScore, creditScore, annotationSet_, eventListener_, externalExecutor_, cache_)

            // End BKM 'PostBureauRiskCategoryTable'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, postBureauRiskCategoryTableArguments_, output_, (System.currentTimeMillis() - postBureauRiskCategoryTableStartTime_))

            return output_
        } catch (e: Exception) {
            logError("Exception caught in 'PostBureauRiskCategoryTable' evaluation", e)
            return null
        }
    }

    private inline fun evaluate(existingCustomer: Boolean?, applicationRiskScore: java.math.BigDecimal?, creditScore: java.math.BigDecimal?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): String? {
        // Apply rules and collect results
        val ruleOutputList_ = com.gs.dmn.runtime.RuleOutputList()
        ruleOutputList_.add(rule0(existingCustomer, applicationRiskScore, creditScore, annotationSet_, eventListener_, externalExecutor_))
        ruleOutputList_.add(rule1(existingCustomer, applicationRiskScore, creditScore, annotationSet_, eventListener_, externalExecutor_))
        ruleOutputList_.add(rule2(existingCustomer, applicationRiskScore, creditScore, annotationSet_, eventListener_, externalExecutor_))
        ruleOutputList_.add(rule3(existingCustomer, applicationRiskScore, creditScore, annotationSet_, eventListener_, externalExecutor_))
        ruleOutputList_.add(rule4(existingCustomer, applicationRiskScore, creditScore, annotationSet_, eventListener_, externalExecutor_))
        ruleOutputList_.add(rule5(existingCustomer, applicationRiskScore, creditScore, annotationSet_, eventListener_, externalExecutor_))
        ruleOutputList_.add(rule6(existingCustomer, applicationRiskScore, creditScore, annotationSet_, eventListener_, externalExecutor_))
        ruleOutputList_.add(rule7(existingCustomer, applicationRiskScore, creditScore, annotationSet_, eventListener_, externalExecutor_))
        ruleOutputList_.add(rule8(existingCustomer, applicationRiskScore, creditScore, annotationSet_, eventListener_, externalExecutor_))
        ruleOutputList_.add(rule9(existingCustomer, applicationRiskScore, creditScore, annotationSet_, eventListener_, externalExecutor_))
        ruleOutputList_.add(rule10(existingCustomer, applicationRiskScore, creditScore, annotationSet_, eventListener_, externalExecutor_))
        ruleOutputList_.add(rule11(existingCustomer, applicationRiskScore, creditScore, annotationSet_, eventListener_, externalExecutor_))
        ruleOutputList_.add(rule12(existingCustomer, applicationRiskScore, creditScore, annotationSet_, eventListener_, externalExecutor_))

        // Return results based on hit policy
        var output_: String?
        if (ruleOutputList_.noMatchedRules()) {
            // Default value
            output_ = null
        } else {
            val ruleOutput_: com.gs.dmn.runtime.RuleOutput? = ruleOutputList_.applySingle(com.gs.dmn.runtime.annotation.HitPolicy.UNIQUE)
            output_ = ruleOutput_?.let({ (ruleOutput_ as PostBureauRiskCategoryTableRuleOutput).postBureauRiskCategoryTable })
        }

        return output_
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 0, annotation = "")
    private fun rule0(existingCustomer: Boolean?, applicationRiskScore: java.math.BigDecimal?, creditScore: java.math.BigDecimal?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor): com.gs.dmn.runtime.RuleOutput {
        // Rule metadata
        val drgRuleMetadata: com.gs.dmn.runtime.listener.Rule = com.gs.dmn.runtime.listener.Rule(0, "")

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

        // Apply rule
        var output_: PostBureauRiskCategoryTableRuleOutput = PostBureauRiskCategoryTableRuleOutput(false)
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (booleanEqual(existingCustomer, false)),
            (numericLessThan(applicationRiskScore, number("120"))),
            (numericLessThan(creditScore, number("590")))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

            // Compute output
            output_.setMatched(true)
            output_.postBureauRiskCategoryTable = "HIGH"

            // Add annotation
            annotationSet_.addAnnotation("PostBureauRiskCategoryTable", 0, "")
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 1, annotation = "")
    private fun rule1(existingCustomer: Boolean?, applicationRiskScore: java.math.BigDecimal?, creditScore: java.math.BigDecimal?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor): com.gs.dmn.runtime.RuleOutput {
        // Rule metadata
        val drgRuleMetadata: com.gs.dmn.runtime.listener.Rule = com.gs.dmn.runtime.listener.Rule(1, "")

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

        // Apply rule
        var output_: PostBureauRiskCategoryTableRuleOutput = PostBureauRiskCategoryTableRuleOutput(false)
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (booleanEqual(existingCustomer, false)),
            (numericLessThan(applicationRiskScore, number("120"))),
            (booleanAnd(numericGreaterEqualThan(creditScore, number("590")), numericLessEqualThan(creditScore, number("610"))))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

            // Compute output
            output_.setMatched(true)
            output_.postBureauRiskCategoryTable = "MEDIUM"

            // Add annotation
            annotationSet_.addAnnotation("PostBureauRiskCategoryTable", 1, "")
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 2, annotation = "")
    private fun rule2(existingCustomer: Boolean?, applicationRiskScore: java.math.BigDecimal?, creditScore: java.math.BigDecimal?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor): com.gs.dmn.runtime.RuleOutput {
        // Rule metadata
        val drgRuleMetadata: com.gs.dmn.runtime.listener.Rule = com.gs.dmn.runtime.listener.Rule(2, "")

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

        // Apply rule
        var output_: PostBureauRiskCategoryTableRuleOutput = PostBureauRiskCategoryTableRuleOutput(false)
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (booleanEqual(existingCustomer, false)),
            (numericLessThan(applicationRiskScore, number("120"))),
            (numericGreaterThan(creditScore, number("610")))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

            // Compute output
            output_.setMatched(true)
            output_.postBureauRiskCategoryTable = "LOW"

            // Add annotation
            annotationSet_.addAnnotation("PostBureauRiskCategoryTable", 2, "")
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 3, annotation = "")
    private fun rule3(existingCustomer: Boolean?, applicationRiskScore: java.math.BigDecimal?, creditScore: java.math.BigDecimal?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor): com.gs.dmn.runtime.RuleOutput {
        // Rule metadata
        val drgRuleMetadata: com.gs.dmn.runtime.listener.Rule = com.gs.dmn.runtime.listener.Rule(3, "")

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

        // Apply rule
        var output_: PostBureauRiskCategoryTableRuleOutput = PostBureauRiskCategoryTableRuleOutput(false)
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (booleanEqual(existingCustomer, false)),
            (booleanAnd(numericGreaterEqualThan(applicationRiskScore, number("120")), numericLessEqualThan(applicationRiskScore, number("130")))),
            (numericLessThan(creditScore, number("600")))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

            // Compute output
            output_.setMatched(true)
            output_.postBureauRiskCategoryTable = "HIGH"

            // Add annotation
            annotationSet_.addAnnotation("PostBureauRiskCategoryTable", 3, "")
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 4, annotation = "")
    private fun rule4(existingCustomer: Boolean?, applicationRiskScore: java.math.BigDecimal?, creditScore: java.math.BigDecimal?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor): com.gs.dmn.runtime.RuleOutput {
        // Rule metadata
        val drgRuleMetadata: com.gs.dmn.runtime.listener.Rule = com.gs.dmn.runtime.listener.Rule(4, "")

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

        // Apply rule
        var output_: PostBureauRiskCategoryTableRuleOutput = PostBureauRiskCategoryTableRuleOutput(false)
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (booleanEqual(existingCustomer, false)),
            (booleanAnd(numericGreaterEqualThan(applicationRiskScore, number("120")), numericLessEqualThan(applicationRiskScore, number("130")))),
            (booleanAnd(numericGreaterEqualThan(creditScore, number("600")), numericLessEqualThan(creditScore, number("625"))))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

            // Compute output
            output_.setMatched(true)
            output_.postBureauRiskCategoryTable = "MEDIUM"

            // Add annotation
            annotationSet_.addAnnotation("PostBureauRiskCategoryTable", 4, "")
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 5, annotation = "")
    private fun rule5(existingCustomer: Boolean?, applicationRiskScore: java.math.BigDecimal?, creditScore: java.math.BigDecimal?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor): com.gs.dmn.runtime.RuleOutput {
        // Rule metadata
        val drgRuleMetadata: com.gs.dmn.runtime.listener.Rule = com.gs.dmn.runtime.listener.Rule(5, "")

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

        // Apply rule
        var output_: PostBureauRiskCategoryTableRuleOutput = PostBureauRiskCategoryTableRuleOutput(false)
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (booleanEqual(existingCustomer, false)),
            (booleanAnd(numericGreaterEqualThan(applicationRiskScore, number("120")), numericLessEqualThan(applicationRiskScore, number("130")))),
            (numericGreaterThan(creditScore, number("625")))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

            // Compute output
            output_.setMatched(true)
            output_.postBureauRiskCategoryTable = "LOW"

            // Add annotation
            annotationSet_.addAnnotation("PostBureauRiskCategoryTable", 5, "")
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 6, annotation = "")
    private fun rule6(existingCustomer: Boolean?, applicationRiskScore: java.math.BigDecimal?, creditScore: java.math.BigDecimal?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor): com.gs.dmn.runtime.RuleOutput {
        // Rule metadata
        val drgRuleMetadata: com.gs.dmn.runtime.listener.Rule = com.gs.dmn.runtime.listener.Rule(6, "")

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

        // Apply rule
        var output_: PostBureauRiskCategoryTableRuleOutput = PostBureauRiskCategoryTableRuleOutput(false)
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (booleanEqual(existingCustomer, false)),
            (numericGreaterThan(applicationRiskScore, number("130"))),
            true
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

            // Compute output
            output_.setMatched(true)
            output_.postBureauRiskCategoryTable = "VERY LOW"

            // Add annotation
            annotationSet_.addAnnotation("PostBureauRiskCategoryTable", 6, "")
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 7, annotation = "")
    private fun rule7(existingCustomer: Boolean?, applicationRiskScore: java.math.BigDecimal?, creditScore: java.math.BigDecimal?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor): com.gs.dmn.runtime.RuleOutput {
        // Rule metadata
        val drgRuleMetadata: com.gs.dmn.runtime.listener.Rule = com.gs.dmn.runtime.listener.Rule(7, "")

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

        // Apply rule
        var output_: PostBureauRiskCategoryTableRuleOutput = PostBureauRiskCategoryTableRuleOutput(false)
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (booleanEqual(existingCustomer, true)),
            (numericLessEqualThan(applicationRiskScore, number("100"))),
            (numericLessThan(creditScore, number("580")))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

            // Compute output
            output_.setMatched(true)
            output_.postBureauRiskCategoryTable = "HIGH"

            // Add annotation
            annotationSet_.addAnnotation("PostBureauRiskCategoryTable", 7, "")
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 8, annotation = "")
    private fun rule8(existingCustomer: Boolean?, applicationRiskScore: java.math.BigDecimal?, creditScore: java.math.BigDecimal?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor): com.gs.dmn.runtime.RuleOutput {
        // Rule metadata
        val drgRuleMetadata: com.gs.dmn.runtime.listener.Rule = com.gs.dmn.runtime.listener.Rule(8, "")

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

        // Apply rule
        var output_: PostBureauRiskCategoryTableRuleOutput = PostBureauRiskCategoryTableRuleOutput(false)
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (booleanEqual(existingCustomer, true)),
            (numericLessEqualThan(applicationRiskScore, number("100"))),
            (booleanAnd(numericGreaterEqualThan(creditScore, number("580")), numericLessEqualThan(creditScore, number("600"))))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

            // Compute output
            output_.setMatched(true)
            output_.postBureauRiskCategoryTable = "MEDIUM"

            // Add annotation
            annotationSet_.addAnnotation("PostBureauRiskCategoryTable", 8, "")
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 9, annotation = "")
    private fun rule9(existingCustomer: Boolean?, applicationRiskScore: java.math.BigDecimal?, creditScore: java.math.BigDecimal?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor): com.gs.dmn.runtime.RuleOutput {
        // Rule metadata
        val drgRuleMetadata: com.gs.dmn.runtime.listener.Rule = com.gs.dmn.runtime.listener.Rule(9, "")

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

        // Apply rule
        var output_: PostBureauRiskCategoryTableRuleOutput = PostBureauRiskCategoryTableRuleOutput(false)
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (booleanEqual(existingCustomer, true)),
            (numericLessEqualThan(applicationRiskScore, number("100"))),
            (numericGreaterThan(creditScore, number("600")))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

            // Compute output
            output_.setMatched(true)
            output_.postBureauRiskCategoryTable = "LOW"

            // Add annotation
            annotationSet_.addAnnotation("PostBureauRiskCategoryTable", 9, "")
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 10, annotation = "")
    private fun rule10(existingCustomer: Boolean?, applicationRiskScore: java.math.BigDecimal?, creditScore: java.math.BigDecimal?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor): com.gs.dmn.runtime.RuleOutput {
        // Rule metadata
        val drgRuleMetadata: com.gs.dmn.runtime.listener.Rule = com.gs.dmn.runtime.listener.Rule(10, "")

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

        // Apply rule
        var output_: PostBureauRiskCategoryTableRuleOutput = PostBureauRiskCategoryTableRuleOutput(false)
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (booleanEqual(existingCustomer, true)),
            (numericGreaterThan(applicationRiskScore, number("100"))),
            (numericLessThan(creditScore, number("590")))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

            // Compute output
            output_.setMatched(true)
            output_.postBureauRiskCategoryTable = "HIGH"

            // Add annotation
            annotationSet_.addAnnotation("PostBureauRiskCategoryTable", 10, "")
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 11, annotation = "")
    private fun rule11(existingCustomer: Boolean?, applicationRiskScore: java.math.BigDecimal?, creditScore: java.math.BigDecimal?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor): com.gs.dmn.runtime.RuleOutput {
        // Rule metadata
        val drgRuleMetadata: com.gs.dmn.runtime.listener.Rule = com.gs.dmn.runtime.listener.Rule(11, "")

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

        // Apply rule
        var output_: PostBureauRiskCategoryTableRuleOutput = PostBureauRiskCategoryTableRuleOutput(false)
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (booleanEqual(existingCustomer, true)),
            (numericGreaterThan(applicationRiskScore, number("100"))),
            (booleanAnd(numericGreaterEqualThan(creditScore, number("590")), numericLessEqualThan(creditScore, number("615"))))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

            // Compute output
            output_.setMatched(true)
            output_.postBureauRiskCategoryTable = "MEDIUM"

            // Add annotation
            annotationSet_.addAnnotation("PostBureauRiskCategoryTable", 11, "")
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 12, annotation = "")
    private fun rule12(existingCustomer: Boolean?, applicationRiskScore: java.math.BigDecimal?, creditScore: java.math.BigDecimal?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor): com.gs.dmn.runtime.RuleOutput {
        // Rule metadata
        val drgRuleMetadata: com.gs.dmn.runtime.listener.Rule = com.gs.dmn.runtime.listener.Rule(12, "")

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

        // Apply rule
        var output_: PostBureauRiskCategoryTableRuleOutput = PostBureauRiskCategoryTableRuleOutput(false)
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (booleanEqual(existingCustomer, true)),
            (numericGreaterThan(applicationRiskScore, number("100"))),
            (numericGreaterThan(creditScore, number("615")))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

            // Compute output
            output_.setMatched(true)
            output_.postBureauRiskCategoryTable = "LOW"

            // Add annotation
            annotationSet_.addAnnotation("PostBureauRiskCategoryTable", 12, "")
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_
    }


    companion object {
        val DRG_ELEMENT_METADATA = com.gs.dmn.runtime.listener.DRGElement(
            "",
            "PostBureauRiskCategoryTable",
            "",
            com.gs.dmn.runtime.annotation.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
            com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
            com.gs.dmn.runtime.annotation.HitPolicy.UNIQUE,
            13
        )

        val INSTANCE = PostBureauRiskCategoryTable()

        fun PostBureauRiskCategoryTable(existingCustomer: Boolean?, applicationRiskScore: java.math.BigDecimal?, creditScore: java.math.BigDecimal?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): String? {
            return INSTANCE.apply(existingCustomer, applicationRiskScore, creditScore, annotationSet_, eventListener_, externalExecutor_, cache_)
        }
    }
}
