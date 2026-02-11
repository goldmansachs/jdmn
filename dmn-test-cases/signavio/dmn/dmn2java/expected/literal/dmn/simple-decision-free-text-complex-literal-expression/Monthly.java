
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"signavio-decision.ftl", "monthly"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "http://www.provider.com/dmn/1.1/diagram/701a4e318a6d4d4f823018c5feaa8f81.xml",
    name = "monthly",
    label = "Monthly",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class Monthly extends com.gs.dmn.signavio.runtime.JavaTimeSignavioBaseDecision<java.lang.Number> {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "monthly",
        "Monthly",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public Monthly() {
    }

    @java.lang.Override()
    public java.lang.Number applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply((input_.get("Loan") != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(input_.get("Loan"), new com.fasterxml.jackson.core.type.TypeReference<type.LoanImpl>() {}) : null), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'Monthly'", e);
            return null;
        }
    }

    public java.lang.Number apply(type.Loan loan, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'monthly'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long monthlyStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments monthlyArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            monthlyArguments_.put("Loan", loan);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, monthlyArguments_);

            // Evaluate decision 'monthly'
            java.lang.Number output_ = evaluate(loan, context_);

            // End decision 'monthly'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, monthlyArguments_, output_, (System.currentTimeMillis() - monthlyStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'monthly' evaluation", e);
            return null;
        }
    }

    protected java.lang.Number evaluate(type.Loan loan, com.gs.dmn.runtime.ExecutionContext context_) {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        return numericDivide(numericDivide(numericMultiply(((java.lang.Number)(loan != null ? loan.getPrincipal() : null)), ((java.lang.Number)(loan != null ? loan.getRate() : null))), number("12")), numericSubtract(number("1"), numericExponentiation(numericAdd(number("1"), numericDivide(((java.lang.Number)(loan != null ? loan.getRate() : null)), number("12"))), numericUnaryMinus(((java.lang.Number)(loan != null ? loan.getTerm() : null))))));
    }
}
