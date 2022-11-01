
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"junit.ftl", "0004-lending.dmn"})
public class _0004LendingTest extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    @org.junit.Test
    public void testCase001() {
        com.gs.dmn.runtime.ExecutionContext context_ = new com.gs.dmn.runtime.ExecutionContext();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();
        // Initialize input data
        type.TApplicantData applicantData = new type.TApplicantDataImpl(number("35"), "EMPLOYED", Boolean.TRUE, "M", new type.MonthlyImpl(number("2000"), number("6000"), number("0")));
        type.TRequestedProduct requestedProduct = new type.TRequestedProductImpl(number("350000"), "STANDARD LOAN", number("0.0395"), number("360"));
        type.TBureauData bureauData = new type.TBureauDataImpl(Boolean.FALSE, number("649"));
        String supportingDocuments = "YES";

        // Check 'Adjudication'
        checkValues("ACCEPT", new Adjudication().apply(applicantData, bureauData, supportingDocuments, context_));
        // Check 'ApplicationRiskScore'
        checkValues(number("130"), new ApplicationRiskScore().apply(applicantData, context_));
        // Check ''Pre-bureauRiskCategory''
        checkValues("LOW", new PreBureauRiskCategory().apply(applicantData, context_));
        // Check 'BureauCallType'
        checkValues("MINI", new BureauCallType().apply(applicantData, context_));
        // Check ''Post-bureauRiskCategory''
        checkValues("LOW", new PostBureauRiskCategory().apply(applicantData, bureauData, context_));
        // Check 'RequiredMonthlyInstallment'
        checkValues(number("1680.880325608555"), new RequiredMonthlyInstallment().apply(requestedProduct, context_));
        // Check ''Pre-bureauAffordability''
        checkValues(Boolean.TRUE, new PreBureauAffordability().apply(applicantData, requestedProduct, context_));
        // Check 'Eligibility'
        checkValues("ELIGIBLE", new Eligibility().apply(applicantData, requestedProduct, context_));
        // Check 'Strategy'
        checkValues("BUREAU", new Strategy().apply(applicantData, requestedProduct, context_));
        // Check ''Post-bureauAffordability''
        checkValues(Boolean.TRUE, new PostBureauAffordability().apply(applicantData, bureauData, requestedProduct, context_));
        // Check 'Routing'
        checkValues("ACCEPT", new Routing().apply(applicantData, bureauData, requestedProduct, context_));

        // Check 'Adjudication' with proto request
        proto.AdjudicationRequest.Builder adjudicationBuilder_ = proto.AdjudicationRequest.newBuilder();
        proto.TApplicantData applicantDataProto0 = type.TApplicantData.toProto(applicantData);
        if (applicantDataProto0 != null) {
            adjudicationBuilder_.setApplicantData(applicantDataProto0);
        }
        proto.TBureauData bureauDataProto0 = type.TBureauData.toProto(bureauData);
        if (bureauDataProto0 != null) {
            adjudicationBuilder_.setBureauData(bureauDataProto0);
        }
        String supportingDocumentsProto0 = (supportingDocuments == null ? "" : supportingDocuments);
        adjudicationBuilder_.setSupportingDocuments(supportingDocumentsProto0);
        proto.AdjudicationRequest adjudicationRequest_ = adjudicationBuilder_.build();
        checkValues("ACCEPT", new Adjudication().applyProto(adjudicationRequest_, context_).getAdjudication());
        // Check 'ApplicationRiskScore' with proto request
        proto.ApplicationRiskScoreRequest.Builder applicationRiskScoreBuilder_ = proto.ApplicationRiskScoreRequest.newBuilder();
        proto.TApplicantData applicantDataProto1 = type.TApplicantData.toProto(applicantData);
        if (applicantDataProto1 != null) {
            applicationRiskScoreBuilder_.setApplicantData(applicantDataProto1);
        }
        proto.ApplicationRiskScoreRequest applicationRiskScoreRequest_ = applicationRiskScoreBuilder_.build();
        checkValues(number("130"), new ApplicationRiskScore().applyProto(applicationRiskScoreRequest_, context_).getApplicationRiskScore());
        // Check ''Pre-bureauRiskCategory'' with proto request
        proto.PreBureauRiskCategoryRequest.Builder preBureauRiskCategoryBuilder_ = proto.PreBureauRiskCategoryRequest.newBuilder();
        proto.TApplicantData applicantDataProto2 = type.TApplicantData.toProto(applicantData);
        if (applicantDataProto2 != null) {
            preBureauRiskCategoryBuilder_.setApplicantData(applicantDataProto2);
        }
        proto.PreBureauRiskCategoryRequest preBureauRiskCategoryRequest_ = preBureauRiskCategoryBuilder_.build();
        checkValues("LOW", new PreBureauRiskCategory().applyProto(preBureauRiskCategoryRequest_, context_).getPreBureauRiskCategory());
        // Check 'BureauCallType' with proto request
        proto.BureauCallTypeRequest.Builder bureauCallTypeBuilder_ = proto.BureauCallTypeRequest.newBuilder();
        proto.TApplicantData applicantDataProto3 = type.TApplicantData.toProto(applicantData);
        if (applicantDataProto3 != null) {
            bureauCallTypeBuilder_.setApplicantData(applicantDataProto3);
        }
        proto.BureauCallTypeRequest bureauCallTypeRequest_ = bureauCallTypeBuilder_.build();
        checkValues("MINI", new BureauCallType().applyProto(bureauCallTypeRequest_, context_).getBureauCallType());
        // Check ''Post-bureauRiskCategory'' with proto request
        proto.PostBureauRiskCategoryRequest.Builder postBureauRiskCategoryBuilder_ = proto.PostBureauRiskCategoryRequest.newBuilder();
        proto.TApplicantData applicantDataProto4 = type.TApplicantData.toProto(applicantData);
        if (applicantDataProto4 != null) {
            postBureauRiskCategoryBuilder_.setApplicantData(applicantDataProto4);
        }
        proto.TBureauData bureauDataProto4 = type.TBureauData.toProto(bureauData);
        if (bureauDataProto4 != null) {
            postBureauRiskCategoryBuilder_.setBureauData(bureauDataProto4);
        }
        proto.PostBureauRiskCategoryRequest postBureauRiskCategoryRequest_ = postBureauRiskCategoryBuilder_.build();
        checkValues("LOW", new PostBureauRiskCategory().applyProto(postBureauRiskCategoryRequest_, context_).getPostBureauRiskCategory());
        // Check 'RequiredMonthlyInstallment' with proto request
        proto.RequiredMonthlyInstallmentRequest.Builder requiredMonthlyInstallmentBuilder_ = proto.RequiredMonthlyInstallmentRequest.newBuilder();
        proto.TRequestedProduct requestedProductProto5 = type.TRequestedProduct.toProto(requestedProduct);
        if (requestedProductProto5 != null) {
            requiredMonthlyInstallmentBuilder_.setRequestedProduct(requestedProductProto5);
        }
        proto.RequiredMonthlyInstallmentRequest requiredMonthlyInstallmentRequest_ = requiredMonthlyInstallmentBuilder_.build();
        checkValues(number("1680.880325608555"), new RequiredMonthlyInstallment().applyProto(requiredMonthlyInstallmentRequest_, context_).getRequiredMonthlyInstallment());
        // Check ''Pre-bureauAffordability'' with proto request
        proto.PreBureauAffordabilityRequest.Builder preBureauAffordabilityBuilder_ = proto.PreBureauAffordabilityRequest.newBuilder();
        proto.TApplicantData applicantDataProto6 = type.TApplicantData.toProto(applicantData);
        if (applicantDataProto6 != null) {
            preBureauAffordabilityBuilder_.setApplicantData(applicantDataProto6);
        }
        proto.TRequestedProduct requestedProductProto6 = type.TRequestedProduct.toProto(requestedProduct);
        if (requestedProductProto6 != null) {
            preBureauAffordabilityBuilder_.setRequestedProduct(requestedProductProto6);
        }
        proto.PreBureauAffordabilityRequest preBureauAffordabilityRequest_ = preBureauAffordabilityBuilder_.build();
        checkValues(Boolean.TRUE, new PreBureauAffordability().applyProto(preBureauAffordabilityRequest_, context_).getPreBureauAffordability());
        // Check 'Eligibility' with proto request
        proto.EligibilityRequest.Builder eligibilityBuilder_ = proto.EligibilityRequest.newBuilder();
        proto.TApplicantData applicantDataProto7 = type.TApplicantData.toProto(applicantData);
        if (applicantDataProto7 != null) {
            eligibilityBuilder_.setApplicantData(applicantDataProto7);
        }
        proto.TRequestedProduct requestedProductProto7 = type.TRequestedProduct.toProto(requestedProduct);
        if (requestedProductProto7 != null) {
            eligibilityBuilder_.setRequestedProduct(requestedProductProto7);
        }
        proto.EligibilityRequest eligibilityRequest_ = eligibilityBuilder_.build();
        checkValues("ELIGIBLE", new Eligibility().applyProto(eligibilityRequest_, context_).getEligibility());
        // Check 'Strategy' with proto request
        proto.StrategyRequest.Builder strategyBuilder_ = proto.StrategyRequest.newBuilder();
        proto.TApplicantData applicantDataProto8 = type.TApplicantData.toProto(applicantData);
        if (applicantDataProto8 != null) {
            strategyBuilder_.setApplicantData(applicantDataProto8);
        }
        proto.TRequestedProduct requestedProductProto8 = type.TRequestedProduct.toProto(requestedProduct);
        if (requestedProductProto8 != null) {
            strategyBuilder_.setRequestedProduct(requestedProductProto8);
        }
        proto.StrategyRequest strategyRequest_ = strategyBuilder_.build();
        checkValues("BUREAU", new Strategy().applyProto(strategyRequest_, context_).getStrategy());
        // Check ''Post-bureauAffordability'' with proto request
        proto.PostBureauAffordabilityRequest.Builder postBureauAffordabilityBuilder_ = proto.PostBureauAffordabilityRequest.newBuilder();
        proto.TApplicantData applicantDataProto9 = type.TApplicantData.toProto(applicantData);
        if (applicantDataProto9 != null) {
            postBureauAffordabilityBuilder_.setApplicantData(applicantDataProto9);
        }
        proto.TBureauData bureauDataProto9 = type.TBureauData.toProto(bureauData);
        if (bureauDataProto9 != null) {
            postBureauAffordabilityBuilder_.setBureauData(bureauDataProto9);
        }
        proto.TRequestedProduct requestedProductProto9 = type.TRequestedProduct.toProto(requestedProduct);
        if (requestedProductProto9 != null) {
            postBureauAffordabilityBuilder_.setRequestedProduct(requestedProductProto9);
        }
        proto.PostBureauAffordabilityRequest postBureauAffordabilityRequest_ = postBureauAffordabilityBuilder_.build();
        checkValues(Boolean.TRUE, new PostBureauAffordability().applyProto(postBureauAffordabilityRequest_, context_).getPostBureauAffordability());
        // Check 'Routing' with proto request
        proto.RoutingRequest.Builder routingBuilder_ = proto.RoutingRequest.newBuilder();
        proto.TApplicantData applicantDataProto10 = type.TApplicantData.toProto(applicantData);
        if (applicantDataProto10 != null) {
            routingBuilder_.setApplicantData(applicantDataProto10);
        }
        proto.TBureauData bureauDataProto10 = type.TBureauData.toProto(bureauData);
        if (bureauDataProto10 != null) {
            routingBuilder_.setBureauData(bureauDataProto10);
        }
        proto.TRequestedProduct requestedProductProto10 = type.TRequestedProduct.toProto(requestedProduct);
        if (requestedProductProto10 != null) {
            routingBuilder_.setRequestedProduct(requestedProductProto10);
        }
        proto.RoutingRequest routingRequest_ = routingBuilder_.build();
        checkValues("ACCEPT", new Routing().applyProto(routingRequest_, context_).getRouting());
    }

    private void checkValues(Object expected, Object actual) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual);
    }
}
