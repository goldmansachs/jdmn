
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"junit.ftl", "0003-iteration.dmn"})
public class Test0003Iteration extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    @org.junit.Test
    public void testCase001() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();
        // Initialize input data
        List<type.TLoan> loans = asList(new type.TLoanImpl(number("200000"), number(".041"), number("360")), new type.TLoanImpl(number("20000"), number(".049"), number("60")));

        // Check MonthlyPayment
        List<java.math.BigDecimal> monthlyPaymentOutput = new MonthlyPayment().apply(loans, annotationSet_, eventListener_, externalExecutor_);
        checkValues(asList(number("966.396742204988"), number("376.509070632494")), monthlyPaymentOutput);
    }

    private void checkValues(Object expected, Object actual) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual);
    }
}
