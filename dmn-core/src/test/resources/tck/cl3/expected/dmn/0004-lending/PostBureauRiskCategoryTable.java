
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"bkm.ftl", "PostBureauRiskCategoryTable"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "PostBureauRiskCategoryTable",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNIQUE,
    rulesCount = 13
)
public class PostBureauRiskCategoryTable extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "PostBureauRiskCategoryTable",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
        com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
        com.gs.dmn.runtime.annotation.HitPolicy.UNIQUE,
        13
    );

    public static final PostBureauRiskCategoryTable INSTANCE = new PostBureauRiskCategoryTable();

    private PostBureauRiskCategoryTable() {
    }

    public static String PostBureauRiskCategoryTable(Boolean existingCustomer, java.math.BigDecimal applicationRiskScore, java.math.BigDecimal creditScore, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        return INSTANCE.apply(existingCustomer, applicationRiskScore, creditScore, annotationSet_, eventListener_, externalExecutor_);
    }

    private String apply(Boolean existingCustomer, java.math.BigDecimal applicationRiskScore, java.math.BigDecimal creditScore, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        try {
            // Start BKM 'PostBureauRiskCategoryTable'
            long postBureauRiskCategoryTableStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments postBureauRiskCategoryTableArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            postBureauRiskCategoryTableArguments_.put("existingCustomer", existingCustomer);
            postBureauRiskCategoryTableArguments_.put("applicationRiskScore", applicationRiskScore);
            postBureauRiskCategoryTableArguments_.put("creditScore", creditScore);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, postBureauRiskCategoryTableArguments_);

            // Evaluate BKM 'PostBureauRiskCategoryTable'
            String output_ = evaluate(existingCustomer, applicationRiskScore, creditScore, annotationSet_, eventListener_, externalExecutor_);

            // End BKM 'PostBureauRiskCategoryTable'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, postBureauRiskCategoryTableArguments_, output_, (System.currentTimeMillis() - postBureauRiskCategoryTableStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'PostBureauRiskCategoryTable' evaluation", e);
            return null;
        }
    }

    private String evaluate(Boolean existingCustomer, java.math.BigDecimal applicationRiskScore, java.math.BigDecimal creditScore, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Apply rules and collect results
        com.gs.dmn.runtime.RuleOutputList ruleOutputList_ = new com.gs.dmn.runtime.RuleOutputList();
        ruleOutputList_.add(rule0(existingCustomer, applicationRiskScore, creditScore, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule1(existingCustomer, applicationRiskScore, creditScore, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule2(existingCustomer, applicationRiskScore, creditScore, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule3(existingCustomer, applicationRiskScore, creditScore, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule4(existingCustomer, applicationRiskScore, creditScore, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule5(existingCustomer, applicationRiskScore, creditScore, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule6(existingCustomer, applicationRiskScore, creditScore, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule7(existingCustomer, applicationRiskScore, creditScore, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule8(existingCustomer, applicationRiskScore, creditScore, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule9(existingCustomer, applicationRiskScore, creditScore, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule10(existingCustomer, applicationRiskScore, creditScore, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule11(existingCustomer, applicationRiskScore, creditScore, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule12(existingCustomer, applicationRiskScore, creditScore, annotationSet_, eventListener_, externalExecutor_));

        // Return results based on hit policy
        String output_;
        if (ruleOutputList_.noMatchedRules()) {
            // Default value
            output_ = null;
        } else {
            com.gs.dmn.runtime.RuleOutput ruleOutput_ = ruleOutputList_.applySingle(com.gs.dmn.runtime.annotation.HitPolicy.UNIQUE);
            output_ = ruleOutput_ == null ? null : ((PostBureauRiskCategoryTableRuleOutput)ruleOutput_).getPostBureauRiskCategoryTable();
        }

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 0, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule0(Boolean existingCustomer, java.math.BigDecimal applicationRiskScore, java.math.BigDecimal creditScore, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(0, "");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        PostBureauRiskCategoryTableRuleOutput output_ = new PostBureauRiskCategoryTableRuleOutput(false);
        if (Boolean.TRUE == booleanAnd(
            (booleanEqual(existingCustomer, Boolean.FALSE)),
            (numericLessThan(applicationRiskScore, number("120"))),
            (numericLessThan(creditScore, number("590")))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setPostBureauRiskCategoryTable("HIGH");

            // Add annotation
            annotationSet_.addAnnotation("PostBureauRiskCategoryTable", 0, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 1, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule1(Boolean existingCustomer, java.math.BigDecimal applicationRiskScore, java.math.BigDecimal creditScore, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(1, "");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        PostBureauRiskCategoryTableRuleOutput output_ = new PostBureauRiskCategoryTableRuleOutput(false);
        if (Boolean.TRUE == booleanAnd(
            (booleanEqual(existingCustomer, Boolean.FALSE)),
            (numericLessThan(applicationRiskScore, number("120"))),
            (booleanAnd(numericGreaterEqualThan(creditScore, number("590")), numericLessEqualThan(creditScore, number("610"))))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setPostBureauRiskCategoryTable("MEDIUM");

            // Add annotation
            annotationSet_.addAnnotation("PostBureauRiskCategoryTable", 1, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 2, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule2(Boolean existingCustomer, java.math.BigDecimal applicationRiskScore, java.math.BigDecimal creditScore, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(2, "");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        PostBureauRiskCategoryTableRuleOutput output_ = new PostBureauRiskCategoryTableRuleOutput(false);
        if (Boolean.TRUE == booleanAnd(
            (booleanEqual(existingCustomer, Boolean.FALSE)),
            (numericLessThan(applicationRiskScore, number("120"))),
            (numericGreaterThan(creditScore, number("610")))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setPostBureauRiskCategoryTable("LOW");

            // Add annotation
            annotationSet_.addAnnotation("PostBureauRiskCategoryTable", 2, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 3, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule3(Boolean existingCustomer, java.math.BigDecimal applicationRiskScore, java.math.BigDecimal creditScore, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(3, "");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        PostBureauRiskCategoryTableRuleOutput output_ = new PostBureauRiskCategoryTableRuleOutput(false);
        if (Boolean.TRUE == booleanAnd(
            (booleanEqual(existingCustomer, Boolean.FALSE)),
            (booleanAnd(numericGreaterEqualThan(applicationRiskScore, number("120")), numericLessEqualThan(applicationRiskScore, number("130")))),
            (numericLessThan(creditScore, number("600")))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setPostBureauRiskCategoryTable("HIGH");

            // Add annotation
            annotationSet_.addAnnotation("PostBureauRiskCategoryTable", 3, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 4, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule4(Boolean existingCustomer, java.math.BigDecimal applicationRiskScore, java.math.BigDecimal creditScore, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(4, "");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        PostBureauRiskCategoryTableRuleOutput output_ = new PostBureauRiskCategoryTableRuleOutput(false);
        if (Boolean.TRUE == booleanAnd(
            (booleanEqual(existingCustomer, Boolean.FALSE)),
            (booleanAnd(numericGreaterEqualThan(applicationRiskScore, number("120")), numericLessEqualThan(applicationRiskScore, number("130")))),
            (booleanAnd(numericGreaterEqualThan(creditScore, number("600")), numericLessEqualThan(creditScore, number("625"))))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setPostBureauRiskCategoryTable("MEDIUM");

            // Add annotation
            annotationSet_.addAnnotation("PostBureauRiskCategoryTable", 4, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 5, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule5(Boolean existingCustomer, java.math.BigDecimal applicationRiskScore, java.math.BigDecimal creditScore, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(5, "");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        PostBureauRiskCategoryTableRuleOutput output_ = new PostBureauRiskCategoryTableRuleOutput(false);
        if (Boolean.TRUE == booleanAnd(
            (booleanEqual(existingCustomer, Boolean.FALSE)),
            (booleanAnd(numericGreaterEqualThan(applicationRiskScore, number("120")), numericLessEqualThan(applicationRiskScore, number("130")))),
            (numericGreaterThan(creditScore, number("625")))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setPostBureauRiskCategoryTable("LOW");

            // Add annotation
            annotationSet_.addAnnotation("PostBureauRiskCategoryTable", 5, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 6, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule6(Boolean existingCustomer, java.math.BigDecimal applicationRiskScore, java.math.BigDecimal creditScore, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(6, "");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        PostBureauRiskCategoryTableRuleOutput output_ = new PostBureauRiskCategoryTableRuleOutput(false);
        if (Boolean.TRUE == booleanAnd(
            (booleanEqual(existingCustomer, Boolean.FALSE)),
            (numericGreaterThan(applicationRiskScore, number("130"))),
            true
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setPostBureauRiskCategoryTable("VERY LOW");

            // Add annotation
            annotationSet_.addAnnotation("PostBureauRiskCategoryTable", 6, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 7, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule7(Boolean existingCustomer, java.math.BigDecimal applicationRiskScore, java.math.BigDecimal creditScore, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(7, "");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        PostBureauRiskCategoryTableRuleOutput output_ = new PostBureauRiskCategoryTableRuleOutput(false);
        if (Boolean.TRUE == booleanAnd(
            (booleanEqual(existingCustomer, Boolean.TRUE)),
            (numericLessEqualThan(applicationRiskScore, number("100"))),
            (numericLessThan(creditScore, number("580")))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setPostBureauRiskCategoryTable("HIGH");

            // Add annotation
            annotationSet_.addAnnotation("PostBureauRiskCategoryTable", 7, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 8, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule8(Boolean existingCustomer, java.math.BigDecimal applicationRiskScore, java.math.BigDecimal creditScore, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(8, "");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        PostBureauRiskCategoryTableRuleOutput output_ = new PostBureauRiskCategoryTableRuleOutput(false);
        if (Boolean.TRUE == booleanAnd(
            (booleanEqual(existingCustomer, Boolean.TRUE)),
            (numericLessEqualThan(applicationRiskScore, number("100"))),
            (booleanAnd(numericGreaterEqualThan(creditScore, number("580")), numericLessEqualThan(creditScore, number("600"))))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setPostBureauRiskCategoryTable("MEDIUM");

            // Add annotation
            annotationSet_.addAnnotation("PostBureauRiskCategoryTable", 8, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 9, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule9(Boolean existingCustomer, java.math.BigDecimal applicationRiskScore, java.math.BigDecimal creditScore, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(9, "");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        PostBureauRiskCategoryTableRuleOutput output_ = new PostBureauRiskCategoryTableRuleOutput(false);
        if (Boolean.TRUE == booleanAnd(
            (booleanEqual(existingCustomer, Boolean.TRUE)),
            (numericLessEqualThan(applicationRiskScore, number("100"))),
            (numericGreaterThan(creditScore, number("600")))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setPostBureauRiskCategoryTable("LOW");

            // Add annotation
            annotationSet_.addAnnotation("PostBureauRiskCategoryTable", 9, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 10, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule10(Boolean existingCustomer, java.math.BigDecimal applicationRiskScore, java.math.BigDecimal creditScore, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(10, "");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        PostBureauRiskCategoryTableRuleOutput output_ = new PostBureauRiskCategoryTableRuleOutput(false);
        if (Boolean.TRUE == booleanAnd(
            (booleanEqual(existingCustomer, Boolean.TRUE)),
            (numericGreaterThan(applicationRiskScore, number("100"))),
            (numericLessThan(creditScore, number("590")))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setPostBureauRiskCategoryTable("HIGH");

            // Add annotation
            annotationSet_.addAnnotation("PostBureauRiskCategoryTable", 10, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 11, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule11(Boolean existingCustomer, java.math.BigDecimal applicationRiskScore, java.math.BigDecimal creditScore, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(11, "");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        PostBureauRiskCategoryTableRuleOutput output_ = new PostBureauRiskCategoryTableRuleOutput(false);
        if (Boolean.TRUE == booleanAnd(
            (booleanEqual(existingCustomer, Boolean.TRUE)),
            (numericGreaterThan(applicationRiskScore, number("100"))),
            (booleanAnd(numericGreaterEqualThan(creditScore, number("590")), numericLessEqualThan(creditScore, number("615"))))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setPostBureauRiskCategoryTable("MEDIUM");

            // Add annotation
            annotationSet_.addAnnotation("PostBureauRiskCategoryTable", 11, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 12, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule12(Boolean existingCustomer, java.math.BigDecimal applicationRiskScore, java.math.BigDecimal creditScore, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(12, "");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        PostBureauRiskCategoryTableRuleOutput output_ = new PostBureauRiskCategoryTableRuleOutput(false);
        if (Boolean.TRUE == booleanAnd(
            (booleanEqual(existingCustomer, Boolean.TRUE)),
            (numericGreaterThan(applicationRiskScore, number("100"))),
            (numericGreaterThan(creditScore, number("615")))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setPostBureauRiskCategoryTable("LOW");

            // Add annotation
            annotationSet_.addAnnotation("PostBureauRiskCategoryTable", 12, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

}
