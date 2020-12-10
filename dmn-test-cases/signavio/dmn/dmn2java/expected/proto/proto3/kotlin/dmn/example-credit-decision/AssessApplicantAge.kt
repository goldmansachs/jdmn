
import java.util.*
import java.util.stream.Collectors

@javax.annotation.Generated(value = ["signavio-decision.ftl", "assessApplicantAge"])
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "assessApplicantAge",
    label = "Assess applicant age",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNIQUE,
    rulesCount = 3
)
class AssessApplicantAge() : com.gs.dmn.signavio.runtime.DefaultSignavioBaseDecision() {
    fun apply(applicant: String?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet): java.math.BigDecimal? {
        return try {
            apply(applicant?.let({ com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(it, object : com.fasterxml.jackson.core.type.TypeReference<type.ApplicantImpl>() {}) }), annotationSet_, com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), com.gs.dmn.runtime.cache.DefaultCache())
        } catch (e: Exception) {
            logError("Cannot apply decision 'AssessApplicantAge'", e)
            null
        }
    }

    fun apply(applicant: String?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): java.math.BigDecimal? {
        return try {
            apply(applicant?.let({ com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(it, object : com.fasterxml.jackson.core.type.TypeReference<type.ApplicantImpl>() {}) }), annotationSet_, eventListener_, externalExecutor_, cache_)
        } catch (e: Exception) {
            logError("Cannot apply decision 'AssessApplicantAge'", e)
            null
        }
    }

    fun apply(applicant: type.Applicant?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet): java.math.BigDecimal? {
        return apply(applicant, annotationSet_, com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), com.gs.dmn.runtime.cache.DefaultCache())
    }

    fun apply(applicant: type.Applicant?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): java.math.BigDecimal? {
        try {
            // Start decision 'assessApplicantAge'
            val assessApplicantAgeStartTime_ = System.currentTimeMillis()
            val assessApplicantAgeArguments_ = com.gs.dmn.runtime.listener.Arguments()
            assessApplicantAgeArguments_.put("Applicant", applicant)
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, assessApplicantAgeArguments_)

            // Evaluate decision 'assessApplicantAge'
            val output_: java.math.BigDecimal? = evaluate(applicant, annotationSet_, eventListener_, externalExecutor_, cache_)

            // End decision 'assessApplicantAge'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, assessApplicantAgeArguments_, output_, (System.currentTimeMillis() - assessApplicantAgeStartTime_))

            return output_
        } catch (e: Exception) {
            logError("Exception caught in 'assessApplicantAge' evaluation", e)
            return null
        }
    }

    fun apply(assessApplicantAgeRequest_: proto.AssessApplicantAgeRequest, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet): proto.AssessApplicantAgeResponse {
        return apply(assessApplicantAgeRequest_, annotationSet_, com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), com.gs.dmn.runtime.cache.DefaultCache())
    }

    fun apply(assessApplicantAgeRequest_: proto.AssessApplicantAgeRequest, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): proto.AssessApplicantAgeResponse {
        // Create arguments from Request Message
        val applicant: type.Applicant? = type.Applicant.toApplicant(assessApplicantAgeRequest_.getApplicant())

        // Invoke apply method
        val output_: java.math.BigDecimal? = apply(applicant, annotationSet_, eventListener_, externalExecutor_, cache_)

        // Convert output to Response Message
        val builder_: proto.AssessApplicantAgeResponse.Builder = proto.AssessApplicantAgeResponse.newBuilder()
        val outputProto_ = (if (output_ == null) 0.0 else output_!!.toDouble())
        builder_.setAssessApplicantAge(outputProto_)
        return builder_.build()
    }

    private inline fun evaluate(applicant: type.Applicant?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): java.math.BigDecimal? {
        // Apply rules and collect results
        val ruleOutputList_ = com.gs.dmn.runtime.RuleOutputList()
        ruleOutputList_.add(rule0(applicant, annotationSet_, eventListener_, externalExecutor_))
        ruleOutputList_.add(rule1(applicant, annotationSet_, eventListener_, externalExecutor_))
        ruleOutputList_.add(rule2(applicant, annotationSet_, eventListener_, externalExecutor_))

        // Return results based on hit policy
        var output_: java.math.BigDecimal?
        if (ruleOutputList_.noMatchedRules()) {
            // Default value
            output_ = null
        } else {
            val ruleOutput_: com.gs.dmn.runtime.RuleOutput? = ruleOutputList_.applySingle(com.gs.dmn.runtime.annotation.HitPolicy.UNIQUE)
            output_ = ruleOutput_?.let({ (ruleOutput_ as AssessApplicantAgeRuleOutput).assessApplicantAge })
        }

        return output_
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 0, annotation = "\"\"")
    private fun rule0(applicant: type.Applicant?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor): com.gs.dmn.runtime.RuleOutput {
        // Rule metadata
        val drgRuleMetadata: com.gs.dmn.runtime.listener.Rule = com.gs.dmn.runtime.listener.Rule(0, "\"\"")

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

        // Apply rule
        var output_: AssessApplicantAgeRuleOutput = AssessApplicantAgeRuleOutput(false)
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (numericLessThan(applicant?.let({ it.age as java.math.BigDecimal? }), number("18")))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

            // Compute output
            output_.setMatched(true)
            output_.assessApplicantAge = numericUnaryMinus(number("10"))

            // Add annotation
            annotationSet_.addAnnotation("assessApplicantAge", 0, "")
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 1, annotation = "\"\"")
    private fun rule1(applicant: type.Applicant?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor): com.gs.dmn.runtime.RuleOutput {
        // Rule metadata
        val drgRuleMetadata: com.gs.dmn.runtime.listener.Rule = com.gs.dmn.runtime.listener.Rule(1, "\"\"")

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

        // Apply rule
        var output_: AssessApplicantAgeRuleOutput = AssessApplicantAgeRuleOutput(false)
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (booleanAnd(numericGreaterEqualThan(applicant?.let({ it.age as java.math.BigDecimal? }), number("18")), numericLessEqualThan(applicant?.let({ it.age as java.math.BigDecimal? }), number("25"))))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

            // Compute output
            output_.setMatched(true)
            output_.assessApplicantAge = number("40")

            // Add annotation
            annotationSet_.addAnnotation("assessApplicantAge", 1, "")
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 2, annotation = "\"\"")
    private fun rule2(applicant: type.Applicant?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor): com.gs.dmn.runtime.RuleOutput {
        // Rule metadata
        val drgRuleMetadata: com.gs.dmn.runtime.listener.Rule = com.gs.dmn.runtime.listener.Rule(2, "\"\"")

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

        // Apply rule
        var output_: AssessApplicantAgeRuleOutput = AssessApplicantAgeRuleOutput(false)
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (numericGreaterThan(applicant?.let({ it.age as java.math.BigDecimal? }), number("25")))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

            // Compute output
            output_.setMatched(true)
            output_.assessApplicantAge = number("60")

            // Add annotation
            annotationSet_.addAnnotation("assessApplicantAge", 2, "")
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_
    }


    companion object {
        val DRG_ELEMENT_METADATA : com.gs.dmn.runtime.listener.DRGElement = com.gs.dmn.runtime.listener.DRGElement(
            "",
            "assessApplicantAge",
            "Assess applicant age",
            com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
            com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
            com.gs.dmn.runtime.annotation.HitPolicy.UNIQUE,
            3
        )

        @JvmStatic
        fun requestToMap(assessApplicantAgeRequest_: proto.AssessApplicantAgeRequest): kotlin.collections.Map<String, Any?> {
            // Create arguments from Request Message
            val applicant: type.Applicant? = type.Applicant.toApplicant(assessApplicantAgeRequest_.getApplicant())

            // Create map
            val map_: kotlin.collections.MutableMap<String, Any?> = mutableMapOf()
            map_.put("Applicant", applicant)
            return map_
        }

        @JvmStatic
        fun responseToOutput(assessApplicantAgeResponse_: proto.AssessApplicantAgeResponse): java.math.BigDecimal? {
            // Extract and convert output
            return java.math.BigDecimal.valueOf(assessApplicantAgeResponse_.getAssessApplicantAge())
        }
    }
}
