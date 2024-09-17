
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
public class AssessIssueRisk extends com.gs.dmn.signavio.runtime.JavaTimeSignavioBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "assessIssueRisk",
        "Assess issue risk",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.OTHER,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public static java.util.Map<String, Object> requestToMap(proto.AssessIssueRiskRequest assessIssueRiskRequest_) {
        // Create arguments from Request Message
        type.Applicant applicant = type.Applicant.toApplicant(assessIssueRiskRequest_.getApplicant());
        java.lang.Number currentRiskAppetite = ((java.lang.Number) java.math.BigDecimal.valueOf(assessIssueRiskRequest_.getCurrentRiskAppetite()));

        // Create map
        java.util.Map<String, Object> map_ = new java.util.LinkedHashMap<>();
        map_.put("Applicant", applicant);
        map_.put("Current risk appetite", currentRiskAppetite);
        return map_;
    }

    public static java.lang.Number responseToOutput(proto.AssessIssueRiskResponse assessIssueRiskResponse_) {
        // Extract and convert output
        return ((java.lang.Number) java.math.BigDecimal.valueOf(assessIssueRiskResponse_.getAssessIssueRisk()));
    }

    private final ProcessPriorIssues processPriorIssues;

    public AssessIssueRisk() {
        this(new ProcessPriorIssues());
    }

    public AssessIssueRisk(ProcessPriorIssues processPriorIssues) {
        this.processPriorIssues = processPriorIssues;
    }

    @java.lang.Override()
    public java.lang.Number applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply((input_.get("Applicant") != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(input_.get("Applicant"), new com.fasterxml.jackson.core.type.TypeReference<type.ApplicantImpl>() {}) : null), (input_.get("Current risk appetite") != null ? number(input_.get("Current risk appetite")) : null), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'AssessIssueRisk'", e);
            return null;
        }
    }

    public java.lang.Number apply(type.Applicant applicant, java.lang.Number currentRiskAppetite, com.gs.dmn.runtime.ExecutionContext context_) {
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
            java.lang.Number output_ = evaluate(applicant, currentRiskAppetite, context_);

            // End decision 'assessIssueRisk'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, assessIssueRiskArguments_, output_, (System.currentTimeMillis() - assessIssueRiskStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'assessIssueRisk' evaluation", e);
            return null;
        }
    }

    public proto.AssessIssueRiskResponse applyProto(proto.AssessIssueRiskRequest assessIssueRiskRequest_, com.gs.dmn.runtime.ExecutionContext context_) {
        // Create arguments from Request Message
        type.Applicant applicant = type.Applicant.toApplicant(assessIssueRiskRequest_.getApplicant());
        java.lang.Number currentRiskAppetite = ((java.lang.Number) java.math.BigDecimal.valueOf(assessIssueRiskRequest_.getCurrentRiskAppetite()));

        // Invoke apply method
        java.lang.Number output_ = apply(applicant, currentRiskAppetite, context_);

        // Convert output to Response Message
        proto.AssessIssueRiskResponse.Builder builder_ = proto.AssessIssueRiskResponse.newBuilder();
        Double outputProto_ = (output_ == null ? 0.0 : output_.doubleValue());
        builder_.setAssessIssueRisk(outputProto_);
        return builder_.build();
    }

    protected java.lang.Number evaluate(type.Applicant applicant, java.lang.Number currentRiskAppetite, com.gs.dmn.runtime.ExecutionContext context_) {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        // Apply child decisions
        List<java.lang.Number> processPriorIssues = this.processPriorIssues.apply(applicant, context_);

        AssessIssue assessIssue = new AssessIssue();
        return sum(processPriorIssues.stream().map(priorIssue_iterator -> assessIssue.apply(currentRiskAppetite, priorIssue_iterator, context_)).collect(Collectors.toList()));
    }
}
