
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"signavio-decision.ftl", "keepInputallTrue"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "keepInputallTrue",
    label = "keepInput_allTrue",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class KeepInputallTrue extends com.gs.dmn.signavio.runtime.DefaultSignavioBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "keepInputallTrue",
        "keepInput_allTrue",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public KeepInputallTrue() {
    }

    public Boolean apply(String booleanAllTrue_iterator, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply((booleanAllTrue_iterator != null ? Boolean.valueOf(booleanAllTrue_iterator) : null), annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
        } catch (Exception e) {
            logError("Cannot apply decision 'KeepInputallTrue'", e);
            return null;
        }
    }

    public Boolean apply(String booleanAllTrue_iterator, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            return apply((booleanAllTrue_iterator != null ? Boolean.valueOf(booleanAllTrue_iterator) : null), annotationSet_, eventListener_, externalExecutor_, cache_);
        } catch (Exception e) {
            logError("Cannot apply decision 'KeepInputallTrue'", e);
            return null;
        }
    }

    public Boolean apply(Boolean booleanAllTrue_iterator, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(booleanAllTrue_iterator, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
    }

    public Boolean apply(Boolean booleanAllTrue_iterator, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision 'keepInputallTrue'
            long keepInputallTrueStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments keepInputallTrueArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            keepInputallTrueArguments_.put("booleanAllTrue", booleanAllTrue_iterator);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, keepInputallTrueArguments_);

            // Evaluate decision 'keepInputallTrue'
            Boolean output_ = evaluate(booleanAllTrue_iterator, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision 'keepInputallTrue'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, keepInputallTrueArguments_, output_, (System.currentTimeMillis() - keepInputallTrueStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'keepInputallTrue' evaluation", e);
            return null;
        }
    }

    protected Boolean evaluate(Boolean booleanAllTrue_iterator, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        return booleanAllTrue_iterator;
    }
}
