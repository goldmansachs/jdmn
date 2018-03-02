
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "ApprovalStatus"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "ApprovalStatus",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNIQUE,
    rulesCount = 4
)
public class ApprovalStatus extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "ApprovalStatus",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
        com.gs.dmn.runtime.annotation.HitPolicy.UNIQUE,
        4
    );

    public ApprovalStatus() {
    }

    public String apply(String age, String riskCategory, String isAffordable, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply((age != null ? number(age) : null), riskCategory, (isAffordable != null ? Boolean.valueOf(isAffordable) : null), annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor());
        } catch (Exception e) {
            logError("Cannot apply decision 'ApprovalStatus'", e);
            return null;
        }
    }

    public String apply(String age, String riskCategory, String isAffordable, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        try {
            return apply((age != null ? number(age) : null), riskCategory, (isAffordable != null ? Boolean.valueOf(isAffordable) : null), annotationSet_, eventListener_, externalExecutor_);
        } catch (Exception e) {
            logError("Cannot apply decision 'ApprovalStatus'", e);
            return null;
        }
    }

    public String apply(java.math.BigDecimal age, String riskCategory, Boolean isAffordable, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(age, riskCategory, isAffordable, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor());
    }

    public String apply(java.math.BigDecimal age, String riskCategory, Boolean isAffordable, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        try {
            // Start decision 'ApprovalStatus'
            long approvalStatusStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments approvalStatusArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            approvalStatusArguments_.put("age", age);
            approvalStatusArguments_.put("riskCategory", riskCategory);
            approvalStatusArguments_.put("isAffordable", isAffordable);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, approvalStatusArguments_);

            // Evaluate decision 'ApprovalStatus'
            String output_ = evaluate(age, riskCategory, isAffordable, annotationSet_, eventListener_, externalExecutor_);

            // End decision 'ApprovalStatus'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, approvalStatusArguments_, output_, (System.currentTimeMillis() - approvalStatusStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'ApprovalStatus' evaluation", e);
            return null;
        }
    }

    private String evaluate(java.math.BigDecimal age, String riskCategory, Boolean isAffordable, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Apply rules and collect results
        com.gs.dmn.runtime.RuleOutputList ruleOutputList_ = new com.gs.dmn.runtime.RuleOutputList();
        ruleOutputList_.add(rule0(age, riskCategory, isAffordable, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule1(age, riskCategory, isAffordable, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule2(age, riskCategory, isAffordable, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule3(age, riskCategory, isAffordable, annotationSet_, eventListener_, externalExecutor_));

        // Return results based on hit policy
        String output_;
        if (ruleOutputList_.noMatchedRules()) {
            // Default value
            output_ = null;
        } else {
            com.gs.dmn.runtime.RuleOutput ruleOutput_ = ruleOutputList_.applySingle(com.gs.dmn.runtime.annotation.HitPolicy.UNIQUE);
            output_ = ruleOutput_ == null ? null : ((ApprovalStatusRuleOutput)ruleOutput_).getApprovalStatus();
        }

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 0, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule0(java.math.BigDecimal age, String riskCategory, Boolean isAffordable, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(0, "");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        ApprovalStatusRuleOutput output_ = new ApprovalStatusRuleOutput(false);
        if (Boolean.TRUE == booleanAnd(
            (numericGreaterEqualThan(age, number("18"))),
            booleanOr((stringEqual(riskCategory, "Medium")), (stringEqual(riskCategory, "Low"))),
            (booleanEqual(isAffordable, Boolean.TRUE))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setApprovalStatus("Approved");

            // Add annotation
            annotationSet_.addAnnotation("ApprovalStatus", 0, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 1, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule1(java.math.BigDecimal age, String riskCategory, Boolean isAffordable, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(1, "");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        ApprovalStatusRuleOutput output_ = new ApprovalStatusRuleOutput(false);
        if (Boolean.TRUE == booleanAnd(
            (numericLessThan(age, number("18"))),
            booleanOr((stringEqual(riskCategory, "Medium")), (stringEqual(riskCategory, "Low"))),
            (booleanEqual(isAffordable, Boolean.TRUE))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setApprovalStatus("Declined");

            // Add annotation
            annotationSet_.addAnnotation("ApprovalStatus", 1, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 2, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule2(java.math.BigDecimal age, String riskCategory, Boolean isAffordable, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(2, "");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        ApprovalStatusRuleOutput output_ = new ApprovalStatusRuleOutput(false);
        if (Boolean.TRUE == booleanAnd(
            true,
            (stringEqual(riskCategory, "High")),
            (booleanEqual(isAffordable, Boolean.TRUE))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setApprovalStatus("Declined");

            // Add annotation
            annotationSet_.addAnnotation("ApprovalStatus", 2, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 3, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule3(java.math.BigDecimal age, String riskCategory, Boolean isAffordable, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(3, "");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        ApprovalStatusRuleOutput output_ = new ApprovalStatusRuleOutput(false);
        if (Boolean.TRUE == booleanAnd(
            true,
            true,
            (booleanEqual(isAffordable, Boolean.FALSE))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setApprovalStatus("Declined");

            // Add annotation
            annotationSet_.addAnnotation("ApprovalStatus", 3, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

}
