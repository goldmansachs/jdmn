
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "ageClassification"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "ageClassification",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNIQUE,
    rulesCount = 3
)
public class AgeClassification extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "ageClassification",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
        com.gs.dmn.runtime.annotation.HitPolicy.UNIQUE,
        3
    );

    public AgeClassification() {
    }

    public com.gs.dmn.runtime.Context apply(String student, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply((student != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(student, new com.fasterxml.jackson.core.type.TypeReference<type.StudentImpl>() {}) : null), annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
        } catch (Exception e) {
            logError("Cannot apply decision 'AgeClassification'", e);
            return null;
        }
    }

    public com.gs.dmn.runtime.Context apply(String student, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            return apply((student != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(student, new com.fasterxml.jackson.core.type.TypeReference<type.StudentImpl>() {}) : null), annotationSet_, eventListener_, externalExecutor_, cache_);
        } catch (Exception e) {
            logError("Cannot apply decision 'AgeClassification'", e);
            return null;
        }
    }

    public com.gs.dmn.runtime.Context apply(type.Student student, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(student, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
    }

    public com.gs.dmn.runtime.Context apply(type.Student student, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision 'ageClassification'
            long ageClassificationStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments ageClassificationArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            ageClassificationArguments_.put("student", student);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, ageClassificationArguments_);

            // Evaluate decision 'ageClassification'
            com.gs.dmn.runtime.Context output_ = lambda.apply(student, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision 'ageClassification'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, ageClassificationArguments_, output_, (System.currentTimeMillis() - ageClassificationStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'ageClassification' evaluation", e);
            return null;
        }
    }

    public com.gs.dmn.runtime.LambdaExpression<com.gs.dmn.runtime.Context> lambda =
        new com.gs.dmn.runtime.LambdaExpression<com.gs.dmn.runtime.Context>() {
            public com.gs.dmn.runtime.Context apply(Object... args_) {
                type.Student student = 0 < args_.length ? (type.Student) args_[0] : null;
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = 1 < args_.length ? (com.gs.dmn.runtime.annotation.AnnotationSet) args_[1] : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = 2 < args_.length ? (com.gs.dmn.runtime.listener.EventListener) args_[2] : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = 3 < args_.length ? (com.gs.dmn.runtime.external.ExternalFunctionExecutor) args_[3] : null;
                com.gs.dmn.runtime.cache.Cache cache_ = 4 < args_.length ? (com.gs.dmn.runtime.cache.Cache) args_[4] : null;

                // Apply rules and collect results
                com.gs.dmn.runtime.RuleOutputList ruleOutputList_ = new com.gs.dmn.runtime.RuleOutputList();
                ruleOutputList_.add(rule0(student, annotationSet_, eventListener_, externalExecutor_, cache_));
                ruleOutputList_.add(rule1(student, annotationSet_, eventListener_, externalExecutor_, cache_));
                ruleOutputList_.add(rule2(student, annotationSet_, eventListener_, externalExecutor_, cache_));

                // Return results based on hit policy
                com.gs.dmn.runtime.Context output_;
                if (ruleOutputList_.noMatchedRules()) {
                    // Default value
                    output_ = null;
                } else {
                    com.gs.dmn.runtime.RuleOutput ruleOutput_ = ruleOutputList_.applySingle(com.gs.dmn.runtime.annotation.HitPolicy.UNIQUE);
                    output_ = toDecisionOutput((AgeClassificationRuleOutput)ruleOutput_);
                }

                return output_;
            }
    };

    @com.gs.dmn.runtime.annotation.Rule(index = 0, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule0(type.Student student, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(0, "");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        AgeClassificationRuleOutput output_ = new AgeClassificationRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (numericLessThan(((java.math.BigDecimal)(student != null ? student.getAge() : null)), number("18")))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setCls("Child");
            output_.setDiscount(number("0.1"));

            // Add annotation
            annotationSet_.addAnnotation("ageClassification", 0, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 1, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule1(type.Student student, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(1, "");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        AgeClassificationRuleOutput output_ = new AgeClassificationRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (booleanAnd(numericGreaterEqualThan(((java.math.BigDecimal)(student != null ? student.getAge() : null)), number("18")), numericLessThan(((java.math.BigDecimal)(student != null ? student.getAge() : null)), number("65"))))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setCls("Adult");
            output_.setDiscount(number("0.2"));

            // Add annotation
            annotationSet_.addAnnotation("ageClassification", 1, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 2, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule2(type.Student student, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(2, "");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        AgeClassificationRuleOutput output_ = new AgeClassificationRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (numericGreaterEqualThan(((java.math.BigDecimal)(student != null ? student.getAge() : null)), number("65")))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setCls("Senior");
            output_.setDiscount(number("0.3"));

            // Add annotation
            annotationSet_.addAnnotation("ageClassification", 2, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    public com.gs.dmn.runtime.Context toDecisionOutput(AgeClassificationRuleOutput ruleOutput_) {
        com.gs.dmn.runtime.Context result_ = new com.gs.dmn.runtime.Context();
        result_.put("cls", ruleOutput_ == null ? null : ruleOutput_.getCls());
        result_.put("discount", ruleOutput_ == null ? null : ruleOutput_.getDiscount());
        return result_;
    }
}
