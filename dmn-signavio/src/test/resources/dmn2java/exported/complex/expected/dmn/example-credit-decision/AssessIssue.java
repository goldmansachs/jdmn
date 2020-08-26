
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"signavio-decision.ftl", "assessIssue"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "assessIssue",
    label = "Assess issue",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class AssessIssue extends com.gs.dmn.signavio.runtime.DefaultSignavioBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "assessIssue",
        "Assess issue",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public AssessIssue() {
    }

    public java.math.BigDecimal apply(String currentRiskAppetite, String priorIssue_iterator, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply((currentRiskAppetite != null ? number(currentRiskAppetite) : null), (priorIssue_iterator != null ? number(priorIssue_iterator) : null), annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
        } catch (Exception e) {
            logError("Cannot apply decision 'AssessIssue'", e);
            return null;
        }
    }

    public java.math.BigDecimal apply(String currentRiskAppetite, String priorIssue_iterator, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            return apply((currentRiskAppetite != null ? number(currentRiskAppetite) : null), (priorIssue_iterator != null ? number(priorIssue_iterator) : null), annotationSet_, eventListener_, externalExecutor_, cache_);
        } catch (Exception e) {
            logError("Cannot apply decision 'AssessIssue'", e);
            return null;
        }
    }

    public java.math.BigDecimal apply(java.math.BigDecimal currentRiskAppetite, java.math.BigDecimal priorIssue_iterator, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(currentRiskAppetite, priorIssue_iterator, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
    }

    public java.math.BigDecimal apply(java.math.BigDecimal currentRiskAppetite, java.math.BigDecimal priorIssue_iterator, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision 'assessIssue'
            long assessIssueStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments assessIssueArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            assessIssueArguments_.put("Current risk appetite", currentRiskAppetite);
            assessIssueArguments_.put("Prior issue", priorIssue_iterator);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, assessIssueArguments_);

            // Evaluate decision 'assessIssue'
            java.math.BigDecimal output_ = evaluate(currentRiskAppetite, priorIssue_iterator, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision 'assessIssue'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, assessIssueArguments_, output_, (System.currentTimeMillis() - assessIssueStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'assessIssue' evaluation", e);
            return null;
        }
    }

    protected java.math.BigDecimal evaluate(java.math.BigDecimal currentRiskAppetite, java.math.BigDecimal priorIssue_iterator, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        return numericMultiply(priorIssue_iterator, numericMultiply(max(asList(number("0"), numericSubtract(number("100"), currentRiskAppetite))), number("0.01")));
    }
}
