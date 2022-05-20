
import java.util.*
import java.util.stream.Collectors

@javax.annotation.Generated(value = ["decision.ftl", "someGtTen1"])
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "someGtTen1",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
class SomeGtTen1(val priceTable1 : PriceTable1 = PriceTable1()) : com.gs.dmn.runtime.DefaultDMNBaseDecision() {
    fun apply(annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): Boolean? {
        try {
            // Start decision 'someGtTen1'
            val someGtTen1StartTime_ = System.currentTimeMillis()
            val someGtTen1Arguments_ = com.gs.dmn.runtime.listener.Arguments()
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, someGtTen1Arguments_)

            // Evaluate decision 'someGtTen1'
            val output_: Boolean? = evaluate(annotationSet_, eventListener_, externalExecutor_, cache_)

            // End decision 'someGtTen1'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, someGtTen1Arguments_, output_, (System.currentTimeMillis() - someGtTen1StartTime_))

            return output_
        } catch (e: Exception) {
            logError("Exception caught in 'someGtTen1' evaluation", e)
            return null
        }
    }

    private inline fun evaluate(annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): Boolean? {
        // Apply child decisions
        val priceTable1: List<type.TItemPrice?>? = this@SomeGtTen1.priceTable1.apply(annotationSet_, eventListener_, externalExecutor_, cache_)

        return booleanOr(priceTable1?.stream()?.map({ i -> numericGreaterThan(i?.let({ it.price as java.math.BigDecimal? }), number("10")) })?.collect(Collectors.toList())?.toList()) as Boolean?
    }

    companion object {
        val DRG_ELEMENT_METADATA : com.gs.dmn.runtime.listener.DRGElement = com.gs.dmn.runtime.listener.DRGElement(
            "",
            "someGtTen1",
            "",
            com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
            com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
            com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
            -1
        )
    }
}
