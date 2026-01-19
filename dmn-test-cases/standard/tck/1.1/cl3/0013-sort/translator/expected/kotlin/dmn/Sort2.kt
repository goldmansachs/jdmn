
import java.util.*
import java.util.stream.Collectors

@javax.annotation.Generated(value = ["decision.ftl", "sort2"])
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "sort2",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
class Sort2() : com.gs.dmn.runtime.JavaTimeDMNBaseDecision<List<type.TRow?>?>() {
    override fun applyMap(input_: MutableMap<String, String>, context_: com.gs.dmn.runtime.ExecutionContext): List<type.TRow?>? {
        try {
            return apply(input_.get("tableB")?.let({ com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(it, object : com.fasterxml.jackson.core.type.TypeReference<List<type.TRow?>?>() {}) }), context_)
        } catch (e: Exception) {
            logError("Cannot apply decision 'Sort2'", e)
            return null
        }
    }

    fun apply(tableB: List<type.TRow?>?, context_: com.gs.dmn.runtime.ExecutionContext): List<type.TRow?>? {
        try {
            // Start decision 'sort2'
            var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
            var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
            var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
            var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
            val sort2StartTime_ = System.currentTimeMillis()
            val sort2Arguments_ = com.gs.dmn.runtime.listener.Arguments()
            sort2Arguments_.put("tableB", tableB)
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, sort2Arguments_)

            // Evaluate decision 'sort2'
            val output_: List<type.TRow?>? = evaluate(tableB, context_)

            // End decision 'sort2'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, sort2Arguments_, output_, (System.currentTimeMillis() - sort2StartTime_))

            return output_
        } catch (e: Exception) {
            logError("Exception caught in 'sort2' evaluation", e)
            return null
        }
    }

    private inline fun evaluate(tableB: List<type.TRow?>?, context_: com.gs.dmn.runtime.ExecutionContext): List<type.TRow?>? {
        var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
        var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
        var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
        var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
        return sort(tableB, com.gs.dmn.runtime.LambdaExpression<Boolean> { args_ -> val x: type.TRow? = args_[0] as type.TRow?; val y: type.TRow? = args_[1] as type.TRow?; numericLessThan(x?.let({ it.col2 as kotlin.Number? }), y?.let({ it.col2 as kotlin.Number? })) }) as List<type.TRow?>?
    }

    companion object {
        val DRG_ELEMENT_METADATA : com.gs.dmn.runtime.listener.DRGElement = com.gs.dmn.runtime.listener.DRGElement(
            "",
            "sort2",
            "",
            com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
            com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
            com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
            -1
        )
    }
}
