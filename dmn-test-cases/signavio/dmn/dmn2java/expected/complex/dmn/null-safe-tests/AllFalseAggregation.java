
import java.util.*;
import java.util.stream.Collectors;

@jakarta.annotation.Generated(value = {"signavio-decision.ftl", "allFalseAggregation"})
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

    @java.lang.Override()
    public Boolean applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply((input_.get("booleanList") != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(input_.get("booleanList"), new com.fasterxml.jackson.core.type.TypeReference<List<Boolean>>() {}) : null), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'AllFalseAggregation'", e);
            return null;
        }
    }

    public Boolean apply(List<Boolean> booleanList, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'allFalseAggregation'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long allFalseAggregationStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments allFalseAggregationArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            allFalseAggregationArguments_.put("booleanList", booleanList);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, allFalseAggregationArguments_);

            // Iterate and aggregate
            Boolean output_ = evaluate(booleanList, context_);

            // End decision 'allFalseAggregation'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, allFalseAggregationArguments_, output_, (System.currentTimeMillis() - allFalseAggregationStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'allFalseAggregation' evaluation", e);
            return null;
        }
    }

    protected Boolean evaluate(List<Boolean> booleanList, com.gs.dmn.runtime.ExecutionContext context_) {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        KeepInputallFalse keepInputallFalse = new KeepInputallFalse();
        return booleanList.stream().allMatch(booleanAllFalse_iterator -> !keepInputallFalse.apply(booleanAllFalse_iterator, context_));
    }
}
