
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"signavio-decision.ftl", "allTrueAggregation"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "allTrueAggregation",
    label = "allTrueAggregation",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.OTHER,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class AllTrueAggregation extends com.gs.dmn.signavio.runtime.DefaultSignavioBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "allTrueAggregation",
        "allTrueAggregation",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.OTHER,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public AllTrueAggregation() {
    }

    public Boolean apply(String booleanList, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply((booleanList != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(booleanList, new com.fasterxml.jackson.core.type.TypeReference<List<Boolean>>() {}) : null), annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
        } catch (Exception e) {
            logError("Cannot apply decision 'AllTrueAggregation'", e);
            return null;
        }
    }

    public Boolean apply(String booleanList, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            return apply((booleanList != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(booleanList, new com.fasterxml.jackson.core.type.TypeReference<List<Boolean>>() {}) : null), annotationSet_, eventListener_, externalExecutor_, cache_);
        } catch (Exception e) {
            logError("Cannot apply decision 'AllTrueAggregation'", e);
            return null;
        }
    }

    public Boolean apply(List<Boolean> booleanList, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(booleanList, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
    }

    public Boolean apply(List<Boolean> booleanList, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision 'allTrueAggregation'
            long allTrueAggregationStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments allTrueAggregationArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            allTrueAggregationArguments_.put("booleanList", booleanList);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, allTrueAggregationArguments_);

            // Iterate and aggregate
            Boolean output_ = evaluate(booleanList, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision 'allTrueAggregation'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, allTrueAggregationArguments_, output_, (System.currentTimeMillis() - allTrueAggregationStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'allTrueAggregation' evaluation", e);
            return null;
        }
    }

    protected Boolean evaluate(List<Boolean> booleanList, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        KeepInputallTrue keepInputallTrue = new KeepInputallTrue();
        return booleanList.stream().allMatch(booleanAllTrue_iterator -> keepInputallTrue.apply(booleanAllTrue_iterator, annotationSet_, eventListener_, externalExecutor_, cache_));
    }
}
