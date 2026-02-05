
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "Implicit Conversions FUNCT"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "Implicit Conversions FUNCT",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.INVOCATION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class ImplicitConversionsFUNCT extends com.gs.dmn.runtime.JavaTimeDMNBaseDecision<String> {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "Implicit Conversions FUNCT",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.INVOCATION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public ImplicitConversionsFUNCT() {
    }

    @java.lang.Override()
    public String applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply(context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'ImplicitConversionsFUNCT'", e);
            return null;
        }
    }

    @java.lang.Override()
    public String applyPojo(com.gs.dmn.runtime.ExecutableDRGElementInput input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply(context_);
        } catch (Exception e) {
            logError("Cannot apply element 'ImplicitConversionsFUNCT'", e);
            return null;
        }
    }

    @java.lang.Override()
    public String applyContext(com.gs.dmn.runtime.Context input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return applyPojo(new ImplicitConversionsFUNCTInput_(input_), context_);
        } catch (Exception e) {
            logError("Cannot apply element 'ImplicitConversionsFUNCT'", e);
            return null;
        }
    }

    public String apply(com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'Implicit Conversions FUNCT'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long implicitConversionsFUNCTStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments implicitConversionsFUNCTArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, implicitConversionsFUNCTArguments_);

            // Evaluate decision 'Implicit Conversions FUNCT'
            String output_ = lambda.apply(context_);

            // End decision 'Implicit Conversions FUNCT'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, implicitConversionsFUNCTArguments_, output_, (System.currentTimeMillis() - implicitConversionsFUNCTStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'Implicit Conversions FUNCT' evaluation", e);
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

                return FUNCT.instance().apply(asList(number("1")), ((java.lang.Number) asElement(asList(number("2")))), toDateTime(date("2000-02-01")), context_);
            }
        };
}
