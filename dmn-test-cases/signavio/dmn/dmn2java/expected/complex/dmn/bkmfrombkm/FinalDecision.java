
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"signavio-decision.ftl", "finalDecision"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "finalDecision",
    label = "final decision",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.ANY,
    rulesCount = 4
)
public class FinalDecision extends com.gs.dmn.signavio.runtime.JavaTimeSignavioBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "finalDecision",
        "final decision",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
        com.gs.dmn.runtime.annotation.HitPolicy.ANY,
        4
    );

    private final DecisionDate decisionDate;
    private final DecisionTime decisionTime;

    public FinalDecision() {
        this(new DecisionDate(), new DecisionTime());
    }

    public FinalDecision(DecisionDate decisionDate, DecisionTime decisionTime) {
        this.decisionDate = decisionDate;
        this.decisionTime = decisionTime;
    }

    @java.lang.Override()
    public String applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply((input_.get("date input") != null ? date(input_.get("date input")) : null), (input_.get("time input") != null ? time(input_.get("time input")) : null), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'FinalDecision'", e);
            return null;
        }
    }

    public String apply(java.time.LocalDate dateInput, java.time.temporal.TemporalAccessor timeInput, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'finalDecision'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long finalDecisionStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments finalDecisionArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            finalDecisionArguments_.put("date input", dateInput);
            finalDecisionArguments_.put("time input", timeInput);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, finalDecisionArguments_);

            // Evaluate decision 'finalDecision'
            String output_ = evaluate(dateInput, timeInput, context_);

            // End decision 'finalDecision'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, finalDecisionArguments_, output_, (System.currentTimeMillis() - finalDecisionStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'finalDecision' evaluation", e);
            return null;
        }
    }

    protected String evaluate(java.time.LocalDate dateInput, java.time.temporal.TemporalAccessor timeInput, com.gs.dmn.runtime.ExecutionContext context_) {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        // Apply child decisions
        List<String> decisionDate = this.decisionDate.apply(dateInput, context_);
        List<String> decisionTime = this.decisionTime.apply(timeInput, context_);

        // Apply rules and collect results
        com.gs.dmn.runtime.RuleOutputList ruleOutputList_ = new com.gs.dmn.runtime.RuleOutputList();
        ruleOutputList_.add(rule0(decisionDate, decisionTime, context_));
        ruleOutputList_.add(rule1(decisionDate, decisionTime, context_));
        ruleOutputList_.add(rule2(decisionDate, decisionTime, context_));
        ruleOutputList_.add(rule3(decisionDate, decisionTime, context_));

        // Return results based on hit policy
        String output_;
        if (ruleOutputList_.noMatchedRules()) {
            // Default value
            output_ = null;
        } else {
            com.gs.dmn.runtime.RuleOutput ruleOutput_ = ruleOutputList_.applySingle(com.gs.dmn.runtime.annotation.HitPolicy.ANY);
            output_ = ruleOutput_ == null ? null : ((FinalDecisionRuleOutput)ruleOutput_).getFinalDecision();
        }

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 0, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule0(List<String> decisionDate, List<String> decisionTime, com.gs.dmn.runtime.ExecutionContext context_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(0, "");

        // Rule start
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        FinalDecisionRuleOutput output_ = new FinalDecisionRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            Boolean.TRUE,
            Boolean.TRUE,
            booleanEqual(numericGreaterEqualThan(count(decisionDate), number("3")), Boolean.TRUE),
            booleanEqual(numericGreaterEqualThan(count(decisionTime), number("3")), Boolean.TRUE)
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setFinalDecision("good");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 1, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule1(List<String> decisionDate, List<String> decisionTime, com.gs.dmn.runtime.ExecutionContext context_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(1, "");

        // Rule start
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        FinalDecisionRuleOutput output_ = new FinalDecisionRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            Boolean.TRUE,
            Boolean.TRUE,
            booleanEqual(numericGreaterEqualThan(count(decisionDate), number("3")), Boolean.FALSE),
            booleanEqual(numericGreaterEqualThan(count(decisionTime), number("3")), Boolean.TRUE)
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setFinalDecision("mmm");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 2, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule2(List<String> decisionDate, List<String> decisionTime, com.gs.dmn.runtime.ExecutionContext context_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(2, "");

        // Rule start
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        FinalDecisionRuleOutput output_ = new FinalDecisionRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            Boolean.TRUE,
            Boolean.TRUE,
            booleanEqual(numericGreaterEqualThan(count(decisionDate), number("3")), Boolean.TRUE),
            booleanEqual(numericGreaterEqualThan(count(decisionTime), number("3")), Boolean.FALSE)
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setFinalDecision("erhm");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 3, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule3(List<String> decisionDate, List<String> decisionTime, com.gs.dmn.runtime.ExecutionContext context_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(3, "");

        // Rule start
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        FinalDecisionRuleOutput output_ = new FinalDecisionRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            Boolean.TRUE,
            Boolean.TRUE,
            booleanEqual(numericGreaterEqualThan(count(decisionDate), number("3")), Boolean.FALSE),
            booleanEqual(numericGreaterEqualThan(count(decisionTime), number("3")), Boolean.FALSE)
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setFinalDecision("bad");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

}
