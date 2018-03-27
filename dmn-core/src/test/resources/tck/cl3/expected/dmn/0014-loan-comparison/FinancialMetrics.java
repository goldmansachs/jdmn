
import java.util.*;
import java.util.stream.Collectors;

import static MonthlyPayment.monthlyPayment;
import static Equity36Mo.equity36Mo;

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

    public static final FinancialMetrics INSTANCE = new FinancialMetrics();

    private FinancialMetrics() {
    }

    public static type.TMetric FinancialMetrics(type.TLoanProduct product, java.math.BigDecimal requestedAmt, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        return INSTANCE.apply(product, requestedAmt, annotationSet_, eventListener_, externalExecutor_);
    }

    private type.TMetric apply(type.TLoanProduct product, java.math.BigDecimal requestedAmt, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        try {
            // Start BKM 'FinancialMetrics'
            long financialMetricsStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments financialMetricsArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            financialMetricsArguments_.put("product", product);
            financialMetricsArguments_.put("requestedAmt", requestedAmt);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, financialMetricsArguments_);

            // Evaluate BKM 'FinancialMetrics'
            type.TMetric output_ = evaluate(product, requestedAmt, annotationSet_, eventListener_, externalExecutor_);

            // End BKM 'FinancialMetrics'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, financialMetricsArguments_, output_, (System.currentTimeMillis() - financialMetricsStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'FinancialMetrics' evaluation", e);
            return null;
        }
    }

    private type.TMetric evaluate(type.TLoanProduct product, java.math.BigDecimal requestedAmt, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        String lenderName = ((String)(product != null ? product.getLenderName() : null));
        java.math.BigDecimal rate = ((java.math.BigDecimal)(product != null ? product.getRate() : null));
        java.math.BigDecimal points = ((java.math.BigDecimal)(product != null ? product.getPoints() : null));
        java.math.BigDecimal fee = ((java.math.BigDecimal)(product != null ? product.getFee() : null));
        java.math.BigDecimal loanAmt = numericAdd(numericMultiply(requestedAmt, numericAdd(number("1"), numericDivide(points, number("100")))), fee);
        java.math.BigDecimal downPmtAmt = numericMultiply(number("0.2"), loanAmt);
        java.math.BigDecimal paymentAmt = monthlyPayment(loanAmt, rate, number("360"), annotationSet_, eventListener_, externalExecutor_);
        java.math.BigDecimal equity36moPct = numericSubtract(number("1"), numericMultiply(numericDivide(equity36Mo(loanAmt, rate, number("36"), paymentAmt, annotationSet_, eventListener_, externalExecutor_), requestedAmt), number("0.8")));
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
}
