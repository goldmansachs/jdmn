
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"junit.ftl", "0005-literal-invocation.dmn"})
public class Test0005LiteralInvocation extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    @org.junit.Test
    public void testCase001() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();
        // Initialize input data
        type.TLoan loan = new type.TLoanImpl(number("600000"), number("0.0375"), number("360"));
        java.math.BigDecimal fee = number("100");

        // Check MonthlyPayment
        checkValues(number("2878.69354943277"), new MonthlyPayment().apply(loan, fee, annotationSet_, eventListener_, externalExecutor_));
    }

    @org.junit.Test
    public void testCase002() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();
        // Initialize input data
        type.TLoan loan = new type.TLoanImpl(number("30000"), number("0.0475"), number("60"));
        java.math.BigDecimal fee = number("100");

        // Check MonthlyPayment
        checkValues(number("662.707359373292"), new MonthlyPayment().apply(loan, fee, annotationSet_, eventListener_, externalExecutor_));
    }

    @org.junit.Test
    public void testCase003() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();
        // Initialize input data
        type.TLoan loan = new type.TLoanImpl(number("600000"), number("0.0399"), number("360"));
        java.math.BigDecimal fee = number("100");

        // Check MonthlyPayment
        checkValues(number("2961.03377700389"), new MonthlyPayment().apply(loan, fee, annotationSet_, eventListener_, externalExecutor_));
    }

    private void checkValues(Object expected, Object actual) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual);
    }
}
