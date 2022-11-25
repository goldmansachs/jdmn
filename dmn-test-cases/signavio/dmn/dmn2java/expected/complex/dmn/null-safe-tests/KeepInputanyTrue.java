
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

    @java.lang.Override()
    public Boolean applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply((input_.get("booleanAnyTrue") != null ? Boolean.valueOf(input_.get("booleanAnyTrue")) : null), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'KeepInputanyTrue'", e);
            return null;
        }
    }

    public Boolean apply(Boolean booleanAnyTrue_iterator, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'keepInputanyTrue'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long keepInputanyTrueStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments keepInputanyTrueArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            keepInputanyTrueArguments_.put("booleanAnyTrue", booleanAnyTrue_iterator);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, keepInputanyTrueArguments_);

            // Evaluate decision 'keepInputanyTrue'
            Boolean output_ = evaluate(booleanAnyTrue_iterator, context_);

            // End decision 'keepInputanyTrue'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, keepInputanyTrueArguments_, output_, (System.currentTimeMillis() - keepInputanyTrueStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'keepInputanyTrue' evaluation", e);
            return null;
        }
    }

    protected Boolean evaluate(Boolean booleanAnyTrue_iterator, com.gs.dmn.runtime.ExecutionContext context_) {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        return booleanAnyTrue_iterator;
    }
}
