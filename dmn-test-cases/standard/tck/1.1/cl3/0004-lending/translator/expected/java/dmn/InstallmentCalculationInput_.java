
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "InstallmentCalculation"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class InstallmentCalculationInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
        private String productType;
        private java.lang.Number rate;
        private java.lang.Number term;
        private java.lang.Number amount;

    public String getProductType() {
        return this.productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public java.lang.Number getRate() {
        return this.rate;
    }

    public void setRate(java.lang.Number rate) {
        this.rate = rate;
    }

    public java.lang.Number getTerm() {
        return this.term;
    }

    public void setTerm(java.lang.Number term) {
        this.term = term;
    }

    public java.lang.Number getAmount() {
        return this.amount;
    }

    public void setAmount(java.lang.Number amount) {
        this.amount = amount;
    }

}
