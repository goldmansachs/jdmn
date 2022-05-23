
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "decision_002_2"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "decision_002_2",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.FUNCTION_DEFINITION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class Decision_002_2 extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "decision_002_2",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.FUNCTION_DEFINITION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public Decision_002_2() {
    }

    @java.lang.Override()
    public com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal> apply(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply(context_.getAnnotations(), context_.getEventListener(), context_.getExternalFunctionExecutor(), context_.getCache());
        } catch (Exception e) {
            logError("Cannot apply decision 'Decision_002_2'", e);
            return null;
        }
    }

    public com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal> apply(com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision 'decision_002_2'
            long decision_002_2StartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments decision_002_2Arguments_ = new com.gs.dmn.runtime.listener.Arguments();
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, decision_002_2Arguments_);

            // Evaluate decision 'decision_002_2'
            com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal> output_ = lambda.apply(annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision 'decision_002_2'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, decision_002_2Arguments_, output_, (System.currentTimeMillis() - decision_002_2StartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'decision_002_2' evaluation", e);
            return null;
        }
    }

    public com.gs.dmn.runtime.LambdaExpression<com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal>> lambda =
        new com.gs.dmn.runtime.LambdaExpression<com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal>>() {
            public com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal> apply(Object... args_) {
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = 0 < args_.length ? (com.gs.dmn.runtime.annotation.AnnotationSet) args_[0] : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = 1 < args_.length ? (com.gs.dmn.runtime.listener.EventListener) args_[1] : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = 2 < args_.length ? (com.gs.dmn.runtime.external.ExternalFunctionExecutor) args_[2] : null;
                com.gs.dmn.runtime.cache.Cache cache_ = 3 < args_.length ? (com.gs.dmn.runtime.cache.Cache) args_[3] : null;

                return new com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal>() {public java.math.BigDecimal apply(Object... args_) {java.math.BigDecimal a = (java.math.BigDecimal)args_[0];return numericAdd(number("1"), a);}};
            }
        };
}
