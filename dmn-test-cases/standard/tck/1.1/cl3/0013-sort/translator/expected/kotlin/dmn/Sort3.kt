
import java.util.*
import java.util.stream.Collectors

@javax.annotation.Generated(value = ["decision.ftl", "sort3"])
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "sort3",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
class Sort3() : com.gs.dmn.runtime.DefaultDMNBaseDecision() {
    override fun apply(input_: MutableMap<String, String>, context_: com.gs.dmn.runtime.ExecutionContext): List<String?>? {
        try {
            return apply(input_.get("stringList"), context_.getAnnotations(), context_.getEventListener(), context_.getExternalFunctionExecutor(), context_.getCache())
        } catch (e: Exception) {
            logError("Cannot apply decision 'Sort3'", e)
            return null
        }
    }

    fun apply(stringList: String?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): List<String?>? {
        return try {
            apply(stringList?.let({ com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(it, object : com.fasterxml.jackson.core.type.TypeReference<List<String?>?>() {}) }), annotationSet_, eventListener_, externalExecutor_, cache_)
        } catch (e: Exception) {
            logError("Cannot apply decision 'Sort3'", e)
            null
        }
    }

    fun apply(stringList: List<String?>?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): List<String?>? {
        try {
            // Start decision 'sort3'
            val sort3StartTime_ = System.currentTimeMillis()
            val sort3Arguments_ = com.gs.dmn.runtime.listener.Arguments()
            sort3Arguments_.put("stringList", stringList)
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, sort3Arguments_)

            // Evaluate decision 'sort3'
            val output_: List<String?>? = evaluate(stringList, annotationSet_, eventListener_, externalExecutor_, cache_)

            // End decision 'sort3'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, sort3Arguments_, output_, (System.currentTimeMillis() - sort3StartTime_))

            return output_
        } catch (e: Exception) {
            logError("Exception caught in 'sort3' evaluation", e)
            return null
        }
    }

    private inline fun evaluate(stringList: List<String?>?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): List<String?>? {
        return sort(stringList, com.gs.dmn.runtime.LambdaExpression<Boolean> { args_ -> val x: String? = args_[0] as String?; val y: String? = args_[1] as String?; stringLessThan(x, y) }) as List<String?>?
    }

    companion object {
        val DRG_ELEMENT_METADATA : com.gs.dmn.runtime.listener.DRGElement = com.gs.dmn.runtime.listener.DRGElement(
            "",
            "sort3",
            "",
            com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
            com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
            com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
            -1
        )
    }
}
