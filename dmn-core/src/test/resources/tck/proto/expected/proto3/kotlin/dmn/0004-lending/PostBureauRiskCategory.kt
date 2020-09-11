
import java.util.*
import java.util.stream.Collectors

@javax.annotation.Generated(value = ["decision.ftl", "PostBureauRiskCategory"])
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "PostBureauRiskCategory",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.INVOCATION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
class PostBureauRiskCategory(val applicationRiskScore : ApplicationRiskScore = ApplicationRiskScore()) : com.gs.dmn.runtime.DefaultDMNBaseDecision() {
    fun apply(applicantData: String?, bureauData: String?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet): String? {
        return try {
            apply(applicantData?.let({ com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(it, object : com.fasterxml.jackson.core.type.TypeReference<type.TApplicantDataImpl>() {}) }), bureauData?.let({ com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(it, object : com.fasterxml.jackson.core.type.TypeReference<type.TBureauDataImpl>() {}) }), annotationSet_, com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), com.gs.dmn.runtime.cache.DefaultCache())
        } catch (e: Exception) {
            logError("Cannot apply decision 'PostBureauRiskCategory'", e)
            null
        }
    }

    fun apply(applicantData: String?, bureauData: String?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): String? {
        return try {
            apply(applicantData?.let({ com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(it, object : com.fasterxml.jackson.core.type.TypeReference<type.TApplicantDataImpl>() {}) }), bureauData?.let({ com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(it, object : com.fasterxml.jackson.core.type.TypeReference<type.TBureauDataImpl>() {}) }), annotationSet_, eventListener_, externalExecutor_, cache_)
        } catch (e: Exception) {
            logError("Cannot apply decision 'PostBureauRiskCategory'", e)
            null
        }
    }

    fun apply(applicantData: type.TApplicantData?, bureauData: type.TBureauData?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet): String? {
        return apply(applicantData, bureauData, annotationSet_, com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), com.gs.dmn.runtime.cache.DefaultCache())
    }

    fun apply(applicantData: type.TApplicantData?, bureauData: type.TBureauData?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): String? {
        try {
            // Start decision 'PostBureauRiskCategory'
            val postBureauRiskCategoryStartTime_ = System.currentTimeMillis()
            val postBureauRiskCategoryArguments_ = com.gs.dmn.runtime.listener.Arguments()
            postBureauRiskCategoryArguments_.put("ApplicantData", applicantData)
            postBureauRiskCategoryArguments_.put("BureauData", bureauData)
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, postBureauRiskCategoryArguments_)

            if (cache_.contains("PostBureauRiskCategory")) {
                // Retrieve value from cache
                var output_:String? = cache_.lookup("PostBureauRiskCategory") as String?

                // End decision 'PostBureauRiskCategory'
                eventListener_.endDRGElement(DRG_ELEMENT_METADATA, postBureauRiskCategoryArguments_, output_, (System.currentTimeMillis() - postBureauRiskCategoryStartTime_))

                return output_
            } else {
                // Apply child decisions
                val applicationRiskScore: java.math.BigDecimal? = this.applicationRiskScore.apply(applicantData, annotationSet_, eventListener_, externalExecutor_, cache_)

                // Evaluate decision 'PostBureauRiskCategory'
                val output_: String? = evaluate(applicantData, applicationRiskScore, bureauData, annotationSet_, eventListener_, externalExecutor_, cache_)
                cache_.bind("PostBureauRiskCategory", output_)

                // End decision 'PostBureauRiskCategory'
                eventListener_.endDRGElement(DRG_ELEMENT_METADATA, postBureauRiskCategoryArguments_, output_, (System.currentTimeMillis() - postBureauRiskCategoryStartTime_))

                return output_
            }
        } catch (e: Exception) {
            logError("Exception caught in 'PostBureauRiskCategory' evaluation", e)
            return null
        }
    }

    fun apply(postBureauRiskCategoryRequest_: proto.PostBureauRiskCategoryRequest, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet): proto.PostBureauRiskCategoryResponse {
        return apply(postBureauRiskCategoryRequest_, annotationSet_, com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), com.gs.dmn.runtime.cache.DefaultCache())
    }

    fun apply(postBureauRiskCategoryRequest_: proto.PostBureauRiskCategoryRequest, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): proto.PostBureauRiskCategoryResponse {
        // Create arguments from Request Message
        val applicantData: type.TApplicantData? = type.TApplicantData.toTApplicantData(postBureauRiskCategoryRequest_.getApplicantData())
        val bureauData: type.TBureauData? = type.TBureauData.toTBureauData(postBureauRiskCategoryRequest_.getBureauData())

        // Invoke apply method
        val output_: String? = apply(applicantData, bureauData, annotationSet_, eventListener_, externalExecutor_, cache_)

        // Convert output to Response Message
        val builder_: proto.PostBureauRiskCategoryResponse.Builder = proto.PostBureauRiskCategoryResponse.newBuilder()
        val outputProto_ = (if (output_ == null) "" else output_!!)
        builder_.setPostBureauRiskCategory(outputProto_)
        return builder_.build()
    }

    private inline fun evaluate(applicantData: type.TApplicantData?, applicationRiskScore: java.math.BigDecimal?, bureauData: type.TBureauData?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): String? {
        return PostBureauRiskCategoryTable.PostBureauRiskCategoryTable(applicantData?.let({ it.existingCustomer as Boolean? }), applicationRiskScore, bureauData?.let({ it.creditScore as java.math.BigDecimal? }), annotationSet_, eventListener_, externalExecutor_, cache_) as String?
    }

    companion object {
        val DRG_ELEMENT_METADATA : com.gs.dmn.runtime.listener.DRGElement = com.gs.dmn.runtime.listener.DRGElement(
            "",
            "PostBureauRiskCategory",
            "",
            com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
            com.gs.dmn.runtime.annotation.ExpressionKind.INVOCATION,
            com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
            -1
        )

        @JvmStatic
        fun requestToMap(postBureauRiskCategoryRequest_: proto.PostBureauRiskCategoryRequest): kotlin.collections.Map<String, Any?> {
            // Create arguments from Request Message
            val applicantData: type.TApplicantData? = type.TApplicantData.toTApplicantData(postBureauRiskCategoryRequest_.getApplicantData())
            val bureauData: type.TBureauData? = type.TBureauData.toTBureauData(postBureauRiskCategoryRequest_.getBureauData())

            // Create map
            val map_: kotlin.collections.MutableMap<String, Any?> = mutableMapOf()
            map_.put("ApplicantData", applicantData)
            map_.put("BureauData", bureauData)
            return map_
        }

        @JvmStatic
        fun responseToOutput(postBureauRiskCategoryResponse_: proto.PostBureauRiskCategoryResponse): String? {
            // Extract and convert output
            return postBureauRiskCategoryResponse_.getPostBureauRiskCategory()
        }
    }
}
