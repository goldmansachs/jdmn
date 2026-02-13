
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"ds.ftl", "From Date to Date and Time DS"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "https://kie.org/dmn/_F9BB5760-8BCA-4216-AAD9-8BD4FB70802D",
    name = "From Date to Date and Time DS",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION_SERVICE,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.OTHER,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class FromDateToDateAndTimeDS extends com.gs.dmn.runtime.JavaTimeDMNBaseDecision<java.time.temporal.TemporalAccessor> {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "https://kie.org/dmn/_F9BB5760-8BCA-4216-AAD9-8BD4FB70802D",
        "From Date to Date and Time DS",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION_SERVICE,
        com.gs.dmn.runtime.annotation.ExpressionKind.OTHER,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    private static class FromDateToDateAndTimeDSLazyHolder {
        static final FromDateToDateAndTimeDS INSTANCE = new FromDateToDateAndTimeDS();
    }
    public static FromDateToDateAndTimeDS instance() {
        return FromDateToDateAndTimeDSLazyHolder.INSTANCE;
    }

    private final Body3 body3;

    private FromDateToDateAndTimeDS() {
        this(new Body3());
    }

    private FromDateToDateAndTimeDS(Body3 body3) {
        this.body3 = body3;
    }

    @java.lang.Override()
    public java.time.temporal.TemporalAccessor applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply(context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'FromDateToDateAndTimeDS'", e);
            return null;
        }
    }

    @java.lang.Override()
    public java.time.temporal.TemporalAccessor applyPojo(com.gs.dmn.runtime.ExecutableDRGElementInput input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply(context_);
        } catch (Exception e) {
            logError("Cannot apply element 'FromDateToDateAndTimeDS'", e);
            return null;
        }
    }

    @java.lang.Override()
    public java.time.temporal.TemporalAccessor applyContext(com.gs.dmn.runtime.Context input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return applyPojo(new FromDateToDateAndTimeDSInput_(input_), context_);
        } catch (Exception e) {
            logError("Cannot apply element 'FromDateToDateAndTimeDS'", e);
            return null;
        }
    }

    public java.time.temporal.TemporalAccessor apply(com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start DS 'From Date to Date and Time DS'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long fromDateToDateAndTimeDSStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments fromDateToDateAndTimeDSArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, fromDateToDateAndTimeDSArguments_);

            // Evaluate DS 'From Date to Date and Time DS'
            java.time.temporal.TemporalAccessor output_ = lambda.apply(context_);

            // End DS 'From Date to Date and Time DS'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, fromDateToDateAndTimeDSArguments_, output_, (System.currentTimeMillis() - fromDateToDateAndTimeDSStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'From Date to Date and Time DS' evaluation", e);
            return null;
        }
    }

    public com.gs.dmn.runtime.LambdaExpression<java.time.temporal.TemporalAccessor> lambda =
        new com.gs.dmn.runtime.LambdaExpression<java.time.temporal.TemporalAccessor>() {
            public java.time.temporal.TemporalAccessor apply(Object... args_) {
                com.gs.dmn.runtime.ExecutionContext context_ = 0 < args_.length ? (com.gs.dmn.runtime.ExecutionContext) args_[0] : null;
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
                com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;

                // Apply child decisions
                java.time.LocalDate body3 = FromDateToDateAndTimeDS.this.body3.apply(context_);

                return toDateTime(body3);
            }
    };
}