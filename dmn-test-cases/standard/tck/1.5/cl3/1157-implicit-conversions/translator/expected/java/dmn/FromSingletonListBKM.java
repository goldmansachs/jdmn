
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"bkm.ftl", "From Singleton List BKM"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "https://kie.org/dmn/_F9BB5760-8BCA-4216-AAD9-8BD4FB70802D",
    name = "From Singleton List BKM",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class FromSingletonListBKM extends com.gs.dmn.runtime.JavaTimeDMNBaseDecision<java.lang.Number> {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "From Singleton List BKM",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    private static class FromSingletonListBKMLazyHolder {
        static final FromSingletonListBKM INSTANCE = new FromSingletonListBKM();
    }
    public static FromSingletonListBKM instance() {
        return FromSingletonListBKMLazyHolder.INSTANCE;
    }

    private FromSingletonListBKM() {
    }

    @java.lang.Override()
    public java.lang.Number applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply(context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'FromSingletonListBKM'", e);
            return null;
        }
    }

    @java.lang.Override()
    public java.lang.Number applyPojo(com.gs.dmn.runtime.ExecutableDRGElementInput input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply(context_);
        } catch (Exception e) {
            logError("Cannot apply element 'FromSingletonListBKM'", e);
            return null;
        }
    }

    @java.lang.Override()
    public java.lang.Number applyContext(com.gs.dmn.runtime.Context input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return applyPojo(new FromSingletonListBKMInput_(input_), context_);
        } catch (Exception e) {
            logError("Cannot apply element 'FromSingletonListBKM'", e);
            return null;
        }
    }

    public java.lang.Number apply(com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start BKM 'From Singleton List BKM'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long fromSingletonListBKMStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments fromSingletonListBKMArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, fromSingletonListBKMArguments_);

            // Evaluate BKM 'From Singleton List BKM'
            java.lang.Number output_ = lambda.apply(context_);

            // End BKM 'From Singleton List BKM'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, fromSingletonListBKMArguments_, output_, (System.currentTimeMillis() - fromSingletonListBKMStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'From Singleton List BKM' evaluation", e);
            return null;
        }
    }

    public com.gs.dmn.runtime.LambdaExpression<java.lang.Number> lambda =
        new com.gs.dmn.runtime.LambdaExpression<java.lang.Number>() {
            public java.lang.Number apply(Object... args_) {
                com.gs.dmn.runtime.ExecutionContext context_ = 0 < args_.length ? (com.gs.dmn.runtime.ExecutionContext) args_[0] : null;
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
                com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;

                return ((java.lang.Number) asElement(asList(number("1"))));
            }
        };
}
