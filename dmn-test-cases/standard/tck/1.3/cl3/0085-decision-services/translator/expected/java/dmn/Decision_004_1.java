
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "decision_004_1"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "decision_004_1",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class Decision_004_1 extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "decision_004_1",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public Decision_004_1() {
    }

    @java.lang.Override()
    public String apply(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply(context_.getAnnotations(), context_.getEventListener(), context_.getExternalFunctionExecutor(), context_.getCache());
        } catch (Exception e) {
            logError("Cannot apply decision 'Decision_004_1'", e);
            return null;
        }
    }

    public String apply(com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision 'decision_004_1'
            long decision_004_1StartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments decision_004_1Arguments_ = new com.gs.dmn.runtime.listener.Arguments();
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, decision_004_1Arguments_);

            // Evaluate decision 'decision_004_1'
            String output_ = lambda.apply(annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision 'decision_004_1'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, decision_004_1Arguments_, output_, (System.currentTimeMillis() - decision_004_1StartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'decision_004_1' evaluation", e);
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

                return DecisionService_004.instance().apply(annotationSet_, eventListener_, externalExecutor_, cache_);
            }
        };
}
