
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "decision_011_1"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "decision_011_1",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class Decision_011_1 extends com.gs.dmn.runtime.JavaTimeDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "decision_011_1",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public Decision_011_1() {
    }

    @java.lang.Override()
    public java.math.BigDecimal applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply((input_.get("input_011_1") != null ? number(input_.get("input_011_1")) : null), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'Decision_011_1'", e);
            return null;
        }
    }

    public java.math.BigDecimal apply(java.math.BigDecimal input_011_1, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'decision_011_1'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long decision_011_1StartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments decision_011_1Arguments_ = new com.gs.dmn.runtime.listener.Arguments();
            decision_011_1Arguments_.put("input_011_1", input_011_1);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, decision_011_1Arguments_);

            // Evaluate decision 'decision_011_1'
            java.math.BigDecimal output_ = lambda.apply(input_011_1, context_);

            // End decision 'decision_011_1'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, decision_011_1Arguments_, output_, (System.currentTimeMillis() - decision_011_1StartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'decision_011_1' evaluation", e);
            return null;
        }
    }

    public com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal> lambda =
        new com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal>() {
            public java.math.BigDecimal apply(Object... args_) {
                java.math.BigDecimal input_011_1 = 0 < args_.length ? (java.math.BigDecimal) args_[0] : null;
                com.gs.dmn.runtime.ExecutionContext context_ = 1 < args_.length ? (com.gs.dmn.runtime.ExecutionContext) args_[1] : null;
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
                com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;

                return Bkm_011_1.instance().apply(new com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal>() {public java.math.BigDecimal apply(Object... args_) {java.math.BigDecimal a = (java.math.BigDecimal)args_[0];return numericMultiply(input_011_1, a);}}, new com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal>() {public java.math.BigDecimal apply(Object... args_) {java.math.BigDecimal b = (java.math.BigDecimal)args_[0];return numericMultiply(input_011_1, b);}}, context_);
            }
        };
}
