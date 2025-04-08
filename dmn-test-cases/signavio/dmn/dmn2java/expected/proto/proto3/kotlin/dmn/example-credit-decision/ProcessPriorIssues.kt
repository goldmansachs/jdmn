
import java.util.*
import java.util.stream.Collectors

@javax.annotation.Generated(value = ["signavio-decision.ftl", "processPriorIssues"])
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "processPriorIssues",
    label = "Process prior issues",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.COLLECT,
    rulesCount = 5
)
class ProcessPriorIssues() : com.gs.dmn.signavio.runtime.JavaTimeSignavioBaseDecision() {
    override fun applyMap(input_: MutableMap<String, String>, context_: com.gs.dmn.runtime.ExecutionContext): List<kotlin.Number?>? {
        try {
            return apply(input_.get("Applicant")?.let({ com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(it, object : com.fasterxml.jackson.core.type.TypeReference<type.ApplicantImpl>() {}) }), context_)
        } catch (e: Exception) {
            logError("Cannot apply decision 'ProcessPriorIssues'", e)
            return null
        }
    }

    fun apply(applicant: type.Applicant?, context_: com.gs.dmn.runtime.ExecutionContext): List<kotlin.Number?>? {
        try {
            // Start decision 'processPriorIssues'
            var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
            var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
            var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
            var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
            val processPriorIssuesStartTime_ = System.currentTimeMillis()
            val processPriorIssuesArguments_ = com.gs.dmn.runtime.listener.Arguments()
            processPriorIssuesArguments_.put("Applicant", applicant)
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, processPriorIssuesArguments_)

            // Evaluate decision 'processPriorIssues'
            val output_: List<kotlin.Number?>? = evaluate(applicant, context_)

            // End decision 'processPriorIssues'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, processPriorIssuesArguments_, output_, (System.currentTimeMillis() - processPriorIssuesStartTime_))

            return output_
        } catch (e: Exception) {
            logError("Exception caught in 'processPriorIssues' evaluation", e)
            return null
        }
    }

    fun applyProto(processPriorIssuesRequest_: proto.ProcessPriorIssuesRequest, context_: com.gs.dmn.runtime.ExecutionContext): proto.ProcessPriorIssuesResponse {
        // Create arguments from Request Message
        val applicant: type.Applicant? = type.Applicant.toApplicant(processPriorIssuesRequest_.getApplicant())

        // Invoke apply method
        val output_: List<kotlin.Number?>? = apply(applicant, context_)

        // Convert output to Response Message
        val builder_: proto.ProcessPriorIssuesResponse.Builder = proto.ProcessPriorIssuesResponse.newBuilder()
        val outputProto_ = output_?.stream()?.map({e -> (if (e == null) 0.0 else e!!.toDouble())})?.collect(java.util.stream.Collectors.toList())
        if (outputProto_ != null) {
            builder_.addAllProcessPriorIssues(outputProto_)
        }
        return builder_.build()
    }

    private inline fun evaluate(applicant: type.Applicant?, context_: com.gs.dmn.runtime.ExecutionContext): List<kotlin.Number?>? {
        var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
        var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
        var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
        var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
        // Apply rules and collect results
        val ruleOutputList_ = com.gs.dmn.runtime.RuleOutputList()
        ruleOutputList_.add(rule0(applicant, context_))
        ruleOutputList_.add(rule1(applicant, context_))
        ruleOutputList_.add(rule2(applicant, context_))
        ruleOutputList_.add(rule3(applicant, context_))
        ruleOutputList_.add(rule4(applicant, context_))

        // Return results based on hit policy
        var output_: List<kotlin.Number?>?
        if (ruleOutputList_.noMatchedRules()) {
            // Default value
            output_ = null
            if (output_ == null) {
                output_ = this.asList()
            }
        } else {
            val ruleOutputs_: List<com.gs.dmn.runtime.RuleOutput> = ruleOutputList_.applyMultiple(com.gs.dmn.runtime.annotation.HitPolicy.COLLECT)
            output_ = ruleOutputs_.stream().map({ ro_ -> (ro_ as ProcessPriorIssuesRuleOutput).processPriorIssues }).collect(Collectors.toList())
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
        var output_: ProcessPriorIssuesRuleOutput = ProcessPriorIssuesRuleOutput(false)
        if (ruleMatches(eventListener_, drgRuleMetadata,
            booleanNot(notContainsAny(applicant?.let({ it.priorIssues as List<String?>? }), asList("Card rejection", "Late payment")))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

            // Compute output
            output_.setMatched(true)
            output_.processPriorIssues = numericUnaryMinus(number("10"))
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
        var output_: ProcessPriorIssuesRuleOutput = ProcessPriorIssuesRuleOutput(false)
        if (ruleMatches(eventListener_, drgRuleMetadata,
            booleanNot(notContainsAny(applicant?.let({ it.priorIssues as List<String?>? }), asList("Default on obligations")))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

            // Compute output
            output_.setMatched(true)
            output_.processPriorIssues = numericUnaryMinus(number("30"))
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
        var output_: ProcessPriorIssuesRuleOutput = ProcessPriorIssuesRuleOutput(false)
        if (ruleMatches(eventListener_, drgRuleMetadata,
            booleanNot(notContainsAny(applicant?.let({ it.priorIssues as List<String?>? }), asList("Bankruptcy")))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

            // Compute output
            output_.setMatched(true)
            output_.processPriorIssues = numericUnaryMinus(number("100"))
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 3, annotation = "")
    private fun rule3(applicant: type.Applicant?, context_: com.gs.dmn.runtime.ExecutionContext): com.gs.dmn.runtime.RuleOutput {
        // Rule metadata
        val drgRuleMetadata: com.gs.dmn.runtime.listener.Rule = com.gs.dmn.runtime.listener.Rule(3, "")

        // Rule start
        var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
        var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
        var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
        var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

        // Apply rule
        var output_: ProcessPriorIssuesRuleOutput = ProcessPriorIssuesRuleOutput(false)
        if (ruleMatches(eventListener_, drgRuleMetadata,
            notContainsAny(applicant?.let({ it.priorIssues as List<String?>? }), asList("Card rejection", "Late payment", "Default on obligations", "Bankruptcy"))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

            // Compute output
            output_.setMatched(true)
            output_.processPriorIssues = number("50")
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 4, annotation = "")
    private fun rule4(applicant: type.Applicant?, context_: com.gs.dmn.runtime.ExecutionContext): com.gs.dmn.runtime.RuleOutput {
        // Rule metadata
        val drgRuleMetadata: com.gs.dmn.runtime.listener.Rule = com.gs.dmn.runtime.listener.Rule(4, "")

        // Rule start
        var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
        var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
        var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
        var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

        // Apply rule
        var output_: ProcessPriorIssuesRuleOutput = ProcessPriorIssuesRuleOutput(false)
        if (ruleMatches(eventListener_, drgRuleMetadata,
            true
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

            // Compute output
            output_.setMatched(true)
            output_.processPriorIssues = numericMultiply(count(applicant?.let({ it.priorIssues as List<String?>? })), numericUnaryMinus(number("5")))
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_
    }


    companion object {
        val DRG_ELEMENT_METADATA : com.gs.dmn.runtime.listener.DRGElement = com.gs.dmn.runtime.listener.DRGElement(
            "",
            "processPriorIssues",
            "Process prior issues",
            com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
            com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
            com.gs.dmn.runtime.annotation.HitPolicy.COLLECT,
            5
        )

        @JvmStatic
        fun requestToMap(processPriorIssuesRequest_: proto.ProcessPriorIssuesRequest): kotlin.collections.Map<String, Any?> {
            // Create arguments from Request Message
            val applicant: type.Applicant? = type.Applicant.toApplicant(processPriorIssuesRequest_.getApplicant())

            // Create map
            val map_: kotlin.collections.MutableMap<String, Any?> = mutableMapOf()
            map_.put("Applicant", applicant)
            return map_
        }

        @JvmStatic
        fun responseToOutput(processPriorIssuesResponse_: proto.ProcessPriorIssuesResponse): List<kotlin.Number?>? {
            // Extract and convert output
            return (processPriorIssuesResponse_.getProcessPriorIssuesList()?.stream()?.map({e -> (java.math.BigDecimal.valueOf(e) as kotlin.Number)})?.collect(java.util.stream.Collectors.toList()) as List<kotlin.Number?>?)
        }
    }
}
