
import java.util.*
import java.util.stream.Collectors

@javax.annotation.Generated(value = ["decision.ftl", "Strategy"])
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "Strategy",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNIQUE,
    rulesCount = 3
)
class Strategy(val bureauCallType : BureauCallType = BureauCallType(), val eligibility : Eligibility = Eligibility()) : com.gs.dmn.runtime.DefaultDMNBaseDecision() {
    override fun applyMap(input_: MutableMap<String, String>, context_: com.gs.dmn.runtime.ExecutionContext): String? {
        try {
            return apply(input_.get("ApplicantData")?.let({ com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(it, object : com.fasterxml.jackson.core.type.TypeReference<type.TApplicantDataImpl>() {}) }), input_.get("RequestedProduct")?.let({ com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(it, object : com.fasterxml.jackson.core.type.TypeReference<type.TRequestedProductImpl>() {}) }), context_)
        } catch (e: Exception) {
            logError("Cannot apply decision 'Strategy'", e)
            return null
        }
    }

    fun apply(applicantData: type.TApplicantData?, requestedProduct: type.TRequestedProduct?, context_: com.gs.dmn.runtime.ExecutionContext): String? {
        try {
            // Start decision 'Strategy'
            var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
            var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
            var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
            var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
            val strategyStartTime_ = System.currentTimeMillis()
            val strategyArguments_ = com.gs.dmn.runtime.listener.Arguments()
            strategyArguments_.put("ApplicantData", applicantData)
            strategyArguments_.put("RequestedProduct", requestedProduct)
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, strategyArguments_)

            // Evaluate decision 'Strategy'
            val output_: String? = evaluate(applicantData, requestedProduct, context_)

            // End decision 'Strategy'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, strategyArguments_, output_, (System.currentTimeMillis() - strategyStartTime_))

            return output_
        } catch (e: Exception) {
            logError("Exception caught in 'Strategy' evaluation", e)
            return null
        }
    }

    fun applyProto(strategyRequest_: proto.StrategyRequest, context_: com.gs.dmn.runtime.ExecutionContext): proto.StrategyResponse {
        // Create arguments from Request Message
        val applicantData: type.TApplicantData? = type.TApplicantData.toTApplicantData(strategyRequest_.getApplicantData())
        val requestedProduct: type.TRequestedProduct? = type.TRequestedProduct.toTRequestedProduct(strategyRequest_.getRequestedProduct())

        // Invoke apply method
        val output_: String? = apply(applicantData, requestedProduct, context_)

        // Convert output to Response Message
        val builder_: proto.StrategyResponse.Builder = proto.StrategyResponse.newBuilder()
        val outputProto_ = (if (output_ == null) "" else output_!!)
        builder_.setStrategy(outputProto_)
        return builder_.build()
    }

    private inline fun evaluate(applicantData: type.TApplicantData?, requestedProduct: type.TRequestedProduct?, context_: com.gs.dmn.runtime.ExecutionContext): String? {
        var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
        var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
        var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
        var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
        // Apply child decisions
        val bureauCallType: String? = this@Strategy.bureauCallType.apply(applicantData, context_)
        val eligibility: String? = this@Strategy.eligibility.apply(applicantData, requestedProduct, context_)

        // Apply rules and collect results
        val ruleOutputList_ = com.gs.dmn.runtime.RuleOutputList()
        ruleOutputList_.add(rule0(bureauCallType, eligibility, context_))
        ruleOutputList_.add(rule1(bureauCallType, eligibility, context_))
        ruleOutputList_.add(rule2(bureauCallType, eligibility, context_))

        // Return results based on hit policy
        var output_: String?
        if (ruleOutputList_.noMatchedRules()) {
            // Default value
            output_ = null
        } else {
            val ruleOutput_: com.gs.dmn.runtime.RuleOutput? = ruleOutputList_.applySingle(com.gs.dmn.runtime.annotation.HitPolicy.UNIQUE)
            output_ = ruleOutput_?.let({ (ruleOutput_ as StrategyRuleOutput).strategy })
        }

        return output_
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 0, annotation = "")
    private fun rule0(bureauCallType: String?, eligibility: String?, context_: com.gs.dmn.runtime.ExecutionContext): com.gs.dmn.runtime.RuleOutput {
        // Rule metadata
        val drgRuleMetadata: com.gs.dmn.runtime.listener.Rule = com.gs.dmn.runtime.listener.Rule(0, "")

        // Rule start
        var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
        var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
        var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
        var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

        // Apply rule
        var output_: StrategyRuleOutput = StrategyRuleOutput(false)
        if (ruleMatches(eventListener_, drgRuleMetadata,
            stringEqual(eligibility, "INELIGIBLE"),
            true
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

            // Compute output
            output_.setMatched(true)
            output_.strategy = "DECLINE"
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 1, annotation = "")
    private fun rule1(bureauCallType: String?, eligibility: String?, context_: com.gs.dmn.runtime.ExecutionContext): com.gs.dmn.runtime.RuleOutput {
        // Rule metadata
        val drgRuleMetadata: com.gs.dmn.runtime.listener.Rule = com.gs.dmn.runtime.listener.Rule(1, "")

        // Rule start
        var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
        var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
        var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
        var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

        // Apply rule
        var output_: StrategyRuleOutput = StrategyRuleOutput(false)
        if (ruleMatches(eventListener_, drgRuleMetadata,
            stringEqual(eligibility, "ELIGIBLE"),
            booleanOr(stringEqual(bureauCallType, "FULL"), stringEqual(bureauCallType, "MINI"))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

            // Compute output
            output_.setMatched(true)
            output_.strategy = "BUREAU"
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 2, annotation = "")
    private fun rule2(bureauCallType: String?, eligibility: String?, context_: com.gs.dmn.runtime.ExecutionContext): com.gs.dmn.runtime.RuleOutput {
        // Rule metadata
        val drgRuleMetadata: com.gs.dmn.runtime.listener.Rule = com.gs.dmn.runtime.listener.Rule(2, "")

        // Rule start
        var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
        var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
        var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
        var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

        // Apply rule
        var output_: StrategyRuleOutput = StrategyRuleOutput(false)
        if (ruleMatches(eventListener_, drgRuleMetadata,
            stringEqual(eligibility, "ELIGIBLE"),
            stringEqual(bureauCallType, "NONE")
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

            // Compute output
            output_.setMatched(true)
            output_.strategy = "THROUGH"
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_
    }


    companion object {
        val DRG_ELEMENT_METADATA : com.gs.dmn.runtime.listener.DRGElement = com.gs.dmn.runtime.listener.DRGElement(
            "",
            "Strategy",
            "",
            com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
            com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
            com.gs.dmn.runtime.annotation.HitPolicy.UNIQUE,
            3
        )

        @JvmStatic
        fun requestToMap(strategyRequest_: proto.StrategyRequest): kotlin.collections.Map<String, Any?> {
            // Create arguments from Request Message
            val applicantData: type.TApplicantData? = type.TApplicantData.toTApplicantData(strategyRequest_.getApplicantData())
            val requestedProduct: type.TRequestedProduct? = type.TRequestedProduct.toTRequestedProduct(strategyRequest_.getRequestedProduct())

            // Create map
            val map_: kotlin.collections.MutableMap<String, Any?> = mutableMapOf()
            map_.put("ApplicantData", applicantData)
            map_.put("RequestedProduct", requestedProduct)
            return map_
        }

        @JvmStatic
        fun responseToOutput(strategyResponse_: proto.StrategyResponse): String? {
            // Extract and convert output
            return strategyResponse_.getStrategy()
        }
    }
}
