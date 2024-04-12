
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"signavio-decision.ftl", "topDecision"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "topDecision",
    label = "top decision",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.ANY,
    rulesCount = 3
)
public class TopDecision extends com.gs.dmn.signavio.runtime.DefaultSignavioBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "topDecision",
        "top decision",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
        com.gs.dmn.runtime.annotation.HitPolicy.ANY,
        3
    );

    private final Decision decision;
    private final SmallMid smallMid;

    public TopDecision() {
        this(new Decision(), new SmallMid());
    }

    public TopDecision(Decision decision, SmallMid smallMid) {
        this.decision = decision;
        this.smallMid = smallMid;
    }

    @java.lang.Override()
    public Boolean applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply((input_.get("TestPersonType") != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(input_.get("TestPersonType"), new com.fasterxml.jackson.core.type.TypeReference<type.TestPersonTypeImpl>() {}) : null), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'TopDecision'", e);
            return null;
        }
    }

    public Boolean apply(type.TestPersonType testPersonType6_iterator, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'topDecision'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long topDecisionStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments topDecisionArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            topDecisionArguments_.put("TestPersonType", testPersonType6_iterator);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, topDecisionArguments_);

            // Evaluate decision 'topDecision'
            Boolean output_ = evaluate(testPersonType6_iterator, context_);

            // End decision 'topDecision'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, topDecisionArguments_, output_, (System.currentTimeMillis() - topDecisionStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'topDecision' evaluation", e);
            return null;
        }
    }

    protected Boolean evaluate(type.TestPersonType testPersonType6_iterator, com.gs.dmn.runtime.ExecutionContext context_) {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        // Apply child decisions
        String decision = this.decision.apply(testPersonType6_iterator, context_);
        List<String> smallMid = this.smallMid.apply(testPersonType6_iterator, context_);

        // Apply rules and collect results
        com.gs.dmn.runtime.RuleOutputList ruleOutputList_ = new com.gs.dmn.runtime.RuleOutputList();
        ruleOutputList_.add(rule0(decision, smallMid, context_));
        ruleOutputList_.add(rule1(decision, smallMid, context_));
        ruleOutputList_.add(rule2(decision, smallMid, context_));

        // Return results based on hit policy
        Boolean output_;
        if (ruleOutputList_.noMatchedRules()) {
            // Default value
            output_ = null;
        } else {
            com.gs.dmn.runtime.RuleOutput ruleOutput_ = ruleOutputList_.applySingle(com.gs.dmn.runtime.annotation.HitPolicy.ANY);
            output_ = ruleOutput_ == null ? null : ((TopDecisionRuleOutput)ruleOutput_).getTopDecision();
        }

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 0, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule0(String decision, List<String> smallMid, com.gs.dmn.runtime.ExecutionContext context_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(0, "");

        // Rule start
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        TopDecisionRuleOutput output_ = new TopDecisionRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            stringEqual(decision, "Consider"),
            containsOnly(smallMid, asList("Accept", "Warn"))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setTopDecision(Boolean.TRUE);
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 1, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule1(String decision, List<String> smallMid, com.gs.dmn.runtime.ExecutionContext context_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(1, "");

        // Rule start
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        TopDecisionRuleOutput output_ = new TopDecisionRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            stringEqual(decision, "Don't Consider"),
            Boolean.TRUE
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setTopDecision(Boolean.FALSE);
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 2, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule2(String decision, List<String> smallMid, com.gs.dmn.runtime.ExecutionContext context_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(2, "");

        // Rule start
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        TopDecisionRuleOutput output_ = new TopDecisionRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            Boolean.TRUE,
            booleanNot(notContainsAny(smallMid, asList("Reject")))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setTopDecision(Boolean.FALSE);
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

}
