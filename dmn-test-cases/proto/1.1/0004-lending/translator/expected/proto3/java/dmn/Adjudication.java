
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "Adjudication"})
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

    public String apply(String applicantData, String bureauData, String supportingDocuments, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply((applicantData != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(applicantData, new com.fasterxml.jackson.core.type.TypeReference<type.TApplicantDataImpl>() {}) : null), (bureauData != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(bureauData, new com.fasterxml.jackson.core.type.TypeReference<type.TBureauDataImpl>() {}) : null), supportingDocuments, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
        } catch (Exception e) {
            logError("Cannot apply decision 'Adjudication'", e);
            return null;
        }
    }

    public String apply(String applicantData, String bureauData, String supportingDocuments, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            return apply((applicantData != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(applicantData, new com.fasterxml.jackson.core.type.TypeReference<type.TApplicantDataImpl>() {}) : null), (bureauData != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(bureauData, new com.fasterxml.jackson.core.type.TypeReference<type.TBureauDataImpl>() {}) : null), supportingDocuments, annotationSet_, eventListener_, externalExecutor_, cache_);
        } catch (Exception e) {
            logError("Cannot apply decision 'Adjudication'", e);
            return null;
        }
    }

    public String apply(type.TApplicantData applicantData, type.TBureauData bureauData, String supportingDocuments, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(applicantData, bureauData, supportingDocuments, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
    }

    public String apply(type.TApplicantData applicantData, type.TBureauData bureauData, String supportingDocuments, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision 'Adjudication'
            long adjudicationStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments adjudicationArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            adjudicationArguments_.put("ApplicantData", applicantData);
            adjudicationArguments_.put("BureauData", bureauData);
            adjudicationArguments_.put("SupportingDocuments", supportingDocuments);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, adjudicationArguments_);

            // Evaluate decision 'Adjudication'
            String output_ = evaluate(applicantData, bureauData, supportingDocuments, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision 'Adjudication'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, adjudicationArguments_, output_, (System.currentTimeMillis() - adjudicationStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'Adjudication' evaluation", e);
            return null;
        }
    }

    public proto.AdjudicationResponse apply(proto.AdjudicationRequest adjudicationRequest_, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(adjudicationRequest_, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
    }

    public proto.AdjudicationResponse apply(proto.AdjudicationRequest adjudicationRequest_, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        // Create arguments from Request Message
        type.TApplicantData applicantData = type.TApplicantData.toTApplicantData(adjudicationRequest_.getApplicantData());
        type.TBureauData bureauData = type.TBureauData.toTBureauData(adjudicationRequest_.getBureauData());
        String supportingDocuments = adjudicationRequest_.getSupportingDocuments();

        // Invoke apply method
        String output_ = apply(applicantData, bureauData, supportingDocuments, annotationSet_, eventListener_, externalExecutor_, cache_);

        // Convert output to Response Message
        proto.AdjudicationResponse.Builder builder_ = proto.AdjudicationResponse.newBuilder();
        String outputProto_ = (output_ == null ? "" : output_);
        builder_.setAdjudication(outputProto_);
        return builder_.build();
    }

    protected String evaluate(type.TApplicantData applicantData, type.TBureauData bureauData, String supportingDocuments, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        return "ACCEPT";
    }
}
