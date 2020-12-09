
import java.util.*
import java.util.stream.Collectors

@javax.annotation.Generated(value = ["decision.ftl", "Adjudication"])
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "Adjudication",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
class Adjudication() : com.gs.dmn.runtime.DefaultDMNBaseDecision() {
    fun apply(applicantData: String?, bureauData: String?, supportingDocuments: String?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet): String? {
        return try {
            apply(applicantData?.let({ com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(it, object : com.fasterxml.jackson.core.type.TypeReference<type.TApplicantDataImpl>() {}) }), bureauData?.let({ com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(it, object : com.fasterxml.jackson.core.type.TypeReference<type.TBureauDataImpl>() {}) }), supportingDocuments, annotationSet_, com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), com.gs.dmn.runtime.cache.DefaultCache())
        } catch (e: Exception) {
            logError("Cannot apply decision 'Adjudication'", e)
            null
        }
    }

    fun apply(applicantData: String?, bureauData: String?, supportingDocuments: String?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): String? {
        return try {
            apply(applicantData?.let({ com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(it, object : com.fasterxml.jackson.core.type.TypeReference<type.TApplicantDataImpl>() {}) }), bureauData?.let({ com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(it, object : com.fasterxml.jackson.core.type.TypeReference<type.TBureauDataImpl>() {}) }), supportingDocuments, annotationSet_, eventListener_, externalExecutor_, cache_)
        } catch (e: Exception) {
            logError("Cannot apply decision 'Adjudication'", e)
            null
        }
    }

    fun apply(applicantData: type.TApplicantData?, bureauData: type.TBureauData?, supportingDocuments: String?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet): String? {
        return apply(applicantData, bureauData, supportingDocuments, annotationSet_, com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), com.gs.dmn.runtime.cache.DefaultCache())
    }

    fun apply(applicantData: type.TApplicantData?, bureauData: type.TBureauData?, supportingDocuments: String?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): String? {
        try {
            // Start decision 'Adjudication'
            val adjudicationStartTime_ = System.currentTimeMillis()
            val adjudicationArguments_ = com.gs.dmn.runtime.listener.Arguments()
            adjudicationArguments_.put("ApplicantData", applicantData)
            adjudicationArguments_.put("BureauData", bureauData)
            adjudicationArguments_.put("SupportingDocuments", supportingDocuments)
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, adjudicationArguments_)

            // Evaluate decision 'Adjudication'
            val output_: String? = evaluate(applicantData, bureauData, supportingDocuments, annotationSet_, eventListener_, externalExecutor_, cache_)

            // End decision 'Adjudication'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, adjudicationArguments_, output_, (System.currentTimeMillis() - adjudicationStartTime_))

            return output_
        } catch (e: Exception) {
            logError("Exception caught in 'Adjudication' evaluation", e)
            return null
        }
    }

    fun apply(adjudicationRequest_: proto.AdjudicationRequest, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet): proto.AdjudicationResponse {
        return apply(adjudicationRequest_, annotationSet_, com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), com.gs.dmn.runtime.cache.DefaultCache())
    }

    fun apply(adjudicationRequest_: proto.AdjudicationRequest, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): proto.AdjudicationResponse {
        // Create arguments from Request Message
        val applicantData: type.TApplicantData? = type.TApplicantData.toTApplicantData(adjudicationRequest_.getApplicantData())
        val bureauData: type.TBureauData? = type.TBureauData.toTBureauData(adjudicationRequest_.getBureauData())
        val supportingDocuments: String? = adjudicationRequest_.getSupportingDocuments()

        // Invoke apply method
        val output_: String? = apply(applicantData, bureauData, supportingDocuments, annotationSet_, eventListener_, externalExecutor_, cache_)

        // Convert output to Response Message
        val builder_: proto.AdjudicationResponse.Builder = proto.AdjudicationResponse.newBuilder()
        val outputProto_ = (if (output_ == null) "" else output_!!)
        builder_.setAdjudication(outputProto_)
        return builder_.build()
    }

    private inline fun evaluate(applicantData: type.TApplicantData?, bureauData: type.TBureauData?, supportingDocuments: String?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): String? {
        return "ACCEPT" as String?
    }

    companion object {
        val DRG_ELEMENT_METADATA : com.gs.dmn.runtime.listener.DRGElement = com.gs.dmn.runtime.listener.DRGElement(
            "",
            "Adjudication",
            "",
            com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
            com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
            com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
            -1
        )

        @JvmStatic
        fun requestToMap(adjudicationRequest_: proto.AdjudicationRequest): kotlin.collections.Map<String, Any?> {
            // Create arguments from Request Message
            val applicantData: type.TApplicantData? = type.TApplicantData.toTApplicantData(adjudicationRequest_.getApplicantData())
            val bureauData: type.TBureauData? = type.TBureauData.toTBureauData(adjudicationRequest_.getBureauData())
            val supportingDocuments: String? = adjudicationRequest_.getSupportingDocuments()

            // Create map
            val map_: kotlin.collections.MutableMap<String, Any?> = mutableMapOf()
            map_.put("ApplicantData", applicantData)
            map_.put("BureauData", bureauData)
            map_.put("SupportingDocuments", supportingDocuments)
            return map_
        }

        @JvmStatic
        fun responseToOutput(adjudicationResponse_: proto.AdjudicationResponse): String? {
            // Extract and convert output
            return adjudicationResponse_.getAdjudication()
        }
    }
}
