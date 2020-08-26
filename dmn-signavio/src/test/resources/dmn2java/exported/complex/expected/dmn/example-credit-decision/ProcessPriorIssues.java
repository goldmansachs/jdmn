
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"signavio-decision.ftl", "processPriorIssues"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "processPriorIssues",
    label = "Process prior issues",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.COLLECT,
    rulesCount = 5
)
public class ProcessPriorIssues extends com.gs.dmn.signavio.runtime.DefaultSignavioBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "processPriorIssues",
        "Process prior issues",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
        com.gs.dmn.runtime.annotation.HitPolicy.COLLECT,
        5
    );

    public ProcessPriorIssues() {
    }

    public List<java.math.BigDecimal> apply(String applicant, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply((applicant != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(applicant, new com.fasterxml.jackson.core.type.TypeReference<type.ApplicantImpl>() {}) : null), annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
        } catch (Exception e) {
            logError("Cannot apply decision 'ProcessPriorIssues'", e);
            return null;
        }
    }

    public List<java.math.BigDecimal> apply(String applicant, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            return apply((applicant != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(applicant, new com.fasterxml.jackson.core.type.TypeReference<type.ApplicantImpl>() {}) : null), annotationSet_, eventListener_, externalExecutor_, cache_);
        } catch (Exception e) {
            logError("Cannot apply decision 'ProcessPriorIssues'", e);
            return null;
        }
    }

    public List<java.math.BigDecimal> apply(type.Applicant applicant, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(applicant, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
    }

    public List<java.math.BigDecimal> apply(type.Applicant applicant, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision 'processPriorIssues'
            long processPriorIssuesStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments processPriorIssuesArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            processPriorIssuesArguments_.put("Applicant", applicant);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, processPriorIssuesArguments_);

            // Evaluate decision 'processPriorIssues'
            List<java.math.BigDecimal> output_ = evaluate(applicant, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision 'processPriorIssues'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, processPriorIssuesArguments_, output_, (System.currentTimeMillis() - processPriorIssuesStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'processPriorIssues' evaluation", e);
            return null;
        }
    }

    protected List<java.math.BigDecimal> evaluate(type.Applicant applicant, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        // Apply rules and collect results
        com.gs.dmn.runtime.RuleOutputList ruleOutputList_ = new com.gs.dmn.runtime.RuleOutputList();
        ruleOutputList_.add(rule0(applicant, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule1(applicant, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule2(applicant, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule3(applicant, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule4(applicant, annotationSet_, eventListener_, externalExecutor_));

        // Return results based on hit policy
        List<java.math.BigDecimal> output_;
        if (ruleOutputList_.noMatchedRules()) {
            // Default value
            output_ = null;
            if (output_ == null) {
                output_ = asList();
            }
        } else {
            List<? extends com.gs.dmn.runtime.RuleOutput> ruleOutputs_ = ruleOutputList_.applyMultiple(com.gs.dmn.runtime.annotation.HitPolicy.COLLECT);
            output_ = ruleOutputs_.stream().map(o -> ((ProcessPriorIssuesRuleOutput)o).getProcessPriorIssues()).collect(Collectors.toList());
        }

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 0, annotation = "\"\"")
    public com.gs.dmn.runtime.RuleOutput rule0(type.Applicant applicant, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(0, "\"\"");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        ProcessPriorIssuesRuleOutput output_ = new ProcessPriorIssuesRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            booleanNot((notContainsAny(((List<String>)(applicant != null ? applicant.getPriorIssues() : null)), asList("Card rejection", "Late payment"))))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setProcessPriorIssues(numericUnaryMinus(number("10")));

            // Add annotation
            annotationSet_.addAnnotation("processPriorIssues", 0, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 1, annotation = "\"\"")
    public com.gs.dmn.runtime.RuleOutput rule1(type.Applicant applicant, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(1, "\"\"");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        ProcessPriorIssuesRuleOutput output_ = new ProcessPriorIssuesRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            booleanNot((notContainsAny(((List<String>)(applicant != null ? applicant.getPriorIssues() : null)), asList("Default on obligations"))))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setProcessPriorIssues(numericUnaryMinus(number("30")));

            // Add annotation
            annotationSet_.addAnnotation("processPriorIssues", 1, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 2, annotation = "\"\"")
    public com.gs.dmn.runtime.RuleOutput rule2(type.Applicant applicant, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(2, "\"\"");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        ProcessPriorIssuesRuleOutput output_ = new ProcessPriorIssuesRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            booleanNot((notContainsAny(((List<String>)(applicant != null ? applicant.getPriorIssues() : null)), asList("Bankruptcy"))))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setProcessPriorIssues(numericUnaryMinus(number("100")));

            // Add annotation
            annotationSet_.addAnnotation("processPriorIssues", 2, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 3, annotation = "\"\"")
    public com.gs.dmn.runtime.RuleOutput rule3(type.Applicant applicant, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(3, "\"\"");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        ProcessPriorIssuesRuleOutput output_ = new ProcessPriorIssuesRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (notContainsAny(((List<String>)(applicant != null ? applicant.getPriorIssues() : null)), asList("Card rejection", "Late payment", "Default on obligations", "Bankruptcy")))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setProcessPriorIssues(number("50"));

            // Add annotation
            annotationSet_.addAnnotation("processPriorIssues", 3, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 4, annotation = "\"\"")
    public com.gs.dmn.runtime.RuleOutput rule4(type.Applicant applicant, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(4, "\"\"");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        ProcessPriorIssuesRuleOutput output_ = new ProcessPriorIssuesRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            Boolean.TRUE
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setProcessPriorIssues(numericMultiply(count(((List<String>)(applicant != null ? applicant.getPriorIssues() : null))), numericUnaryMinus(number("5"))));

            // Add annotation
            annotationSet_.addAnnotation("processPriorIssues", 4, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

}
