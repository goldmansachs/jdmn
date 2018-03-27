
import java.util.*;
import java.util.stream.Collectors;

import static ApplicationRiskScoreModel.ApplicationRiskScoreModel;

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
public class ApplicationRiskScore extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "ApplicationRiskScore",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.INVOCATION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public ApplicationRiskScore() {
    }

    public java.math.BigDecimal apply(String applicantData, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply((applicantData != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(applicantData, type.TApplicantDataImpl.class) : null), annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor());
        } catch (Exception e) {
            logError("Cannot apply decision 'ApplicationRiskScore'", e);
            return null;
        }
    }

    public java.math.BigDecimal apply(String applicantData, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        try {
            return apply((applicantData != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(applicantData, type.TApplicantDataImpl.class) : null), annotationSet_, eventListener_, externalExecutor_);
        } catch (Exception e) {
            logError("Cannot apply decision 'ApplicationRiskScore'", e);
            return null;
        }
    }

    public java.math.BigDecimal apply(type.TApplicantData applicantData, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(applicantData, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor());
    }

    public java.math.BigDecimal apply(type.TApplicantData applicantData, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        try {
            // Start decision 'ApplicationRiskScore'
            long applicationRiskScoreStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments applicationRiskScoreArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            applicationRiskScoreArguments_.put("applicantData", applicantData);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, applicationRiskScoreArguments_);

            // Evaluate decision 'ApplicationRiskScore'
            java.math.BigDecimal output_ = evaluate(applicantData, annotationSet_, eventListener_, externalExecutor_);

            // End decision 'ApplicationRiskScore'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, applicationRiskScoreArguments_, output_, (System.currentTimeMillis() - applicationRiskScoreStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'ApplicationRiskScore' evaluation", e);
            return null;
        }
    }

    private java.math.BigDecimal evaluate(type.TApplicantData applicantData, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        return ApplicationRiskScoreModel(((java.math.BigDecimal)(applicantData != null ? applicantData.getAge() : null)), ((String)(applicantData != null ? applicantData.getMaritalStatus() : null)), ((String)(applicantData != null ? applicantData.getEmploymentStatus() : null)), annotationSet_, eventListener_, externalExecutor_);
    }
}
