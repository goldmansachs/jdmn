
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "Post-bureauRiskCategoryTable"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class PostBureauRiskCategoryTableInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
        private Boolean existingCustomer;
        private java.lang.Number applicationRiskScore;
        private java.lang.Number creditScore;

    public Boolean getExistingCustomer() {
        return this.existingCustomer;
    }

    public void setExistingCustomer(Boolean existingCustomer) {
        this.existingCustomer = existingCustomer;
    }

    public java.lang.Number getApplicationRiskScore() {
        return this.applicationRiskScore;
    }

    public void setApplicationRiskScore(java.lang.Number applicationRiskScore) {
        this.applicationRiskScore = applicationRiskScore;
    }

    public java.lang.Number getCreditScore() {
        return this.creditScore;
    }

    public void setCreditScore(java.lang.Number creditScore) {
        this.creditScore = creditScore;
    }

}
