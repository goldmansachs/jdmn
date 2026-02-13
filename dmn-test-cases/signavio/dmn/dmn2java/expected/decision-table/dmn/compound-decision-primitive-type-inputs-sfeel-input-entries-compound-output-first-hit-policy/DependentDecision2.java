
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"signavio-decision.ftl", "dependentDecision2"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "http://www.provider.com/dmn/1.1/diagram/b1489a504a724a1caf493a6cb5187c2c.xml",
    name = "dependentDecision2",
    label = "Dependent Decision 2",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.FIRST,
    rulesCount = 1
)
public class DependentDecision2 extends com.gs.dmn.signavio.runtime.JavaTimeSignavioBaseDecision<type.DependentDecision2> {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "http://www.provider.com/dmn/1.1/diagram/b1489a504a724a1caf493a6cb5187c2c.xml",
        "dependentDecision2",
        "Dependent Decision 2",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
        com.gs.dmn.runtime.annotation.HitPolicy.FIRST,
        1
    );

    public DependentDecision2() {
    }

    @java.lang.Override()
    public type.DependentDecision2 applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply((input_.get("DD2 Number Input") != null ? number(input_.get("DD2 Number Input")) : null), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'DependentDecision2'", e);
            return null;
        }
    }

    public type.DependentDecision2 apply(java.lang.Number dd2NumberInput, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'dependentDecision2'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long dependentDecision2StartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments dependentDecision2Arguments_ = new com.gs.dmn.runtime.listener.Arguments();
            dependentDecision2Arguments_.put("DD2 Number Input", dd2NumberInput);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, dependentDecision2Arguments_);

            // Evaluate decision 'dependentDecision2'
            type.DependentDecision2 output_ = evaluate(dd2NumberInput, context_);

            // End decision 'dependentDecision2'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, dependentDecision2Arguments_, output_, (System.currentTimeMillis() - dependentDecision2StartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'dependentDecision2' evaluation", e);
            return null;
        }
    }

    protected type.DependentDecision2 evaluate(java.lang.Number dd2NumberInput, com.gs.dmn.runtime.ExecutionContext context_) {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        // Apply rules and collect results
        com.gs.dmn.runtime.RuleOutputList ruleOutputList_ = new com.gs.dmn.runtime.RuleOutputList();
        ruleOutputList_.add(rule0(dd2NumberInput, context_));

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
    public com.gs.dmn.runtime.RuleOutput rule0(java.lang.Number dd2NumberInput, com.gs.dmn.runtime.ExecutionContext context_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(0, "");

        // Rule start
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        DependentDecision2RuleOutput output_ = new DependentDecision2RuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            numericEqual(dd2NumberInput, number("1"))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setDD2O1("dd2o1");
            output_.setDD2O2("dd2o2");
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
