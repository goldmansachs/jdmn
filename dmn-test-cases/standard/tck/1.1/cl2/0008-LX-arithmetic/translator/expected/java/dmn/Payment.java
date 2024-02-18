
import java.util.*;
import java.util.stream.Collectors;

@jakarta.annotation.Generated(value = {"decision.ftl", "payment"})
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

    @java.lang.Override()
    public java.math.BigDecimal applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply((input_.get("loan") != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(input_.get("loan"), new com.fasterxml.jackson.core.type.TypeReference<type.TLoanImpl>() {}) : null), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'Payment'", e);
            return null;
        }
    }

    public java.math.BigDecimal apply(type.TLoan loan, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'payment'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long paymentStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments paymentArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            paymentArguments_.put("loan", loan);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, paymentArguments_);

            // Evaluate decision 'payment'
            java.math.BigDecimal output_ = lambda.apply(loan, context_);

            // End decision 'payment'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, paymentArguments_, output_, (System.currentTimeMillis() - paymentStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'payment' evaluation", e);
            return null;
        }
    }

    public com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal> lambda =
        new com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal>() {
            public java.math.BigDecimal apply(Object... args_) {
                type.TLoan loan = 0 < args_.length ? (type.TLoan) args_[0] : null;
                com.gs.dmn.runtime.ExecutionContext context_ = 1 < args_.length ? (com.gs.dmn.runtime.ExecutionContext) args_[1] : null;
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
                com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;

                return numericDivide(numericDivide(numericMultiply(((java.math.BigDecimal)(loan != null ? loan.getPrincipal() : null)), ((java.math.BigDecimal)(loan != null ? loan.getRate() : null))), number("12")), numericSubtract(number("1"), numericExponentiation(numericAdd(number("1"), numericDivide(((java.math.BigDecimal)(loan != null ? loan.getRate() : null)), number("12"))), numericUnaryMinus(((java.math.BigDecimal)(loan != null ? loan.getTermMonths() : null))))));
            }
        };
}
