
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"ds.ftl", "To Singleton List DS"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "To Singleton List DS",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION_SERVICE,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.OTHER,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class ToSingletonListDS extends com.gs.dmn.runtime.JavaTimeDMNBaseDecision<List<java.time.LocalDate>> {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "To Singleton List DS",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION_SERVICE,
        com.gs.dmn.runtime.annotation.ExpressionKind.OTHER,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    private static class ToSingletonListDSLazyHolder {
        static final ToSingletonListDS INSTANCE = new ToSingletonListDS();
    }
    public static ToSingletonListDS instance() {
        return ToSingletonListDSLazyHolder.INSTANCE;
    }

    private final Body1 body1;

    private ToSingletonListDS() {
        this(new Body1());
    }

    private ToSingletonListDS(Body1 body1) {
        this.body1 = body1;
    }

    @java.lang.Override()
    public List<java.time.LocalDate> applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply(context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'ToSingletonListDS'", e);
            return null;
        }
    }

    public List<java.time.LocalDate> apply(com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start DS 'To Singleton List DS'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long toSingletonListDSStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments toSingletonListDSArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, toSingletonListDSArguments_);

            // Evaluate DS 'To Singleton List DS'
            List<java.time.LocalDate> output_ = lambda.apply(context_);

            // End DS 'To Singleton List DS'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, toSingletonListDSArguments_, output_, (System.currentTimeMillis() - toSingletonListDSStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'To Singleton List DS' evaluation", e);
            return null;
        }
    }

    public com.gs.dmn.runtime.LambdaExpression<List<java.time.LocalDate>> lambda =
        new com.gs.dmn.runtime.LambdaExpression<List<java.time.LocalDate>>() {
            public List<java.time.LocalDate> apply(Object... args_) {
                com.gs.dmn.runtime.ExecutionContext context_ = 0 < args_.length ? (com.gs.dmn.runtime.ExecutionContext) args_[0] : null;
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
                com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;

                // Apply child decisions
                java.time.LocalDate body1 = ToSingletonListDS.this.body1.apply(context_);

                return asList(body1);
            }
    };
}