
import java.util.*;
import java.util.stream.Collectors;

@jakarta.annotation.Generated(value = {"decision.ftl", "Adjudication"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "Adjudication",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class Adjudication extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "Adjudication",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public static java.util.Map<String, Object> requestToMap(proto.AdjudicationRequest adjudicationRequest_) {
        // Create arguments from Request Message
        type.TApplicantData applicantData = type.TApplicantData.toTApplicantData(adjudicationRequest_.getApplicantData());
        type.TBureauData bureauData = type.TBureauData.toTBureauData(adjudicationRequest_.getBureauData());
        String supportingDocuments = adjudicationRequest_.getSupportingDocuments();

        // Create map
        java.util.Map<String, Object> map_ = new java.util.LinkedHashMap<>();
        map_.put("ApplicantData", applicantData);
        map_.put("BureauData", bureauData);
        map_.put("SupportingDocuments", supportingDocuments);
        return map_;
    }

    public static String responseToOutput(proto.AdjudicationResponse adjudicationResponse_) {
        // Extract and convert output
        return adjudicationResponse_.getAdjudication();
    }

    public Adjudication() {
    }

    @java.lang.Override()
    public String applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply((input_.get("ApplicantData") != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(input_.get("ApplicantData"), new com.fasterxml.jackson.core.type.TypeReference<type.TApplicantDataImpl>() {}) : null), (input_.get("BureauData") != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(input_.get("BureauData"), new com.fasterxml.jackson.core.type.TypeReference<type.TBureauDataImpl>() {}) : null), input_.get("SupportingDocuments"), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'Adjudication'", e);
            return null;
        }
    }

    public String apply(type.TApplicantData applicantData, type.TBureauData bureauData, String supportingDocuments, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'Adjudication'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long adjudicationStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments adjudicationArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            adjudicationArguments_.put("ApplicantData", applicantData);
            adjudicationArguments_.put("BureauData", bureauData);
            adjudicationArguments_.put("SupportingDocuments", supportingDocuments);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, adjudicationArguments_);

            // Evaluate decision 'Adjudication'
            String output_ = lambda.apply(applicantData, bureauData, supportingDocuments, context_);

            // End decision 'Adjudication'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, adjudicationArguments_, output_, (System.currentTimeMillis() - adjudicationStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'Adjudication' evaluation", e);
            return null;
        }
    }

    public proto.AdjudicationResponse applyProto(proto.AdjudicationRequest adjudicationRequest_, com.gs.dmn.runtime.ExecutionContext context_) {
        // Create arguments from Request Message
        type.TApplicantData applicantData = type.TApplicantData.toTApplicantData(adjudicationRequest_.getApplicantData());
        type.TBureauData bureauData = type.TBureauData.toTBureauData(adjudicationRequest_.getBureauData());
        String supportingDocuments = adjudicationRequest_.getSupportingDocuments();

        // Invoke apply method
        String output_ = apply(applicantData, bureauData, supportingDocuments, context_);

        // Convert output to Response Message
        proto.AdjudicationResponse.Builder builder_ = proto.AdjudicationResponse.newBuilder();
        String outputProto_ = (output_ == null ? "" : output_);
        builder_.setAdjudication(outputProto_);
        return builder_.build();
    }

    public com.gs.dmn.runtime.LambdaExpression<String> lambda =
        new com.gs.dmn.runtime.LambdaExpression<String>() {
            public String apply(Object... args_) {
                type.TApplicantData applicantData = 0 < args_.length ? (type.TApplicantData) args_[0] : null;
                type.TBureauData bureauData = 1 < args_.length ? (type.TBureauData) args_[1] : null;
                String supportingDocuments = 2 < args_.length ? (String) args_[2] : null;
                com.gs.dmn.runtime.ExecutionContext context_ = 3 < args_.length ? (com.gs.dmn.runtime.ExecutionContext) args_[3] : null;
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
                com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;

                return "ACCEPT";
            }
        };
}
