
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "PostBureauRiskCategory"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "PostBureauRiskCategory",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.INVOCATION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class PostBureauRiskCategory extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "PostBureauRiskCategory",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.INVOCATION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    private final ApplicationRiskScore applicationRiskScore;

    public PostBureauRiskCategory() {
        this(new ApplicationRiskScore());
    }

    public PostBureauRiskCategory(ApplicationRiskScore applicationRiskScore) {
        this.applicationRiskScore = applicationRiskScore;
    }

    public String apply(String applicantData, String bureauData, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply((applicantData != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(applicantData, new com.fasterxml.jackson.core.type.TypeReference<type.TApplicantDataImpl>() {}) : null), (bureauData != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(bureauData, new com.fasterxml.jackson.core.type.TypeReference<type.TBureauDataImpl>() {}) : null), annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
        } catch (Exception e) {
            logError("Cannot apply decision 'PostBureauRiskCategory'", e);
            return null;
        }
    }

    public String apply(String applicantData, String bureauData, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            return apply((applicantData != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(applicantData, new com.fasterxml.jackson.core.type.TypeReference<type.TApplicantDataImpl>() {}) : null), (bureauData != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(bureauData, new com.fasterxml.jackson.core.type.TypeReference<type.TBureauDataImpl>() {}) : null), annotationSet_, eventListener_, externalExecutor_, cache_);
        } catch (Exception e) {
            logError("Cannot apply decision 'PostBureauRiskCategory'", e);
            return null;
        }
    }

    public String apply(type.TApplicantData applicantData, type.TBureauData bureauData, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(applicantData, bureauData, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
    }

    public String apply(type.TApplicantData applicantData, type.TBureauData bureauData, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision 'PostBureauRiskCategory'
            long postBureauRiskCategoryStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments postBureauRiskCategoryArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            postBureauRiskCategoryArguments_.put("ApplicantData", applicantData);
            postBureauRiskCategoryArguments_.put("BureauData", bureauData);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, postBureauRiskCategoryArguments_);

            // Apply child decisions
            java.math.BigDecimal applicationRiskScore = this.applicationRiskScore.apply(applicantData, annotationSet_, eventListener_, externalExecutor_, cache_);

            // Evaluate decision 'PostBureauRiskCategory'
            String output_ = evaluate(applicantData, applicationRiskScore, bureauData, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision 'PostBureauRiskCategory'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, postBureauRiskCategoryArguments_, output_, (System.currentTimeMillis() - postBureauRiskCategoryStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'PostBureauRiskCategory' evaluation", e);
            return null;
        }
    }

    protected String evaluate(type.TApplicantData applicantData, java.math.BigDecimal applicationRiskScore, type.TBureauData bureauData, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        return PostBureauRiskCategoryTable.PostBureauRiskCategoryTable(((Boolean)(applicantData != null ? applicantData.getExistingCustomer() : null)), applicationRiskScore, ((java.math.BigDecimal)(bureauData != null ? bureauData.getCreditScore() : null)), annotationSet_, eventListener_, externalExecutor_, cache_);
    }
}
