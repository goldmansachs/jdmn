
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"bkm.ftl", "bkm_013_1"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "http://www.montera.com.au/spec/DMN/0092-feel-lambda",
    name = "bkm_013_1",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class Bkm_013_1 extends com.gs.dmn.runtime.JavaTimeDMNBaseDecision<java.lang.Number> {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "bkm_013_1",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    private static class Bkm_013_1LazyHolder {
        static final Bkm_013_1 INSTANCE = new Bkm_013_1();
    }
    public static Bkm_013_1 instance() {
        return Bkm_013_1LazyHolder.INSTANCE;
    }

    private Bkm_013_1() {
    }

    @java.lang.Override()
    public java.lang.Number applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        throw new com.gs.dmn.runtime.DMNRuntimeException("Not all arguments can be serialized");
    }

    @java.lang.Override()
    public java.lang.Number applyPojo(com.gs.dmn.runtime.ExecutableDRGElementInput input_, com.gs.dmn.runtime.ExecutionContext context_) {
        throw new com.gs.dmn.runtime.DMNRuntimeException("Not all arguments can be serialized");
    }

    @java.lang.Override()
    public java.lang.Number applyContext(com.gs.dmn.runtime.Context input_, com.gs.dmn.runtime.ExecutionContext context_) {
        throw new com.gs.dmn.runtime.DMNRuntimeException("Not all arguments can be serialized");
    }

    public java.lang.Number apply(com.gs.dmn.runtime.LambdaExpression<java.lang.Number> fn1, com.gs.dmn.runtime.LambdaExpression<java.lang.Number> fn2, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start BKM 'bkm_013_1'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long bkm_013_1StartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments bkm_013_1Arguments_ = new com.gs.dmn.runtime.listener.Arguments();
            bkm_013_1Arguments_.put("fn1", fn1);
            bkm_013_1Arguments_.put("fn2", fn2);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, bkm_013_1Arguments_);

            // Evaluate BKM 'bkm_013_1'
            java.lang.Number output_ = lambda.apply(fn1, fn2, context_);

            // End BKM 'bkm_013_1'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, bkm_013_1Arguments_, output_, (System.currentTimeMillis() - bkm_013_1StartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'bkm_013_1' evaluation", e);
            return null;
        }
    }

    public com.gs.dmn.runtime.LambdaExpression<java.lang.Number> lambda =
        new com.gs.dmn.runtime.LambdaExpression<java.lang.Number>() {
            public java.lang.Number apply(Object... args_) {
                com.gs.dmn.runtime.LambdaExpression<java.lang.Number> fn1 = 0 < args_.length ? (com.gs.dmn.runtime.LambdaExpression<java.lang.Number>) args_[0] : null;
                com.gs.dmn.runtime.LambdaExpression<java.lang.Number> fn2 = 1 < args_.length ? (com.gs.dmn.runtime.LambdaExpression<java.lang.Number>) args_[1] : null;
                com.gs.dmn.runtime.ExecutionContext context_ = 2 < args_.length ? (com.gs.dmn.runtime.ExecutionContext) args_[2] : null;
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
                com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;

                return numericMultiply(fn1.apply(number("5"), context_), fn2.apply(number("10"), context_));
            }
        };
}
