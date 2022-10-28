
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"signavio-decision.ftl", "makeCreditDecision"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "makeCreditDecision",
    label = "Make credit decision",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNIQUE,
    rulesCount = 3
)
public class MakeCreditDecision extends com.gs.dmn.signavio.runtime.DefaultSignavioBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "makeCreditDecision",
        "Make credit decision",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
        com.gs.dmn.runtime.annotation.HitPolicy.UNIQUE,
        3
    );

    public static java.util.Map<String, Object> requestToMap(proto.MakeCreditDecisionRequest makeCreditDecisionRequest_) {
        // Create arguments from Request Message
        type.Applicant applicant = type.Applicant.toApplicant(makeCreditDecisionRequest_.getApplicant());
        java.math.BigDecimal currentRiskAppetite = java.math.BigDecimal.valueOf(makeCreditDecisionRequest_.getCurrentRiskAppetite());
        java.math.BigDecimal lendingThreshold = java.math.BigDecimal.valueOf(makeCreditDecisionRequest_.getLendingThreshold());

        // Create map
        java.util.Map<String, Object> map_ = new java.util.LinkedHashMap<>();
        map_.put("Applicant", applicant);
        map_.put("Current risk appetite", currentRiskAppetite);
        map_.put("Lending threshold", lendingThreshold);
        return map_;
    }

    public static String responseToOutput(proto.MakeCreditDecisionResponse makeCreditDecisionResponse_) {
        // Extract and convert output
        return makeCreditDecisionResponse_.getMakeCreditDecision();
    }

    private final CompareAgainstLendingThreshold compareAgainstLendingThreshold;

    public MakeCreditDecision() {
        this(new CompareAgainstLendingThreshold());
    }

    public MakeCreditDecision(CompareAgainstLendingThreshold compareAgainstLendingThreshold) {
        this.compareAgainstLendingThreshold = compareAgainstLendingThreshold;
    }

    @java.lang.Override()
    public String applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply((input_.get("Applicant") != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(input_.get("Applicant"), new com.fasterxml.jackson.core.type.TypeReference<type.ApplicantImpl>() {}) : null), (input_.get("Current risk appetite") != null ? number(input_.get("Current risk appetite")) : null), (input_.get("Lending threshold") != null ? number(input_.get("Lending threshold")) : null), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'MakeCreditDecision'", e);
            return null;
        }
    }

    public String apply(type.Applicant applicant, java.math.BigDecimal currentRiskAppetite, java.math.BigDecimal lendingThreshold, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'makeCreditDecision'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long makeCreditDecisionStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments makeCreditDecisionArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            makeCreditDecisionArguments_.put("Applicant", applicant);
            makeCreditDecisionArguments_.put("Current risk appetite", currentRiskAppetite);
            makeCreditDecisionArguments_.put("Lending threshold", lendingThreshold);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, makeCreditDecisionArguments_);

            // Evaluate decision 'makeCreditDecision'
            String output_ = evaluate(applicant, currentRiskAppetite, lendingThreshold, context_);

            // End decision 'makeCreditDecision'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, makeCreditDecisionArguments_, output_, (System.currentTimeMillis() - makeCreditDecisionStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'makeCreditDecision' evaluation", e);
            return null;
        }
    }

    public proto.MakeCreditDecisionResponse applyProto(proto.MakeCreditDecisionRequest makeCreditDecisionRequest_, com.gs.dmn.runtime.ExecutionContext context_) {
        // Create arguments from Request Message
        type.Applicant applicant = type.Applicant.toApplicant(makeCreditDecisionRequest_.getApplicant());
        java.math.BigDecimal currentRiskAppetite = java.math.BigDecimal.valueOf(makeCreditDecisionRequest_.getCurrentRiskAppetite());
        java.math.BigDecimal lendingThreshold = java.math.BigDecimal.valueOf(makeCreditDecisionRequest_.getLendingThreshold());

        // Invoke apply method
        String output_ = apply(applicant, currentRiskAppetite, lendingThreshold, context_);

        // Convert output to Response Message
        proto.MakeCreditDecisionResponse.Builder builder_ = proto.MakeCreditDecisionResponse.newBuilder();
        String outputProto_ = (output_ == null ? "" : output_);
        builder_.setMakeCreditDecision(outputProto_);
        return builder_.build();
    }

    protected String evaluate(type.Applicant applicant, java.math.BigDecimal currentRiskAppetite, java.math.BigDecimal lendingThreshold, com.gs.dmn.runtime.ExecutionContext context_) {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        // Apply child decisions
        java.math.BigDecimal compareAgainstLendingThreshold = this.compareAgainstLendingThreshold.apply(applicant, currentRiskAppetite, lendingThreshold, context_);

        // Apply rules and collect results
        com.gs.dmn.runtime.RuleOutputList ruleOutputList_ = new com.gs.dmn.runtime.RuleOutputList();
        ruleOutputList_.add(rule0(compareAgainstLendingThreshold, context_));
        ruleOutputList_.add(rule1(compareAgainstLendingThreshold, context_));
        ruleOutputList_.add(rule2(compareAgainstLendingThreshold, context_));

        // Return results based on hit policy
        String output_;
        if (ruleOutputList_.noMatchedRules()) {
            // Default value
            output_ = null;
        } else {
            com.gs.dmn.runtime.RuleOutput ruleOutput_ = ruleOutputList_.applySingle(com.gs.dmn.runtime.annotation.HitPolicy.UNIQUE);
            output_ = ruleOutput_ == null ? null : ((MakeCreditDecisionRuleOutput)ruleOutput_).getMakeCreditDecision();
        }

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 0, annotation = "\"\"")
    public com.gs.dmn.runtime.RuleOutput rule0(java.math.BigDecimal compareAgainstLendingThreshold, com.gs.dmn.runtime.ExecutionContext context_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(0, "\"\"");

        // Rule start
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        MakeCreditDecisionRuleOutput output_ = new MakeCreditDecisionRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (numericLessThan(compareAgainstLendingThreshold, numericUnaryMinus(number("0.1"))))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setMakeCreditDecision("Reject");

            // Add annotation
            annotationSet_.addAnnotation("makeCreditDecision", 0, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 1, annotation = "\"\"")
    public com.gs.dmn.runtime.RuleOutput rule1(java.math.BigDecimal compareAgainstLendingThreshold, com.gs.dmn.runtime.ExecutionContext context_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(1, "\"\"");

        // Rule start
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        MakeCreditDecisionRuleOutput output_ = new MakeCreditDecisionRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (booleanAnd(numericGreaterEqualThan(compareAgainstLendingThreshold, numericUnaryMinus(number("0.1"))), numericLessEqualThan(compareAgainstLendingThreshold, number("0.1"))))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setMakeCreditDecision("Recommend further assessment");

            // Add annotation
            annotationSet_.addAnnotation("makeCreditDecision", 1, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 2, annotation = "\"\"")
    public com.gs.dmn.runtime.RuleOutput rule2(java.math.BigDecimal compareAgainstLendingThreshold, com.gs.dmn.runtime.ExecutionContext context_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(2, "\"\"");

        // Rule start
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        MakeCreditDecisionRuleOutput output_ = new MakeCreditDecisionRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (numericGreaterThan(compareAgainstLendingThreshold, number("0.1")))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setMakeCreditDecision("Accept");

            // Add annotation
            annotationSet_.addAnnotation("makeCreditDecision", 2, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

}
