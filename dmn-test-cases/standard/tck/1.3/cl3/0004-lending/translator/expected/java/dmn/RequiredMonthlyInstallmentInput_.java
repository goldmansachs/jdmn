
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "RequiredMonthlyInstallment"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class RequiredMonthlyInstallmentInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private type.TRequestedProduct requestedProduct;

    public type.TRequestedProduct getRequestedProduct() {
        return this.requestedProduct;
    }

    public void setRequestedProduct(type.TRequestedProduct requestedProduct) {
        this.requestedProduct = requestedProduct;
    }

}
