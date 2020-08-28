
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"signavio-decision.ftl", "formattingAndCoercing"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "formattingAndCoercing",
    label = "formattingAndCoercing",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class FormattingAndCoercing extends com.gs.dmn.signavio.runtime.DefaultSignavioBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "formattingAndCoercing",
        "formattingAndCoercing",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public FormattingAndCoercing() {
    }

    public java.math.BigDecimal apply(String numberB, String string, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply((numberB != null ? number(numberB) : null), string, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
        } catch (Exception e) {
            logError("Cannot apply decision 'FormattingAndCoercing'", e);
            return null;
        }
    }

    public java.math.BigDecimal apply(String numberB, String string, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            return apply((numberB != null ? number(numberB) : null), string, annotationSet_, eventListener_, externalExecutor_, cache_);
        } catch (Exception e) {
            logError("Cannot apply decision 'FormattingAndCoercing'", e);
            return null;
        }
    }

    public java.math.BigDecimal apply(java.math.BigDecimal numberB, String string, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(numberB, string, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
    }

    public java.math.BigDecimal apply(java.math.BigDecimal numberB, String string, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision 'formattingAndCoercing'
            long formattingAndCoercingStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments formattingAndCoercingArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            formattingAndCoercingArguments_.put("numberB", numberB);
            formattingAndCoercingArguments_.put("string", string);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, formattingAndCoercingArguments_);

            // Evaluate decision 'formattingAndCoercing'
            java.math.BigDecimal output_ = evaluate(numberB, string, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision 'formattingAndCoercing'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, formattingAndCoercingArguments_, output_, (System.currentTimeMillis() - formattingAndCoercingStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'formattingAndCoercing' evaluation", e);
            return null;
        }
    }

    protected java.math.BigDecimal evaluate(java.math.BigDecimal numberB, String string, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        return number(text(numberB, string));
    }
}
