
import java.util.*
import java.util.stream.Collectors

@javax.annotation.Generated(value = ["decision.ftl", "everyGtTen3"])
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "everyGtTen3",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
class EveryGtTen3(val priceTable1 : PriceTable1 = PriceTable1()) : com.gs.dmn.runtime.DefaultDMNBaseDecision() {
    fun apply(annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet): Boolean? {
        return apply(annotationSet_, com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), com.gs.dmn.runtime.cache.DefaultCache())
    }

    fun apply(annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): Boolean? {
        try {
            // Start decision 'everyGtTen3'
            val everyGtTen3StartTime_ = System.currentTimeMillis()
            val everyGtTen3Arguments_ = com.gs.dmn.runtime.listener.Arguments()
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, everyGtTen3Arguments_)

            // Apply child decisions
            val priceTable1: List<type.TItemPrice?>? = this.priceTable1.apply(annotationSet_, eventListener_, externalExecutor_, cache_)

            // Evaluate decision 'everyGtTen3'
            val output_: Boolean? = evaluate(priceTable1, annotationSet_, eventListener_, externalExecutor_, cache_)

            // End decision 'everyGtTen3'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, everyGtTen3Arguments_, output_, (System.currentTimeMillis() - everyGtTen3StartTime_))

            return output_
        } catch (e: Exception) {
            logError("Exception caught in 'everyGtTen3' evaluation", e)
            return null
        }
    }

    private inline fun evaluate(priceTable1: List<type.TItemPrice?>?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): Boolean? {
        return booleanAnd(priceTable1?.stream()?.map({ i -> booleanEqual(GtTen.gtTen(i?.let({ it.price as java.math.BigDecimal? }), annotationSet_, eventListener_, externalExecutor_, cache_), true) })?.collect(Collectors.toList())?.toList()) as Boolean?
    }

    companion object {
        val DRG_ELEMENT_METADATA : com.gs.dmn.runtime.listener.DRGElement = com.gs.dmn.runtime.listener.DRGElement(
            "",
            "everyGtTen3",
            "",
            com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
            com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
            com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
            -1
        )
    }
}
