
import java.util.*
import java.util.stream.Collectors

@javax.annotation.Generated(value = ["decision.ftl", "PreBureauRiskCategory"])
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "PreBureauRiskCategory",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.INVOCATION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
class PreBureauRiskCategory(val applicationRiskScore : ApplicationRiskScore = ApplicationRiskScore()) : com.gs.dmn.runtime.DefaultDMNBaseDecision() {
    fun apply(applicantData: String?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet): String? {
        return try {
            apply(applicantData?.let({ com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(it, object : com.fasterxml.jackson.core.type.TypeReference<type.TApplicantDataImpl>() {}) }), annotationSet_, com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), com.gs.dmn.runtime.cache.DefaultCache())
        } catch (e: Exception) {
            logError("Cannot apply decision 'PreBureauRiskCategory'", e)
            null
        }
    }

    fun apply(applicantData: String?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): String? {
        return try {
            apply(applicantData?.let({ com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(it, object : com.fasterxml.jackson.core.type.TypeReference<type.TApplicantDataImpl>() {}) }), annotationSet_, eventListener_, externalExecutor_, cache_)
        } catch (e: Exception) {
            logError("Cannot apply decision 'PreBureauRiskCategory'", e)
            null
        }
    }

    fun apply(applicantData: type.TApplicantData?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet): String? {
        return apply(applicantData, annotationSet_, com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), com.gs.dmn.runtime.cache.DefaultCache())
    }

    fun apply(applicantData: type.TApplicantData?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): String? {
        try {
            // Start decision 'PreBureauRiskCategory'
            val preBureauRiskCategoryStartTime_ = System.currentTimeMillis()
            val preBureauRiskCategoryArguments_ = com.gs.dmn.runtime.listener.Arguments()
            preBureauRiskCategoryArguments_.put("ApplicantData", applicantData)
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, preBureauRiskCategoryArguments_)

            if (cache_.contains("PreBureauRiskCategory")) {
                // Retrieve value from cache
                var output_:String? = cache_.lookup("PreBureauRiskCategory") as String?

                // End decision 'PreBureauRiskCategory'
                eventListener_.endDRGElement(DRG_ELEMENT_METADATA, preBureauRiskCategoryArguments_, output_, (System.currentTimeMillis() - preBureauRiskCategoryStartTime_))

                return output_
            } else {
                // Apply child decisions
                val applicationRiskScore: java.math.BigDecimal? = this.applicationRiskScore.apply(applicantData, annotationSet_, eventListener_, externalExecutor_, cache_)

                // Evaluate decision 'PreBureauRiskCategory'
                val output_: String? = evaluate(applicantData, applicationRiskScore, annotationSet_, eventListener_, externalExecutor_, cache_)
                cache_.bind("PreBureauRiskCategory", output_)

                // End decision 'PreBureauRiskCategory'
                eventListener_.endDRGElement(DRG_ELEMENT_METADATA, preBureauRiskCategoryArguments_, output_, (System.currentTimeMillis() - preBureauRiskCategoryStartTime_))

                return output_
            }
        } catch (e: Exception) {
            logError("Exception caught in 'PreBureauRiskCategory' evaluation", e)
            return null
        }
    }

    fun apply(preBureauRiskCategoryRequest_: proto.PreBureauRiskCategoryRequest, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet): proto.PreBureauRiskCategoryResponse {
        return apply(preBureauRiskCategoryRequest_, annotationSet_, com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), com.gs.dmn.runtime.cache.DefaultCache())
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

    private inline fun evaluate(applicantData: type.TApplicantData?, applicationRiskScore: java.math.BigDecimal?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): String? {
        return PreBureauRiskCategoryTable.PreBureauRiskCategoryTable(applicantData?.let({ it.existingCustomer as Boolean? }), applicationRiskScore, annotationSet_, eventListener_, externalExecutor_, cache_) as String?
    }

    companion object {
        val DRG_ELEMENT_METADATA : com.gs.dmn.runtime.listener.DRGElement = com.gs.dmn.runtime.listener.DRGElement(
            "",
            "PreBureauRiskCategory",
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
