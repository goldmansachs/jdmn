
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"signavio-decision.ftl", "dependentDecision1"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "http://www.provider.com/dmn/1.1/diagram/b1489a504a724a1caf493a6cb5187c2c.xml",
    name = "dependentDecision1",
    label = "Dependent Decision 1",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.FIRST,
    rulesCount = 1
)
public class DependentDecision1 extends com.gs.dmn.signavio.runtime.JavaTimeSignavioBaseDecision<type.DependentDecision1> {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "http://www.provider.com/dmn/1.1/diagram/b1489a504a724a1caf493a6cb5187c2c.xml",
        "dependentDecision1",
        "Dependent Decision 1",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
        com.gs.dmn.runtime.annotation.HitPolicy.FIRST,
        1
    );

    public DependentDecision1() {
    }

    @java.lang.Override()
    public type.DependentDecision1 applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply(input_.get("DD1 Text Input"), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'DependentDecision1'", e);
            return null;
        }
    }

    public type.DependentDecision1 apply(String dd1TextInput, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'dependentDecision1'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long dependentDecision1StartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments dependentDecision1Arguments_ = new com.gs.dmn.runtime.listener.Arguments();
            dependentDecision1Arguments_.put("DD1 Text Input", dd1TextInput);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, dependentDecision1Arguments_);

            // Evaluate decision 'dependentDecision1'
            type.DependentDecision1 output_ = evaluate(dd1TextInput, context_);

            // End decision 'dependentDecision1'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, dependentDecision1Arguments_, output_, (System.currentTimeMillis() - dependentDecision1StartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'dependentDecision1' evaluation", e);
            return null;
        }
    }

    protected type.DependentDecision1 evaluate(String dd1TextInput, com.gs.dmn.runtime.ExecutionContext context_) {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        // Apply rules and collect results
        com.gs.dmn.runtime.RuleOutputList ruleOutputList_ = new com.gs.dmn.runtime.RuleOutputList();
        ruleOutputList_.add(rule0(dd1TextInput, context_));

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
    public com.gs.dmn.runtime.RuleOutput rule0(String dd1TextInput, com.gs.dmn.runtime.ExecutionContext context_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(0, "");

        // Rule start
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        DependentDecision1RuleOutput output_ = new DependentDecision1RuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            stringEqual(dd1TextInput, "a")
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setDD1O1("dd1o1");
            output_.setDD1O2("dd1o2");
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
