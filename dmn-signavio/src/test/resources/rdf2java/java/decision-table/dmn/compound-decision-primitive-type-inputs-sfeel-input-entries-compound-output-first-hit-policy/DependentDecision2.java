
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"signavio-decision.ftl", "dependentDecision2"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "dependentDecision2",
    label = "Dependent Decision 2",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.FIRST,
    rulesCount = 1
)
public class DependentDecision2 extends com.gs.dmn.signavio.runtime.DefaultSignavioBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "dependentDecision2",
        "Dependent Decision 2",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
        com.gs.dmn.runtime.annotation.HitPolicy.FIRST,
        1
    );

    public DependentDecision2() {
    }

    public type.DependentDecision2 apply(String dD2NumberInput, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply((dD2NumberInput != null ? number(dD2NumberInput) : null), annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
        } catch (Exception e) {
            logError("Cannot apply decision 'DependentDecision2'", e);
            return null;
        }
    }

    public type.DependentDecision2 apply(String dD2NumberInput, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            return apply((dD2NumberInput != null ? number(dD2NumberInput) : null), annotationSet_, eventListener_, externalExecutor_, cache_);
        } catch (Exception e) {
            logError("Cannot apply decision 'DependentDecision2'", e);
            return null;
        }
    }

    public type.DependentDecision2 apply(java.math.BigDecimal dD2NumberInput, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(dD2NumberInput, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
    }

    public type.DependentDecision2 apply(java.math.BigDecimal dD2NumberInput, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision 'dependentDecision2'
            long dependentDecision2StartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments dependentDecision2Arguments_ = new com.gs.dmn.runtime.listener.Arguments();
            dependentDecision2Arguments_.put("DD2 Number Input", dD2NumberInput);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, dependentDecision2Arguments_);

            // Evaluate decision 'dependentDecision2'
            type.DependentDecision2 output_ = evaluate(dD2NumberInput, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision 'dependentDecision2'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, dependentDecision2Arguments_, output_, (System.currentTimeMillis() - dependentDecision2StartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'dependentDecision2' evaluation", e);
            return null;
        }
    }

    protected type.DependentDecision2 evaluate(java.math.BigDecimal dD2NumberInput, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        // Apply rules and collect results
        com.gs.dmn.runtime.RuleOutputList ruleOutputList_ = new com.gs.dmn.runtime.RuleOutputList();
        ruleOutputList_.add(rule0(dD2NumberInput, annotationSet_, eventListener_, externalExecutor_));

        // Return results based on hit policy
        type.DependentDecision2 output_;
        if (ruleOutputList_.noMatchedRules()) {
            // Default value
            output_ = null;
        } else {
            com.gs.dmn.runtime.RuleOutput ruleOutput_ = ruleOutputList_.applySingle(com.gs.dmn.runtime.annotation.HitPolicy.FIRST);
            output_ = toDecisionOutput((DependentDecision2RuleOutput)ruleOutput_);
        }

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 0, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule0(java.math.BigDecimal dD2NumberInput, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(0, "");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        DependentDecision2RuleOutput output_ = new DependentDecision2RuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (numericEqual(dD2NumberInput, number("1")))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setDD2O1("dd2o1");
            output_.setDD2O2("dd2o2");

            // Add annotation
            annotationSet_.addAnnotation("dependentDecision2", 0, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    public type.DependentDecision2 toDecisionOutput(DependentDecision2RuleOutput ruleOutput_) {
        type.DependentDecision2Impl result_ = new type.DependentDecision2Impl();
        result_.setDD2O1(ruleOutput_ == null ? null : ruleOutput_.getDD2O1());
        result_.setDD2O2(ruleOutput_ == null ? null : ruleOutput_.getDD2O2());
        return result_;
    }
}
