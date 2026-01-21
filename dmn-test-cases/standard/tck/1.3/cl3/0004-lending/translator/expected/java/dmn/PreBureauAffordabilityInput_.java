
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "Pre-bureauAffordability"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class PreBureauAffordabilityInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
        private type.TApplicantData applicantData;
        private type.TRequestedProduct requestedProduct;

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
