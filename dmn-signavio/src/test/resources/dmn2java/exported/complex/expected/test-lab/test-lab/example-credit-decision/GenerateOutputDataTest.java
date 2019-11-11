
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"junit.ftl", "9acf44f2b05343d79fc35140c493c1e0/sid-8DBE416B-B1CA-43EC-BFE6-7D5DFA296EB6"})
public class GenerateOutputDataTest extends com.gs.dmn.signavio.runtime.DefaultSignavioBaseDecision {
    private final GenerateOutputData generateOutputData = new GenerateOutputData();

    @org.junit.Test
    public void testCase1() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        java.math.BigDecimal currentRiskAppetite = number("50");
        java.math.BigDecimal lendingThreshold = number("25");
        type.Applicant applicant = new type.ApplicantImpl(number("38"), number("100"), "Amy", asList("Late payment"));
        List<type.GenerateOutputData> generateOutputData = this.generateOutputData.apply(applicant, currentRiskAppetite, lendingThreshold, annotationSet_);

        checkValues(asList(new type.GenerateOutputDataImpl(number("27.5"), "Accept", numericUnaryMinus(number("7.5")))), generateOutputData);
    }

    @org.junit.Test
    public void testCase2() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        java.math.BigDecimal currentRiskAppetite = number("50");
        java.math.BigDecimal lendingThreshold = number("25");
        type.Applicant applicant = new type.ApplicantImpl(number("18"), number("65"), "Bill", asList("Card rejection", "Default on obligations"));
        List<type.GenerateOutputData> generateOutputData = this.generateOutputData.apply(applicant, currentRiskAppetite, lendingThreshold, annotationSet_);

        checkValues(asList(new type.GenerateOutputDataImpl(numericUnaryMinus(number("10")), "Reject", numericUnaryMinus(number("25")))), generateOutputData);
    }

    @org.junit.Test
    public void testCase3() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        java.math.BigDecimal currentRiskAppetite = number("50");
        java.math.BigDecimal lendingThreshold = number("25");
        type.Applicant applicant = new type.ApplicantImpl(number("65"), number("80"), "Charlie", asList("Late payment", "Default on obligations", "Bankruptcy"));
        List<type.GenerateOutputData> generateOutputData = this.generateOutputData.apply(applicant, currentRiskAppetite, lendingThreshold, annotationSet_);

        checkValues(asList(new type.GenerateOutputDataImpl(numericUnaryMinus(number("42.5")), "Reject", numericUnaryMinus(number("77.5")))), generateOutputData);
    }

    private void checkValues(Object expected, Object actual) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual);
    }
}
