
import java.util.*;
import java.util.stream.Collectors;

import static FinancialMetrics.FinancialMetrics;

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
public class RankedProducts extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
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

    public type.TRankedProducts apply(String requestedAmt, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply((requestedAmt != null ? number(requestedAmt) : null), annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor());
        } catch (Exception e) {
            logError("Cannot apply decision 'RankedProducts'", e);
            return null;
        }
    }

    public type.TRankedProducts apply(String requestedAmt, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        try {
            return apply((requestedAmt != null ? number(requestedAmt) : null), annotationSet_, eventListener_, externalExecutor_);
        } catch (Exception e) {
            logError("Cannot apply decision 'RankedProducts'", e);
            return null;
        }
    }

    public type.TRankedProducts apply(java.math.BigDecimal requestedAmt, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(requestedAmt, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor());
    }

    public type.TRankedProducts apply(java.math.BigDecimal requestedAmt, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        try {
            // Start decision 'RankedProducts'
            long rankedProductsStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments rankedProductsArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            rankedProductsArguments_.put("requestedAmt", requestedAmt);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, rankedProductsArguments_);

            // Apply child decisions
            List<type.TLoanProduct> bankrates = this.bankrates.apply(annotationSet_, eventListener_, externalExecutor_);

            // Evaluate decision 'RankedProducts'
            type.TRankedProducts output_ = evaluate(bankrates, requestedAmt, annotationSet_, eventListener_, externalExecutor_);

            // End decision 'RankedProducts'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, rankedProductsArguments_, output_, (System.currentTimeMillis() - rankedProductsStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'RankedProducts' evaluation", e);
            return null;
        }
    }

    private type.TRankedProducts evaluate(List<type.TLoanProduct> bankrates, java.math.BigDecimal requestedAmt, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        List<type.TMetric> metricsTable = bankrates.stream().map(i -> FinancialMetrics(i, requestedAmt, annotationSet_, eventListener_, externalExecutor_)).collect(Collectors.toList());
        List<type.TMetric> rankByRate = sort(metricsTable, new com.gs.dmn.runtime.LambdaExpression<Boolean>() {public Boolean apply(Object... args) {type.TMetric x = (type.TMetric)args[0]; type.TMetric y = (type.TMetric)args[1];return numericLessThan(((java.math.BigDecimal)(x != null ? x.getRate() : null)), ((java.math.BigDecimal)(y != null ? y.getRate() : null)));}});
        List<type.TMetric> rankByDownPmt = sort(metricsTable, new com.gs.dmn.runtime.LambdaExpression<Boolean>() {public Boolean apply(Object... args) {type.TMetric x = (type.TMetric)args[0]; type.TMetric y = (type.TMetric)args[1];return numericLessThan(((java.math.BigDecimal)(x != null ? x.getDownPmtAmt() : null)), ((java.math.BigDecimal)(y != null ? y.getDownPmtAmt() : null)));}});
        List<type.TMetric> rankByMonthlyPmt = sort(metricsTable, new com.gs.dmn.runtime.LambdaExpression<Boolean>() {public Boolean apply(Object... args) {type.TMetric x = (type.TMetric)args[0]; type.TMetric y = (type.TMetric)args[1];return numericLessThan(((java.math.BigDecimal)(x != null ? x.getPaymentAmt() : null)), ((java.math.BigDecimal)(y != null ? y.getPaymentAmt() : null)));}});
        List<type.TMetric> rankByEquityPct = sort(metricsTable, new com.gs.dmn.runtime.LambdaExpression<Boolean>() {public Boolean apply(Object... args) {type.TMetric x = (type.TMetric)args[0]; type.TMetric y = (type.TMetric)args[1];return numericGreaterThan(((java.math.BigDecimal)(x != null ? x.getEquity36moPct() : null)), ((java.math.BigDecimal)(y != null ? y.getEquity36moPct() : null)));}});
        type.TRankedProductsImpl rankedProducts = new type.TRankedProductsImpl();
        rankedProducts.setMetricsTable(metricsTable);
        rankedProducts.setRankByRate(rankByRate);
        rankedProducts.setRankByDownPmt(rankByDownPmt);
        rankedProducts.setRankByMonthlyPmt(rankByMonthlyPmt);
        rankedProducts.setRankByEquityPct(rankByEquityPct);
        return rankedProducts;
    }
}
