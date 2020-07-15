
import java.util.*
import java.util.stream.Collectors

@javax.annotation.Generated(value = ["decision.ftl", "decision4"])
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "decision4",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
class Decision4() : com.gs.dmn.runtime.DefaultDMNBaseDecision() {
    fun apply(employees: String?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet): String? {
        return try {
            apply(employees?.let({ com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(it, object : com.fasterxml.jackson.core.type.TypeReference<List<String?>?>() {}) }), annotationSet_, com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor())
        } catch (e: Exception) {
            logError("Cannot apply decision 'Decision4'", e)
            null
        }
    }

    fun apply(employees: String?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor): String? {
        return try {
            apply(employees?.let({ com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(it, object : com.fasterxml.jackson.core.type.TypeReference<List<String?>?>() {}) }), annotationSet_, eventListener_, externalExecutor_)
        } catch (e: Exception) {
            logError("Cannot apply decision 'Decision4'", e)
            null
        }
    }

    fun apply(employees: List<String?>?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet): String? {
        return apply(employees, annotationSet_, com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor())
    }

    fun apply(employees: List<String?>?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor): String? {
        try {
            // Start decision 'decision4'
            val decision4StartTime_ = System.currentTimeMillis()
            val decision4Arguments_ = com.gs.dmn.runtime.listener.Arguments()
            decision4Arguments_.put("Employees", employees);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, decision4Arguments_)

            // Evaluate decision 'decision4'
            val output_: String? = evaluate(employees, annotationSet_, eventListener_, externalExecutor_)

            // End decision 'decision4'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, decision4Arguments_, output_, (System.currentTimeMillis() - decision4StartTime_))

            return output_
        } catch (e: Exception) {
            logError("Exception caught in 'decision4' evaluation", e)
            return null
        }
    }

    private inline fun evaluate(employees: List<String?>?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor): String? {
        return asElement(employees?.filter({ item -> stringEqual(item, "Bob") })) as String?
    }

    companion object {
        val DRG_ELEMENT_METADATA : com.gs.dmn.runtime.listener.DRGElement = com.gs.dmn.runtime.listener.DRGElement(
            "",
            "decision4",
            "",
            com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
            com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
            com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
            -1
        )
    }
}
