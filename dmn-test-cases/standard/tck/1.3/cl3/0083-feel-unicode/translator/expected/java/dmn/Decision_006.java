
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "decision_006"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "http://www.montera.com.au/spec/DMN/0083-feel-unicode",
    name = "decision_006",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class Decision_006 extends com.gs.dmn.runtime.JavaTimeDMNBaseDecision<com.gs.dmn.runtime.Context> {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "decision_006",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public Decision_006() {
    }

    @java.lang.Override()
    public com.gs.dmn.runtime.Context applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply(context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'Decision_006'", e);
            return null;
        }
    }

    @java.lang.Override()
    public com.gs.dmn.runtime.Context applyPojo(com.gs.dmn.runtime.ExecutableDRGElementInput input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply(context_);
        } catch (Exception e) {
            logError("Cannot apply element 'Decision_006'", e);
            return null;
        }
    }

    @java.lang.Override()
    public com.gs.dmn.runtime.Context applyContext(com.gs.dmn.runtime.Context input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return applyPojo(new Decision_006Input_(input_), context_);
        } catch (Exception e) {
            logError("Cannot apply element 'Decision_006'", e);
            return null;
        }
    }

    public com.gs.dmn.runtime.Context apply(com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'decision_006'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long decision_006StartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments decision_006Arguments_ = new com.gs.dmn.runtime.listener.Arguments();
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, decision_006Arguments_);

            // Evaluate decision 'decision_006'
            com.gs.dmn.runtime.Context output_ = lambda.apply(context_);

            // End decision 'decision_006'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, decision_006Arguments_, output_, (System.currentTimeMillis() - decision_006StartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'decision_006' evaluation", e);
            return null;
        }
    }

    public com.gs.dmn.runtime.LambdaExpression<com.gs.dmn.runtime.Context> lambda =
        new com.gs.dmn.runtime.LambdaExpression<com.gs.dmn.runtime.Context>() {
            public com.gs.dmn.runtime.Context apply(Object... args_) {
                com.gs.dmn.runtime.ExecutionContext context_ = 0 < args_.length ? (com.gs.dmn.runtime.ExecutionContext) args_[0] : null;
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
                com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;

                return new com.gs.dmn.runtime.Context().add("🐎", "bar");
            }
        };
}
