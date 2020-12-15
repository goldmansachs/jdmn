
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"signavio-decision.ftl", "dotProduct"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "dotProduct",
    label = "DotProduct",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNIQUE,
    rulesCount = 2
)
public class DotProduct extends com.gs.dmn.signavio.runtime.DefaultSignavioBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "dotProduct",
        "DotProduct",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
        com.gs.dmn.runtime.annotation.HitPolicy.UNIQUE,
        2
    );

    private final CalculateDotProduct calculateDotProduct;

    public DotProduct() {
        this(new CalculateDotProduct());
    }

    public DotProduct(CalculateDotProduct calculateDotProduct) {
        this.calculateDotProduct = calculateDotProduct;
    }

    public type.DotProduct apply(String a, String b, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply((a != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(a, new com.fasterxml.jackson.core.type.TypeReference<List<java.math.BigDecimal>>() {}) : null), (b != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(b, new com.fasterxml.jackson.core.type.TypeReference<List<java.math.BigDecimal>>() {}) : null), annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
        } catch (Exception e) {
            logError("Cannot apply decision 'DotProduct'", e);
            return null;
        }
    }

    public type.DotProduct apply(String a, String b, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            return apply((a != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(a, new com.fasterxml.jackson.core.type.TypeReference<List<java.math.BigDecimal>>() {}) : null), (b != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(b, new com.fasterxml.jackson.core.type.TypeReference<List<java.math.BigDecimal>>() {}) : null), annotationSet_, eventListener_, externalExecutor_, cache_);
        } catch (Exception e) {
            logError("Cannot apply decision 'DotProduct'", e);
            return null;
        }
    }

    public type.DotProduct apply(List<java.math.BigDecimal> a, List<java.math.BigDecimal> b, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(a, b, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
    }

    public type.DotProduct apply(List<java.math.BigDecimal> a, List<java.math.BigDecimal> b, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision 'dotProduct'
            long dotProductStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments dotProductArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            dotProductArguments_.put("A", a);
            dotProductArguments_.put("B", b);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, dotProductArguments_);

            // Apply child decisions
            java.math.BigDecimal calculateDotProduct = this.calculateDotProduct.apply(a, b, annotationSet_, eventListener_, externalExecutor_, cache_);

            // Evaluate decision 'dotProduct'
            type.DotProduct output_ = evaluate(a, b, calculateDotProduct, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision 'dotProduct'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, dotProductArguments_, output_, (System.currentTimeMillis() - dotProductStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'dotProduct' evaluation", e);
            return null;
        }
    }

    protected type.DotProduct evaluate(List<java.math.BigDecimal> a, List<java.math.BigDecimal> b, java.math.BigDecimal calculateDotProduct, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        // Apply rules and collect results
        com.gs.dmn.runtime.RuleOutputList ruleOutputList_ = new com.gs.dmn.runtime.RuleOutputList();
        ruleOutputList_.add(rule0(a, b, calculateDotProduct, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule1(a, b, calculateDotProduct, annotationSet_, eventListener_, externalExecutor_));

        // Return results based on hit policy
        type.DotProduct output_;
        if (ruleOutputList_.noMatchedRules()) {
            // Default value
            output_ = null;
        } else {
            com.gs.dmn.runtime.RuleOutput ruleOutput_ = ruleOutputList_.applySingle(com.gs.dmn.runtime.annotation.HitPolicy.UNIQUE);
            output_ = toDecisionOutput((DotProductRuleOutput)ruleOutput_);
        }

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 0, annotation = "\"\"")
    public com.gs.dmn.runtime.RuleOutput rule0(List<java.math.BigDecimal> a, List<java.math.BigDecimal> b, java.math.BigDecimal calculateDotProduct, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(0, "\"\"");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        DotProductRuleOutput output_ = new DotProductRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (booleanEqual(numericEqual(count(a), count(b)), Boolean.TRUE))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setDotProduct2(calculateDotProduct);
            output_.setOutputMessage(concat(asList(text(count(a), "0"), "D vector dot product == ", text(calculateDotProduct, "0.###"))));

            // Add annotation
            annotationSet_.addAnnotation("dotProduct", 0, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 1, annotation = "\"\"")
    public com.gs.dmn.runtime.RuleOutput rule1(List<java.math.BigDecimal> a, List<java.math.BigDecimal> b, java.math.BigDecimal calculateDotProduct, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(1, "\"\"");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        DotProductRuleOutput output_ = new DotProductRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (booleanEqual(numericEqual(count(a), count(b)), Boolean.FALSE))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setDotProduct2(number("0"));
            output_.setOutputMessage(concat(asList("Error: Cannot calculate the dot product for a ", text(count(a), "0"), "D vector and a ", text(count(b), "0"), "D vector")));

            // Add annotation
            annotationSet_.addAnnotation("dotProduct", 1, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    public type.DotProduct toDecisionOutput(DotProductRuleOutput ruleOutput_) {
        type.DotProductImpl result_ = new type.DotProductImpl();
        result_.setDotProduct2(ruleOutput_ == null ? null : ruleOutput_.getDotProduct2());
        result_.setOutputMessage(ruleOutput_ == null ? null : ruleOutput_.getOutputMessage());
        return result_;
    }
}
