
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "RankedProducts"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "RankedProducts",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.CONTEXT,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class RankedProducts extends com.gs.dmn.runtime.JavaTimeDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "RankedProducts",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.CONTEXT,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    private final Bankrates bankrates;

    public RankedProducts() {
        this(new Bankrates());
    }

    public RankedProducts(Bankrates bankrates) {
        this.bankrates = bankrates;
    }

    @java.lang.Override()
    public type.TRankedProducts applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply((input_.get("RequestedAmt") != null ? number(input_.get("RequestedAmt")) : null), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'RankedProducts'", e);
            return null;
        }
    }

    public type.TRankedProducts apply(java.lang.Number requestedAmt, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'RankedProducts'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long rankedProductsStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments rankedProductsArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            rankedProductsArguments_.put("RequestedAmt", requestedAmt);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, rankedProductsArguments_);

            // Evaluate decision 'RankedProducts'
            type.TRankedProducts output_ = lambda.apply(requestedAmt, context_);

            // End decision 'RankedProducts'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, rankedProductsArguments_, output_, (System.currentTimeMillis() - rankedProductsStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'RankedProducts' evaluation", e);
            return null;
        }
    }

    public com.gs.dmn.runtime.LambdaExpression<type.TRankedProducts> lambda =
        new com.gs.dmn.runtime.LambdaExpression<type.TRankedProducts>() {
            public type.TRankedProducts apply(Object... args_) {
                java.lang.Number requestedAmt = 0 < args_.length ? (java.lang.Number) args_[0] : null;
                com.gs.dmn.runtime.ExecutionContext context_ = 1 < args_.length ? (com.gs.dmn.runtime.ExecutionContext) args_[1] : null;
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
                com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;

                // Apply child decisions
                List<type.TLoanProduct> bankrates = RankedProducts.this.bankrates.apply(context_);

                List<type.TMetric> metricsTable = bankrates.stream().map(i -> FinancialMetrics.instance().apply(i, requestedAmt, context_)).collect(Collectors.toList());
                List<type.TMetric> rankByRate = sort(metricsTable, new com.gs.dmn.runtime.LambdaExpression<Boolean>() {public Boolean apply(Object... args_) {type.TMetric x = (type.TMetric)args_[0]; type.TMetric y = (type.TMetric)args_[1];return numericLessThan(((java.lang.Number)(x != null ? x.getRate() : null)), ((java.lang.Number)(y != null ? y.getRate() : null)));}});
                List<type.TMetric> rankByDownPmt = sort(metricsTable, new com.gs.dmn.runtime.LambdaExpression<Boolean>() {public Boolean apply(Object... args_) {type.TMetric x = (type.TMetric)args_[0]; type.TMetric y = (type.TMetric)args_[1];return numericLessThan(((java.lang.Number)(x != null ? x.getDownPmtAmt() : null)), ((java.lang.Number)(y != null ? y.getDownPmtAmt() : null)));}});
                List<type.TMetric> rankByMonthlyPmt = sort(metricsTable, new com.gs.dmn.runtime.LambdaExpression<Boolean>() {public Boolean apply(Object... args_) {type.TMetric x = (type.TMetric)args_[0]; type.TMetric y = (type.TMetric)args_[1];return numericLessThan(((java.lang.Number)(x != null ? x.getPaymentAmt() : null)), ((java.lang.Number)(y != null ? y.getPaymentAmt() : null)));}});
                List<type.TMetric> rankByEquityPct = sort(metricsTable, new com.gs.dmn.runtime.LambdaExpression<Boolean>() {public Boolean apply(Object... args_) {type.TMetric x = (type.TMetric)args_[0]; type.TMetric y = (type.TMetric)args_[1];return numericGreaterThan(((java.lang.Number)(x != null ? x.getEquity36moPct() : null)), ((java.lang.Number)(y != null ? y.getEquity36moPct() : null)));}});
                type.TRankedProductsImpl rankedProducts = new type.TRankedProductsImpl();
                rankedProducts.setMetricsTable(metricsTable);
                rankedProducts.setRankByRate(rankByRate);
                rankedProducts.setRankByDownPmt(rankByDownPmt);
                rankedProducts.setRankByMonthlyPmt(rankByMonthlyPmt);
                rankedProducts.setRankByEquityPct(rankByEquityPct);
                return rankedProducts;
            }
        };
}
