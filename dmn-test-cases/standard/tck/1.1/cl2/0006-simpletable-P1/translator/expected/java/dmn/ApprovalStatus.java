
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "'Approval Status'"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "'Approval Status'",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.PRIORITY,
    rulesCount = 4
)
public class ApprovalStatus extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "'Approval Status'",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
        com.gs.dmn.runtime.annotation.HitPolicy.PRIORITY,
        4
    );

    public ApprovalStatus() {
    }

    @java.lang.Override()
    public String apply(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply(input_.get("Age"), input_.get("RiskCategory"), input_.get("isAffordable"), context_.getAnnotations(), context_.getEventListener(), context_.getExternalFunctionExecutor(), context_.getCache());
        } catch (Exception e) {
            logError("Cannot apply decision 'ApprovalStatus'", e);
            return null;
        }
    }

    public String apply(String age, String riskCategory, String isAffordable, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            return apply((age != null ? number(age) : null), riskCategory, (isAffordable != null ? Boolean.valueOf(isAffordable) : null), annotationSet_, eventListener_, externalExecutor_, cache_);
        } catch (Exception e) {
            logError("Cannot apply decision 'ApprovalStatus'", e);
            return null;
        }
    }

    public String apply(java.math.BigDecimal age, String riskCategory, Boolean isAffordable, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision ''Approval Status''
            long approvalStatusStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments approvalStatusArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            approvalStatusArguments_.put("Age", age);
            approvalStatusArguments_.put("RiskCategory", riskCategory);
            approvalStatusArguments_.put("isAffordable", isAffordable);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, approvalStatusArguments_);

            // Evaluate decision ''Approval Status''
            String output_ = lambda.apply(age, riskCategory, isAffordable, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision ''Approval Status''
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, approvalStatusArguments_, output_, (System.currentTimeMillis() - approvalStatusStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in ''Approval Status'' evaluation", e);
            return null;
        }
    }

    public com.gs.dmn.runtime.LambdaExpression<String> lambda =
        new com.gs.dmn.runtime.LambdaExpression<String>() {
            public String apply(Object... args_) {
                java.math.BigDecimal age = 0 < args_.length ? (java.math.BigDecimal) args_[0] : null;
                String riskCategory = 1 < args_.length ? (String) args_[1] : null;
                Boolean isAffordable = 2 < args_.length ? (Boolean) args_[2] : null;
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = 3 < args_.length ? (com.gs.dmn.runtime.annotation.AnnotationSet) args_[3] : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = 4 < args_.length ? (com.gs.dmn.runtime.listener.EventListener) args_[4] : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = 5 < args_.length ? (com.gs.dmn.runtime.external.ExternalFunctionExecutor) args_[5] : null;
                com.gs.dmn.runtime.cache.Cache cache_ = 6 < args_.length ? (com.gs.dmn.runtime.cache.Cache) args_[6] : null;

                // Apply rules and collect results
                com.gs.dmn.runtime.RuleOutputList ruleOutputList_ = new com.gs.dmn.runtime.RuleOutputList();
                ruleOutputList_.add(rule0(age, riskCategory, isAffordable, annotationSet_, eventListener_, externalExecutor_, cache_));
                ruleOutputList_.add(rule1(age, riskCategory, isAffordable, annotationSet_, eventListener_, externalExecutor_, cache_));
                ruleOutputList_.add(rule2(age, riskCategory, isAffordable, annotationSet_, eventListener_, externalExecutor_, cache_));
                ruleOutputList_.add(rule3(age, riskCategory, isAffordable, annotationSet_, eventListener_, externalExecutor_, cache_));

                // Return results based on hit policy
                String output_;
                if (ruleOutputList_.noMatchedRules()) {
                    // Default value
                    output_ = null;
                } else {
                    com.gs.dmn.runtime.RuleOutput ruleOutput_ = ruleOutputList_.applySingle(com.gs.dmn.runtime.annotation.HitPolicy.PRIORITY);
                    output_ = ruleOutput_ == null ? null : ((ApprovalStatusRuleOutput)ruleOutput_).getApprovalStatus();
                }

                return output_;
            }
    };

    @com.gs.dmn.runtime.annotation.Rule(index = 0, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule0(java.math.BigDecimal age, String riskCategory, Boolean isAffordable, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(0, "");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        ApprovalStatusRuleOutput output_ = new ApprovalStatusRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (numericGreaterEqualThan(age, number("18"))),
            booleanOr((stringEqual(riskCategory, "Medium")), (stringEqual(riskCategory, "Low"))),
            (booleanEqual(isAffordable, Boolean.TRUE))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setApprovalStatus("Approved");
            output_.setApprovalStatusPriority(2);

            // Add annotation
            annotationSet_.addAnnotation("'Approval Status'", 0, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 1, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule1(java.math.BigDecimal age, String riskCategory, Boolean isAffordable, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(1, "");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        ApprovalStatusRuleOutput output_ = new ApprovalStatusRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (numericLessThan(age, number("18"))),
            Boolean.TRUE,
            Boolean.TRUE
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setApprovalStatus("Declined");
            output_.setApprovalStatusPriority(1);

            // Add annotation
            annotationSet_.addAnnotation("'Approval Status'", 1, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 2, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule2(java.math.BigDecimal age, String riskCategory, Boolean isAffordable, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(2, "");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        ApprovalStatusRuleOutput output_ = new ApprovalStatusRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            Boolean.TRUE,
            (stringEqual(riskCategory, "High")),
            Boolean.TRUE
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setApprovalStatus("Declined");
            output_.setApprovalStatusPriority(1);

            // Add annotation
            annotationSet_.addAnnotation("'Approval Status'", 2, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 3, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule3(java.math.BigDecimal age, String riskCategory, Boolean isAffordable, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(3, "");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        ApprovalStatusRuleOutput output_ = new ApprovalStatusRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            Boolean.TRUE,
            Boolean.TRUE,
            (booleanEqual(isAffordable, Boolean.FALSE))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setApprovalStatus("Declined");
            output_.setApprovalStatusPriority(1);

            // Add annotation
            annotationSet_.addAnnotation("'Approval Status'", 3, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

}
