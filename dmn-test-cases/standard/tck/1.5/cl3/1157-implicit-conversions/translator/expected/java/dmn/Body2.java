
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "Body 2"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "Body 2",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class Body2 extends com.gs.dmn.runtime.JavaTimeDMNBaseDecision<List<java.time.LocalDate>> {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "Body 2",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public Body2() {
    }

    @java.lang.Override()
    public List<java.time.LocalDate> applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply(context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'Body2'", e);
            return null;
        }
    }

    public List<java.time.LocalDate> apply(com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'Body 2'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long body2StartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments body2Arguments_ = new com.gs.dmn.runtime.listener.Arguments();
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, body2Arguments_);

            // Evaluate decision 'Body 2'
            List<java.time.LocalDate> output_ = lambda.apply(context_);

            // End decision 'Body 2'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, body2Arguments_, output_, (System.currentTimeMillis() - body2StartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'Body 2' evaluation", e);
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

                return asList(date(number("2000"), number("1"), number("2")));
            }
        };
}
