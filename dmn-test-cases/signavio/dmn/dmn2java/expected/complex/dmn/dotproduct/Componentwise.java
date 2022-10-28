
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"signavio-decision.ftl", "componentwise"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "componentwise",
    label = "Componentwise",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class Componentwise extends com.gs.dmn.signavio.runtime.DefaultSignavioBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "componentwise",
        "Componentwise",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public Componentwise() {
    }

    @java.lang.Override()
    public List<type.Componentwise> applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply((input_.get("A") != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(input_.get("A"), new com.fasterxml.jackson.core.type.TypeReference<List<java.math.BigDecimal>>() {}) : null), (input_.get("B") != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(input_.get("B"), new com.fasterxml.jackson.core.type.TypeReference<List<java.math.BigDecimal>>() {}) : null), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'Componentwise'", e);
            return null;
        }
    }

    public List<type.Componentwise> apply(List<java.math.BigDecimal> a, List<java.math.BigDecimal> b, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'componentwise'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long componentwiseStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments componentwiseArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            componentwiseArguments_.put("A", a);
            componentwiseArguments_.put("B", b);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, componentwiseArguments_);

            // Evaluate decision 'componentwise'
            List<type.Componentwise> output_ = evaluate(a, b, context_);

            // End decision 'componentwise'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, componentwiseArguments_, output_, (System.currentTimeMillis() - componentwiseStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'componentwise' evaluation", e);
            return null;
        }
    }

    protected List<type.Componentwise> evaluate(List<java.math.BigDecimal> a, List<java.math.BigDecimal> b, com.gs.dmn.runtime.ExecutionContext context_) {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        return zip(asList("A", "B"), asList(a, b)).stream().map(x -> type.Componentwise.toComponentwise(x)).collect(Collectors.toList());
    }
}
