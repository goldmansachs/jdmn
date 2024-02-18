
import java.util.*;
import java.util.stream.Collectors;

@jakarta.annotation.Generated(value = {"signavio-decision.ftl", "buildDateStringInAnnotation"})
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

    @java.lang.Override()
    public Boolean applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply((input_.get("day") != null ? number(input_.get("day")) : null), (input_.get("month") != null ? number(input_.get("month")) : null), (input_.get("year") != null ? number(input_.get("year")) : null), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'BuildDateStringInAnnotation'", e);
            return null;
        }
    }

    public Boolean apply(java.math.BigDecimal day, java.math.BigDecimal month, java.math.BigDecimal year, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'buildDateStringInAnnotation'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long buildDateStringInAnnotationStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments buildDateStringInAnnotationArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            buildDateStringInAnnotationArguments_.put("day", day);
            buildDateStringInAnnotationArguments_.put("month", month);
            buildDateStringInAnnotationArguments_.put("year", year);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, buildDateStringInAnnotationArguments_);

            // Evaluate decision 'buildDateStringInAnnotation'
            Boolean output_ = evaluate(day, month, year, context_);

            // End decision 'buildDateStringInAnnotation'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, buildDateStringInAnnotationArguments_, output_, (System.currentTimeMillis() - buildDateStringInAnnotationStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'buildDateStringInAnnotation' evaluation", e);
            return null;
        }
    }

    protected Boolean evaluate(java.math.BigDecimal day, java.math.BigDecimal month, java.math.BigDecimal year, com.gs.dmn.runtime.ExecutionContext context_) {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        // Apply rules and collect results
        com.gs.dmn.runtime.RuleOutputList ruleOutputList_ = new com.gs.dmn.runtime.RuleOutputList();
        ruleOutputList_.add(rule0(day, month, year, context_));

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
    public com.gs.dmn.runtime.RuleOutput rule0(java.math.BigDecimal day, java.math.BigDecimal month, java.math.BigDecimal year, com.gs.dmn.runtime.ExecutionContext context_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(0, "");

        // Rule start
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
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
