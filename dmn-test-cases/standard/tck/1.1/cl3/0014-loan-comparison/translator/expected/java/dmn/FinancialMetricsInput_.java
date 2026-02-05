
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "FinancialMetrics"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class FinancialMetricsInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private type.TLoanProduct product;
    private java.lang.Number requestedAmt;

    public FinancialMetricsInput_() {
    }

    public FinancialMetricsInput_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object product = input_.get("product");
            setProduct(type.TLoanProduct.toTLoanProduct(product));
            Object requestedAmt = input_.get("requestedAmt");
            setRequestedAmt((java.lang.Number)requestedAmt);
        }
    }

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
