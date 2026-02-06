
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"junit.ftl", "0004-lending.dmn"})
public class _0004LendingTest extends com.gs.dmn.runtime.JavaTimeDMNBaseDecision<Object> {
    @org.junit.jupiter.api.Test
    public void testCase001_1() {
        // Initialize input
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();
        input_.add("ApplicantData", new type.TApplicantDataImpl(number("35"), "EMPLOYED", Boolean.TRUE, "M", new type.MonthlyImpl(number("2000"), number("6000"), number("0"))));
        input_.add("RequestedProduct", new type.TRequestedProductImpl(number("350000"), "STANDARD LOAN", number("0.0395"), number("360")));
        input_.add("BureauData", new type.TBureauDataImpl(Boolean.FALSE, number("649")));
        input_.add("SupportingDocuments", "YES");

        // Check 'Adjudication'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues("ACCEPT", new Adjudication().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase001_2() {
        // Initialize input
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();
        input_.add("ApplicantData", new type.TApplicantDataImpl(number("35"), "EMPLOYED", Boolean.TRUE, "M", new type.MonthlyImpl(number("2000"), number("6000"), number("0"))));
        input_.add("RequestedProduct", new type.TRequestedProductImpl(number("350000"), "STANDARD LOAN", number("0.0395"), number("360")));
        input_.add("BureauData", new type.TBureauDataImpl(Boolean.FALSE, number("649")));
        input_.add("SupportingDocuments", "YES");

        // Check 'ApplicationRiskScore'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues(number("130"), new ApplicationRiskScore().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase001_3() {
        // Initialize input
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();
        input_.add("ApplicantData", new type.TApplicantDataImpl(number("35"), "EMPLOYED", Boolean.TRUE, "M", new type.MonthlyImpl(number("2000"), number("6000"), number("0"))));
        input_.add("RequestedProduct", new type.TRequestedProductImpl(number("350000"), "STANDARD LOAN", number("0.0395"), number("360")));
        input_.add("BureauData", new type.TBureauDataImpl(Boolean.FALSE, number("649")));
        input_.add("SupportingDocuments", "YES");

        // Check 'Pre-bureauRiskCategory'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues("LOW", new PreBureauRiskCategory().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase001_4() {
        // Initialize input
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();
        input_.add("ApplicantData", new type.TApplicantDataImpl(number("35"), "EMPLOYED", Boolean.TRUE, "M", new type.MonthlyImpl(number("2000"), number("6000"), number("0"))));
        input_.add("RequestedProduct", new type.TRequestedProductImpl(number("350000"), "STANDARD LOAN", number("0.0395"), number("360")));
        input_.add("BureauData", new type.TBureauDataImpl(Boolean.FALSE, number("649")));
        input_.add("SupportingDocuments", "YES");

        // Check 'BureauCallType'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues("MINI", new BureauCallType().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase001_5() {
        // Initialize input
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();
        input_.add("ApplicantData", new type.TApplicantDataImpl(number("35"), "EMPLOYED", Boolean.TRUE, "M", new type.MonthlyImpl(number("2000"), number("6000"), number("0"))));
        input_.add("RequestedProduct", new type.TRequestedProductImpl(number("350000"), "STANDARD LOAN", number("0.0395"), number("360")));
        input_.add("BureauData", new type.TBureauDataImpl(Boolean.FALSE, number("649")));
        input_.add("SupportingDocuments", "YES");

        // Check 'Post-bureauRiskCategory'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues("LOW", new PostBureauRiskCategory().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase001_6() {
        // Initialize input
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();
        input_.add("ApplicantData", new type.TApplicantDataImpl(number("35"), "EMPLOYED", Boolean.TRUE, "M", new type.MonthlyImpl(number("2000"), number("6000"), number("0"))));
        input_.add("RequestedProduct", new type.TRequestedProductImpl(number("350000"), "STANDARD LOAN", number("0.0395"), number("360")));
        input_.add("BureauData", new type.TBureauDataImpl(Boolean.FALSE, number("649")));
        input_.add("SupportingDocuments", "YES");

        // Check 'RequiredMonthlyInstallment'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues(number("1680.880325608555"), new RequiredMonthlyInstallment().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase001_7() {
        // Initialize input
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();
        input_.add("ApplicantData", new type.TApplicantDataImpl(number("35"), "EMPLOYED", Boolean.TRUE, "M", new type.MonthlyImpl(number("2000"), number("6000"), number("0"))));
        input_.add("RequestedProduct", new type.TRequestedProductImpl(number("350000"), "STANDARD LOAN", number("0.0395"), number("360")));
        input_.add("BureauData", new type.TBureauDataImpl(Boolean.FALSE, number("649")));
        input_.add("SupportingDocuments", "YES");

        // Check 'Pre-bureauAffordability'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues(Boolean.TRUE, new PreBureauAffordability().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase001_8() {
        // Initialize input
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();
        input_.add("ApplicantData", new type.TApplicantDataImpl(number("35"), "EMPLOYED", Boolean.TRUE, "M", new type.MonthlyImpl(number("2000"), number("6000"), number("0"))));
        input_.add("RequestedProduct", new type.TRequestedProductImpl(number("350000"), "STANDARD LOAN", number("0.0395"), number("360")));
        input_.add("BureauData", new type.TBureauDataImpl(Boolean.FALSE, number("649")));
        input_.add("SupportingDocuments", "YES");

        // Check 'Eligibility'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues("ELIGIBLE", new Eligibility().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase001_9() {
        // Initialize input
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();
        input_.add("ApplicantData", new type.TApplicantDataImpl(number("35"), "EMPLOYED", Boolean.TRUE, "M", new type.MonthlyImpl(number("2000"), number("6000"), number("0"))));
        input_.add("RequestedProduct", new type.TRequestedProductImpl(number("350000"), "STANDARD LOAN", number("0.0395"), number("360")));
        input_.add("BureauData", new type.TBureauDataImpl(Boolean.FALSE, number("649")));
        input_.add("SupportingDocuments", "YES");

        // Check 'Strategy'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues("BUREAU", new Strategy().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase001_10() {
        // Initialize input
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();
        input_.add("ApplicantData", new type.TApplicantDataImpl(number("35"), "EMPLOYED", Boolean.TRUE, "M", new type.MonthlyImpl(number("2000"), number("6000"), number("0"))));
        input_.add("RequestedProduct", new type.TRequestedProductImpl(number("350000"), "STANDARD LOAN", number("0.0395"), number("360")));
        input_.add("BureauData", new type.TBureauDataImpl(Boolean.FALSE, number("649")));
        input_.add("SupportingDocuments", "YES");

        // Check 'Post-bureauAffordability'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues(Boolean.TRUE, new PostBureauAffordability().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase001_11() {
        // Initialize input
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();
        input_.add("ApplicantData", new type.TApplicantDataImpl(number("35"), "EMPLOYED", Boolean.TRUE, "M", new type.MonthlyImpl(number("2000"), number("6000"), number("0"))));
        input_.add("RequestedProduct", new type.TRequestedProductImpl(number("350000"), "STANDARD LOAN", number("0.0395"), number("360")));
        input_.add("BureauData", new type.TBureauDataImpl(Boolean.FALSE, number("649")));
        input_.add("SupportingDocuments", "YES");

        // Check 'Routing'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        checkValues("ACCEPT", new Routing().applyContext(input_, context_));
    }

    private void checkValues(Object expected, Object actual) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual);
    }
}
