
import java.util.*
import java.util.stream.Collectors

@javax.annotation.Generated(value = ["decision.ftl", "'Pre-bureauRiskCategory'"])
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "'Pre-bureauRiskCategory'",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.INVOCATION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
class PreBureauRiskCategory(val applicationRiskScore : ApplicationRiskScore = ApplicationRiskScore()) : com.gs.dmn.runtime.DefaultDMNBaseDecision() {
    fun apply(applicantData: String?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): String? {
        return try {
            apply(applicantData?.let({ com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(it, object : com.fasterxml.jackson.core.type.TypeReference<type.TApplicantDataImpl>() {}) }), annotationSet_, eventListener_, externalExecutor_, cache_)
        } catch (e: Exception) {
            logError("Cannot apply decision 'PreBureauRiskCategory'", e)
            null
        }
    }

    fun apply(applicantData: type.TApplicantData?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): String? {
        try {
            // Start decision ''Pre-bureauRiskCategory''
            val preBureauRiskCategoryStartTime_ = System.currentTimeMillis()
            val preBureauRiskCategoryArguments_ = com.gs.dmn.runtime.listener.Arguments()
            preBureauRiskCategoryArguments_.put("ApplicantData", applicantData)
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, preBureauRiskCategoryArguments_)

            if (cache_.contains("'Pre-bureauRiskCategory'")) {
                // Retrieve value from cache
                var output_:String? = cache_.lookup("'Pre-bureauRiskCategory'") as String?

                // End decision ''Pre-bureauRiskCategory''
                eventListener_.endDRGElement(DRG_ELEMENT_METADATA, preBureauRiskCategoryArguments_, output_, (System.currentTimeMillis() - preBureauRiskCategoryStartTime_))

                return output_
            } else {
                // Evaluate decision ''Pre-bureauRiskCategory''
                val output_: String? = evaluate(applicantData, annotationSet_, eventListener_, externalExecutor_, cache_)
                cache_.bind("'Pre-bureauRiskCategory'", output_)

                // End decision ''Pre-bureauRiskCategory''
                eventListener_.endDRGElement(DRG_ELEMENT_METADATA, preBureauRiskCategoryArguments_, output_, (System.currentTimeMillis() - preBureauRiskCategoryStartTime_))

                return output_
            }
        } catch (e: Exception) {
            logError("Exception caught in ''Pre-bureauRiskCategory'' evaluation", e)
            return null
        }
    }

    fun apply(preBureauRiskCategoryRequest_: proto.PreBureauRiskCategoryRequest, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): proto.PreBureauRiskCategoryResponse {
        // Create arguments from Request Message
        val applicantData: type.TApplicantData? = type.TApplicantData.toTApplicantData(preBureauRiskCategoryRequest_.getApplicantData())

        // Invoke apply method
        val output_: String? = apply(applicantData, annotationSet_, eventListener_, externalExecutor_, cache_)

        // Convert output to Response Message
        val builder_: proto.PreBureauRiskCategoryResponse.Builder = proto.PreBureauRiskCategoryResponse.newBuilder()
        val outputProto_ = (if (output_ == null) "" else output_!!)
        builder_.setPreBureauRiskCategory(outputProto_)
        return builder_.build()
    }

    private inline fun evaluate(applicantData: type.TApplicantData?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): String? {
        // Apply child decisions
        val applicationRiskScore: java.math.BigDecimal? = this@PreBureauRiskCategory.applicationRiskScore.apply(applicantData, annotationSet_, eventListener_, externalExecutor_, cache_)

        return PreBureauRiskCategoryTable.instance().apply(applicantData?.let({ it.existingCustomer as Boolean? }), applicationRiskScore, annotationSet_, eventListener_, externalExecutor_, cache_) as String?
    }

    companion object {
        val DRG_ELEMENT_METADATA : com.gs.dmn.runtime.listener.DRGElement = com.gs.dmn.runtime.listener.DRGElement(
            "",
            "'Pre-bureauRiskCategory'",
            "",
            com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
            com.gs.dmn.runtime.annotation.ExpressionKind.INVOCATION,
            com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
            -1
        )

        @JvmStatic
        fun requestToMap(preBureauRiskCategoryRequest_: proto.PreBureauRiskCategoryRequest): kotlin.collections.Map<String, Any?> {
            // Create arguments from Request Message
            val applicantData: type.TApplicantData? = type.TApplicantData.toTApplicantData(preBureauRiskCategoryRequest_.getApplicantData())

            // Create map
            val map_: kotlin.collections.MutableMap<String, Any?> = mutableMapOf()
            map_.put("ApplicantData", applicantData)
            return map_
        }

        @JvmStatic
        fun responseToOutput(preBureauRiskCategoryResponse_: proto.PreBureauRiskCategoryResponse): String? {
            // Extract and convert output
            return preBureauRiskCategoryResponse_.getPreBureauRiskCategory()
        }
    }
}
