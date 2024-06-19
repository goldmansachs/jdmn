
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
class PriceTable1() : com.gs.dmn.runtime.JavaTimeDMNBaseDecision() {
    override fun applyMap(input_: MutableMap<String, String>, context_: com.gs.dmn.runtime.ExecutionContext): List<type.TItemPrice?>? {
        try {
            return apply(context_)
        } catch (e: Exception) {
            logError("Cannot apply decision 'PriceTable1'", e)
            return null
        }
    }

    fun apply(context_: com.gs.dmn.runtime.ExecutionContext): List<type.TItemPrice?>? {
        try {
            // Start decision 'priceTable1'
            var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
            var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
            var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
            var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
            val priceTable1StartTime_ = System.currentTimeMillis()
            val priceTable1Arguments_ = com.gs.dmn.runtime.listener.Arguments()
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, priceTable1Arguments_)

            // Evaluate decision 'priceTable1'
            val output_: List<type.TItemPrice?>? = evaluate(context_)

            // End decision 'priceTable1'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, priceTable1Arguments_, output_, (System.currentTimeMillis() - priceTable1StartTime_))

            return output_
        } catch (e: Exception) {
            logError("Exception caught in 'priceTable1' evaluation", e)
            return null
        }
    }

    private inline fun evaluate(context_: com.gs.dmn.runtime.ExecutionContext): List<type.TItemPrice?>? {
        var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
        var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
        var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
        var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
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
