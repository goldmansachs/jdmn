
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"signavio-decision.ftl", "product"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "product",
    label = "Product",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNIQUE,
    rulesCount = 1
)
public class Product extends com.gs.dmn.signavio.runtime.DefaultSignavioBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "product",
        "Product",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
        com.gs.dmn.runtime.annotation.HitPolicy.UNIQUE,
        1
    );

    public Product() {
    }

    public java.math.BigDecimal apply(String componentwise4_iterator, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply((componentwise4_iterator != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(componentwise4_iterator, new com.fasterxml.jackson.core.type.TypeReference<type.Componentwise3Impl>() {}) : null), annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
        } catch (Exception e) {
            logError("Cannot apply decision 'Product'", e);
            return null;
        }
    }

    public java.math.BigDecimal apply(String componentwise4_iterator, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            return apply((componentwise4_iterator != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(componentwise4_iterator, new com.fasterxml.jackson.core.type.TypeReference<type.Componentwise3Impl>() {}) : null), annotationSet_, eventListener_, externalExecutor_, cache_);
        } catch (Exception e) {
            logError("Cannot apply decision 'Product'", e);
            return null;
        }
    }

    public java.math.BigDecimal apply(type.Componentwise3 componentwise4_iterator, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(componentwise4_iterator, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
    }

    public java.math.BigDecimal apply(type.Componentwise3 componentwise4_iterator, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision 'product'
            long productStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments productArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            productArguments_.put("Componentwise", componentwise4_iterator);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, productArguments_);

            // Evaluate decision 'product'
            java.math.BigDecimal output_ = evaluate(componentwise4_iterator, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision 'product'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, productArguments_, output_, (System.currentTimeMillis() - productStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'product' evaluation", e);
            return null;
        }
    }

    protected java.math.BigDecimal evaluate(type.Componentwise3 componentwise4_iterator, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        // Apply rules and collect results
        com.gs.dmn.runtime.RuleOutputList ruleOutputList_ = new com.gs.dmn.runtime.RuleOutputList();
        ruleOutputList_.add(rule0(componentwise4_iterator, annotationSet_, eventListener_, externalExecutor_));

        // Return results based on hit policy
        java.math.BigDecimal output_;
        if (ruleOutputList_.noMatchedRules()) {
            // Default value
            output_ = null;
        } else {
            com.gs.dmn.runtime.RuleOutput ruleOutput_ = ruleOutputList_.applySingle(com.gs.dmn.runtime.annotation.HitPolicy.UNIQUE);
            output_ = ruleOutput_ == null ? null : ((ProductRuleOutput)ruleOutput_).getProduct();
        }

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 0, annotation = "\"\"")
    public com.gs.dmn.runtime.RuleOutput rule0(type.Componentwise3 componentwise4_iterator, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(0, "\"\"");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        ProductRuleOutput output_ = new ProductRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            Boolean.TRUE
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setProduct(numericMultiply(((java.math.BigDecimal)(componentwise4_iterator != null ? componentwise4_iterator.getA() : null)), ((java.math.BigDecimal)(componentwise4_iterator != null ? componentwise4_iterator.getB() : null))));

            // Add annotation
            annotationSet_.addAnnotation("product", 0, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

}
