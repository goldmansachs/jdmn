
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "PreBureauRiskCategory"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "PreBureauRiskCategory",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.INVOCATION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class PreBureauRiskCategory extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "PreBureauRiskCategory",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.INVOCATION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public static java.util.Map<String, Object> requestToMap(proto.PreBureauRiskCategoryRequest preBureauRiskCategoryRequest_) {
        // Create arguments from Request Message
        type.TApplicantData applicantData = type.TApplicantData.toTApplicantData(preBureauRiskCategoryRequest_.getApplicantData());

        // Create map
        java.util.Map<String, Object> map_ = new java.util.LinkedHashMap<>();
        map_.put("ApplicantData", applicantData);
        return map_;
    }

    public static String responseToOutput(proto.PreBureauRiskCategoryResponse preBureauRiskCategoryResponse_) {
        // Extract and convert output
        return preBureauRiskCategoryResponse_.getPreBureauRiskCategory();
    }

    private final ApplicationRiskScore applicationRiskScore;

    public PreBureauRiskCategory() {
        this(new ApplicationRiskScore());
    }

    public PreBureauRiskCategory(ApplicationRiskScore applicationRiskScore) {
        this.applicationRiskScore = applicationRiskScore;
    }

    public String apply(String applicantData, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply((applicantData != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(applicantData, new com.fasterxml.jackson.core.type.TypeReference<type.TApplicantDataImpl>() {}) : null), annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
        } catch (Exception e) {
            logError("Cannot apply decision 'PreBureauRiskCategory'", e);
            return null;
        }
    }

    public String apply(String applicantData, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            return apply((applicantData != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(applicantData, new com.fasterxml.jackson.core.type.TypeReference<type.TApplicantDataImpl>() {}) : null), annotationSet_, eventListener_, externalExecutor_, cache_);
        } catch (Exception e) {
            logError("Cannot apply decision 'PreBureauRiskCategory'", e);
            return null;
        }
    }

    public String apply(type.TApplicantData applicantData, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(applicantData, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
    }

    public String apply(type.TApplicantData applicantData, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision 'PreBureauRiskCategory'
            long preBureauRiskCategoryStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments preBureauRiskCategoryArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            preBureauRiskCategoryArguments_.put("ApplicantData", applicantData);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, preBureauRiskCategoryArguments_);

            if (cache_.contains("PreBureauRiskCategory")) {
                // Retrieve value from cache
                String output_ = (String)cache_.lookup("PreBureauRiskCategory");

                // End decision 'PreBureauRiskCategory'
                eventListener_.endDRGElement(DRG_ELEMENT_METADATA, preBureauRiskCategoryArguments_, output_, (System.currentTimeMillis() - preBureauRiskCategoryStartTime_));

                return output_;
            } else {
                // Apply child decisions
                java.math.BigDecimal applicationRiskScore = this.applicationRiskScore.apply(applicantData, annotationSet_, eventListener_, externalExecutor_, cache_);

                // Evaluate decision 'PreBureauRiskCategory'
                String output_ = evaluate(applicantData, applicationRiskScore, annotationSet_, eventListener_, externalExecutor_, cache_);
                cache_.bind("PreBureauRiskCategory", output_);

                // End decision 'PreBureauRiskCategory'
                eventListener_.endDRGElement(DRG_ELEMENT_METADATA, preBureauRiskCategoryArguments_, output_, (System.currentTimeMillis() - preBureauRiskCategoryStartTime_));

                return output_;
            }
        } catch (Exception e) {
            logError("Exception caught in 'PreBureauRiskCategory' evaluation", e);
            return null;
        }
    }

    public proto.PreBureauRiskCategoryResponse apply(proto.PreBureauRiskCategoryRequest preBureauRiskCategoryRequest_, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(preBureauRiskCategoryRequest_, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
    }

    public proto.PreBureauRiskCategoryResponse apply(proto.PreBureauRiskCategoryRequest preBureauRiskCategoryRequest_, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        // Create arguments from Request Message
        type.TApplicantData applicantData = type.TApplicantData.toTApplicantData(preBureauRiskCategoryRequest_.getApplicantData());

        // Invoke apply method
        String output_ = apply(applicantData, annotationSet_, eventListener_, externalExecutor_, cache_);

        // Convert output to Response Message
        proto.PreBureauRiskCategoryResponse.Builder builder_ = proto.PreBureauRiskCategoryResponse.newBuilder();
        String outputProto_ = (output_ == null ? "" : output_);
        builder_.setPreBureauRiskCategory(outputProto_);
        return builder_.build();
    }

    protected String evaluate(type.TApplicantData applicantData, java.math.BigDecimal applicationRiskScore, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        return PreBureauRiskCategoryTable.PreBureauRiskCategoryTable(((Boolean)(applicantData != null ? applicantData.getExistingCustomer() : null)), applicationRiskScore, annotationSet_, eventListener_, externalExecutor_, cache_);
    }
}
