
import java.util.*
import java.util.stream.Collectors

@javax.annotation.Generated(value = ["decision.ftl", "everyGtTen1"])
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "everyGtTen1",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
class EveryGtTen1(val priceTable1 : PriceTable1 = PriceTable1()) : com.gs.dmn.runtime.JavaTimeDMNBaseDecision() {
    override fun applyMap(input_: MutableMap<String, String>, context_: com.gs.dmn.runtime.ExecutionContext): Boolean? {
        try {
            return apply(context_)
        } catch (e: Exception) {
            logError("Cannot apply decision 'EveryGtTen1'", e)
            return null
        }
    }

    fun apply(context_: com.gs.dmn.runtime.ExecutionContext): Boolean? {
        try {
            // Start decision 'everyGtTen1'
            var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
            var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
            var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
            var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
            val everyGtTen1StartTime_ = System.currentTimeMillis()
            val everyGtTen1Arguments_ = com.gs.dmn.runtime.listener.Arguments()
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, everyGtTen1Arguments_)

            // Evaluate decision 'everyGtTen1'
            val output_: Boolean? = evaluate(context_)

            // End decision 'everyGtTen1'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, everyGtTen1Arguments_, output_, (System.currentTimeMillis() - everyGtTen1StartTime_))

            return output_
        } catch (e: Exception) {
            logError("Exception caught in 'everyGtTen1' evaluation", e)
            return null
        }
    }

    private inline fun evaluate(context_: com.gs.dmn.runtime.ExecutionContext): Boolean? {
        var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
        var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
        var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
        var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
        // Apply child decisions
        val priceTable1: List<type.TItemPrice?>? = this@EveryGtTen1.priceTable1.apply(context_)

        return booleanAnd(priceTable1?.stream()?.map({ i -> numericGreaterThan(i?.let({ it.price as java.math.BigDecimal? }), number("10")) })?.collect(Collectors.toList())?.toList()) as Boolean?
    }

    companion object {
        val DRG_ELEMENT_METADATA : com.gs.dmn.runtime.listener.DRGElement = com.gs.dmn.runtime.listener.DRGElement(
            "",
            "everyGtTen1",
            "",
            com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
            com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
            com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
            -1
        )
    }
}
