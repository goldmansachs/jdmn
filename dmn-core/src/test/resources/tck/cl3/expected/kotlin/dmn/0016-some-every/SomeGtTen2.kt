
import java.util.*
import java.util.stream.Collectors

@javax.annotation.Generated(value = ["decision.ftl", "someGtTen2"])
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "someGtTen2",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
class SomeGtTen2() : com.gs.dmn.runtime.DefaultDMNBaseDecision() {
    fun apply(priceTable2: String?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet): Boolean? {
        return try {
            apply(priceTable2?.let({ com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(it, object : com.fasterxml.jackson.core.type.TypeReference<List<type.TItemPrice?>?>() {}) }), annotationSet_, com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), com.gs.dmn.runtime.cache.DefaultCache())
        } catch (e: Exception) {
            logError("Cannot apply decision 'SomeGtTen2'", e)
            null
        }
    }

    fun apply(priceTable2: String?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): Boolean? {
        return try {
            apply(priceTable2?.let({ com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(it, object : com.fasterxml.jackson.core.type.TypeReference<List<type.TItemPrice?>?>() {}) }), annotationSet_, eventListener_, externalExecutor_, cache_)
        } catch (e: Exception) {
            logError("Cannot apply decision 'SomeGtTen2'", e)
            null
        }
    }

    fun apply(priceTable2: List<type.TItemPrice?>?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet): Boolean? {
        return apply(priceTable2, annotationSet_, com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), com.gs.dmn.runtime.cache.DefaultCache())
    }

    fun apply(priceTable2: List<type.TItemPrice?>?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): Boolean? {
        try {
            // Start decision 'someGtTen2'
            val someGtTen2StartTime_ = System.currentTimeMillis()
            val someGtTen2Arguments_ = com.gs.dmn.runtime.listener.Arguments()
            someGtTen2Arguments_.put("priceTable2", priceTable2)
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, someGtTen2Arguments_)

            // Evaluate decision 'someGtTen2'
            val output_: Boolean? = evaluate(priceTable2, annotationSet_, eventListener_, externalExecutor_, cache_)

            // End decision 'someGtTen2'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, someGtTen2Arguments_, output_, (System.currentTimeMillis() - someGtTen2StartTime_))

            return output_
        } catch (e: Exception) {
            logError("Exception caught in 'someGtTen2' evaluation", e)
            return null
        }
    }

    private inline fun evaluate(priceTable2: List<type.TItemPrice?>?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): Boolean? {
        return booleanOr(priceTable2?.stream()?.map({ i -> numericGreaterThan(i?.let({ it.price as java.math.BigDecimal? }), number("10")) })?.collect(Collectors.toList())?.toList()) as Boolean?
    }

    companion object {
        val DRG_ELEMENT_METADATA : com.gs.dmn.runtime.listener.DRGElement = com.gs.dmn.runtime.listener.DRGElement(
            "",
            "someGtTen2",
            "",
            com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
            com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
            com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
            -1
        )
    }
}
