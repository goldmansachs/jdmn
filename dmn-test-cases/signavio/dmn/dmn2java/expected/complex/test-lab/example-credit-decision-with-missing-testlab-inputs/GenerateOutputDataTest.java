
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"junit.ftl", "9acf44f2b05343d79fc35140c493c1e0/sid-8DBE416B-B1CA-43EC-BFE6-7D5DFA296EB6"})
public class GenerateOutputDataTest extends com.gs.dmn.signavio.runtime.JavaTimeSignavioBaseDecision<Object> {
    private final GenerateOutputData generateOutputData = new GenerateOutputData();

    @org.junit.jupiter.api.Test
    public void testCase1() {
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        java.lang.Number lendingThreshold = number("25");
        type.Applicant applicant = new type.ApplicantImpl(number("38"), number("100"), "Amy", asList("Late payment"));
        // Add missing arguments
        java.lang.Number currentRiskAppetite = null;
        List<type.GenerateOutputData> generateOutputData = this.generateOutputData.apply(applicant, currentRiskAppetite, lendingThreshold, context_);

        checkValues(asList(new type.GenerateOutputDataImpl(null, null, null)), generateOutputData);
    }

    @org.junit.jupiter.api.Test
    public void testCase2() {
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        java.lang.Number lendingThreshold = number("25");
        type.Applicant applicant = new type.ApplicantImpl(number("18"), number("65"), "Bill", asList("Card rejection", "Default on obligations"));
        // Add missing arguments
        java.lang.Number currentRiskAppetite = null;
        List<type.GenerateOutputData> generateOutputData = this.generateOutputData.apply(applicant, currentRiskAppetite, lendingThreshold, context_);

        checkValues(asList(new type.GenerateOutputDataImpl(null, null, null)), generateOutputData);
    }

    @org.junit.jupiter.api.Test
    public void testCase3() {
        com.gs.dmn.runtime.ExecutionContext context_ = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().build();
        java.lang.Number lendingThreshold = number("25");
        type.Applicant applicant = new type.ApplicantImpl(number("65"), number("80"), "Charlie", asList("Late payment", "Default on obligations", "Bankruptcy"));
        // Add missing arguments
        java.lang.Number currentRiskAppetite = null;
        List<type.GenerateOutputData> generateOutputData = this.generateOutputData.apply(applicant, currentRiskAppetite, lendingThreshold, context_);

        checkValues(asList(new type.GenerateOutputDataImpl(null, null, null)), generateOutputData);
    }

    private void checkValues(Object expected, Object actual) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual);
    }
}
