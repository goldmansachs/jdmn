
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "Strategy"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class StrategyInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private type.TApplicantData applicantData;
    private type.TRequestedProduct requestedProduct;

    public StrategyInput_() {
    }

    public StrategyInput_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object applicantData = input_.get("ApplicantData");
            setApplicantData(type.TApplicantData.toTApplicantData(applicantData));
            Object requestedProduct = input_.get("RequestedProduct");
            setRequestedProduct(type.TRequestedProduct.toTRequestedProduct(requestedProduct));
        }
    }

    public type.TApplicantData getApplicantData() {
        return this.applicantData;
    }

    public void setApplicantData(type.TApplicantData applicantData) {
        this.applicantData = applicantData;
    }

    public type.TRequestedProduct getRequestedProduct() {
        return this.requestedProduct;
    }

    public void setRequestedProduct(type.TRequestedProduct requestedProduct) {
        this.requestedProduct = requestedProduct;
    }

}
