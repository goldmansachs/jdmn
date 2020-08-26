
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"signavio-decision.ftl", "buildDateStringInAnnotation"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "buildDateStringInAnnotation",
    label = "buildDateStringInAnnotation",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNIQUE,
    rulesCount = 1
)
public class BuildDateStringInAnnotation extends com.gs.dmn.signavio.runtime.DefaultSignavioBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "buildDateStringInAnnotation",
        "buildDateStringInAnnotation",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
        com.gs.dmn.runtime.annotation.HitPolicy.UNIQUE,
        1
    );

    public BuildDateStringInAnnotation() {
    }

    public Boolean apply(String day, String month, String year, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply((day != null ? number(day) : null), (month != null ? number(month) : null), (year != null ? number(year) : null), annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
        } catch (Exception e) {
            logError("Cannot apply decision 'BuildDateStringInAnnotation'", e);
            return null;
        }
    }

    public Boolean apply(String day, String month, String year, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            return apply((day != null ? number(day) : null), (month != null ? number(month) : null), (year != null ? number(year) : null), annotationSet_, eventListener_, externalExecutor_, cache_);
        } catch (Exception e) {
            logError("Cannot apply decision 'BuildDateStringInAnnotation'", e);
            return null;
        }
    }

    public Boolean apply(java.math.BigDecimal day, java.math.BigDecimal month, java.math.BigDecimal year, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(day, month, year, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
    }

    public Boolean apply(java.math.BigDecimal day, java.math.BigDecimal month, java.math.BigDecimal year, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision 'buildDateStringInAnnotation'
            long buildDateStringInAnnotationStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments buildDateStringInAnnotationArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            buildDateStringInAnnotationArguments_.put("day", day);
            buildDateStringInAnnotationArguments_.put("month", month);
            buildDateStringInAnnotationArguments_.put("year", year);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, buildDateStringInAnnotationArguments_);

            // Evaluate decision 'buildDateStringInAnnotation'
            Boolean output_ = evaluate(day, month, year, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision 'buildDateStringInAnnotation'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, buildDateStringInAnnotationArguments_, output_, (System.currentTimeMillis() - buildDateStringInAnnotationStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'buildDateStringInAnnotation' evaluation", e);
            return null;
        }
    }

    protected Boolean evaluate(java.math.BigDecimal day, java.math.BigDecimal month, java.math.BigDecimal year, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        // Apply rules and collect results
        com.gs.dmn.runtime.RuleOutputList ruleOutputList_ = new com.gs.dmn.runtime.RuleOutputList();
        ruleOutputList_.add(rule0(day, month, year, annotationSet_, eventListener_, externalExecutor_));

        // Return results based on hit policy
        Boolean output_;
        if (ruleOutputList_.noMatchedRules()) {
            // Default value
            output_ = null;
        } else {
            com.gs.dmn.runtime.RuleOutput ruleOutput_ = ruleOutputList_.applySingle(com.gs.dmn.runtime.annotation.HitPolicy.UNIQUE);
            output_ = ruleOutput_ == null ? null : ((BuildDateStringInAnnotationRuleOutput)ruleOutput_).getAllDefined();
        }

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 0, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule0(java.math.BigDecimal day, java.math.BigDecimal month, java.math.BigDecimal year, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(0, "");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        BuildDateStringInAnnotationRuleOutput output_ = new BuildDateStringInAnnotationRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            Boolean.TRUE,
            Boolean.TRUE,
            Boolean.TRUE
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setAllDefined(Boolean.TRUE);

            // Add annotation
            annotationSet_.addAnnotation("buildDateStringInAnnotation", 0, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

}
