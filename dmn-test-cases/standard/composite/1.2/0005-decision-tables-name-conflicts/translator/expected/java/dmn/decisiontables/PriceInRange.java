package decisiontables;

import java.util.*;
import java.util.stream.Collectors;

@jakarta.annotation.Generated(value = {"decision.ftl", "priceInRange"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "decisiontables",
    name = "priceInRange",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.PRIORITY,
    rulesCount = 2
)
public class PriceInRange extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "decisiontables",
        "priceInRange",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
        com.gs.dmn.runtime.annotation.HitPolicy.PRIORITY,
        2
    );

    public PriceInRange() {
    }

    @java.lang.Override()
    public String applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply((input_.get("decisionInputs1.numB") != null ? number(input_.get("decisionInputs1.numB")) : null), (input_.get("decisionInputs1.numC") != null ? number(input_.get("decisionInputs1.numC")) : null), (input_.get("decisionInputs1.structA") != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(input_.get("decisionInputs1.structA"), new com.fasterxml.jackson.core.type.TypeReference<decisioninputs1.type.TAImpl>() {}) : null), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'PriceInRange'", e);
            return null;
        }
    }

    public String apply(java.math.BigDecimal decisioninputs1_numB, java.math.BigDecimal decisioninputs1_numC, decisioninputs1.type.TA decisioninputs1_structA, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'priceInRange'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long priceInRangeStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments priceInRangeArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            priceInRangeArguments_.put("decisionInputs1.numB", decisioninputs1_numB);
            priceInRangeArguments_.put("decisionInputs1.numC", decisioninputs1_numC);
            priceInRangeArguments_.put("decisionInputs1.structA", decisioninputs1_structA);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, priceInRangeArguments_);

            // Evaluate decision 'priceInRange'
            String output_ = lambda.apply(decisioninputs1_numB, decisioninputs1_numC, decisioninputs1_structA, context_);

            // End decision 'priceInRange'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, priceInRangeArguments_, output_, (System.currentTimeMillis() - priceInRangeStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'priceInRange' evaluation", e);
            return null;
        }
    }

    public com.gs.dmn.runtime.LambdaExpression<String> lambda =
        new com.gs.dmn.runtime.LambdaExpression<String>() {
            public String apply(Object... args_) {
                java.math.BigDecimal decisioninputs1_numB = 0 < args_.length ? (java.math.BigDecimal) args_[0] : null;
                java.math.BigDecimal decisioninputs1_numC = 1 < args_.length ? (java.math.BigDecimal) args_[1] : null;
                decisioninputs1.type.TA decisioninputs1_structA = 2 < args_.length ? (decisioninputs1.type.TA) args_[2] : null;
                com.gs.dmn.runtime.ExecutionContext context_ = 3 < args_.length ? (com.gs.dmn.runtime.ExecutionContext) args_[3] : null;
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
                com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;

                // Apply rules and collect results
                com.gs.dmn.runtime.RuleOutputList ruleOutputList_ = new com.gs.dmn.runtime.RuleOutputList();
                ruleOutputList_.add(rule0(decisioninputs1_numB, decisioninputs1_numC, decisioninputs1_structA, context_));
                ruleOutputList_.add(rule1(decisioninputs1_numB, decisioninputs1_numC, decisioninputs1_structA, context_));

                // Return results based on hit policy
                String output_;
                if (ruleOutputList_.noMatchedRules()) {
                    // Default value
                    output_ = null;
                } else {
                    com.gs.dmn.runtime.RuleOutput ruleOutput_ = ruleOutputList_.applySingle(com.gs.dmn.runtime.annotation.HitPolicy.PRIORITY);
                    output_ = ruleOutput_ == null ? null : ((PriceInRangeRuleOutput)ruleOutput_).getPriceInRange();
                }

                return output_;
            }
    };

    @com.gs.dmn.runtime.annotation.Rule(index = 0, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule0(java.math.BigDecimal decisioninputs1_numB, java.math.BigDecimal decisioninputs1_numC, decisioninputs1.type.TA decisioninputs1_structA, com.gs.dmn.runtime.ExecutionContext context_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(0, "");

        // Rule start
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        PriceInRangeRuleOutput output_ = new PriceInRangeRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            booleanAnd(numericGreaterEqualThan(((java.math.BigDecimal)(decisioninputs1_structA != null ? decisioninputs1_structA.getPrice() : null)), decisioninputs1_numB), numericLessEqualThan(((java.math.BigDecimal)(decisioninputs1_structA != null ? decisioninputs1_structA.getPrice() : null)), decisioninputs1_numC))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setPriceInRange("In range");
            output_.setPriceInRangePriority(2);

            // Add annotation
            annotationSet_.addAnnotation("priceInRange", 0, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 1, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule1(java.math.BigDecimal decisioninputs1_numB, java.math.BigDecimal decisioninputs1_numC, decisioninputs1.type.TA decisioninputs1_structA, com.gs.dmn.runtime.ExecutionContext context_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(1, "");

        // Rule start
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        PriceInRangeRuleOutput output_ = new PriceInRangeRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            Boolean.TRUE
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setPriceInRange("Not in range");
            output_.setPriceInRangePriority(1);

            // Add annotation
            annotationSet_.addAnnotation("priceInRange", 1, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

}
