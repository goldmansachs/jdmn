
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "decision_012_4"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "decision_012_4",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class Decision_012_4 extends com.gs.dmn.runtime.JavaTimeDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "decision_012_4",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public Decision_012_4() {
    }

    @java.lang.Override()
    public String applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply(context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'Decision_012_4'", e);
            return null;
        }
    }

    public String apply(com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'decision_012_4'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long decision_012_4StartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments decision_012_4Arguments_ = new com.gs.dmn.runtime.listener.Arguments();
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, decision_012_4Arguments_);

            if (cache_.contains("decision_012_4")) {
                // Retrieve value from cache
                String output_ = (String)cache_.lookup("decision_012_4");

                // End decision 'decision_012_4'
                eventListener_.endDRGElement(DRG_ELEMENT_METADATA, decision_012_4Arguments_, output_, (System.currentTimeMillis() - decision_012_4StartTime_));

                return output_;
            } else {
                // Evaluate decision 'decision_012_4'
                String output_ = lambda.apply(context_);
                cache_.bind("decision_012_4", output_);

                // End decision 'decision_012_4'
                eventListener_.endDRGElement(DRG_ELEMENT_METADATA, decision_012_4Arguments_, output_, (System.currentTimeMillis() - decision_012_4StartTime_));

                return output_;
            }
        } catch (Exception e) {
            logError("Exception caught in 'decision_012_4' evaluation", e);
            return null;
        }
    }

    public com.gs.dmn.runtime.LambdaExpression<String> lambda =
        new com.gs.dmn.runtime.LambdaExpression<String>() {
            public String apply(Object... args_) {
                com.gs.dmn.runtime.ExecutionContext context_ = 0 < args_.length ? (com.gs.dmn.runtime.ExecutionContext) args_[0] : null;
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
                com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;

                return "I never get invoked";
            }
        };
}
