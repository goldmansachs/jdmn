
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"signavio-decision.ftl", "removeall"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "removeall",
    label = "removeall",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class Removeall extends com.gs.dmn.signavio.runtime.DefaultSignavioBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "removeall",
        "removeall",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public Removeall() {
    }

    public List<String> apply(String blacklist, String names, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply((blacklist != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(blacklist, new com.fasterxml.jackson.core.type.TypeReference<List<String>>() {}) : null), (names != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(names, new com.fasterxml.jackson.core.type.TypeReference<List<String>>() {}) : null), annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
        } catch (Exception e) {
            logError("Cannot apply decision 'Removeall'", e);
            return null;
        }
    }

    public List<String> apply(String blacklist, String names, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            return apply((blacklist != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(blacklist, new com.fasterxml.jackson.core.type.TypeReference<List<String>>() {}) : null), (names != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(names, new com.fasterxml.jackson.core.type.TypeReference<List<String>>() {}) : null), annotationSet_, eventListener_, externalExecutor_, cache_);
        } catch (Exception e) {
            logError("Cannot apply decision 'Removeall'", e);
            return null;
        }
    }

    public List<String> apply(List<String> blacklist, List<String> names, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(blacklist, names, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
    }

    public List<String> apply(List<String> blacklist, List<String> names, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision 'removeall'
            long removeallStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments removeallArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            removeallArguments_.put("blacklist", blacklist);
            removeallArguments_.put("names", names);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, removeallArguments_);

            // Evaluate decision 'removeall'
            List<String> output_ = evaluate(blacklist, names, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision 'removeall'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, removeallArguments_, output_, (System.currentTimeMillis() - removeallStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'removeall' evaluation", e);
            return null;
        }
    }

    protected List<String> evaluate(List<String> blacklist, List<String> names, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        return removeAll(names, blacklist);
    }
}
