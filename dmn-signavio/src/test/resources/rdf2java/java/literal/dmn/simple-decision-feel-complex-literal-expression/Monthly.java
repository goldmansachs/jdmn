
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"signavio-decision.ftl", "monthly"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "monthly",
    label = "Monthly",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class Monthly extends com.gs.dmn.signavio.runtime.DefaultSignavioBaseDecision {
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

    public java.math.BigDecimal apply(String loan, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply((loan != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(loan, new com.fasterxml.jackson.core.type.TypeReference<type.LoanImpl>() {}) : null), annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
        } catch (Exception e) {
            logError("Cannot apply decision 'Monthly'", e);
            return null;
        }
    }

    public java.math.BigDecimal apply(String loan, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            return apply((loan != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(loan, new com.fasterxml.jackson.core.type.TypeReference<type.LoanImpl>() {}) : null), annotationSet_, eventListener_, externalExecutor_, cache_);
        } catch (Exception e) {
            logError("Cannot apply decision 'Monthly'", e);
            return null;
        }
    }

    public java.math.BigDecimal apply(type.Loan loan, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(loan, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
    }

    public java.math.BigDecimal apply(type.Loan loan, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision 'monthly'
            long monthlyStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments monthlyArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            monthlyArguments_.put("Loan", loan);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, monthlyArguments_);

            // Evaluate decision 'monthly'
            java.math.BigDecimal output_ = evaluate(loan, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision 'monthly'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, monthlyArguments_, output_, (System.currentTimeMillis() - monthlyStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'monthly' evaluation", e);
            return null;
        }
    }

    protected java.math.BigDecimal evaluate(type.Loan loan, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        return ((java.math.BigDecimal)(loan != null ? loan.getPrincipal() : null));
    }
}
