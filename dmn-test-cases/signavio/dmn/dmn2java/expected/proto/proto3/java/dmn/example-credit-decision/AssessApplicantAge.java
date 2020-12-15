
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"signavio-decision.ftl", "assessApplicantAge"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "assessApplicantAge",
    label = "Assess applicant age",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNIQUE,
    rulesCount = 3
)
public class AssessApplicantAge extends com.gs.dmn.signavio.runtime.DefaultSignavioBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "assessApplicantAge",
        "Assess applicant age",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
        com.gs.dmn.runtime.annotation.HitPolicy.UNIQUE,
        3
    );

    public static java.util.Map<String, Object> requestToMap(proto.AssessApplicantAgeRequest assessApplicantAgeRequest_) {
        // Create arguments from Request Message
        type.Applicant applicant = type.Applicant.toApplicant(assessApplicantAgeRequest_.getApplicant());

        // Create map
        java.util.Map<String, Object> map_ = new java.util.LinkedHashMap<>();
        map_.put("Applicant", applicant);
        return map_;
    }

    public static java.math.BigDecimal responseToOutput(proto.AssessApplicantAgeResponse assessApplicantAgeResponse_) {
        // Extract and convert output
        return java.math.BigDecimal.valueOf(assessApplicantAgeResponse_.getAssessApplicantAge());
    }

    public AssessApplicantAge() {
    }

    public java.math.BigDecimal apply(String applicant, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply((applicant != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(applicant, new com.fasterxml.jackson.core.type.TypeReference<type.ApplicantImpl>() {}) : null), annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
        } catch (Exception e) {
            logError("Cannot apply decision 'AssessApplicantAge'", e);
            return null;
        }
    }

    public java.math.BigDecimal apply(String applicant, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            return apply((applicant != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(applicant, new com.fasterxml.jackson.core.type.TypeReference<type.ApplicantImpl>() {}) : null), annotationSet_, eventListener_, externalExecutor_, cache_);
        } catch (Exception e) {
            logError("Cannot apply decision 'AssessApplicantAge'", e);
            return null;
        }
    }

    public java.math.BigDecimal apply(type.Applicant applicant, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(applicant, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
    }

    public java.math.BigDecimal apply(type.Applicant applicant, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision 'assessApplicantAge'
            long assessApplicantAgeStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments assessApplicantAgeArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            assessApplicantAgeArguments_.put("Applicant", applicant);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, assessApplicantAgeArguments_);

            // Evaluate decision 'assessApplicantAge'
            java.math.BigDecimal output_ = evaluate(applicant, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision 'assessApplicantAge'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, assessApplicantAgeArguments_, output_, (System.currentTimeMillis() - assessApplicantAgeStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'assessApplicantAge' evaluation", e);
            return null;
        }
    }

    public proto.AssessApplicantAgeResponse apply(proto.AssessApplicantAgeRequest assessApplicantAgeRequest_, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(assessApplicantAgeRequest_, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
    }

    public proto.AssessApplicantAgeResponse apply(proto.AssessApplicantAgeRequest assessApplicantAgeRequest_, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        // Create arguments from Request Message
        type.Applicant applicant = type.Applicant.toApplicant(assessApplicantAgeRequest_.getApplicant());

        // Invoke apply method
        java.math.BigDecimal output_ = apply(applicant, annotationSet_, eventListener_, externalExecutor_, cache_);

        // Convert output to Response Message
        proto.AssessApplicantAgeResponse.Builder builder_ = proto.AssessApplicantAgeResponse.newBuilder();
        Double outputProto_ = (output_ == null ? 0.0 : output_.doubleValue());
        builder_.setAssessApplicantAge(outputProto_);
        return builder_.build();
    }

    protected java.math.BigDecimal evaluate(type.Applicant applicant, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        // Apply rules and collect results
        com.gs.dmn.runtime.RuleOutputList ruleOutputList_ = new com.gs.dmn.runtime.RuleOutputList();
        ruleOutputList_.add(rule0(applicant, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule1(applicant, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule2(applicant, annotationSet_, eventListener_, externalExecutor_));

        // Return results based on hit policy
        java.math.BigDecimal output_;
        if (ruleOutputList_.noMatchedRules()) {
            // Default value
            output_ = null;
        } else {
            com.gs.dmn.runtime.RuleOutput ruleOutput_ = ruleOutputList_.applySingle(com.gs.dmn.runtime.annotation.HitPolicy.UNIQUE);
            output_ = ruleOutput_ == null ? null : ((AssessApplicantAgeRuleOutput)ruleOutput_).getAssessApplicantAge();
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
        AssessApplicantAgeRuleOutput output_ = new AssessApplicantAgeRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (numericLessThan(((java.math.BigDecimal)(applicant != null ? applicant.getAge() : null)), number("18")))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setAssessApplicantAge(numericUnaryMinus(number("10")));

            // Add annotation
            annotationSet_.addAnnotation("assessApplicantAge", 0, "");
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
        AssessApplicantAgeRuleOutput output_ = new AssessApplicantAgeRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (booleanAnd(numericGreaterEqualThan(((java.math.BigDecimal)(applicant != null ? applicant.getAge() : null)), number("18")), numericLessEqualThan(((java.math.BigDecimal)(applicant != null ? applicant.getAge() : null)), number("25"))))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setAssessApplicantAge(number("40"));

            // Add annotation
            annotationSet_.addAnnotation("assessApplicantAge", 1, "");
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
        AssessApplicantAgeRuleOutput output_ = new AssessApplicantAgeRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (numericGreaterThan(((java.math.BigDecimal)(applicant != null ? applicant.getAge() : null)), number("25")))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setAssessApplicantAge(number("60"));

            // Add annotation
            annotationSet_.addAnnotation("assessApplicantAge", 2, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

}
