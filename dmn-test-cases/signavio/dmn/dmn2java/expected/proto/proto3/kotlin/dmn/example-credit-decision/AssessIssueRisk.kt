
import java.util.*
import java.util.stream.Collectors

@jakarta.annotation.Generated(value = ["signavio-decision.ftl", "assessIssueRisk"])
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "assessIssueRisk",
    label = "Assess issue risk",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.OTHER,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
class AssessIssueRisk(val processPriorIssues : ProcessPriorIssues = ProcessPriorIssues()) : com.gs.dmn.signavio.runtime.DefaultSignavioBaseDecision() {
    override fun applyMap(input_: MutableMap<String, String>, context_: com.gs.dmn.runtime.ExecutionContext): java.math.BigDecimal? {
        try {
            return apply(input_.get("Applicant")?.let({ com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(it, object : com.fasterxml.jackson.core.type.TypeReference<type.ApplicantImpl>() {}) }), input_.get("Current risk appetite")?.let({ number(it) }), context_)
        } catch (e: Exception) {
            logError("Cannot apply decision 'AssessIssueRisk'", e)
            return null
        }
    }

    fun apply(applicant: type.Applicant?, currentRiskAppetite: java.math.BigDecimal?, context_: com.gs.dmn.runtime.ExecutionContext): java.math.BigDecimal? {
        try {
            // Start decision 'assessIssueRisk'
            var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
            var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
            var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
            var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
            val assessIssueRiskStartTime_ = System.currentTimeMillis()
            val assessIssueRiskArguments_ = com.gs.dmn.runtime.listener.Arguments()
            assessIssueRiskArguments_.put("Applicant", applicant)
            assessIssueRiskArguments_.put("Current risk appetite", currentRiskAppetite)
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, assessIssueRiskArguments_)

            // Iterate and aggregate
            var output_: java.math.BigDecimal? = evaluate(applicant, currentRiskAppetite, context_)

            // End decision 'assessIssueRisk'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, assessIssueRiskArguments_, output_, (System.currentTimeMillis() - assessIssueRiskStartTime_))

            return output_
        } catch (e: Exception) {
            logError("Exception caught in 'assessIssueRisk' evaluation", e)
            return null
        }
    }

    fun applyProto(assessIssueRiskRequest_: proto.AssessIssueRiskRequest, context_: com.gs.dmn.runtime.ExecutionContext): proto.AssessIssueRiskResponse {
        // Create arguments from Request Message
        val applicant: type.Applicant? = type.Applicant.toApplicant(assessIssueRiskRequest_.getApplicant())
        val currentRiskAppetite: java.math.BigDecimal? = java.math.BigDecimal.valueOf(assessIssueRiskRequest_.getCurrentRiskAppetite())

        // Invoke apply method
        val output_: java.math.BigDecimal? = apply(applicant, currentRiskAppetite, context_)

        // Convert output to Response Message
        val builder_: proto.AssessIssueRiskResponse.Builder = proto.AssessIssueRiskResponse.newBuilder()
        val outputProto_ = (if (output_ == null) 0.0 else output_!!.toDouble())
        builder_.setAssessIssueRisk(outputProto_)
        return builder_.build()
    }

    private inline fun evaluate(applicant: type.Applicant?, currentRiskAppetite: java.math.BigDecimal?, context_: com.gs.dmn.runtime.ExecutionContext): java.math.BigDecimal? {
        var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
        var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
        var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
        var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
        // Apply child decisions
        val processPriorIssues: List<java.math.BigDecimal?>? = this.processPriorIssues.apply(applicant, context_)

        val assessIssue: AssessIssue = AssessIssue()
        return sum(processPriorIssues?.stream()?.map({priorIssue_iterator -> assessIssue.apply(currentRiskAppetite, priorIssue_iterator, context_)})?.collect(Collectors.toList()))
    }

    companion object {
        val DRG_ELEMENT_METADATA : com.gs.dmn.runtime.listener.DRGElement = com.gs.dmn.runtime.listener.DRGElement(
            "",
            "assessIssueRisk",
            "Assess issue risk",
            com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
            com.gs.dmn.runtime.annotation.ExpressionKind.OTHER,
            com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
            -1
        )

        @JvmStatic
        fun requestToMap(assessIssueRiskRequest_: proto.AssessIssueRiskRequest): kotlin.collections.Map<String, Any?> {
            // Create arguments from Request Message
            val applicant: type.Applicant? = type.Applicant.toApplicant(assessIssueRiskRequest_.getApplicant())
            val currentRiskAppetite: java.math.BigDecimal? = java.math.BigDecimal.valueOf(assessIssueRiskRequest_.getCurrentRiskAppetite())

            // Create map
            val map_: kotlin.collections.MutableMap<String, Any?> = mutableMapOf()
            map_.put("Applicant", applicant)
            map_.put("Current risk appetite", currentRiskAppetite)
            return map_
        }

        @JvmStatic
        fun responseToOutput(assessIssueRiskResponse_: proto.AssessIssueRiskResponse): java.math.BigDecimal? {
            // Extract and convert output
            return java.math.BigDecimal.valueOf(assessIssueRiskResponse_.getAssessIssueRisk())
        }
    }
}
