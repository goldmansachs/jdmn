
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "Pre-bureauAffordability"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class PreBureauAffordabilityInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private type.TApplicantData applicantData;
    private type.TRequestedProduct requestedProduct;

    public PreBureauAffordabilityInput_() {
    }

    public PreBureauAffordabilityInput_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object applicantData = input_.get("http://www.trisotech.com/definitions/_4e0f0b70-d31c-471c-bd52-5ca709ed362b#ApplicantData");
            setApplicantData(type.TApplicantData.toTApplicantData(applicantData));
            Object requestedProduct = input_.get("http://www.trisotech.com/definitions/_4e0f0b70-d31c-471c-bd52-5ca709ed362b#RequestedProduct");
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
