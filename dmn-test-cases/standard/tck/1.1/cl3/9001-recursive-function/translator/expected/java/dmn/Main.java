
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
public class Main extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
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
    public java.math.BigDecimal apply(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply(input_.get("n"), context_.getAnnotations(), context_.getEventListener(), context_.getExternalFunctionExecutor(), context_.getCache());
        } catch (Exception e) {
            logError("Cannot apply decision 'Main'", e);
            return null;
        }
    }

    public java.math.BigDecimal apply(String n, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            return apply((n != null ? number(n) : null), annotationSet_, eventListener_, externalExecutor_, cache_);
        } catch (Exception e) {
            logError("Cannot apply decision 'Main'", e);
            return null;
        }
    }

    public java.math.BigDecimal apply(java.math.BigDecimal n, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision 'main'
            long mainStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments mainArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            mainArguments_.put("n", n);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, mainArguments_);

            // Evaluate decision 'main'
            java.math.BigDecimal output_ = lambda.apply(n, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision 'main'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, mainArguments_, output_, (System.currentTimeMillis() - mainStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'main' evaluation", e);
            return null;
        }
    }

    public com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal> lambda =
        new com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal>() {
            public java.math.BigDecimal apply(Object... args_) {
                java.math.BigDecimal n = 0 < args_.length ? (java.math.BigDecimal) args_[0] : null;
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = 1 < args_.length ? (com.gs.dmn.runtime.annotation.AnnotationSet) args_[1] : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = 2 < args_.length ? (com.gs.dmn.runtime.listener.EventListener) args_[2] : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = 3 < args_.length ? (com.gs.dmn.runtime.external.ExternalFunctionExecutor) args_[3] : null;
                com.gs.dmn.runtime.cache.Cache cache_ = 4 < args_.length ? (com.gs.dmn.runtime.cache.Cache) args_[4] : null;

                return FACT.instance().apply(n, annotationSet_, eventListener_, externalExecutor_, cache_);
            }
        };
}
