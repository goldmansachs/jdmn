
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"signavio-decision.ftl", "decide"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "decide",
    label = "decide",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.ANY,
    rulesCount = 4
)
public class Decide extends com.gs.dmn.signavio.runtime.DefaultSignavioBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "decide",
        "decide",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
        com.gs.dmn.runtime.annotation.HitPolicy.ANY,
        4
    );

    public Decide() {
    }

    public String apply(String properties_iterator, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(properties_iterator, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
    }

    public String apply(String properties_iterator, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision 'decide'
            long decideStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments decideArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            decideArguments_.put("Properties", properties_iterator);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, decideArguments_);

            // Evaluate decision 'decide'
            String output_ = evaluate(properties_iterator, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision 'decide'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, decideArguments_, output_, (System.currentTimeMillis() - decideStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'decide' evaluation", e);
            return null;
        }
    }

    protected String evaluate(String properties_iterator, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        // Apply rules and collect results
        com.gs.dmn.runtime.RuleOutputList ruleOutputList_ = new com.gs.dmn.runtime.RuleOutputList();
        ruleOutputList_.add(rule0(properties_iterator, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule1(properties_iterator, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule2(properties_iterator, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule3(properties_iterator, annotationSet_, eventListener_, externalExecutor_));

        // Return results based on hit policy
        String output_;
        if (ruleOutputList_.noMatchedRules()) {
            // Default value
            output_ = null;
        } else {
            com.gs.dmn.runtime.RuleOutput ruleOutput_ = ruleOutputList_.applySingle(com.gs.dmn.runtime.annotation.HitPolicy.ANY);
            output_ = ruleOutput_ == null ? null : ((DecideRuleOutput)ruleOutput_).getDecide();
        }

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 0, annotation = "\"\"")
    public com.gs.dmn.runtime.RuleOutput rule0(String properties_iterator, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(0, "\"\"");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        DecideRuleOutput output_ = new DecideRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            booleanOr((stringEqual(properties_iterator, "Happy")), (stringEqual(properties_iterator, "Tall")), (stringEqual(properties_iterator, "Quiet")))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setDecide("Accept");

            // Add annotation
            annotationSet_.addAnnotation("decide", 0, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 1, annotation = "\"\"")
    public com.gs.dmn.runtime.RuleOutput rule1(String properties_iterator, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(1, "\"\"");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        DecideRuleOutput output_ = new DecideRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            booleanOr((stringEqual(properties_iterator, "Sad")), (stringEqual(properties_iterator, "Short")), (stringEqual(properties_iterator, "Loud")))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setDecide("Warn");

            // Add annotation
            annotationSet_.addAnnotation("decide", 1, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 2, annotation = "\"\"")
    public com.gs.dmn.runtime.RuleOutput rule2(String properties_iterator, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(2, "\"\"");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        DecideRuleOutput output_ = new DecideRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            booleanNot(booleanOr((stringEqual(properties_iterator, "Happy")), (stringEqual(properties_iterator, "Sad")), (stringEqual(properties_iterator, "Tall")), (stringEqual(properties_iterator, "Short")), (stringEqual(properties_iterator, "Loud")), (stringEqual(properties_iterator, "Quiet"))))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setDecide("Reject");

            // Add annotation
            annotationSet_.addAnnotation("decide", 2, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 3, annotation = "\"\"")
    public com.gs.dmn.runtime.RuleOutput rule3(String properties_iterator, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(3, "\"\"");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        DecideRuleOutput output_ = new DecideRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (properties_iterator == null)
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setDecide("Reject");

            // Add annotation
            annotationSet_.addAnnotation("decide", 3, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

}
