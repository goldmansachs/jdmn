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

    public Boolean apply(String decisioninputs1_structA, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply((decisioninputs1_structA != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(decisioninputs1_structA, new com.fasterxml.jackson.core.type.TypeReference<decisioninputs1.type.TAImpl>() {}) : null), annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
        } catch (Exception e) {
            logError("Cannot apply decision 'PriceGt10'", e);
            return null;
        }
    }

    public Boolean apply(String decisioninputs1_structA, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            return apply((decisioninputs1_structA != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(decisioninputs1_structA, new com.fasterxml.jackson.core.type.TypeReference<decisioninputs1.type.TAImpl>() {}) : null), annotationSet_, eventListener_, externalExecutor_, cache_);
        } catch (Exception e) {
            logError("Cannot apply decision 'PriceGt10'", e);
            return null;
        }
    }

    public Boolean apply(decisioninputs1.type.TA decisioninputs1_structA, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(decisioninputs1_structA, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
    }

    public Boolean apply(decisioninputs1.type.TA decisioninputs1_structA, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision 'priceGt10'
            long priceGt10StartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments priceGt10Arguments_ = new com.gs.dmn.runtime.listener.Arguments();
            priceGt10Arguments_.put("decisionInputs1.structA", decisioninputs1_structA);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, priceGt10Arguments_);

            // Evaluate decision 'priceGt10'
            Boolean output_ = evaluate(decisioninputs1_structA, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision 'priceGt10'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, priceGt10Arguments_, output_, (System.currentTimeMillis() - priceGt10StartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'priceGt10' evaluation", e);
            return null;
        }
    }

    protected Boolean evaluate(decisioninputs1.type.TA decisioninputs1_structA, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        // Apply rules and collect results
        com.gs.dmn.runtime.RuleOutputList ruleOutputList_ = new com.gs.dmn.runtime.RuleOutputList();
        ruleOutputList_.add(rule0(decisioninputs1_structA, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule1(decisioninputs1_structA, annotationSet_, eventListener_, externalExecutor_));

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

    @com.gs.dmn.runtime.annotation.Rule(index = 0, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule0(decisioninputs1.type.TA decisioninputs1_structA, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(0, "");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        PriceGt10RuleOutput output_ = new PriceGt10RuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (numericGreaterThan(((java.math.BigDecimal)(decisioninputs1_structA != null ? decisioninputs1_structA.getPrice() : null)), number("10")))
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
    public com.gs.dmn.runtime.RuleOutput rule1(decisioninputs1.type.TA decisioninputs1_structA, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(1, "");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        PriceGt10RuleOutput output_ = new PriceGt10RuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (numericLessEqualThan(((java.math.BigDecimal)(decisioninputs1_structA != null ? decisioninputs1_structA.getPrice() : null)), number("10")))
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
