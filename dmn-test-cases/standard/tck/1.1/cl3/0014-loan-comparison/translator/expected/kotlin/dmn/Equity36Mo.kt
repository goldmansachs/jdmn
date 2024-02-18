
import java.util.*
import java.util.stream.Collectors

@jakarta.annotation.Generated(value = ["bkm.ftl", "equity36Mo"])
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "equity36Mo",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
class Equity36Mo : com.gs.dmn.runtime.DefaultDMNBaseDecision {
    private constructor() {}

    override fun applyMap(input_: MutableMap<String, String>, context_: com.gs.dmn.runtime.ExecutionContext): java.math.BigDecimal? {
        try {
            return apply(input_.get("p")?.let({ number(it) }), input_.get("r")?.let({ number(it) }), input_.get("n")?.let({ number(it) }), input_.get("pmt")?.let({ number(it) }), context_)
        } catch (e: Exception) {
            logError("Cannot apply decision 'Equity36Mo'", e)
            return null
        }
    }

    fun apply(p: java.math.BigDecimal?, r: java.math.BigDecimal?, n: java.math.BigDecimal?, pmt: java.math.BigDecimal?, context_: com.gs.dmn.runtime.ExecutionContext): java.math.BigDecimal? {
        try {
            // Start BKM 'equity36Mo'
            var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
            var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
            var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
            var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
            val equity36MoStartTime_ = System.currentTimeMillis()
            val equity36MoArguments_ = com.gs.dmn.runtime.listener.Arguments()
            equity36MoArguments_.put("p", p)
            equity36MoArguments_.put("r", r)
            equity36MoArguments_.put("n", n)
            equity36MoArguments_.put("pmt", pmt)
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, equity36MoArguments_)

            // Evaluate BKM 'equity36Mo'
            val output_: java.math.BigDecimal? = evaluate(p, r, n, pmt, context_)

            // End BKM 'equity36Mo'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, equity36MoArguments_, output_, (System.currentTimeMillis() - equity36MoStartTime_))

            return output_
        } catch (e: Exception) {
            logError("Exception caught in 'equity36Mo' evaluation", e)
            return null
        }
    }

    private inline fun evaluate(p: java.math.BigDecimal?, r: java.math.BigDecimal?, n: java.math.BigDecimal?, pmt: java.math.BigDecimal?, context_: com.gs.dmn.runtime.ExecutionContext): java.math.BigDecimal? {
        var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
        var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
        var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
        var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
        return numericSubtract(numericMultiply(p, numericExponentiation(numericAdd(number("1"), numericDivide(r, number("12"))), n)), numericDivide(numericMultiply(pmt, numericAdd(numericUnaryMinus(number("1")), numericExponentiation(numericAdd(number("1"), numericDivide(r, number("12"))), n))), r)) as java.math.BigDecimal?
    }

    companion object {
        val DRG_ELEMENT_METADATA : com.gs.dmn.runtime.listener.DRGElement = com.gs.dmn.runtime.listener.DRGElement(
            "",
            "equity36Mo",
            "",
            com.gs.dmn.runtime.annotation.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
            com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
            com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
            -1
        )

        private val INSTANCE = Equity36Mo()

        @JvmStatic
        fun instance(): Equity36Mo {
            return INSTANCE
        }
    }
}
