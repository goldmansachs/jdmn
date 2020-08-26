
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"signavio-decision.ftl", "compile"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "compile",
    label = "compile",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.COLLECT,
    rulesCount = 8
)
public class Compile extends com.gs.dmn.signavio.runtime.DefaultSignavioBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "compile",
        "compile",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
        com.gs.dmn.runtime.annotation.HitPolicy.COLLECT,
        8
    );

    public Compile() {
    }

    public List<type.Compile> apply(String name, String numbers, String trafficLight, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply(name, (numbers != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(numbers, new com.fasterxml.jackson.core.type.TypeReference<List<java.math.BigDecimal>>() {}) : null), (trafficLight != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(trafficLight, new com.fasterxml.jackson.core.type.TypeReference<List<String>>() {}) : null), annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
        } catch (Exception e) {
            logError("Cannot apply decision 'Compile'", e);
            return null;
        }
    }

    public List<type.Compile> apply(String name, String numbers, String trafficLight, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            return apply(name, (numbers != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(numbers, new com.fasterxml.jackson.core.type.TypeReference<List<java.math.BigDecimal>>() {}) : null), (trafficLight != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(trafficLight, new com.fasterxml.jackson.core.type.TypeReference<List<String>>() {}) : null), annotationSet_, eventListener_, externalExecutor_, cache_);
        } catch (Exception e) {
            logError("Cannot apply decision 'Compile'", e);
            return null;
        }
    }

    public List<type.Compile> apply(String name, List<java.math.BigDecimal> numbers, List<String> trafficLight, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(name, numbers, trafficLight, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
    }

    public List<type.Compile> apply(String name, List<java.math.BigDecimal> numbers, List<String> trafficLight, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision 'compile'
            long compileStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments compileArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            compileArguments_.put("name", name);
            compileArguments_.put("numbers", numbers);
            compileArguments_.put("Traffic Light", trafficLight);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, compileArguments_);

            // Evaluate decision 'compile'
            List<type.Compile> output_ = evaluate(name, numbers, trafficLight, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision 'compile'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, compileArguments_, output_, (System.currentTimeMillis() - compileStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'compile' evaluation", e);
            return null;
        }
    }

    protected List<type.Compile> evaluate(String name, List<java.math.BigDecimal> numbers, List<String> trafficLight, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        // Apply rules and collect results
        com.gs.dmn.runtime.RuleOutputList ruleOutputList_ = new com.gs.dmn.runtime.RuleOutputList();
        ruleOutputList_.add(rule0(name, numbers, trafficLight, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule1(name, numbers, trafficLight, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule2(name, numbers, trafficLight, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule3(name, numbers, trafficLight, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule4(name, numbers, trafficLight, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule5(name, numbers, trafficLight, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule6(name, numbers, trafficLight, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule7(name, numbers, trafficLight, annotationSet_, eventListener_, externalExecutor_));

        // Return results based on hit policy
        List<type.Compile> output_;
        if (ruleOutputList_.noMatchedRules()) {
            // Default value
            output_ = null;
            if (output_ == null) {
                output_ = asList();
            }
        } else {
            List<? extends com.gs.dmn.runtime.RuleOutput> ruleOutputs_ = ruleOutputList_.applyMultiple(com.gs.dmn.runtime.annotation.HitPolicy.COLLECT);
            output_ = ruleOutputs_.stream().map(o -> toDecisionOutput(((CompileRuleOutput)o))).collect(Collectors.toList());
        }

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 0, annotation = "\"\"")
    public com.gs.dmn.runtime.RuleOutput rule0(String name, List<java.math.BigDecimal> numbers, List<String> trafficLight, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(0, "\"\"");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        CompileRuleOutput output_ = new CompileRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            Boolean.TRUE,
            Boolean.TRUE,
            (listEqual(trafficLight, asList("Red")))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setNextTrafficLight("Yellow");
            output_.setAvgOfNumbers(avg(numbers));
            output_.setName(left(name, number("1")));

            // Add annotation
            annotationSet_.addAnnotation("compile", 0, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 1, annotation = "\"\"")
    public com.gs.dmn.runtime.RuleOutput rule1(String name, List<java.math.BigDecimal> numbers, List<String> trafficLight, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(1, "\"\"");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        CompileRuleOutput output_ = new CompileRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            Boolean.TRUE,
            Boolean.TRUE,
            (listEqual(trafficLight, asList("Yellow")))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setNextTrafficLight("Green");
            output_.setAvgOfNumbers(sum(numbers));
            output_.setName(lower(name));

            // Add annotation
            annotationSet_.addAnnotation("compile", 1, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 2, annotation = "\"\"")
    public com.gs.dmn.runtime.RuleOutput rule2(String name, List<java.math.BigDecimal> numbers, List<String> trafficLight, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(2, "\"\"");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        CompileRuleOutput output_ = new CompileRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            Boolean.TRUE,
            Boolean.TRUE,
            (listEqual(trafficLight, asList("Green")))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setNextTrafficLight("Red");
            output_.setAvgOfNumbers(count(numbers));
            output_.setName(right(name, number("1")));

            // Add annotation
            annotationSet_.addAnnotation("compile", 2, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 3, annotation = "\"\"")
    public com.gs.dmn.runtime.RuleOutput rule3(String name, List<java.math.BigDecimal> numbers, List<String> trafficLight, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(3, "\"\"");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        CompileRuleOutput output_ = new CompileRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            Boolean.TRUE,
            Boolean.TRUE,
            (notContainsAny(trafficLight, asList("Red", "Green")))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setNextTrafficLight("Yellow");
            output_.setAvgOfNumbers(max(numbers));
            output_.setName(upper(name));

            // Add annotation
            annotationSet_.addAnnotation("compile", 3, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 4, annotation = "\"\"")
    public com.gs.dmn.runtime.RuleOutput rule4(String name, List<java.math.BigDecimal> numbers, List<String> trafficLight, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(4, "\"\"");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        CompileRuleOutput output_ = new CompileRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            Boolean.TRUE,
            Boolean.TRUE,
            (containsOnly(trafficLight, asList("Red", "Green")))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setNextTrafficLight("Yellow");
            output_.setAvgOfNumbers(median(numbers));
            output_.setName(mid(name, number("1"), number("2")));

            // Add annotation
            annotationSet_.addAnnotation("compile", 4, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 5, annotation = "\"\"")
    public com.gs.dmn.runtime.RuleOutput rule5(String name, List<java.math.BigDecimal> numbers, List<String> trafficLight, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(5, "\"\"");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        CompileRuleOutput output_ = new CompileRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            Boolean.TRUE,
            Boolean.TRUE,
            booleanNot((notContainsAny(trafficLight, asList("Yellow"))))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setNextTrafficLight("Red");
            output_.setAvgOfNumbers(min(numbers));
            output_.setName(name);

            // Add annotation
            annotationSet_.addAnnotation("compile", 5, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 6, annotation = "\"\"")
    public com.gs.dmn.runtime.RuleOutput rule6(String name, List<java.math.BigDecimal> numbers, List<String> trafficLight, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(6, "\"\"");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        CompileRuleOutput output_ = new CompileRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            Boolean.TRUE,
            Boolean.TRUE,
            (containsOnly(trafficLight, asList("Red")))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setNextTrafficLight("Red");
            output_.setAvgOfNumbers(mode(numbers));
            output_.setName(trim(name));

            // Add annotation
            annotationSet_.addAnnotation("compile", 6, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 7, annotation = "\"\"")
    public com.gs.dmn.runtime.RuleOutput rule7(String name, List<java.math.BigDecimal> numbers, List<String> trafficLight, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(7, "\"\"");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        CompileRuleOutput output_ = new CompileRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            Boolean.TRUE,
            Boolean.TRUE,
            (containsOnly(trafficLight, asList("Green")))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setNextTrafficLight("Green");
            output_.setAvgOfNumbers(product(numbers));
            output_.setName(text(avg(numbers), "0"));

            // Add annotation
            annotationSet_.addAnnotation("compile", 7, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    public type.Compile toDecisionOutput(CompileRuleOutput ruleOutput_) {
        type.CompileImpl result_ = new type.CompileImpl();
        result_.setNextTrafficLight(ruleOutput_ == null ? null : ruleOutput_.getNextTrafficLight());
        result_.setAvgOfNumbers(ruleOutput_ == null ? null : ruleOutput_.getAvgOfNumbers());
        result_.setName(ruleOutput_ == null ? null : ruleOutput_.getName());
        return result_;
    }
}
