
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"signavio-decision.ftl", "sUM"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "sUM",
    label = "SUM",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class SUM extends com.gs.dmn.signavio.runtime.DefaultSignavioBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "sUM",
        "SUM",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public SUM() {
    }

    public java.math.BigDecimal apply(String a, String b, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply((a != null ? number(a) : null), (b != null ? number(b) : null), annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
        } catch (Exception e) {
            logError("Cannot apply decision 'SUM'", e);
            return null;
        }
    }

    public java.math.BigDecimal apply(String a, String b, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            return apply((a != null ? number(a) : null), (b != null ? number(b) : null), annotationSet_, eventListener_, externalExecutor_, cache_);
        } catch (Exception e) {
            logError("Cannot apply decision 'SUM'", e);
            return null;
        }
    }

    public java.math.BigDecimal apply(java.math.BigDecimal a, java.math.BigDecimal b, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(a, b, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
    }

    public java.math.BigDecimal apply(java.math.BigDecimal a, java.math.BigDecimal b, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision 'sUM'
            long sUMStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments sUMArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            sUMArguments_.put("A", a);
            sUMArguments_.put("B", b);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, sUMArguments_);

            // Evaluate decision 'sUM'
            java.math.BigDecimal output_ = evaluate(a, b, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision 'sUM'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, sUMArguments_, output_, (System.currentTimeMillis() - sUMStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'sUM' evaluation", e);
            return null;
        }
    }

    protected java.math.BigDecimal evaluate(java.math.BigDecimal a, java.math.BigDecimal b, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        return numericAdd(a, b);
    }
}
