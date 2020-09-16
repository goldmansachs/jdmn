
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
class Sort2() : com.gs.dmn.runtime.DefaultDMNBaseDecision() {
    fun apply(tableB: String?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet): List<type.TRow?>? {
        return try {
            apply(tableB?.let({ com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(it, object : com.fasterxml.jackson.core.type.TypeReference<List<type.TRow?>?>() {}) }), annotationSet_, com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), com.gs.dmn.runtime.cache.DefaultCache())
        } catch (e: Exception) {
            logError("Cannot apply decision 'Sort2'", e)
            null
        }
    }

    fun apply(tableB: String?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): List<type.TRow?>? {
        return try {
            apply(tableB?.let({ com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(it, object : com.fasterxml.jackson.core.type.TypeReference<List<type.TRow?>?>() {}) }), annotationSet_, eventListener_, externalExecutor_, cache_)
        } catch (e: Exception) {
            logError("Cannot apply decision 'Sort2'", e)
            null
        }
    }

    fun apply(tableB: List<type.TRow?>?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet): List<type.TRow?>? {
        return apply(tableB, annotationSet_, com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), com.gs.dmn.runtime.cache.DefaultCache())
    }

    fun apply(tableB: List<type.TRow?>?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): List<type.TRow?>? {
        try {
            // Start decision 'sort2'
            val sort2StartTime_ = System.currentTimeMillis()
            val sort2Arguments_ = com.gs.dmn.runtime.listener.Arguments()
            sort2Arguments_.put("tableB", tableB)
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, sort2Arguments_)

            // Evaluate decision 'sort2'
            val output_: List<type.TRow?>? = evaluate(tableB, annotationSet_, eventListener_, externalExecutor_, cache_)

            // End decision 'sort2'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, sort2Arguments_, output_, (System.currentTimeMillis() - sort2StartTime_))

            return output_
        } catch (e: Exception) {
            logError("Exception caught in 'sort2' evaluation", e)
            return null
        }
    }

    private inline fun evaluate(tableB: List<type.TRow?>?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): List<type.TRow?>? {
        return sort(tableB, com.gs.dmn.runtime.LambdaExpression<Boolean> { args -> val x: type.TRow? = args[0] as type.TRow?; val y: type.TRow? = args[1] as type.TRow?;numericLessThan(x?.let({ it.col2 as java.math.BigDecimal? }), y?.let({ it.col2 as java.math.BigDecimal? })) })?.map({ x -> type.TRow.toTRow(x) }) as List<type.TRow?>?
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
