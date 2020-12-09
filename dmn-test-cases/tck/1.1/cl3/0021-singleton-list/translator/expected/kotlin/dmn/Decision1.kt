
import java.util.*
import java.util.stream.Collectors

@javax.annotation.Generated(value = ["decision.ftl", "decision1"])
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "decision1",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
class Decision1() : com.gs.dmn.runtime.DefaultDMNBaseDecision() {
    fun apply(employees: String?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet): List<String?>? {
        return try {
            apply(employees?.let({ com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(it, object : com.fasterxml.jackson.core.type.TypeReference<List<String?>?>() {}) }), annotationSet_, com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), com.gs.dmn.runtime.cache.DefaultCache())
        } catch (e: Exception) {
            logError("Cannot apply decision 'Decision1'", e)
            null
        }
    }

    fun apply(employees: String?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): List<String?>? {
        return try {
            apply(employees?.let({ com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(it, object : com.fasterxml.jackson.core.type.TypeReference<List<String?>?>() {}) }), annotationSet_, eventListener_, externalExecutor_, cache_)
        } catch (e: Exception) {
            logError("Cannot apply decision 'Decision1'", e)
            null
        }
    }

    fun apply(employees: List<String?>?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet): List<String?>? {
        return apply(employees, annotationSet_, com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), com.gs.dmn.runtime.cache.DefaultCache())
    }

    fun apply(employees: List<String?>?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): List<String?>? {
        try {
            // Start decision 'decision1'
            val decision1StartTime_ = System.currentTimeMillis()
            val decision1Arguments_ = com.gs.dmn.runtime.listener.Arguments()
            decision1Arguments_.put("Employees", employees)
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, decision1Arguments_)

            // Evaluate decision 'decision1'
            val output_: List<String?>? = evaluate(employees, annotationSet_, eventListener_, externalExecutor_, cache_)

            // End decision 'decision1'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, decision1Arguments_, output_, (System.currentTimeMillis() - decision1StartTime_))

            return output_
        } catch (e: Exception) {
            logError("Exception caught in 'decision1' evaluation", e)
            return null
        }
    }

    private inline fun evaluate(employees: List<String?>?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): List<String?>? {
        return sublist(employees, number("2"), number("1")) as List<String?>?
    }

    companion object {
        val DRG_ELEMENT_METADATA : com.gs.dmn.runtime.listener.DRGElement = com.gs.dmn.runtime.listener.DRGElement(
            "",
            "decision1",
            "",
            com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
            com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
            com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
            -1
        )
    }
}
