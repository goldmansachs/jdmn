
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
        checkValues("ACCEPT", Adjudication.instance().applyContext(input_, context_));
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
        checkValues(number("130"), ApplicationRiskScore.instance().applyContext(input_, context_));
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
        checkValues("LOW", PreBureauRiskCategory.instance().applyContext(input_, context_));
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
        checkValues("MINI", BureauCallType.instance().applyContext(input_, context_));
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
        checkValues("LOW", PostBureauRiskCategory.instance().applyContext(input_, context_));
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
        checkValues(number("1680.880325608555"), RequiredMonthlyInstallment.instance().applyContext(input_, context_));
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
        checkValues(Boolean.TRUE, PreBureauAffordability.instance().applyContext(input_, context_));
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
        checkValues("ELIGIBLE", Eligibility.instance().applyContext(input_, context_));
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
        checkValues("BUREAU", Strategy.instance().applyContext(input_, context_));
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
        checkValues(Boolean.TRUE, PostBureauAffordability.instance().applyContext(input_, context_));
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
        checkValues("ACCEPT", Routing.instance().applyContext(input_, context_));
    }

    private void checkValues(Object expected, Object actual) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual);
    }
}
