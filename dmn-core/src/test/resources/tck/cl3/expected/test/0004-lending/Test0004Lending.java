
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"junit.ftl", "0004-lending.dmn"})
public class Test0004Lending extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    @org.junit.Test
    public void testCase001() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();
        // Initialize input data
        type.TApplicantData applicantData = new type.TApplicantDataImpl(number("35"), "EMPLOYED", true, "M", new type.MonthlyImpl(number("2000"), number("6000"), number("0")));
        type.TRequestedProduct requestedProduct = new type.TRequestedProductImpl(number("350000"), "STANDARD LOAN", number("0.0395"), number("360"));
        type.TBureauData bureauData = new type.TBureauDataImpl(false, number("649"));
        String supportingDocuments = "YES";

        // Check Adjudication
        checkValues("ACCEPT", new Adjudication().apply(applicantData, bureauData, supportingDocuments, annotationSet_, eventListener_, externalExecutor_));
        // Check ApplicationRiskScore
        checkValues(number("130"), new ApplicationRiskScore().apply(applicantData, annotationSet_, eventListener_, externalExecutor_));
        // Check PreBureauRiskCategory
        checkValues("LOW", new PreBureauRiskCategory().apply(applicantData, annotationSet_, eventListener_, externalExecutor_));
        // Check BureauCallType
        checkValues("MINI", new BureauCallType().apply(applicantData, annotationSet_, eventListener_, externalExecutor_));
        // Check PostBureauRiskCategory
        checkValues("LOW", new PostBureauRiskCategory().apply(applicantData, bureauData, annotationSet_, eventListener_, externalExecutor_));
        // Check RequiredMonthlyInstallment
        checkValues(number("1680.880325608555"), new RequiredMonthlyInstallment().apply(requestedProduct, annotationSet_, eventListener_, externalExecutor_));
        // Check PreBureauAffordability
        checkValues(true, new PreBureauAffordability().apply(applicantData, requestedProduct, annotationSet_, eventListener_, externalExecutor_));
        // Check Eligibility
        checkValues("ELIGIBLE", new Eligibility().apply(applicantData, requestedProduct, annotationSet_, eventListener_, externalExecutor_));
        // Check Strategy
        checkValues("BUREAU", new Strategy().apply(applicantData, requestedProduct, annotationSet_, eventListener_, externalExecutor_));
        // Check PostBureauAffordability
        checkValues(true, new PostBureauAffordability().apply(applicantData, bureauData, requestedProduct, annotationSet_, eventListener_, externalExecutor_));
        // Check Routing
        checkValues("ACCEPT", new Routing().apply(applicantData, bureauData, requestedProduct, annotationSet_, eventListener_, externalExecutor_));
    }

    private void checkValues(Object expected, Object actual) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual);
    }
}
