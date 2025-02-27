
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "ApplicationRiskScore"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "ApplicationRiskScore",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.INVOCATION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class ApplicationRiskScore extends com.gs.dmn.runtime.JavaTimeDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "ApplicationRiskScore",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.INVOCATION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public static java.util.Map<String, Object> requestToMap(proto.ApplicationRiskScoreRequest applicationRiskScoreRequest_) {
        // Create arguments from Request Message
        type.TApplicantData applicantData = type.TApplicantData.toTApplicantData(applicationRiskScoreRequest_.getApplicantData());

        // Create map
        java.util.Map<String, Object> map_ = new java.util.LinkedHashMap<>();
        map_.put("ApplicantData", applicantData);
        return map_;
    }

    public static java.lang.Number responseToOutput(proto.ApplicationRiskScoreResponse applicationRiskScoreResponse_) {
        // Extract and convert output
        return ((java.lang.Number) java.math.BigDecimal.valueOf(applicationRiskScoreResponse_.getApplicationRiskScore()));
    }

    public ApplicationRiskScore() {
    }

    @java.lang.Override()
    public java.lang.Number applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply((input_.get("ApplicantData") != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(input_.get("ApplicantData"), new com.fasterxml.jackson.core.type.TypeReference<type.TApplicantDataImpl>() {}) : null), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'ApplicationRiskScore'", e);
            return null;
        }
    }

    public java.lang.Number apply(type.TApplicantData applicantData, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'ApplicationRiskScore'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long applicationRiskScoreStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments applicationRiskScoreArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            applicationRiskScoreArguments_.put("ApplicantData", applicantData);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, applicationRiskScoreArguments_);

            if (cache_.contains("ApplicationRiskScore")) {
                // Retrieve value from cache
                java.lang.Number output_ = (java.lang.Number)cache_.lookup("ApplicationRiskScore");

                // End decision 'ApplicationRiskScore'
                eventListener_.endDRGElement(DRG_ELEMENT_METADATA, applicationRiskScoreArguments_, output_, (System.currentTimeMillis() - applicationRiskScoreStartTime_));

                return output_;
            } else {
                // Evaluate decision 'ApplicationRiskScore'
                java.lang.Number output_ = lambda.apply(applicantData, context_);
                cache_.bind("ApplicationRiskScore", output_);

                // End decision 'ApplicationRiskScore'
                eventListener_.endDRGElement(DRG_ELEMENT_METADATA, applicationRiskScoreArguments_, output_, (System.currentTimeMillis() - applicationRiskScoreStartTime_));

                return output_;
            }
        } catch (Exception e) {
            logError("Exception caught in 'ApplicationRiskScore' evaluation", e);
            return null;
        }
    }

    public proto.ApplicationRiskScoreResponse applyProto(proto.ApplicationRiskScoreRequest applicationRiskScoreRequest_, com.gs.dmn.runtime.ExecutionContext context_) {
        // Create arguments from Request Message
        type.TApplicantData applicantData = type.TApplicantData.toTApplicantData(applicationRiskScoreRequest_.getApplicantData());

        // Invoke apply method
        java.lang.Number output_ = apply(applicantData, context_);

        // Convert output to Response Message
        proto.ApplicationRiskScoreResponse.Builder builder_ = proto.ApplicationRiskScoreResponse.newBuilder();
        Double outputProto_ = (output_ == null ? 0.0 : output_.doubleValue());
        builder_.setApplicationRiskScore(outputProto_);
        return builder_.build();
    }

    public com.gs.dmn.runtime.LambdaExpression<java.lang.Number> lambda =
        new com.gs.dmn.runtime.LambdaExpression<java.lang.Number>() {
            public java.lang.Number apply(Object... args_) {
                type.TApplicantData applicantData = 0 < args_.length ? (type.TApplicantData) args_[0] : null;
                com.gs.dmn.runtime.ExecutionContext context_ = 1 < args_.length ? (com.gs.dmn.runtime.ExecutionContext) args_[1] : null;
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
                com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;

                return ApplicationRiskScoreModel.instance().apply(((java.lang.Number)(applicantData != null ? applicantData.getAge() : null)), ((String)(applicantData != null ? applicantData.getMaritalStatus() : null)), ((String)(applicantData != null ? applicantData.getEmploymentStatus() : null)), context_);
            }
        };
}
