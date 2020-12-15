
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"signavio-decision.ftl", "childObject"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "childObject",
    label = "ChildObject",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class ChildObject extends com.gs.dmn.signavio.runtime.DefaultSignavioBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "childObject",
        "ChildObject",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    private final Abc abc;

    public ChildObject() {
        this(new Abc());
    }

    public ChildObject(Abc abc) {
        this.abc = abc;
    }

    public java.math.BigDecimal apply(String num, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply((num != null ? number(num) : null), annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
        } catch (Exception e) {
            logError("Cannot apply decision 'ChildObject'", e);
            return null;
        }
    }

    public java.math.BigDecimal apply(String num, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            return apply((num != null ? number(num) : null), annotationSet_, eventListener_, externalExecutor_, cache_);
        } catch (Exception e) {
            logError("Cannot apply decision 'ChildObject'", e);
            return null;
        }
    }

    public java.math.BigDecimal apply(java.math.BigDecimal num, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(num, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
    }

    public java.math.BigDecimal apply(java.math.BigDecimal num, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision 'childObject'
            long childObjectStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments childObjectArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            childObjectArguments_.put("num", num);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, childObjectArguments_);

            // Apply child decisions
            java.math.BigDecimal abc = this.abc.apply(num, annotationSet_, eventListener_, externalExecutor_, cache_);

            // Evaluate decision 'childObject'
            java.math.BigDecimal output_ = evaluate(abc, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision 'childObject'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, childObjectArguments_, output_, (System.currentTimeMillis() - childObjectStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'childObject' evaluation", e);
            return null;
        }
    }

    protected java.math.BigDecimal evaluate(java.math.BigDecimal abc, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        return abc;
    }
}
