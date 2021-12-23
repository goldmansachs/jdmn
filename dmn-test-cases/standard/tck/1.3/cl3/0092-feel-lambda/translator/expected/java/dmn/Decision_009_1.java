
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "decision_009_1"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "decision_009_1",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.CONTEXT,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class Decision_009_1 extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "decision_009_1",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.CONTEXT,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public Decision_009_1() {
    }

    public java.math.BigDecimal apply(com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
    }

    public java.math.BigDecimal apply(com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision 'decision_009_1'
            long decision_009_1StartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments decision_009_1Arguments_ = new com.gs.dmn.runtime.listener.Arguments();
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, decision_009_1Arguments_);

            // Evaluate decision 'decision_009_1'
            java.math.BigDecimal output_ = lambda.apply(annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision 'decision_009_1'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, decision_009_1Arguments_, output_, (System.currentTimeMillis() - decision_009_1StartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'decision_009_1' evaluation", e);
            return null;
        }
    }

    public com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal> lambda =
        new com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal>() {
            public java.math.BigDecimal apply(Object... args) {
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = 0 < args.length ? (com.gs.dmn.runtime.annotation.AnnotationSet) args[0] : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = 1 < args.length ? (com.gs.dmn.runtime.listener.EventListener) args[1] : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = 2 < args.length ? (com.gs.dmn.runtime.external.ExternalFunctionExecutor) args[2] : null;
                com.gs.dmn.runtime.cache.Cache cache_ = 3 < args.length ? (com.gs.dmn.runtime.cache.Cache) args[3] : null;

                java.math.BigDecimal a = number("10");
                return Bkm_009_1.instance().apply(number("100"), annotationSet_, eventListener_, externalExecutor_, cache_).apply(number("2"), annotationSet_, eventListener_, externalExecutor_, cache_);
            }
        };
}
