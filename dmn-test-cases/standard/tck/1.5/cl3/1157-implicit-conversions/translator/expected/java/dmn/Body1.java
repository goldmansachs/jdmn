
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "Body 1"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "Body 1",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class Body1 extends com.gs.dmn.runtime.JavaTimeDMNBaseDecision<java.time.LocalDate> {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "Body 1",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public Body1() {
    }

    @java.lang.Override()
    public java.time.LocalDate applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply(context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'Body1'", e);
            return null;
        }
    }

    @java.lang.Override()
    public java.time.LocalDate applyPojo(com.gs.dmn.runtime.ExecutableDRGElementInput input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply(context_);
        } catch (Exception e) {
            logError("Cannot apply element 'Body1'", e);
            return null;
        }
    }

    public java.time.LocalDate apply(com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'Body 1'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long body1StartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments body1Arguments_ = new com.gs.dmn.runtime.listener.Arguments();
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, body1Arguments_);

            // Evaluate decision 'Body 1'
            java.time.LocalDate output_ = lambda.apply(context_);

            // End decision 'Body 1'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, body1Arguments_, output_, (System.currentTimeMillis() - body1StartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'Body 1' evaluation", e);
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

                return date(number("2000"), number("1"), number("2"));
            }
        };
}
