
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"signavio-decision.ftl", "negateSecond"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "negateSecond",
    label = "negateSecond",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class NegateSecond extends com.gs.dmn.signavio.runtime.DefaultSignavioBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "negateSecond",
        "negateSecond",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public NegateSecond() {
    }

    public java.math.BigDecimal apply(String second, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply((second != null ? number(second) : null), annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
        } catch (Exception e) {
            logError("Cannot apply decision 'NegateSecond'", e);
            return null;
        }
    }

    public java.math.BigDecimal apply(String second, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            return apply((second != null ? number(second) : null), annotationSet_, eventListener_, externalExecutor_, cache_);
        } catch (Exception e) {
            logError("Cannot apply decision 'NegateSecond'", e);
            return null;
        }
    }

    public java.math.BigDecimal apply(java.math.BigDecimal second, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(second, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
    }

    public java.math.BigDecimal apply(java.math.BigDecimal second, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision 'negateSecond'
            long negateSecondStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments negateSecondArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            negateSecondArguments_.put("second", second);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, negateSecondArguments_);

            // Evaluate decision 'negateSecond'
            java.math.BigDecimal output_ = evaluate(second, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision 'negateSecond'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, negateSecondArguments_, output_, (System.currentTimeMillis() - negateSecondStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'negateSecond' evaluation", e);
            return null;
        }
    }

    protected java.math.BigDecimal evaluate(java.math.BigDecimal second, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        return numericUnaryMinus(second);
    }
}
