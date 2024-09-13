
import java.util.*
import java.util.stream.Collectors

@javax.annotation.Generated(value = ["decision.ftl", "RankedProducts"])
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "RankedProducts",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.CONTEXT,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
class RankedProducts(val bankrates : Bankrates = Bankrates()) : com.gs.dmn.runtime.JavaTimeDMNBaseDecision() {
    override fun applyMap(input_: MutableMap<String, String>, context_: com.gs.dmn.runtime.ExecutionContext): type.TRankedProducts? {
        try {
            return apply(input_.get("RequestedAmt")?.let({ number(it) }), context_)
        } catch (e: Exception) {
            logError("Cannot apply decision 'RankedProducts'", e)
            return null
        }
    }

    fun apply(requestedAmt: kotlin.Number?, context_: com.gs.dmn.runtime.ExecutionContext): type.TRankedProducts? {
        try {
            // Start decision 'RankedProducts'
            var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
            var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
            var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
            var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
            val rankedProductsStartTime_ = System.currentTimeMillis()
            val rankedProductsArguments_ = com.gs.dmn.runtime.listener.Arguments()
            rankedProductsArguments_.put("RequestedAmt", requestedAmt)
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, rankedProductsArguments_)

            // Evaluate decision 'RankedProducts'
            val output_: type.TRankedProducts? = evaluate(requestedAmt, context_)

            // End decision 'RankedProducts'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, rankedProductsArguments_, output_, (System.currentTimeMillis() - rankedProductsStartTime_))

            return output_
        } catch (e: Exception) {
            logError("Exception caught in 'RankedProducts' evaluation", e)
            return null
        }
    }

    private inline fun evaluate(requestedAmt: kotlin.Number?, context_: com.gs.dmn.runtime.ExecutionContext): type.TRankedProducts? {
        var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
        var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
        var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
        var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
        // Apply child decisions
        val bankrates: List<type.TLoanProduct?>? = this@RankedProducts.bankrates.apply(context_)

        val metricsTable: List<type.TMetric?>? = bankrates?.stream()?.map({ i -> FinancialMetrics.instance()?.apply(i, requestedAmt, context_) })?.collect(Collectors.toList()) as List<type.TMetric?>?
        val rankByRate: List<type.TMetric?>? = sort(metricsTable, com.gs.dmn.runtime.LambdaExpression<Boolean> { args_ -> val x: type.TMetric? = args_[0] as type.TMetric?; val y: type.TMetric? = args_[1] as type.TMetric?; numericLessThan(x?.let({ it.rate as kotlin.Number? }), y?.let({ it.rate as kotlin.Number? })) }) as List<type.TMetric?>?
        val rankByDownPmt: List<type.TMetric?>? = sort(metricsTable, com.gs.dmn.runtime.LambdaExpression<Boolean> { args_ -> val x: type.TMetric? = args_[0] as type.TMetric?; val y: type.TMetric? = args_[1] as type.TMetric?; numericLessThan(x?.let({ it.downPmtAmt as kotlin.Number? }), y?.let({ it.downPmtAmt as kotlin.Number? })) }) as List<type.TMetric?>?
        val rankByMonthlyPmt: List<type.TMetric?>? = sort(metricsTable, com.gs.dmn.runtime.LambdaExpression<Boolean> { args_ -> val x: type.TMetric? = args_[0] as type.TMetric?; val y: type.TMetric? = args_[1] as type.TMetric?; numericLessThan(x?.let({ it.paymentAmt as kotlin.Number? }), y?.let({ it.paymentAmt as kotlin.Number? })) }) as List<type.TMetric?>?
        val rankByEquityPct: List<type.TMetric?>? = sort(metricsTable, com.gs.dmn.runtime.LambdaExpression<Boolean> { args_ -> val x: type.TMetric? = args_[0] as type.TMetric?; val y: type.TMetric? = args_[1] as type.TMetric?; numericGreaterThan(x?.let({ it.equity36moPct as kotlin.Number? }), y?.let({ it.equity36moPct as kotlin.Number? })) }) as List<type.TMetric?>?
        val rankedProducts: type.TRankedProductsImpl? = type.TRankedProductsImpl() as type.TRankedProductsImpl?
        rankedProducts?.metricsTable = metricsTable
        rankedProducts?.rankByRate = rankByRate
        rankedProducts?.rankByDownPmt = rankByDownPmt
        rankedProducts?.rankByMonthlyPmt = rankByMonthlyPmt
        rankedProducts?.rankByEquityPct = rankByEquityPct
        return rankedProducts
    }

    companion object {
        val DRG_ELEMENT_METADATA : com.gs.dmn.runtime.listener.DRGElement = com.gs.dmn.runtime.listener.DRGElement(
            "",
            "RankedProducts",
            "",
            com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
            com.gs.dmn.runtime.annotation.ExpressionKind.CONTEXT,
            com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
            -1
        )
    }
}
