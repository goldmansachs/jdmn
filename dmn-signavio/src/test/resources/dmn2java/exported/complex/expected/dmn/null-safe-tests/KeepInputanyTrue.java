
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"signavio-decision.ftl", "keepInputanyTrue"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "keepInputanyTrue",
    label = "keepInput_anyTrue",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class KeepInputanyTrue extends com.gs.dmn.signavio.runtime.DefaultSignavioBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "keepInputanyTrue",
        "keepInput_anyTrue",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public KeepInputanyTrue() {
    }

    public Boolean apply(String booleanAnyTrue_iterator, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply((booleanAnyTrue_iterator != null ? Boolean.valueOf(booleanAnyTrue_iterator) : null), annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
        } catch (Exception e) {
            logError("Cannot apply decision 'KeepInputanyTrue'", e);
            return null;
        }
    }

    public Boolean apply(String booleanAnyTrue_iterator, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            return apply((booleanAnyTrue_iterator != null ? Boolean.valueOf(booleanAnyTrue_iterator) : null), annotationSet_, eventListener_, externalExecutor_, cache_);
        } catch (Exception e) {
            logError("Cannot apply decision 'KeepInputanyTrue'", e);
            return null;
        }
    }

    public Boolean apply(Boolean booleanAnyTrue_iterator, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(booleanAnyTrue_iterator, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
    }

    public Boolean apply(Boolean booleanAnyTrue_iterator, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision 'keepInputanyTrue'
            long keepInputanyTrueStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments keepInputanyTrueArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            keepInputanyTrueArguments_.put("booleanAnyTrue", booleanAnyTrue_iterator);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, keepInputanyTrueArguments_);

            // Evaluate decision 'keepInputanyTrue'
            Boolean output_ = evaluate(booleanAnyTrue_iterator, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision 'keepInputanyTrue'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, keepInputanyTrueArguments_, output_, (System.currentTimeMillis() - keepInputanyTrueStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'keepInputanyTrue' evaluation", e);
            return null;
        }
    }

    protected Boolean evaluate(Boolean booleanAnyTrue_iterator, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        return booleanAnyTrue_iterator;
    }
}
