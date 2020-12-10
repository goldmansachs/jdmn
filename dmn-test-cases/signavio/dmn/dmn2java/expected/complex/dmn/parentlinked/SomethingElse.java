
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"signavio-decision.ftl", "somethingElse"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "somethingElse",
    label = "SomethingElse",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNIQUE,
    rulesCount = 1
)
public class SomethingElse extends com.gs.dmn.signavio.runtime.DefaultSignavioBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "somethingElse",
        "SomethingElse",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
        com.gs.dmn.runtime.annotation.HitPolicy.UNIQUE,
        1
    );

    private final ChildObject childObject;

    public SomethingElse() {
        this(new ChildObject());
    }

    public SomethingElse(ChildObject childObject) {
        this.childObject = childObject;
    }

    public java.math.BigDecimal apply(String num, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply((num != null ? number(num) : null), annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
        } catch (Exception e) {
            logError("Cannot apply decision 'SomethingElse'", e);
            return null;
        }
    }

    public java.math.BigDecimal apply(String num, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            return apply((num != null ? number(num) : null), annotationSet_, eventListener_, externalExecutor_, cache_);
        } catch (Exception e) {
            logError("Cannot apply decision 'SomethingElse'", e);
            return null;
        }
    }

    public java.math.BigDecimal apply(java.math.BigDecimal num, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(num, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
    }

    public java.math.BigDecimal apply(java.math.BigDecimal num, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision 'somethingElse'
            long somethingElseStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments somethingElseArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            somethingElseArguments_.put("num", num);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, somethingElseArguments_);

            // Apply child decisions
            java.math.BigDecimal childObject = this.childObject.apply(num, annotationSet_, eventListener_, externalExecutor_, cache_);

            // Evaluate decision 'somethingElse'
            java.math.BigDecimal output_ = evaluate(childObject, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision 'somethingElse'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, somethingElseArguments_, output_, (System.currentTimeMillis() - somethingElseStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'somethingElse' evaluation", e);
            return null;
        }
    }

    protected java.math.BigDecimal evaluate(java.math.BigDecimal childObject, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        // Apply rules and collect results
        com.gs.dmn.runtime.RuleOutputList ruleOutputList_ = new com.gs.dmn.runtime.RuleOutputList();
        ruleOutputList_.add(rule0(childObject, annotationSet_, eventListener_, externalExecutor_));

        // Return results based on hit policy
        java.math.BigDecimal output_;
        if (ruleOutputList_.noMatchedRules()) {
            // Default value
            output_ = null;
        } else {
            com.gs.dmn.runtime.RuleOutput ruleOutput_ = ruleOutputList_.applySingle(com.gs.dmn.runtime.annotation.HitPolicy.UNIQUE);
            output_ = ruleOutput_ == null ? null : ((SomethingElseRuleOutput)ruleOutput_).getSomethingElse();
        }

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 0, annotation = "\"\"")
    public com.gs.dmn.runtime.RuleOutput rule0(java.math.BigDecimal childObject, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(0, "\"\"");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        SomethingElseRuleOutput output_ = new SomethingElseRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            Boolean.TRUE
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setSomethingElse(numericAdd(childObject, number("10")));

            // Add annotation
            annotationSet_.addAnnotation("somethingElse", 0, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

}
