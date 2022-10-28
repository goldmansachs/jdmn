
import java.util.*
import java.util.stream.Collectors

@javax.annotation.Generated(value = ["signavio-decision.ftl", "assessIssue"])
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "assessIssue",
    label = "Assess issue",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
class AssessIssue() : com.gs.dmn.signavio.runtime.DefaultSignavioBaseDecision() {
    override fun applyMap(input_: MutableMap<String, String>, context_: com.gs.dmn.runtime.ExecutionContext): java.math.BigDecimal? {
        try {
            return apply(input_.get("Current risk appetite")?.let({ number(it) }), input_.get("Prior issue")?.let({ number(it) }), context_)
        } catch (e: Exception) {
            logError("Cannot apply decision 'AssessIssue'", e)
            return null
        }
    }

    fun apply(currentRiskAppetite: java.math.BigDecimal?, priorIssue_iterator: java.math.BigDecimal?, context_: com.gs.dmn.runtime.ExecutionContext): java.math.BigDecimal? {
        try {
            // Start decision 'assessIssue'
            var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
            var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
            var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
            var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
            val assessIssueStartTime_ = System.currentTimeMillis()
            val assessIssueArguments_ = com.gs.dmn.runtime.listener.Arguments()
            assessIssueArguments_.put("Current risk appetite", currentRiskAppetite)
            assessIssueArguments_.put("Prior issue", priorIssue_iterator)
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, assessIssueArguments_)

            // Evaluate decision 'assessIssue'
            val output_: java.math.BigDecimal? = evaluate(currentRiskAppetite, priorIssue_iterator, context_)

            // End decision 'assessIssue'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, assessIssueArguments_, output_, (System.currentTimeMillis() - assessIssueStartTime_))

            return output_
        } catch (e: Exception) {
            logError("Exception caught in 'assessIssue' evaluation", e)
            return null
        }
    }

    fun applyProto(assessIssueRequest_: proto.AssessIssueRequest, context_: com.gs.dmn.runtime.ExecutionContext): proto.AssessIssueResponse {
        // Create arguments from Request Message
        val currentRiskAppetite: java.math.BigDecimal? = java.math.BigDecimal.valueOf(assessIssueRequest_.getCurrentRiskAppetite())
        val priorIssue_iterator: java.math.BigDecimal? = java.math.BigDecimal.valueOf(assessIssueRequest_.getPriorIssueIterator())

        // Invoke apply method
        val output_: java.math.BigDecimal? = apply(currentRiskAppetite, priorIssue_iterator, context_)

        // Convert output to Response Message
        val builder_: proto.AssessIssueResponse.Builder = proto.AssessIssueResponse.newBuilder()
        val outputProto_ = (if (output_ == null) 0.0 else output_!!.toDouble())
        builder_.setAssessIssue(outputProto_)
        return builder_.build()
    }

    private inline fun evaluate(currentRiskAppetite: java.math.BigDecimal?, priorIssue_iterator: java.math.BigDecimal?, context_: com.gs.dmn.runtime.ExecutionContext): java.math.BigDecimal? {
        var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
        var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
        var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
        var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
        return numericMultiply(priorIssue_iterator, numericMultiply(max(asList(number("0"), numericSubtract(number("100"), currentRiskAppetite))), number("0.01"))) as java.math.BigDecimal?
    }

    companion object {
        val DRG_ELEMENT_METADATA : com.gs.dmn.runtime.listener.DRGElement = com.gs.dmn.runtime.listener.DRGElement(
            "",
            "assessIssue",
            "Assess issue",
            com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
            com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
            com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
            -1
        )

        @JvmStatic
        fun requestToMap(assessIssueRequest_: proto.AssessIssueRequest): kotlin.collections.Map<String, Any?> {
            // Create arguments from Request Message
            val currentRiskAppetite: java.math.BigDecimal? = java.math.BigDecimal.valueOf(assessIssueRequest_.getCurrentRiskAppetite())
            val priorIssue_iterator: java.math.BigDecimal? = java.math.BigDecimal.valueOf(assessIssueRequest_.getPriorIssueIterator())

            // Create map
            val map_: kotlin.collections.MutableMap<String, Any?> = mutableMapOf()
            map_.put("Current risk appetite", currentRiskAppetite)
            map_.put("Prior issue", priorIssue_iterator)
            return map_
        }

        @JvmStatic
        fun responseToOutput(assessIssueResponse_: proto.AssessIssueResponse): java.math.BigDecimal? {
            // Extract and convert output
            return java.math.BigDecimal.valueOf(assessIssueResponse_.getAssessIssue())
        }
    }
}
