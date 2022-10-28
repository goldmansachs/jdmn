
import java.util.*
import java.util.stream.Collectors

@javax.annotation.Generated(value = ["signavio-decision.ftl", "generateOutputData"])
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "generateOutputData",
    label = "Generate output data",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
class GenerateOutputData(val assessIssueRisk : AssessIssueRisk = AssessIssueRisk(), val compareAgainstLendingThreshold : CompareAgainstLendingThreshold = CompareAgainstLendingThreshold(), val makeCreditDecision : MakeCreditDecision = MakeCreditDecision()) : com.gs.dmn.signavio.runtime.DefaultSignavioBaseDecision() {
    override fun applyMap(input_: MutableMap<String, String>, context_: com.gs.dmn.runtime.ExecutionContext): List<type.GenerateOutputData?>? {
        try {
            return apply(input_.get("Applicant")?.let({ com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(it, object : com.fasterxml.jackson.core.type.TypeReference<type.ApplicantImpl>() {}) }), input_.get("Current risk appetite")?.let({ number(it) }), input_.get("Lending threshold")?.let({ number(it) }), context_)
        } catch (e: Exception) {
            logError("Cannot apply decision 'GenerateOutputData'", e)
            return null
        }
    }

    fun apply(applicant: type.Applicant?, currentRiskAppetite: java.math.BigDecimal?, lendingThreshold: java.math.BigDecimal?, context_: com.gs.dmn.runtime.ExecutionContext): List<type.GenerateOutputData?>? {
        try {
            // Start decision 'generateOutputData'
            var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
            var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
            var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
            var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
            val generateOutputDataStartTime_ = System.currentTimeMillis()
            val generateOutputDataArguments_ = com.gs.dmn.runtime.listener.Arguments()
            generateOutputDataArguments_.put("Applicant", applicant)
            generateOutputDataArguments_.put("Current risk appetite", currentRiskAppetite)
            generateOutputDataArguments_.put("Lending threshold", lendingThreshold)
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, generateOutputDataArguments_)

            // Evaluate decision 'generateOutputData'
            val output_: List<type.GenerateOutputData?>? = evaluate(applicant, currentRiskAppetite, lendingThreshold, context_)

            // End decision 'generateOutputData'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, generateOutputDataArguments_, output_, (System.currentTimeMillis() - generateOutputDataStartTime_))

            return output_
        } catch (e: Exception) {
            logError("Exception caught in 'generateOutputData' evaluation", e)
            return null
        }
    }

    fun applyProto(generateOutputDataRequest_: proto.GenerateOutputDataRequest, context_: com.gs.dmn.runtime.ExecutionContext): proto.GenerateOutputDataResponse {
        // Create arguments from Request Message
        val applicant: type.Applicant? = type.Applicant.toApplicant(generateOutputDataRequest_.getApplicant())
        val currentRiskAppetite: java.math.BigDecimal? = java.math.BigDecimal.valueOf(generateOutputDataRequest_.getCurrentRiskAppetite())
        val lendingThreshold: java.math.BigDecimal? = java.math.BigDecimal.valueOf(generateOutputDataRequest_.getLendingThreshold())

        // Invoke apply method
        val output_: List<type.GenerateOutputData?>? = apply(applicant, currentRiskAppetite, lendingThreshold, context_)

        // Convert output to Response Message
        val builder_: proto.GenerateOutputDataResponse.Builder = proto.GenerateOutputDataResponse.newBuilder()
        val outputProto_ = output_?.stream()?.map({e -> type.GenerateOutputData.toProto(e)})?.collect(java.util.stream.Collectors.toList())
        if (outputProto_ != null) {
            builder_.addAllGenerateOutputData(outputProto_)
        }
        return builder_.build()
    }

    private inline fun evaluate(applicant: type.Applicant?, currentRiskAppetite: java.math.BigDecimal?, lendingThreshold: java.math.BigDecimal?, context_: com.gs.dmn.runtime.ExecutionContext): List<type.GenerateOutputData?>? {
        var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
        var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
        var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
        var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
        // Apply child decisions
        val assessIssueRisk: java.math.BigDecimal? = this.assessIssueRisk.apply(applicant, currentRiskAppetite, context_)
        val compareAgainstLendingThreshold: java.math.BigDecimal? = this.compareAgainstLendingThreshold.apply(applicant, currentRiskAppetite, lendingThreshold, context_)
        val makeCreditDecision: String? = this.makeCreditDecision.apply(applicant, currentRiskAppetite, lendingThreshold, context_)

        return zip(asList("Decision", "Assessment", "Issue"), asList(asList(makeCreditDecision), asList(compareAgainstLendingThreshold), asList(assessIssueRisk)))?.map({ x -> type.GenerateOutputData.toGenerateOutputData(x) }) as List<type.GenerateOutputData?>?
    }

    companion object {
        val DRG_ELEMENT_METADATA : com.gs.dmn.runtime.listener.DRGElement = com.gs.dmn.runtime.listener.DRGElement(
            "",
            "generateOutputData",
            "Generate output data",
            com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
            com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
            com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
            -1
        )

        @JvmStatic
        fun requestToMap(generateOutputDataRequest_: proto.GenerateOutputDataRequest): kotlin.collections.Map<String, Any?> {
            // Create arguments from Request Message
            val applicant: type.Applicant? = type.Applicant.toApplicant(generateOutputDataRequest_.getApplicant())
            val currentRiskAppetite: java.math.BigDecimal? = java.math.BigDecimal.valueOf(generateOutputDataRequest_.getCurrentRiskAppetite())
            val lendingThreshold: java.math.BigDecimal? = java.math.BigDecimal.valueOf(generateOutputDataRequest_.getLendingThreshold())

            // Create map
            val map_: kotlin.collections.MutableMap<String, Any?> = mutableMapOf()
            map_.put("Applicant", applicant)
            map_.put("Current risk appetite", currentRiskAppetite)
            map_.put("Lending threshold", lendingThreshold)
            return map_
        }

        @JvmStatic
        fun responseToOutput(generateOutputDataResponse_: proto.GenerateOutputDataResponse): List<type.GenerateOutputData?>? {
            // Extract and convert output
            return (generateOutputDataResponse_.getGenerateOutputDataList()?.stream()?.map({e -> type.GenerateOutputData.toGenerateOutputData(e)})?.collect(java.util.stream.Collectors.toList()) as List<type.GenerateOutputData?>?)
        }
    }
}
