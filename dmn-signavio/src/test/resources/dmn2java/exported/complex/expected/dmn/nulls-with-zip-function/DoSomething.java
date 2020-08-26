
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"signavio-decision.ftl", "doSomething"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "doSomething",
    label = "do something",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNIQUE,
    rulesCount = 4
)
public class DoSomething extends com.gs.dmn.signavio.runtime.DefaultSignavioBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "doSomething",
        "do something",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
        com.gs.dmn.runtime.annotation.HitPolicy.UNIQUE,
        4
    );

    public DoSomething() {
    }

    public String apply(String zip4_iterator, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply((zip4_iterator != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(zip4_iterator, new com.fasterxml.jackson.core.type.TypeReference<type.Zip3Impl>() {}) : null), annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
        } catch (Exception e) {
            logError("Cannot apply decision 'DoSomething'", e);
            return null;
        }
    }

    public String apply(String zip4_iterator, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            return apply((zip4_iterator != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(zip4_iterator, new com.fasterxml.jackson.core.type.TypeReference<type.Zip3Impl>() {}) : null), annotationSet_, eventListener_, externalExecutor_, cache_);
        } catch (Exception e) {
            logError("Cannot apply decision 'DoSomething'", e);
            return null;
        }
    }

    public String apply(type.Zip3 zip4_iterator, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(zip4_iterator, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
    }

    public String apply(type.Zip3 zip4_iterator, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision 'doSomething'
            long doSomethingStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments doSomethingArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            doSomethingArguments_.put("zip", zip4_iterator);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, doSomethingArguments_);

            // Evaluate decision 'doSomething'
            String output_ = evaluate(zip4_iterator, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision 'doSomething'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, doSomethingArguments_, output_, (System.currentTimeMillis() - doSomethingStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'doSomething' evaluation", e);
            return null;
        }
    }

    protected String evaluate(type.Zip3 zip4_iterator, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        // Apply rules and collect results
        com.gs.dmn.runtime.RuleOutputList ruleOutputList_ = new com.gs.dmn.runtime.RuleOutputList();
        ruleOutputList_.add(rule0(zip4_iterator, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule1(zip4_iterator, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule2(zip4_iterator, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule3(zip4_iterator, annotationSet_, eventListener_, externalExecutor_));

        // Return results based on hit policy
        String output_;
        if (ruleOutputList_.noMatchedRules()) {
            // Default value
            output_ = null;
        } else {
            com.gs.dmn.runtime.RuleOutput ruleOutput_ = ruleOutputList_.applySingle(com.gs.dmn.runtime.annotation.HitPolicy.UNIQUE);
            output_ = ruleOutput_ == null ? null : ((DoSomethingRuleOutput)ruleOutput_).getDoSomething();
        }

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 0, annotation = "\"\"")
    public com.gs.dmn.runtime.RuleOutput rule0(type.Zip3 zip4_iterator, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(0, "\"\"");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        DoSomethingRuleOutput output_ = new DoSomethingRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            booleanNot((((String)(zip4_iterator != null ? zip4_iterator.getInputA() : null)) == null)),
            booleanNot((((java.math.BigDecimal)(zip4_iterator != null ? zip4_iterator.getInputB() : null)) == null))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setDoSomething("both defined");

            // Add annotation
            annotationSet_.addAnnotation("doSomething", 0, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 1, annotation = "\"\"")
    public com.gs.dmn.runtime.RuleOutput rule1(type.Zip3 zip4_iterator, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(1, "\"\"");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        DoSomethingRuleOutput output_ = new DoSomethingRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            booleanNot((((String)(zip4_iterator != null ? zip4_iterator.getInputA() : null)) == null)),
            (((java.math.BigDecimal)(zip4_iterator != null ? zip4_iterator.getInputB() : null)) == null)
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setDoSomething("text defined");

            // Add annotation
            annotationSet_.addAnnotation("doSomething", 1, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 2, annotation = "\"\"")
    public com.gs.dmn.runtime.RuleOutput rule2(type.Zip3 zip4_iterator, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(2, "\"\"");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        DoSomethingRuleOutput output_ = new DoSomethingRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (((String)(zip4_iterator != null ? zip4_iterator.getInputA() : null)) == null),
            booleanNot((((java.math.BigDecimal)(zip4_iterator != null ? zip4_iterator.getInputB() : null)) == null))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setDoSomething("number defined");

            // Add annotation
            annotationSet_.addAnnotation("doSomething", 2, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 3, annotation = "\"\"")
    public com.gs.dmn.runtime.RuleOutput rule3(type.Zip3 zip4_iterator, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(3, "\"\"");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        DoSomethingRuleOutput output_ = new DoSomethingRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (((String)(zip4_iterator != null ? zip4_iterator.getInputA() : null)) == null),
            (((java.math.BigDecimal)(zip4_iterator != null ? zip4_iterator.getInputB() : null)) == null)
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setDoSomething("neither defined");

            // Add annotation
            annotationSet_.addAnnotation("doSomething", 3, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

}
