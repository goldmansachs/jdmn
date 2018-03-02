
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "Approval"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "Approval",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNIQUE,
    rulesCount = 5
)
public class Approval extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "Approval",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
        com.gs.dmn.runtime.annotation.HitPolicy.UNIQUE,
        5
    );

    public Approval() {
    }

    public type.TApproval apply(String age, String riskCategory, String isAffordable, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply((age != null ? number(age) : null), riskCategory, (isAffordable != null ? Boolean.valueOf(isAffordable) : null), annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor());
        } catch (Exception e) {
            logError("Cannot apply decision 'Approval'", e);
            return null;
        }
    }

    public type.TApproval apply(String age, String riskCategory, String isAffordable, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        try {
            return apply((age != null ? number(age) : null), riskCategory, (isAffordable != null ? Boolean.valueOf(isAffordable) : null), annotationSet_, eventListener_, externalExecutor_);
        } catch (Exception e) {
            logError("Cannot apply decision 'Approval'", e);
            return null;
        }
    }

    public type.TApproval apply(java.math.BigDecimal age, String riskCategory, Boolean isAffordable, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(age, riskCategory, isAffordable, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor());
    }

    public type.TApproval apply(java.math.BigDecimal age, String riskCategory, Boolean isAffordable, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        try {
            // Start decision 'Approval'
            long approvalStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments approvalArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            approvalArguments_.put("age", age);
            approvalArguments_.put("riskCategory", riskCategory);
            approvalArguments_.put("isAffordable", isAffordable);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, approvalArguments_);

            // Evaluate decision 'Approval'
            type.TApproval output_ = evaluate(age, riskCategory, isAffordable, annotationSet_, eventListener_, externalExecutor_);

            // End decision 'Approval'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, approvalArguments_, output_, (System.currentTimeMillis() - approvalStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'Approval' evaluation", e);
            return null;
        }
    }

    private type.TApproval evaluate(java.math.BigDecimal age, String riskCategory, Boolean isAffordable, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Apply rules and collect results
        com.gs.dmn.runtime.RuleOutputList ruleOutputList_ = new com.gs.dmn.runtime.RuleOutputList();
        ruleOutputList_.add(rule0(age, riskCategory, isAffordable, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule1(age, riskCategory, isAffordable, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule2(age, riskCategory, isAffordable, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule3(age, riskCategory, isAffordable, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule4(age, riskCategory, isAffordable, annotationSet_, eventListener_, externalExecutor_));

        // Return results based on hit policy
        type.TApproval output_;
        if (ruleOutputList_.noMatchedRules()) {
            // Default value
            output_ = new type.TApprovalImpl("Standard", "Declined");
        } else {
            com.gs.dmn.runtime.RuleOutput ruleOutput_ = ruleOutputList_.applySingle(com.gs.dmn.runtime.annotation.HitPolicy.UNIQUE);
            output_ = toDecisionOutput((ApprovalRuleOutput)ruleOutput_);
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
        ApprovalRuleOutput output_ = new ApprovalRuleOutput(false);
        if (Boolean.TRUE == booleanAnd(
            (numericGreaterEqualThan(age, number("18"))),
            (stringEqual(riskCategory, "Low")),
            (booleanEqual(isAffordable, Boolean.TRUE))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setStatus("Approved");
            output_.setRate("Best");

            // Add annotation
            annotationSet_.addAnnotation("Approval", 0, "");
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
        ApprovalRuleOutput output_ = new ApprovalRuleOutput(false);
        if (Boolean.TRUE == booleanAnd(
            (numericGreaterEqualThan(age, number("18"))),
            (stringEqual(riskCategory, "Medium")),
            (booleanEqual(isAffordable, Boolean.TRUE))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setStatus("Approved");
            output_.setRate("Standard");

            // Add annotation
            annotationSet_.addAnnotation("Approval", 1, "");
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
        ApprovalRuleOutput output_ = new ApprovalRuleOutput(false);
        if (Boolean.TRUE == booleanAnd(
            (numericLessThan(age, number("18"))),
            booleanOr((stringEqual(riskCategory, "Medium")), (stringEqual(riskCategory, "Low"))),
            (booleanEqual(isAffordable, Boolean.TRUE))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setStatus("Declined");
            output_.setRate("Standard");

            // Add annotation
            annotationSet_.addAnnotation("Approval", 2, "");
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
        ApprovalRuleOutput output_ = new ApprovalRuleOutput(false);
        if (Boolean.TRUE == booleanAnd(
            true,
            (stringEqual(riskCategory, "High")),
            (booleanEqual(isAffordable, Boolean.TRUE))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setStatus("Declined");
            output_.setRate("Standard");

            // Add annotation
            annotationSet_.addAnnotation("Approval", 3, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 4, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule4(java.math.BigDecimal age, String riskCategory, Boolean isAffordable, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(4, "");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        ApprovalRuleOutput output_ = new ApprovalRuleOutput(false);
        if (Boolean.TRUE == booleanAnd(
            true,
            true,
            (booleanEqual(isAffordable, Boolean.FALSE))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setStatus("Declined");
            output_.setRate("Standard");

            // Add annotation
            annotationSet_.addAnnotation("Approval", 4, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    public type.TApproval toDecisionOutput(ApprovalRuleOutput ruleOutput_) {
        type.TApprovalImpl result_ = new type.TApprovalImpl();
        result_.setStatus(ruleOutput_ == null ? null : ruleOutput_.getStatus());
        result_.setRate(ruleOutput_ == null ? null : ruleOutput_.getRate());
        return result_;
    }
}
