
import java.util.*
import java.util.stream.Collectors

@javax.annotation.Generated(value = ["bkm.ftl", "FinancialMetrics"])
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "FinancialMetrics",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.CONTEXT,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
class FinancialMetrics : com.gs.dmn.runtime.DefaultDMNBaseDecision {
    private constructor() {}

    override fun applyMap(input_: MutableMap<String, String>, context_: com.gs.dmn.runtime.ExecutionContext): type.TMetric? {
        try {
            return apply(input_.get("product")?.let({ com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(it, object : com.fasterxml.jackson.core.type.TypeReference<type.TLoanProductImpl>() {}) }), input_.get("requestedAmt")?.let({ number(it) }), context_)
        } catch (e: Exception) {
            logError("Cannot apply decision 'FinancialMetrics'", e)
            return null
        }
    }

    fun apply(product: type.TLoanProduct?, requestedAmt: java.math.BigDecimal?, context_: com.gs.dmn.runtime.ExecutionContext): type.TMetric? {
        try {
            // Start BKM 'FinancialMetrics'
            var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
            var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
            var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
            var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
            val financialMetricsStartTime_ = System.currentTimeMillis()
            val financialMetricsArguments_ = com.gs.dmn.runtime.listener.Arguments()
            financialMetricsArguments_.put("product", product)
            financialMetricsArguments_.put("requestedAmt", requestedAmt)
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, financialMetricsArguments_)

            // Evaluate BKM 'FinancialMetrics'
            val output_: type.TMetric? = evaluate(product, requestedAmt, context_)

            // End BKM 'FinancialMetrics'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, financialMetricsArguments_, output_, (System.currentTimeMillis() - financialMetricsStartTime_))

            return output_
        } catch (e: Exception) {
            logError("Exception caught in 'FinancialMetrics' evaluation", e)
            return null
        }
    }

    private inline fun evaluate(product: type.TLoanProduct?, requestedAmt: java.math.BigDecimal?, context_: com.gs.dmn.runtime.ExecutionContext): type.TMetric? {
        var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
        var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
        var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
        var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
        val lenderName: String? = product?.let({ it.lenderName as String? }) as String?
        val rate: java.math.BigDecimal? = product?.let({ it.rate as java.math.BigDecimal? }) as java.math.BigDecimal?
        val points: java.math.BigDecimal? = product?.let({ it.points as java.math.BigDecimal? }) as java.math.BigDecimal?
        val fee: java.math.BigDecimal? = product?.let({ it.fee as java.math.BigDecimal? }) as java.math.BigDecimal?
        val loanAmt: java.math.BigDecimal? = numericAdd(numericMultiply(requestedAmt, numericAdd(number("1"), numericDivide(points, number("100")))), fee) as java.math.BigDecimal?
        val downPmtAmt: java.math.BigDecimal? = numericMultiply(number("0.2"), loanAmt) as java.math.BigDecimal?
        val paymentAmt: java.math.BigDecimal? = MonthlyPayment.instance()?.apply(loanAmt, rate, number("360"), context_) as java.math.BigDecimal?
        val equity36moPct: java.math.BigDecimal? = numericSubtract(number("1"), numericMultiply(numericDivide(Equity36Mo.instance()?.apply(loanAmt, rate, number("36"), paymentAmt, context_), requestedAmt), number("0.8"))) as java.math.BigDecimal?
        val financialMetrics: type.TMetricImpl? = type.TMetricImpl() as type.TMetricImpl?
        financialMetrics?.lenderName = lenderName
        financialMetrics?.rate = rate
        financialMetrics?.points = points
        financialMetrics?.fee = fee
        financialMetrics?.loanAmt = loanAmt
        financialMetrics?.downPmtAmt = downPmtAmt
        financialMetrics?.paymentAmt = paymentAmt
        financialMetrics?.equity36moPct = equity36moPct
        return financialMetrics
    }

    companion object {
        val DRG_ELEMENT_METADATA : com.gs.dmn.runtime.listener.DRGElement = com.gs.dmn.runtime.listener.DRGElement(
            "",
            "FinancialMetrics",
            "",
            com.gs.dmn.runtime.annotation.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
            com.gs.dmn.runtime.annotation.ExpressionKind.CONTEXT,
            com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
            -1
        )

        private val INSTANCE = FinancialMetrics()

        @JvmStatic
        fun instance(): FinancialMetrics {
            return INSTANCE
        }
    }
}
