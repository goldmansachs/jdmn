
import java.util.*;
import java.util.stream.Collectors;

import static PMT.PMT;

@javax.annotation.Generated(value = {"decision.ftl", "MonthlyPayment"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "MonthlyPayment",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class MonthlyPayment extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "MonthlyPayment",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public MonthlyPayment() {
    }

    public java.math.BigDecimal apply(String loan, String fee, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply((loan != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(loan, type.TLoanImpl.class) : null), (fee != null ? number(fee) : null), annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor());
        } catch (Exception e) {
            logError("Cannot apply decision 'MonthlyPayment'", e);
            return null;
        }
    }

    public java.math.BigDecimal apply(String loan, String fee, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        try {
            return apply((loan != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(loan, type.TLoanImpl.class) : null), (fee != null ? number(fee) : null), annotationSet_, eventListener_, externalExecutor_);
        } catch (Exception e) {
            logError("Cannot apply decision 'MonthlyPayment'", e);
            return null;
        }
    }

    public java.math.BigDecimal apply(type.TLoan loan, java.math.BigDecimal fee, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(loan, fee, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor());
    }

    public java.math.BigDecimal apply(type.TLoan loan, java.math.BigDecimal fee, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        try {
            // Start decision 'MonthlyPayment'
            long monthlyPaymentStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments monthlyPaymentArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            monthlyPaymentArguments_.put("loan", loan);
            monthlyPaymentArguments_.put("fee", fee);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, monthlyPaymentArguments_);

            // Evaluate decision 'MonthlyPayment'
            java.math.BigDecimal output_ = evaluate(loan, fee, annotationSet_, eventListener_, externalExecutor_);

            // End decision 'MonthlyPayment'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, monthlyPaymentArguments_, output_, (System.currentTimeMillis() - monthlyPaymentStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'MonthlyPayment' evaluation", e);
            return null;
        }
    }

    private java.math.BigDecimal evaluate(type.TLoan loan, java.math.BigDecimal fee, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        return numericAdd(PMT(((java.math.BigDecimal)(loan != null ? loan.getAmount() : null)), ((java.math.BigDecimal)(loan != null ? loan.getRate() : null)), ((java.math.BigDecimal)(loan != null ? loan.getTerm() : null)), annotationSet_, eventListener_, externalExecutor_), fee);
    }
}
