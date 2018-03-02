
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "ExtraDaysCase3"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "ExtraDaysCase3",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.COLLECT,
    rulesCount = 2
)
public class ExtraDaysCase3 extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "ExtraDaysCase3",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
        com.gs.dmn.runtime.annotation.HitPolicy.COLLECT,
        2
    );

    public ExtraDaysCase3() {
    }

    public java.math.BigDecimal apply(String age, String yearsOfService, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply((age != null ? number(age) : null), (yearsOfService != null ? number(yearsOfService) : null), annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor());
        } catch (Exception e) {
            logError("Cannot apply decision 'ExtraDaysCase3'", e);
            return null;
        }
    }

    public java.math.BigDecimal apply(String age, String yearsOfService, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        try {
            return apply((age != null ? number(age) : null), (yearsOfService != null ? number(yearsOfService) : null), annotationSet_, eventListener_, externalExecutor_);
        } catch (Exception e) {
            logError("Cannot apply decision 'ExtraDaysCase3'", e);
            return null;
        }
    }

    public java.math.BigDecimal apply(java.math.BigDecimal age, java.math.BigDecimal yearsOfService, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(age, yearsOfService, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor());
    }

    public java.math.BigDecimal apply(java.math.BigDecimal age, java.math.BigDecimal yearsOfService, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        try {
            // Start decision 'ExtraDaysCase3'
            long extraDaysCase3StartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments extraDaysCase3Arguments_ = new com.gs.dmn.runtime.listener.Arguments();
            extraDaysCase3Arguments_.put("age", age);
            extraDaysCase3Arguments_.put("yearsOfService", yearsOfService);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, extraDaysCase3Arguments_);

            // Evaluate decision 'ExtraDaysCase3'
            java.math.BigDecimal output_ = evaluate(age, yearsOfService, annotationSet_, eventListener_, externalExecutor_);

            // End decision 'ExtraDaysCase3'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, extraDaysCase3Arguments_, output_, (System.currentTimeMillis() - extraDaysCase3StartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'ExtraDaysCase3' evaluation", e);
            return null;
        }
    }

    private java.math.BigDecimal evaluate(java.math.BigDecimal age, java.math.BigDecimal yearsOfService, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Apply rules and collect results
        com.gs.dmn.runtime.RuleOutputList ruleOutputList_ = new com.gs.dmn.runtime.RuleOutputList();
        ruleOutputList_.add(rule0(age, yearsOfService, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule1(age, yearsOfService, annotationSet_, eventListener_, externalExecutor_));

        // Return results based on hit policy
        java.math.BigDecimal output_;
        if (ruleOutputList_.noMatchedRules()) {
            // Default value
            output_ = number("0");
        } else {
            List<? extends com.gs.dmn.runtime.RuleOutput> ruleOutputs_ = ruleOutputList_.applyMultiple(com.gs.dmn.runtime.annotation.HitPolicy.COLLECT);
            output_ = max(ruleOutputs_.stream().map(o -> ((ExtraDaysCase3RuleOutput)o).getExtraDaysCase3()).collect(Collectors.toList()));
        }

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 0, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule0(java.math.BigDecimal age, java.math.BigDecimal yearsOfService, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(0, "");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        ExtraDaysCase3RuleOutput output_ = new ExtraDaysCase3RuleOutput(false);
        if (Boolean.TRUE == booleanAnd(
            true,
            (booleanAnd(numericGreaterEqualThan(yearsOfService, number("15")), numericLessThan(yearsOfService, number("30"))))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setExtraDaysCase3(number("2"));

            // Add annotation
            annotationSet_.addAnnotation("ExtraDaysCase3", 0, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 1, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule1(java.math.BigDecimal age, java.math.BigDecimal yearsOfService, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(1, "");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        ExtraDaysCase3RuleOutput output_ = new ExtraDaysCase3RuleOutput(false);
        if (Boolean.TRUE == booleanAnd(
            (numericGreaterEqualThan(age, number("45"))),
            true
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setExtraDaysCase3(number("2"));

            // Add annotation
            annotationSet_.addAnnotation("ExtraDaysCase3", 1, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

}
