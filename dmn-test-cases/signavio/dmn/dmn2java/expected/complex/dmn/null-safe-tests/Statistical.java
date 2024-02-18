
import java.util.*;
import java.util.stream.Collectors;

@jakarta.annotation.Generated(value = {"signavio-decision.ftl", "statistical"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "statistical",
    label = "statistical",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class Statistical extends com.gs.dmn.signavio.runtime.DefaultSignavioBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "statistical",
        "statistical",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public Statistical() {
    }

    @java.lang.Override()
    public java.math.BigDecimal applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply((input_.get("numberList") != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(input_.get("numberList"), new com.fasterxml.jackson.core.type.TypeReference<List<java.math.BigDecimal>>() {}) : null), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'Statistical'", e);
            return null;
        }
    }

    public java.math.BigDecimal apply(List<java.math.BigDecimal> numberList, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'statistical'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long statisticalStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments statisticalArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            statisticalArguments_.put("numberList", numberList);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, statisticalArguments_);

            // Evaluate decision 'statistical'
            java.math.BigDecimal output_ = evaluate(numberList, context_);

            // End decision 'statistical'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, statisticalArguments_, output_, (System.currentTimeMillis() - statisticalStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'statistical' evaluation", e);
            return null;
        }
    }

    protected java.math.BigDecimal evaluate(List<java.math.BigDecimal> numberList, com.gs.dmn.runtime.ExecutionContext context_) {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        return numericMultiply(mode(numberList), numericDivide(numericAdd(avg(numberList), median(numberList)), numericAdd(min(numberList), max(numberList))));
    }
}
