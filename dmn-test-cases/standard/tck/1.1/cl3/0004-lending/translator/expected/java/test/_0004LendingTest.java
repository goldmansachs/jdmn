
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"junit.ftl", "0004-lending.dmn"})
public class _0004LendingTest extends com.gs.dmn.runtime.JavaTimeDMNBaseDecision {
    @org.junit.jupiter.api.Test
    public void testCase001() {
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
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
        // Check 'Pre-bureauRiskCategory'
        checkValues("LOW", new PreBureauRiskCategory().apply(applicantData, context_));
        // Check 'BureauCallType'
        checkValues("MINI", new BureauCallType().apply(applicantData, context_));
        // Check 'Post-bureauRiskCategory'
        checkValues("LOW", new PostBureauRiskCategory().apply(applicantData, bureauData, context_));
        // Check 'RequiredMonthlyInstallment'
        checkValues(number("1680.880325608555"), new RequiredMonthlyInstallment().apply(requestedProduct, context_));
        // Check 'Pre-bureauAffordability'
        checkValues(Boolean.TRUE, new PreBureauAffordability().apply(applicantData, requestedProduct, context_));
        // Check 'Eligibility'
        checkValues("ELIGIBLE", new Eligibility().apply(applicantData, requestedProduct, context_));
        // Check 'Strategy'
        checkValues("BUREAU", new Strategy().apply(applicantData, requestedProduct, context_));
        // Check 'Post-bureauAffordability'
        checkValues(Boolean.TRUE, new PostBureauAffordability().apply(applicantData, bureauData, requestedProduct, context_));
        // Check 'Routing'
        checkValues("ACCEPT", new Routing().apply(applicantData, bureauData, requestedProduct, context_));
    }

    private void checkValues(Object expected, Object actual) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual);
    }
}
