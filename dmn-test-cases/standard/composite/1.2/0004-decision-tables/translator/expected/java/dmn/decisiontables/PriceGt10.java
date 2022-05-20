package decisiontables;

import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "priceGt10"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "decisiontables",
    name = "priceGt10",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNIQUE,
    rulesCount = 2
)
public class PriceGt10 extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "decisiontables",
        "priceGt10",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
        com.gs.dmn.runtime.annotation.HitPolicy.UNIQUE,
        2
    );

    public PriceGt10() {
    }

    public Boolean apply(String decisioninputs_structA, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            return apply((decisioninputs_structA != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(decisioninputs_structA, new com.fasterxml.jackson.core.type.TypeReference<decisioninputs.type.TAImpl>() {}) : null), annotationSet_, eventListener_, externalExecutor_, cache_);
        } catch (Exception e) {
            logError("Cannot apply decision 'PriceGt10'", e);
            return null;
        }
    }

    public Boolean apply(decisioninputs.type.TA decisioninputs_structA, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision 'priceGt10'
            long priceGt10StartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments priceGt10Arguments_ = new com.gs.dmn.runtime.listener.Arguments();
            priceGt10Arguments_.put("decisionInputs.structA", decisioninputs_structA);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, priceGt10Arguments_);

            // Evaluate decision 'priceGt10'
            Boolean output_ = lambda.apply(decisioninputs_structA, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision 'priceGt10'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, priceGt10Arguments_, output_, (System.currentTimeMillis() - priceGt10StartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'priceGt10' evaluation", e);
            return null;
        }
    }

    public com.gs.dmn.runtime.LambdaExpression<Boolean> lambda =
        new com.gs.dmn.runtime.LambdaExpression<Boolean>() {
            public Boolean apply(Object... args_) {
                decisioninputs.type.TA decisioninputs_structA = 0 < args_.length ? (decisioninputs.type.TA) args_[0] : null;
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = 1 < args_.length ? (com.gs.dmn.runtime.annotation.AnnotationSet) args_[1] : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = 2 < args_.length ? (com.gs.dmn.runtime.listener.EventListener) args_[2] : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = 3 < args_.length ? (com.gs.dmn.runtime.external.ExternalFunctionExecutor) args_[3] : null;
                com.gs.dmn.runtime.cache.Cache cache_ = 4 < args_.length ? (com.gs.dmn.runtime.cache.Cache) args_[4] : null;

                // Apply rules and collect results
                com.gs.dmn.runtime.RuleOutputList ruleOutputList_ = new com.gs.dmn.runtime.RuleOutputList();
                ruleOutputList_.add(rule0(decisioninputs_structA, annotationSet_, eventListener_, externalExecutor_, cache_));
                ruleOutputList_.add(rule1(decisioninputs_structA, annotationSet_, eventListener_, externalExecutor_, cache_));

                // Return results based on hit policy
                Boolean output_;
                if (ruleOutputList_.noMatchedRules()) {
                    // Default value
                    output_ = null;
                } else {
                    com.gs.dmn.runtime.RuleOutput ruleOutput_ = ruleOutputList_.applySingle(com.gs.dmn.runtime.annotation.HitPolicy.UNIQUE);
                    output_ = ruleOutput_ == null ? null : ((PriceGt10RuleOutput)ruleOutput_).getPriceGt10();
                }

                return output_;
            }
    };

    @com.gs.dmn.runtime.annotation.Rule(index = 0, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule0(decisioninputs.type.TA decisioninputs_structA, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(0, "");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        PriceGt10RuleOutput output_ = new PriceGt10RuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (numericGreaterThan(((java.math.BigDecimal)(decisioninputs_structA != null ? decisioninputs_structA.getPrice() : null)), number("10")))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setPriceGt10(Boolean.TRUE);

            // Add annotation
            annotationSet_.addAnnotation("priceGt10", 0, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 1, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule1(decisioninputs.type.TA decisioninputs_structA, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(1, "");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        PriceGt10RuleOutput output_ = new PriceGt10RuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (numericLessEqualThan(((java.math.BigDecimal)(decisioninputs_structA != null ? decisioninputs_structA.getPrice() : null)), number("10")))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setPriceGt10(Boolean.FALSE);

            // Add annotation
            annotationSet_.addAnnotation("priceGt10", 1, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

}
