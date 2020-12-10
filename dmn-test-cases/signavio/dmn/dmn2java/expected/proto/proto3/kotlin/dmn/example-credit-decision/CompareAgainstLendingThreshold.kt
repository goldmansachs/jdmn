
import java.util.*
import java.util.stream.Collectors

@javax.annotation.Generated(value = ["signavio-decision.ftl", "compareAgainstLendingThreshold"])
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "compareAgainstLendingThreshold",
    label = "Compare against lending threshold",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.ANY,
    rulesCount = 2
)
class CompareAgainstLendingThreshold(val assessApplicantAge : AssessApplicantAge = AssessApplicantAge(), val assessIssueRisk : AssessIssueRisk = AssessIssueRisk()) : com.gs.dmn.signavio.runtime.DefaultSignavioBaseDecision() {
    fun apply(applicant: String?, currentRiskAppetite: String?, lendingThreshold: String?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet): java.math.BigDecimal? {
        return try {
            apply(applicant?.let({ com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(it, object : com.fasterxml.jackson.core.type.TypeReference<type.ApplicantImpl>() {}) }), currentRiskAppetite?.let({ number(it) }), lendingThreshold?.let({ number(it) }), annotationSet_, com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), com.gs.dmn.runtime.cache.DefaultCache())
        } catch (e: Exception) {
            logError("Cannot apply decision 'CompareAgainstLendingThreshold'", e)
            null
        }
    }

    fun apply(applicant: String?, currentRiskAppetite: String?, lendingThreshold: String?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): java.math.BigDecimal? {
        return try {
            apply(applicant?.let({ com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(it, object : com.fasterxml.jackson.core.type.TypeReference<type.ApplicantImpl>() {}) }), currentRiskAppetite?.let({ number(it) }), lendingThreshold?.let({ number(it) }), annotationSet_, eventListener_, externalExecutor_, cache_)
        } catch (e: Exception) {
            logError("Cannot apply decision 'CompareAgainstLendingThreshold'", e)
            null
        }
    }

    fun apply(applicant: type.Applicant?, currentRiskAppetite: java.math.BigDecimal?, lendingThreshold: java.math.BigDecimal?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet): java.math.BigDecimal? {
        return apply(applicant, currentRiskAppetite, lendingThreshold, annotationSet_, com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), com.gs.dmn.runtime.cache.DefaultCache())
    }

    fun apply(applicant: type.Applicant?, currentRiskAppetite: java.math.BigDecimal?, lendingThreshold: java.math.BigDecimal?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): java.math.BigDecimal? {
        try {
            // Start decision 'compareAgainstLendingThreshold'
            val compareAgainstLendingThresholdStartTime_ = System.currentTimeMillis()
            val compareAgainstLendingThresholdArguments_ = com.gs.dmn.runtime.listener.Arguments()
            compareAgainstLendingThresholdArguments_.put("Applicant", applicant)
            compareAgainstLendingThresholdArguments_.put("Current risk appetite", currentRiskAppetite)
            compareAgainstLendingThresholdArguments_.put("Lending threshold", lendingThreshold)
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, compareAgainstLendingThresholdArguments_)

            // Apply child decisions
            val assessApplicantAge: java.math.BigDecimal? = this.assessApplicantAge.apply(applicant, annotationSet_, eventListener_, externalExecutor_, cache_)
            val assessIssueRisk: java.math.BigDecimal? = this.assessIssueRisk.apply(applicant, currentRiskAppetite, annotationSet_, eventListener_, externalExecutor_, cache_)

            // Evaluate decision 'compareAgainstLendingThreshold'
            val output_: java.math.BigDecimal? = evaluate(assessApplicantAge, assessIssueRisk, lendingThreshold, annotationSet_, eventListener_, externalExecutor_, cache_)

            // End decision 'compareAgainstLendingThreshold'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, compareAgainstLendingThresholdArguments_, output_, (System.currentTimeMillis() - compareAgainstLendingThresholdStartTime_))

            return output_
        } catch (e: Exception) {
            logError("Exception caught in 'compareAgainstLendingThreshold' evaluation", e)
            return null
        }
    }

    fun apply(compareAgainstLendingThresholdRequest_: proto.CompareAgainstLendingThresholdRequest, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet): proto.CompareAgainstLendingThresholdResponse {
        return apply(compareAgainstLendingThresholdRequest_, annotationSet_, com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), com.gs.dmn.runtime.cache.DefaultCache())
    }

    fun apply(compareAgainstLendingThresholdRequest_: proto.CompareAgainstLendingThresholdRequest, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): proto.CompareAgainstLendingThresholdResponse {
        // Create arguments from Request Message
        val applicant: type.Applicant? = type.Applicant.toApplicant(compareAgainstLendingThresholdRequest_.getApplicant())
        val currentRiskAppetite: java.math.BigDecimal? = java.math.BigDecimal.valueOf(compareAgainstLendingThresholdRequest_.getCurrentRiskAppetite())
        val lendingThreshold: java.math.BigDecimal? = java.math.BigDecimal.valueOf(compareAgainstLendingThresholdRequest_.getLendingThreshold())

        // Invoke apply method
        val output_: java.math.BigDecimal? = apply(applicant, currentRiskAppetite, lendingThreshold, annotationSet_, eventListener_, externalExecutor_, cache_)

        // Convert output to Response Message
        val builder_: proto.CompareAgainstLendingThresholdResponse.Builder = proto.CompareAgainstLendingThresholdResponse.newBuilder()
        val outputProto_ = (if (output_ == null) 0.0 else output_!!.toDouble())
        builder_.setCompareAgainstLendingThreshold(outputProto_)
        return builder_.build()
    }

    private inline fun evaluate(assessApplicantAge: java.math.BigDecimal?, assessIssueRisk: java.math.BigDecimal?, lendingThreshold: java.math.BigDecimal?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): java.math.BigDecimal? {
        // Apply rules and collect results
        val ruleOutputList_ = com.gs.dmn.runtime.RuleOutputList()
        ruleOutputList_.add(rule0(assessApplicantAge, assessIssueRisk, lendingThreshold, annotationSet_, eventListener_, externalExecutor_))
        ruleOutputList_.add(rule1(assessApplicantAge, assessIssueRisk, lendingThreshold, annotationSet_, eventListener_, externalExecutor_))

        // Return results based on hit policy
        var output_: java.math.BigDecimal?
        if (ruleOutputList_.noMatchedRules()) {
            // Default value
            output_ = null
        } else {
            val ruleOutput_: com.gs.dmn.runtime.RuleOutput? = ruleOutputList_.applySingle(com.gs.dmn.runtime.annotation.HitPolicy.ANY)
            output_ = ruleOutput_?.let({ (ruleOutput_ as CompareAgainstLendingThresholdRuleOutput).compareAgainstLendingThreshold })
        }

        return output_
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 0, annotation = "string(\"Raw issue score is \") + string(assessIssueRisk) + string(\", Age-weighted score is \") + string(assessApplicantAge) + string(\", Acceptance threshold is \") + string(lendingThreshold)")
    private fun rule0(assessApplicantAge: java.math.BigDecimal?, assessIssueRisk: java.math.BigDecimal?, lendingThreshold: java.math.BigDecimal?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor): com.gs.dmn.runtime.RuleOutput {
        // Rule metadata
        val drgRuleMetadata: com.gs.dmn.runtime.listener.Rule = com.gs.dmn.runtime.listener.Rule(0, "string(\"Raw issue score is \") + string(assessIssueRisk) + string(\", Age-weighted score is \") + string(assessApplicantAge) + string(\", Acceptance threshold is \") + string(lendingThreshold)")

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

        // Apply rule
        var output_: CompareAgainstLendingThresholdRuleOutput = CompareAgainstLendingThresholdRuleOutput(false)
        if (ruleMatches(eventListener_, drgRuleMetadata,
            booleanNot((lendingThreshold == null))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

            // Compute output
            output_.setMatched(true)
            output_.compareAgainstLendingThreshold = numericSubtract(numericAdd(assessIssueRisk, assessApplicantAge), lendingThreshold)

            // Add annotation
            annotationSet_.addAnnotation("compareAgainstLendingThreshold", 0, stringAdd(stringAdd(stringAdd(stringAdd(stringAdd(string("Raw issue score is "), string(assessIssueRisk)), string(", Age-weighted score is ")), string(assessApplicantAge)), string(", Acceptance threshold is ")), string(lendingThreshold)))
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 1, annotation = "string(\"Error: threshold undefined\")")
    private fun rule1(assessApplicantAge: java.math.BigDecimal?, assessIssueRisk: java.math.BigDecimal?, lendingThreshold: java.math.BigDecimal?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor): com.gs.dmn.runtime.RuleOutput {
        // Rule metadata
        val drgRuleMetadata: com.gs.dmn.runtime.listener.Rule = com.gs.dmn.runtime.listener.Rule(1, "string(\"Error: threshold undefined\")")

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

        // Apply rule
        var output_: CompareAgainstLendingThresholdRuleOutput = CompareAgainstLendingThresholdRuleOutput(false)
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (lendingThreshold == null)
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

            // Compute output
            output_.setMatched(true)
            output_.compareAgainstLendingThreshold = number("0")

            // Add annotation
            annotationSet_.addAnnotation("compareAgainstLendingThreshold", 1, string("Error: threshold undefined"))
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_
    }


    companion object {
        val DRG_ELEMENT_METADATA : com.gs.dmn.runtime.listener.DRGElement = com.gs.dmn.runtime.listener.DRGElement(
            "",
            "compareAgainstLendingThreshold",
            "Compare against lending threshold",
            com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
            com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
            com.gs.dmn.runtime.annotation.HitPolicy.ANY,
            2
        )

        @JvmStatic
        fun requestToMap(compareAgainstLendingThresholdRequest_: proto.CompareAgainstLendingThresholdRequest): kotlin.collections.Map<String, Any?> {
            // Create arguments from Request Message
            val applicant: type.Applicant? = type.Applicant.toApplicant(compareAgainstLendingThresholdRequest_.getApplicant())
            val currentRiskAppetite: java.math.BigDecimal? = java.math.BigDecimal.valueOf(compareAgainstLendingThresholdRequest_.getCurrentRiskAppetite())
            val lendingThreshold: java.math.BigDecimal? = java.math.BigDecimal.valueOf(compareAgainstLendingThresholdRequest_.getLendingThreshold())

            // Create map
            val map_: kotlin.collections.MutableMap<String, Any?> = mutableMapOf()
            map_.put("Applicant", applicant)
            map_.put("Current risk appetite", currentRiskAppetite)
            map_.put("Lending threshold", lendingThreshold)
            return map_
        }

        @JvmStatic
        fun responseToOutput(compareAgainstLendingThresholdResponse_: proto.CompareAgainstLendingThresholdResponse): java.math.BigDecimal? {
            // Extract and convert output
            return java.math.BigDecimal.valueOf(compareAgainstLendingThresholdResponse_.getCompareAgainstLendingThreshold())
        }
    }
}
