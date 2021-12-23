
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"bkm.ftl", "FinancialMetrics"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "FinancialMetrics",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.CONTEXT,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class FinancialMetrics extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "FinancialMetrics",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
        com.gs.dmn.runtime.annotation.ExpressionKind.CONTEXT,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    private static class FinancialMetricsLazyHolder {
        static final FinancialMetrics INSTANCE = new FinancialMetrics();
    }
    public static FinancialMetrics instance() {
        return FinancialMetricsLazyHolder.INSTANCE;
    }

    private FinancialMetrics() {
    }

    public type.TMetric apply(type.TLoanProduct product, java.math.BigDecimal requestedAmt, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start BKM 'FinancialMetrics'
            long financialMetricsStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments financialMetricsArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            financialMetricsArguments_.put("product", product);
            financialMetricsArguments_.put("requestedAmt", requestedAmt);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, financialMetricsArguments_);

            // Evaluate BKM 'FinancialMetrics'
            type.TMetric output_ = lambda.apply(product, requestedAmt, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End BKM 'FinancialMetrics'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, financialMetricsArguments_, output_, (System.currentTimeMillis() - financialMetricsStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'FinancialMetrics' evaluation", e);
            return null;
        }
    }

    public com.gs.dmn.runtime.LambdaExpression<type.TMetric> lambda =
        new com.gs.dmn.runtime.LambdaExpression<type.TMetric>() {
            public type.TMetric apply(Object... args_) {
                type.TLoanProduct product = 0 < args_.length ? (type.TLoanProduct) args_[0] : null;
                java.math.BigDecimal requestedAmt = 1 < args_.length ? (java.math.BigDecimal) args_[1] : null;
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = 2 < args_.length ? (com.gs.dmn.runtime.annotation.AnnotationSet) args_[2] : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = 3 < args_.length ? (com.gs.dmn.runtime.listener.EventListener) args_[3] : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = 4 < args_.length ? (com.gs.dmn.runtime.external.ExternalFunctionExecutor) args_[4] : null;
                com.gs.dmn.runtime.cache.Cache cache_ = 5 < args_.length ? (com.gs.dmn.runtime.cache.Cache) args_[5] : null;

                String lenderName = ((String)(product != null ? product.getLenderName() : null));
                java.math.BigDecimal rate = ((java.math.BigDecimal)(product != null ? product.getRate() : null));
                java.math.BigDecimal points = ((java.math.BigDecimal)(product != null ? product.getPoints() : null));
                java.math.BigDecimal fee = ((java.math.BigDecimal)(product != null ? product.getFee() : null));
                java.math.BigDecimal loanAmt = numericAdd(numericMultiply(requestedAmt, numericAdd(number("1"), numericDivide(points, number("100")))), fee);
                java.math.BigDecimal downPmtAmt = numericMultiply(number("0.2"), loanAmt);
                java.math.BigDecimal paymentAmt = MonthlyPayment.instance().apply(loanAmt, rate, number("360"), annotationSet_, eventListener_, externalExecutor_, cache_);
                java.math.BigDecimal equity36moPct = numericSubtract(number("1"), numericMultiply(numericDivide(Equity36Mo.instance().apply(loanAmt, rate, number("36"), paymentAmt, annotationSet_, eventListener_, externalExecutor_, cache_), requestedAmt), number("0.8")));
                type.TMetricImpl financialMetrics = new type.TMetricImpl();
                financialMetrics.setLenderName(lenderName);
                financialMetrics.setRate(rate);
                financialMetrics.setPoints(points);
                financialMetrics.setFee(fee);
                financialMetrics.setLoanAmt(loanAmt);
                financialMetrics.setDownPmtAmt(downPmtAmt);
                financialMetrics.setPaymentAmt(paymentAmt);
                financialMetrics.setEquity36moPct(equity36moPct);
                return financialMetrics;
            }
        };
}
