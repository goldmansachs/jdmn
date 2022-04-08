
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"bkm.ftl", "'Pre-bureauRiskCategoryTable'"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "'Pre-bureauRiskCategoryTable'",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNIQUE,
    rulesCount = 8
)
public class PreBureauRiskCategoryTable extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "'Pre-bureauRiskCategoryTable'",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
        com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
        com.gs.dmn.runtime.annotation.HitPolicy.UNIQUE,
        8
    );

    private static class PreBureauRiskCategoryTableLazyHolder {
        static final PreBureauRiskCategoryTable INSTANCE = new PreBureauRiskCategoryTable();
    }
    public static PreBureauRiskCategoryTable instance() {
        return PreBureauRiskCategoryTableLazyHolder.INSTANCE;
    }

    private PreBureauRiskCategoryTable() {
    }

    public String apply(Boolean existingCustomer, java.math.BigDecimal applicationRiskScore, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start BKM ''Pre-bureauRiskCategoryTable''
            long preBureauRiskCategoryTableStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments preBureauRiskCategoryTableArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            preBureauRiskCategoryTableArguments_.put("ExistingCustomer", existingCustomer);
            preBureauRiskCategoryTableArguments_.put("ApplicationRiskScore", applicationRiskScore);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, preBureauRiskCategoryTableArguments_);

            // Evaluate BKM ''Pre-bureauRiskCategoryTable''
            String output_ = lambda.apply(existingCustomer, applicationRiskScore, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End BKM ''Pre-bureauRiskCategoryTable''
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, preBureauRiskCategoryTableArguments_, output_, (System.currentTimeMillis() - preBureauRiskCategoryTableStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in ''Pre-bureauRiskCategoryTable'' evaluation", e);
            return null;
        }
    }

    public com.gs.dmn.runtime.LambdaExpression<String> lambda =
        new com.gs.dmn.runtime.LambdaExpression<String>() {
            public String apply(Object... args_) {
                Boolean existingCustomer = 0 < args_.length ? (Boolean) args_[0] : null;
                java.math.BigDecimal applicationRiskScore = 1 < args_.length ? (java.math.BigDecimal) args_[1] : null;
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = 2 < args_.length ? (com.gs.dmn.runtime.annotation.AnnotationSet) args_[2] : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = 3 < args_.length ? (com.gs.dmn.runtime.listener.EventListener) args_[3] : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = 4 < args_.length ? (com.gs.dmn.runtime.external.ExternalFunctionExecutor) args_[4] : null;
                com.gs.dmn.runtime.cache.Cache cache_ = 5 < args_.length ? (com.gs.dmn.runtime.cache.Cache) args_[5] : null;

                // Apply rules and collect results
                com.gs.dmn.runtime.RuleOutputList ruleOutputList_ = new com.gs.dmn.runtime.RuleOutputList();
                ruleOutputList_.add(rule0(existingCustomer, applicationRiskScore, annotationSet_, eventListener_, externalExecutor_, cache_));
                ruleOutputList_.add(rule1(existingCustomer, applicationRiskScore, annotationSet_, eventListener_, externalExecutor_, cache_));
                ruleOutputList_.add(rule2(existingCustomer, applicationRiskScore, annotationSet_, eventListener_, externalExecutor_, cache_));
                ruleOutputList_.add(rule3(existingCustomer, applicationRiskScore, annotationSet_, eventListener_, externalExecutor_, cache_));
                ruleOutputList_.add(rule4(existingCustomer, applicationRiskScore, annotationSet_, eventListener_, externalExecutor_, cache_));
                ruleOutputList_.add(rule5(existingCustomer, applicationRiskScore, annotationSet_, eventListener_, externalExecutor_, cache_));
                ruleOutputList_.add(rule6(existingCustomer, applicationRiskScore, annotationSet_, eventListener_, externalExecutor_, cache_));
                ruleOutputList_.add(rule7(existingCustomer, applicationRiskScore, annotationSet_, eventListener_, externalExecutor_, cache_));

                // Return results based on hit policy
                String output_;
                if (ruleOutputList_.noMatchedRules()) {
                    // Default value
                    output_ = null;
                } else {
                    com.gs.dmn.runtime.RuleOutput ruleOutput_ = ruleOutputList_.applySingle(com.gs.dmn.runtime.annotation.HitPolicy.UNIQUE);
                    output_ = ruleOutput_ == null ? null : ((PreBureauRiskCategoryTableRuleOutput)ruleOutput_).getPreBureauRiskCategoryTable();
                }

                return output_;
            }
    };

    @com.gs.dmn.runtime.annotation.Rule(index = 0, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule0(Boolean existingCustomer, java.math.BigDecimal applicationRiskScore, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(0, "");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        PreBureauRiskCategoryTableRuleOutput output_ = new PreBureauRiskCategoryTableRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (booleanEqual(existingCustomer, Boolean.FALSE)),
            (numericLessThan(applicationRiskScore, number("100")))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setPreBureauRiskCategoryTable("HIGH");

            // Add annotation
            annotationSet_.addAnnotation("'Pre-bureauRiskCategoryTable'", 0, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 1, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule1(Boolean existingCustomer, java.math.BigDecimal applicationRiskScore, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(1, "");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        PreBureauRiskCategoryTableRuleOutput output_ = new PreBureauRiskCategoryTableRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (booleanEqual(existingCustomer, Boolean.FALSE)),
            (booleanAnd(numericGreaterEqualThan(applicationRiskScore, number("100")), numericLessThan(applicationRiskScore, number("120"))))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setPreBureauRiskCategoryTable("MEDIUM");

            // Add annotation
            annotationSet_.addAnnotation("'Pre-bureauRiskCategoryTable'", 1, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 2, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule2(Boolean existingCustomer, java.math.BigDecimal applicationRiskScore, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(2, "");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        PreBureauRiskCategoryTableRuleOutput output_ = new PreBureauRiskCategoryTableRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (booleanEqual(existingCustomer, Boolean.FALSE)),
            (booleanAnd(numericGreaterEqualThan(applicationRiskScore, number("120")), numericLessThan(applicationRiskScore, number("130"))))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setPreBureauRiskCategoryTable("LOW");

            // Add annotation
            annotationSet_.addAnnotation("'Pre-bureauRiskCategoryTable'", 2, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 3, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule3(Boolean existingCustomer, java.math.BigDecimal applicationRiskScore, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(3, "");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        PreBureauRiskCategoryTableRuleOutput output_ = new PreBureauRiskCategoryTableRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (booleanEqual(existingCustomer, Boolean.FALSE)),
            (numericGreaterThan(applicationRiskScore, number("130")))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setPreBureauRiskCategoryTable("VERY LOW");

            // Add annotation
            annotationSet_.addAnnotation("'Pre-bureauRiskCategoryTable'", 3, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 4, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule4(Boolean existingCustomer, java.math.BigDecimal applicationRiskScore, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(4, "");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        PreBureauRiskCategoryTableRuleOutput output_ = new PreBureauRiskCategoryTableRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (booleanEqual(existingCustomer, Boolean.TRUE)),
            (numericLessThan(applicationRiskScore, number("80")))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setPreBureauRiskCategoryTable("DECLINE");

            // Add annotation
            annotationSet_.addAnnotation("'Pre-bureauRiskCategoryTable'", 4, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 5, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule5(Boolean existingCustomer, java.math.BigDecimal applicationRiskScore, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(5, "");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        PreBureauRiskCategoryTableRuleOutput output_ = new PreBureauRiskCategoryTableRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (booleanEqual(existingCustomer, Boolean.TRUE)),
            (booleanAnd(numericGreaterEqualThan(applicationRiskScore, number("80")), numericLessThan(applicationRiskScore, number("90"))))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setPreBureauRiskCategoryTable("HIGH");

            // Add annotation
            annotationSet_.addAnnotation("'Pre-bureauRiskCategoryTable'", 5, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 6, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule6(Boolean existingCustomer, java.math.BigDecimal applicationRiskScore, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(6, "");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        PreBureauRiskCategoryTableRuleOutput output_ = new PreBureauRiskCategoryTableRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (booleanEqual(existingCustomer, Boolean.TRUE)),
            (booleanAnd(numericGreaterEqualThan(applicationRiskScore, number("90")), numericLessEqualThan(applicationRiskScore, number("110"))))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setPreBureauRiskCategoryTable("MEDIUM");

            // Add annotation
            annotationSet_.addAnnotation("'Pre-bureauRiskCategoryTable'", 6, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 7, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule7(Boolean existingCustomer, java.math.BigDecimal applicationRiskScore, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(7, "");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        PreBureauRiskCategoryTableRuleOutput output_ = new PreBureauRiskCategoryTableRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (booleanEqual(existingCustomer, Boolean.TRUE)),
            (numericGreaterThan(applicationRiskScore, number("110")))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setPreBureauRiskCategoryTable("LOW");

            // Add annotation
            annotationSet_.addAnnotation("'Pre-bureauRiskCategoryTable'", 7, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

}
