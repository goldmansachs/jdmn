
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"bkm.ftl", "bkm_003_1"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "bkm_003_1",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class Bkm_003_1 extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "bkm_003_1",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    private static class Bkm_003_1LazyHolder {
        static final Bkm_003_1 INSTANCE = new Bkm_003_1();
    }
    public static Bkm_003_1 instance() {
        return Bkm_003_1LazyHolder.INSTANCE;
    }

    private Bkm_003_1() {
    }

    public com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal> apply(com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start BKM 'bkm_003_1'
            long bkm_003_1StartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments bkm_003_1Arguments_ = new com.gs.dmn.runtime.listener.Arguments();
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, bkm_003_1Arguments_);

            // Evaluate BKM 'bkm_003_1'
            com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal> output_ = lambda.apply(annotationSet_, eventListener_, externalExecutor_, cache_);

            // End BKM 'bkm_003_1'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, bkm_003_1Arguments_, output_, (System.currentTimeMillis() - bkm_003_1StartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'bkm_003_1' evaluation", e);
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

                return new com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal>() {public java.math.BigDecimal apply(Object... args) {java.math.BigDecimal a = (java.math.BigDecimal)args[0];return numericAdd(number("1"), a);}};
            }
        };
}
