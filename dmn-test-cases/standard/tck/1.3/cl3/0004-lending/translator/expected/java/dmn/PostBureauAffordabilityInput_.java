
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "Post-bureauAffordability"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class PostBureauAffordabilityInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private type.TApplicantData applicantData;
    private type.TBureauData bureauData;
    private type.TRequestedProduct requestedProduct;

    public PostBureauAffordabilityInput_() {
    }

    public PostBureauAffordabilityInput_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object applicantData = input_.get("ApplicantData");
            setApplicantData(type.TApplicantData.toTApplicantData(applicantData));
            Object bureauData = input_.get("BureauData");
            setBureauData(type.TBureauData.toTBureauData(bureauData));
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

    public type.TBureauData getBureauData() {
        return this.bureauData;
    }

    public void setBureauData(type.TBureauData bureauData) {
        this.bureauData = bureauData;
    }

    public type.TRequestedProduct getRequestedProduct() {
        return this.requestedProduct;
    }

    public void setRequestedProduct(type.TRequestedProduct requestedProduct) {
        this.requestedProduct = requestedProduct;
    }

}
