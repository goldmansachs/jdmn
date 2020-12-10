
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"signavio-decision.ftl", "body"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "body",
    label = "Body ",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNIQUE,
    rulesCount = 3
)
public class Body extends com.gs.dmn.signavio.runtime.DefaultSignavioBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "body",
        "Body ",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
        com.gs.dmn.runtime.annotation.HitPolicy.UNIQUE,
        3
    );

    public Body() {
    }

    public java.math.BigDecimal apply(String it_iterator, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply((it_iterator != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(it_iterator, new com.fasterxml.jackson.core.type.TypeReference<type.ItImpl>() {}) : null), annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
        } catch (Exception e) {
            logError("Cannot apply decision 'Body'", e);
            return null;
        }
    }

    public java.math.BigDecimal apply(String it_iterator, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            return apply((it_iterator != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(it_iterator, new com.fasterxml.jackson.core.type.TypeReference<type.ItImpl>() {}) : null), annotationSet_, eventListener_, externalExecutor_, cache_);
        } catch (Exception e) {
            logError("Cannot apply decision 'Body'", e);
            return null;
        }
    }

    public java.math.BigDecimal apply(type.It it_iterator, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(it_iterator, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
    }

    public java.math.BigDecimal apply(type.It it_iterator, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision 'body'
            long bodyStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments bodyArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            bodyArguments_.put("it", it_iterator);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, bodyArguments_);

            // Evaluate decision 'body'
            java.math.BigDecimal output_ = evaluate(it_iterator, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision 'body'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, bodyArguments_, output_, (System.currentTimeMillis() - bodyStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'body' evaluation", e);
            return null;
        }
    }

    protected java.math.BigDecimal evaluate(type.It it_iterator, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        // Apply rules and collect results
        com.gs.dmn.runtime.RuleOutputList ruleOutputList_ = new com.gs.dmn.runtime.RuleOutputList();
        ruleOutputList_.add(rule0(it_iterator, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule1(it_iterator, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule2(it_iterator, annotationSet_, eventListener_, externalExecutor_));

        // Return results based on hit policy
        java.math.BigDecimal output_;
        if (ruleOutputList_.noMatchedRules()) {
            // Default value
            output_ = null;
        } else {
            com.gs.dmn.runtime.RuleOutput ruleOutput_ = ruleOutputList_.applySingle(com.gs.dmn.runtime.annotation.HitPolicy.UNIQUE);
            output_ = ruleOutput_ == null ? null : ((BodyRuleOutput)ruleOutput_).getBody();
        }

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 0, annotation = "\"\"")
    public com.gs.dmn.runtime.RuleOutput rule0(type.It it_iterator, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(0, "\"\"");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        BodyRuleOutput output_ = new BodyRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (numericLessThan(((java.math.BigDecimal)(it_iterator != null ? it_iterator.getA() : null)), number("0"))),
            (numericLessThan(((java.math.BigDecimal)(it_iterator != null ? it_iterator.getB() : null)), number("0")))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setBody(numericUnaryMinus(number("1")));

            // Add annotation
            annotationSet_.addAnnotation("body", 0, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 1, annotation = "\"\"")
    public com.gs.dmn.runtime.RuleOutput rule1(type.It it_iterator, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(1, "\"\"");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        BodyRuleOutput output_ = new BodyRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (numericGreaterThan(((java.math.BigDecimal)(it_iterator != null ? it_iterator.getA() : null)), number("0"))),
            (numericGreaterThan(((java.math.BigDecimal)(it_iterator != null ? it_iterator.getB() : null)), number("0")))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setBody(number("1"));

            // Add annotation
            annotationSet_.addAnnotation("body", 1, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 2, annotation = "\"\"")
    public com.gs.dmn.runtime.RuleOutput rule2(type.It it_iterator, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(2, "\"\"");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        BodyRuleOutput output_ = new BodyRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (numericEqual(((java.math.BigDecimal)(it_iterator != null ? it_iterator.getA() : null)), number("0"))),
            (numericEqual(((java.math.BigDecimal)(it_iterator != null ? it_iterator.getB() : null)), number("0")))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setBody(number("0"));

            // Add annotation
            annotationSet_.addAnnotation("body", 2, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

}
