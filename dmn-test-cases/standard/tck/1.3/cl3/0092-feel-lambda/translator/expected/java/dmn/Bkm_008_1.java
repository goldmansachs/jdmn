
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"bkm.ftl", "bkm_008_1"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "bkm_008_1",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class Bkm_008_1 extends com.gs.dmn.runtime.JavaTimeDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "bkm_008_1",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    private static class Bkm_008_1LazyHolder {
        static final Bkm_008_1 INSTANCE = new Bkm_008_1();
    }
    public static Bkm_008_1 instance() {
        return Bkm_008_1LazyHolder.INSTANCE;
    }

    private Bkm_008_1() {
    }

    @java.lang.Override()
    public com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal> applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply((input_.get("a") != null ? number(input_.get("a")) : null), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'Bkm_008_1'", e);
            return null;
        }
    }

    public com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal> apply(java.math.BigDecimal a, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start BKM 'bkm_008_1'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long bkm_008_1StartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments bkm_008_1Arguments_ = new com.gs.dmn.runtime.listener.Arguments();
            bkm_008_1Arguments_.put("a", a);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, bkm_008_1Arguments_);

            // Evaluate BKM 'bkm_008_1'
            com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal> output_ = lambda.apply(a, context_);

            // End BKM 'bkm_008_1'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, bkm_008_1Arguments_, output_, (System.currentTimeMillis() - bkm_008_1StartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'bkm_008_1' evaluation", e);
            return null;
        }
    }

    public com.gs.dmn.runtime.LambdaExpression<com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal>> lambda =
        new com.gs.dmn.runtime.LambdaExpression<com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal>>() {
            public com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal> apply(Object... args_) {
                java.math.BigDecimal a = 0 < args_.length ? (java.math.BigDecimal) args_[0] : null;
                com.gs.dmn.runtime.ExecutionContext context_ = 1 < args_.length ? (com.gs.dmn.runtime.ExecutionContext) args_[1] : null;
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
                com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;

                return new com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal>() {public java.math.BigDecimal apply(Object... args_) {java.math.BigDecimal a = (java.math.BigDecimal)args_[0]; java.math.BigDecimal b = (java.math.BigDecimal)args_[1];return numericMultiply(a, b);}};
            }
        };
}
