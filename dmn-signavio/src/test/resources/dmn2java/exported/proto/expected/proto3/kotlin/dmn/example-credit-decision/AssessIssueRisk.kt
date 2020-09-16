
import java.util.*
import java.util.stream.Collectors

@javax.annotation.Generated(value = ["signavio-decision.ftl", "assessIssueRisk"])
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
    fun apply(applicant: String?, currentRiskAppetite: String?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet): java.math.BigDecimal? {
        return try {
            apply(applicant?.let({ com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(it, object : com.fasterxml.jackson.core.type.TypeReference<type.ApplicantImpl>() {}) }), currentRiskAppetite?.let({ number(it) }), annotationSet_, com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), com.gs.dmn.runtime.cache.DefaultCache())
        } catch (e: Exception) {
            logError("Cannot apply decision 'AssessIssueRisk'", e)
            null
        }
    }

    fun apply(applicant: String?, currentRiskAppetite: String?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): java.math.BigDecimal? {
        return try {
            apply(applicant?.let({ com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(it, object : com.fasterxml.jackson.core.type.TypeReference<type.ApplicantImpl>() {}) }), currentRiskAppetite?.let({ number(it) }), annotationSet_, eventListener_, externalExecutor_, cache_)
        } catch (e: Exception) {
            logError("Cannot apply decision 'AssessIssueRisk'", e)
            null
        }
    }

    fun apply(applicant: type.Applicant?, currentRiskAppetite: java.math.BigDecimal?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet): java.math.BigDecimal? {
        return apply(applicant, currentRiskAppetite, annotationSet_, com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), com.gs.dmn.runtime.cache.DefaultCache())
    }

    fun apply(applicant: type.Applicant?, currentRiskAppetite: java.math.BigDecimal?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): java.math.BigDecimal? {
        try {
            // Start decision 'assessIssueRisk'
            val assessIssueRiskStartTime_ = System.currentTimeMillis()
            val assessIssueRiskArguments_ = com.gs.dmn.runtime.listener.Arguments()
            assessIssueRiskArguments_.put("Applicant", applicant)
            assessIssueRiskArguments_.put("Current risk appetite", currentRiskAppetite)
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, assessIssueRiskArguments_)

            // Apply child decisions
            val processPriorIssues: List<java.math.BigDecimal?>? = this.processPriorIssues.apply(applicant, annotationSet_, eventListener_, externalExecutor_, cache_)

            // Iterate and aggregate
            var output_: java.math.BigDecimal? = evaluate(applicant, currentRiskAppetite, processPriorIssues, annotationSet_, eventListener_, externalExecutor_, cache_)

            // End decision 'assessIssueRisk'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, assessIssueRiskArguments_, output_, (System.currentTimeMillis() - assessIssueRiskStartTime_))

            return output_
        } catch (e: Exception) {
            logError("Exception caught in 'assessIssueRisk' evaluation", e)
            return null
        }
    }

    fun apply(assessIssueRiskRequest_: proto.AssessIssueRiskRequest, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet): proto.AssessIssueRiskResponse {
        return apply(assessIssueRiskRequest_, annotationSet_, com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), com.gs.dmn.runtime.cache.DefaultCache())
    }

    fun apply(assessIssueRiskRequest_: proto.AssessIssueRiskRequest, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): proto.AssessIssueRiskResponse {
        // Create arguments from Request Message
        val applicant: type.Applicant? = type.Applicant.toApplicant(assessIssueRiskRequest_.getApplicant())
        val currentRiskAppetite: java.math.BigDecimal? = java.math.BigDecimal.valueOf(assessIssueRiskRequest_.getCurrentRiskAppetite())

        // Invoke apply method
        val output_: java.math.BigDecimal? = apply(applicant, currentRiskAppetite, annotationSet_, eventListener_, externalExecutor_, cache_)

        // Convert output to Response Message
        val builder_: proto.AssessIssueRiskResponse.Builder = proto.AssessIssueRiskResponse.newBuilder()
        val outputProto_ = (if (output_ == null) 0.0 else output_!!.toDouble())
        builder_.setAssessIssueRisk(outputProto_)
        return builder_.build()
    }

    private inline fun evaluate(applicant: type.Applicant?, currentRiskAppetite: java.math.BigDecimal?, processPriorIssues: List<java.math.BigDecimal?>?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): java.math.BigDecimal? {
        val assessIssue: AssessIssue = AssessIssue()
        return sum(processPriorIssues?.stream()?.map({priorIssue_iterator -> assessIssue.apply(currentRiskAppetite, priorIssue_iterator, annotationSet_, eventListener_, externalExecutor_, cache_)})?.collect(Collectors.toList()))
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
