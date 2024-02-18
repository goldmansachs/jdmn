
import java.util.*;
import java.util.stream.Collectors;

@jakarta.annotation.Generated(value = {"decision.ftl", "decision_001_2"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "decision_001_2",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class Decision_001_2 extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "decision_001_2",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public Decision_001_2() {
    }

    @java.lang.Override()
    public com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal> applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply(context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'Decision_001_2'", e);
            return null;
        }
    }

    public com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal> apply(com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'decision_001_2'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long decision_001_2StartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments decision_001_2Arguments_ = new com.gs.dmn.runtime.listener.Arguments();
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, decision_001_2Arguments_);

            // Evaluate decision 'decision_001_2'
            com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal> output_ = lambda.apply(context_);

            // End decision 'decision_001_2'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, decision_001_2Arguments_, output_, (System.currentTimeMillis() - decision_001_2StartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'decision_001_2' evaluation", e);
            return null;
        }
    }

    public com.gs.dmn.runtime.LambdaExpression<com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal>> lambda =
        new com.gs.dmn.runtime.LambdaExpression<com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal>>() {
            public com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal> apply(Object... args_) {
                com.gs.dmn.runtime.ExecutionContext context_ = 0 < args_.length ? (com.gs.dmn.runtime.ExecutionContext) args_[0] : null;
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
                com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;

                return new com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal>() {public java.math.BigDecimal apply(Object... args_) {java.math.BigDecimal a = (java.math.BigDecimal)args_[0];return numericAdd(number("1"), a);}};
            }
        };
}
