
import java.util.*;
import java.util.stream.Collectors;

@jakarta.annotation.Generated(value = {"signavio-decision.ftl", "keepInputallTrue"})
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

    @java.lang.Override()
    public Boolean applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply((input_.get("booleanAllTrue") != null ? Boolean.valueOf(input_.get("booleanAllTrue")) : null), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'KeepInputallTrue'", e);
            return null;
        }
    }

    public Boolean apply(Boolean booleanAllTrue_iterator, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'keepInputallTrue'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long keepInputallTrueStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments keepInputallTrueArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            keepInputallTrueArguments_.put("booleanAllTrue", booleanAllTrue_iterator);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, keepInputallTrueArguments_);

            // Evaluate decision 'keepInputallTrue'
            Boolean output_ = evaluate(booleanAllTrue_iterator, context_);

            // End decision 'keepInputallTrue'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, keepInputallTrueArguments_, output_, (System.currentTimeMillis() - keepInputallTrueStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'keepInputallTrue' evaluation", e);
            return null;
        }
    }

    protected Boolean evaluate(Boolean booleanAllTrue_iterator, com.gs.dmn.runtime.ExecutionContext context_) {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        return booleanAllTrue_iterator;
    }
}
