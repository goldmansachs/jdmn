
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"bkm.ftl", "ApplicationRiskScoreModel"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "ApplicationRiskScoreModel",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.COLLECT,
    rulesCount = 11
)
public class ApplicationRiskScoreModel extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "ApplicationRiskScoreModel",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
        com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
        com.gs.dmn.runtime.annotation.HitPolicy.COLLECT,
        11
    );

    public static final ApplicationRiskScoreModel INSTANCE = new ApplicationRiskScoreModel();

    private ApplicationRiskScoreModel() {
    }

    public static java.math.BigDecimal ApplicationRiskScoreModel(java.math.BigDecimal age, String maritalStatus, String employmentStatus, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        return INSTANCE.apply(age, maritalStatus, employmentStatus, annotationSet_, eventListener_, externalExecutor_);
    }

    private java.math.BigDecimal apply(java.math.BigDecimal age, String maritalStatus, String employmentStatus, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        try {
            // Start BKM 'ApplicationRiskScoreModel'
            long applicationRiskScoreModelStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments applicationRiskScoreModelArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            applicationRiskScoreModelArguments_.put("age", age);
            applicationRiskScoreModelArguments_.put("maritalStatus", maritalStatus);
            applicationRiskScoreModelArguments_.put("employmentStatus", employmentStatus);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, applicationRiskScoreModelArguments_);

            // Evaluate BKM 'ApplicationRiskScoreModel'
            java.math.BigDecimal output_ = evaluate(age, maritalStatus, employmentStatus, annotationSet_, eventListener_, externalExecutor_);

            // End BKM 'ApplicationRiskScoreModel'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, applicationRiskScoreModelArguments_, output_, (System.currentTimeMillis() - applicationRiskScoreModelStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'ApplicationRiskScoreModel' evaluation", e);
            return null;
        }
    }

    private java.math.BigDecimal evaluate(java.math.BigDecimal age, String maritalStatus, String employmentStatus, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Apply rules and collect results
        com.gs.dmn.runtime.RuleOutputList ruleOutputList_ = new com.gs.dmn.runtime.RuleOutputList();
        ruleOutputList_.add(rule0(age, maritalStatus, employmentStatus, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule1(age, maritalStatus, employmentStatus, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule2(age, maritalStatus, employmentStatus, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule3(age, maritalStatus, employmentStatus, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule4(age, maritalStatus, employmentStatus, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule5(age, maritalStatus, employmentStatus, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule6(age, maritalStatus, employmentStatus, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule7(age, maritalStatus, employmentStatus, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule8(age, maritalStatus, employmentStatus, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule9(age, maritalStatus, employmentStatus, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule10(age, maritalStatus, employmentStatus, annotationSet_, eventListener_, externalExecutor_));

        // Return results based on hit policy
        java.math.BigDecimal output_;
        if (ruleOutputList_.noMatchedRules()) {
            // Default value
            output_ = null;
        } else {
            List<? extends com.gs.dmn.runtime.RuleOutput> ruleOutputs_ = ruleOutputList_.applyMultiple(com.gs.dmn.runtime.annotation.HitPolicy.COLLECT);
            output_ = sum(ruleOutputs_.stream().map(o -> ((ApplicationRiskScoreModelRuleOutput)o).getApplicationRiskScoreModel()).collect(Collectors.toList()));
        }

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 0, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule0(java.math.BigDecimal age, String maritalStatus, String employmentStatus, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(0, "");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        ApplicationRiskScoreModelRuleOutput output_ = new ApplicationRiskScoreModelRuleOutput(false);
        if (Boolean.TRUE == booleanAnd(
            (booleanAnd(numericGreaterEqualThan(age, number("18")), numericLessEqualThan(age, number("21")))),
            true,
            true
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setApplicationRiskScoreModel(number("32"));

            // Add annotation
            annotationSet_.addAnnotation("ApplicationRiskScoreModel", 0, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 1, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule1(java.math.BigDecimal age, String maritalStatus, String employmentStatus, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(1, "");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        ApplicationRiskScoreModelRuleOutput output_ = new ApplicationRiskScoreModelRuleOutput(false);
        if (Boolean.TRUE == booleanAnd(
            (booleanAnd(numericGreaterEqualThan(age, number("22")), numericLessEqualThan(age, number("25")))),
            true,
            true
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setApplicationRiskScoreModel(number("35"));

            // Add annotation
            annotationSet_.addAnnotation("ApplicationRiskScoreModel", 1, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 2, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule2(java.math.BigDecimal age, String maritalStatus, String employmentStatus, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(2, "");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        ApplicationRiskScoreModelRuleOutput output_ = new ApplicationRiskScoreModelRuleOutput(false);
        if (Boolean.TRUE == booleanAnd(
            (booleanAnd(numericGreaterEqualThan(age, number("26")), numericLessEqualThan(age, number("35")))),
            true,
            true
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setApplicationRiskScoreModel(number("40"));

            // Add annotation
            annotationSet_.addAnnotation("ApplicationRiskScoreModel", 2, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 3, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule3(java.math.BigDecimal age, String maritalStatus, String employmentStatus, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(3, "");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        ApplicationRiskScoreModelRuleOutput output_ = new ApplicationRiskScoreModelRuleOutput(false);
        if (Boolean.TRUE == booleanAnd(
            (booleanAnd(numericGreaterEqualThan(age, number("36")), numericLessEqualThan(age, number("49")))),
            true,
            true
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setApplicationRiskScoreModel(number("43"));

            // Add annotation
            annotationSet_.addAnnotation("ApplicationRiskScoreModel", 3, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 4, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule4(java.math.BigDecimal age, String maritalStatus, String employmentStatus, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(4, "");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        ApplicationRiskScoreModelRuleOutput output_ = new ApplicationRiskScoreModelRuleOutput(false);
        if (Boolean.TRUE == booleanAnd(
            (numericGreaterEqualThan(age, number("50"))),
            true,
            true
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setApplicationRiskScoreModel(number("48"));

            // Add annotation
            annotationSet_.addAnnotation("ApplicationRiskScoreModel", 4, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 5, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule5(java.math.BigDecimal age, String maritalStatus, String employmentStatus, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(5, "");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        ApplicationRiskScoreModelRuleOutput output_ = new ApplicationRiskScoreModelRuleOutput(false);
        if (Boolean.TRUE == booleanAnd(
            true,
            (stringEqual(maritalStatus, "S")),
            true
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setApplicationRiskScoreModel(number("25"));

            // Add annotation
            annotationSet_.addAnnotation("ApplicationRiskScoreModel", 5, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 6, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule6(java.math.BigDecimal age, String maritalStatus, String employmentStatus, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(6, "");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        ApplicationRiskScoreModelRuleOutput output_ = new ApplicationRiskScoreModelRuleOutput(false);
        if (Boolean.TRUE == booleanAnd(
            true,
            (stringEqual(maritalStatus, "M")),
            true
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setApplicationRiskScoreModel(number("45"));

            // Add annotation
            annotationSet_.addAnnotation("ApplicationRiskScoreModel", 6, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 7, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule7(java.math.BigDecimal age, String maritalStatus, String employmentStatus, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(7, "");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        ApplicationRiskScoreModelRuleOutput output_ = new ApplicationRiskScoreModelRuleOutput(false);
        if (Boolean.TRUE == booleanAnd(
            true,
            true,
            (stringEqual(employmentStatus, "UNEMPLOYED"))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setApplicationRiskScoreModel(number("15"));

            // Add annotation
            annotationSet_.addAnnotation("ApplicationRiskScoreModel", 7, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 8, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule8(java.math.BigDecimal age, String maritalStatus, String employmentStatus, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(8, "");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        ApplicationRiskScoreModelRuleOutput output_ = new ApplicationRiskScoreModelRuleOutput(false);
        if (Boolean.TRUE == booleanAnd(
            true,
            true,
            (stringEqual(employmentStatus, "EMPLOYED"))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setApplicationRiskScoreModel(number("45"));

            // Add annotation
            annotationSet_.addAnnotation("ApplicationRiskScoreModel", 8, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 9, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule9(java.math.BigDecimal age, String maritalStatus, String employmentStatus, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(9, "");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        ApplicationRiskScoreModelRuleOutput output_ = new ApplicationRiskScoreModelRuleOutput(false);
        if (Boolean.TRUE == booleanAnd(
            true,
            true,
            (stringEqual(employmentStatus, "SELF-EMPLOYED"))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setApplicationRiskScoreModel(number("36"));

            // Add annotation
            annotationSet_.addAnnotation("ApplicationRiskScoreModel", 9, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 10, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule10(java.math.BigDecimal age, String maritalStatus, String employmentStatus, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(10, "");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        ApplicationRiskScoreModelRuleOutput output_ = new ApplicationRiskScoreModelRuleOutput(false);
        if (Boolean.TRUE == booleanAnd(
            true,
            true,
            (stringEqual(employmentStatus, "STUDENT"))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setApplicationRiskScoreModel(number("18"));

            // Add annotation
            annotationSet_.addAnnotation("ApplicationRiskScoreModel", 10, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

}
