
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"signavio-decision.ftl", "anyTrueAggregation"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "anyTrueAggregation",
    label = "anyTrueAggregation",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.OTHER,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class AnyTrueAggregation extends com.gs.dmn.signavio.runtime.JavaTimeSignavioBaseDecision<Boolean> {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "anyTrueAggregation",
        "anyTrueAggregation",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.OTHER,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public AnyTrueAggregation() {
    }

    @java.lang.Override()
    public Boolean applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply((input_.get("booleanList") != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(input_.get("booleanList"), new com.fasterxml.jackson.core.type.TypeReference<List<Boolean>>() {}) : null), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'AnyTrueAggregation'", e);
            return null;
        }
    }

    public Boolean apply(List<Boolean> booleanList, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'anyTrueAggregation'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long anyTrueAggregationStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments anyTrueAggregationArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            anyTrueAggregationArguments_.put("booleanList", booleanList);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, anyTrueAggregationArguments_);

            // Iterate and aggregate
            Boolean output_ = evaluate(booleanList, context_);

            // End decision 'anyTrueAggregation'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, anyTrueAggregationArguments_, output_, (System.currentTimeMillis() - anyTrueAggregationStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'anyTrueAggregation' evaluation", e);
            return null;
        }
    }

    protected Boolean evaluate(List<Boolean> booleanList, com.gs.dmn.runtime.ExecutionContext context_) {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        KeepInputanyTrue keepInputanyTrue = new KeepInputanyTrue();
        return booleanList.stream().anyMatch(booleanAnyTrue_iterator -> keepInputanyTrue.apply(booleanAnyTrue_iterator, context_));
    }
}
