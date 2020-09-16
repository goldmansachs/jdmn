
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
    fun apply(applicant: String?, currentRiskAppetite: String?, lendingThreshold: String?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet): List<type.GenerateOutputData?>? {
        return try {
            apply(applicant?.let({ com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(it, object : com.fasterxml.jackson.core.type.TypeReference<type.ApplicantImpl>() {}) }), currentRiskAppetite?.let({ number(it) }), lendingThreshold?.let({ number(it) }), annotationSet_, com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), com.gs.dmn.runtime.cache.DefaultCache())
        } catch (e: Exception) {
            logError("Cannot apply decision 'GenerateOutputData'", e)
            null
        }
    }

    fun apply(applicant: String?, currentRiskAppetite: String?, lendingThreshold: String?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): List<type.GenerateOutputData?>? {
        return try {
            apply(applicant?.let({ com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(it, object : com.fasterxml.jackson.core.type.TypeReference<type.ApplicantImpl>() {}) }), currentRiskAppetite?.let({ number(it) }), lendingThreshold?.let({ number(it) }), annotationSet_, eventListener_, externalExecutor_, cache_)
        } catch (e: Exception) {
            logError("Cannot apply decision 'GenerateOutputData'", e)
            null
        }
    }

    fun apply(applicant: type.Applicant?, currentRiskAppetite: java.math.BigDecimal?, lendingThreshold: java.math.BigDecimal?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet): List<type.GenerateOutputData?>? {
        return apply(applicant, currentRiskAppetite, lendingThreshold, annotationSet_, com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), com.gs.dmn.runtime.cache.DefaultCache())
    }

    fun apply(applicant: type.Applicant?, currentRiskAppetite: java.math.BigDecimal?, lendingThreshold: java.math.BigDecimal?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): List<type.GenerateOutputData?>? {
        try {
            // Start decision 'generateOutputData'
            val generateOutputDataStartTime_ = System.currentTimeMillis()
            val generateOutputDataArguments_ = com.gs.dmn.runtime.listener.Arguments()
            generateOutputDataArguments_.put("Applicant", applicant)
            generateOutputDataArguments_.put("Current risk appetite", currentRiskAppetite)
            generateOutputDataArguments_.put("Lending threshold", lendingThreshold)
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, generateOutputDataArguments_)

            // Apply child decisions
            val assessIssueRisk: java.math.BigDecimal? = this.assessIssueRisk.apply(applicant, currentRiskAppetite, annotationSet_, eventListener_, externalExecutor_, cache_)
            val compareAgainstLendingThreshold: java.math.BigDecimal? = this.compareAgainstLendingThreshold.apply(applicant, currentRiskAppetite, lendingThreshold, annotationSet_, eventListener_, externalExecutor_, cache_)
            val makeCreditDecision: String? = this.makeCreditDecision.apply(applicant, currentRiskAppetite, lendingThreshold, annotationSet_, eventListener_, externalExecutor_, cache_)

            // Evaluate decision 'generateOutputData'
            val output_: List<type.GenerateOutputData?>? = evaluate(assessIssueRisk, compareAgainstLendingThreshold, makeCreditDecision, annotationSet_, eventListener_, externalExecutor_, cache_)

            // End decision 'generateOutputData'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, generateOutputDataArguments_, output_, (System.currentTimeMillis() - generateOutputDataStartTime_))

            return output_
        } catch (e: Exception) {
            logError("Exception caught in 'generateOutputData' evaluation", e)
            return null
        }
    }

    fun apply(generateOutputDataRequest_: proto.GenerateOutputDataRequest, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet): proto.GenerateOutputDataResponse {
        return apply(generateOutputDataRequest_, annotationSet_, com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), com.gs.dmn.runtime.cache.DefaultCache())
    }

    fun apply(generateOutputDataRequest_: proto.GenerateOutputDataRequest, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): proto.GenerateOutputDataResponse {
        // Create arguments from Request Message
        val applicant: type.Applicant? = type.Applicant.toApplicant(generateOutputDataRequest_.getApplicant())
        val currentRiskAppetite: java.math.BigDecimal? = java.math.BigDecimal.valueOf(generateOutputDataRequest_.getCurrentRiskAppetite())
        val lendingThreshold: java.math.BigDecimal? = java.math.BigDecimal.valueOf(generateOutputDataRequest_.getLendingThreshold())

        // Invoke apply method
        val output_: List<type.GenerateOutputData?>? = apply(applicant, currentRiskAppetite, lendingThreshold, annotationSet_, eventListener_, externalExecutor_, cache_)

        // Convert output to Response Message
        val builder_: proto.GenerateOutputDataResponse.Builder = proto.GenerateOutputDataResponse.newBuilder()
        val outputProto_ = output_?.stream()?.map({e -> type.GenerateOutputData.toProto(e)})?.collect(java.util.stream.Collectors.toList())
        if (outputProto_ != null) {
            builder_.addAllGenerateOutputData(outputProto_)
        }
        return builder_.build()
    }

    private inline fun evaluate(assessIssueRisk: java.math.BigDecimal?, compareAgainstLendingThreshold: java.math.BigDecimal?, makeCreditDecision: String?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): List<type.GenerateOutputData?>? {
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
