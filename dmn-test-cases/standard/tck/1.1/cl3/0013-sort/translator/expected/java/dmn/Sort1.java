
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "sort1"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "sort1",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class Sort1 extends com.gs.dmn.runtime.JavaTimeDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "sort1",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public Sort1() {
    }

    @java.lang.Override()
    public List<java.math.BigDecimal> applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply((input_.get("listA") != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(input_.get("listA"), new com.fasterxml.jackson.core.type.TypeReference<List<java.math.BigDecimal>>() {}) : null), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'Sort1'", e);
            return null;
        }
    }

    public List<java.math.BigDecimal> apply(List<java.math.BigDecimal> listA, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'sort1'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long sort1StartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments sort1Arguments_ = new com.gs.dmn.runtime.listener.Arguments();
            sort1Arguments_.put("listA", listA);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, sort1Arguments_);

            // Evaluate decision 'sort1'
            List<java.math.BigDecimal> output_ = lambda.apply(listA, context_);

            // End decision 'sort1'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, sort1Arguments_, output_, (System.currentTimeMillis() - sort1StartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'sort1' evaluation", e);
            return null;
        }
    }

    public com.gs.dmn.runtime.LambdaExpression<List<java.math.BigDecimal>> lambda =
        new com.gs.dmn.runtime.LambdaExpression<List<java.math.BigDecimal>>() {
            public List<java.math.BigDecimal> apply(Object... args_) {
                List<java.math.BigDecimal> listA = 0 < args_.length ? (List<java.math.BigDecimal>) args_[0] : null;
                com.gs.dmn.runtime.ExecutionContext context_ = 1 < args_.length ? (com.gs.dmn.runtime.ExecutionContext) args_[1] : null;
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
                com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;

                return sort(listA, new com.gs.dmn.runtime.LambdaExpression<Boolean>() {public Boolean apply(Object... args_) {java.math.BigDecimal x = (java.math.BigDecimal)args_[0]; java.math.BigDecimal y = (java.math.BigDecimal)args_[1];return numericGreaterThan(x, y);}});
            }
        };
}
