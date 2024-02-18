
import java.util.*;
import java.util.stream.Collectors;

@jakarta.annotation.Generated(value = {"bkm.ftl", "Circumference"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "Circumference",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class Circumference extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "Circumference",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    private static class CircumferenceLazyHolder {
        static final Circumference INSTANCE = new Circumference();
    }
    public static Circumference instance() {
        return CircumferenceLazyHolder.INSTANCE;
    }

    private Circumference() {
    }

    @java.lang.Override()
    public java.math.BigDecimal applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply((input_.get("radius") != null ? number(input_.get("radius")) : null), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'Circumference'", e);
            return null;
        }
    }

    public java.math.BigDecimal apply(java.math.BigDecimal radius, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start BKM 'Circumference'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long circumferenceStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments circumferenceArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            circumferenceArguments_.put("radius", radius);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, circumferenceArguments_);

            // Evaluate BKM 'Circumference'
            java.math.BigDecimal output_ = lambda.apply(radius, context_);

            // End BKM 'Circumference'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, circumferenceArguments_, output_, (System.currentTimeMillis() - circumferenceStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'Circumference' evaluation", e);
            return null;
        }
    }

    public com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal> lambda =
        new com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal>() {
            public java.math.BigDecimal apply(Object... args_) {
                java.math.BigDecimal radius = 0 < args_.length ? (java.math.BigDecimal) args_[0] : null;
                com.gs.dmn.runtime.ExecutionContext context_ = 1 < args_.length ? (com.gs.dmn.runtime.ExecutionContext) args_[1] : null;
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
                com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;

                return numericMultiply(numericMultiply(number("2"), number("3.141592")), radius);
            }
        };
}
