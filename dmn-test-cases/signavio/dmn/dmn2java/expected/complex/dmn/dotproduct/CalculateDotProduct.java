
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"signavio-decision.ftl", "calculateDotProduct"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "calculateDotProduct",
    label = "CalculateDotProduct",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.OTHER,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class CalculateDotProduct extends com.gs.dmn.signavio.runtime.JavaTimeSignavioBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "calculateDotProduct",
        "CalculateDotProduct",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.OTHER,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    private final Componentwise componentwise;

    public CalculateDotProduct() {
        this(new Componentwise());
    }

    public CalculateDotProduct(Componentwise componentwise) {
        this.componentwise = componentwise;
    }

    @java.lang.Override()
    public java.lang.Number applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply((input_.get("A") != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(input_.get("A"), new com.fasterxml.jackson.core.type.TypeReference<List<java.lang.Number>>() {}) : null), (input_.get("B") != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(input_.get("B"), new com.fasterxml.jackson.core.type.TypeReference<List<java.lang.Number>>() {}) : null), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'CalculateDotProduct'", e);
            return null;
        }
    }

    public java.lang.Number apply(List<java.lang.Number> a, List<java.lang.Number> b, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'calculateDotProduct'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long calculateDotProductStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments calculateDotProductArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            calculateDotProductArguments_.put("A", a);
            calculateDotProductArguments_.put("B", b);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, calculateDotProductArguments_);

            // Iterate and aggregate
            java.lang.Number output_ = evaluate(a, b, context_);

            // End decision 'calculateDotProduct'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, calculateDotProductArguments_, output_, (System.currentTimeMillis() - calculateDotProductStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'calculateDotProduct' evaluation", e);
            return null;
        }
    }

    protected java.lang.Number evaluate(List<java.lang.Number> a, List<java.lang.Number> b, com.gs.dmn.runtime.ExecutionContext context_) {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        // Apply child decisions
        List<type.Componentwise> componentwise = this.componentwise.apply(a, b, context_);

        Product product = new Product();
        return sum(componentwise.stream().map(componentwise4_iterator -> product.apply(type.Componentwise3.toComponentwise3(componentwise4_iterator), context_)).collect(Collectors.toList()));
    }
}
