
import java.util.*
import java.util.stream.Collectors

@javax.annotation.Generated(value = ["decision.ftl", "decision5"])
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "decision5",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
class Decision5() : com.gs.dmn.runtime.DefaultDMNBaseDecision() {
    fun apply(employees: String?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet): String? {
        return try {
            apply(employees?.let({ com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(it, object : com.fasterxml.jackson.core.type.TypeReference<List<String?>?>() {}) }), annotationSet_, com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), com.gs.dmn.runtime.cache.DefaultCache())
        } catch (e: Exception) {
            logError("Cannot apply decision 'Decision5'", e)
            null
        }
    }

    fun apply(employees: String?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): String? {
        return try {
            apply(employees?.let({ com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(it, object : com.fasterxml.jackson.core.type.TypeReference<List<String?>?>() {}) }), annotationSet_, eventListener_, externalExecutor_, cache_)
        } catch (e: Exception) {
            logError("Cannot apply decision 'Decision5'", e)
            null
        }
    }

    fun apply(employees: List<String?>?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet): String? {
        return apply(employees, annotationSet_, com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), com.gs.dmn.runtime.cache.DefaultCache())
    }

    fun apply(employees: List<String?>?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): String? {
        try {
            // Start decision 'decision5'
            val decision5StartTime_ = System.currentTimeMillis()
            val decision5Arguments_ = com.gs.dmn.runtime.listener.Arguments()
            decision5Arguments_.put("Employees", employees)
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, decision5Arguments_)

            // Evaluate decision 'decision5'
            val output_: String? = evaluate(employees, annotationSet_, eventListener_, externalExecutor_, cache_)

            // End decision 'decision5'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, decision5Arguments_, output_, (System.currentTimeMillis() - decision5StartTime_))

            return output_
        } catch (e: Exception) {
            logError("Exception caught in 'decision5' evaluation", e)
            return null
        }
    }

    private inline fun evaluate(employees: List<String?>?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): String? {
        return upperCase(asElement(employees?.filter({ item -> stringEqual(item, "Bob") }))) as String?
    }

    companion object {
        val DRG_ELEMENT_METADATA : com.gs.dmn.runtime.listener.DRGElement = com.gs.dmn.runtime.listener.DRGElement(
            "",
            "decision5",
            "",
            com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
            com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
            com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
            -1
        )
    }
}
