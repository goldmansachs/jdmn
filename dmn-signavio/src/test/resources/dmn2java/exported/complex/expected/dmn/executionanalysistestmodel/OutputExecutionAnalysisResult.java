
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"signavio-decision.ftl", "outputExecutionAnalysisResult"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "outputExecutionAnalysisResult",
    label = "OutputExecutionAnalysisResult",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.COLLECT,
    rulesCount = 10
)
public class OutputExecutionAnalysisResult extends com.gs.dmn.signavio.runtime.DefaultSignavioBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "outputExecutionAnalysisResult",
        "OutputExecutionAnalysisResult",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
        com.gs.dmn.runtime.annotation.HitPolicy.COLLECT,
        10
    );

    public OutputExecutionAnalysisResult() {
    }

    public List<String> apply(String inputValue, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply((inputValue != null ? number(inputValue) : null), annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
        } catch (Exception e) {
            logError("Cannot apply decision 'OutputExecutionAnalysisResult'", e);
            return null;
        }
    }

    public List<String> apply(String inputValue, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            return apply((inputValue != null ? number(inputValue) : null), annotationSet_, eventListener_, externalExecutor_, cache_);
        } catch (Exception e) {
            logError("Cannot apply decision 'OutputExecutionAnalysisResult'", e);
            return null;
        }
    }

    public List<String> apply(java.math.BigDecimal inputValue, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(inputValue, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
    }

    public List<String> apply(java.math.BigDecimal inputValue, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision 'outputExecutionAnalysisResult'
            long outputExecutionAnalysisResultStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments outputExecutionAnalysisResultArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            outputExecutionAnalysisResultArguments_.put("InputValue", inputValue);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, outputExecutionAnalysisResultArguments_);

            // Evaluate decision 'outputExecutionAnalysisResult'
            List<String> output_ = evaluate(inputValue, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision 'outputExecutionAnalysisResult'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, outputExecutionAnalysisResultArguments_, output_, (System.currentTimeMillis() - outputExecutionAnalysisResultStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'outputExecutionAnalysisResult' evaluation", e);
            return null;
        }
    }

    protected List<String> evaluate(java.math.BigDecimal inputValue, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        // Apply rules and collect results
        com.gs.dmn.runtime.RuleOutputList ruleOutputList_ = new com.gs.dmn.runtime.RuleOutputList();
        ruleOutputList_.add(rule0(inputValue, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule1(inputValue, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule2(inputValue, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule3(inputValue, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule4(inputValue, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule5(inputValue, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule6(inputValue, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule7(inputValue, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule8(inputValue, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule9(inputValue, annotationSet_, eventListener_, externalExecutor_));

        // Return results based on hit policy
        List<String> output_;
        if (ruleOutputList_.noMatchedRules()) {
            // Default value
            output_ = null;
            if (output_ == null) {
                output_ = asList();
            }
        } else {
            List<? extends com.gs.dmn.runtime.RuleOutput> ruleOutputs_ = ruleOutputList_.applyMultiple(com.gs.dmn.runtime.annotation.HitPolicy.COLLECT);
            output_ = ruleOutputs_.stream().map(o -> ((OutputExecutionAnalysisResultRuleOutput)o).getOutputExecutionAnalysisResult()).collect(Collectors.toList());
        }

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 0, annotation = "string(\"Message1\")")
    public com.gs.dmn.runtime.RuleOutput rule0(java.math.BigDecimal inputValue, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(0, "string(\"Message1\")");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        OutputExecutionAnalysisResultRuleOutput output_ = new OutputExecutionAnalysisResultRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (numericGreaterEqualThan(inputValue, number("1")))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setOutputExecutionAnalysisResult("Output1");

            // Add annotation
            annotationSet_.addAnnotation("outputExecutionAnalysisResult", 0, string("Message1"));
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 1, annotation = "string(\"Message2\")")
    public com.gs.dmn.runtime.RuleOutput rule1(java.math.BigDecimal inputValue, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(1, "string(\"Message2\")");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        OutputExecutionAnalysisResultRuleOutput output_ = new OutputExecutionAnalysisResultRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (numericGreaterEqualThan(inputValue, number("2")))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setOutputExecutionAnalysisResult("Output2");

            // Add annotation
            annotationSet_.addAnnotation("outputExecutionAnalysisResult", 1, string("Message2"));
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 2, annotation = "string(\"Message3\")")
    public com.gs.dmn.runtime.RuleOutput rule2(java.math.BigDecimal inputValue, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(2, "string(\"Message3\")");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        OutputExecutionAnalysisResultRuleOutput output_ = new OutputExecutionAnalysisResultRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (numericGreaterEqualThan(inputValue, number("3")))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setOutputExecutionAnalysisResult("Output3");

            // Add annotation
            annotationSet_.addAnnotation("outputExecutionAnalysisResult", 2, string("Message3"));
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 3, annotation = "string(\"Message4\")")
    public com.gs.dmn.runtime.RuleOutput rule3(java.math.BigDecimal inputValue, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(3, "string(\"Message4\")");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        OutputExecutionAnalysisResultRuleOutput output_ = new OutputExecutionAnalysisResultRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (numericGreaterEqualThan(inputValue, number("4")))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setOutputExecutionAnalysisResult("Output4");

            // Add annotation
            annotationSet_.addAnnotation("outputExecutionAnalysisResult", 3, string("Message4"));
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 4, annotation = "string(\"Message5\")")
    public com.gs.dmn.runtime.RuleOutput rule4(java.math.BigDecimal inputValue, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(4, "string(\"Message5\")");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        OutputExecutionAnalysisResultRuleOutput output_ = new OutputExecutionAnalysisResultRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (numericGreaterEqualThan(inputValue, number("5")))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setOutputExecutionAnalysisResult("Output5");

            // Add annotation
            annotationSet_.addAnnotation("outputExecutionAnalysisResult", 4, string("Message5"));
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 5, annotation = "string(\"Message6\")")
    public com.gs.dmn.runtime.RuleOutput rule5(java.math.BigDecimal inputValue, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(5, "string(\"Message6\")");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        OutputExecutionAnalysisResultRuleOutput output_ = new OutputExecutionAnalysisResultRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (numericGreaterEqualThan(inputValue, number("6")))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setOutputExecutionAnalysisResult("Output6");

            // Add annotation
            annotationSet_.addAnnotation("outputExecutionAnalysisResult", 5, string("Message6"));
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 6, annotation = "string(\"Message7\")")
    public com.gs.dmn.runtime.RuleOutput rule6(java.math.BigDecimal inputValue, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(6, "string(\"Message7\")");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        OutputExecutionAnalysisResultRuleOutput output_ = new OutputExecutionAnalysisResultRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (numericGreaterEqualThan(inputValue, number("7")))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setOutputExecutionAnalysisResult("Output7");

            // Add annotation
            annotationSet_.addAnnotation("outputExecutionAnalysisResult", 6, string("Message7"));
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 7, annotation = "string(\"Message8\")")
    public com.gs.dmn.runtime.RuleOutput rule7(java.math.BigDecimal inputValue, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(7, "string(\"Message8\")");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        OutputExecutionAnalysisResultRuleOutput output_ = new OutputExecutionAnalysisResultRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (numericGreaterEqualThan(inputValue, number("8")))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setOutputExecutionAnalysisResult("Output8");

            // Add annotation
            annotationSet_.addAnnotation("outputExecutionAnalysisResult", 7, string("Message8"));
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 8, annotation = "string(\"Message9\")")
    public com.gs.dmn.runtime.RuleOutput rule8(java.math.BigDecimal inputValue, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(8, "string(\"Message9\")");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        OutputExecutionAnalysisResultRuleOutput output_ = new OutputExecutionAnalysisResultRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (numericGreaterEqualThan(inputValue, number("9")))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setOutputExecutionAnalysisResult("Output9");

            // Add annotation
            annotationSet_.addAnnotation("outputExecutionAnalysisResult", 8, string("Message9"));
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 9, annotation = "string(\"Message10\")")
    public com.gs.dmn.runtime.RuleOutput rule9(java.math.BigDecimal inputValue, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(9, "string(\"Message10\")");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        OutputExecutionAnalysisResultRuleOutput output_ = new OutputExecutionAnalysisResultRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (numericGreaterEqualThan(inputValue, number("10")))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setOutputExecutionAnalysisResult("Output10");

            // Add annotation
            annotationSet_.addAnnotation("outputExecutionAnalysisResult", 9, string("Message10"));
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

}
