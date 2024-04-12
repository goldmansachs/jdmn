
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
    override fun applyMap(input_: MutableMap<String, String>, context_: com.gs.dmn.runtime.ExecutionContext): java.math.BigDecimal? {
        try {
            return apply(input_.get("Applicant")?.let({ com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(it, object : com.fasterxml.jackson.core.type.TypeReference<type.ApplicantImpl>() {}) }), context_)
        } catch (e: Exception) {
            logError("Cannot apply decision 'AssessApplicantAge'", e)
            return null
        }
    }

    fun apply(applicant: type.Applicant?, context_: com.gs.dmn.runtime.ExecutionContext): java.math.BigDecimal? {
        try {
            // Start decision 'assessApplicantAge'
            var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
            var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
            var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
            var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
            val assessApplicantAgeStartTime_ = System.currentTimeMillis()
            val assessApplicantAgeArguments_ = com.gs.dmn.runtime.listener.Arguments()
            assessApplicantAgeArguments_.put("Applicant", applicant)
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, assessApplicantAgeArguments_)

            // Evaluate decision 'assessApplicantAge'
            val output_: java.math.BigDecimal? = evaluate(applicant, context_)

            // End decision 'assessApplicantAge'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, assessApplicantAgeArguments_, output_, (System.currentTimeMillis() - assessApplicantAgeStartTime_))

            return output_
        } catch (e: Exception) {
            logError("Exception caught in 'assessApplicantAge' evaluation", e)
            return null
        }
    }

    fun applyProto(assessApplicantAgeRequest_: proto.AssessApplicantAgeRequest, context_: com.gs.dmn.runtime.ExecutionContext): proto.AssessApplicantAgeResponse {
        // Create arguments from Request Message
        val applicant: type.Applicant? = type.Applicant.toApplicant(assessApplicantAgeRequest_.getApplicant())

        // Invoke apply method
        val output_: java.math.BigDecimal? = apply(applicant, context_)

        // Convert output to Response Message
        val builder_: proto.AssessApplicantAgeResponse.Builder = proto.AssessApplicantAgeResponse.newBuilder()
        val outputProto_ = (if (output_ == null) 0.0 else output_!!.toDouble())
        builder_.setAssessApplicantAge(outputProto_)
        return builder_.build()
    }

    private inline fun evaluate(applicant: type.Applicant?, context_: com.gs.dmn.runtime.ExecutionContext): java.math.BigDecimal? {
        var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
        var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
        var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
        var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
        // Apply rules and collect results
        val ruleOutputList_ = com.gs.dmn.runtime.RuleOutputList()
        ruleOutputList_.add(rule0(applicant, context_))
        ruleOutputList_.add(rule1(applicant, context_))
        ruleOutputList_.add(rule2(applicant, context_))

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

    @com.gs.dmn.runtime.annotation.Rule(index = 0, annotation = "")
    private fun rule0(applicant: type.Applicant?, context_: com.gs.dmn.runtime.ExecutionContext): com.gs.dmn.runtime.RuleOutput {
        // Rule metadata
        val drgRuleMetadata: com.gs.dmn.runtime.listener.Rule = com.gs.dmn.runtime.listener.Rule(0, "")

        // Rule start
        var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
        var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
        var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
        var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

        // Apply rule
        var output_: AssessApplicantAgeRuleOutput = AssessApplicantAgeRuleOutput(false)
        if (ruleMatches(eventListener_, drgRuleMetadata,
            numericLessThan(applicant?.let({ it.age as java.math.BigDecimal? }), number("18"))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

            // Compute output
            output_.setMatched(true)
            output_.assessApplicantAge = numericUnaryMinus(number("10"))
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 1, annotation = "")
    private fun rule1(applicant: type.Applicant?, context_: com.gs.dmn.runtime.ExecutionContext): com.gs.dmn.runtime.RuleOutput {
        // Rule metadata
        val drgRuleMetadata: com.gs.dmn.runtime.listener.Rule = com.gs.dmn.runtime.listener.Rule(1, "")

        // Rule start
        var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
        var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
        var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
        var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

        // Apply rule
        var output_: AssessApplicantAgeRuleOutput = AssessApplicantAgeRuleOutput(false)
        if (ruleMatches(eventListener_, drgRuleMetadata,
            booleanAnd(numericGreaterEqualThan(applicant?.let({ it.age as java.math.BigDecimal? }), number("18")), numericLessEqualThan(applicant?.let({ it.age as java.math.BigDecimal? }), number("25")))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

            // Compute output
            output_.setMatched(true)
            output_.assessApplicantAge = number("40")
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 2, annotation = "")
    private fun rule2(applicant: type.Applicant?, context_: com.gs.dmn.runtime.ExecutionContext): com.gs.dmn.runtime.RuleOutput {
        // Rule metadata
        val drgRuleMetadata: com.gs.dmn.runtime.listener.Rule = com.gs.dmn.runtime.listener.Rule(2, "")

        // Rule start
        var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
        var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
        var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
        var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

        // Apply rule
        var output_: AssessApplicantAgeRuleOutput = AssessApplicantAgeRuleOutput(false)
        if (ruleMatches(eventListener_, drgRuleMetadata,
            numericGreaterThan(applicant?.let({ it.age as java.math.BigDecimal? }), number("25"))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

            // Compute output
            output_.setMatched(true)
            output_.assessApplicantAge = number("60")
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
