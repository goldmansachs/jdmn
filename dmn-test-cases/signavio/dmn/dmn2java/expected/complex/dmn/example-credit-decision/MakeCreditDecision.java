
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"signavio-decision.ftl", "makeCreditDecision"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "http://www.provider.com/dmn/1.1/diagram/9acf44f2b05343d79fc35140c493c1e0.xml",
    name = "makeCreditDecision",
    label = "Make credit decision",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNIQUE,
    rulesCount = 3
)
public class MakeCreditDecision extends com.gs.dmn.signavio.runtime.JavaTimeSignavioBaseDecision<String> {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "makeCreditDecision",
        "Make credit decision",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
        com.gs.dmn.runtime.annotation.HitPolicy.UNIQUE,
        3
    );

    private final CompareAgainstLendingThreshold compareAgainstLendingThreshold;

    public MakeCreditDecision() {
        this(new CompareAgainstLendingThreshold());
    }

    public MakeCreditDecision(CompareAgainstLendingThreshold compareAgainstLendingThreshold) {
        this.compareAgainstLendingThreshold = compareAgainstLendingThreshold;
    }

    @java.lang.Override()
    public String applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply((input_.get("Applicant") != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(input_.get("Applicant"), new com.fasterxml.jackson.core.type.TypeReference<type.ApplicantImpl>() {}) : null), (input_.get("Current risk appetite") != null ? number(input_.get("Current risk appetite")) : null), (input_.get("Lending threshold") != null ? number(input_.get("Lending threshold")) : null), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'MakeCreditDecision'", e);
            return null;
        }
    }

    public String apply(type.Applicant applicant, java.lang.Number currentRiskAppetite, java.lang.Number lendingThreshold, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'makeCreditDecision'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long makeCreditDecisionStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments makeCreditDecisionArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            makeCreditDecisionArguments_.put("Applicant", applicant);
            makeCreditDecisionArguments_.put("Current risk appetite", currentRiskAppetite);
            makeCreditDecisionArguments_.put("Lending threshold", lendingThreshold);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, makeCreditDecisionArguments_);

            // Evaluate decision 'makeCreditDecision'
            String output_ = evaluate(applicant, currentRiskAppetite, lendingThreshold, context_);

            // End decision 'makeCreditDecision'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, makeCreditDecisionArguments_, output_, (System.currentTimeMillis() - makeCreditDecisionStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'makeCreditDecision' evaluation", e);
            return null;
        }
    }

    protected String evaluate(type.Applicant applicant, java.lang.Number currentRiskAppetite, java.lang.Number lendingThreshold, com.gs.dmn.runtime.ExecutionContext context_) {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        // Apply child decisions
        java.lang.Number compareAgainstLendingThreshold = this.compareAgainstLendingThreshold.apply(applicant, currentRiskAppetite, lendingThreshold, context_);

        // Apply rules and collect results
        com.gs.dmn.runtime.RuleOutputList ruleOutputList_ = new com.gs.dmn.runtime.RuleOutputList();
        ruleOutputList_.add(rule0(compareAgainstLendingThreshold, context_));
        ruleOutputList_.add(rule1(compareAgainstLendingThreshold, context_));
        ruleOutputList_.add(rule2(compareAgainstLendingThreshold, context_));

        // Return results based on hit policy
        String output_;
        if (ruleOutputList_.noMatchedRules()) {
            // Default value
            output_ = null;
        } else {
            com.gs.dmn.runtime.RuleOutput ruleOutput_ = ruleOutputList_.applySingle(com.gs.dmn.runtime.annotation.HitPolicy.UNIQUE);
            output_ = ruleOutput_ == null ? null : ((MakeCreditDecisionRuleOutput)ruleOutput_).getMakeCreditDecision();
        }

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 0, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule0(java.lang.Number compareAgainstLendingThreshold, com.gs.dmn.runtime.ExecutionContext context_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(0, "");

        // Rule start
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        MakeCreditDecisionRuleOutput output_ = new MakeCreditDecisionRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            numericLessThan(compareAgainstLendingThreshold, numericUnaryMinus(number("0.1")))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setMakeCreditDecision("Reject");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 1, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule1(java.lang.Number compareAgainstLendingThreshold, com.gs.dmn.runtime.ExecutionContext context_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(1, "");

        // Rule start
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        MakeCreditDecisionRuleOutput output_ = new MakeCreditDecisionRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            booleanAnd(numericGreaterEqualThan(compareAgainstLendingThreshold, numericUnaryMinus(number("0.1"))), numericLessEqualThan(compareAgainstLendingThreshold, number("0.1")))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setMakeCreditDecision("Recommend further assessment");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 2, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule2(java.lang.Number compareAgainstLendingThreshold, com.gs.dmn.runtime.ExecutionContext context_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(2, "");

        // Rule start
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        MakeCreditDecisionRuleOutput output_ = new MakeCreditDecisionRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            numericGreaterThan(compareAgainstLendingThreshold, number("0.1"))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setMakeCreditDecision("Accept");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

}
