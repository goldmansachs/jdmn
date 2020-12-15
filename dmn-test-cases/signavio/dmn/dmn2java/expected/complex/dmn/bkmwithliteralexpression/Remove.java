
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"signavio-decision.ftl", "remove"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "remove",
    label = "remove",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class Remove extends com.gs.dmn.signavio.runtime.DefaultSignavioBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "remove",
        "remove",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public Remove() {
    }

    public List<String> apply(String rgb2, String rgb2List, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply(rgb2, (rgb2List != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(rgb2List, new com.fasterxml.jackson.core.type.TypeReference<List<String>>() {}) : null), annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
        } catch (Exception e) {
            logError("Cannot apply decision 'Remove'", e);
            return null;
        }
    }

    public List<String> apply(String rgb2, String rgb2List, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            return apply(rgb2, (rgb2List != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(rgb2List, new com.fasterxml.jackson.core.type.TypeReference<List<String>>() {}) : null), annotationSet_, eventListener_, externalExecutor_, cache_);
        } catch (Exception e) {
            logError("Cannot apply decision 'Remove'", e);
            return null;
        }
    }

    public List<String> apply(String rgb2, List<String> rgb2List, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(rgb2, rgb2List, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
    }

    public List<String> apply(String rgb2, List<String> rgb2List, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision 'remove'
            long removeStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments removeArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            removeArguments_.put("rgb2", rgb2);
            removeArguments_.put("rgb2 list", rgb2List);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, removeArguments_);

            // Evaluate decision 'remove'
            List<String> output_ = evaluate(rgb2, rgb2List, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision 'remove'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, removeArguments_, output_, (System.currentTimeMillis() - removeStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'remove' evaluation", e);
            return null;
        }
    }

    protected List<String> evaluate(String rgb2, List<String> rgb2List, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        return remove(rgb2List, rgb2);
    }
}
