
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"signavio-decision.ftl", "incompleteDecisionTable"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "incompleteDecisionTable",
    label = "IncompleteDecisionTable",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNIQUE,
    rulesCount = 1
)
public class IncompleteDecisionTable extends com.gs.dmn.signavio.runtime.DefaultSignavioBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "incompleteDecisionTable",
        "IncompleteDecisionTable",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
        com.gs.dmn.runtime.annotation.HitPolicy.UNIQUE,
        1
    );

    public IncompleteDecisionTable() {
    }

    public type.IncompleteDecisionTable apply(String inputString, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(inputString, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
    }

    public type.IncompleteDecisionTable apply(String inputString, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision 'incompleteDecisionTable'
            long incompleteDecisionTableStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments incompleteDecisionTableArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            incompleteDecisionTableArguments_.put("InputString", inputString);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, incompleteDecisionTableArguments_);

            // Evaluate decision 'incompleteDecisionTable'
            type.IncompleteDecisionTable output_ = evaluate(inputString, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision 'incompleteDecisionTable'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, incompleteDecisionTableArguments_, output_, (System.currentTimeMillis() - incompleteDecisionTableStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'incompleteDecisionTable' evaluation", e);
            return null;
        }
    }

    protected type.IncompleteDecisionTable evaluate(String inputString, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        // Apply rules and collect results
        com.gs.dmn.runtime.RuleOutputList ruleOutputList_ = new com.gs.dmn.runtime.RuleOutputList();
        ruleOutputList_.add(rule0(inputString, annotationSet_, eventListener_, externalExecutor_));

        // Return results based on hit policy
        type.IncompleteDecisionTable output_;
        if (ruleOutputList_.noMatchedRules()) {
            // Default value
            output_ = null;
        } else {
            com.gs.dmn.runtime.RuleOutput ruleOutput_ = ruleOutputList_.applySingle(com.gs.dmn.runtime.annotation.HitPolicy.UNIQUE);
            output_ = toDecisionOutput((IncompleteDecisionTableRuleOutput)ruleOutput_);
        }

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 0, annotation = "\"\"")
    public com.gs.dmn.runtime.RuleOutput rule0(String inputString, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(0, "\"\"");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        IncompleteDecisionTableRuleOutput output_ = new IncompleteDecisionTableRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (stringEqual(inputString, "abc"))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setA("def");
            output_.setB(number("123"));

            // Add annotation
            annotationSet_.addAnnotation("incompleteDecisionTable", 0, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    public type.IncompleteDecisionTable toDecisionOutput(IncompleteDecisionTableRuleOutput ruleOutput_) {
        type.IncompleteDecisionTableImpl result_ = new type.IncompleteDecisionTableImpl();
        result_.setA(ruleOutput_ == null ? null : ruleOutput_.getA());
        result_.setB(ruleOutput_ == null ? null : ruleOutput_.getB());
        return result_;
    }
}
