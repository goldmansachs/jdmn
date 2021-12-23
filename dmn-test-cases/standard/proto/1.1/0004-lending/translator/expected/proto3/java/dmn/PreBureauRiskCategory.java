
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "'Pre-bureauRiskCategory'"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "'Pre-bureauRiskCategory'",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.INVOCATION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class PreBureauRiskCategory extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "'Pre-bureauRiskCategory'",
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
            // Start decision ''Pre-bureauRiskCategory''
            long preBureauRiskCategoryStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments preBureauRiskCategoryArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            preBureauRiskCategoryArguments_.put("ApplicantData", applicantData);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, preBureauRiskCategoryArguments_);

            if (cache_.contains("'Pre-bureauRiskCategory'")) {
                // Retrieve value from cache
                String output_ = (String)cache_.lookup("'Pre-bureauRiskCategory'");

                // End decision ''Pre-bureauRiskCategory''
                eventListener_.endDRGElement(DRG_ELEMENT_METADATA, preBureauRiskCategoryArguments_, output_, (System.currentTimeMillis() - preBureauRiskCategoryStartTime_));

                return output_;
            } else {
                // Evaluate decision ''Pre-bureauRiskCategory''
                String output_ = lambda.apply(applicantData, annotationSet_, eventListener_, externalExecutor_, cache_);
                cache_.bind("'Pre-bureauRiskCategory'", output_);

                // End decision ''Pre-bureauRiskCategory''
                eventListener_.endDRGElement(DRG_ELEMENT_METADATA, preBureauRiskCategoryArguments_, output_, (System.currentTimeMillis() - preBureauRiskCategoryStartTime_));

                return output_;
            }
        } catch (Exception e) {
            logError("Exception caught in ''Pre-bureauRiskCategory'' evaluation", e);
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

    public com.gs.dmn.runtime.LambdaExpression<String> lambda =
        new com.gs.dmn.runtime.LambdaExpression<String>() {
            public String apply(Object... args) {
                type.TApplicantData applicantData = 0 < args.length ? (type.TApplicantData) args[0] : null;
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = 1 < args.length ? (com.gs.dmn.runtime.annotation.AnnotationSet) args[1] : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = 2 < args.length ? (com.gs.dmn.runtime.listener.EventListener) args[2] : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = 3 < args.length ? (com.gs.dmn.runtime.external.ExternalFunctionExecutor) args[3] : null;
                com.gs.dmn.runtime.cache.Cache cache_ = 4 < args.length ? (com.gs.dmn.runtime.cache.Cache) args[4] : null;

                // Apply child decisions
                java.math.BigDecimal applicationRiskScore = PreBureauRiskCategory.this.applicationRiskScore.apply(applicantData, annotationSet_, eventListener_, externalExecutor_, cache_);

                return PreBureauRiskCategoryTable.instance().apply(((Boolean)(applicantData != null ? applicantData.getExistingCustomer() : null)), applicationRiskScore, annotationSet_, eventListener_, externalExecutor_, cache_);
            }
        };
}
