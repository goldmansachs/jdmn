
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
            apply(applicant?.let({ com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(it, object : com.fasterxml.jackson.core.type.TypeReference<type.ApplicantImpl>() {}) }), currentRiskAppetite?.let({ number(it) }), annotationSet_, com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor())
        } catch (e: Exception) {
            logError("Cannot apply decision 'AssessIssueRisk'", e)
            null
        }
    }

    fun apply(applicant: String?, currentRiskAppetite: String?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor): java.math.BigDecimal? {
        return try {
            apply(applicant?.let({ com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(it, object : com.fasterxml.jackson.core.type.TypeReference<type.ApplicantImpl>() {}) }), currentRiskAppetite?.let({ number(it) }), annotationSet_, eventListener_, externalExecutor_)
        } catch (e: Exception) {
            logError("Cannot apply decision 'AssessIssueRisk'", e)
            null
        }
    }

    fun apply(applicant: type.Applicant?, currentRiskAppetite: java.math.BigDecimal?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet): java.math.BigDecimal? {
        return apply(applicant, currentRiskAppetite, annotationSet_, com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor())
    }

    fun apply(applicant: type.Applicant?, currentRiskAppetite: java.math.BigDecimal?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor): java.math.BigDecimal? {
        try {
            // Start decision 'assessIssueRisk'
            val assessIssueRiskStartTime_ = System.currentTimeMillis()
            val assessIssueRiskArguments_ = com.gs.dmn.runtime.listener.Arguments()
            assessIssueRiskArguments_.put("Applicant", applicant);
            assessIssueRiskArguments_.put("Current risk appetite", currentRiskAppetite);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, assessIssueRiskArguments_)

            // Apply child decisions
            val processPriorIssues: List<java.math.BigDecimal?>? = this.processPriorIssues.apply(applicant, annotationSet_, eventListener_, externalExecutor_)

            // Iterate and aggregate
            var output_: java.math.BigDecimal? = evaluate(applicant, currentRiskAppetite, processPriorIssues, annotationSet_, eventListener_, externalExecutor_)

            // End decision 'assessIssueRisk'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, assessIssueRiskArguments_, output_, (System.currentTimeMillis() - assessIssueRiskStartTime_))

            return output_
        } catch (e: Exception) {
            logError("Exception caught in 'assessIssueRisk' evaluation", e)
            return null
        }
    }

    fun apply(assessIssueRiskRequest_: proto.AssessIssueRiskRequest, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet): proto.AssessIssueRiskResponse {
        return apply(assessIssueRiskRequest_, annotationSet_, com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor())
    }

    fun apply(assessIssueRiskRequest_: proto.AssessIssueRiskRequest, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor): proto.AssessIssueRiskResponse {
        // Create arguments from Request Message
        var applicant: type.Applicant? = type.Applicant.toApplicant(assessIssueRiskRequest_.getApplicant())
        var currentRiskAppetite: java.math.BigDecimal? = java.math.BigDecimal.valueOf(assessIssueRiskRequest_.getCurrentRiskAppetite())
        
        // Invoke apply method
        var output_: java.math.BigDecimal? = apply(applicant, currentRiskAppetite, annotationSet_, eventListener_, externalExecutor_)
        
        // Convert output to Response Message
        var builder_: proto.AssessIssueRiskResponse.Builder = proto.AssessIssueRiskResponse.newBuilder()
        builder_.setAssessIssueRisk((if (output_ == null) 0.0 else output_!!.toDouble()))
        return builder_.build()
    }

    private inline fun evaluate(applicant: type.Applicant?, currentRiskAppetite: java.math.BigDecimal?, processPriorIssues: List<java.math.BigDecimal?>?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor): java.math.BigDecimal? {
        val assessIssue: AssessIssue = AssessIssue()
        return sum(processPriorIssues?.stream()?.map({priorIssue_iterator -> assessIssue.apply(currentRiskAppetite, priorIssue_iterator, annotationSet_, eventListener_, externalExecutor_)})?.collect(Collectors.toList()))
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
    }
}
