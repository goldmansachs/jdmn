
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
public class Approval extends com.gs.dmn.runtime.JavaTimeDMNBaseDecision<type.TApproval> {
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

    @java.lang.Override()
    public type.TApproval applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply((input_.get("Age") != null ? number(input_.get("Age")) : null), input_.get("RiskCategory"), (input_.get("isAffordable") != null ? Boolean.valueOf(input_.get("isAffordable")) : null), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'Approval'", e);
            return null;
        }
    }

    @java.lang.Override()
    public type.TApproval applyPojo(com.gs.dmn.runtime.ExecutableDRGElementInput input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply(((ApprovalInput_)input_).getAge(), ((ApprovalInput_)input_).getRiskCategory(), ((ApprovalInput_)input_).getIsAffordable(), context_);
        } catch (Exception e) {
            logError("Cannot apply element 'Approval'", e);
            return null;
        }
    }

    @java.lang.Override()
    public type.TApproval applyContext(com.gs.dmn.runtime.Context input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return applyPojo(new ApprovalInput_(input_), context_);
        } catch (Exception e) {
            logError("Cannot apply element 'Approval'", e);
            return null;
        }
    }

    public type.TApproval apply(java.lang.Number age, String riskCategory, Boolean isAffordable, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'Approval'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long approvalStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments approvalArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            approvalArguments_.put("Age", age);
            approvalArguments_.put("RiskCategory", riskCategory);
            approvalArguments_.put("isAffordable", isAffordable);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, approvalArguments_);

            // Evaluate decision 'Approval'
            type.TApproval output_ = lambda.apply(age, riskCategory, isAffordable, context_);

            // End decision 'Approval'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, approvalArguments_, output_, (System.currentTimeMillis() - approvalStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'Approval' evaluation", e);
            return null;
        }
    }

    public com.gs.dmn.runtime.LambdaExpression<type.TApproval> lambda =
        new com.gs.dmn.runtime.LambdaExpression<type.TApproval>() {
            public type.TApproval apply(Object... args_) {
                java.lang.Number age = 0 < args_.length ? (java.lang.Number) args_[0] : null;
                String riskCategory = 1 < args_.length ? (String) args_[1] : null;
                Boolean isAffordable = 2 < args_.length ? (Boolean) args_[2] : null;
                com.gs.dmn.runtime.ExecutionContext context_ = 3 < args_.length ? (com.gs.dmn.runtime.ExecutionContext) args_[3] : null;
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
                com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;

                // Apply rules and collect results
                com.gs.dmn.runtime.RuleOutputList ruleOutputList_ = new com.gs.dmn.runtime.RuleOutputList();
                ruleOutputList_.add(rule0(age, riskCategory, isAffordable, context_));
                ruleOutputList_.add(rule1(age, riskCategory, isAffordable, context_));
                ruleOutputList_.add(rule2(age, riskCategory, isAffordable, context_));
                ruleOutputList_.add(rule3(age, riskCategory, isAffordable, context_));
                ruleOutputList_.add(rule4(age, riskCategory, isAffordable, context_));

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
    };

    @com.gs.dmn.runtime.annotation.Rule(index = 0, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule0(java.lang.Number age, String riskCategory, Boolean isAffordable, com.gs.dmn.runtime.ExecutionContext context_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(0, "");

        // Rule start
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        ApprovalRuleOutput output_ = new ApprovalRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            numericGreaterEqualThan(age, number("18")),
            stringEqual(riskCategory, "Low"),
            booleanEqual(isAffordable, Boolean.TRUE)
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setStatus("Approved");
            output_.setRate("Best");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 1, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule1(java.lang.Number age, String riskCategory, Boolean isAffordable, com.gs.dmn.runtime.ExecutionContext context_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(1, "");

        // Rule start
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        ApprovalRuleOutput output_ = new ApprovalRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            numericGreaterEqualThan(age, number("18")),
            stringEqual(riskCategory, "Medium"),
            booleanEqual(isAffordable, Boolean.TRUE)
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setStatus("Approved");
            output_.setRate("Standard");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 2, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule2(java.lang.Number age, String riskCategory, Boolean isAffordable, com.gs.dmn.runtime.ExecutionContext context_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(2, "");

        // Rule start
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        ApprovalRuleOutput output_ = new ApprovalRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            numericLessThan(age, number("18")),
            booleanOr(stringEqual(riskCategory, "Medium"), stringEqual(riskCategory, "Low")),
            booleanEqual(isAffordable, Boolean.TRUE)
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setStatus("Declined");
            output_.setRate("Standard");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 3, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule3(java.lang.Number age, String riskCategory, Boolean isAffordable, com.gs.dmn.runtime.ExecutionContext context_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(3, "");

        // Rule start
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        ApprovalRuleOutput output_ = new ApprovalRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            Boolean.TRUE,
            stringEqual(riskCategory, "High"),
            booleanEqual(isAffordable, Boolean.TRUE)
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setStatus("Declined");
            output_.setRate("Standard");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 4, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule4(java.lang.Number age, String riskCategory, Boolean isAffordable, com.gs.dmn.runtime.ExecutionContext context_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(4, "");

        // Rule start
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        ApprovalRuleOutput output_ = new ApprovalRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            Boolean.TRUE,
            Boolean.TRUE,
            booleanEqual(isAffordable, Boolean.FALSE)
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setStatus("Declined");
            output_.setRate("Standard");
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
