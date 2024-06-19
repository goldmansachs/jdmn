
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "main"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "main",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class Main extends com.gs.dmn.runtime.JavaTimeDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "main",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public Main() {
    }

    @java.lang.Override()
    public java.lang.Number applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply((input_.get("n") != null ? number(input_.get("n")) : null), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'Main'", e);
            return null;
        }
    }

    public java.lang.Number apply(java.lang.Number n, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'main'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long mainStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments mainArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            mainArguments_.put("n", n);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, mainArguments_);

            // Evaluate decision 'main'
            java.lang.Number output_ = lambda.apply(n, context_);

            // End decision 'main'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, mainArguments_, output_, (System.currentTimeMillis() - mainStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'main' evaluation", e);
            return null;
        }
    }

    public com.gs.dmn.runtime.LambdaExpression<java.lang.Number> lambda =
        new com.gs.dmn.runtime.LambdaExpression<java.lang.Number>() {
            public java.lang.Number apply(Object... args_) {
                java.lang.Number n = 0 < args_.length ? (java.lang.Number) args_[0] : null;
                com.gs.dmn.runtime.ExecutionContext context_ = 1 < args_.length ? (com.gs.dmn.runtime.ExecutionContext) args_[1] : null;
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
                com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;

                return FACT.instance().apply(n, context_);
            }
        };
}
