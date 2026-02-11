
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"bkm.ftl", "FinancialMetrics"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "http://www.trisotech.com/definitions/_56c7d4a5-e6db-4bba-ac5f-dc082a16f719",
    name = "FinancialMetrics",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.CONTEXT,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class FinancialMetrics extends com.gs.dmn.runtime.JavaTimeDMNBaseDecision<type.TMetric> {
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

    @java.lang.Override()
    public type.TMetric applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply((input_.get("product") != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(input_.get("product"), new com.fasterxml.jackson.core.type.TypeReference<type.TLoanProductImpl>() {}) : null), (input_.get("requestedAmt") != null ? number(input_.get("requestedAmt")) : null), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'FinancialMetrics'", e);
            return null;
        }
    }

    @java.lang.Override()
    public type.TMetric applyPojo(com.gs.dmn.runtime.ExecutableDRGElementInput input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply(((FinancialMetricsInput_)input_).getProduct(), ((FinancialMetricsInput_)input_).getRequestedAmt(), context_);
        } catch (Exception e) {
            logError("Cannot apply element 'FinancialMetrics'", e);
            return null;
        }
    }

    @java.lang.Override()
    public type.TMetric applyContext(com.gs.dmn.runtime.Context input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return applyPojo(new FinancialMetricsInput_(input_), context_);
        } catch (Exception e) {
            logError("Cannot apply element 'FinancialMetrics'", e);
            return null;
        }
    }

    public type.TMetric apply(type.TLoanProduct product, java.lang.Number requestedAmt, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start BKM 'FinancialMetrics'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long financialMetricsStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments financialMetricsArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            financialMetricsArguments_.put("product", product);
            financialMetricsArguments_.put("requestedAmt", requestedAmt);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, financialMetricsArguments_);

            // Evaluate BKM 'FinancialMetrics'
            type.TMetric output_ = lambda.apply(product, requestedAmt, context_);

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
                java.lang.Number requestedAmt = 1 < args_.length ? (java.lang.Number) args_[1] : null;
                com.gs.dmn.runtime.ExecutionContext context_ = 2 < args_.length ? (com.gs.dmn.runtime.ExecutionContext) args_[2] : null;
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
                com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;

                String lenderName = ((String)(product != null ? product.getLenderName() : null));
                java.lang.Number rate = ((java.lang.Number)(product != null ? product.getRate() : null));
                java.lang.Number points = ((java.lang.Number)(product != null ? product.getPoints() : null));
                java.lang.Number fee = ((java.lang.Number)(product != null ? product.getFee() : null));
                java.lang.Number loanAmt = numericAdd(numericMultiply(requestedAmt, numericAdd(number("1"), numericDivide(points, number("100")))), fee);
                java.lang.Number downPmtAmt = numericMultiply(number("0.2"), loanAmt);
                java.lang.Number paymentAmt = MonthlyPayment.instance().apply(loanAmt, rate, number("360"), context_);
                java.lang.Number equity36moPct = numericSubtract(number("1"), numericMultiply(numericDivide(Equity36Mo.instance().apply(loanAmt, rate, number("36"), paymentAmt, context_), requestedAmt), number("0.8")));
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
