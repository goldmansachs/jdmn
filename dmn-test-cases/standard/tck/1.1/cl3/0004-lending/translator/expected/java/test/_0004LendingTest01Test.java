
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"junit.ftl", "0004-lending.dmn"})
public class _0004LendingTest01Test extends com.gs.dmn.runtime.JavaTimeDMNBaseDecision<Object> {
    private static final com.gs.dmn.runtime.coverage.trace.CoverageTraceListener listener = new com.gs.dmn.runtime.coverage.trace.CoverageTraceListener("http://www.trisotech.com/definitions/_4e0f0b70-d31c-471c-bd52-5ca709ed362b", "0004-lending", 20);

    @org.junit.jupiter.api.Test
    public void testCase001_1() {
        // Initialize inputs
        type.TApplicantData applicantData = new type.TApplicantDataImpl(number("35"), "EMPLOYED", Boolean.TRUE, "M", new type.MonthlyImpl(number("2000"), number("6000"), number("0")));
        type.TRequestedProduct requestedProduct = new type.TRequestedProductImpl(number("350000"), "STANDARD LOAN", number("0.0395"), number("360"));
        type.TBureauData bureauData = new type.TBureauDataImpl(Boolean.FALSE, number("649"));
        String supportingDocuments = "YES";

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();
        input_.add("ApplicantData", applicantData);
        input_.add("RequestedProduct", requestedProduct);
        input_.add("BureauData", bureauData);
        input_.add("SupportingDocuments", supportingDocuments);

        // Check 'Adjudication'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().withEventListener(listener).build();
        checkValues("ACCEPT", new Adjudication().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase001_2() {
        // Initialize inputs
        type.TApplicantData applicantData = new type.TApplicantDataImpl(number("35"), "EMPLOYED", Boolean.TRUE, "M", new type.MonthlyImpl(number("2000"), number("6000"), number("0")));
        type.TRequestedProduct requestedProduct = new type.TRequestedProductImpl(number("350000"), "STANDARD LOAN", number("0.0395"), number("360"));
        type.TBureauData bureauData = new type.TBureauDataImpl(Boolean.FALSE, number("649"));
        String supportingDocuments = "YES";

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();
        input_.add("ApplicantData", applicantData);
        input_.add("RequestedProduct", requestedProduct);
        input_.add("BureauData", bureauData);
        input_.add("SupportingDocuments", supportingDocuments);

        // Check 'ApplicationRiskScore'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().withEventListener(listener).build();
        checkValues(number("130"), new ApplicationRiskScore().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase001_3() {
        // Initialize inputs
        type.TApplicantData applicantData = new type.TApplicantDataImpl(number("35"), "EMPLOYED", Boolean.TRUE, "M", new type.MonthlyImpl(number("2000"), number("6000"), number("0")));
        type.TRequestedProduct requestedProduct = new type.TRequestedProductImpl(number("350000"), "STANDARD LOAN", number("0.0395"), number("360"));
        type.TBureauData bureauData = new type.TBureauDataImpl(Boolean.FALSE, number("649"));
        String supportingDocuments = "YES";

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();
        input_.add("ApplicantData", applicantData);
        input_.add("RequestedProduct", requestedProduct);
        input_.add("BureauData", bureauData);
        input_.add("SupportingDocuments", supportingDocuments);

        // Check 'Pre-bureauRiskCategory'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().withEventListener(listener).build();
        checkValues("LOW", new PreBureauRiskCategory().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase001_4() {
        // Initialize inputs
        type.TApplicantData applicantData = new type.TApplicantDataImpl(number("35"), "EMPLOYED", Boolean.TRUE, "M", new type.MonthlyImpl(number("2000"), number("6000"), number("0")));
        type.TRequestedProduct requestedProduct = new type.TRequestedProductImpl(number("350000"), "STANDARD LOAN", number("0.0395"), number("360"));
        type.TBureauData bureauData = new type.TBureauDataImpl(Boolean.FALSE, number("649"));
        String supportingDocuments = "YES";

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();
        input_.add("ApplicantData", applicantData);
        input_.add("RequestedProduct", requestedProduct);
        input_.add("BureauData", bureauData);
        input_.add("SupportingDocuments", supportingDocuments);

        // Check 'BureauCallType'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().withEventListener(listener).build();
        checkValues("MINI", new BureauCallType().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase001_5() {
        // Initialize inputs
        type.TApplicantData applicantData = new type.TApplicantDataImpl(number("35"), "EMPLOYED", Boolean.TRUE, "M", new type.MonthlyImpl(number("2000"), number("6000"), number("0")));
        type.TRequestedProduct requestedProduct = new type.TRequestedProductImpl(number("350000"), "STANDARD LOAN", number("0.0395"), number("360"));
        type.TBureauData bureauData = new type.TBureauDataImpl(Boolean.FALSE, number("649"));
        String supportingDocuments = "YES";

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();
        input_.add("ApplicantData", applicantData);
        input_.add("RequestedProduct", requestedProduct);
        input_.add("BureauData", bureauData);
        input_.add("SupportingDocuments", supportingDocuments);

        // Check 'Post-bureauRiskCategory'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().withEventListener(listener).build();
        checkValues("LOW", new PostBureauRiskCategory().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase001_6() {
        // Initialize inputs
        type.TApplicantData applicantData = new type.TApplicantDataImpl(number("35"), "EMPLOYED", Boolean.TRUE, "M", new type.MonthlyImpl(number("2000"), number("6000"), number("0")));
        type.TRequestedProduct requestedProduct = new type.TRequestedProductImpl(number("350000"), "STANDARD LOAN", number("0.0395"), number("360"));
        type.TBureauData bureauData = new type.TBureauDataImpl(Boolean.FALSE, number("649"));
        String supportingDocuments = "YES";

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();
        input_.add("ApplicantData", applicantData);
        input_.add("RequestedProduct", requestedProduct);
        input_.add("BureauData", bureauData);
        input_.add("SupportingDocuments", supportingDocuments);

        // Check 'RequiredMonthlyInstallment'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().withEventListener(listener).build();
        checkValues(number("1680.880325608555"), new RequiredMonthlyInstallment().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase001_7() {
        // Initialize inputs
        type.TApplicantData applicantData = new type.TApplicantDataImpl(number("35"), "EMPLOYED", Boolean.TRUE, "M", new type.MonthlyImpl(number("2000"), number("6000"), number("0")));
        type.TRequestedProduct requestedProduct = new type.TRequestedProductImpl(number("350000"), "STANDARD LOAN", number("0.0395"), number("360"));
        type.TBureauData bureauData = new type.TBureauDataImpl(Boolean.FALSE, number("649"));
        String supportingDocuments = "YES";

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();
        input_.add("ApplicantData", applicantData);
        input_.add("RequestedProduct", requestedProduct);
        input_.add("BureauData", bureauData);
        input_.add("SupportingDocuments", supportingDocuments);

        // Check 'Pre-bureauAffordability'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().withEventListener(listener).build();
        checkValues(Boolean.TRUE, new PreBureauAffordability().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase001_8() {
        // Initialize inputs
        type.TApplicantData applicantData = new type.TApplicantDataImpl(number("35"), "EMPLOYED", Boolean.TRUE, "M", new type.MonthlyImpl(number("2000"), number("6000"), number("0")));
        type.TRequestedProduct requestedProduct = new type.TRequestedProductImpl(number("350000"), "STANDARD LOAN", number("0.0395"), number("360"));
        type.TBureauData bureauData = new type.TBureauDataImpl(Boolean.FALSE, number("649"));
        String supportingDocuments = "YES";

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();
        input_.add("ApplicantData", applicantData);
        input_.add("RequestedProduct", requestedProduct);
        input_.add("BureauData", bureauData);
        input_.add("SupportingDocuments", supportingDocuments);

        // Check 'Eligibility'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().withEventListener(listener).build();
        checkValues("ELIGIBLE", new Eligibility().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase001_9() {
        // Initialize inputs
        type.TApplicantData applicantData = new type.TApplicantDataImpl(number("35"), "EMPLOYED", Boolean.TRUE, "M", new type.MonthlyImpl(number("2000"), number("6000"), number("0")));
        type.TRequestedProduct requestedProduct = new type.TRequestedProductImpl(number("350000"), "STANDARD LOAN", number("0.0395"), number("360"));
        type.TBureauData bureauData = new type.TBureauDataImpl(Boolean.FALSE, number("649"));
        String supportingDocuments = "YES";

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();
        input_.add("ApplicantData", applicantData);
        input_.add("RequestedProduct", requestedProduct);
        input_.add("BureauData", bureauData);
        input_.add("SupportingDocuments", supportingDocuments);

        // Check 'Strategy'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().withEventListener(listener).build();
        checkValues("BUREAU", new Strategy().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase001_10() {
        // Initialize inputs
        type.TApplicantData applicantData = new type.TApplicantDataImpl(number("35"), "EMPLOYED", Boolean.TRUE, "M", new type.MonthlyImpl(number("2000"), number("6000"), number("0")));
        type.TRequestedProduct requestedProduct = new type.TRequestedProductImpl(number("350000"), "STANDARD LOAN", number("0.0395"), number("360"));
        type.TBureauData bureauData = new type.TBureauDataImpl(Boolean.FALSE, number("649"));
        String supportingDocuments = "YES";

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();
        input_.add("ApplicantData", applicantData);
        input_.add("RequestedProduct", requestedProduct);
        input_.add("BureauData", bureauData);
        input_.add("SupportingDocuments", supportingDocuments);

        // Check 'Post-bureauAffordability'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().withEventListener(listener).build();
        checkValues(Boolean.TRUE, new PostBureauAffordability().applyContext(input_, context_));
    }

    @org.junit.jupiter.api.Test
    public void testCase001_11() {
        // Initialize inputs
        type.TApplicantData applicantData = new type.TApplicantDataImpl(number("35"), "EMPLOYED", Boolean.TRUE, "M", new type.MonthlyImpl(number("2000"), number("6000"), number("0")));
        type.TRequestedProduct requestedProduct = new type.TRequestedProductImpl(number("350000"), "STANDARD LOAN", number("0.0395"), number("360"));
        type.TBureauData bureauData = new type.TBureauDataImpl(Boolean.FALSE, number("649"));
        String supportingDocuments = "YES";

        // Initialize input context
        com.gs.dmn.runtime.Context input_ = new com.gs.dmn.runtime.Context();
        input_.add("ApplicantData", applicantData);
        input_.add("RequestedProduct", requestedProduct);
        input_.add("BureauData", bureauData);
        input_.add("SupportingDocuments", supportingDocuments);

        // Check 'Routing'
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().withEventListener(listener).build();
        checkValues("ACCEPT", new Routing().applyContext(input_, context_));
    }

    private void checkValues(Object expected, Object actual) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.AfterAll
    static void saveTrace() {
        java.io.File traceDir = new java.io.File("target/coverage-traces");
        if (!traceDir.exists()) {
            traceDir.mkdirs();
        }
        java.io.File traceFile = new java.io.File(traceDir, "0004-lending-test-01.json");
        try (java.io.FileWriter writer = new java.io.FileWriter(traceFile)) {
            com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValue(writer, listener.getModelTraces());
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
