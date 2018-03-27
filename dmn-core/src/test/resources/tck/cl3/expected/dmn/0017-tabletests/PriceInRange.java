
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "priceInRange"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "priceInRange",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.PRIORITY,
    rulesCount = 2
)
public class PriceInRange extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "priceInRange",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
        com.gs.dmn.runtime.annotation.HitPolicy.PRIORITY,
        2
    );

    public PriceInRange() {
    }

    public String apply(String numB, String numC, String structA, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply((numB != null ? number(numB) : null), (numC != null ? number(numC) : null), (structA != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(structA, type.TAImpl.class) : null), annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor());
        } catch (Exception e) {
            logError("Cannot apply decision 'PriceInRange'", e);
            return null;
        }
    }

    public String apply(String numB, String numC, String structA, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        try {
            return apply((numB != null ? number(numB) : null), (numC != null ? number(numC) : null), (structA != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(structA, type.TAImpl.class) : null), annotationSet_, eventListener_, externalExecutor_);
        } catch (Exception e) {
            logError("Cannot apply decision 'PriceInRange'", e);
            return null;
        }
    }

    public String apply(java.math.BigDecimal numB, java.math.BigDecimal numC, type.TA structA, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(numB, numC, structA, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor());
    }

    public String apply(java.math.BigDecimal numB, java.math.BigDecimal numC, type.TA structA, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        try {
            // Start decision 'priceInRange'
            long priceInRangeStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments priceInRangeArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            priceInRangeArguments_.put("numB", numB);
            priceInRangeArguments_.put("numC", numC);
            priceInRangeArguments_.put("structA", structA);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, priceInRangeArguments_);

            // Evaluate decision 'priceInRange'
            String output_ = evaluate(numB, numC, structA, annotationSet_, eventListener_, externalExecutor_);

            // End decision 'priceInRange'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, priceInRangeArguments_, output_, (System.currentTimeMillis() - priceInRangeStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'priceInRange' evaluation", e);
            return null;
        }
    }

    private String evaluate(java.math.BigDecimal numB, java.math.BigDecimal numC, type.TA structA, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Apply rules and collect results
        com.gs.dmn.runtime.RuleOutputList ruleOutputList_ = new com.gs.dmn.runtime.RuleOutputList();
        ruleOutputList_.add(rule0(numB, numC, structA, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule1(numB, numC, structA, annotationSet_, eventListener_, externalExecutor_));

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

    @com.gs.dmn.runtime.annotation.Rule(index = 0, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule0(java.math.BigDecimal numB, java.math.BigDecimal numC, type.TA structA, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(0, "");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        PriceInRangeRuleOutput output_ = new PriceInRangeRuleOutput(false);
        if (Boolean.TRUE == (booleanAnd(numericGreaterEqualThan(((java.math.BigDecimal)(structA != null ? structA.getPrice() : null)), numB), numericLessEqualThan(((java.math.BigDecimal)(structA != null ? structA.getPrice() : null)), numC)))) {
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
    public com.gs.dmn.runtime.RuleOutput rule1(java.math.BigDecimal numB, java.math.BigDecimal numC, type.TA structA, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(1, "");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        PriceInRangeRuleOutput output_ = new PriceInRangeRuleOutput(false);
        if (Boolean.TRUE == true) {
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
