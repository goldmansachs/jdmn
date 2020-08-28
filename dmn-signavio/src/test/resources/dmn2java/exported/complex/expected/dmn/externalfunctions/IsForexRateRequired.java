
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"signavio-decision.ftl", "isForexRateRequired"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "isForexRateRequired",
    label = "IsForexRateRequired",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNIQUE,
    rulesCount = 2
)
public class IsForexRateRequired extends com.gs.dmn.signavio.runtime.DefaultSignavioBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "isForexRateRequired",
        "IsForexRateRequired",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
        com.gs.dmn.runtime.annotation.HitPolicy.UNIQUE,
        2
    );

    public IsForexRateRequired() {
    }

    public Boolean apply(String derivativeType, String taxChargeType, String transactionTaxMetaData, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply(derivativeType, taxChargeType, (transactionTaxMetaData != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(transactionTaxMetaData, new com.fasterxml.jackson.core.type.TypeReference<type.TransactionTaxMetaDataImpl>() {}) : null), annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
        } catch (Exception e) {
            logError("Cannot apply decision 'IsForexRateRequired'", e);
            return null;
        }
    }

    public Boolean apply(String derivativeType, String taxChargeType, String transactionTaxMetaData, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            return apply(derivativeType, taxChargeType, (transactionTaxMetaData != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(transactionTaxMetaData, new com.fasterxml.jackson.core.type.TypeReference<type.TransactionTaxMetaDataImpl>() {}) : null), annotationSet_, eventListener_, externalExecutor_, cache_);
        } catch (Exception e) {
            logError("Cannot apply decision 'IsForexRateRequired'", e);
            return null;
        }
    }

    public Boolean apply(String derivativeType, String taxChargeType, type.TransactionTaxMetaData transactionTaxMetaData, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(derivativeType, taxChargeType, transactionTaxMetaData, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
    }

    public Boolean apply(String derivativeType, String taxChargeType, type.TransactionTaxMetaData transactionTaxMetaData, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision 'isForexRateRequired'
            long isForexRateRequiredStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments isForexRateRequiredArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            isForexRateRequiredArguments_.put("DerivativeType", derivativeType);
            isForexRateRequiredArguments_.put("TaxChargeType", taxChargeType);
            isForexRateRequiredArguments_.put("TransactionTaxMetaData", transactionTaxMetaData);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, isForexRateRequiredArguments_);

            // Evaluate decision 'isForexRateRequired'
            Boolean output_ = evaluate(derivativeType, taxChargeType, transactionTaxMetaData, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision 'isForexRateRequired'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, isForexRateRequiredArguments_, output_, (System.currentTimeMillis() - isForexRateRequiredStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'isForexRateRequired' evaluation", e);
            return null;
        }
    }

    protected Boolean evaluate(String derivativeType, String taxChargeType, type.TransactionTaxMetaData transactionTaxMetaData, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        // Apply rules and collect results
        com.gs.dmn.runtime.RuleOutputList ruleOutputList_ = new com.gs.dmn.runtime.RuleOutputList();
        ruleOutputList_.add(rule0(derivativeType, taxChargeType, transactionTaxMetaData, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule1(derivativeType, taxChargeType, transactionTaxMetaData, annotationSet_, eventListener_, externalExecutor_));

        // Return results based on hit policy
        Boolean output_;
        if (ruleOutputList_.noMatchedRules()) {
            // Default value
            output_ = null;
        } else {
            com.gs.dmn.runtime.RuleOutput ruleOutput_ = ruleOutputList_.applySingle(com.gs.dmn.runtime.annotation.HitPolicy.UNIQUE);
            output_ = ruleOutput_ == null ? null : ((IsForexRateRequiredRuleOutput)ruleOutput_).getIsForexRateRequired();
        }

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 0, annotation = "\"\"")
    public com.gs.dmn.runtime.RuleOutput rule0(String derivativeType, String taxChargeType, type.TransactionTaxMetaData transactionTaxMetaData, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(0, "\"\"");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        IsForexRateRequiredRuleOutput output_ = new IsForexRateRequiredRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (stringEqual(derivativeType, "RIGHTS_WARRANTS")),
            (stringEqual(taxChargeType, "2")),
            (stringEqual(((String)(transactionTaxMetaData != null ? transactionTaxMetaData.getTaxType() : null)), "FTT")),
            (stringEqual(((String)(transactionTaxMetaData != null ? transactionTaxMetaData.getJurisdiction() : null)), "ITALY")),
            (stringEqual(((String)(transactionTaxMetaData != null ? transactionTaxMetaData.getAssetClass() : null)), "DERIVATIVES"))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setIsForexRateRequired(Boolean.TRUE);

            // Add annotation
            annotationSet_.addAnnotation("isForexRateRequired", 0, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 1, annotation = "\"\"")
    public com.gs.dmn.runtime.RuleOutput rule1(String derivativeType, String taxChargeType, type.TransactionTaxMetaData transactionTaxMetaData, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(1, "\"\"");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        IsForexRateRequiredRuleOutput output_ = new IsForexRateRequiredRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            Boolean.TRUE,
            Boolean.TRUE,
            Boolean.TRUE,
            Boolean.TRUE,
            Boolean.TRUE
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setIsForexRateRequired(Boolean.FALSE);

            // Add annotation
            annotationSet_.addAnnotation("isForexRateRequired", 1, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

}
