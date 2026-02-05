
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "Implicit Conversions CE"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "Implicit Conversions CE",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.CONTEXT,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class ImplicitConversionsCE extends com.gs.dmn.runtime.JavaTimeDMNBaseDecision<String> {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "Implicit Conversions CE",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.CONTEXT,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public ImplicitConversionsCE() {
    }

    @java.lang.Override()
    public String applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply(context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'ImplicitConversionsCE'", e);
            return null;
        }
    }

    @java.lang.Override()
    public String applyPojo(com.gs.dmn.runtime.ExecutableDRGElementInput input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply(context_);
        } catch (Exception e) {
            logError("Cannot apply element 'ImplicitConversionsCE'", e);
            return null;
        }
    }

    @java.lang.Override()
    public String applyContext(com.gs.dmn.runtime.Context input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return applyPojo(new ImplicitConversionsCEInput_(input_), context_);
        } catch (Exception e) {
            logError("Cannot apply element 'ImplicitConversionsCE'", e);
            return null;
        }
    }

    public String apply(com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'Implicit Conversions CE'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long implicitConversionsCEStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments implicitConversionsCEArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, implicitConversionsCEArguments_);

            // Evaluate decision 'Implicit Conversions CE'
            String output_ = lambda.apply(context_);

            // End decision 'Implicit Conversions CE'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, implicitConversionsCEArguments_, output_, (System.currentTimeMillis() - implicitConversionsCEStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'Implicit Conversions CE' evaluation", e);
            return null;
        }
    }

    public com.gs.dmn.runtime.LambdaExpression<String> lambda =
        new com.gs.dmn.runtime.LambdaExpression<String>() {
            public String apply(Object... args_) {
                com.gs.dmn.runtime.ExecutionContext context_ = 0 < args_.length ? (com.gs.dmn.runtime.ExecutionContext) args_[0] : null;
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
                com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;

                List<java.lang.Number> numberList = asList(number("1"));
                java.lang.Number number = ((java.lang.Number) asElement(asList(number("2"))));
                java.time.temporal.TemporalAccessor dateTime = toDateTime(date("2000-12-01"));
                return stringJoin(asList(string(numberList), string(number), string(dateTime)), "-");
            }
        };
}
