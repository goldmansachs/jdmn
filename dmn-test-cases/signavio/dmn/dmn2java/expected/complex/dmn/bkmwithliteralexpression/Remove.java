
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
public class Remove extends com.gs.dmn.signavio.runtime.JavaTimeSignavioBaseDecision {
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

    @java.lang.Override()
    public List<String> applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply(input_.get("rgb2"), (input_.get("rgb2 list") != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(input_.get("rgb2 list"), new com.fasterxml.jackson.core.type.TypeReference<List<String>>() {}) : null), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'Remove'", e);
            return null;
        }
    }

    public List<String> apply(String rgb2, List<String> rgb2List, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'remove'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long removeStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments removeArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            removeArguments_.put("rgb2", rgb2);
            removeArguments_.put("rgb2 list", rgb2List);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, removeArguments_);

            // Evaluate decision 'remove'
            List<String> output_ = evaluate(rgb2, rgb2List, context_);

            // End decision 'remove'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, removeArguments_, output_, (System.currentTimeMillis() - removeStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'remove' evaluation", e);
            return null;
        }
    }

    protected List<String> evaluate(String rgb2, List<String> rgb2List, com.gs.dmn.runtime.ExecutionContext context_) {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        return remove(rgb2List, rgb2);
    }
}
