
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
public class AssessIssue extends com.gs.dmn.signavio.runtime.JavaTimeSignavioBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "assessIssue",
        "Assess issue",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public static java.util.Map<String, Object> requestToMap(proto.AssessIssueRequest assessIssueRequest_) {
        // Create arguments from Request Message
        java.lang.Number currentRiskAppetite = ((java.lang.Number) java.math.BigDecimal.valueOf(assessIssueRequest_.getCurrentRiskAppetite()));
        java.lang.Number priorIssue_iterator = ((java.lang.Number) java.math.BigDecimal.valueOf(assessIssueRequest_.getPriorIssueIterator()));

        // Create map
        java.util.Map<String, Object> map_ = new java.util.LinkedHashMap<>();
        map_.put("Current risk appetite", currentRiskAppetite);
        map_.put("Prior issue", priorIssue_iterator);
        return map_;
    }

    public static java.lang.Number responseToOutput(proto.AssessIssueResponse assessIssueResponse_) {
        // Extract and convert output
        return ((java.lang.Number) java.math.BigDecimal.valueOf(assessIssueResponse_.getAssessIssue()));
    }

    public AssessIssue() {
    }

    @java.lang.Override()
    public java.lang.Number applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply((input_.get("Current risk appetite") != null ? number(input_.get("Current risk appetite")) : null), (input_.get("Prior issue") != null ? number(input_.get("Prior issue")) : null), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'AssessIssue'", e);
            return null;
        }
    }

    public java.lang.Number apply(java.lang.Number currentRiskAppetite, java.lang.Number priorIssue_iterator, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'assessIssue'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long assessIssueStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments assessIssueArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            assessIssueArguments_.put("Current risk appetite", currentRiskAppetite);
            assessIssueArguments_.put("Prior issue", priorIssue_iterator);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, assessIssueArguments_);

            // Evaluate decision 'assessIssue'
            java.lang.Number output_ = evaluate(currentRiskAppetite, priorIssue_iterator, context_);

            // End decision 'assessIssue'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, assessIssueArguments_, output_, (System.currentTimeMillis() - assessIssueStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'assessIssue' evaluation", e);
            return null;
        }
    }

    public proto.AssessIssueResponse applyProto(proto.AssessIssueRequest assessIssueRequest_, com.gs.dmn.runtime.ExecutionContext context_) {
        // Create arguments from Request Message
        java.lang.Number currentRiskAppetite = ((java.lang.Number) java.math.BigDecimal.valueOf(assessIssueRequest_.getCurrentRiskAppetite()));
        java.lang.Number priorIssue_iterator = ((java.lang.Number) java.math.BigDecimal.valueOf(assessIssueRequest_.getPriorIssueIterator()));

        // Invoke apply method
        java.lang.Number output_ = apply(currentRiskAppetite, priorIssue_iterator, context_);

        // Convert output to Response Message
        proto.AssessIssueResponse.Builder builder_ = proto.AssessIssueResponse.newBuilder();
        Double outputProto_ = (output_ == null ? 0.0 : output_.doubleValue());
        builder_.setAssessIssue(outputProto_);
        return builder_.build();
    }

    protected java.lang.Number evaluate(java.lang.Number currentRiskAppetite, java.lang.Number priorIssue_iterator, com.gs.dmn.runtime.ExecutionContext context_) {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        return numericMultiply(priorIssue_iterator, numericMultiply(max(asList(number("0"), numericSubtract(number("100"), currentRiskAppetite))), number("0.01")));
    }
}
