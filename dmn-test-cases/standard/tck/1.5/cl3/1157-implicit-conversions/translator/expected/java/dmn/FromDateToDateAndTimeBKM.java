
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"bkm.ftl", "From Date To Date and Time BKM"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "https://kie.org/dmn/_F9BB5760-8BCA-4216-AAD9-8BD4FB70802D",
    name = "From Date To Date and Time BKM",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class FromDateToDateAndTimeBKM extends com.gs.dmn.runtime.JavaTimeDMNBaseDecision<java.time.temporal.TemporalAccessor> {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "https://kie.org/dmn/_F9BB5760-8BCA-4216-AAD9-8BD4FB70802D",
        "From Date To Date and Time BKM",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    private static class FromDateToDateAndTimeBKMLazyHolder {
        static final FromDateToDateAndTimeBKM INSTANCE = new FromDateToDateAndTimeBKM();
    }
    public static FromDateToDateAndTimeBKM instance() {
        return FromDateToDateAndTimeBKMLazyHolder.INSTANCE;
    }

    private FromDateToDateAndTimeBKM() {
    }

    @java.lang.Override()
    public java.time.temporal.TemporalAccessor applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply(context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'FromDateToDateAndTimeBKM'", e);
            return null;
        }
    }

    @java.lang.Override()
    public java.time.temporal.TemporalAccessor applyPojo(com.gs.dmn.runtime.ExecutableDRGElementInput input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply(context_);
        } catch (Exception e) {
            logError("Cannot apply element 'FromDateToDateAndTimeBKM'", e);
            return null;
        }
    }

    @java.lang.Override()
    public java.time.temporal.TemporalAccessor applyContext(com.gs.dmn.runtime.Context input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return applyPojo(new FromDateToDateAndTimeBKMInput_(input_), context_);
        } catch (Exception e) {
            logError("Cannot apply element 'FromDateToDateAndTimeBKM'", e);
            return null;
        }
    }

    public java.time.temporal.TemporalAccessor apply(com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start BKM 'From Date To Date and Time BKM'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long fromDateToDateAndTimeBKMStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments fromDateToDateAndTimeBKMArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, fromDateToDateAndTimeBKMArguments_);

            // Evaluate BKM 'From Date To Date and Time BKM'
            java.time.temporal.TemporalAccessor output_ = lambda.apply(context_);

            // End BKM 'From Date To Date and Time BKM'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, fromDateToDateAndTimeBKMArguments_, output_, (System.currentTimeMillis() - fromDateToDateAndTimeBKMStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'From Date To Date and Time BKM' evaluation", e);
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

                return toDateTime(date(number("2000"), number("1"), number("2")));
            }
        };
}
