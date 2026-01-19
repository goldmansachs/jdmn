
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"junit.ftl", "0004-lending.dmn"})
public class _0004LendingTest extends com.gs.dmn.runtime.JavaTimeDMNBaseDecision<Object> {
    @org.junit.jupiter.api.Test
    public void testCase001_1() {
        // Initialize arguments
        type.TApplicantData applicantData = new type.TApplicantDataImpl(number("35"), "EMPLOYED", Boolean.TRUE, "M", new type.MonthlyImpl(number("2000"), number("6000"), number("0")));
        type.TRequestedProduct requestedProduct = new type.TRequestedProductImpl(number("350000"), "STANDARD LOAN", number("0.0395"), number("360"));
        type.TBureauData bureauData = new type.TBureauDataImpl(Boolean.FALSE, number("649"));
        String supportingDocuments = "YES";

        // Check 'Adjudication'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues("ACCEPT", new Adjudication().apply(applicantData, bureauData, supportingDocuments, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase001_2() {
        // Initialize arguments
        type.TApplicantData applicantData = new type.TApplicantDataImpl(number("35"), "EMPLOYED", Boolean.TRUE, "M", new type.MonthlyImpl(number("2000"), number("6000"), number("0")));
        type.TRequestedProduct requestedProduct = new type.TRequestedProductImpl(number("350000"), "STANDARD LOAN", number("0.0395"), number("360"));
        type.TBureauData bureauData = new type.TBureauDataImpl(Boolean.FALSE, number("649"));
        String supportingDocuments = "YES";

        // Check 'ApplicationRiskScore'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues(number("130"), new ApplicationRiskScore().apply(applicantData, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase001_3() {
        // Initialize arguments
        type.TApplicantData applicantData = new type.TApplicantDataImpl(number("35"), "EMPLOYED", Boolean.TRUE, "M", new type.MonthlyImpl(number("2000"), number("6000"), number("0")));
        type.TRequestedProduct requestedProduct = new type.TRequestedProductImpl(number("350000"), "STANDARD LOAN", number("0.0395"), number("360"));
        type.TBureauData bureauData = new type.TBureauDataImpl(Boolean.FALSE, number("649"));
        String supportingDocuments = "YES";

        // Check 'Pre-bureauRiskCategory'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues("LOW", new PreBureauRiskCategory().apply(applicantData, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase001_4() {
        // Initialize arguments
        type.TApplicantData applicantData = new type.TApplicantDataImpl(number("35"), "EMPLOYED", Boolean.TRUE, "M", new type.MonthlyImpl(number("2000"), number("6000"), number("0")));
        type.TRequestedProduct requestedProduct = new type.TRequestedProductImpl(number("350000"), "STANDARD LOAN", number("0.0395"), number("360"));
        type.TBureauData bureauData = new type.TBureauDataImpl(Boolean.FALSE, number("649"));
        String supportingDocuments = "YES";

        // Check 'BureauCallType'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues("MINI", new BureauCallType().apply(applicantData, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase001_5() {
        // Initialize arguments
        type.TApplicantData applicantData = new type.TApplicantDataImpl(number("35"), "EMPLOYED", Boolean.TRUE, "M", new type.MonthlyImpl(number("2000"), number("6000"), number("0")));
        type.TRequestedProduct requestedProduct = new type.TRequestedProductImpl(number("350000"), "STANDARD LOAN", number("0.0395"), number("360"));
        type.TBureauData bureauData = new type.TBureauDataImpl(Boolean.FALSE, number("649"));
        String supportingDocuments = "YES";

        // Check 'Post-bureauRiskCategory'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues("LOW", new PostBureauRiskCategory().apply(applicantData, bureauData, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase001_6() {
        // Initialize arguments
        type.TApplicantData applicantData = new type.TApplicantDataImpl(number("35"), "EMPLOYED", Boolean.TRUE, "M", new type.MonthlyImpl(number("2000"), number("6000"), number("0")));
        type.TRequestedProduct requestedProduct = new type.TRequestedProductImpl(number("350000"), "STANDARD LOAN", number("0.0395"), number("360"));
        type.TBureauData bureauData = new type.TBureauDataImpl(Boolean.FALSE, number("649"));
        String supportingDocuments = "YES";

        // Check 'RequiredMonthlyInstallment'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues(number("1680.880325608555"), new RequiredMonthlyInstallment().apply(requestedProduct, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase001_7() {
        // Initialize arguments
        type.TApplicantData applicantData = new type.TApplicantDataImpl(number("35"), "EMPLOYED", Boolean.TRUE, "M", new type.MonthlyImpl(number("2000"), number("6000"), number("0")));
        type.TRequestedProduct requestedProduct = new type.TRequestedProductImpl(number("350000"), "STANDARD LOAN", number("0.0395"), number("360"));
        type.TBureauData bureauData = new type.TBureauDataImpl(Boolean.FALSE, number("649"));
        String supportingDocuments = "YES";

        // Check 'Pre-bureauAffordability'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues(Boolean.TRUE, new PreBureauAffordability().apply(applicantData, requestedProduct, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase001_8() {
        // Initialize arguments
        type.TApplicantData applicantData = new type.TApplicantDataImpl(number("35"), "EMPLOYED", Boolean.TRUE, "M", new type.MonthlyImpl(number("2000"), number("6000"), number("0")));
        type.TRequestedProduct requestedProduct = new type.TRequestedProductImpl(number("350000"), "STANDARD LOAN", number("0.0395"), number("360"));
        type.TBureauData bureauData = new type.TBureauDataImpl(Boolean.FALSE, number("649"));
        String supportingDocuments = "YES";

        // Check 'Eligibility'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues("ELIGIBLE", new Eligibility().apply(applicantData, requestedProduct, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase001_9() {
        // Initialize arguments
        type.TApplicantData applicantData = new type.TApplicantDataImpl(number("35"), "EMPLOYED", Boolean.TRUE, "M", new type.MonthlyImpl(number("2000"), number("6000"), number("0")));
        type.TRequestedProduct requestedProduct = new type.TRequestedProductImpl(number("350000"), "STANDARD LOAN", number("0.0395"), number("360"));
        type.TBureauData bureauData = new type.TBureauDataImpl(Boolean.FALSE, number("649"));
        String supportingDocuments = "YES";

        // Check 'Strategy'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues("BUREAU", new Strategy().apply(applicantData, requestedProduct, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase001_10() {
        // Initialize arguments
        type.TApplicantData applicantData = new type.TApplicantDataImpl(number("35"), "EMPLOYED", Boolean.TRUE, "M", new type.MonthlyImpl(number("2000"), number("6000"), number("0")));
        type.TRequestedProduct requestedProduct = new type.TRequestedProductImpl(number("350000"), "STANDARD LOAN", number("0.0395"), number("360"));
        type.TBureauData bureauData = new type.TBureauDataImpl(Boolean.FALSE, number("649"));
        String supportingDocuments = "YES";

        // Check 'Post-bureauAffordability'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues(Boolean.TRUE, new PostBureauAffordability().apply(applicantData, bureauData, requestedProduct, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase001_11() {
        // Initialize arguments
        type.TApplicantData applicantData = new type.TApplicantDataImpl(number("35"), "EMPLOYED", Boolean.TRUE, "M", new type.MonthlyImpl(number("2000"), number("6000"), number("0")));
        type.TRequestedProduct requestedProduct = new type.TRequestedProductImpl(number("350000"), "STANDARD LOAN", number("0.0395"), number("360"));
        type.TBureauData bureauData = new type.TBureauDataImpl(Boolean.FALSE, number("649"));
        String supportingDocuments = "YES";

        // Check 'Routing'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues("ACCEPT", new Routing().apply(applicantData, bureauData, requestedProduct, context_));
    }

    private void checkValues(Object expected, Object actual) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual);
    }
}
