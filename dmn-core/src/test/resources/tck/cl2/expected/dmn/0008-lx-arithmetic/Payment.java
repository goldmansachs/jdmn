
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "payment"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "payment",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class Payment extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "payment",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public Payment() {
    }

    public java.math.BigDecimal apply(String loan, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply((loan != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(loan, type.TLoanImpl.class) : null), annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor());
        } catch (Exception e) {
            logError("Cannot apply decision 'Payment'", e);
            return null;
        }
    }

    public java.math.BigDecimal apply(String loan, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        try {
            return apply((loan != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(loan, type.TLoanImpl.class) : null), annotationSet_, eventListener_, externalExecutor_);
        } catch (Exception e) {
            logError("Cannot apply decision 'Payment'", e);
            return null;
        }
    }

    public java.math.BigDecimal apply(type.TLoan loan, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(loan, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor());
    }

    public java.math.BigDecimal apply(type.TLoan loan, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        try {
            // Decision start
            long startTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments arguments_ = new com.gs.dmn.runtime.listener.Arguments();
            arguments_.put("loan", loan);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, arguments_);

            // Evaluate expression
            java.math.BigDecimal output_ = evaluate(loan, annotationSet_, eventListener_, externalExecutor_);

            // Decision end
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, arguments_, output_, (System.currentTimeMillis() - startTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'payment' evaluation", e);
            return null;
        }
    }

    private java.math.BigDecimal evaluate(type.TLoan loan, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        return numericDivide(numericDivide(numericMultiply(((java.math.BigDecimal)loan.getPrincipal()), ((java.math.BigDecimal)loan.getRate())), number("12")), numericSubtract(number("1"), numericExponentiation(numericAdd(number("1"), numericDivide(((java.math.BigDecimal)loan.getRate()), number("12"))), numericUnaryMinus(((java.math.BigDecimal)loan.getTermMonths())))));
    }
}
