
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

    private fun apply(preBureauRiskCategory: String?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): String? {
        try {
            // Start BKM 'BureauCallTypeTable'
            val bureauCallTypeTableStartTime_ = System.currentTimeMillis()
            val bureauCallTypeTableArguments_ = com.gs.dmn.runtime.listener.Arguments()
            bureauCallTypeTableArguments_.put("PreBureauRiskCategory", preBureauRiskCategory)
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, bureauCallTypeTableArguments_)

            // Evaluate BKM 'BureauCallTypeTable'
            val output_: String? = evaluate(preBureauRiskCategory, annotationSet_, eventListener_, externalExecutor_, cache_)

            // End BKM 'BureauCallTypeTable'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, bureauCallTypeTableArguments_, output_, (System.currentTimeMillis() - bureauCallTypeTableStartTime_))

            return output_
        } catch (e: Exception) {
            logError("Exception caught in 'BureauCallTypeTable' evaluation", e)
            return null
        }
    }

    private inline fun evaluate(preBureauRiskCategory: String?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): String? {
        // Apply rules and collect results
        val ruleOutputList_ = com.gs.dmn.runtime.RuleOutputList()
        ruleOutputList_.add(rule0(preBureauRiskCategory, annotationSet_, eventListener_, externalExecutor_))
        ruleOutputList_.add(rule1(preBureauRiskCategory, annotationSet_, eventListener_, externalExecutor_))
        ruleOutputList_.add(rule2(preBureauRiskCategory, annotationSet_, eventListener_, externalExecutor_))

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
    private fun rule0(preBureauRiskCategory: String?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor): com.gs.dmn.runtime.RuleOutput {
        // Rule metadata
        val drgRuleMetadata: com.gs.dmn.runtime.listener.Rule = com.gs.dmn.runtime.listener.Rule(0, "")

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

        // Apply rule
        var output_: BureauCallTypeTableRuleOutput = BureauCallTypeTableRuleOutput(false)
        if (ruleMatches(eventListener_, drgRuleMetadata,
            booleanOr((stringEqual(preBureauRiskCategory, "HIGH")), (stringEqual(preBureauRiskCategory, "MEDIUM")))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

            // Compute output
            output_.setMatched(true)
            output_.bureauCallTypeTable = "FULL"

            // Add annotation
            annotationSet_.addAnnotation("BureauCallTypeTable", 0, "")
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 1, annotation = "")
    private fun rule1(preBureauRiskCategory: String?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor): com.gs.dmn.runtime.RuleOutput {
        // Rule metadata
        val drgRuleMetadata: com.gs.dmn.runtime.listener.Rule = com.gs.dmn.runtime.listener.Rule(1, "")

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

        // Apply rule
        var output_: BureauCallTypeTableRuleOutput = BureauCallTypeTableRuleOutput(false)
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (stringEqual(preBureauRiskCategory, "LOW"))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

            // Compute output
            output_.setMatched(true)
            output_.bureauCallTypeTable = "MINI"

            // Add annotation
            annotationSet_.addAnnotation("BureauCallTypeTable", 1, "")
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 2, annotation = "")
    private fun rule2(preBureauRiskCategory: String?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor): com.gs.dmn.runtime.RuleOutput {
        // Rule metadata
        val drgRuleMetadata: com.gs.dmn.runtime.listener.Rule = com.gs.dmn.runtime.listener.Rule(2, "")

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

        // Apply rule
        var output_: BureauCallTypeTableRuleOutput = BureauCallTypeTableRuleOutput(false)
        if (ruleMatches(eventListener_, drgRuleMetadata,
            booleanOr((stringEqual(preBureauRiskCategory, "VERY LOW")), (stringEqual(preBureauRiskCategory, "DECLINE")))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

            // Compute output
            output_.setMatched(true)
            output_.bureauCallTypeTable = "NONE"

            // Add annotation
            annotationSet_.addAnnotation("BureauCallTypeTable", 2, "")
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_
    }


    companion object {
        val DRG_ELEMENT_METADATA = com.gs.dmn.runtime.listener.DRGElement(
            "",
            "BureauCallTypeTable",
            "",
            com.gs.dmn.runtime.annotation.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
            com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
            com.gs.dmn.runtime.annotation.HitPolicy.UNIQUE,
            3
        )

        val INSTANCE = BureauCallTypeTable()

        fun BureauCallTypeTable(preBureauRiskCategory: String?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): String? {
            return INSTANCE.apply(preBureauRiskCategory, annotationSet_, eventListener_, externalExecutor_, cache_)
        }
    }
}
