
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"junit.ftl", "0004-lending.dmn"})
public class Test0004Lending extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    @org.junit.Test
    public void testCase001() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();
        com.gs.dmn.runtime.cache.Cache cache_ = new com.gs.dmn.runtime.cache.DefaultCache();
        // Initialize input data
        type.TApplicantData applicantData = new type.TApplicantDataImpl(number("35"), "EMPLOYED", true, "M", new type.MonthlyImpl(number("2000"), number("6000"), number("0")));
        type.TRequestedProduct requestedProduct = new type.TRequestedProductImpl(number("350000"), "STANDARD LOAN", number("0.0395"), number("360"));
        type.TBureauData bureauData = new type.TBureauDataImpl(false, number("649"));
        String supportingDocuments = "YES";

        // Check Adjudication
        checkValues("ACCEPT", new Adjudication().apply(applicantData, bureauData, supportingDocuments, annotationSet_, eventListener_, externalExecutor_, cache_));
        // Check ApplicationRiskScore
        checkValues(number("130"), new ApplicationRiskScore().apply(applicantData, annotationSet_, eventListener_, externalExecutor_, cache_));
        // Check PreBureauRiskCategory
        checkValues("LOW", new PreBureauRiskCategory().apply(applicantData, annotationSet_, eventListener_, externalExecutor_, cache_));
        // Check BureauCallType
        checkValues("MINI", new BureauCallType().apply(applicantData, annotationSet_, eventListener_, externalExecutor_, cache_));
        // Check PostBureauRiskCategory
        checkValues("LOW", new PostBureauRiskCategory().apply(applicantData, bureauData, annotationSet_, eventListener_, externalExecutor_, cache_));
        // Check RequiredMonthlyInstallment
        checkValues(number("1680.880325608555"), new RequiredMonthlyInstallment().apply(requestedProduct, annotationSet_, eventListener_, externalExecutor_, cache_));
        // Check PreBureauAffordability
        checkValues(true, new PreBureauAffordability().apply(applicantData, requestedProduct, annotationSet_, eventListener_, externalExecutor_, cache_));
        // Check Eligibility
        checkValues("ELIGIBLE", new Eligibility().apply(applicantData, requestedProduct, annotationSet_, eventListener_, externalExecutor_, cache_));
        // Check Strategy
        checkValues("BUREAU", new Strategy().apply(applicantData, requestedProduct, annotationSet_, eventListener_, externalExecutor_, cache_));
        // Check PostBureauAffordability
        checkValues(true, new PostBureauAffordability().apply(applicantData, bureauData, requestedProduct, annotationSet_, eventListener_, externalExecutor_, cache_));
        // Check Routing
        checkValues("ACCEPT", new Routing().apply(applicantData, bureauData, requestedProduct, annotationSet_, eventListener_, externalExecutor_, cache_));

        // Check Adjudication with proto request
        proto.AdjudicationRequest.Builder adjudicationBuilder_ = proto.AdjudicationRequest.newBuilder();
        adjudicationBuilder_.setApplicantData(type.TApplicantData.toProto(applicantData));
        adjudicationBuilder_.setBureauData(type.TBureauData.toProto(bureauData));
        adjudicationBuilder_.setSupportingDocuments(supportingDocuments);
        proto.AdjudicationRequest adjudicationRequest_ = adjudicationBuilder_.build();
        checkValues("ACCEPT", new Adjudication().apply(adjudicationRequest_, annotationSet_, eventListener_, externalExecutor_, cache_).getAdjudication());
        // Check ApplicationRiskScore with proto request
        proto.ApplicationRiskScoreRequest.Builder applicationRiskScoreBuilder_ = proto.ApplicationRiskScoreRequest.newBuilder();
        applicationRiskScoreBuilder_.setApplicantData(type.TApplicantData.toProto(applicantData));
        proto.ApplicationRiskScoreRequest applicationRiskScoreRequest_ = applicationRiskScoreBuilder_.build();
        checkValues(number("130"), new ApplicationRiskScore().apply(applicationRiskScoreRequest_, annotationSet_, eventListener_, externalExecutor_, cache_).getApplicationRiskScore());
        // Check PreBureauRiskCategory with proto request
        proto.PreBureauRiskCategoryRequest.Builder preBureauRiskCategoryBuilder_ = proto.PreBureauRiskCategoryRequest.newBuilder();
        preBureauRiskCategoryBuilder_.setApplicantData(type.TApplicantData.toProto(applicantData));
        proto.PreBureauRiskCategoryRequest preBureauRiskCategoryRequest_ = preBureauRiskCategoryBuilder_.build();
        checkValues("LOW", new PreBureauRiskCategory().apply(preBureauRiskCategoryRequest_, annotationSet_, eventListener_, externalExecutor_, cache_).getPreBureauRiskCategory());
        // Check BureauCallType with proto request
        proto.BureauCallTypeRequest.Builder bureauCallTypeBuilder_ = proto.BureauCallTypeRequest.newBuilder();
        bureauCallTypeBuilder_.setApplicantData(type.TApplicantData.toProto(applicantData));
        proto.BureauCallTypeRequest bureauCallTypeRequest_ = bureauCallTypeBuilder_.build();
        checkValues("MINI", new BureauCallType().apply(bureauCallTypeRequest_, annotationSet_, eventListener_, externalExecutor_, cache_).getBureauCallType());
        // Check PostBureauRiskCategory with proto request
        proto.PostBureauRiskCategoryRequest.Builder postBureauRiskCategoryBuilder_ = proto.PostBureauRiskCategoryRequest.newBuilder();
        postBureauRiskCategoryBuilder_.setApplicantData(type.TApplicantData.toProto(applicantData));
        postBureauRiskCategoryBuilder_.setBureauData(type.TBureauData.toProto(bureauData));
        proto.PostBureauRiskCategoryRequest postBureauRiskCategoryRequest_ = postBureauRiskCategoryBuilder_.build();
        checkValues("LOW", new PostBureauRiskCategory().apply(postBureauRiskCategoryRequest_, annotationSet_, eventListener_, externalExecutor_, cache_).getPostBureauRiskCategory());
        // Check RequiredMonthlyInstallment with proto request
        proto.RequiredMonthlyInstallmentRequest.Builder requiredMonthlyInstallmentBuilder_ = proto.RequiredMonthlyInstallmentRequest.newBuilder();
        requiredMonthlyInstallmentBuilder_.setRequestedProduct(type.TRequestedProduct.toProto(requestedProduct));
        proto.RequiredMonthlyInstallmentRequest requiredMonthlyInstallmentRequest_ = requiredMonthlyInstallmentBuilder_.build();
        checkValues(number("1680.880325608555"), new RequiredMonthlyInstallment().apply(requiredMonthlyInstallmentRequest_, annotationSet_, eventListener_, externalExecutor_, cache_).getRequiredMonthlyInstallment());
        // Check PreBureauAffordability with proto request
        proto.PreBureauAffordabilityRequest.Builder preBureauAffordabilityBuilder_ = proto.PreBureauAffordabilityRequest.newBuilder();
        preBureauAffordabilityBuilder_.setApplicantData(type.TApplicantData.toProto(applicantData));
        preBureauAffordabilityBuilder_.setRequestedProduct(type.TRequestedProduct.toProto(requestedProduct));
        proto.PreBureauAffordabilityRequest preBureauAffordabilityRequest_ = preBureauAffordabilityBuilder_.build();
        checkValues(true, new PreBureauAffordability().apply(preBureauAffordabilityRequest_, annotationSet_, eventListener_, externalExecutor_, cache_).getPreBureauAffordability());
        // Check Eligibility with proto request
        proto.EligibilityRequest.Builder eligibilityBuilder_ = proto.EligibilityRequest.newBuilder();
        eligibilityBuilder_.setApplicantData(type.TApplicantData.toProto(applicantData));
        eligibilityBuilder_.setRequestedProduct(type.TRequestedProduct.toProto(requestedProduct));
        proto.EligibilityRequest eligibilityRequest_ = eligibilityBuilder_.build();
        checkValues("ELIGIBLE", new Eligibility().apply(eligibilityRequest_, annotationSet_, eventListener_, externalExecutor_, cache_).getEligibility());
        // Check Strategy with proto request
        proto.StrategyRequest.Builder strategyBuilder_ = proto.StrategyRequest.newBuilder();
        strategyBuilder_.setApplicantData(type.TApplicantData.toProto(applicantData));
        strategyBuilder_.setRequestedProduct(type.TRequestedProduct.toProto(requestedProduct));
        proto.StrategyRequest strategyRequest_ = strategyBuilder_.build();
        checkValues("BUREAU", new Strategy().apply(strategyRequest_, annotationSet_, eventListener_, externalExecutor_, cache_).getStrategy());
        // Check PostBureauAffordability with proto request
        proto.PostBureauAffordabilityRequest.Builder postBureauAffordabilityBuilder_ = proto.PostBureauAffordabilityRequest.newBuilder();
        postBureauAffordabilityBuilder_.setApplicantData(type.TApplicantData.toProto(applicantData));
        postBureauAffordabilityBuilder_.setBureauData(type.TBureauData.toProto(bureauData));
        postBureauAffordabilityBuilder_.setRequestedProduct(type.TRequestedProduct.toProto(requestedProduct));
        proto.PostBureauAffordabilityRequest postBureauAffordabilityRequest_ = postBureauAffordabilityBuilder_.build();
        checkValues(true, new PostBureauAffordability().apply(postBureauAffordabilityRequest_, annotationSet_, eventListener_, externalExecutor_, cache_).getPostBureauAffordability());
        // Check Routing with proto request
        proto.RoutingRequest.Builder routingBuilder_ = proto.RoutingRequest.newBuilder();
        routingBuilder_.setApplicantData(type.TApplicantData.toProto(applicantData));
        routingBuilder_.setBureauData(type.TBureauData.toProto(bureauData));
        routingBuilder_.setRequestedProduct(type.TRequestedProduct.toProto(requestedProduct));
        proto.RoutingRequest routingRequest_ = routingBuilder_.build();
        checkValues("ACCEPT", new Routing().apply(routingRequest_, annotationSet_, eventListener_, externalExecutor_, cache_).getRouting());
    }

    private void checkValues(Object expected, Object actual) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual);
    }
}
