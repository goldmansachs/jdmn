
import java.util.*
import java.util.stream.Collectors

@javax.annotation.Generated(value = ["decision.ftl", "sort1"])
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "sort1",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
class Sort1() : com.gs.dmn.runtime.JavaTimeDMNBaseDecision() {
    override fun applyMap(input_: MutableMap<String, String>, context_: com.gs.dmn.runtime.ExecutionContext): List<java.lang.Number?>? {
        try {
            return apply(input_.get("listA")?.let({ com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(it, object : com.fasterxml.jackson.core.type.TypeReference<List<java.lang.Number?>?>() {}) }), context_)
        } catch (e: Exception) {
            logError("Cannot apply decision 'Sort1'", e)
            return null
        }
    }

    fun apply(listA: List<java.lang.Number?>?, context_: com.gs.dmn.runtime.ExecutionContext): List<java.lang.Number?>? {
        try {
            // Start decision 'sort1'
            var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
            var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
            var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
            var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
            val sort1StartTime_ = System.currentTimeMillis()
            val sort1Arguments_ = com.gs.dmn.runtime.listener.Arguments()
            sort1Arguments_.put("listA", listA)
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, sort1Arguments_)

            // Evaluate decision 'sort1'
            val output_: List<java.lang.Number?>? = evaluate(listA, context_)

            // End decision 'sort1'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, sort1Arguments_, output_, (System.currentTimeMillis() - sort1StartTime_))

            return output_
        } catch (e: Exception) {
            logError("Exception caught in 'sort1' evaluation", e)
            return null
        }
    }

    private inline fun evaluate(listA: List<java.lang.Number?>?, context_: com.gs.dmn.runtime.ExecutionContext): List<java.lang.Number?>? {
        var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
        var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
        var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
        var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
        return sort(listA, com.gs.dmn.runtime.LambdaExpression<Boolean> { args_ -> val x: java.lang.Number? = args_[0] as java.lang.Number?; val y: java.lang.Number? = args_[1] as java.lang.Number?; numericGreaterThan(x, y) }) as List<java.lang.Number?>?
    }

    companion object {
        val DRG_ELEMENT_METADATA : com.gs.dmn.runtime.listener.DRGElement = com.gs.dmn.runtime.listener.DRGElement(
            "",
            "sort1",
            "",
            com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
            com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
            com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
            -1
        )
    }
}
