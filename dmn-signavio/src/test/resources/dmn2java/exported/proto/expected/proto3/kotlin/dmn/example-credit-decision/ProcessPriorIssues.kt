
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
class ProcessPriorIssues() : com.gs.dmn.signavio.runtime.DefaultSignavioBaseDecision() {
    fun apply(applicant: String?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet): List<java.math.BigDecimal?>? {
        return try {
            apply(applicant?.let({ com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(it, object : com.fasterxml.jackson.core.type.TypeReference<type.ApplicantImpl>() {}) }), annotationSet_, com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), com.gs.dmn.runtime.cache.DefaultCache())
        } catch (e: Exception) {
            logError("Cannot apply decision 'ProcessPriorIssues'", e)
            null
        }
    }

    fun apply(applicant: String?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): List<java.math.BigDecimal?>? {
        return try {
            apply(applicant?.let({ com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(it, object : com.fasterxml.jackson.core.type.TypeReference<type.ApplicantImpl>() {}) }), annotationSet_, eventListener_, externalExecutor_, cache_)
        } catch (e: Exception) {
            logError("Cannot apply decision 'ProcessPriorIssues'", e)
            null
        }
    }

    fun apply(applicant: type.Applicant?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet): List<java.math.BigDecimal?>? {
        return apply(applicant, annotationSet_, com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), com.gs.dmn.runtime.cache.DefaultCache())
    }

    fun apply(applicant: type.Applicant?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): List<java.math.BigDecimal?>? {
        try {
            // Start decision 'processPriorIssues'
            val processPriorIssuesStartTime_ = System.currentTimeMillis()
            val processPriorIssuesArguments_ = com.gs.dmn.runtime.listener.Arguments()
            processPriorIssuesArguments_.put("Applicant", applicant)
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, processPriorIssuesArguments_)

            // Evaluate decision 'processPriorIssues'
            val output_: List<java.math.BigDecimal?>? = evaluate(applicant, annotationSet_, eventListener_, externalExecutor_, cache_)

            // End decision 'processPriorIssues'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, processPriorIssuesArguments_, output_, (System.currentTimeMillis() - processPriorIssuesStartTime_))

            return output_
        } catch (e: Exception) {
            logError("Exception caught in 'processPriorIssues' evaluation", e)
            return null
        }
    }

    fun apply(processPriorIssuesRequest_: proto.ProcessPriorIssuesRequest, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet): proto.ProcessPriorIssuesResponse {
        return apply(processPriorIssuesRequest_, annotationSet_, com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), com.gs.dmn.runtime.cache.DefaultCache())
    }

    fun apply(processPriorIssuesRequest_: proto.ProcessPriorIssuesRequest, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): proto.ProcessPriorIssuesResponse {
        // Create arguments from Request Message
        val applicant: type.Applicant? = type.Applicant.toApplicant(processPriorIssuesRequest_.getApplicant())

        // Invoke apply method
        val output_: List<java.math.BigDecimal?>? = apply(applicant, annotationSet_, eventListener_, externalExecutor_, cache_)

        // Convert output to Response Message
        val builder_: proto.ProcessPriorIssuesResponse.Builder = proto.ProcessPriorIssuesResponse.newBuilder()
        val outputProto_ = output_?.stream()?.map({e -> (if (e == null) 0.0 else e!!.toDouble())})?.collect(java.util.stream.Collectors.toList())
        if (outputProto_ != null) {
            builder_.addAllProcessPriorIssues(outputProto_)
        }
        return builder_.build()
    }

    private inline fun evaluate(applicant: type.Applicant?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): List<java.math.BigDecimal?>? {
        // Apply rules and collect results
        val ruleOutputList_ = com.gs.dmn.runtime.RuleOutputList()
        ruleOutputList_.add(rule0(applicant, annotationSet_, eventListener_, externalExecutor_))
        ruleOutputList_.add(rule1(applicant, annotationSet_, eventListener_, externalExecutor_))
        ruleOutputList_.add(rule2(applicant, annotationSet_, eventListener_, externalExecutor_))
        ruleOutputList_.add(rule3(applicant, annotationSet_, eventListener_, externalExecutor_))
        ruleOutputList_.add(rule4(applicant, annotationSet_, eventListener_, externalExecutor_))

        // Return results based on hit policy
        var output_: List<java.math.BigDecimal?>?
        if (ruleOutputList_.noMatchedRules()) {
            // Default value
            output_ = null
            if (output_ == null) {
                output_ = this.asList()
            }
        } else {
            val ruleOutputs_: List<com.gs.dmn.runtime.RuleOutput> = ruleOutputList_.applyMultiple(com.gs.dmn.runtime.annotation.HitPolicy.COLLECT)
            output_ = ruleOutputs_.stream().map({ o -> (o as ProcessPriorIssuesRuleOutput).processPriorIssues }).collect(Collectors.toList())
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
        var output_: ProcessPriorIssuesRuleOutput = ProcessPriorIssuesRuleOutput(false)
        if (ruleMatches(eventListener_, drgRuleMetadata,
            booleanNot((notContainsAny(applicant?.let({ it.priorIssues as List<String?>? }), asList("Card rejection", "Late payment"))))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

            // Compute output
            output_.setMatched(true)
            output_.processPriorIssues = numericUnaryMinus(number("10"))

            // Add annotation
            annotationSet_.addAnnotation("processPriorIssues", 0, "")
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
        var output_: ProcessPriorIssuesRuleOutput = ProcessPriorIssuesRuleOutput(false)
        if (ruleMatches(eventListener_, drgRuleMetadata,
            booleanNot((notContainsAny(applicant?.let({ it.priorIssues as List<String?>? }), asList("Default on obligations"))))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

            // Compute output
            output_.setMatched(true)
            output_.processPriorIssues = numericUnaryMinus(number("30"))

            // Add annotation
            annotationSet_.addAnnotation("processPriorIssues", 1, "")
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
        var output_: ProcessPriorIssuesRuleOutput = ProcessPriorIssuesRuleOutput(false)
        if (ruleMatches(eventListener_, drgRuleMetadata,
            booleanNot((notContainsAny(applicant?.let({ it.priorIssues as List<String?>? }), asList("Bankruptcy"))))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

            // Compute output
            output_.setMatched(true)
            output_.processPriorIssues = numericUnaryMinus(number("100"))

            // Add annotation
            annotationSet_.addAnnotation("processPriorIssues", 2, "")
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 3, annotation = "\"\"")
    private fun rule3(applicant: type.Applicant?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor): com.gs.dmn.runtime.RuleOutput {
        // Rule metadata
        val drgRuleMetadata: com.gs.dmn.runtime.listener.Rule = com.gs.dmn.runtime.listener.Rule(3, "\"\"")

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

        // Apply rule
        var output_: ProcessPriorIssuesRuleOutput = ProcessPriorIssuesRuleOutput(false)
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (notContainsAny(applicant?.let({ it.priorIssues as List<String?>? }), asList("Card rejection", "Late payment", "Default on obligations", "Bankruptcy")))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

            // Compute output
            output_.setMatched(true)
            output_.processPriorIssues = number("50")

            // Add annotation
            annotationSet_.addAnnotation("processPriorIssues", 3, "")
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 4, annotation = "\"\"")
    private fun rule4(applicant: type.Applicant?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor): com.gs.dmn.runtime.RuleOutput {
        // Rule metadata
        val drgRuleMetadata: com.gs.dmn.runtime.listener.Rule = com.gs.dmn.runtime.listener.Rule(4, "\"\"")

        // Rule start
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

            // Add annotation
            annotationSet_.addAnnotation("processPriorIssues", 4, "")
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
        fun responseToOutput(processPriorIssuesResponse_: proto.ProcessPriorIssuesResponse): List<java.math.BigDecimal?>? {
            // Extract and convert output
            return (processPriorIssuesResponse_.getProcessPriorIssuesList()?.stream()?.map({e -> java.math.BigDecimal.valueOf(e)})?.collect(java.util.stream.Collectors.toList()) as List<java.math.BigDecimal?>?)
        }
    }
}
