
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "Decision2"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "Decision2",
    label = "Decision2",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class Decision2 extends com.gs.dmn.runtime.JavaTimeDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "Decision2",
        "Decision2",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public Decision2() {
    }

    @java.lang.Override()
    public java.lang.Number applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply((input_.get("InputDate") != null ? date(input_.get("InputDate")) : null), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'Decision2'", e);
            return null;
        }
    }

    public java.lang.Number apply(java.time.LocalDate inputDate, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'Decision2'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long decision2StartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments decision2Arguments_ = new com.gs.dmn.runtime.listener.Arguments();
            decision2Arguments_.put("InputDate", inputDate);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, decision2Arguments_);

            // Evaluate decision 'Decision2'
            java.lang.Number output_ = lambda.apply(inputDate, context_);

            // End decision 'Decision2'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, decision2Arguments_, output_, (System.currentTimeMillis() - decision2StartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'Decision2' evaluation", e);
            return null;
        }
    }

    public com.gs.dmn.runtime.LambdaExpression<java.lang.Number> lambda =
        new com.gs.dmn.runtime.LambdaExpression<java.lang.Number>() {
            public java.lang.Number apply(Object... args_) {
                java.time.LocalDate inputDate = 0 < args_.length ? (java.time.LocalDate) args_[0] : null;
                com.gs.dmn.runtime.ExecutionContext context_ = 1 < args_.length ? (com.gs.dmn.runtime.ExecutionContext) args_[1] : null;
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
                com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;

                return com.gs.dmn.signavio.feel.lib.JavaTimeSignavioLib.INSTANCE.day(inputDate);
            }
        };
}
