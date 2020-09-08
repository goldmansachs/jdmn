
import java.util.*
import java.util.stream.Collectors

@javax.annotation.Generated(value = ["decision.ftl", "BureauCallType"])
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "BureauCallType",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.INVOCATION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
class BureauCallType(val preBureauRiskCategory : PreBureauRiskCategory = PreBureauRiskCategory()) : com.gs.dmn.runtime.DefaultDMNBaseDecision() {
    fun apply(applicantData: String?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet): String? {
        return try {
            apply(applicantData?.let({ com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(it, object : com.fasterxml.jackson.core.type.TypeReference<type.TApplicantDataImpl>() {}) }), annotationSet_, com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), com.gs.dmn.runtime.cache.DefaultCache())
        } catch (e: Exception) {
            logError("Cannot apply decision 'BureauCallType'", e)
            null
        }
    }

    fun apply(applicantData: String?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): String? {
        return try {
            apply(applicantData?.let({ com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(it, object : com.fasterxml.jackson.core.type.TypeReference<type.TApplicantDataImpl>() {}) }), annotationSet_, eventListener_, externalExecutor_, cache_)
        } catch (e: Exception) {
            logError("Cannot apply decision 'BureauCallType'", e)
            null
        }
    }

    fun apply(applicantData: type.TApplicantData?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet): String? {
        return apply(applicantData, annotationSet_, com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), com.gs.dmn.runtime.cache.DefaultCache())
    }

    fun apply(applicantData: type.TApplicantData?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): String? {
        try {
            // Start decision 'BureauCallType'
            val bureauCallTypeStartTime_ = System.currentTimeMillis()
            val bureauCallTypeArguments_ = com.gs.dmn.runtime.listener.Arguments()
            bureauCallTypeArguments_.put("ApplicantData", applicantData)
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, bureauCallTypeArguments_)

            // Apply child decisions
            val preBureauRiskCategory: String? = this.preBureauRiskCategory.apply(applicantData, annotationSet_, eventListener_, externalExecutor_, cache_)

            // Evaluate decision 'BureauCallType'
            val output_: String? = evaluate(preBureauRiskCategory, annotationSet_, eventListener_, externalExecutor_, cache_)

            // End decision 'BureauCallType'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, bureauCallTypeArguments_, output_, (System.currentTimeMillis() - bureauCallTypeStartTime_))

            return output_
        } catch (e: Exception) {
            logError("Exception caught in 'BureauCallType' evaluation", e)
            return null
        }
    }

    private inline fun evaluate(preBureauRiskCategory: String?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): String? {
        return BureauCallTypeTable.BureauCallTypeTable(preBureauRiskCategory, annotationSet_, eventListener_, externalExecutor_, cache_) as String?
    }

    companion object {
        val DRG_ELEMENT_METADATA : com.gs.dmn.runtime.listener.DRGElement = com.gs.dmn.runtime.listener.DRGElement(
            "",
            "BureauCallType",
            "",
            com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
            com.gs.dmn.runtime.annotation.ExpressionKind.INVOCATION,
            com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
            -1
        )
    }
}
