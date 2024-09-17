
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
public class ProcessPriorIssues extends com.gs.dmn.signavio.runtime.JavaTimeSignavioBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "processPriorIssues",
        "Process prior issues",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
        com.gs.dmn.runtime.annotation.HitPolicy.COLLECT,
        5
    );

    public static java.util.Map<String, Object> requestToMap(proto.ProcessPriorIssuesRequest processPriorIssuesRequest_) {
        // Create arguments from Request Message
        type.Applicant applicant = type.Applicant.toApplicant(processPriorIssuesRequest_.getApplicant());

        // Create map
        java.util.Map<String, Object> map_ = new java.util.LinkedHashMap<>();
        map_.put("Applicant", applicant);
        return map_;
    }

    public static List<java.lang.Number> responseToOutput(proto.ProcessPriorIssuesResponse processPriorIssuesResponse_) {
        // Extract and convert output
        return ((List<java.lang.Number>) (processPriorIssuesResponse_.getProcessPriorIssuesList() == null ? null : processPriorIssuesResponse_.getProcessPriorIssuesList().stream().map(e -> ((java.lang.Number) java.math.BigDecimal.valueOf(e))).collect(java.util.stream.Collectors.toList())));
    }

    public ProcessPriorIssues() {
    }

    @java.lang.Override()
    public List<java.lang.Number> applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply((input_.get("Applicant") != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(input_.get("Applicant"), new com.fasterxml.jackson.core.type.TypeReference<type.ApplicantImpl>() {}) : null), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'ProcessPriorIssues'", e);
            return null;
        }
    }

    public List<java.lang.Number> apply(type.Applicant applicant, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'processPriorIssues'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long processPriorIssuesStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments processPriorIssuesArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            processPriorIssuesArguments_.put("Applicant", applicant);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, processPriorIssuesArguments_);

            // Evaluate decision 'processPriorIssues'
            List<java.lang.Number> output_ = evaluate(applicant, context_);

            // End decision 'processPriorIssues'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, processPriorIssuesArguments_, output_, (System.currentTimeMillis() - processPriorIssuesStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'processPriorIssues' evaluation", e);
            return null;
        }
    }

    public proto.ProcessPriorIssuesResponse applyProto(proto.ProcessPriorIssuesRequest processPriorIssuesRequest_, com.gs.dmn.runtime.ExecutionContext context_) {
        // Create arguments from Request Message
        type.Applicant applicant = type.Applicant.toApplicant(processPriorIssuesRequest_.getApplicant());

        // Invoke apply method
        List<java.lang.Number> output_ = apply(applicant, context_);

        // Convert output to Response Message
        proto.ProcessPriorIssuesResponse.Builder builder_ = proto.ProcessPriorIssuesResponse.newBuilder();
        List<Double> outputProto_ = ((List) (output_ == null ? null : output_.stream().map(e -> (e == null ? 0.0 : e.doubleValue())).collect(java.util.stream.Collectors.toList())));
        if (outputProto_ != null) {
            builder_.addAllProcessPriorIssues(outputProto_);
        }
        return builder_.build();
    }

    protected List<java.lang.Number> evaluate(type.Applicant applicant, com.gs.dmn.runtime.ExecutionContext context_) {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        // Apply rules and collect results
        com.gs.dmn.runtime.RuleOutputList ruleOutputList_ = new com.gs.dmn.runtime.RuleOutputList();
        ruleOutputList_.add(rule0(applicant, context_));
        ruleOutputList_.add(rule1(applicant, context_));
        ruleOutputList_.add(rule2(applicant, context_));
        ruleOutputList_.add(rule3(applicant, context_));
        ruleOutputList_.add(rule4(applicant, context_));

        // Return results based on hit policy
        List<java.lang.Number> output_;
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

    @com.gs.dmn.runtime.annotation.Rule(index = 0, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule0(type.Applicant applicant, com.gs.dmn.runtime.ExecutionContext context_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(0, "");

        // Rule start
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        ProcessPriorIssuesRuleOutput output_ = new ProcessPriorIssuesRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            booleanNot(notContainsAny(((List<String>)(applicant != null ? applicant.getPriorIssues() : null)), asList("Card rejection", "Late payment")))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setProcessPriorIssues(numericUnaryMinus(number("10")));
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 1, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule1(type.Applicant applicant, com.gs.dmn.runtime.ExecutionContext context_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(1, "");

        // Rule start
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        ProcessPriorIssuesRuleOutput output_ = new ProcessPriorIssuesRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            booleanNot(notContainsAny(((List<String>)(applicant != null ? applicant.getPriorIssues() : null)), asList("Default on obligations")))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setProcessPriorIssues(numericUnaryMinus(number("30")));
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 2, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule2(type.Applicant applicant, com.gs.dmn.runtime.ExecutionContext context_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(2, "");

        // Rule start
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        ProcessPriorIssuesRuleOutput output_ = new ProcessPriorIssuesRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            booleanNot(notContainsAny(((List<String>)(applicant != null ? applicant.getPriorIssues() : null)), asList("Bankruptcy")))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setProcessPriorIssues(numericUnaryMinus(number("100")));
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 3, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule3(type.Applicant applicant, com.gs.dmn.runtime.ExecutionContext context_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(3, "");

        // Rule start
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        ProcessPriorIssuesRuleOutput output_ = new ProcessPriorIssuesRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            notContainsAny(((List<String>)(applicant != null ? applicant.getPriorIssues() : null)), asList("Card rejection", "Late payment", "Default on obligations", "Bankruptcy"))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setProcessPriorIssues(number("50"));
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 4, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule4(type.Applicant applicant, com.gs.dmn.runtime.ExecutionContext context_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(4, "");

        // Rule start
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
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
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

}
