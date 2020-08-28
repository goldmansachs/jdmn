
import java.util.*
import java.util.stream.Collectors

@javax.annotation.Generated(value = ["decision.ftl", "priceTable1"])
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "priceTable1",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.RELATION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
class PriceTable1() : com.gs.dmn.runtime.DefaultDMNBaseDecision() {
    fun apply(annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet): List<type.TItemPrice?>? {
        return apply(annotationSet_, com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), com.gs.dmn.runtime.cache.DefaultCache())
    }

    fun apply(annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): List<type.TItemPrice?>? {
        try {
            // Start decision 'priceTable1'
            val priceTable1StartTime_ = System.currentTimeMillis()
            val priceTable1Arguments_ = com.gs.dmn.runtime.listener.Arguments()
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, priceTable1Arguments_)

            // Evaluate decision 'priceTable1'
            val output_: List<type.TItemPrice?>? = evaluate(annotationSet_, eventListener_, externalExecutor_, cache_)

            // End decision 'priceTable1'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, priceTable1Arguments_, output_, (System.currentTimeMillis() - priceTable1StartTime_))

            return output_
        } catch (e: Exception) {
            logError("Exception caught in 'priceTable1' evaluation", e)
            return null
        }
    }

    private inline fun evaluate(annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): List<type.TItemPrice?>? {
        return asList(type.TItemPriceImpl("widget", number("25")),
				type.TItemPriceImpl("sprocket", number("15")),
				type.TItemPriceImpl("trinket", number("1.5"))) as List<type.TItemPrice?>?
    }

    companion object {
        val DRG_ELEMENT_METADATA : com.gs.dmn.runtime.listener.DRGElement = com.gs.dmn.runtime.listener.DRGElement(
            "",
            "priceTable1",
            "",
            com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
            com.gs.dmn.runtime.annotation.ExpressionKind.RELATION,
            com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
            -1
        )
    }
}
