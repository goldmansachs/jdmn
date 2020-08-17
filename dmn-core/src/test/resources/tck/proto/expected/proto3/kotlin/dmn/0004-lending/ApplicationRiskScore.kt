
import java.util.*
import java.util.stream.Collectors

@javax.annotation.Generated(value = ["decision.ftl", "ApplicationRiskScore"])
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "ApplicationRiskScore",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.INVOCATION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
class ApplicationRiskScore() : com.gs.dmn.runtime.DefaultDMNBaseDecision() {
    fun apply(applicantData: String?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet): java.math.BigDecimal? {
        return try {
            apply(applicantData?.let({ com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(it, object : com.fasterxml.jackson.core.type.TypeReference<type.TApplicantDataImpl>() {}) }), annotationSet_, com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), com.gs.dmn.runtime.cache.DefaultCache())
        } catch (e: Exception) {
            logError("Cannot apply decision 'ApplicationRiskScore'", e)
            null
        }
    }

    fun apply(applicantData: String?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor): java.math.BigDecimal? {
        return try {
            val cache_ = com.gs.dmn.runtime.cache.DefaultCache()
            apply(applicantData?.let({ com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(it, object : com.fasterxml.jackson.core.type.TypeReference<type.TApplicantDataImpl>() {}) }), annotationSet_, eventListener_, externalExecutor_, cache_)
        } catch (e: Exception) {
            logError("Cannot apply decision 'ApplicationRiskScore'", e)
            null
        }
    }

    fun apply(applicantData: String?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): java.math.BigDecimal? {
        return try {
            apply(applicantData?.let({ com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(it, object : com.fasterxml.jackson.core.type.TypeReference<type.TApplicantDataImpl>() {}) }), annotationSet_, eventListener_, externalExecutor_, cache_)
        } catch (e: Exception) {
            logError("Cannot apply decision 'ApplicationRiskScore'", e)
            null
        }
    }

    fun apply(applicantData: type.TApplicantData?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet): java.math.BigDecimal? {
        return apply(applicantData, annotationSet_, com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), com.gs.dmn.runtime.cache.DefaultCache())
    }

    fun apply(applicantData: type.TApplicantData?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): java.math.BigDecimal? {
        try {
            // Start decision 'ApplicationRiskScore'
            val applicationRiskScoreStartTime_ = System.currentTimeMillis()
            val applicationRiskScoreArguments_ = com.gs.dmn.runtime.listener.Arguments()
            applicationRiskScoreArguments_.put("ApplicantData", applicantData);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, applicationRiskScoreArguments_)

            if (cache_.contains("ApplicationRiskScore")) {
                // Retrieve value from cache
                var output_:java.math.BigDecimal? = cache_.lookup("ApplicationRiskScore") as java.math.BigDecimal?

                // End decision 'ApplicationRiskScore'
                eventListener_.endDRGElement(DRG_ELEMENT_METADATA, applicationRiskScoreArguments_, output_, (System.currentTimeMillis() - applicationRiskScoreStartTime_))

                return output_
            } else {
                // Evaluate decision 'ApplicationRiskScore'
                val output_: java.math.BigDecimal? = evaluate(applicantData, annotationSet_, eventListener_, externalExecutor_, cache_)
                cache_.bind("ApplicationRiskScore", output_)

                // End decision 'ApplicationRiskScore'
                eventListener_.endDRGElement(DRG_ELEMENT_METADATA, applicationRiskScoreArguments_, output_, (System.currentTimeMillis() - applicationRiskScoreStartTime_))

                return output_
            }
        } catch (e: Exception) {
            logError("Exception caught in 'ApplicationRiskScore' evaluation", e)
            return null
        }
    }

    fun apply(applicationRiskScoreRequest_: proto.ApplicationRiskScoreRequest, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet): proto.ApplicationRiskScoreResponse {
        return apply(applicationRiskScoreRequest_, annotationSet_, com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), com.gs.dmn.runtime.cache.DefaultCache())
    }

    fun apply(applicationRiskScoreRequest_: proto.ApplicationRiskScoreRequest, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): proto.ApplicationRiskScoreResponse {
        // Create arguments from Request Message
        var applicantData: type.TApplicantData? = type.TApplicantData.toTApplicantData(applicationRiskScoreRequest_.getApplicantData())
        
        // Invoke apply method
        var output_: java.math.BigDecimal? = apply(applicantData, annotationSet_, eventListener_, externalExecutor_, cache_)
        
        // Convert output to Response Message
        var builder_: proto.ApplicationRiskScoreResponse.Builder = proto.ApplicationRiskScoreResponse.newBuilder()
        builder_.setApplicationRiskScore((if (output_ == null) 0.0 else output_!!.toDouble()))
        return builder_.build()
    }

    private inline fun evaluate(applicantData: type.TApplicantData?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): java.math.BigDecimal? {
        return ApplicationRiskScoreModel.ApplicationRiskScoreModel(applicantData?.let({ it.age as java.math.BigDecimal? }), applicantData?.let({ it.maritalStatus as String? }), applicantData?.let({ it.employmentStatus as String? }), annotationSet_, eventListener_, externalExecutor_, cache_) as java.math.BigDecimal?
    }

    companion object {
        val DRG_ELEMENT_METADATA : com.gs.dmn.runtime.listener.DRGElement = com.gs.dmn.runtime.listener.DRGElement(
            "",
            "ApplicationRiskScore",
            "",
            com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
            com.gs.dmn.runtime.annotation.ExpressionKind.INVOCATION,
            com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
            -1
        )
    }
}
