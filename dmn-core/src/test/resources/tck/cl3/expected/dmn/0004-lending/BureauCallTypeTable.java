
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"bkm.ftl", "BureauCallTypeTable"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "BureauCallTypeTable",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNIQUE,
    rulesCount = 3
)
public class BureauCallTypeTable extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "BureauCallTypeTable",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
        com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
        com.gs.dmn.runtime.annotation.HitPolicy.UNIQUE,
        3
    );

    public static final BureauCallTypeTable INSTANCE = new BureauCallTypeTable();

    private BureauCallTypeTable() {
    }

    public static String BureauCallTypeTable(String preBureauRiskCategory, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        return INSTANCE.apply(preBureauRiskCategory, annotationSet_, eventListener_, externalExecutor_);
    }

    private String apply(String preBureauRiskCategory, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        try {
            // Start BKM 'BureauCallTypeTable'
            long bureauCallTypeTableStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments bureauCallTypeTableArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            bureauCallTypeTableArguments_.put("preBureauRiskCategory", preBureauRiskCategory);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, bureauCallTypeTableArguments_);

            // Evaluate BKM 'BureauCallTypeTable'
            String output_ = evaluate(preBureauRiskCategory, annotationSet_, eventListener_, externalExecutor_);

            // End BKM 'BureauCallTypeTable'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, bureauCallTypeTableArguments_, output_, (System.currentTimeMillis() - bureauCallTypeTableStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'BureauCallTypeTable' evaluation", e);
            return null;
        }
    }

    private String evaluate(String preBureauRiskCategory, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Apply rules and collect results
        com.gs.dmn.runtime.RuleOutputList ruleOutputList_ = new com.gs.dmn.runtime.RuleOutputList();
        ruleOutputList_.add(rule0(preBureauRiskCategory, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule1(preBureauRiskCategory, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule2(preBureauRiskCategory, annotationSet_, eventListener_, externalExecutor_));

        // Return results based on hit policy
        String output_;
        if (ruleOutputList_.noMatchedRules()) {
            // Default value
            output_ = null;
        } else {
            com.gs.dmn.runtime.RuleOutput ruleOutput_ = ruleOutputList_.applySingle(com.gs.dmn.runtime.annotation.HitPolicy.UNIQUE);
            output_ = ruleOutput_ == null ? null : ((BureauCallTypeTableRuleOutput)ruleOutput_).getBureauCallTypeTable();
        }

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 0, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule0(String preBureauRiskCategory, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(0, "");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        BureauCallTypeTableRuleOutput output_ = new BureauCallTypeTableRuleOutput(false);
        if (Boolean.TRUE == booleanOr((stringEqual(preBureauRiskCategory, "HIGH")), (stringEqual(preBureauRiskCategory, "MEDIUM")))) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setBureauCallTypeTable("FULL");

            // Add annotation
            annotationSet_.addAnnotation("BureauCallTypeTable", 0, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 1, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule1(String preBureauRiskCategory, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(1, "");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        BureauCallTypeTableRuleOutput output_ = new BureauCallTypeTableRuleOutput(false);
        if (Boolean.TRUE == (stringEqual(preBureauRiskCategory, "LOW"))) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setBureauCallTypeTable("MINI");

            // Add annotation
            annotationSet_.addAnnotation("BureauCallTypeTable", 1, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 2, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule2(String preBureauRiskCategory, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(2, "");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        BureauCallTypeTableRuleOutput output_ = new BureauCallTypeTableRuleOutput(false);
        if (Boolean.TRUE == booleanOr((stringEqual(preBureauRiskCategory, "VERY LOW")), (stringEqual(preBureauRiskCategory, "DECLINE")))) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setBureauCallTypeTable("NONE");

            // Add annotation
            annotationSet_.addAnnotation("BureauCallTypeTable", 2, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

}
