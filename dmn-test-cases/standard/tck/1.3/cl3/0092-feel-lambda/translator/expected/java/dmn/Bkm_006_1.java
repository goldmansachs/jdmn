
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"bkm.ftl", "bkm_006_1"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "bkm_006_1",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.FUNCTION_DEFINITION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class Bkm_006_1 extends com.gs.dmn.runtime.JavaTimeDMNBaseDecision<com.gs.dmn.runtime.LambdaExpression<java.lang.Number>> {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "bkm_006_1",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
        com.gs.dmn.runtime.annotation.ExpressionKind.FUNCTION_DEFINITION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    private static class Bkm_006_1LazyHolder {
        static final Bkm_006_1 INSTANCE = new Bkm_006_1();
    }
    public static Bkm_006_1 instance() {
        return Bkm_006_1LazyHolder.INSTANCE;
    }

    private Bkm_006_1() {
    }

    @java.lang.Override()
    public com.gs.dmn.runtime.LambdaExpression<java.lang.Number> applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply((input_.get("a") != null ? number(input_.get("a")) : null), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'Bkm_006_1'", e);
            return null;
        }
    }

    @java.lang.Override()
    public com.gs.dmn.runtime.LambdaExpression<java.lang.Number> applyPojo(com.gs.dmn.runtime.ExecutableDRGElementInput input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply(((Bkm_006_1Input_)input_).getA(), context_);
        } catch (Exception e) {
            logError("Cannot apply element 'Bkm_006_1'", e);
            return null;
        }
    }

    public com.gs.dmn.runtime.LambdaExpression<java.lang.Number> apply(java.lang.Number a, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start BKM 'bkm_006_1'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long bkm_006_1StartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments bkm_006_1Arguments_ = new com.gs.dmn.runtime.listener.Arguments();
            bkm_006_1Arguments_.put("a", a);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, bkm_006_1Arguments_);

            // Evaluate BKM 'bkm_006_1'
            com.gs.dmn.runtime.LambdaExpression<java.lang.Number> output_ = lambda.apply(a, context_);

            // End BKM 'bkm_006_1'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, bkm_006_1Arguments_, output_, (System.currentTimeMillis() - bkm_006_1StartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'bkm_006_1' evaluation", e);
            return null;
        }
    }

    public com.gs.dmn.runtime.LambdaExpression<com.gs.dmn.runtime.LambdaExpression<java.lang.Number>> lambda =
        new com.gs.dmn.runtime.LambdaExpression<com.gs.dmn.runtime.LambdaExpression<java.lang.Number>>() {
            public com.gs.dmn.runtime.LambdaExpression<java.lang.Number> apply(Object... args_) {
                java.lang.Number a = 0 < args_.length ? (java.lang.Number) args_[0] : null;
                com.gs.dmn.runtime.ExecutionContext context_ = 1 < args_.length ? (com.gs.dmn.runtime.ExecutionContext) args_[1] : null;
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
                com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;

                return new com.gs.dmn.runtime.LambdaExpression<java.lang.Number>() {public java.lang.Number apply(Object... args_) {java.lang.Number b = (java.lang.Number)args_[0];return numericMultiply(a, b);}};
            }
        };
}
