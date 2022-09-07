
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
class RankedProducts(val bankrates : Bankrates = Bankrates()) : com.gs.dmn.runtime.DefaultDMNBaseDecision() {
    override fun apply(input_: MutableMap<String, String>, context_: com.gs.dmn.runtime.ExecutionContext): type.TRankedProducts? {
        try {
            return apply(input_.get("RequestedAmt"), context_.getAnnotations(), context_.getEventListener(), context_.getExternalFunctionExecutor(), context_.getCache())
        } catch (e: Exception) {
            logError("Cannot apply decision 'RankedProducts'", e)
            return null
        }
    }

    fun apply(requestedAmt: String?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): type.TRankedProducts? {
        return try {
            apply(requestedAmt?.let({ number(it) }), annotationSet_, eventListener_, externalExecutor_, cache_)
        } catch (e: Exception) {
            logError("Cannot apply decision 'RankedProducts'", e)
            null
        }
    }

    fun apply(requestedAmt: java.math.BigDecimal?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): type.TRankedProducts? {
        try {
            // Start decision 'RankedProducts'
            val rankedProductsStartTime_ = System.currentTimeMillis()
            val rankedProductsArguments_ = com.gs.dmn.runtime.listener.Arguments()
            rankedProductsArguments_.put("RequestedAmt", requestedAmt)
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, rankedProductsArguments_)

            // Evaluate decision 'RankedProducts'
            val output_: type.TRankedProducts? = evaluate(requestedAmt, annotationSet_, eventListener_, externalExecutor_, cache_)

            // End decision 'RankedProducts'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, rankedProductsArguments_, output_, (System.currentTimeMillis() - rankedProductsStartTime_))

            return output_
        } catch (e: Exception) {
            logError("Exception caught in 'RankedProducts' evaluation", e)
            return null
        }
    }

    private inline fun evaluate(requestedAmt: java.math.BigDecimal?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): type.TRankedProducts? {
        // Apply child decisions
        val bankrates: List<type.TLoanProduct?>? = this@RankedProducts.bankrates.apply(annotationSet_, eventListener_, externalExecutor_, cache_)

        val metricsTable: List<type.TMetric?>? = bankrates?.stream()?.map({ i -> FinancialMetrics.instance()?.apply(i, requestedAmt, annotationSet_, eventListener_, externalExecutor_, cache_) })?.collect(Collectors.toList()) as List<type.TMetric?>?
        val rankByRate: List<type.TMetric?>? = sort(metricsTable, com.gs.dmn.runtime.LambdaExpression<Boolean> { args_ -> val x: type.TMetric? = args_[0] as type.TMetric?; val y: type.TMetric? = args_[1] as type.TMetric?; numericLessThan(x?.let({ it.rate as java.math.BigDecimal? }), y?.let({ it.rate as java.math.BigDecimal? })) }) as List<type.TMetric?>?
        val rankByDownPmt: List<type.TMetric?>? = sort(metricsTable, com.gs.dmn.runtime.LambdaExpression<Boolean> { args_ -> val x: type.TMetric? = args_[0] as type.TMetric?; val y: type.TMetric? = args_[1] as type.TMetric?; numericLessThan(x?.let({ it.downPmtAmt as java.math.BigDecimal? }), y?.let({ it.downPmtAmt as java.math.BigDecimal? })) }) as List<type.TMetric?>?
        val rankByMonthlyPmt: List<type.TMetric?>? = sort(metricsTable, com.gs.dmn.runtime.LambdaExpression<Boolean> { args_ -> val x: type.TMetric? = args_[0] as type.TMetric?; val y: type.TMetric? = args_[1] as type.TMetric?; numericLessThan(x?.let({ it.paymentAmt as java.math.BigDecimal? }), y?.let({ it.paymentAmt as java.math.BigDecimal? })) }) as List<type.TMetric?>?
        val rankByEquityPct: List<type.TMetric?>? = sort(metricsTable, com.gs.dmn.runtime.LambdaExpression<Boolean> { args_ -> val x: type.TMetric? = args_[0] as type.TMetric?; val y: type.TMetric? = args_[1] as type.TMetric?; numericGreaterThan(x?.let({ it.equity36moPct as java.math.BigDecimal? }), y?.let({ it.equity36moPct as java.math.BigDecimal? })) }) as List<type.TMetric?>?
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
