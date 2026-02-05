
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "RequiredMonthlyInstallment"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class RequiredMonthlyInstallmentInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private type.TRequestedProduct requestedProduct;

    public RequiredMonthlyInstallmentInput_() {
    }

    public RequiredMonthlyInstallmentInput_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object requestedProduct = input_.get("RequestedProduct");
            setRequestedProduct(type.TRequestedProduct.toTRequestedProduct(requestedProduct));
        }
    }

    public type.TRequestedProduct getRequestedProduct() {
        return this.requestedProduct;
    }

    public void setRequestedProduct(type.TRequestedProduct requestedProduct) {
        this.requestedProduct = requestedProduct;
    }

}
