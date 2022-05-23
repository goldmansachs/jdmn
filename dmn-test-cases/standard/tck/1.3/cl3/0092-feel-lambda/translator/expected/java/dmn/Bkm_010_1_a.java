
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"bkm.ftl", "bkm_010_1_a"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "bkm_010_1_a",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.FUNCTION_DEFINITION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class Bkm_010_1_a extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "bkm_010_1_a",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
        com.gs.dmn.runtime.annotation.ExpressionKind.FUNCTION_DEFINITION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    private static class Bkm_010_1_aLazyHolder {
        static final Bkm_010_1_a INSTANCE = new Bkm_010_1_a();
    }
    public static Bkm_010_1_a instance() {
        return Bkm_010_1_aLazyHolder.INSTANCE;
    }

    private Bkm_010_1_a() {
    }

    @java.lang.Override()
    public com.gs.dmn.runtime.LambdaExpression<com.gs.dmn.runtime.LambdaExpression<com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal>>> apply(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply((input_.get("a") != null ? number(input_.get("a")) : null), context_.getAnnotations(), context_.getEventListener(), context_.getExternalFunctionExecutor(), context_.getCache());
        } catch (Exception e) {
            logError("Cannot apply decision 'Bkm_010_1_a'", e);
            return null;
        }
    }

    public com.gs.dmn.runtime.LambdaExpression<com.gs.dmn.runtime.LambdaExpression<com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal>>> apply(java.math.BigDecimal a, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start BKM 'bkm_010_1_a'
            long bkm_010_1_aStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments bkm_010_1_aArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            bkm_010_1_aArguments_.put("a", a);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, bkm_010_1_aArguments_);

            // Evaluate BKM 'bkm_010_1_a'
            com.gs.dmn.runtime.LambdaExpression<com.gs.dmn.runtime.LambdaExpression<com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal>>> output_ = lambda.apply(a, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End BKM 'bkm_010_1_a'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, bkm_010_1_aArguments_, output_, (System.currentTimeMillis() - bkm_010_1_aStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'bkm_010_1_a' evaluation", e);
            return null;
        }
    }

    public com.gs.dmn.runtime.LambdaExpression<com.gs.dmn.runtime.LambdaExpression<com.gs.dmn.runtime.LambdaExpression<com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal>>>> lambda =
        new com.gs.dmn.runtime.LambdaExpression<com.gs.dmn.runtime.LambdaExpression<com.gs.dmn.runtime.LambdaExpression<com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal>>>>() {
            public com.gs.dmn.runtime.LambdaExpression<com.gs.dmn.runtime.LambdaExpression<com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal>>> apply(Object... args_) {
                java.math.BigDecimal a = 0 < args_.length ? (java.math.BigDecimal) args_[0] : null;
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = 1 < args_.length ? (com.gs.dmn.runtime.annotation.AnnotationSet) args_[1] : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = 2 < args_.length ? (com.gs.dmn.runtime.listener.EventListener) args_[2] : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = 3 < args_.length ? (com.gs.dmn.runtime.external.ExternalFunctionExecutor) args_[3] : null;
                com.gs.dmn.runtime.cache.Cache cache_ = 4 < args_.length ? (com.gs.dmn.runtime.cache.Cache) args_[4] : null;

                return new com.gs.dmn.runtime.LambdaExpression<com.gs.dmn.runtime.LambdaExpression<com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal>>>() {public com.gs.dmn.runtime.LambdaExpression<com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal>> apply(Object... args_) {java.math.BigDecimal b = (java.math.BigDecimal)args_[0];return new com.gs.dmn.runtime.LambdaExpression<com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal>>() {public com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal> apply(Object... args_) {java.math.BigDecimal c = (java.math.BigDecimal)args_[0];return new com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal>() {public java.math.BigDecimal apply(Object... args_) {java.math.BigDecimal d = (java.math.BigDecimal)args_[0];return numericMultiply(numericMultiply(numericMultiply(a, b), c), d);}};}};}};
            }
        };
}
