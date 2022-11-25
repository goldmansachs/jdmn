
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"signavio-decision.ftl", "assessIssueRisk"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "assessIssueRisk",
    label = "Assess issue risk",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.OTHER,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class AssessIssueRisk extends com.gs.dmn.signavio.runtime.DefaultSignavioBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "assessIssueRisk",
        "Assess issue risk",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.OTHER,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    private static class AssessIssueRiskLazyHolder {
        static final AssessIssueRisk INSTANCE = new AssessIssueRisk(ProcessPriorIssues.instance());
    }
    public static AssessIssueRisk instance() {
        return AssessIssueRiskLazyHolder.INSTANCE;
    }

    private final ProcessPriorIssues processPriorIssues;

    public AssessIssueRisk() {
        this(new ProcessPriorIssues());
    }

    public AssessIssueRisk(ProcessPriorIssues processPriorIssues) {
        this.processPriorIssues = processPriorIssues;
    }

    @java.lang.Override()
    public java.math.BigDecimal applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply((input_.get("Applicant") != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(input_.get("Applicant"), new com.fasterxml.jackson.core.type.TypeReference<type.ApplicantImpl>() {}) : null), (input_.get("Current risk appetite") != null ? number(input_.get("Current risk appetite")) : null), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'AssessIssueRisk'", e);
            return null;
        }
    }

    public java.math.BigDecimal apply(type.Applicant applicant, java.math.BigDecimal currentRiskAppetite, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'assessIssueRisk'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long assessIssueRiskStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments assessIssueRiskArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            assessIssueRiskArguments_.put("Applicant", applicant);
            assessIssueRiskArguments_.put("Current risk appetite", currentRiskAppetite);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, assessIssueRiskArguments_);

            // Iterate and aggregate
            java.math.BigDecimal output_ = evaluate(applicant, currentRiskAppetite, context_);

            // End decision 'assessIssueRisk'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, assessIssueRiskArguments_, output_, (System.currentTimeMillis() - assessIssueRiskStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'assessIssueRisk' evaluation", e);
            return null;
        }
    }

    protected java.math.BigDecimal evaluate(type.Applicant applicant, java.math.BigDecimal currentRiskAppetite, com.gs.dmn.runtime.ExecutionContext context_) {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        // Apply child decisions
        List<java.math.BigDecimal> processPriorIssues = this.processPriorIssues.apply(applicant, context_);

        AssessIssue assessIssue = new AssessIssue();
        return sum(processPriorIssues.stream().map(priorIssue_iterator -> assessIssue.apply(currentRiskAppetite, priorIssue_iterator, context_)).collect(Collectors.toList()));
    }
}
