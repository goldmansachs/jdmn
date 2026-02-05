
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "Pre-bureauRiskCategoryTable"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class PreBureauRiskCategoryTableInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private Boolean existingCustomer;
    private java.lang.Number applicationRiskScore;

    public PreBureauRiskCategoryTableInput_() {
    }

    public PreBureauRiskCategoryTableInput_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object existingCustomer = input_.get("ExistingCustomer");
            setExistingCustomer((Boolean)existingCustomer);
            Object applicationRiskScore = input_.get("ApplicationRiskScore");
            setApplicationRiskScore((java.lang.Number)applicationRiskScore);
        }
    }

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

}
