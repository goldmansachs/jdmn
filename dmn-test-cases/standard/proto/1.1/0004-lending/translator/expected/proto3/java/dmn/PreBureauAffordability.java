
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "'Pre-bureauAffordability'"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "'Pre-bureauAffordability'",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.INVOCATION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class PreBureauAffordability extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "'Pre-bureauAffordability'",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.INVOCATION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public static java.util.Map<String, Object> requestToMap(proto.PreBureauAffordabilityRequest preBureauAffordabilityRequest_) {
        // Create arguments from Request Message
        type.TApplicantData applicantData = type.TApplicantData.toTApplicantData(preBureauAffordabilityRequest_.getApplicantData());
        type.TRequestedProduct requestedProduct = type.TRequestedProduct.toTRequestedProduct(preBureauAffordabilityRequest_.getRequestedProduct());

        // Create map
        java.util.Map<String, Object> map_ = new java.util.LinkedHashMap<>();
        map_.put("ApplicantData", applicantData);
        map_.put("RequestedProduct", requestedProduct);
        return map_;
    }

    public static Boolean responseToOutput(proto.PreBureauAffordabilityResponse preBureauAffordabilityResponse_) {
        // Extract and convert output
        return preBureauAffordabilityResponse_.getPreBureauAffordability();
    }

    private final PreBureauRiskCategory preBureauRiskCategory;
    private final RequiredMonthlyInstallment requiredMonthlyInstallment;

    public PreBureauAffordability() {
        this(new PreBureauRiskCategory(), new RequiredMonthlyInstallment());
    }

    public PreBureauAffordability(PreBureauRiskCategory preBureauRiskCategory, RequiredMonthlyInstallment requiredMonthlyInstallment) {
        this.preBureauRiskCategory = preBureauRiskCategory;
        this.requiredMonthlyInstallment = requiredMonthlyInstallment;
    }

    public Boolean apply(String applicantData, String requestedProduct, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply((applicantData != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(applicantData, new com.fasterxml.jackson.core.type.TypeReference<type.TApplicantDataImpl>() {}) : null), (requestedProduct != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(requestedProduct, new com.fasterxml.jackson.core.type.TypeReference<type.TRequestedProductImpl>() {}) : null), annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
        } catch (Exception e) {
            logError("Cannot apply decision 'PreBureauAffordability'", e);
            return null;
        }
    }

    public Boolean apply(String applicantData, String requestedProduct, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            return apply((applicantData != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(applicantData, new com.fasterxml.jackson.core.type.TypeReference<type.TApplicantDataImpl>() {}) : null), (requestedProduct != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(requestedProduct, new com.fasterxml.jackson.core.type.TypeReference<type.TRequestedProductImpl>() {}) : null), annotationSet_, eventListener_, externalExecutor_, cache_);
        } catch (Exception e) {
            logError("Cannot apply decision 'PreBureauAffordability'", e);
            return null;
        }
    }

    public Boolean apply(type.TApplicantData applicantData, type.TRequestedProduct requestedProduct, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(applicantData, requestedProduct, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
    }

    public Boolean apply(type.TApplicantData applicantData, type.TRequestedProduct requestedProduct, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision ''Pre-bureauAffordability''
            long preBureauAffordabilityStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments preBureauAffordabilityArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            preBureauAffordabilityArguments_.put("ApplicantData", applicantData);
            preBureauAffordabilityArguments_.put("RequestedProduct", requestedProduct);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, preBureauAffordabilityArguments_);

            // Evaluate decision ''Pre-bureauAffordability''
            Boolean output_ = lambda.apply(applicantData, requestedProduct, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision ''Pre-bureauAffordability''
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, preBureauAffordabilityArguments_, output_, (System.currentTimeMillis() - preBureauAffordabilityStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in ''Pre-bureauAffordability'' evaluation", e);
            return null;
        }
    }

    public proto.PreBureauAffordabilityResponse apply(proto.PreBureauAffordabilityRequest preBureauAffordabilityRequest_, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(preBureauAffordabilityRequest_, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
    }

    public proto.PreBureauAffordabilityResponse apply(proto.PreBureauAffordabilityRequest preBureauAffordabilityRequest_, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        // Create arguments from Request Message
        type.TApplicantData applicantData = type.TApplicantData.toTApplicantData(preBureauAffordabilityRequest_.getApplicantData());
        type.TRequestedProduct requestedProduct = type.TRequestedProduct.toTRequestedProduct(preBureauAffordabilityRequest_.getRequestedProduct());

        // Invoke apply method
        Boolean output_ = apply(applicantData, requestedProduct, annotationSet_, eventListener_, externalExecutor_, cache_);

        // Convert output to Response Message
        proto.PreBureauAffordabilityResponse.Builder builder_ = proto.PreBureauAffordabilityResponse.newBuilder();
        Boolean outputProto_ = (output_ == null ? false : output_);
        builder_.setPreBureauAffordability(outputProto_);
        return builder_.build();
    }

    public com.gs.dmn.runtime.LambdaExpression<Boolean> lambda =
        new com.gs.dmn.runtime.LambdaExpression<Boolean>() {
            public Boolean apply(Object... args) {
                type.TApplicantData applicantData = 0 < args.length ? (type.TApplicantData) args[0] : null;
                type.TRequestedProduct requestedProduct = 1 < args.length ? (type.TRequestedProduct) args[1] : null;
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = 2 < args.length ? (com.gs.dmn.runtime.annotation.AnnotationSet) args[2] : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = 3 < args.length ? (com.gs.dmn.runtime.listener.EventListener) args[3] : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = 4 < args.length ? (com.gs.dmn.runtime.external.ExternalFunctionExecutor) args[4] : null;
                com.gs.dmn.runtime.cache.Cache cache_ = 5 < args.length ? (com.gs.dmn.runtime.cache.Cache) args[5] : null;

                // Apply child decisions
                String preBureauRiskCategory = PreBureauAffordability.this.preBureauRiskCategory.apply(applicantData, annotationSet_, eventListener_, externalExecutor_, cache_);
                java.math.BigDecimal requiredMonthlyInstallment = PreBureauAffordability.this.requiredMonthlyInstallment.apply(requestedProduct, annotationSet_, eventListener_, externalExecutor_, cache_);

                return AffordabilityCalculation.instance().apply(((java.math.BigDecimal)(((type.Monthly)(applicantData != null ? applicantData.getMonthly() : null)) != null ? ((type.Monthly)(applicantData != null ? applicantData.getMonthly() : null)).getIncome() : null)), ((java.math.BigDecimal)(((type.Monthly)(applicantData != null ? applicantData.getMonthly() : null)) != null ? ((type.Monthly)(applicantData != null ? applicantData.getMonthly() : null)).getRepayments() : null)), ((java.math.BigDecimal)(((type.Monthly)(applicantData != null ? applicantData.getMonthly() : null)) != null ? ((type.Monthly)(applicantData != null ? applicantData.getMonthly() : null)).getExpenses() : null)), preBureauRiskCategory, requiredMonthlyInstallment, annotationSet_, eventListener_, externalExecutor_, cache_);
            }
        };
}
