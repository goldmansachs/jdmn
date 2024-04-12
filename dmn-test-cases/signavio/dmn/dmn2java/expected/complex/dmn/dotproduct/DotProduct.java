
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

    @java.lang.Override()
    public type.DotProduct applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply((input_.get("A") != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(input_.get("A"), new com.fasterxml.jackson.core.type.TypeReference<List<java.math.BigDecimal>>() {}) : null), (input_.get("B") != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(input_.get("B"), new com.fasterxml.jackson.core.type.TypeReference<List<java.math.BigDecimal>>() {}) : null), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'DotProduct'", e);
            return null;
        }
    }

    public type.DotProduct apply(List<java.math.BigDecimal> a, List<java.math.BigDecimal> b, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'dotProduct'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long dotProductStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments dotProductArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            dotProductArguments_.put("A", a);
            dotProductArguments_.put("B", b);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, dotProductArguments_);

            // Evaluate decision 'dotProduct'
            type.DotProduct output_ = evaluate(a, b, context_);

            // End decision 'dotProduct'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, dotProductArguments_, output_, (System.currentTimeMillis() - dotProductStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'dotProduct' evaluation", e);
            return null;
        }
    }

    protected type.DotProduct evaluate(List<java.math.BigDecimal> a, List<java.math.BigDecimal> b, com.gs.dmn.runtime.ExecutionContext context_) {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        // Apply child decisions
        java.math.BigDecimal calculateDotProduct = this.calculateDotProduct.apply(a, b, context_);

        // Apply rules and collect results
        com.gs.dmn.runtime.RuleOutputList ruleOutputList_ = new com.gs.dmn.runtime.RuleOutputList();
        ruleOutputList_.add(rule0(a, b, calculateDotProduct, context_));
        ruleOutputList_.add(rule1(a, b, calculateDotProduct, context_));

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

    @com.gs.dmn.runtime.annotation.Rule(index = 0, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule0(List<java.math.BigDecimal> a, List<java.math.BigDecimal> b, java.math.BigDecimal calculateDotProduct, com.gs.dmn.runtime.ExecutionContext context_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(0, "");

        // Rule start
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        DotProductRuleOutput output_ = new DotProductRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            booleanEqual(numericEqual(count(a), count(b)), Boolean.TRUE)
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setDotProduct2(calculateDotProduct);
            output_.setOutputMessage(concat(asList(text(count(a), "0"), "D vector dot product == ", text(calculateDotProduct, "0.###"))));
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 1, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule1(List<java.math.BigDecimal> a, List<java.math.BigDecimal> b, java.math.BigDecimal calculateDotProduct, com.gs.dmn.runtime.ExecutionContext context_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(1, "");

        // Rule start
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        DotProductRuleOutput output_ = new DotProductRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            booleanEqual(numericEqual(count(a), count(b)), Boolean.FALSE)
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setDotProduct2(number("0"));
            output_.setOutputMessage(concat(asList("Error: Cannot calculate the dot product for a ", text(count(a), "0"), "D vector and a ", text(count(b), "0"), "D vector")));
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
