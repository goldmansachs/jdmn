
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"bkm.ftl", "bkm_017_1"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "bkm_017_1",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class Bkm_017_1 extends com.gs.dmn.runtime.JavaTimeDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "bkm_017_1",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    private static class Bkm_017_1LazyHolder {
        static final Bkm_017_1 INSTANCE = new Bkm_017_1();
    }
    public static Bkm_017_1 instance() {
        return Bkm_017_1LazyHolder.INSTANCE;
    }

    private Bkm_017_1() {
    }

    @java.lang.Override()
    public List<String> applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        throw new com.gs.dmn.runtime.DMNRuntimeException("Not all arguments can be serialized");
    }

    public List<String> apply(com.gs.dmn.runtime.LambdaExpression<Boolean> fn1, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start BKM 'bkm_017_1'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long bkm_017_1StartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments bkm_017_1Arguments_ = new com.gs.dmn.runtime.listener.Arguments();
            bkm_017_1Arguments_.put("fn1", fn1);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, bkm_017_1Arguments_);

            // Evaluate BKM 'bkm_017_1'
            List<String> output_ = lambda.apply(fn1, context_);

            // End BKM 'bkm_017_1'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, bkm_017_1Arguments_, output_, (System.currentTimeMillis() - bkm_017_1StartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'bkm_017_1' evaluation", e);
            return null;
        }
    }

    public com.gs.dmn.runtime.LambdaExpression<List<String>> lambda =
        new com.gs.dmn.runtime.LambdaExpression<List<String>>() {
            public List<String> apply(Object... args_) {
                com.gs.dmn.runtime.LambdaExpression<Boolean> fn1 = 0 < args_.length ? (com.gs.dmn.runtime.LambdaExpression<Boolean>) args_[0] : null;
                com.gs.dmn.runtime.ExecutionContext context_ = 1 < args_.length ? (com.gs.dmn.runtime.ExecutionContext) args_[1] : null;
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
                com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;

                return sort(asList("a", "z", "a", "z"), fn1);
            }
        };
}
