
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"signavio-decision.ftl", "allFalseAggregation"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "allFalseAggregation",
    label = "allFalseAggregation",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.OTHER,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class AllFalseAggregation extends com.gs.dmn.signavio.runtime.DefaultSignavioBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "allFalseAggregation",
        "allFalseAggregation",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.OTHER,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public AllFalseAggregation() {
    }

    public Boolean apply(String booleanList, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply((booleanList != null ? asList(com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(booleanList, Boolean[].class)) : null), annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor());
        } catch (Exception e) {
            logError("Cannot apply decision 'AllFalseAggregation'", e);
            return null;
        }
    }

    public Boolean apply(String booleanList, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        try {
            return apply((booleanList != null ? asList(com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(booleanList, Boolean[].class)) : null), annotationSet_, eventListener_, externalExecutor_);
        } catch (Exception e) {
            logError("Cannot apply decision 'AllFalseAggregation'", e);
            return null;
        }
    }

    public Boolean apply(List<Boolean> booleanList, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(booleanList, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor());
    }

    public Boolean apply(List<Boolean> booleanList, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        try {
            // Start decision 'allFalseAggregation'
            long allFalseAggregationStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments allFalseAggregationArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            allFalseAggregationArguments_.put("booleanList", booleanList);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, allFalseAggregationArguments_);

            // Iterate and aggregate
            Boolean output_ = evaluate(booleanList, annotationSet_, eventListener_, externalExecutor_);

            // End decision 'allFalseAggregation'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, allFalseAggregationArguments_, output_, (System.currentTimeMillis() - allFalseAggregationStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'allFalseAggregation' evaluation", e);
            return null;
        }
    }

    protected Boolean evaluate(List<Boolean> booleanList, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        KeepInputallFalse keepInputallFalse = new KeepInputallFalse();
        return booleanList.stream().allMatch(booleanAllFalse_iterator -> !keepInputallFalse.apply(booleanAllFalse_iterator, annotationSet_, eventListener_, externalExecutor_));
    }
}
