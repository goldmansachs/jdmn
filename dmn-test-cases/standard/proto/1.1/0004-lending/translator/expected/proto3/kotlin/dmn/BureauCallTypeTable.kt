
import java.util.*
import java.util.stream.Collectors

@javax.annotation.Generated(value = ["bkm.ftl", "BureauCallTypeTable"])
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "BureauCallTypeTable",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNIQUE,
    rulesCount = 3
)
class BureauCallTypeTable : com.gs.dmn.runtime.DefaultDMNBaseDecision {
    private constructor() {}

    override fun applyMap(input_: MutableMap<String, String>, context_: com.gs.dmn.runtime.ExecutionContext): String? {
        try {
            return apply(input_.get("Pre-bureauRiskCategory"), context_)
        } catch (e: Exception) {
            logError("Cannot apply decision 'BureauCallTypeTable'", e)
            return null
        }
    }

    fun apply(preBureauRiskCategory: String?, context_: com.gs.dmn.runtime.ExecutionContext): String? {
        try {
            // Start BKM 'BureauCallTypeTable'
            var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
            var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
            var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
            var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
            val bureauCallTypeTableStartTime_ = System.currentTimeMillis()
            val bureauCallTypeTableArguments_ = com.gs.dmn.runtime.listener.Arguments()
            bureauCallTypeTableArguments_.put("Pre-bureauRiskCategory", preBureauRiskCategory)
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, bureauCallTypeTableArguments_)

            // Evaluate BKM 'BureauCallTypeTable'
            val output_: String? = evaluate(preBureauRiskCategory, context_)

            // End BKM 'BureauCallTypeTable'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, bureauCallTypeTableArguments_, output_, (System.currentTimeMillis() - bureauCallTypeTableStartTime_))

            return output_
        } catch (e: Exception) {
            logError("Exception caught in 'BureauCallTypeTable' evaluation", e)
            return null
        }
    }

    private inline fun evaluate(preBureauRiskCategory: String?, context_: com.gs.dmn.runtime.ExecutionContext): String? {
        var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
        var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
        var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
        var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
        // Apply rules and collect results
        val ruleOutputList_ = com.gs.dmn.runtime.RuleOutputList()
        ruleOutputList_.add(rule0(preBureauRiskCategory, context_))
        ruleOutputList_.add(rule1(preBureauRiskCategory, context_))
        ruleOutputList_.add(rule2(preBureauRiskCategory, context_))

        // Return results based on hit policy
        var output_: String?
        if (ruleOutputList_.noMatchedRules()) {
            // Default value
            output_ = null
        } else {
            val ruleOutput_: com.gs.dmn.runtime.RuleOutput? = ruleOutputList_.applySingle(com.gs.dmn.runtime.annotation.HitPolicy.UNIQUE)
            output_ = ruleOutput_?.let({ (ruleOutput_ as BureauCallTypeTableRuleOutput).bureauCallTypeTable })
        }

        return output_
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 0, annotation = "")
    private fun rule0(preBureauRiskCategory: String?, context_: com.gs.dmn.runtime.ExecutionContext): com.gs.dmn.runtime.RuleOutput {
        // Rule metadata
        val drgRuleMetadata: com.gs.dmn.runtime.listener.Rule = com.gs.dmn.runtime.listener.Rule(0, "")

        // Rule start
        var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
        var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
        var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
        var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

        // Apply rule
        var output_: BureauCallTypeTableRuleOutput = BureauCallTypeTableRuleOutput(false)
        if (ruleMatches(eventListener_, drgRuleMetadata,
            booleanOr(stringEqual(preBureauRiskCategory, "HIGH"), stringEqual(preBureauRiskCategory, "MEDIUM"))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

            // Compute output
            output_.setMatched(true)
            output_.bureauCallTypeTable = "FULL"
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 1, annotation = "")
    private fun rule1(preBureauRiskCategory: String?, context_: com.gs.dmn.runtime.ExecutionContext): com.gs.dmn.runtime.RuleOutput {
        // Rule metadata
        val drgRuleMetadata: com.gs.dmn.runtime.listener.Rule = com.gs.dmn.runtime.listener.Rule(1, "")

        // Rule start
        var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
        var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
        var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
        var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

        // Apply rule
        var output_: BureauCallTypeTableRuleOutput = BureauCallTypeTableRuleOutput(false)
        if (ruleMatches(eventListener_, drgRuleMetadata,
            stringEqual(preBureauRiskCategory, "LOW")
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

            // Compute output
            output_.setMatched(true)
            output_.bureauCallTypeTable = "MINI"
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 2, annotation = "")
    private fun rule2(preBureauRiskCategory: String?, context_: com.gs.dmn.runtime.ExecutionContext): com.gs.dmn.runtime.RuleOutput {
        // Rule metadata
        val drgRuleMetadata: com.gs.dmn.runtime.listener.Rule = com.gs.dmn.runtime.listener.Rule(2, "")

        // Rule start
        var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
        var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
        var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
        var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

        // Apply rule
        var output_: BureauCallTypeTableRuleOutput = BureauCallTypeTableRuleOutput(false)
        if (ruleMatches(eventListener_, drgRuleMetadata,
            booleanOr(stringEqual(preBureauRiskCategory, "VERY LOW"), stringEqual(preBureauRiskCategory, "DECLINE"))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

            // Compute output
            output_.setMatched(true)
            output_.bureauCallTypeTable = "NONE"
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_
    }


    companion object {
        val DRG_ELEMENT_METADATA : com.gs.dmn.runtime.listener.DRGElement = com.gs.dmn.runtime.listener.DRGElement(
            "",
            "BureauCallTypeTable",
            "",
            com.gs.dmn.runtime.annotation.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
            com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
            com.gs.dmn.runtime.annotation.HitPolicy.UNIQUE,
            3
        )

        private val INSTANCE = BureauCallTypeTable()

        @JvmStatic
        fun instance(): BureauCallTypeTable {
            return INSTANCE
        }
    }
}
