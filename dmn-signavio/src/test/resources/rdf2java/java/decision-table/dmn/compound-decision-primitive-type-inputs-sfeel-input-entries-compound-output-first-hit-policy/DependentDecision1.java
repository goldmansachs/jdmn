
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"signavio-decision.ftl", "dependentDecision1"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "dependentDecision1",
    label = "Dependent Decision 1",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.FIRST,
    rulesCount = 1
)
public class DependentDecision1 extends com.gs.dmn.signavio.runtime.DefaultSignavioBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "dependentDecision1",
        "Dependent Decision 1",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
        com.gs.dmn.runtime.annotation.HitPolicy.FIRST,
        1
    );

    public DependentDecision1() {
    }

    public type.DependentDecision1 apply(String dD1TextInput, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(dD1TextInput, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
    }

    public type.DependentDecision1 apply(String dD1TextInput, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision 'dependentDecision1'
            long dependentDecision1StartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments dependentDecision1Arguments_ = new com.gs.dmn.runtime.listener.Arguments();
            dependentDecision1Arguments_.put("DD1 Text Input", dD1TextInput);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, dependentDecision1Arguments_);

            // Evaluate decision 'dependentDecision1'
            type.DependentDecision1 output_ = evaluate(dD1TextInput, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision 'dependentDecision1'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, dependentDecision1Arguments_, output_, (System.currentTimeMillis() - dependentDecision1StartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'dependentDecision1' evaluation", e);
            return null;
        }
    }

    protected type.DependentDecision1 evaluate(String dD1TextInput, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        // Apply rules and collect results
        com.gs.dmn.runtime.RuleOutputList ruleOutputList_ = new com.gs.dmn.runtime.RuleOutputList();
        ruleOutputList_.add(rule0(dD1TextInput, annotationSet_, eventListener_, externalExecutor_));

        // Return results based on hit policy
        type.DependentDecision1 output_;
        if (ruleOutputList_.noMatchedRules()) {
            // Default value
            output_ = null;
        } else {
            com.gs.dmn.runtime.RuleOutput ruleOutput_ = ruleOutputList_.applySingle(com.gs.dmn.runtime.annotation.HitPolicy.FIRST);
            output_ = toDecisionOutput((DependentDecision1RuleOutput)ruleOutput_);
        }

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 0, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule0(String dD1TextInput, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(0, "");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        DependentDecision1RuleOutput output_ = new DependentDecision1RuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (stringEqual(dD1TextInput, "a"))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setDD1O1("dd1o1");
            output_.setDD1O2("dd1o2");

            // Add annotation
            annotationSet_.addAnnotation("dependentDecision1", 0, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    public type.DependentDecision1 toDecisionOutput(DependentDecision1RuleOutput ruleOutput_) {
        type.DependentDecision1Impl result_ = new type.DependentDecision1Impl();
        result_.setDD1O1(ruleOutput_ == null ? null : ruleOutput_.getDD1O1());
        result_.setDD1O2(ruleOutput_ == null ? null : ruleOutput_.getDD1O2());
        return result_;
    }
}
