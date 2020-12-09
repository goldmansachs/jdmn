
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
class Sort1() : com.gs.dmn.runtime.DefaultDMNBaseDecision() {
    fun apply(listA: String?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet): List<java.math.BigDecimal?>? {
        return try {
            apply(listA?.let({ com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(it, object : com.fasterxml.jackson.core.type.TypeReference<List<java.math.BigDecimal?>?>() {}) }), annotationSet_, com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), com.gs.dmn.runtime.cache.DefaultCache())
        } catch (e: Exception) {
            logError("Cannot apply decision 'Sort1'", e)
            null
        }
    }

    fun apply(listA: String?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): List<java.math.BigDecimal?>? {
        return try {
            apply(listA?.let({ com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(it, object : com.fasterxml.jackson.core.type.TypeReference<List<java.math.BigDecimal?>?>() {}) }), annotationSet_, eventListener_, externalExecutor_, cache_)
        } catch (e: Exception) {
            logError("Cannot apply decision 'Sort1'", e)
            null
        }
    }

    fun apply(listA: List<java.math.BigDecimal?>?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet): List<java.math.BigDecimal?>? {
        return apply(listA, annotationSet_, com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), com.gs.dmn.runtime.cache.DefaultCache())
    }

    fun apply(listA: List<java.math.BigDecimal?>?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): List<java.math.BigDecimal?>? {
        try {
            // Start decision 'sort1'
            val sort1StartTime_ = System.currentTimeMillis()
            val sort1Arguments_ = com.gs.dmn.runtime.listener.Arguments()
            sort1Arguments_.put("listA", listA)
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, sort1Arguments_)

            // Evaluate decision 'sort1'
            val output_: List<java.math.BigDecimal?>? = evaluate(listA, annotationSet_, eventListener_, externalExecutor_, cache_)

            // End decision 'sort1'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, sort1Arguments_, output_, (System.currentTimeMillis() - sort1StartTime_))

            return output_
        } catch (e: Exception) {
            logError("Exception caught in 'sort1' evaluation", e)
            return null
        }
    }

    private inline fun evaluate(listA: List<java.math.BigDecimal?>?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): List<java.math.BigDecimal?>? {
        return sort(listA, com.gs.dmn.runtime.LambdaExpression<Boolean> { args -> val x: java.math.BigDecimal? = args[0] as java.math.BigDecimal?; val y: java.math.BigDecimal? = args[1] as java.math.BigDecimal?;numericGreaterThan(x, y) }) as List<java.math.BigDecimal?>?
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
