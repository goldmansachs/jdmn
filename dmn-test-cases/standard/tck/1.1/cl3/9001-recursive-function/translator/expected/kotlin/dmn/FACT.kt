
import java.util.*
import java.util.stream.Collectors

@jakarta.annotation.Generated(value = ["bkm.ftl", "FACT"])
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "FACT",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
class FACT : com.gs.dmn.runtime.DefaultDMNBaseDecision {
    private constructor() {}

    override fun applyMap(input_: MutableMap<String, String>, context_: com.gs.dmn.runtime.ExecutionContext): java.math.BigDecimal? {
        try {
            return apply(input_.get("n")?.let({ number(it) }), context_)
        } catch (e: Exception) {
            logError("Cannot apply decision 'FACT'", e)
            return null
        }
    }

    fun apply(n: java.math.BigDecimal?, context_: com.gs.dmn.runtime.ExecutionContext): java.math.BigDecimal? {
        try {
            // Start BKM 'FACT'
            var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
            var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
            var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
            var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
            val fACTStartTime_ = System.currentTimeMillis()
            val fACTArguments_ = com.gs.dmn.runtime.listener.Arguments()
            fACTArguments_.put("n", n)
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, fACTArguments_)

            // Evaluate BKM 'FACT'
            val output_: java.math.BigDecimal? = evaluate(n, context_)

            // End BKM 'FACT'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, fACTArguments_, output_, (System.currentTimeMillis() - fACTStartTime_))

            return output_
        } catch (e: Exception) {
            logError("Exception caught in 'FACT' evaluation", e)
            return null
        }
    }

    private inline fun evaluate(n: java.math.BigDecimal?, context_: com.gs.dmn.runtime.ExecutionContext): java.math.BigDecimal? {
        var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
        var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
        var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
        var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
        return (if (booleanEqual(numericLessThan(n, number("0")), true)) null else (if (booleanEqual(numericEqual(n, number("0")), true)) number("1") else numericMultiply(n, FACT.instance()?.apply(numericSubtract(n, number("1")), context_)))) as java.math.BigDecimal?
    }

    companion object {
        val DRG_ELEMENT_METADATA : com.gs.dmn.runtime.listener.DRGElement = com.gs.dmn.runtime.listener.DRGElement(
            "",
            "FACT",
            "",
            com.gs.dmn.runtime.annotation.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
            com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
            com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
            -1
        )

        private val INSTANCE = FACT()

        @JvmStatic
        fun instance(): FACT {
            return INSTANCE
        }
    }
}
