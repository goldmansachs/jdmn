
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"bkm.ftl", "To Singleton List BKM"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "https://kie.org/dmn/_F9BB5760-8BCA-4216-AAD9-8BD4FB70802D",
    name = "To Singleton List BKM",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class ToSingletonListBKM extends com.gs.dmn.runtime.JavaTimeDMNBaseDecision<List<java.lang.Number>> {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "To Singleton List BKM",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    private static class ToSingletonListBKMLazyHolder {
        static final ToSingletonListBKM INSTANCE = new ToSingletonListBKM();
    }
    public static ToSingletonListBKM instance() {
        return ToSingletonListBKMLazyHolder.INSTANCE;
    }

    private ToSingletonListBKM() {
    }

    @java.lang.Override()
    public List<java.lang.Number> applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply(context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'ToSingletonListBKM'", e);
            return null;
        }
    }

    @java.lang.Override()
    public List<java.lang.Number> applyPojo(com.gs.dmn.runtime.ExecutableDRGElementInput input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply(context_);
        } catch (Exception e) {
            logError("Cannot apply element 'ToSingletonListBKM'", e);
            return null;
        }
    }

    @java.lang.Override()
    public List<java.lang.Number> applyContext(com.gs.dmn.runtime.Context input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return applyPojo(new ToSingletonListBKMInput_(input_), context_);
        } catch (Exception e) {
            logError("Cannot apply element 'ToSingletonListBKM'", e);
            return null;
        }
    }

    public List<java.lang.Number> apply(com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start BKM 'To Singleton List BKM'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long toSingletonListBKMStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments toSingletonListBKMArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, toSingletonListBKMArguments_);

            // Evaluate BKM 'To Singleton List BKM'
            List<java.lang.Number> output_ = lambda.apply(context_);

            // End BKM 'To Singleton List BKM'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, toSingletonListBKMArguments_, output_, (System.currentTimeMillis() - toSingletonListBKMStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'To Singleton List BKM' evaluation", e);
            return null;
        }
    }

    public com.gs.dmn.runtime.LambdaExpression<List<java.lang.Number>> lambda =
        new com.gs.dmn.runtime.LambdaExpression<List<java.lang.Number>>() {
            public List<java.lang.Number> apply(Object... args_) {
                com.gs.dmn.runtime.ExecutionContext context_ = 0 < args_.length ? (com.gs.dmn.runtime.ExecutionContext) args_[0] : null;
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
                com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;

                return asList(number("1"));
            }
        };
}
