
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"ds.ftl", "From Singleton List DS"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "From Singleton List DS",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION_SERVICE,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.OTHER,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class FromSingletonListDS extends com.gs.dmn.runtime.JavaTimeDMNBaseDecision<java.time.LocalDate> {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "From Singleton List DS",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION_SERVICE,
        com.gs.dmn.runtime.annotation.ExpressionKind.OTHER,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    private static class FromSingletonListDSLazyHolder {
        static final FromSingletonListDS INSTANCE = new FromSingletonListDS();
    }
    public static FromSingletonListDS instance() {
        return FromSingletonListDSLazyHolder.INSTANCE;
    }

    private final Body2 body2;

    private FromSingletonListDS() {
        this(new Body2());
    }

    private FromSingletonListDS(Body2 body2) {
        this.body2 = body2;
    }

    @java.lang.Override()
    public java.time.LocalDate applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply(context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'FromSingletonListDS'", e);
            return null;
        }
    }

    @java.lang.Override()
    public java.time.LocalDate applyPojo(com.gs.dmn.runtime.ExecutableDRGElementInput input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply(context_);
        } catch (Exception e) {
            logError("Cannot apply element 'FromSingletonListDS'", e);
            return null;
        }
    }

    @java.lang.Override()
    public java.time.LocalDate applyContext(com.gs.dmn.runtime.Context input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return applyPojo(new FromSingletonListDSInput_(input_), context_);
        } catch (Exception e) {
            logError("Cannot apply element 'FromSingletonListDS'", e);
            return null;
        }
    }

    public java.time.LocalDate apply(com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start DS 'From Singleton List DS'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long fromSingletonListDSStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments fromSingletonListDSArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, fromSingletonListDSArguments_);

            // Evaluate DS 'From Singleton List DS'
            java.time.LocalDate output_ = lambda.apply(context_);

            // End DS 'From Singleton List DS'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, fromSingletonListDSArguments_, output_, (System.currentTimeMillis() - fromSingletonListDSStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'From Singleton List DS' evaluation", e);
            return null;
        }
    }

    public com.gs.dmn.runtime.LambdaExpression<java.time.LocalDate> lambda =
        new com.gs.dmn.runtime.LambdaExpression<java.time.LocalDate>() {
            public java.time.LocalDate apply(Object... args_) {
                com.gs.dmn.runtime.ExecutionContext context_ = 0 < args_.length ? (com.gs.dmn.runtime.ExecutionContext) args_[0] : null;
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
                com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;

                // Apply child decisions
                List<java.time.LocalDate> body2 = FromSingletonListDS.this.body2.apply(context_);

                return ((java.time.LocalDate) asElement(body2));
            }
    };
}