
import java.util.*
import java.util.stream.Collectors

@jakarta.annotation.Generated(value = ["bkm.ftl", "PMT"])
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "PMT",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
class PMT : com.gs.dmn.runtime.DefaultDMNBaseDecision {
    private constructor() {}

    override fun applyMap(input_: MutableMap<String, String>, context_: com.gs.dmn.runtime.ExecutionContext): java.math.BigDecimal? {
        try {
            return apply(input_.get("p")?.let({ number(it) }), input_.get("r")?.let({ number(it) }), input_.get("n")?.let({ number(it) }), context_)
        } catch (e: Exception) {
            logError("Cannot apply decision 'PMT'", e)
            return null
        }
    }

    fun apply(p: java.math.BigDecimal?, r: java.math.BigDecimal?, n: java.math.BigDecimal?, context_: com.gs.dmn.runtime.ExecutionContext): java.math.BigDecimal? {
        try {
            // Start BKM 'PMT'
            var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
            var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
            var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
            var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
            val pMTStartTime_ = System.currentTimeMillis()
            val pMTArguments_ = com.gs.dmn.runtime.listener.Arguments()
            pMTArguments_.put("p", p)
            pMTArguments_.put("r", r)
            pMTArguments_.put("n", n)
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, pMTArguments_)

            // Evaluate BKM 'PMT'
            val output_: java.math.BigDecimal? = evaluate(p, r, n, context_)

            // End BKM 'PMT'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, pMTArguments_, output_, (System.currentTimeMillis() - pMTStartTime_))

            return output_
        } catch (e: Exception) {
            logError("Exception caught in 'PMT' evaluation", e)
            return null
        }
    }

    private inline fun evaluate(p: java.math.BigDecimal?, r: java.math.BigDecimal?, n: java.math.BigDecimal?, context_: com.gs.dmn.runtime.ExecutionContext): java.math.BigDecimal? {
        var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
        var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
        var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
        var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
        return numericDivide(numericDivide(numericMultiply(p, r), number("12")), numericSubtract(number("1"), numericExponentiation(numericAdd(number("1"), numericDivide(r, number("12"))), numericUnaryMinus(n)))) as java.math.BigDecimal?
    }

    companion object {
        val DRG_ELEMENT_METADATA : com.gs.dmn.runtime.listener.DRGElement = com.gs.dmn.runtime.listener.DRGElement(
            "",
            "PMT",
            "",
            com.gs.dmn.runtime.annotation.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
            com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
            com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
            -1
        )

        private val INSTANCE = PMT()

        @JvmStatic
        fun instance(): PMT {
            return INSTANCE
        }
    }
}
