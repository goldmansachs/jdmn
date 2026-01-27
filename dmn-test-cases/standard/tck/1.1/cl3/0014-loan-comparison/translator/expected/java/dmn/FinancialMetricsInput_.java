
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "FinancialMetrics"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class FinancialMetricsInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private type.TLoanProduct product;
    private java.lang.Number requestedAmt;

    public type.TLoanProduct getProduct() {
        return this.product;
    }

    public void setProduct(type.TLoanProduct product) {
        this.product = product;
    }

    public java.lang.Number getRequestedAmt() {
        return this.requestedAmt;
    }

    public void setRequestedAmt(java.lang.Number requestedAmt) {
        this.requestedAmt = requestedAmt;
    }

}
