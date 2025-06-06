
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"signavio-decision.ftl", "generateOutputData"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "generateOutputData",
    label = "Generate output data",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class GenerateOutputData extends com.gs.dmn.signavio.runtime.JavaTimeSignavioBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "generateOutputData",
        "Generate output data",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    private final AssessIssueRisk assessIssueRisk;
    private final CompareAgainstLendingThreshold compareAgainstLendingThreshold;
    private final MakeCreditDecision makeCreditDecision;

    public GenerateOutputData() {
        this(new AssessIssueRisk(), new CompareAgainstLendingThreshold(), new MakeCreditDecision());
    }

    public GenerateOutputData(AssessIssueRisk assessIssueRisk, CompareAgainstLendingThreshold compareAgainstLendingThreshold, MakeCreditDecision makeCreditDecision) {
        this.assessIssueRisk = assessIssueRisk;
        this.compareAgainstLendingThreshold = compareAgainstLendingThreshold;
        this.makeCreditDecision = makeCreditDecision;
    }

    @java.lang.Override()
    public List<type.GenerateOutputData> applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply((input_.get("Applicant") != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(input_.get("Applicant"), new com.fasterxml.jackson.core.type.TypeReference<type.ApplicantImpl>() {}) : null), (input_.get("Current risk appetite") != null ? number(input_.get("Current risk appetite")) : null), (input_.get("Lending threshold") != null ? number(input_.get("Lending threshold")) : null), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'GenerateOutputData'", e);
            return null;
        }
    }

    public List<type.GenerateOutputData> apply(type.Applicant applicant, java.lang.Number currentRiskAppetite, java.lang.Number lendingThreshold, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'generateOutputData'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long generateOutputDataStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments generateOutputDataArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            generateOutputDataArguments_.put("Applicant", applicant);
            generateOutputDataArguments_.put("Current risk appetite", currentRiskAppetite);
            generateOutputDataArguments_.put("Lending threshold", lendingThreshold);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, generateOutputDataArguments_);

            // Evaluate decision 'generateOutputData'
            List<type.GenerateOutputData> output_ = evaluate(applicant, currentRiskAppetite, lendingThreshold, context_);

            // End decision 'generateOutputData'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, generateOutputDataArguments_, output_, (System.currentTimeMillis() - generateOutputDataStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'generateOutputData' evaluation", e);
            return null;
        }
    }

    protected List<type.GenerateOutputData> evaluate(type.Applicant applicant, java.lang.Number currentRiskAppetite, java.lang.Number lendingThreshold, com.gs.dmn.runtime.ExecutionContext context_) {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        // Apply child decisions
        java.lang.Number assessIssueRisk = this.assessIssueRisk.apply(applicant, currentRiskAppetite, context_);
        java.lang.Number compareAgainstLendingThreshold = this.compareAgainstLendingThreshold.apply(applicant, currentRiskAppetite, lendingThreshold, context_);
        String makeCreditDecision = this.makeCreditDecision.apply(applicant, currentRiskAppetite, lendingThreshold, context_);

        return zip(asList("Decision", "Assessment", "Issue"), asList(asList(makeCreditDecision), asList(compareAgainstLendingThreshold), asList(assessIssueRisk))).stream().map(x_ -> type.GenerateOutputData.toGenerateOutputData(x_)).collect(Collectors.toList());
    }
}
