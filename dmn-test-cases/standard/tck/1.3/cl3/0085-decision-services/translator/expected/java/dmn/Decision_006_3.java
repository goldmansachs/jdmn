
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "decision_006_3"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "decision_006_3",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class Decision_006_3 extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "decision_006_3",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public Decision_006_3() {
    }

    public String apply(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply(context_.getAnnotations(), context_.getEventListener(), context_.getExternalFunctionExecutor(), context_.getCache());
        } catch (Exception e) {
            logError("Cannot apply decision 'Decision_006_3'", e);
            return null;
        }
    }

    public String apply(com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision 'decision_006_3'
            long decision_006_3StartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments decision_006_3Arguments_ = new com.gs.dmn.runtime.listener.Arguments();
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, decision_006_3Arguments_);

            if (cache_.contains("decision_006_3")) {
                // Retrieve value from cache
                String output_ = (String)cache_.lookup("decision_006_3");

                // End decision 'decision_006_3'
                eventListener_.endDRGElement(DRG_ELEMENT_METADATA, decision_006_3Arguments_, output_, (System.currentTimeMillis() - decision_006_3StartTime_));

                return output_;
            } else {
                // Evaluate decision 'decision_006_3'
                String output_ = lambda.apply(annotationSet_, eventListener_, externalExecutor_, cache_);
                cache_.bind("decision_006_3", output_);

                // End decision 'decision_006_3'
                eventListener_.endDRGElement(DRG_ELEMENT_METADATA, decision_006_3Arguments_, output_, (System.currentTimeMillis() - decision_006_3StartTime_));

                return output_;
            }
        } catch (Exception e) {
            logError("Exception caught in 'decision_006_3' evaluation", e);
            return null;
        }
    }

    public com.gs.dmn.runtime.LambdaExpression<String> lambda =
        new com.gs.dmn.runtime.LambdaExpression<String>() {
            public String apply(Object... args_) {
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = 0 < args_.length ? (com.gs.dmn.runtime.annotation.AnnotationSet) args_[0] : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = 1 < args_.length ? (com.gs.dmn.runtime.listener.EventListener) args_[1] : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = 2 < args_.length ? (com.gs.dmn.runtime.external.ExternalFunctionExecutor) args_[2] : null;
                com.gs.dmn.runtime.cache.Cache cache_ = 3 < args_.length ? (com.gs.dmn.runtime.cache.Cache) args_[3] : null;

                return "I never get invoked";
            }
        };
}
