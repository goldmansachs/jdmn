
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "decision_018"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "decision_018",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class Decision_018 extends com.gs.dmn.runtime.JavaTimeDMNBaseDecision<List<String>> {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "decision_018",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public Decision_018() {
    }

    @java.lang.Override()
    public List<String> applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply(context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'Decision_018'", e);
            return null;
        }
    }

    @java.lang.Override()
    public List<String> applyPojo(com.gs.dmn.runtime.ExecutableDRGElementInput input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply(context_);
        } catch (Exception e) {
            logError("Cannot apply element 'Decision_018'", e);
            return null;
        }
    }

    @java.lang.Override()
    public List<String> applyContext(com.gs.dmn.runtime.Context input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return applyPojo(new Decision_018Input_(input_), context_);
        } catch (Exception e) {
            logError("Cannot apply element 'Decision_018'", e);
            return null;
        }
    }

    public List<String> apply(com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'decision_018'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long decision_018StartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments decision_018Arguments_ = new com.gs.dmn.runtime.listener.Arguments();
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, decision_018Arguments_);

            // Evaluate decision 'decision_018'
            List<String> output_ = lambda.apply(context_);

            // End decision 'decision_018'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, decision_018Arguments_, output_, (System.currentTimeMillis() - decision_018StartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'decision_018' evaluation", e);
            return null;
        }
    }

    public com.gs.dmn.runtime.LambdaExpression<List<String>> lambda =
        new com.gs.dmn.runtime.LambdaExpression<List<String>>() {
            public List<String> apply(Object... args_) {
                com.gs.dmn.runtime.ExecutionContext context_ = 0 < args_.length ? (com.gs.dmn.runtime.ExecutionContext) args_[0] : null;
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
                com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;

                return sort(asList("a", "z", "a", "z"), Bkm_018.instance().lambda);
            }
        };
}
