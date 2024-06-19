
import java.util.*
import java.util.stream.Collectors

@javax.annotation.Generated(value = ["decision.ftl", "decision3"])
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "decision3",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
class Decision3() : com.gs.dmn.runtime.JavaTimeDMNBaseDecision() {
    override fun applyMap(input_: MutableMap<String, String>, context_: com.gs.dmn.runtime.ExecutionContext): List<String?>? {
        try {
            return apply(input_.get("Employees")?.let({ com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(it, object : com.fasterxml.jackson.core.type.TypeReference<List<String?>?>() {}) }), context_)
        } catch (e: Exception) {
            logError("Cannot apply decision 'Decision3'", e)
            return null
        }
    }

    fun apply(employees: List<String?>?, context_: com.gs.dmn.runtime.ExecutionContext): List<String?>? {
        try {
            // Start decision 'decision3'
            var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
            var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
            var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
            var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
            val decision3StartTime_ = System.currentTimeMillis()
            val decision3Arguments_ = com.gs.dmn.runtime.listener.Arguments()
            decision3Arguments_.put("Employees", employees)
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, decision3Arguments_)

            // Evaluate decision 'decision3'
            val output_: List<String?>? = evaluate(employees, context_)

            // End decision 'decision3'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, decision3Arguments_, output_, (System.currentTimeMillis() - decision3StartTime_))

            return output_
        } catch (e: Exception) {
            logError("Exception caught in 'decision3' evaluation", e)
            return null
        }
    }

    private inline fun evaluate(employees: List<String?>?, context_: com.gs.dmn.runtime.ExecutionContext): List<String?>? {
        var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
        var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
        var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
        var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
        return employees?.filter({ item -> stringEqual(item, "Bob") }) as List<String?>?
    }

    companion object {
        val DRG_ELEMENT_METADATA : com.gs.dmn.runtime.listener.DRGElement = com.gs.dmn.runtime.listener.DRGElement(
            "",
            "decision3",
            "",
            com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
            com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
            com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
            -1
        )
    }
}
