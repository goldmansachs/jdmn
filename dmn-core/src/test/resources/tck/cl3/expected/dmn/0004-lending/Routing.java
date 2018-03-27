
import java.util.*;
import java.util.stream.Collectors;

import static RoutingRules.RoutingRules;

@javax.annotation.Generated(value = {"decision.ftl", "Routing"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "Routing",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.INVOCATION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class Routing extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "Routing",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.INVOCATION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );
    private final PostBureauAffordability postBureauAffordability;
    private final PostBureauRiskCategory postBureauRiskCategory;

    public Routing() {
        this(new PostBureauAffordability(), new PostBureauRiskCategory());
    }

    public Routing(PostBureauAffordability postBureauAffordability, PostBureauRiskCategory postBureauRiskCategory) {
        this.postBureauAffordability = postBureauAffordability;
        this.postBureauRiskCategory = postBureauRiskCategory;
    }

    public String apply(String applicantData, String bureauData, String requestedProduct, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply((applicantData != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(applicantData, type.TApplicantDataImpl.class) : null), (bureauData != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(bureauData, type.TBureauDataImpl.class) : null), (requestedProduct != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(requestedProduct, type.TRequestedProductImpl.class) : null), annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor());
        } catch (Exception e) {
            logError("Cannot apply decision 'Routing'", e);
            return null;
        }
    }

    public String apply(String applicantData, String bureauData, String requestedProduct, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        try {
            return apply((applicantData != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(applicantData, type.TApplicantDataImpl.class) : null), (bureauData != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(bureauData, type.TBureauDataImpl.class) : null), (requestedProduct != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(requestedProduct, type.TRequestedProductImpl.class) : null), annotationSet_, eventListener_, externalExecutor_);
        } catch (Exception e) {
            logError("Cannot apply decision 'Routing'", e);
            return null;
        }
    }

    public String apply(type.TApplicantData applicantData, type.TBureauData bureauData, type.TRequestedProduct requestedProduct, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(applicantData, bureauData, requestedProduct, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor());
    }

    public String apply(type.TApplicantData applicantData, type.TBureauData bureauData, type.TRequestedProduct requestedProduct, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        try {
            // Start decision 'Routing'
            long routingStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments routingArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            routingArguments_.put("applicantData", applicantData);
            routingArguments_.put("bureauData", bureauData);
            routingArguments_.put("requestedProduct", requestedProduct);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, routingArguments_);

            // Apply child decisions
            Boolean postBureauAffordability = this.postBureauAffordability.apply(applicantData, bureauData, requestedProduct, annotationSet_, eventListener_, externalExecutor_);
            String postBureauRiskCategory = this.postBureauRiskCategory.apply(applicantData, bureauData, annotationSet_, eventListener_, externalExecutor_);

            // Evaluate decision 'Routing'
            String output_ = evaluate(bureauData, postBureauAffordability, postBureauRiskCategory, annotationSet_, eventListener_, externalExecutor_);

            // End decision 'Routing'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, routingArguments_, output_, (System.currentTimeMillis() - routingStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'Routing' evaluation", e);
            return null;
        }
    }

    private String evaluate(type.TBureauData bureauData, Boolean postBureauAffordability, String postBureauRiskCategory, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        return RoutingRules(postBureauRiskCategory, postBureauAffordability, ((Boolean)(bureauData != null ? bureauData.getBankrupt() : null)), ((java.math.BigDecimal)(bureauData != null ? bureauData.getCreditScore() : null)), annotationSet_, eventListener_, externalExecutor_);
    }
}
