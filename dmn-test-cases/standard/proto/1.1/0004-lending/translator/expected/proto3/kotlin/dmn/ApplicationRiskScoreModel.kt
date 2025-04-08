
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
class ApplicationRiskScoreModel : com.gs.dmn.runtime.JavaTimeDMNBaseDecision {
    private constructor() {}

    override fun applyMap(input_: MutableMap<String, String>, context_: com.gs.dmn.runtime.ExecutionContext): kotlin.Number? {
        try {
            return apply(input_.get("Age")?.let({ number(it) }), input_.get("MaritalStatus"), input_.get("EmploymentStatus"), context_)
        } catch (e: Exception) {
            logError("Cannot apply decision 'ApplicationRiskScoreModel'", e)
            return null
        }
    }

    fun apply(age: kotlin.Number?, maritalStatus: String?, employmentStatus: String?, context_: com.gs.dmn.runtime.ExecutionContext): kotlin.Number? {
        try {
            // Start BKM 'ApplicationRiskScoreModel'
            var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
            var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
            var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
            var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
            val applicationRiskScoreModelStartTime_ = System.currentTimeMillis()
            val applicationRiskScoreModelArguments_ = com.gs.dmn.runtime.listener.Arguments()
            applicationRiskScoreModelArguments_.put("Age", age)
            applicationRiskScoreModelArguments_.put("MaritalStatus", maritalStatus)
            applicationRiskScoreModelArguments_.put("EmploymentStatus", employmentStatus)
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, applicationRiskScoreModelArguments_)

            // Evaluate BKM 'ApplicationRiskScoreModel'
            val output_: kotlin.Number? = evaluate(age, maritalStatus, employmentStatus, context_)

            // End BKM 'ApplicationRiskScoreModel'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, applicationRiskScoreModelArguments_, output_, (System.currentTimeMillis() - applicationRiskScoreModelStartTime_))

            return output_
        } catch (e: Exception) {
            logError("Exception caught in 'ApplicationRiskScoreModel' evaluation", e)
            return null
        }
    }

    private inline fun evaluate(age: kotlin.Number?, maritalStatus: String?, employmentStatus: String?, context_: com.gs.dmn.runtime.ExecutionContext): kotlin.Number? {
        var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
        var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
        var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
        var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
        // Apply rules and collect results
        val ruleOutputList_ = com.gs.dmn.runtime.RuleOutputList()
        ruleOutputList_.add(rule0(age, maritalStatus, employmentStatus, context_))
        ruleOutputList_.add(rule1(age, maritalStatus, employmentStatus, context_))
        ruleOutputList_.add(rule2(age, maritalStatus, employmentStatus, context_))
        ruleOutputList_.add(rule3(age, maritalStatus, employmentStatus, context_))
        ruleOutputList_.add(rule4(age, maritalStatus, employmentStatus, context_))
        ruleOutputList_.add(rule5(age, maritalStatus, employmentStatus, context_))
        ruleOutputList_.add(rule6(age, maritalStatus, employmentStatus, context_))
        ruleOutputList_.add(rule7(age, maritalStatus, employmentStatus, context_))
        ruleOutputList_.add(rule8(age, maritalStatus, employmentStatus, context_))
        ruleOutputList_.add(rule9(age, maritalStatus, employmentStatus, context_))
        ruleOutputList_.add(rule10(age, maritalStatus, employmentStatus, context_))

        // Return results based on hit policy
        var output_: kotlin.Number?
        if (ruleOutputList_.noMatchedRules()) {
            // Default value
            output_ = null
        } else {
            val ruleOutputs_: List<com.gs.dmn.runtime.RuleOutput> = ruleOutputList_.applyMultiple(com.gs.dmn.runtime.annotation.HitPolicy.COLLECT)
            output_ = sum(ruleOutputs_?.map({ x_ -> (x_ as ApplicationRiskScoreModelRuleOutput)?.applicationRiskScoreModel }))
        }

        return output_
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 0, annotation = "")
    private fun rule0(age: kotlin.Number?, maritalStatus: String?, employmentStatus: String?, context_: com.gs.dmn.runtime.ExecutionContext): com.gs.dmn.runtime.RuleOutput {
        // Rule metadata
        val drgRuleMetadata: com.gs.dmn.runtime.listener.Rule = com.gs.dmn.runtime.listener.Rule(0, "")

        // Rule start
        var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
        var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
        var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
        var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

        // Apply rule
        var output_: ApplicationRiskScoreModelRuleOutput = ApplicationRiskScoreModelRuleOutput(false)
        if (ruleMatches(eventListener_, drgRuleMetadata,
            booleanAnd(numericGreaterEqualThan(age, number("18")), numericLessEqualThan(age, number("21"))),
            true,
            true
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

            // Compute output
            output_.setMatched(true)
            output_.applicationRiskScoreModel = number("32")
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 1, annotation = "")
    private fun rule1(age: kotlin.Number?, maritalStatus: String?, employmentStatus: String?, context_: com.gs.dmn.runtime.ExecutionContext): com.gs.dmn.runtime.RuleOutput {
        // Rule metadata
        val drgRuleMetadata: com.gs.dmn.runtime.listener.Rule = com.gs.dmn.runtime.listener.Rule(1, "")

        // Rule start
        var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
        var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
        var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
        var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

        // Apply rule
        var output_: ApplicationRiskScoreModelRuleOutput = ApplicationRiskScoreModelRuleOutput(false)
        if (ruleMatches(eventListener_, drgRuleMetadata,
            booleanAnd(numericGreaterEqualThan(age, number("22")), numericLessEqualThan(age, number("25"))),
            true,
            true
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

            // Compute output
            output_.setMatched(true)
            output_.applicationRiskScoreModel = number("35")
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 2, annotation = "")
    private fun rule2(age: kotlin.Number?, maritalStatus: String?, employmentStatus: String?, context_: com.gs.dmn.runtime.ExecutionContext): com.gs.dmn.runtime.RuleOutput {
        // Rule metadata
        val drgRuleMetadata: com.gs.dmn.runtime.listener.Rule = com.gs.dmn.runtime.listener.Rule(2, "")

        // Rule start
        var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
        var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
        var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
        var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

        // Apply rule
        var output_: ApplicationRiskScoreModelRuleOutput = ApplicationRiskScoreModelRuleOutput(false)
        if (ruleMatches(eventListener_, drgRuleMetadata,
            booleanAnd(numericGreaterEqualThan(age, number("26")), numericLessEqualThan(age, number("35"))),
            true,
            true
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

            // Compute output
            output_.setMatched(true)
            output_.applicationRiskScoreModel = number("40")
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 3, annotation = "")
    private fun rule3(age: kotlin.Number?, maritalStatus: String?, employmentStatus: String?, context_: com.gs.dmn.runtime.ExecutionContext): com.gs.dmn.runtime.RuleOutput {
        // Rule metadata
        val drgRuleMetadata: com.gs.dmn.runtime.listener.Rule = com.gs.dmn.runtime.listener.Rule(3, "")

        // Rule start
        var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
        var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
        var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
        var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

        // Apply rule
        var output_: ApplicationRiskScoreModelRuleOutput = ApplicationRiskScoreModelRuleOutput(false)
        if (ruleMatches(eventListener_, drgRuleMetadata,
            booleanAnd(numericGreaterEqualThan(age, number("36")), numericLessEqualThan(age, number("49"))),
            true,
            true
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

            // Compute output
            output_.setMatched(true)
            output_.applicationRiskScoreModel = number("43")
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 4, annotation = "")
    private fun rule4(age: kotlin.Number?, maritalStatus: String?, employmentStatus: String?, context_: com.gs.dmn.runtime.ExecutionContext): com.gs.dmn.runtime.RuleOutput {
        // Rule metadata
        val drgRuleMetadata: com.gs.dmn.runtime.listener.Rule = com.gs.dmn.runtime.listener.Rule(4, "")

        // Rule start
        var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
        var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
        var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
        var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

        // Apply rule
        var output_: ApplicationRiskScoreModelRuleOutput = ApplicationRiskScoreModelRuleOutput(false)
        if (ruleMatches(eventListener_, drgRuleMetadata,
            numericGreaterEqualThan(age, number("50")),
            true,
            true
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

            // Compute output
            output_.setMatched(true)
            output_.applicationRiskScoreModel = number("48")
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 5, annotation = "")
    private fun rule5(age: kotlin.Number?, maritalStatus: String?, employmentStatus: String?, context_: com.gs.dmn.runtime.ExecutionContext): com.gs.dmn.runtime.RuleOutput {
        // Rule metadata
        val drgRuleMetadata: com.gs.dmn.runtime.listener.Rule = com.gs.dmn.runtime.listener.Rule(5, "")

        // Rule start
        var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
        var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
        var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
        var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

        // Apply rule
        var output_: ApplicationRiskScoreModelRuleOutput = ApplicationRiskScoreModelRuleOutput(false)
        if (ruleMatches(eventListener_, drgRuleMetadata,
            true,
            stringEqual(maritalStatus, "S"),
            true
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

            // Compute output
            output_.setMatched(true)
            output_.applicationRiskScoreModel = number("25")
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 6, annotation = "")
    private fun rule6(age: kotlin.Number?, maritalStatus: String?, employmentStatus: String?, context_: com.gs.dmn.runtime.ExecutionContext): com.gs.dmn.runtime.RuleOutput {
        // Rule metadata
        val drgRuleMetadata: com.gs.dmn.runtime.listener.Rule = com.gs.dmn.runtime.listener.Rule(6, "")

        // Rule start
        var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
        var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
        var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
        var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

        // Apply rule
        var output_: ApplicationRiskScoreModelRuleOutput = ApplicationRiskScoreModelRuleOutput(false)
        if (ruleMatches(eventListener_, drgRuleMetadata,
            true,
            stringEqual(maritalStatus, "M"),
            true
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

            // Compute output
            output_.setMatched(true)
            output_.applicationRiskScoreModel = number("45")
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 7, annotation = "")
    private fun rule7(age: kotlin.Number?, maritalStatus: String?, employmentStatus: String?, context_: com.gs.dmn.runtime.ExecutionContext): com.gs.dmn.runtime.RuleOutput {
        // Rule metadata
        val drgRuleMetadata: com.gs.dmn.runtime.listener.Rule = com.gs.dmn.runtime.listener.Rule(7, "")

        // Rule start
        var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
        var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
        var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
        var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

        // Apply rule
        var output_: ApplicationRiskScoreModelRuleOutput = ApplicationRiskScoreModelRuleOutput(false)
        if (ruleMatches(eventListener_, drgRuleMetadata,
            true,
            true,
            stringEqual(employmentStatus, "UNEMPLOYED")
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

            // Compute output
            output_.setMatched(true)
            output_.applicationRiskScoreModel = number("15")
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 8, annotation = "")
    private fun rule8(age: kotlin.Number?, maritalStatus: String?, employmentStatus: String?, context_: com.gs.dmn.runtime.ExecutionContext): com.gs.dmn.runtime.RuleOutput {
        // Rule metadata
        val drgRuleMetadata: com.gs.dmn.runtime.listener.Rule = com.gs.dmn.runtime.listener.Rule(8, "")

        // Rule start
        var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
        var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
        var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
        var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

        // Apply rule
        var output_: ApplicationRiskScoreModelRuleOutput = ApplicationRiskScoreModelRuleOutput(false)
        if (ruleMatches(eventListener_, drgRuleMetadata,
            true,
            true,
            stringEqual(employmentStatus, "EMPLOYED")
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

            // Compute output
            output_.setMatched(true)
            output_.applicationRiskScoreModel = number("45")
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 9, annotation = "")
    private fun rule9(age: kotlin.Number?, maritalStatus: String?, employmentStatus: String?, context_: com.gs.dmn.runtime.ExecutionContext): com.gs.dmn.runtime.RuleOutput {
        // Rule metadata
        val drgRuleMetadata: com.gs.dmn.runtime.listener.Rule = com.gs.dmn.runtime.listener.Rule(9, "")

        // Rule start
        var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
        var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
        var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
        var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

        // Apply rule
        var output_: ApplicationRiskScoreModelRuleOutput = ApplicationRiskScoreModelRuleOutput(false)
        if (ruleMatches(eventListener_, drgRuleMetadata,
            true,
            true,
            stringEqual(employmentStatus, "SELF-EMPLOYED")
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

            // Compute output
            output_.setMatched(true)
            output_.applicationRiskScoreModel = number("36")
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 10, annotation = "")
    private fun rule10(age: kotlin.Number?, maritalStatus: String?, employmentStatus: String?, context_: com.gs.dmn.runtime.ExecutionContext): com.gs.dmn.runtime.RuleOutput {
        // Rule metadata
        val drgRuleMetadata: com.gs.dmn.runtime.listener.Rule = com.gs.dmn.runtime.listener.Rule(10, "")

        // Rule start
        var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
        var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
        var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
        var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

        // Apply rule
        var output_: ApplicationRiskScoreModelRuleOutput = ApplicationRiskScoreModelRuleOutput(false)
        if (ruleMatches(eventListener_, drgRuleMetadata,
            true,
            true,
            stringEqual(employmentStatus, "STUDENT")
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

            // Compute output
            output_.setMatched(true)
            output_.applicationRiskScoreModel = number("18")
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_
    }


    companion object {
        val DRG_ELEMENT_METADATA : com.gs.dmn.runtime.listener.DRGElement = com.gs.dmn.runtime.listener.DRGElement(
            "",
            "ApplicationRiskScoreModel",
            "",
            com.gs.dmn.runtime.annotation.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
            com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
            com.gs.dmn.runtime.annotation.HitPolicy.COLLECT,
            11
        )

        private val INSTANCE = ApplicationRiskScoreModel()

        @JvmStatic
        fun instance(): ApplicationRiskScoreModel {
            return INSTANCE
        }
    }
}
