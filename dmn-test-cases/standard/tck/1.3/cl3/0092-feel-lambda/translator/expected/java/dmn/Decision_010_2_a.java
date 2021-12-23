
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "decision_010_2_a"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "decision_010_2_a",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class Decision_010_2_a extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "decision_010_2_a",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public Decision_010_2_a() {
    }

    public com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal> apply(com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
    }

    public com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal> apply(com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision 'decision_010_2_a'
            long decision_010_2_aStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments decision_010_2_aArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, decision_010_2_aArguments_);

            // Evaluate decision 'decision_010_2_a'
            com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal> output_ = lambda.apply(annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision 'decision_010_2_a'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, decision_010_2_aArguments_, output_, (System.currentTimeMillis() - decision_010_2_aStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'decision_010_2_a' evaluation", e);
            return null;
        }
    }

    public com.gs.dmn.runtime.LambdaExpression<com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal>> lambda =
        new com.gs.dmn.runtime.LambdaExpression<com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal>>() {
            public com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal> apply(Object... args) {
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = 0 < args.length ? (com.gs.dmn.runtime.annotation.AnnotationSet) args[0] : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = 1 < args.length ? (com.gs.dmn.runtime.listener.EventListener) args[1] : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = 2 < args.length ? (com.gs.dmn.runtime.external.ExternalFunctionExecutor) args[2] : null;
                com.gs.dmn.runtime.cache.Cache cache_ = 3 < args.length ? (com.gs.dmn.runtime.cache.Cache) args[3] : null;

                return Bkm_010_1_a.instance().apply(number("2"), annotationSet_, eventListener_, externalExecutor_, cache_).apply(number("3"), annotationSet_, eventListener_, externalExecutor_, cache_).apply(number("4"), annotationSet_, eventListener_, externalExecutor_, cache_);
            }
        };
}
