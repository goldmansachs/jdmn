
import java.util.*
import java.util.stream.Collectors

@javax.annotation.Generated(value = ["bkm.ftl", "ApplicationRiskScoreModel"])
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "ApplicationRiskScoreModel",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.COLLECT,
    rulesCount = 11
)
class ApplicationRiskScoreModel : com.gs.dmn.runtime.DefaultDMNBaseDecision {
    private constructor() {}

    private fun apply(age: java.math.BigDecimal?, maritalStatus: String?, employmentStatus: String?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): java.math.BigDecimal? {
        try {
            // Start BKM 'ApplicationRiskScoreModel'
            val applicationRiskScoreModelStartTime_ = System.currentTimeMillis()
            val applicationRiskScoreModelArguments_ = com.gs.dmn.runtime.listener.Arguments()
            applicationRiskScoreModelArguments_.put("Age", age)
            applicationRiskScoreModelArguments_.put("MaritalStatus", maritalStatus)
            applicationRiskScoreModelArguments_.put("EmploymentStatus", employmentStatus)
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, applicationRiskScoreModelArguments_)

            // Evaluate BKM 'ApplicationRiskScoreModel'
            val output_: java.math.BigDecimal? = evaluate(age, maritalStatus, employmentStatus, annotationSet_, eventListener_, externalExecutor_, cache_)

            // End BKM 'ApplicationRiskScoreModel'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, applicationRiskScoreModelArguments_, output_, (System.currentTimeMillis() - applicationRiskScoreModelStartTime_))

            return output_
        } catch (e: Exception) {
            logError("Exception caught in 'ApplicationRiskScoreModel' evaluation", e)
            return null
        }
    }

    private inline fun evaluate(age: java.math.BigDecimal?, maritalStatus: String?, employmentStatus: String?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): java.math.BigDecimal? {
        // Apply rules and collect results
        val ruleOutputList_ = com.gs.dmn.runtime.RuleOutputList()
        ruleOutputList_.add(rule0(age, maritalStatus, employmentStatus, annotationSet_, eventListener_, externalExecutor_))
        ruleOutputList_.add(rule1(age, maritalStatus, employmentStatus, annotationSet_, eventListener_, externalExecutor_))
        ruleOutputList_.add(rule2(age, maritalStatus, employmentStatus, annotationSet_, eventListener_, externalExecutor_))
        ruleOutputList_.add(rule3(age, maritalStatus, employmentStatus, annotationSet_, eventListener_, externalExecutor_))
        ruleOutputList_.add(rule4(age, maritalStatus, employmentStatus, annotationSet_, eventListener_, externalExecutor_))
        ruleOutputList_.add(rule5(age, maritalStatus, employmentStatus, annotationSet_, eventListener_, externalExecutor_))
        ruleOutputList_.add(rule6(age, maritalStatus, employmentStatus, annotationSet_, eventListener_, externalExecutor_))
        ruleOutputList_.add(rule7(age, maritalStatus, employmentStatus, annotationSet_, eventListener_, externalExecutor_))
        ruleOutputList_.add(rule8(age, maritalStatus, employmentStatus, annotationSet_, eventListener_, externalExecutor_))
        ruleOutputList_.add(rule9(age, maritalStatus, employmentStatus, annotationSet_, eventListener_, externalExecutor_))
        ruleOutputList_.add(rule10(age, maritalStatus, employmentStatus, annotationSet_, eventListener_, externalExecutor_))

        // Return results based on hit policy
        var output_: java.math.BigDecimal?
        if (ruleOutputList_.noMatchedRules()) {
            // Default value
            output_ = null
        } else {
            val ruleOutputs_: List<com.gs.dmn.runtime.RuleOutput> = ruleOutputList_.applyMultiple(com.gs.dmn.runtime.annotation.HitPolicy.COLLECT)
            output_ = sum(ruleOutputs_?.map({ o -> (o as ApplicationRiskScoreModelRuleOutput)?.applicationRiskScoreModel }))
        }

        return output_
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 0, annotation = "")
    private fun rule0(age: java.math.BigDecimal?, maritalStatus: String?, employmentStatus: String?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor): com.gs.dmn.runtime.RuleOutput {
        // Rule metadata
        val drgRuleMetadata: com.gs.dmn.runtime.listener.Rule = com.gs.dmn.runtime.listener.Rule(0, "")

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

        // Apply rule
        var output_: ApplicationRiskScoreModelRuleOutput = ApplicationRiskScoreModelRuleOutput(false)
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (booleanAnd(numericGreaterEqualThan(age, number("18")), numericLessEqualThan(age, number("21")))),
            true,
            true
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

            // Compute output
            output_.setMatched(true)
            output_.applicationRiskScoreModel = number("32")

            // Add annotation
            annotationSet_.addAnnotation("ApplicationRiskScoreModel", 0, "")
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 1, annotation = "")
    private fun rule1(age: java.math.BigDecimal?, maritalStatus: String?, employmentStatus: String?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor): com.gs.dmn.runtime.RuleOutput {
        // Rule metadata
        val drgRuleMetadata: com.gs.dmn.runtime.listener.Rule = com.gs.dmn.runtime.listener.Rule(1, "")

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

        // Apply rule
        var output_: ApplicationRiskScoreModelRuleOutput = ApplicationRiskScoreModelRuleOutput(false)
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (booleanAnd(numericGreaterEqualThan(age, number("22")), numericLessEqualThan(age, number("25")))),
            true,
            true
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

            // Compute output
            output_.setMatched(true)
            output_.applicationRiskScoreModel = number("35")

            // Add annotation
            annotationSet_.addAnnotation("ApplicationRiskScoreModel", 1, "")
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 2, annotation = "")
    private fun rule2(age: java.math.BigDecimal?, maritalStatus: String?, employmentStatus: String?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor): com.gs.dmn.runtime.RuleOutput {
        // Rule metadata
        val drgRuleMetadata: com.gs.dmn.runtime.listener.Rule = com.gs.dmn.runtime.listener.Rule(2, "")

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

        // Apply rule
        var output_: ApplicationRiskScoreModelRuleOutput = ApplicationRiskScoreModelRuleOutput(false)
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (booleanAnd(numericGreaterEqualThan(age, number("26")), numericLessEqualThan(age, number("35")))),
            true,
            true
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

            // Compute output
            output_.setMatched(true)
            output_.applicationRiskScoreModel = number("40")

            // Add annotation
            annotationSet_.addAnnotation("ApplicationRiskScoreModel", 2, "")
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 3, annotation = "")
    private fun rule3(age: java.math.BigDecimal?, maritalStatus: String?, employmentStatus: String?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor): com.gs.dmn.runtime.RuleOutput {
        // Rule metadata
        val drgRuleMetadata: com.gs.dmn.runtime.listener.Rule = com.gs.dmn.runtime.listener.Rule(3, "")

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

        // Apply rule
        var output_: ApplicationRiskScoreModelRuleOutput = ApplicationRiskScoreModelRuleOutput(false)
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (booleanAnd(numericGreaterEqualThan(age, number("36")), numericLessEqualThan(age, number("49")))),
            true,
            true
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

            // Compute output
            output_.setMatched(true)
            output_.applicationRiskScoreModel = number("43")

            // Add annotation
            annotationSet_.addAnnotation("ApplicationRiskScoreModel", 3, "")
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 4, annotation = "")
    private fun rule4(age: java.math.BigDecimal?, maritalStatus: String?, employmentStatus: String?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor): com.gs.dmn.runtime.RuleOutput {
        // Rule metadata
        val drgRuleMetadata: com.gs.dmn.runtime.listener.Rule = com.gs.dmn.runtime.listener.Rule(4, "")

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

        // Apply rule
        var output_: ApplicationRiskScoreModelRuleOutput = ApplicationRiskScoreModelRuleOutput(false)
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (numericGreaterEqualThan(age, number("50"))),
            true,
            true
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

            // Compute output
            output_.setMatched(true)
            output_.applicationRiskScoreModel = number("48")

            // Add annotation
            annotationSet_.addAnnotation("ApplicationRiskScoreModel", 4, "")
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 5, annotation = "")
    private fun rule5(age: java.math.BigDecimal?, maritalStatus: String?, employmentStatus: String?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor): com.gs.dmn.runtime.RuleOutput {
        // Rule metadata
        val drgRuleMetadata: com.gs.dmn.runtime.listener.Rule = com.gs.dmn.runtime.listener.Rule(5, "")

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

        // Apply rule
        var output_: ApplicationRiskScoreModelRuleOutput = ApplicationRiskScoreModelRuleOutput(false)
        if (ruleMatches(eventListener_, drgRuleMetadata,
            true,
            (stringEqual(maritalStatus, "S")),
            true
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

            // Compute output
            output_.setMatched(true)
            output_.applicationRiskScoreModel = number("25")

            // Add annotation
            annotationSet_.addAnnotation("ApplicationRiskScoreModel", 5, "")
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 6, annotation = "")
    private fun rule6(age: java.math.BigDecimal?, maritalStatus: String?, employmentStatus: String?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor): com.gs.dmn.runtime.RuleOutput {
        // Rule metadata
        val drgRuleMetadata: com.gs.dmn.runtime.listener.Rule = com.gs.dmn.runtime.listener.Rule(6, "")

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

        // Apply rule
        var output_: ApplicationRiskScoreModelRuleOutput = ApplicationRiskScoreModelRuleOutput(false)
        if (ruleMatches(eventListener_, drgRuleMetadata,
            true,
            (stringEqual(maritalStatus, "M")),
            true
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

            // Compute output
            output_.setMatched(true)
            output_.applicationRiskScoreModel = number("45")

            // Add annotation
            annotationSet_.addAnnotation("ApplicationRiskScoreModel", 6, "")
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 7, annotation = "")
    private fun rule7(age: java.math.BigDecimal?, maritalStatus: String?, employmentStatus: String?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor): com.gs.dmn.runtime.RuleOutput {
        // Rule metadata
        val drgRuleMetadata: com.gs.dmn.runtime.listener.Rule = com.gs.dmn.runtime.listener.Rule(7, "")

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

        // Apply rule
        var output_: ApplicationRiskScoreModelRuleOutput = ApplicationRiskScoreModelRuleOutput(false)
        if (ruleMatches(eventListener_, drgRuleMetadata,
            true,
            true,
            (stringEqual(employmentStatus, "UNEMPLOYED"))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

            // Compute output
            output_.setMatched(true)
            output_.applicationRiskScoreModel = number("15")

            // Add annotation
            annotationSet_.addAnnotation("ApplicationRiskScoreModel", 7, "")
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 8, annotation = "")
    private fun rule8(age: java.math.BigDecimal?, maritalStatus: String?, employmentStatus: String?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor): com.gs.dmn.runtime.RuleOutput {
        // Rule metadata
        val drgRuleMetadata: com.gs.dmn.runtime.listener.Rule = com.gs.dmn.runtime.listener.Rule(8, "")

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

        // Apply rule
        var output_: ApplicationRiskScoreModelRuleOutput = ApplicationRiskScoreModelRuleOutput(false)
        if (ruleMatches(eventListener_, drgRuleMetadata,
            true,
            true,
            (stringEqual(employmentStatus, "EMPLOYED"))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

            // Compute output
            output_.setMatched(true)
            output_.applicationRiskScoreModel = number("45")

            // Add annotation
            annotationSet_.addAnnotation("ApplicationRiskScoreModel", 8, "")
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 9, annotation = "")
    private fun rule9(age: java.math.BigDecimal?, maritalStatus: String?, employmentStatus: String?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor): com.gs.dmn.runtime.RuleOutput {
        // Rule metadata
        val drgRuleMetadata: com.gs.dmn.runtime.listener.Rule = com.gs.dmn.runtime.listener.Rule(9, "")

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

        // Apply rule
        var output_: ApplicationRiskScoreModelRuleOutput = ApplicationRiskScoreModelRuleOutput(false)
        if (ruleMatches(eventListener_, drgRuleMetadata,
            true,
            true,
            (stringEqual(employmentStatus, "SELF-EMPLOYED"))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

            // Compute output
            output_.setMatched(true)
            output_.applicationRiskScoreModel = number("36")

            // Add annotation
            annotationSet_.addAnnotation("ApplicationRiskScoreModel", 9, "")
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 10, annotation = "")
    private fun rule10(age: java.math.BigDecimal?, maritalStatus: String?, employmentStatus: String?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor): com.gs.dmn.runtime.RuleOutput {
        // Rule metadata
        val drgRuleMetadata: com.gs.dmn.runtime.listener.Rule = com.gs.dmn.runtime.listener.Rule(10, "")

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

        // Apply rule
        var output_: ApplicationRiskScoreModelRuleOutput = ApplicationRiskScoreModelRuleOutput(false)
        if (ruleMatches(eventListener_, drgRuleMetadata,
            true,
            true,
            (stringEqual(employmentStatus, "STUDENT"))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

            // Compute output
            output_.setMatched(true)
            output_.applicationRiskScoreModel = number("18")

            // Add annotation
            annotationSet_.addAnnotation("ApplicationRiskScoreModel", 10, "")
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_
    }


    companion object {
        val DRG_ELEMENT_METADATA = com.gs.dmn.runtime.listener.DRGElement(
            "",
            "ApplicationRiskScoreModel",
            "",
            com.gs.dmn.runtime.annotation.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
            com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
            com.gs.dmn.runtime.annotation.HitPolicy.COLLECT,
            11
        )

        val INSTANCE = ApplicationRiskScoreModel()

        fun ApplicationRiskScoreModel(age: java.math.BigDecimal?, maritalStatus: String?, employmentStatus: String?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): java.math.BigDecimal? {
            return INSTANCE.apply(age, maritalStatus, employmentStatus, annotationSet_, eventListener_, externalExecutor_, cache_)
        }
    }
}
