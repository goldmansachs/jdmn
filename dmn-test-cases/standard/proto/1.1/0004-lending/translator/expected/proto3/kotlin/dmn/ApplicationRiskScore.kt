
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
class ApplicationRiskScore() : com.gs.dmn.runtime.JavaTimeDMNBaseDecision() {
    override fun applyMap(input_: MutableMap<String, String>, context_: com.gs.dmn.runtime.ExecutionContext): kotlin.Number? {
        try {
            return apply(input_.get("ApplicantData")?.let({ com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(it, object : com.fasterxml.jackson.core.type.TypeReference<type.TApplicantDataImpl>() {}) }), context_)
        } catch (e: Exception) {
            logError("Cannot apply decision 'ApplicationRiskScore'", e)
            return null
        }
    }

    fun apply(applicantData: type.TApplicantData?, context_: com.gs.dmn.runtime.ExecutionContext): kotlin.Number? {
        try {
            // Start decision 'ApplicationRiskScore'
            var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
            var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
            var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
            var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
            val applicationRiskScoreStartTime_ = System.currentTimeMillis()
            val applicationRiskScoreArguments_ = com.gs.dmn.runtime.listener.Arguments()
            applicationRiskScoreArguments_.put("ApplicantData", applicantData)
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, applicationRiskScoreArguments_)

            if (cache_.contains("ApplicationRiskScore")) {
                // Retrieve value from cache
                var output_:kotlin.Number? = cache_.lookup("ApplicationRiskScore") as kotlin.Number?

                // End decision 'ApplicationRiskScore'
                eventListener_.endDRGElement(DRG_ELEMENT_METADATA, applicationRiskScoreArguments_, output_, (System.currentTimeMillis() - applicationRiskScoreStartTime_))

                return output_
            } else {
                // Evaluate decision 'ApplicationRiskScore'
                val output_: kotlin.Number? = evaluate(applicantData, context_)
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

    fun applyProto(applicationRiskScoreRequest_: proto.ApplicationRiskScoreRequest, context_: com.gs.dmn.runtime.ExecutionContext): proto.ApplicationRiskScoreResponse {
        // Create arguments from Request Message
        val applicantData: type.TApplicantData? = type.TApplicantData.toTApplicantData(applicationRiskScoreRequest_.getApplicantData())

        // Invoke apply method
        val output_: kotlin.Number? = apply(applicantData, context_)

        // Convert output to Response Message
        val builder_: proto.ApplicationRiskScoreResponse.Builder = proto.ApplicationRiskScoreResponse.newBuilder()
        val outputProto_ = (if (output_ == null) 0.0 else output_!!.toDouble())
        builder_.setApplicationRiskScore(outputProto_)
        return builder_.build()
    }

    private inline fun evaluate(applicantData: type.TApplicantData?, context_: com.gs.dmn.runtime.ExecutionContext): kotlin.Number? {
        var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
        var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
        var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
        var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
        return ApplicationRiskScoreModel.instance().apply(applicantData?.let({ it.age as kotlin.Number? }), applicantData?.let({ it.maritalStatus as String? }), applicantData?.let({ it.employmentStatus as String? }), context_) as kotlin.Number?
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

        @JvmStatic
        fun requestToMap(applicationRiskScoreRequest_: proto.ApplicationRiskScoreRequest): kotlin.collections.Map<String, Any?> {
            // Create arguments from Request Message
            val applicantData: type.TApplicantData? = type.TApplicantData.toTApplicantData(applicationRiskScoreRequest_.getApplicantData())

            // Create map
            val map_: kotlin.collections.MutableMap<String, Any?> = mutableMapOf()
            map_.put("ApplicantData", applicantData)
            return map_
        }

        @JvmStatic
        fun responseToOutput(applicationRiskScoreResponse_: proto.ApplicationRiskScoreResponse): kotlin.Number? {
            // Extract and convert output
            return (java.math.BigDecimal.valueOf(applicationRiskScoreResponse_.getApplicationRiskScore()) as kotlin.Number)
        }
    }
}
