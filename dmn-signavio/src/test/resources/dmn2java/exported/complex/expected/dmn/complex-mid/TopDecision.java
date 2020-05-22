
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

    public Boolean apply(String testPersonType6_iterator, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply((testPersonType6_iterator != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(testPersonType6_iterator, type.TestPersonTypeImpl.class) : null), annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor());
        } catch (Exception e) {
            logError("Cannot apply decision 'TopDecision'", e);
            return null;
        }
    }

    public Boolean apply(String testPersonType6_iterator, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        try {
            return apply((testPersonType6_iterator != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(testPersonType6_iterator, type.TestPersonTypeImpl.class) : null), annotationSet_, eventListener_, externalExecutor_);
        } catch (Exception e) {
            logError("Cannot apply decision 'TopDecision'", e);
            return null;
        }
    }

    public Boolean apply(type.TestPersonType testPersonType6_iterator, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(testPersonType6_iterator, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor());
    }

    public Boolean apply(type.TestPersonType testPersonType6_iterator, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        try {
            // Start decision 'topDecision'
            long topDecisionStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments topDecisionArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            topDecisionArguments_.put("testPersonType6_iterator", testPersonType6_iterator);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, topDecisionArguments_);

            // Apply child decisions
            String decision = this.decision.apply(testPersonType6_iterator, annotationSet_, eventListener_, externalExecutor_);
            List<String> smallMid = this.smallMid.apply(testPersonType6_iterator, annotationSet_, eventListener_, externalExecutor_);

            // Evaluate decision 'topDecision'
            Boolean output_ = evaluate(decision, smallMid, annotationSet_, eventListener_, externalExecutor_);

            // End decision 'topDecision'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, topDecisionArguments_, output_, (System.currentTimeMillis() - topDecisionStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'topDecision' evaluation", e);
            return null;
        }
    }

    protected Boolean evaluate(String decision, List<String> smallMid, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Apply rules and collect results
        com.gs.dmn.runtime.RuleOutputList ruleOutputList_ = new com.gs.dmn.runtime.RuleOutputList();
        ruleOutputList_.add(rule0(decision, smallMid, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule1(decision, smallMid, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule2(decision, smallMid, annotationSet_, eventListener_, externalExecutor_));

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

    @com.gs.dmn.runtime.annotation.Rule(index = 0, annotation = "\"\"")
    public com.gs.dmn.runtime.RuleOutput rule0(String decision, List<String> smallMid, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(0, "\"\"");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        TopDecisionRuleOutput output_ = new TopDecisionRuleOutput(false);
        if (Boolean.TRUE == booleanAnd(
            (stringEqual(decision, "Consider")),
            (containsOnly(smallMid, asList("Accept", "Warn")))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setTopDecision(Boolean.TRUE);

            // Add annotation
            annotationSet_.addAnnotation("topDecision", 0, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 1, annotation = "\"\"")
    public com.gs.dmn.runtime.RuleOutput rule1(String decision, List<String> smallMid, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(1, "\"\"");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        TopDecisionRuleOutput output_ = new TopDecisionRuleOutput(false);
        if (Boolean.TRUE == (stringEqual(decision, "Don't Consider"))) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setTopDecision(Boolean.FALSE);

            // Add annotation
            annotationSet_.addAnnotation("topDecision", 1, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 2, annotation = "\"\"")
    public com.gs.dmn.runtime.RuleOutput rule2(String decision, List<String> smallMid, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(2, "\"\"");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        TopDecisionRuleOutput output_ = new TopDecisionRuleOutput(false);
        if (Boolean.TRUE == booleanNot((notContainsAny(smallMid, asList("Reject"))))) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setTopDecision(Boolean.FALSE);

            // Add annotation
            annotationSet_.addAnnotation("topDecision", 2, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

}
