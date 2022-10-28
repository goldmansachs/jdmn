
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"bkm.ftl", "bkm_012_1"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "bkm_012_1",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class Bkm_012_1 extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "bkm_012_1",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    private static class Bkm_012_1LazyHolder {
        static final Bkm_012_1 INSTANCE = new Bkm_012_1();
    }
    public static Bkm_012_1 instance() {
        return Bkm_012_1LazyHolder.INSTANCE;
    }

    private Bkm_012_1() {
    }

    @java.lang.Override()
    public java.math.BigDecimal applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        throw new com.gs.dmn.runtime.DMNRuntimeException("Not all arguments can be serialized");
    }

    public java.math.BigDecimal apply(com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal> fn1, com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal> fn2, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start BKM 'bkm_012_1'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long bkm_012_1StartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments bkm_012_1Arguments_ = new com.gs.dmn.runtime.listener.Arguments();
            bkm_012_1Arguments_.put("fn1", fn1);
            bkm_012_1Arguments_.put("fn2", fn2);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, bkm_012_1Arguments_);

            // Evaluate BKM 'bkm_012_1'
            java.math.BigDecimal output_ = lambda.apply(fn1, fn2, context_);

            // End BKM 'bkm_012_1'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, bkm_012_1Arguments_, output_, (System.currentTimeMillis() - bkm_012_1StartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'bkm_012_1' evaluation", e);
            return null;
        }
    }

    public com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal> lambda =
        new com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal>() {
            public java.math.BigDecimal apply(Object... args_) {
                com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal> fn1 = 0 < args_.length ? (com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal>) args_[0] : null;
                com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal> fn2 = 1 < args_.length ? (com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal>) args_[1] : null;
                com.gs.dmn.runtime.ExecutionContext context_ = 2 < args_.length ? (com.gs.dmn.runtime.ExecutionContext) args_[2] : null;
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
                com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;

                return numericMultiply(fn1.apply(number("5"), context_), fn2.apply(number("10"), context_));
            }
        };
}
