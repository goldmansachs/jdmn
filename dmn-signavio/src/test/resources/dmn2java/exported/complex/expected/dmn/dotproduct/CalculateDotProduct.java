
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
public class CalculateDotProduct extends com.gs.dmn.signavio.runtime.DefaultSignavioBaseDecision {
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

    public java.math.BigDecimal apply(String a, String b, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply((a != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(a, new com.fasterxml.jackson.core.type.TypeReference<List<java.math.BigDecimal>>() {}) : null), (b != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(b, new com.fasterxml.jackson.core.type.TypeReference<List<java.math.BigDecimal>>() {}) : null), annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
        } catch (Exception e) {
            logError("Cannot apply decision 'CalculateDotProduct'", e);
            return null;
        }
    }

    public java.math.BigDecimal apply(String a, String b, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            return apply((a != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(a, new com.fasterxml.jackson.core.type.TypeReference<List<java.math.BigDecimal>>() {}) : null), (b != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(b, new com.fasterxml.jackson.core.type.TypeReference<List<java.math.BigDecimal>>() {}) : null), annotationSet_, eventListener_, externalExecutor_, cache_);
        } catch (Exception e) {
            logError("Cannot apply decision 'CalculateDotProduct'", e);
            return null;
        }
    }

    public java.math.BigDecimal apply(List<java.math.BigDecimal> a, List<java.math.BigDecimal> b, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(a, b, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
    }

    public java.math.BigDecimal apply(List<java.math.BigDecimal> a, List<java.math.BigDecimal> b, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision 'calculateDotProduct'
            long calculateDotProductStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments calculateDotProductArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            calculateDotProductArguments_.put("A", a);
            calculateDotProductArguments_.put("B", b);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, calculateDotProductArguments_);

            // Apply child decisions
            List<type.Componentwise> componentwise = this.componentwise.apply(a, b, annotationSet_, eventListener_, externalExecutor_, cache_);

            // Iterate and aggregate
            java.math.BigDecimal output_ = evaluate(a, b, componentwise, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision 'calculateDotProduct'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, calculateDotProductArguments_, output_, (System.currentTimeMillis() - calculateDotProductStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'calculateDotProduct' evaluation", e);
            return null;
        }
    }

    protected java.math.BigDecimal evaluate(List<java.math.BigDecimal> a, List<java.math.BigDecimal> b, List<type.Componentwise> componentwise, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        Product product = new Product();
        return sum(componentwise.stream().map(componentwise4_iterator -> product.apply(type.Componentwise3.toComponentwise3(componentwise4_iterator), annotationSet_, eventListener_, externalExecutor_, cache_)).collect(Collectors.toList()));
    }
}
