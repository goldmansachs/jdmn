
import java.util.*
import java.util.stream.Collectors

@javax.annotation.Generated(value = ["decision.ftl", "sort1"])
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "http://www.trisotech.com/definitions/_ac1acfdd-6baa-4f30-9cac-5d23957b4217",
    name = "sort1",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
class Sort1() : com.gs.dmn.runtime.JavaTimeDMNBaseDecision<List<kotlin.Number?>?>() {
    override fun applyMap(input_: MutableMap<String, String>, context_: com.gs.dmn.runtime.ExecutionContext): List<kotlin.Number?>? {
        try {
            return apply(input_.get("listA")?.let({ com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(it, object : com.fasterxml.jackson.core.type.TypeReference<List<kotlin.Number?>?>() {}) }), context_)
        } catch (e: Exception) {
            logError("Cannot apply decision 'Sort1'", e)
            return null
        }
    }

    fun apply(listA: List<kotlin.Number?>?, context_: com.gs.dmn.runtime.ExecutionContext): List<kotlin.Number?>? {
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
            val output_: List<kotlin.Number?>? = evaluate(listA, context_)

            // End decision 'sort1'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, sort1Arguments_, output_, (System.currentTimeMillis() - sort1StartTime_))

            return output_
        } catch (e: Exception) {
            logError("Exception caught in 'sort1' evaluation", e)
            return null
        }
    }

    private inline fun evaluate(listA: List<kotlin.Number?>?, context_: com.gs.dmn.runtime.ExecutionContext): List<kotlin.Number?>? {
        var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
        var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
        var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
        var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
        return sort(listA, com.gs.dmn.runtime.LambdaExpression<Boolean> { args_ -> val x: kotlin.Number? = args_[0] as kotlin.Number?; val y: kotlin.Number? = args_[1] as kotlin.Number?; numericGreaterThan(x, y) }) as List<kotlin.Number?>?
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
